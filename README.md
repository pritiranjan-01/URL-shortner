# URL Shortener 🔗

A modern, web-based URL shortening service built with Spring Boot. Transform long URLs into short, shareable links with a clean and intuitive user interface.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

- **URL Shortening**: Convert long URLs into short, 6-character alphanumeric codes
- **Web Interface**: Beautiful, responsive web UI built with Thymeleaf templates
- **URL Redirection**: Automatic redirection to original URLs using short codes
- **Duplicate Detection**: Prevents duplicate entries for the same URL
- **Metadata Tracking**: Stores creation date and optional expiration date
- **Error Handling**: Comprehensive validation and error messages
- **Responsive Design**: Mobile-friendly interface with modern CSS styling
- **Copy to Clipboard**: One-click copy functionality for shortened URLs

## 🛠 Technology Stack

- **Backend Framework**: Spring Boot 3.5.9
- **Java Version**: 17
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA / Hibernate
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Frontend**: HTML5, CSS3, JavaScript
- **Validation**: Jakarta Validation API
- **Utilities**: Lombok

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 17 or higher
- **Maven**: Version 3.6+ (for building the project)
- **MySQL**: Version 8.0+ (for database)
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (optional but recommended)

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd UrlShortner
```

### 2. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE urlshortner;
```

### 3. Configure Database Connection

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/urlshortner
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build the Project

```bash
mvn clean install
```

## ⚙️ Configuration

The application configuration is stored in `src/main/resources/application.properties`:

```properties
# Application Name
spring.application.name=UrlShortner

# Static Resources
spring.web.resources.static-locations=classpath:/static/
spring.web.resources.add-mappings=true

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/urlshortner
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
```

### Configuration Options

- `spring.jpa.hibernate.ddl-auto=update`: Automatically creates/updates database tables
- `spring.jpa.show-sql=true`: Shows SQL queries in console (set to `false` in production)
- `spring.jpa.properties.hibernate.format_sql=true`: Formats SQL output for readability

## 🏃 Running the Application

### Option 1: Using Maven

```bash
mvn spring-boot:run
```

### Option 2: Using IDE

1. Open the project in your IDE
2. Navigate to `UrlShortnerApplication.java`
3. Run the `main` method

### Option 3: Using JAR

```bash
mvn clean package
java -jar target/UrlShortner-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## 📖 Usage

### Web Interface

1. **Access the Application**: Open your browser and navigate to `http://localhost:8080`

2. **Shorten a URL**:
   - Enter a long URL in the input field (e.g., `https://www.example.com/very/long/url/path`)
   - Click "Shorten URL"
   - Your shortened URL will be displayed along with metadata

3. **Copy Short URL**: Click the "📋 Copy" button to copy the shortened URL to your clipboard

4. **Use Short URL**: Access the shortened URL (e.g., `http://localhost:8080/abc123`) to redirect to the original URL

### Example

**Input URL:**
```
https://www.example.com/very/long/url/path/with/many/segments
```

**Generated Short URL:**
```
http://localhost:8080/aBc123
```

## 📁 Project Structure

```
UrlShortner/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/url/shortner/
│   │   │       ├── controller/
│   │   │       │   └── HomeController.java      # MVC Controller
│   │   │       ├── dto/
│   │   │       │   ├── ShortenUrlRequestDTO.java
│   │   │       │   └── ShortenUrlResponseDTO.java
│   │   │       ├── entity/
│   │   │       │   └── UrlShortner.java         # JPA Entity
│   │   │       ├── repository/
│   │   │       │   └── UrlShortnerRepository.java
│   │   │       ├── service/
│   │   │       │   └── UrlShortnerService.java  # Business Logic
│   │   │       ├── util/
│   │   │       │   └── ShortCodeGenerator.java # Short Code Generator
│   │   │       └── UrlShortnerApplication.java  # Main Application
│   │   └── resources/
│   │       ├── static/
│   │       │   └── css/
│   │       │       └── style.css                # CSS Styles
│   │       ├── templates/
│   │       │   ├── index.html                   # Form Template
│   │       │   └── result.html                  # Result Template
│   │       └── application.properties           # Configuration
│   └── test/
├── pom.xml                                       # Maven Dependencies
└── README.md                                     # Project Documentation
```

## 🔌 API Endpoints

### Web Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/` | Display URL shortening form |
| `POST` | `/shorten` | Process form submission and create short URL |
| `GET` | `/{shortCode}` | Redirect to original URL (6-character alphanumeric code) |

### Endpoint Details

#### GET `/`
- **Description**: Displays the main form page
- **Response**: HTML page with URL input form

#### POST `/shorten`
- **Description**: Creates a shortened URL
- **Parameters**: 
  - `url` (form parameter): The original URL to shorten
- **Response**: HTML page with shortened URL details
- **Validation**: 
  - URL must not be empty
  - URL must be in valid format (http:// or https://)

#### GET `/{shortCode}`
- **Description**: Redirects to the original URL
- **Path Variable**: 
  - `shortCode`: 6-character alphanumeric code
- **Response**: 
  - `302 Found`: Redirects to original URL
  - `404 Not Found`: If short code doesn't exist

## 🗄️ Database Schema

### `url_shortner` Table

| Column | Type | Constraints | Description |
|--------|------|------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique identifier |
| `short_code` | VARCHAR(32) | UNIQUE, NOT NULL | 6-character short code |
| `original_url` | VARCHAR(2048) | NOT NULL | Original long URL |
| `created_at` | DATETIME | NOT NULL | Creation timestamp |
| `expire_at` | DATETIME | NULL | Expiration timestamp (optional) |

### Entity Relationship

- One `UrlShortner` entity represents one URL mapping
- `short_code` is unique and indexed for fast lookups
- Automatic timestamp generation on creation

## 🔧 Key Components

### ShortCodeGenerator
- Generates random 6-character alphanumeric codes
- Uses BASE62 encoding (0-9, A-Z, a-z)
- Ensures uniqueness through collision detection

### UrlShortnerService
- Business logic for URL shortening
- Handles duplicate URL detection
- Manages short code generation and validation

### HomeController
- MVC controller handling web requests
- Form validation and error handling
- Model attribute management for Thymeleaf templates

## 🎨 Frontend

- **Templates**: Thymeleaf-based HTML templates
- **Styling**: Modern CSS with gradient backgrounds and animations
- **Responsive**: Mobile-friendly design
- **JavaScript**: Copy-to-clipboard functionality

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database `urlshortner` exists

2. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`
   - Or stop the process using port 8080

3. **Template Not Found**
   - Ensure Thymeleaf dependency is in `pom.xml`
   - Verify templates are in `src/main/resources/templates/`

4. **Short Code Collision**
   - The application handles collisions automatically
   - If persistent, check `ShortCodeGenerator` implementation

## 📝 Development Notes

- The application uses Spring Boot's auto-configuration
- Database tables are auto-created via Hibernate (`ddl-auto=update`)
- Short codes are 6 characters long (can be modified in `ShortCodeGenerator`)
- URLs are validated before shortening
- Duplicate URLs return existing short codes

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👤 Author

**Your Name**
- GitHub: [@pritiranjan-01](https://github.com/pritiranjan-01)

## 🙏 Acknowledgments

- Spring Boot community
- Thymeleaf documentation
- MySQL documentation

---

**Note**: Remember to update database credentials and other sensitive information before deploying to production!

