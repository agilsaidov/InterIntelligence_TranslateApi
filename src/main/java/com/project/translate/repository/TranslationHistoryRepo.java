package com.project.translate.repository;

import com.project.translate.model.TranslationHistory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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


}
