# Translation API

A RESTful API built with Spring Boot that provides translation services powered by DeepL API, featuring comprehensive user authentication, translation history management, and starred translations functionality.

## 🚀 Features

- 🌐 **Text Translation** - Translate text between multiple languages using DeepL API
- 🔐 **JWT Authentication** - Secure user authentication with token-based authorization
- 📝 **Translation History** - Track and manage all translation records with pagination
- ⭐ **Starred Translations** - Save and organize favorite translations
- 👤 **User Profile Management** - Complete user account management with profile updates
- 🔒 **Password Management** - Secure password change functionality
- 🗑️ **Soft Delete** - User and translation soft deletion for data integrity
- ⚡ **Redis Integration** - Token blacklisting and caching with Redis
- 🔍 **Pagination Support** - Efficient data retrieval with page-based results

## 🛠️ Tech Stack

### Backend
- **Framework:** Spring Boot 3.x
- **Language:** Java 17+
- **Security:** Spring Security with JWT (JJWT)
- **Database:** MySQL
- **ORM:** Spring Data JPA / Hibernate
- **Cache:** Redis
- **Translation API:** DeepL API
- **Build Tool:** Maven
- **Validation:** Jakarta Bean Validation

### Key Dependencies
- Lombok - Reduce boilerplate code
- Jackson - JSON processing
- BCrypt - Password encryption

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Redis** (for token blacklisting)
- **DeepL API Key** (get from [DeepL](https://www.deepl.com/pro-api))
- **Git**

## 🔧 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/agilsaidov/InterIntelligence_TranslateApi.git
cd InterIntelligence_TranslateApi
git checkout backend
```

### 2. Configure MySQL Database

Create a MySQL database:

```sql
CREATE DATABASE translate_db;
USE translate_db;
```

Run the SQL schema files:

```bash
# Execute the schema files in order
mysql -u your_username -p translate_db < users.sql
mysql -u your_username -p translate_db < translation_history.sql
```

### 3. Configure Application Properties

Create or update `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/translate_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# JWT Configuration
JWT_SECRET=your_jwt_secret_key_minimum_256_bits

# DeepL API Configuration
deepl.api.key=your_deepl_api_key

# Redis Configuration
spring.redis.host=localhost
spring.redis.port=6379

# CORS Configuration
cors.allowed.origins=http://localhost:3000

# Server Configuration
server.port=8080
```

### 4. Install Redis

**macOS:**
```bash
brew install redis
brew services start redis
```

**Ubuntu/Debian:**
```bash
sudo apt-get install redis-server
sudo systemctl start redis
```

**Windows:**
Download from [Redis Windows](https://github.com/microsoftarchive/redis/releases)

### 5. Build the Project

```bash
mvn clean install
```

### 6. Run the Application

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Authentication Endpoints

#### Register User
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "Password123",
  "username": "username123"
}
```

**Validation Rules:**
- Email: Valid email format
- Password: 8-50 characters, must contain uppercase, lowercase, and digit
- Username: 6-30 characters

**Response (201 Created):**
```json
{
  "email": "user@example.com",
  "message": "Registered Successfully"
}
```

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "Password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "user@example.com",
  "username": "username123",
  "publicId": "USR1234567",
  "profilePicture": null
}
```

#### Logout
```http
POST /api/v1/auth/logout
Authorization: Bearer {token}
```

**Response (200 OK)**

---

### Translation Endpoints

#### Translate Text
```http
POST /api/v1/translate
Authorization: Bearer {token}
Content-Type: application/json

{
  "text": "Hello, world!",
  "sourceLang": "EN",
  "targetLang": "TR",
  "userId": "USR1234567"
}
```

**Supported Languages:** EN, DE, FR, ES, IT, PT, RU, ZH, JA, TR, and more (check DeepL documentation)

**Response (200 OK):**
```json
{
  "id": 1,
  "translatedText": "Merhaba dünya!",
  "detectedSourceLanguage": "EN"
}
```

---

### Translation History Endpoints

#### Get Translation History
```http
GET /api/v1/history?page=0
Authorization: Bearer {token}
```

**Query Parameters:**
- `page` (optional): Page number, default is 0 (10 items per page)

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "sourceText": "Hello, world!",
      "translatedText": "Merhaba dünya!",
      "sourceLang": "EN",
      "targetLang": "TR",
      "translatedAt": "2024-12-04T10:30:00",
      "starred": false
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalPages": 5,
  "totalElements": 42
}
```

#### Delete Translation
```http
DELETE /api/v1/history?translationId=1
Authorization: Bearer {token}
```

**Response (204 No Content)**

#### Clear All History
```http
DELETE /api/v1/history/clear
Authorization: Bearer {token}
```

**Response (204 No Content)**

---

### Starred Translations Endpoints

#### Star a Translation
```http
POST /api/v1/starred?translationId=1
Authorization: Bearer {token}
```

**Response (200 OK)**

#### Get Starred Translations
```http
GET /api/v1/starred?page=0
Authorization: Bearer {token}
```

**Query Parameters:**
- `page` (optional): Page number, default is 0 (10 items per page)

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "sourceText": "Hello, world!",
      "translatedText": "Salam dünya!",
      "sourceLang": "EN",
      "targetLang": "AZ",
      "translatedAt": "2024-12-04T10:30:00",
      "starred": true,
      "starredAt": "2024-12-04T11:00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalPages": 2,
  "totalElements": 15
}
```

#### Unstar a Translation
```http
DELETE /api/v1/starred?translationId=1
Authorization: Bearer {token}
```

**Response (204 No Content)**

#### Clear All Starred Translations
```http
DELETE /api/v1/starred/clear
Authorization: Bearer {token}
```

**Response (204 No Content)**

---

### User Profile Endpoints

#### Get User Profile
```http
GET /api/v1/user/profile
Authorization: Bearer {token}
```

**Response (200 OK):**
```json
{
  "userId": "USR1234567",
  "email": "user@example.com",
  "username": "username123",
  "name": "John",
  "surname": "Doe",
  "nativeLang": "EN",
  "preferredLang": "AZ",
  "role": "USER",
  "lastLogin": "2024-12-04T10:30:00",
  "createdAt": "2024-01-15T08:00:00",
  "updatedAt": "2024-12-04T10:30:00",
  "profilePicture": null
}
```

#### Update User Profile
```http
POST /api/v1/user/profile
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "nativeLang": "EN",
  "preferredLang": "AZ"
}
```

**Response (200 OK):** Updated user profile object

#### Change Password
```http
POST /api/v1/user/changepassword
Authorization: Bearer {token}
Content-Type: application/json

