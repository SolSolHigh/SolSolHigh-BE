package com.shinhan.solsolhigh.account.application;

import com.shinhan.solsolhigh.account.domain.*;
import com.shinhan.solsolhigh.account.exception.AccountAlreadyExistsException;
import com.shinhan.solsolhigh.account.exception.AccountNotFoundException;
import com.shinhan.solsolhigh.account.infra.MasterBankService;
import com.shinhan.solsolhigh.user.domain.*;
import com.shinhan.solsolhigh.user.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AccountService {
    private final MasterBankService masterBankService;
    private final MasterBankMemberRepository masterBankMemberRepository;
    private final AccountRepository accountRepository;
    private final DepositRepository depositRepository;
    private final DemandDepositRepository demandDepositRepository;
    private final SavingRepository savingRepository;
    private final UserRepository userRepository;
    private final ChildRepository childRepository;

    @Transactional
    public void initBankMember(Integer id, String email){
        String userKey = masterBankService.createBankMember(email);
        if(userKey == null)
            userKey = masterBankService.getBankMember(email);
        masterBankMemberRepository.save(MasterBankMember.builder()
                .id(id)
                .userKey(userKey)
                .build());
    }

    @Transactional
    public List<AccountDetail> getChildAccounts(Integer id, String nickname){
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent() == null)
            throw new ParentNotFoundException();
        if(!Objects.equals(child.getParent().getId(), id))
            throw new FamilyNotExistException();
        return getAccounts(child.getId());
    }


    @Transactional
    public List<AccountDetail> getAccounts(Integer id){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        String userKey = user.getMasterBankMember().getUserKey();
        List<Account> accounts = accountRepository.findByUserId(id);
        List<AccountDetail> result = new ArrayList<>();
        Child child;
        for(Account account : accounts){
            switch(account.getType()){
                case DEMAND_DEPOSIT:
                    result.add(masterBankService.getDemandDepositAccount(userKey,account.getAccountNo()));
                    break;
                case DEPOSIT:
                    DepositDetail depositDetail = masterBankService.getDepositAccount(userKey,account.getAccountNo());
                    child = childRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
                    depositDetail.setDepositGoalMoney(child.getDepositGoalMoney());
                    depositDetail.setDepositRewardMoney(child.getDepositRewardMoney());
                    result.add(depositDetail);
                    break;
                case SAVING:
                    SavingDetail savingDetail = masterBankService.getSavingAccount(userKey,account.getAccountNo());
                    child = childRepository.findById(user.getId()).orElseThrow(UserNotFoundException::new);
                    savingDetail.setSavingRewardMoney(child.getSavingRewardMoney());
                    result.add(savingDetail);
                    break;
            }
        }
        return result;
    }

    @Transactional
    public void createDemandDepositAccount(Integer id, String accountTypeUniqueNo) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        String userKey = user.getMasterBankMember().getUserKey();
        if(demandDepositRepository.existsByUserId(id))
            throw new AccountAlreadyExistsException();
        String accountNo = masterBankService.createDepositAccount(userKey, accountTypeUniqueNo);
        demandDepositRepository.save(
                DemandDeposit.builder()
                        .user(user)
                        .accountNo(accountNo)
                        .build());
    }

    @Transactional
    public void createDemandDepositAccountByChild(String nickname, String accountTypeUniqueNo) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent() == null)
            throw new ParentNotFoundException();
        createDemandDepositAccount(child.getId(), accountTypeUniqueNo);
    }

    @Transactional
    public void createDepositAccountByChild(String nickname, String accountTypeUniqueNo, Integer depositGoalMoney) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent() == null)
            throw new ParentNotFoundException();
        String userKey = child.getMasterBankMember().getUserKey();
        if(depositRepository.existsByUserId(child.getId()))
            throw new AccountAlreadyExistsException();
        String accountNo = masterBankService.createDepositAccount(userKey, accountTypeUniqueNo);
        depositRepository.save(
                Deposit.builder()
                        .user(child)
                        .accountNo(accountNo)
                        .build());
        child.changeDepositGoalMoney(depositGoalMoney);
    }

    @Transactional
    public void createSavingAccountByChild(String nickname, String accountTypeUniqueNo, Long depositBalance) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent() == null)
            throw new ParentNotFoundException();
        String userKey = child.getMasterBankMember().getUserKey();
        if(savingRepository.existsByUserId(child.getId()))
            throw new AccountAlreadyExistsException();
        DemandDeposit demandDeposit = demandDepositRepository.findOneByUserId(child.getId());
        String accountNo = masterBankService.createSavingAccount(userKey, demandDeposit.getAccountNo(), accountTypeUniqueNo, depositBalance);
        savingRepository.save(
                Saving.builder()
                        .user(child)
                        .accountNo(accountNo)
                        .build());
    }
    @Transactional
    public void deleteChildSavingAccount(Integer id) {
        Child child = childRepository.findById(id).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        Saving account = savingRepository.findByUserId(child.getId()).orElseThrow(AccountNotFoundException::new);
        String userKey = child.getMasterBankMember().getUserKey();
        masterBankService.deleteSavingAccount(userKey, account.getAccountNo());
        savingRepository.delete(account);
    }

    @Transactional
    public void deleteChildDepositAccount(Integer id) {
        Child child = childRepository.findById(id).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        Deposit account = depositRepository.findByUserId(child.getId()).orElseThrow(AccountNotFoundException::new);
        String userKey = child.getMasterBankMember().getUserKey();
        DemandDeposit refundAccount = demandDepositRepository.findByUserId(child.getId()).orElseThrow(AccountNotFoundException::new);
        DemandDepositDetail detail = masterBankService.getDemandDepositAccount(userKey, account.getAccountNo());
        if(Integer.parseInt(detail.getAccountBalance()) >= child.getDepositGoalMoney()){
            Parent parent = child.getParent();
            if(parent == null)
                throw new ParentNotFoundException();
            if(parent.getIsDeleted())
                throw new UserWithdrawalException();
            masterBankService.transferDemandDepositAccount(
                    parent.getMasterBankMember().getUserKey(),
                    refundAccount.getAccountNo(),
                    Long.valueOf(child.getDepositRewardMoney()),
                    demandDepositRepository.findOneByUserId(parent.getId()).getAccountNo(),
                    "저축 모으기 성공",
                    "저축 모으기 성공"
                    );
        }
        masterBankService.deleteDemandDepositAccount(userKey, account.getAccountNo(), refundAccount.getAccountNo());
        depositRepository.delete(account);
    }

    @Transactional
    public void transferAccount(AccountTransferMoneyDto dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(UserNotFoundException::new);
        DemandDeposit demandDeposit = demandDepositRepository.findByUserId(dto.getId()).orElseThrow(AccountNotFoundException::new);
        masterBankService.transferDemandDepositAccount(user.getMasterBankMember().getUserKey(),
                dto.getDepositAccountNo(),
                dto.getTransactionBalance(),
                demandDeposit.getAccountNo(),
                dto.getTransactionSummary(),
                dto.getTransactionSummary());
    }

    private void checkDeletedUser(User user){
        if(user.getIsDeleted())
            throw new UserWithdrawalException();
    }

    @Transactional
    public List<DemandDepositAccountTransaction> getDemandDepositAccount(Integer id, String startDate, String endDate) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        DemandDeposit account = demandDepositRepository.findByUserId(id).orElseThrow(AccountNotFoundException::new);
        return masterBankService.getDemandDepositAccountHistory(user.getMasterBankMember().getUserKey(),
                account.getAccountNo(),
                startDate,
                endDate);

    }

    @Transactional
    public List<DemandDepositAccountTransaction> getDepositAccount(Integer id, String startDate, String endDate) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Deposit account = depositRepository.findByUserId(id).orElseThrow(AccountNotFoundException::new);
        return masterBankService.getDemandDepositAccountHistory(user.getMasterBankMember().getUserKey(),
                account.getAccountNo(),
                startDate,
                endDate);
    }

}
