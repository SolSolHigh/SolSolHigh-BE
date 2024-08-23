package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.user.domain.*;
import com.shinhan.solsolhigh.user.exception.UserNicknameDuplicatedException;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import com.shinhan.solsolhigh.user.exception.UserWithdrawalException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(user.getIsDeleted())
            throw new UserWithdrawalException();
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
    public ChildInfo getChlidInfoByNickname(String nickname) {
        Child child = childRepository.findByNickname(nickname).orElseThrow(UserNotFoundException::new);
        return ChildInfo.from(child);
    }
}
