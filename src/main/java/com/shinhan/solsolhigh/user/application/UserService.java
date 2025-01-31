package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.account.application.AccountService;
import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.common.event.Events;
import com.shinhan.solsolhigh.common.util.ListUtils;
import com.shinhan.solsolhigh.notification.domain.NotificationType;
import com.shinhan.solsolhigh.notification.infra.NotificationRequestedEvent;
import com.shinhan.solsolhigh.user.domain.*;
import com.shinhan.solsolhigh.user.exception.*;
import com.shinhan.solsolhigh.user.infra.ProductAiChat;
import com.shinhan.solsolhigh.user.infra.ProductAiDraw;
import com.shinhan.solsolhigh.user.infra.ProductRecommendDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final HttpSession session;
    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final ChildRegisterRequestRepository childRegisterRequestRepository;
    private final TemporaryUserRepository temporaryUserRepository;
    private final ParentRepository parentRepository;
    private final AccountService accountService;
    private final ProductAiChat productAiChat;
    private final ProductAiDraw productAiDraw;


    @Transactional(readOnly = true)
    public boolean checkDuplicatedNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Integer id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return UserInfo.from(user);
    }

    @Transactional
    public void modifyUserInfo(@Valid UserInfoModifyDto dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(UserNotFoundException::new);
        modifyUser(user, dto);
    }

    private void modifyUser(User user, UserInfoModifyDto dto) {
        if(dto.getNickname() != null){
            if(userRepository.existsByNickname(dto.getNickname()))
                throw new UserNicknameDuplicatedException();
            user.changeNickname(dto.getNickname());
        }
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.PARENT)
    public ChildInfo getChildInfoByNickname(String nickname) {
        Child child = childRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        return ChildInfo.from(child);
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.PARENT)
    public List<ChildInfo> getSessionChildrenInfo(Integer id) {
        List<Child> children = ListUtils.removeIf(childRepository.findByParentId(id), User::getIsDeleted);
        return ListUtils.applyFunctionToElements(children, ChildInfo::from);
    }

    @Transactional
    @Authorized(allowed = User.Type.PARENT)
    public void removeChildFromParent(ChildRemoveFromParentDto dto) {
        Child child = childRepository.findByNickname(dto.getNickname()).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent() == null || !Objects.equals(child.getParent().getId(), dto.getId()))
            throw new ChildUnregisteredException();
        child.changeParent(null);
    }


    @Transactional
    @Authorized(allowed = User.Type.PARENT)
    public void requestRegisterChildFromParent(ChildRegisterRequestFromParentDto dto) {
        Child child = childRepository.findByNickname(dto.getNickname()).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        checkExistsParent(child);
        Parent parent = parentRepository.findById(dto.getId()).orElseThrow(UserNotFoundException::new);
        if(childRegisterRequestRepository.existsByChildIdAndParentIdAndState(child.getId(), parent.getId(), ChildRegisterRequest.State.REQUESTED))
            throw new ChildRegisterRequestAlreadyRequestedException();

        ChildRegisterRequest request = ChildRegisterRequest.builder()
                .child(child)
                .parent(parent)
                .build();

        ChildRegisterRequest save = childRegisterRequestRepository.save(request);
        Events.raise(NotificationRequestedEvent.builder().userId(child.getId()).notificationType(NotificationType.CHILD_ENROLL_REQUEST).bodyValue(parent.getNickname()).targetId(save.getId().toString()).timestamp(LocalDateTime.now()).build());
    }

    @Transactional
    public void withdrawalUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(user.getType()== User.Type.CHILD)
            childDeletePreProcessing(user);
        else
            parentDeletePreProcessing(user);
        userRepository.delete(user);
        session.invalidate();;
    }

    @Transactional
    @Authorized(allowed = User.Type.CHILD)
    public void responseRegisterChildFromParent(ChildRegisterResponseFromParentDto dto) {
        Child child = childRepository.findByIdUsingFetchParent(dto.getId()).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        checkExistsParent(child);

        ChildRegisterRequest request = childRegisterRequestRepository.findById(dto.getRequestId()).orElseThrow(ChildRegisterRequestNotFoundException::new);

        if(!request.getChild().equals(child))
            throw new ChildRegisterRequestMisMatchException();

        Parent parent = request.getParent();
        if (parent == null)
            throw new ChildRegisterRequestMisMatchException();
        checkDeletedUser(parent);

        if(dto.getIsAccept()){
            request.changeState(ChildRegisterRequest.State.ACCEPTED);
            child.changeParent(parent);
            Events.raise(NotificationRequestedEvent.builder().userId(parent.getId()).notificationType(NotificationType.CHILD_ENROLL_ACCESS).bodyValue(child.getNickname()).targetId(request.getId().toString()).timestamp(LocalDateTime.now()).build());
        } else {
            request.changeState(ChildRegisterRequest.State.REJECTED);
            Events.raise(NotificationRequestedEvent.builder().userId(parent.getId()).notificationType(NotificationType.CHILD_ENROLL_DENY).bodyValue(child.getNickname()).targetId(request.getId().toString()).timestamp(LocalDateTime.now()).build());
        }
    }

    @Transactional
    public void signupUser(@Valid UserSignupDto dto) {
        TemporaryUser temporaryUser = temporaryUserRepository.findByCode(dto.getCode()).orElseThrow(TemporaryUserNotFoundException::new);

        String email = temporaryUser.getEmail();
        String name = temporaryUser.getName();

        User user;

        if(dto.getType() == User.Type.PARENT)
            user = parentRepository.save(dto.toParent(email, name));
        else
            user = childRepository.save(dto.toChild(email, name));

        accountService.initBankMember(user.getId(), email);
        temporaryUserRepository.delete(temporaryUser);
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.CHILD)
    public ParentInfo getParentInfo(Integer id) {
        Child child = childRepository.findByIdUsingFetchParent(id).orElseThrow(UserNotFoundException::new);
        if(child.getParent() == null)
            throw new ParentNotFoundException();
        return ParentInfo.from(child.getParent());
    }

    @Transactional
    @Authorized(allowed = User.Type.PARENT)
    public void removeRegisterChildFromParent(ChildRegisterRequestRemoveFromParentDto dto){
        ChildRegisterRequest request = childRegisterRequestRepository.findById(dto.getRequestId()).orElseThrow(ChildRegisterRequestNotFoundException::new);
        if(!Objects.equals(dto.getId(), request.getParent().getId()))
            throw new ChildRegisterRequestMisMatchException();
        request.changeState(ChildRegisterRequest.State.CANCELED);
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.PARENT)
    public List<ChildRegisterRequestInfoAndChildInfo> getRegisterChildFromParentByParent(Integer id){
        List<ChildRegisterRequest> requests = ListUtils.removeIf(childRegisterRequestRepository
                .findByParentIdAndStateUsingFetchChild(id, ChildRegisterRequest.State.REQUESTED),
                request -> request.getChild().getIsDeleted());
        return ListUtils.applyFunctionToElements(requests, (element) -> ChildRegisterRequestInfoAndChildInfo.of(element, element.getChild()));
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.CHILD)
    public List<ChildRegisterRequestInfoAndParentInfo> getRegisterChildFromParentByChild(Integer id) {
        List<ChildRegisterRequest> requests = ListUtils.removeIf(childRegisterRequestRepository
                        .findByChildIdAndStateUsingFetchParent(id, ChildRegisterRequest.State.REQUESTED),
                request -> request.getParent().getIsDeleted());
        return ListUtils.applyFunctionToElements(requests, (element) -> ChildRegisterRequestInfoAndParentInfo.of(element, element.getParent()));
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.PARENT)
    public Integer getChildDepositRewardMoney(Integer id, String nickname) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent()==null)
            throw new ParentNotFoundException();

        if(!Objects.equals(child.getParent().getId(), id))
            throw new FamilyNotExistException();

        return child.getDepositRewardMoney();
    }

    @Transactional
    @Authorized(allowed = User.Type.PARENT)
    public void changeChildDepositRewardMoney(Integer id, String nickname, Integer depositRewardMoney) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent()==null)
            throw new ParentNotFoundException();

        if(!Objects.equals(child.getParent().getId(), id))
            throw new FamilyNotExistException();

        child.changeDepositRewardMoney(depositRewardMoney);
    }

    @Transactional
    public void deleteChildDepositRewardMoney(Integer id, String nickname) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent()==null)
            throw new ParentNotFoundException();

        if(!Objects.equals(child.getParent().getId(), id))
            throw new FamilyNotExistException();

        child.changeDepositRewardMoney(0);
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = User.Type.PARENT)
    public Integer getChildSavingRewardMoney(Integer id, String nickname) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent()==null)
            throw new ParentNotFoundException();

        if(!Objects.equals(child.getParent().getId(), id))
            throw new FamilyNotExistException();

        return child.getSavingRewardMoney();
    }

    @Transactional
    @Authorized(allowed = User.Type.PARENT)
    public void changeChildSavingRewardMoney(Integer id, String nickname, Integer savingRewardMoney) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent()==null)
            throw new ParentNotFoundException();

        if(!Objects.equals(child.getParent().getId(), id))
            throw new FamilyNotExistException();

        child.changeSavingRewardMoney(savingRewardMoney);
    }

    @Transactional
    public void deleteChildSavingRewardMoney(Integer id, String nickname) {
        Child child = childRepository.findByNicknameUsingFetchParent(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent()==null)
            throw new ParentNotFoundException();

        if(!Objects.equals(child.getParent().getId(), id))
            throw new FamilyNotExistException();

        child.changeSavingRewardMoney(0);
    }


    private void checkDeletedUser(User user){
        if(user.getIsDeleted())
            throw new UserWithdrawalException();
    }

    private void checkExistsParent(Child child){
        if(child.getParent() != null)
            throw new ChildAlreadyExistsParentException();
    }

    private void childDeletePreProcessing(User user){
        Child child = (Child) user;
    }

    private void parentDeletePreProcessing(User user){
        Parent parent = (Parent) user;
        childRepository.removeParent(parent);
    }

    @Transactional
    @Authorized(allowed = User.Type.CHILD)
    public String recommendStuff(Integer id) {
        Child child = childRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(child.getIsDeleted())
            throw new UserWithdrawalException();
        ProductRecommendDto dto = productAiChat.getProductFromChildInfo(child);
        return productAiDraw.createImageGetUrl(dto);
    }
}
