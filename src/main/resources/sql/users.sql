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

          profile_picture_url VARCHAR(500)
);