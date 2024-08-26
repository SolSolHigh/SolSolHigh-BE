package com.shinhan.solsolhigh.promise.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.promise.application.PromiseTicketService;
import com.shinhan.solsolhigh.promise.ui.request.PromiseTicketUseRequestRequest;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promise-tickets")
@AllArgsConstructor
public class PromiseTicketController {

    private final PromiseTicketService promiseTicketService;

    @PostMapping("/request")
    public ResponseEntity<?> useRequestPromiseTicket(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                     @Valid @RequestBody PromiseTicketUseRequestRequest request){
        promiseTicketService.useRequestPromiseTicket(request.toDto(userPrinciple));
        return ResponseEntity.ok().build();
    }
}
