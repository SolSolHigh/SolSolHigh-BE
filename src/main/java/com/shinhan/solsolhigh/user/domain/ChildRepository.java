package com.shinhan.solsolhigh.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Integer> {
    public Boolean existsByIdAndParentId(Integer id, Integer parentId);
}
