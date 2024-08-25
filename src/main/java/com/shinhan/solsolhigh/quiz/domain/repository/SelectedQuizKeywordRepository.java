package com.shinhan.solsolhigh.quiz.domain.repository;

import com.shinhan.solsolhigh.quiz.domain.SelectedQuizKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SelectedQuizKeywordRepository extends JpaRepository<SelectedQuizKeyword, Integer> {

    List<SelectedQuizKeyword> findAllByChild_Id(Integer childId);

    Boolean existsByQuizKeyword_IdAndChild_Id(Integer quizKeywordId, Integer childId);

    Integer countAllByChild_Id(Integer childId);

    @Modifying
    void deleteByChild_IdAndQuizKeyword_Id(Integer childId, Integer quizKeywordId);

    @Query("SELECT sqk FROM SelectedQuizKeyword sqk JOIN FETCH sqk.quizKeyword ")
    List<SelectedQuizKeyword> findAllByChild_idWithFetch(Integer childId);
}
