package com.shinhan.solsolhigh.fcm.application;

import com.shinhan.solsolhigh.fcm.application.dto.FcmTokenRegisterRequest;
import com.shinhan.solsolhigh.fcm.domain.Fcm;
import com.shinhan.solsolhigh.fcm.domain.FcmRepository;
import com.shinhan.solsolhigh.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FcmRegisterService {
    private final FcmRepository fcmRepository;
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;

    @Transactional
    public void registerFcmToken(FcmTokenRegisterRequest fcmTokenRegisterRequest, UserPrinciple userPrinciple) {
        if(fcmRepository.existsByUser_IdAndToken(userPrinciple.getId(), fcmTokenRegisterRequest.getFcmToken())) {
            return;
        }
        User user;
        if(User.Type.CHILD.equals(userPrinciple.getType())) {
            user = childRepository.getReferenceById(userPrinciple.getId());
        }else {
            user = parentRepository.getReferenceById(userPrinciple.getId());
        }

        Fcm build = Fcm.builder().token(fcmTokenRegisterRequest.getFcmToken()).user(user).build();
        fcmRepository.save(build);
    }
}
