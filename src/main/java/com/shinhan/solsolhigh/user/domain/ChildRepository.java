package com.shinhan.solsolhigh.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Integer> {
    Optional<Child> findByNickname(String nickname);

    List<Child> findByParentId(Integer id);

    @Modifying
    @Query("""
           UPDATE Child c
           SET c.parent = null
           WHERE c.parent = :parent
           """)
    void removeParent(@Param("parent") Parent parent);

    Boolean existsByNicknameAndParentId(String nickname, Integer parentId);
    Boolean existsByIdAndParentId(Integer id, Integer parentId);
}