{
  "oldPassword": "OldPassword123",
  "newPassword": "NewPassword456"
}
```

**Response (200 OK)**

#### Delete Account (Soft Delete)
```http
DELETE /api/v1/user/profile
Authorization: Bearer {token}
```

**Response (200 OK)**

---

## 🗂️ Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── project/
│   │           └── translate/
│   │               ├── auth/
│   │               │   ├── controller/
│   │               │   │   └── AuthController.java
│   │               │   ├── dto/
│   │               │   │   ├── request/
│   │               │   │   │   ├── LoginRequest.java
│   │               │   │   │   └── RegistrationRequest.java
│   │               │   │   └── response/
│   │               │   │       ├── LoginResponse.java
│   │               │   │       └── RegistrationResponse.java
│   │               │   └── service/
│   │               │       └── AuthService.java
│   │               ├── controller/
│   │               │   ├── StarredTranslationController.java
│   │               │   ├── TranslateController.java
│   │               │   ├── TranslationHistoryController.java
│   │               │   └── UserController.java
│   │               ├── dto/
│   │               │   ├── request/
│   │               │   │   ├── ChangePasswordRequest.java
│   │               │   │   ├── TranslationRequestDto.java
│   │               │   │   └── UserDataUpdateRequest.java
│   │               │   └── response/
│   │               │       ├── ExceptionResponse.java
│   │               │       ├── TranslationHistoryResponse.java
│   │               │       ├── TranslationResponse.java
│   │               │       └── UserResponse.java
│   │               ├── exception/
│   │               │   ├── AuthException.java
│   │               │   ├── DeletedUserException.java
│   │               │   ├── InvalidPasswordException.java
│   │               │   ├── NotFoundException.java
│   │               │   ├── StarredTranslationProcessException.java
│   │               │   └── TranslationException.java
│   │               ├── model/
│   │               │   ├── AccountStatus.java
│   │               │   ├── AppUser.java
│   │               │   └── TranslationHistory.java
│   │               ├── repository/
│   │               │   ├── AppUserRepo.java
│   │               │   └── TranslationHistoryRepo.java
│   │               ├── security/
│   │               │   ├── AppUserDetailsService.java
│   │               │   ├── JwtService.java
│   │               │   ├── JwtValidationFilter.java
│   │               │   └── SecurityConfig.java
│   │               ├── service/
│   │               │   ├── AppUserService.java
│   │               │   ├── TranslateService.java
│   │               │   └── TranslationHistoryService.java
│   │               ├── utils/
│   │               │   ├── TranslationHistoryMapper.java
│   │               │   ├── UserIdGenerator.java
│   │               │   └── UserMapper.java
│   │               └── TranslateApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/
    └── java/
        └── com/
            └── project/
                └── translate/
```

## 🔒 Security Features

### JWT Token Authentication
- Token expiration: 1 hour
- Token stored in Authorization header as Bearer token
- Automatic token validation on each request
- Public endpoints: `/api/v1/auth/register`, `/api/v1/auth/login`, `/api/v1/translate`

### Token Blacklisting (Redis)
- Logout invalidates tokens immediately
- Blacklisted tokens stored with 60-minute expiration
- Prevents reuse of logged-out tokens

