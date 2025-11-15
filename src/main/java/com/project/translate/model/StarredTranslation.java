package com.project.translate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "starred_translation")
public class StarredTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "translation_id", nullable = false)
    private Long translationId;

    @Column(name = "starred_at")
    @CreationTimestamp
    private LocalDateTime starredAt;
}
