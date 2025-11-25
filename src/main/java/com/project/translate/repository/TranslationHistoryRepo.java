package com.project.translate.repository;

import com.project.translate.model.TranslationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TranslationHistoryRepo extends CrudRepository<TranslationHistory,Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE translation_history SET deleted = true WHERE id = :id AND user_id = :userId", nativeQuery = true)
    int softDeleteByIdAndUserId(@Param("id") Long id, @Param("userId") String userId);


    @Modifying
    @Transactional
    @Query("UPDATE TranslationHistory t SET t.deleted = true WHERE t.userId = :userId")
    int softDeleteHistoryByUserId(@Param("userId") String userId);


    @Query("SELECT t FROM TranslationHistory t WHERE t.userId= :userId AND t.deleted = false")
    List<TranslationHistory> getTranslationHistoriesByUserId(@Param("userId") String userId, Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE TranslationHistory t SET t.starred = true, t.starredAt = CURRENT_TIMESTAMP WHERE t.userId = :userId AND t.id = :translationId AND t.deleted = false")
    int starByUserIdAndTranslationId(@Param("userId") String userId, @Param("translationId")Long translationId);

    @Query("SELECT t FROM TranslationHistory t WHERE t.userId = :userId AND t.starred = true")
    Page<TranslationHistory> getStarredTranslationsByUserId(@Param("userId") String userId, Pageable pageable);
}
