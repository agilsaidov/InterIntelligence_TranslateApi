CREATE TABLE translation_history(
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                user_id VARCHAR(10) NOT NULL,
                source_text TEXT NOT NULL,
                translated_text TEXT NOT NULL,
                source_lang VARCHAR(10) NOT NULL,
                target_lang VARCHAR(10) NOT NULL,
                translated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_user_active_history ON translation_history(user_id, deleted, translated_at DESC);

-- For counting user's translations
CREATE INDEX idx_user_deleted ON translation_history(user_id, deleted);

-- For language pair analysis (analytics)
CREATE INDEX idx_lang_pair ON translation_history(source_lang, target_lang);

-- For full-text search on translations
CREATE FULLTEXT INDEX idx_source_text ON translation_history(source_text);
CREATE FULLTEXT INDEX idx_translated_text ON translation_history(translated_text);

-- For finding translations by date range (analytics)
CREATE INDEX idx_translated_at ON translation_history(translated_at DESC);

-- Composite for checking if translation exists before starring
CREATE INDEX idx_user_translation_lookup ON translation_history(id, user_id);