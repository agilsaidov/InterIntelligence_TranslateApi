package com.project.translate.repository;

import com.project.translate.model.TranslationHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationHistoryRepo extends CrudRepository<TranslationHistory,Long> {
}