### User Blacklisting
- Deleted users cannot access the API
- User deletion triggers automatic token invalidation
- Soft delete preserves data integrity

### Password Security
- BCrypt password encoding
- Strong password requirements enforced
- Secure password change with old password verification

### CORS Configuration
- Configured for `http://localhost:3000` (React frontend)
- Customizable allowed origins
- Credentials support enabled

## 📊 Database Schema

### Users Table
```sql
- id (BIGINT, Primary Key, Auto Increment)
- public_id (VARCHAR(10), Unique)
- email (VARCHAR(255), Unique)
- username (VARCHAR(255), Unique)
- password (VARCHAR(300))
- name (VARCHAR(150))
- surname (VARCHAR(150))
- native_language (VARCHAR(10))
- preferred_language (VARCHAR(10))
- role (VARCHAR(50), Default: 'USER')
- account_status (VARCHAR(50), Default: 'ACTIVE')
- last_login (TIMESTAMP)
- login_count (INT, Default: 0)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)
- profile_picture_url (VARCHAR(500))
- deleted (BOOLEAN, Default: FALSE)
- deleted_at (TIMESTAMP)
```

### Translation History Table
```sql
- id (BIGINT, Primary Key, Auto Increment)
- user_id (VARCHAR(10))
- source_text (TEXT)
- translated_text (TEXT)
- source_lang (VARCHAR(10))
- target_lang (VARCHAR(10))
- translated_at (TIMESTAMP)
- starred (BOOLEAN, Default: FALSE)
- starred_at (TIMESTAMP)
- deleted (BOOLEAN, Default: FALSE)
```

### Optimized Indexes
- User lookups: `idx_public_id`, `idx_email`, `idx_username`
- Active users: `idx_active_users` (deleted, account_status, last_login)
- Translation history: `idx_user_active_history` (user_id, deleted, translated_at)
- Starred translations: `idx_user_starred_all` (user_id, starred, translated_at)
- Full-text search: `idx_source_text`, `idx_translated_text`

## ⚠️ Error Handling

The API returns standardized error responses:

```json
{
  "status": 400,
  "code": "ERROR_CODE",
  "message": "Human-readable error message",
  "timestamp": "2024-12-04T10:30:00"
}
```

### Common Error Codes

| Status | Code | Description |
|--------|------|-------------|
| 400 | INVALID_TOKEN | Token format is invalid |
| 401 | INVALID_CREDENTIALS | Wrong email or password |
| 401 | TOKEN_EXPIRED | JWT token has expired |
| 401 | BLACKLISTED_TOKEN | Token has been blacklisted |
| 404 | USER_NOT_FOUND | User does not exist |
| 404 | TRANSLATION_NOT_FOUND | Translation not found |
| 409 | EMAIL_ALREADY_EXISTS | Email is already registered |
| 409 | USERNAME_ALREADY_EXISTS | Username is already taken |
| 500 | AUTHENTICATION_ERROR | Internal authentication error |
| 500 | TRANSLATION_FAILED | DeepL API error |


## 🔍 Monitoring & Logging

### Application Logs
Logs are generated for:
- User registration and login
- Translation operations
- Starred/unstarred translations
- Password changes
- User deletion
- Token blacklisting

### Log Levels
- **INFO**: Normal operations
- **WARN**: Invalid operations, not found errors
- **ERROR**: Translation failures, exceptions

### Example Log Output
```
2024-12-04 10:30:00 INFO  AuthService - User registered successfully with email: user@example.com
2024-12-04 10:31:15 INFO  AuthService - Login Successfully with email: user@example.com
2024-12-04 10:32:00 INFO  TranslateController - Translating text from EN to AZ
2024-12-04 10:32:30 INFO  TranslationHistoryService - Translation 1 has been starred for user USR1234567
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style Guidelines
- Follow Java naming conventions
- Use Lombok annotations for boilerplate reduction
- Write comprehensive JavaDoc for public methods
- Maintain service layer transaction boundaries
- Keep controllers thin, business logic in services

## 📄 License

This project is licensed under the MIT License.

## 📧 Contact

**Project Repository:** [https://github.com/agilsaidov/InterIntelligence_TranslateApi](https://github.com/agilsaidov/InterIntelligence_TranslateApi)

## 🙏 Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot) - Application framework
- [DeepL API](https://www.deepl.com/pro-api) - Translation service
- [JJWT](https://github.com/jwtk/jjwt) - JWT implementation
- [Redis](https://redis.io/) - Caching and token management
- [MySQL](https://www.mysql.com/) - Database
- [Lombok](https://projectlombok.org/) - Code generation

## 📌 Additional Notes

### API Versioning
The API uses `/api/v1/` prefix for versioning. Future versions can be added as `/api/v2/` without breaking existing clients.

### Future Enhancements
- [ ] Add support for document translation
- [ ] Implement refresh tokens
- [ ] Add rate limiting per user
- [ ] Add more logs
- [ ] Multi-language support for error messages
