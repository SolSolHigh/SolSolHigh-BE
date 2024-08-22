package com.shinhan.solsolhigh.quiz.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectedQuizKeywordRepository extends JpaRepository<SelectedQuizKeyword, Integer> {

    List<SelectedQuizKeyword> findAllByChild_Id(Integer childId);
}
