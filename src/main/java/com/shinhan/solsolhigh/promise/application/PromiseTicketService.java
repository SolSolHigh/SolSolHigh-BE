package com.shinhan.solsolhigh.promise.application;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.promise.domain.PromiseTicket;
import com.shinhan.solsolhigh.promise.domain.PromiseTicketRepository;
import com.shinhan.solsolhigh.promise.exception.PromiseTicketNotExistsException;
import com.shinhan.solsolhigh.user.domain.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PromiseTicketService {
    private final PromiseTicketRepository promiseTicketRepository;

    @Transactional
    @Authorized(allowed = User.Type.CHILD)
    public void useRequestPromiseTicket(@Valid PromiseTicketUseRequestDto dto) {
        PromiseTicket ticket = promiseTicketRepository.findOneUnrequestedTicketByChildId(dto.getId()).orElseThrow(PromiseTicketNotExistsException::new);
        ticket.initRequestAt();
        ticket.changeDescription(dto.getDescription());
        //TODO. 부모에게 알림 전송
    }
}
