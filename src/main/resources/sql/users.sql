CREATE TABLE users(
              id BIGINT PRIMARY KEY AUTO_INCREMENT,
              public_id VARCHAR(10) UNIQUE NOT NULL,
              email VARCHAR(255) UNIQUE NOT NULL,
              username VARCHAR(255) UNIQUE NOT NULL,
              password VARCHAR(300) NOT NULL,

              name VARCHAR(150),
              surname VARCHAR(150),

              native_language VARCHAR(10),
              preferred_language VARCHAR(10),

              role VARCHAR(50) NOT NULL DEFAULT 'USER',
              account_status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',

              last_login TIMESTAMP,
              login_count INT NOT NULL DEFAULT 0,

              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

              profile_picture_url VARCHAR(500),

              deleted BOOLEAN NOT NULL DEFAULT FALSE,
              deleted_at TIMESTAMP NULL
);

-- Indexes for users
CREATE UNIQUE INDEX idx_public_id ON users(public_id);  -- Already implied by UNIQUE, but explicit is good
CREATE UNIQUE INDEX idx_email ON users(email);
CREATE UNIQUE INDEX idx_username ON users(username);

-- Composite index for active user lookups (very common query)
CREATE INDEX idx_active_users ON users(deleted, account_status, last_login DESC);

-- Index for role-based queries (admin dashboard, user management)
CREATE INDEX idx_role_status ON users(role, account_status);

-- Index for finding recently created users
CREATE INDEX idx_created_at ON users(created_at DESC);