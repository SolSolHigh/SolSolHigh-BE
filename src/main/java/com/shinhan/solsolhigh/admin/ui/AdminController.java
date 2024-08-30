package com.shinhan.solsolhigh.admin.ui;

import com.shinhan.solsolhigh.account.infra.MasterBankService;
import com.shinhan.solsolhigh.admin.ui.request.AccountPlusRequest;
import com.shinhan.solsolhigh.admin.ui.request.DemandDepositCreateRequest;
import com.shinhan.solsolhigh.admin.ui.request.SavingCreateRequest;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import com.shinhan.solsolhigh.user.domain.UserRepository;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final UserRepository userRepository;
    private final MasterBankService masterBankService;

    @PostMapping("/demand-deposit")
    public ResponseEntity<?> createDemandDeposit(@RequestBody DemandDepositCreateRequest request){
        masterBankService.createDemandDepositProduct(request.getAccountName(), request.getAccountDescription());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saving")
    public ResponseEntity<?> createSaving(@RequestBody SavingCreateRequest request){
        masterBankService.createSavingProduct(request.getAccountName(),
                request.getAccountDescription(),
                request.getSubscriptionPeriod(),
                request.getMinSubscriptionBalance(),
                request.getMaxSubscriptionBalance(),
                request.getInterestRate(),
                request.getRateDescription());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/money/plus")
    public ResponseEntity<?> plusAccountMoney(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                              @RequestBody AccountPlusRequest request){
        User user = userRepository.findById(userPrinciple.getId()).orElseThrow(UserNotFoundException::new);
        masterBankService.plusAccountMoney(user.getMasterBankMember().getUserKey(),
                request.getAccountNo(),
                request.getTransactionBalance(),
                request.getTransactionSummary());
        return ResponseEntity.ok().build();
    }

}
