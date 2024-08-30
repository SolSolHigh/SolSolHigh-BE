package com.shinhan.solsolhigh.promise.application;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.common.event.Events;
import com.shinhan.solsolhigh.common.util.ListUtils;
import com.shinhan.solsolhigh.notification.domain.NotificationType;
import com.shinhan.solsolhigh.notification.infra.NotificationRequestedEvent;
import com.shinhan.solsolhigh.promise.domain.PromiseTicket;
import com.shinhan.solsolhigh.promise.domain.PromiseTicketRepository;
import com.shinhan.solsolhigh.promise.exception.PromiseTicketAlreadyUsedException;
import com.shinhan.solsolhigh.promise.exception.PromiseTicketNotExistsException;
import com.shinhan.solsolhigh.promise.exception.PromiseTicketNotFoundException;
import com.shinhan.solsolhigh.s3.exception.ImageUploadFailException;
import com.shinhan.solsolhigh.s3.upload.S3Uploader;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PromiseTicketService {
    private final PromiseTicketRepository promiseTicketRepository;
    private final ChildRepository childRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    @Authorized(allowed = User.Type.CHILD)
    public void useRequestPromiseTicket(@Valid PromiseTicketUseRequestDto dto) {
        PromiseTicket ticket = promiseTicketRepository.findOneUnrequestedTicketByChildId(dto.getId()).orElseThrow(PromiseTicketNotExistsException::new);
        ticket.initRequestAt();
        ticket.changeDescription(dto.getDescription());
        //TODO. 부모에게 알림 전송
        Events.raise(NotificationRequestedEvent.builder().userId(ticket.getChild().getParent().getId()).notificationType(NotificationType.PROMISE_TICKET_USE_REQUEST).bodyValue(ticket.getChild().getNickname()).targetId(ticket.getId().toString()).timestamp(LocalDateTime.now()));
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

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.CHILD)
    public Page<PromiseTicketInfo> getPromiseTicketInfosById(PromiseTicketUsedByIdDto dto) {
        List<PromiseTicket> promiseTickets = promiseTicketRepository.findByUsedPromiseTicketByIdUsingPagination(dto.getId(), dto.getPageable());
        Long count = promiseTicketRepository.countUsedTicketById(dto.getId());
        return new PageImpl<>(ListUtils.applyFunctionToElements(promiseTickets, PromiseTicketInfo::from), dto.getPageable(), count);
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.PARENT)
    public Page<PromiseTicketInfo> getPromiseTicketInfosByNickname(PromiseTicketUsedByNicknameDto dto) {
        Child child = childRepository.findByNickname(dto.getNickname()).orElseThrow(UserNotFoundException::new);
        if(child.getIsDeleted())
            throw new UserWithdrawalException();
        if(child.getParent() == null)
            throw new ParentNotFoundException();
        if(!Objects.equals(child.getParent().getId(),dto.getId()))
            throw new FamilyNotExistException();

        List<PromiseTicket> promiseTickets = promiseTicketRepository.findByUsedPromiseTicketByIdUsingPagination(child.getId(), dto.getPageable());
        Long count = promiseTicketRepository.countUsedTicketById(child.getId());
        return new PageImpl<>(ListUtils.applyFunctionToElements(promiseTickets, PromiseTicketInfo::from), dto.getPageable(), count);
    }

    @Transactional
    @Authorized(allowed = User.Type.PARENT)
    public void usePromiseTicket(@Valid PromiseTicketUseDto dto) {
        PromiseTicket promiseTicket = promiseTicketRepository.findById(dto.getPromiseTicketId()).orElseThrow(PromiseTicketNotFoundException::new);
        Child child = promiseTicket.getChild();

        if(child.getIsDeleted())
            throw new UserWithdrawalException();
        if(child.getParent() == null)
            throw new ParentNotFoundException();
        if(!Objects.equals(child.getParent().getId(),dto.getId()))
            throw new FamilyNotExistException();

        promiseTicket.initUsedAt();
        try {
            String url = s3Uploader.upload(dto.getImage(), "promiseTicket/1");
            promiseTicket.changeImageUrl(url);
        } catch (IOException e) {
            throw new ImageUploadFailException();
        }
    }
}
