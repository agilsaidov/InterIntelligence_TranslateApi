package com.project.translate.repository;

import com.project.translate.dto.response.StarredTranslationResponse;
import com.project.translate.model.StarredTranslation;
import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StarredTranslationRepo extends JpaRepository<StarredTranslation, Long> {

    @Modifying
    @Transactional
    long deleteByUserIdAndTranslationId(String userId, Long translationId);

    @Query("""
    SELECT NEW com.project.translate.dto.response.StarredTranslationResponse(
        h.id,
        h.sourceText,
        h.translatedText,
        h.sourceLang,
        h.targetLang,
        st.starredAt
    )
    FROM TranslationHistory h
    JOIN StarredTranslation st
      ON h.id = st.translationId
    WHERE st.userId = :userId
      AND h.deleted = false
    ORDER BY st.starredAt DESC
""")
    List<StarredTranslationResponse> getStarredTranslationsByUserId(@Param("userId") String userId);

}
