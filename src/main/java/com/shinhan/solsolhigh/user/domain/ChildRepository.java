package com.shinhan.solsolhigh.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Integer> {
    Optional<Child> findByNickname(String nickname);

    List<Child> findByParentId(Integer id);
}
