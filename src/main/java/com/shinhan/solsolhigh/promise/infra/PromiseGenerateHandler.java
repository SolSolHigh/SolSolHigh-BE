package com.shinhan.solsolhigh.promise.infra;

import com.shinhan.solsolhigh.promise.domain.PromiseTicket;
import com.shinhan.solsolhigh.promise.domain.PromiseTicketRepository;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PromiseGenerateHandler {
    private final PromiseTicketRepository promiseTicketRepository;
    private final ChildRepository childRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generatePromiseTicket(PromiseGenertedEvent event) {
        Child child = childRepository.getReferenceById(event.getChildId());
        promiseTicketRepository.save(PromiseTicket.builder().child(child).build());
    }
}
