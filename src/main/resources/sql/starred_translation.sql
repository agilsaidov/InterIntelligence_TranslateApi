CREATE TABLE starred_translation(
            id BIGINT PRIMARY KEY AUTO_INCREMENT,
            user_id VARCHAR(10) NOT NULL,
            translation_id BIGINT NOT NULL,
            starred_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

            -- Foreign keys
            CONSTRAINT fk_starred_user
                FOREIGN KEY (user_id)
                REFERENCES users(public_id)
                ON DELETE CASCADE,

            CONSTRAINT fk_starred_translation
                FOREIGN KEY (translation_id)
                REFERENCES translation_history(id)
                ON DELETE CASCADE,

            -- Prevent duplicate stars (this is also an index!)
            CONSTRAINT uk_user_translation UNIQUE (user_id, translation_id)

);

-- Index 1: Get user's starred translations (MOST COMMON QUERY)
-- Covers: SELECT * FROM starred_translation WHERE user_id = ? ORDER BY starred_at DESC
CREATE INDEX idx_user_starred ON starred_translation(user_id, starred_at DESC);

-- Index 2: Find who starred a specific translation
-- Covers: SELECT * FROM starred_translation WHERE translation_id = ?
CREATE INDEX idx_translation_stars ON starred_translation(translation_id);