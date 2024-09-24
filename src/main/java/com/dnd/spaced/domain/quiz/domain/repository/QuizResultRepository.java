package com.dnd.spaced.domain.quiz.domain.repository;

import com.dnd.spaced.domain.quiz.domain.QuizResult;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultRepository extends CrudRepository<QuizResult, Long> {
    @Query("SELECT COUNT(DISTINCT qr.accountId) FROM QuizResult qr WHERE DATE(qr.createdAt) = CURRENT_DATE")
    long countParticipantsToday();
}
