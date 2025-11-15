package com.project.translate.repository;

import com.project.translate.model.StarredTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaredTranslationRepo extends JpaRepository<StarredTranslation, Long> {}
