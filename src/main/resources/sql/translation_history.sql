CREATE TABLE translation_history(
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                user_id VARCHAR(10) NOT NULL,
                source_text TEXT NOT NULL,
                translated_text TEXT NOT NULL,
                source_lang VARCHAR(10) NOT NULL,
                target_lang VARCHAR(10) NOT NULL,
                translated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                starred BOOLEAN NOT NULL DEFAULT FALSE,
                starred_at TIMESTAMP,
                deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Core user history queries
CREATE INDEX idx_user_active_history ON translation_history(user_id, deleted, translated_at DESC);

-- For: all starred translations (ignore deleted status)
CREATE INDEX idx_user_starred_all ON translation_history(user_id, starred, translated_at DESC);

-- Counting user's translations
CREATE INDEX idx_user_deleted ON translation_history(user_id, deleted);

-- Language pair analysis (analytics)
CREATE INDEX idx_lang_pair ON translation_history(source_lang, target_lang);

-- Full-text search on translations
CREATE FULLTEXT INDEX idx_source_text ON translation_history(source_text);
CREATE FULLTEXT INDEX idx_translated_text ON translation_history(translated_text);

-- Date range queries (analytics)
CREATE INDEX idx_translated_at ON translation_history(translated_at DESC);