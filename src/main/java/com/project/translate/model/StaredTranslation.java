package com.project.translate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stared_translations")
public class StaredTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "source_text", nullable = false)
    private String sourceText;

    @Column(name = "translated_text", nullable = false)
    private String translatedText;

    @Column(name = "source_lang", nullable = false)
    private String sourceLang;

    @Column(name = "target_lang", nullable = false)
    private String targetLang;

    @Column(name = "translated_at")
    @CreationTimestamp
    private LocalDateTime translatedAt;
}
