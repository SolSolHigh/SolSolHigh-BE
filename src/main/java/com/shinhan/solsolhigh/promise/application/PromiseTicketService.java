package com.shinhan.solsolhigh.promise.application;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.common.util.ListUtils;
import com.shinhan.solsolhigh.promise.domain.PromiseTicket;
import com.shinhan.solsolhigh.promise.domain.PromiseTicketRepository;
import com.shinhan.solsolhigh.promise.exception.PromiseTicketNotExistsException;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.exception.FamilyNotExistException;
import com.shinhan.solsolhigh.user.exception.ParentNotFoundException;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import com.shinhan.solsolhigh.user.exception.UserWithdrawalException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PromiseTicketService {
    private final PromiseTicketRepository promiseTicketRepository;
    private final ChildRepository childRepository;

    @Transactional
    @Authorized(allowed = User.Type.CHILD)
    public void useRequestPromiseTicket(@Valid PromiseTicketUseRequestDto dto) {
        PromiseTicket ticket = promiseTicketRepository.findOneUnrequestedTicketByChildId(dto.getId()).orElseThrow(PromiseTicketNotExistsException::new);
        ticket.initRequestAt();
        ticket.changeDescription(dto.getDescription());
        //TODO. 부모에게 알림 전송
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.CHILD)
    public Long countUnusedPromiseTicketById(Integer id) {
        return promiseTicketRepository.countUnusedTicketByChildId(id);
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.PARENT)
    public Long countUnusedPromiseTicketByNickname(PromiseTicketUnusedCountByChildDto dto) {
        Child child = childRepository.findByNickname(dto.getNickname()).orElseThrow(UserNotFoundException::new);
        if(child.getIsDeleted())
            throw new UserWithdrawalException();
        if(child.getParent() == null)
            throw new ParentNotFoundException();
        if(!Objects.equals(child.getParent().getId(),dto.getId()))
            throw new FamilyNotExistException();
        return promiseTicketRepository.countUnusedTicketByChildId(child.getId());
    }

    public Page<PromiseTicketInfo> getPromiseTicketInfosById(PromiseTicketUsedByIdDto dto) {
        List<PromiseTicket> promiseTickets = promiseTicketRepository.findByUsedPromiseTicketByIdUsingPagination(dto.getId(), dto.getPageable());
        Long count = promiseTicketRepository.countUnusedTicketById(dto.getId());
        return new PageImpl<>(ListUtils.applyFunctionToElements(promiseTickets, PromiseTicketInfo::from), dto.getPageable(), count);
    }
}
