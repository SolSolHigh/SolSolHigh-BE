package com.shinhan.solsolhigh.account.ui;

import com.shinhan.solsolhigh.account.application.*;
import com.shinhan.solsolhigh.account.infra.MasterBankService;
import com.shinhan.solsolhigh.account.ui.request.*;
import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.user.application.UserService;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AccountController {
    private final MasterBankService masterBankService;
    private final AccountService accountService;
    private final UserService userService;

    @GetMapping("/accounts/demand-deposit/products")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> getDemandDepositProducts(){
        List<DemandDepositProductDto> dtos =  masterBankService.getDemandDepositProducts();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/accounts/deposit/products")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> getDepositProducts(){
        List<DemandDepositProductDto> dtos =  masterBankService.getDemandDepositProducts();
        for(DemandDepositProductDto dto : dtos)
            dto.changeAccountType("2");
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/accounts/saving/products")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> getSavingProducts(){
        List<SavingProductDto> dtos =  masterBankService.getSavingProducts();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/accounts")
    public ResponseEntity<?> getSessionAccounts(@AuthenticationPrincipal UserPrinciple userPrinciple){
        List<AccountDetail> dtos =  accountService.getAccounts(userPrinciple.getId());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/accounts/children/{nickname}")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> getChildAccounts(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                              @PathVariable String nickname){
        List<AccountDetail> dtos =  accountService.getChildAccounts(userPrinciple.getId(), nickname);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/accounts/demand-deposit")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> createDemandDepositAccount(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                        @RequestBody AccountCreateRequest request){
        accountService.createDemandDepositAccount(userPrinciple.getId(), request.getAccountTypeUniqueNo());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/accounts/children/{nickname}/demand-deposit")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> createDemandDepositAccountByChild(@RequestBody AccountCreateRequest request,
                                                        @PathVariable String nickname){
        accountService.createDemandDepositAccountByChild(nickname, request.getAccountTypeUniqueNo());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/accounts/children/{nickname}/deposit")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> createDepositAccountByChild(@RequestBody DepositAccountCreateRequest request,
                                                         @PathVariable String nickname){
        accountService.createDepositAccountByChild(nickname, request.getAccountTypeUniqueNo(), request.getDepositGoalMoney());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/accounts/children/{nickname}/saving")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> createSavingAccountByChild(@RequestBody SavingAccountCreateRequest request,
                                                        @PathVariable String nickname){
        accountService.createSavingAccountByChild(nickname, request.getAccountTypeUniqueNo(), request.getDepositBalance());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/accounts/children/{nickname}/deposit/scheduled-transfer")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> getChildDepositRewardMoney(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                        @PathVariable String nickname){
        Integer depositRewardMoney = userService.getChildDepositRewardMoney(userPrinciple.getId(), nickname);
        return ResponseEntity.ok(Map.of("depositRewardMoney", depositRewardMoney));
    }

    @PutMapping("/accounts/children/{nickname}/deposit/scheduled-transfer")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> changeChildDepositRewardMoney(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                           @PathVariable String nickname,
                                                           @RequestBody ChildDepositRewardMoneyChangeRequest request){
        userService.changeChildDepositRewardMoney(userPrinciple.getId(), nickname, request.getDepositRewardMoney());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/accounts/children/{nickname}/deposit/scheduled-transfer")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> changeChildDepositRewardMoney(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                           @PathVariable String nickname){
        userService.deleteChildDepositRewardMoney(userPrinciple.getId(), nickname);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/accounts/children/{nickname}/saving/scheduled-transfer")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> getChildSavingRewardMoney(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                        @PathVariable String nickname){
        Integer savingRewardMoney = userService.getChildSavingRewardMoney(userPrinciple.getId(), nickname);
        return ResponseEntity.ok(Map.of("depositSavingMoney", savingRewardMoney));
    }

    @PutMapping("/accounts/children/{nickname}/saving/scheduled-transfer")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> changeChildSavingRewardMoney(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                           @PathVariable String nickname,
                                                           @RequestBody ChildSavingRewardMoneyChangeRequest request){
        userService.changeChildSavingRewardMoney(userPrinciple.getId(), nickname, request.getSavingRewardMoney());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/accounts/children/{nickname}/saving/scheduled-transfer")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> deleteChildSavingRewardMoney(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                          @PathVariable String nickname){
        userService.deleteChildSavingRewardMoney(userPrinciple.getId(), nickname);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/accounts/saving")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> deleteChildSavingAccount(@AuthenticationPrincipal UserPrinciple userPrinciple){
        accountService.deleteChildSavingAccount(userPrinciple.getId());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/accounts/deposit")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> deleteChildDepositAccount(@AuthenticationPrincipal UserPrinciple userPrinciple){
        accountService.deleteChildDepositAccount(userPrinciple.getId());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/accounts/transfer")
    public ResponseEntity<?> transferMoney(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                           @RequestBody AccountTransferMoneyRequest request){
        accountService.transferAccount(request.toDto(userPrinciple.getId()));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/accounts/demand-deposit")
    public ResponseEntity<?> getDemandDepositAccount(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                     @RequestParam String startDate,
                                                     @RequestParam String endDate){
        List<DemandDepositAccountTransaction> dtos = accountService.getDemandDepositAccount(userPrinciple.getId(),startDate,endDate);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/accounts/deposit")
    public ResponseEntity<?> getDepositAccount(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                     @RequestParam String startDate,
                                                     @RequestParam String endDate){
        List<DemandDepositAccountTransaction> dtos = accountService.getDepositAccount(userPrinciple.getId(),startDate,endDate);
        return ResponseEntity.ok(dtos);
    }
}