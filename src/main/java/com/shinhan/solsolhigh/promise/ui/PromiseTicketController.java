package com.shinhan.solsolhigh.promise.ui;

import com.shinhan.solsolhigh.promise.application.*;
import com.shinhan.solsolhigh.promise.ui.request.PromiseTicketUseRequestRequest;
import com.shinhan.solsolhigh.promise.ui.response.PromiseTicketUnusedCountResponse;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/count")
    public ResponseEntity<?> countUnusedPromiseTicketById(@AuthenticationPrincipal UserPrinciple userPrinciple){
        Long count = promiseTicketService.countUnusedPromiseTicketById(userPrinciple.getId());
        return ResponseEntity.ok(PromiseTicketUnusedCountResponse.builder().count(count).build());
    }

    @GetMapping("/children/{nickname}/count")
    public ResponseEntity<?> countUnusedPromiseTicketById(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                          @PathVariable String nickname){
        Long count = promiseTicketService.countUnusedPromiseTicketByNickname(PromiseTicketUnusedCountByChildDto.builder()
                .id(userPrinciple.getId())
                .nickname(nickname)
                .build());
        return ResponseEntity.ok(PromiseTicketUnusedCountResponse.builder().count(count).build());
    }

    @GetMapping
    public ResponseEntity<?> getUsedPromiseTicketById(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                      Pageable pageable){
        Page<PromiseTicketInfo> infos = promiseTicketService.getPromiseTicketInfosById(
                PromiseTicketUsedByIdDto.builder()
                        .id(userPrinciple.getId())
                        .pageable(pageable)
                        .build());
        return ResponseEntity.ok(infos);
    }

    @GetMapping("/children/{nickname}")
    public ResponseEntity<?> getUsedPromiseTicketByNickname(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                      @PathVariable String nickname,
                                                      Pageable pageable){
        Page<PromiseTicketInfo> infos = promiseTicketService.getPromiseTicketInfosByNickname(
                PromiseTicketUsedByNicknameDto.builder()
                        .id(userPrinciple.getId())
                        .pageable(pageable)
                        .nickname(nickname)
                        .build());
        return ResponseEntity.ok(infos);
    }
}
