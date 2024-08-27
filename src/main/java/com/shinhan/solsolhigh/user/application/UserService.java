package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.alarm.domain.ChildRegisterAlarm;
import com.shinhan.solsolhigh.alarm.domain.ChildRegisterAlarmRepository;
import com.shinhan.solsolhigh.alarm.exception.AlarmMisMatchException;
import com.shinhan.solsolhigh.alarm.exception.AlarmNotFoundException;
import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.common.util.JPAUtils;
import com.shinhan.solsolhigh.common.util.ListUtils;
import com.shinhan.solsolhigh.user.domain.*;
import com.shinhan.solsolhigh.user.exception.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final HttpSession session;
    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final ChildRegisterAlarmRepository childRegisterAlarmRepository;
    private final TemporaryUserRepository temporaryUserRepository;
    private final ParentRepository parentRepository;

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
        //TODO. 알림 서비스 호출
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
        Child child = childRepository.findById(dto.getId()).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        checkExistsParent(child);

        ChildRegisterAlarm alarm = childRegisterAlarmRepository.findById(dto.getAlarmId()).orElseThrow(AlarmNotFoundException::new);

        if(!alarm.getReceiver().equals(child))
            throw new AlarmMisMatchException();

        User user = alarm.getSender();
        if(user.getType() != User.Type.PARENT)
            throw new UserTypeMismatchException();

        Parent parent = JPAUtils.typeCast(Parent.class, user);
        checkDeletedUser(parent);

        if(dto.getIsAccept()){
            alarm.changeState(ChildRegisterAlarm.State.ACCEPTED);
            child.changeParent(parent);
            //TODO. 수락 알림 서비스 호출
        } else {
            alarm.changeState(ChildRegisterAlarm.State.REJECTED);
            //TODO. 거절 알림 서비스 호출
        }
    }

    @Transactional
    public void signupUser(@Valid UserSignupDto dto) {
        TemporaryUser temporaryUser = temporaryUserRepository.findByCode(dto.getCode()).orElseThrow(TemporaryUserNotFoundException::new);

        String email = temporaryUser.getEmail();
        String name = temporaryUser.getName();

        if(dto.getType() == User.Type.PARENT)
            parentRepository.save(dto.toParent(email, name));
        else
            childRepository.save(dto.toChild(email, name));

        temporaryUserRepository.delete(temporaryUser);
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
}
