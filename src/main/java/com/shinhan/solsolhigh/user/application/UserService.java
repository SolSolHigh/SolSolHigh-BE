package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.common.util.ListUtils;
import com.shinhan.solsolhigh.user.domain.*;
import com.shinhan.solsolhigh.user.exception.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ChildRepository childRepository;

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
    @Authorized(allowed = Parent.class)
    public ChildInfo getChildInfoByNickname(String nickname) {
        Child child = childRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        return ChildInfo.from(child);
    }

    @Transactional(readOnly = true)
    @Authorized(allowed = Parent.class)
    public List<ChildInfo> getSessionChildrenInfo(Integer id) {
        List<Child> children = ListUtils.removeIf(childRepository.findByParentId(id), User::getIsDeleted);
        return ListUtils.applyFunctionToElements(children, ChildInfo::from);
    }

    @Transactional
    @Authorized(allowed = Parent.class)
    public void removeChildFromParent(ChildRemoveFromParentDto dto) {
        Child child = childRepository.findByNickname(dto.getNickname()).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        if(child.getParent() == null || !Objects.equals(child.getParent().getId(), dto.getId()))
            throw new ChildUnregisteredException();
        child.changeParent(null);
    }


    @Transactional
    @Authorized(allowed = Parent.class)
    public void requestRegisterChildFromParent(ChildRegisterRequestFromParentDto dto) {
        Child child = childRepository.findByNickname(dto.getNickname()).orElseThrow(UserNotFoundException::new);
        checkDeletedUser(child);
        checkExistsParent(child);
        //TODO. FCM 등록 로직
    }

    private void checkDeletedUser(User user){
        if(user.getIsDeleted())
            throw new UserWithdrawalException();
    }

    private void checkExistsParent(Child child){
        if(child.getParent() != null)
            throw new ChildAlreadyExistsParentException();
    }
}
