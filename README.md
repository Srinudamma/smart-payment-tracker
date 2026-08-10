# 💰 Smart Expense & Payment Tracker

A full-stack personal finance web application built with **Java and Spring Boot** that helps users manage daily expenses, monitor spending limits, analyze spending patterns, and generate financial reports.

## 🚀 Live Demo

🌐 **Live Application:**  
https://smart-payment-and-expense-tracker.onrender.com/

💻 **GitHub Repository:**  
https://github.com/Srinudamma/smart-payment-tracker

> The application is deployed on Render and uses PostgreSQL for persistent data storage.

---

## 📌 Overview

Managing personal expenses manually can make it difficult to understand spending habits and stay within a budget.

The **Smart Expense & Payment Tracker** provides a centralized platform for managing personal financial activity.

Users can:

- Record and manage daily expenses
- Categorize expenses
- Set and monitor spending limits
- Analyze spending patterns
- Generate PDF reports
- Send financial reports through email
- Secure user accounts
- Store financial data using PostgreSQL

---

## ✨ Features

### 👤 User Management

- User registration and login
- Secure authentication
- Protected application resources
- Spring Security integration

### 💸 Expense Management

- Add new expenses
- View expense history
- Update expense information
- Delete expenses
- Categorize expenses
- Track spending over time

### 🎯 Spending Limits

- Configure spending limits
- Monitor expenses against defined limits
- Track budget utilization

### 📊 Expense Analytics

- Analyze spending patterns
- View expense summaries
- Monitor spending across categories

### 📄 PDF Reports

- Generate expense reports in PDF format
- Download generated reports
- Maintain financial records

### 📧 Email Reports

- Send financial reports through email
- SMTP-based email integration
- Automated email reporting functionality

---

## 🛠️ Technology Stack

### Backend

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- Spring Validation
- Spring Mail

### Frontend

- Thymeleaf
- HTML
- CSS
- JavaScript

### Database

- PostgreSQL

### Build & Development

- Maven
- Git
- GitHub

### Deployment

- Docker
- Render

### Reporting

- OpenPDF
- iText

---

## 🏗️ Application Architecture

```text
                    ┌─────────────────────┐
                    │       Browser       │
                    │  HTML / CSS / JS    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     Thymeleaf       │
                    │   Presentation      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Controllers      │
                    │   Request Handling  │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │      Services       │
                    │   Business Logic    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Repositories     │
                    │   Spring Data JPA   │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │     PostgreSQL      │
                    │      Database       │
                    └─────────────────────┘

📂 Project Structure
smart-payment-tracker/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/SmartPaymentTracker/
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       ├── static/
│   │       └── application.properties
│   │
│   └── test/
│
├── .mvn/
├── Dockerfile
├── Procfile
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md

⚙️ Configuration

The application uses environment variables for sensitive configuration.

Database
DB_URL
DB_USERNAME
DB_PASSWORD
Email
SPRING_MAIL_HOST
SPRING_MAIL_PORT
SPRING_MAIL_USERNAME
SPRING_MAIL_PASSWORD
Example PostgreSQL Configuration
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
Example Gmail SMTP Configuration
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=your-email@gmail.com
SPRING_MAIL_PASSWORD=your-app-password

⚠️ Never commit database passwords, email passwords, API keys, or other sensitive credentials to GitHub.

💻 Running Locally
1. Clone the Repository
git clone https://github.com/Srinudamma/smart-payment-tracker.git
cd smart-payment-tracker
2. Configure PostgreSQL

Create a PostgreSQL database and configure the required environment variables:

DB_URL=jdbc:postgresql://localhost:5432/smart_payment_tracker
DB_USERNAME=your_username
DB_PASSWORD=your_password
3. Configure Email

Set the required SMTP environment variables if email functionality is required.

4. Run the Application

Using Maven:

./mvnw spring-boot:run

On Windows:

mvnw.cmd spring-boot:run

The application will start on:

http://localhost:8080

🐳 Running with Docker
Build the Docker Image
docker build -t smart-payment-tracker .
Run the Container
docker run -p 8080:8080 smart-payment-tracker

The project uses Docker for containerized deployment.

☁️ Deployment

The application is deployed using Render with Docker and PostgreSQL.

Deployment Architecture
                 GitHub Repository
                        │
                        ▼
                     Render
                        │
                        ▼
                Docker Container
                        │
                        ▼
                 Spring Boot App
                        │
                        ▼
                   PostgreSQL
Deployment Features
Docker-based deployment
PostgreSQL database
Environment-based configuration
Secure credential management
Cloud-hosted Spring Boot application

🌐 Live Application:

https://smart-payment-and-expense-tracker.onrender.com/

🔒 Security

The application uses Spring Security to provide authentication and protect application resources.

Sensitive configuration such as:

Database credentials
Email credentials
Application secrets

is supplied through environment variables instead of being stored directly in the source code.

🧪 Testing

The project contains a test structure under:

src/test/

Tests can be executed using:

./mvnw test
📈 Future Enhancements

Potential future improvements include:

Interactive charts and advanced analytics
Recurring expense management
Multiple account and wallet tracking
Intelligent budget recommendations
Mobile-friendly UI improvements
REST API documentation using Swagger/OpenAPI
Advanced notification system
CI/CD automation
Enhanced financial insights
🎯 Key Learning Outcomes

This project provided hands-on experience with:

Java application development
Object-Oriented Programming
Spring Boot
RESTful backend development
Spring Data JPA
Hibernate
PostgreSQL database integration
Spring Security
Authentication and authorization
Thymeleaf
Email integration
PDF generation
Docker containerization
Cloud deployment
Environment-based configuration
Git and GitHub workflow
👨‍💻 Author
Srinu Damma

B.Tech – Information Technology

💻 GitHub:
https://github.com/Srinudamma

🚀 Live Project:
https://smart-payment-and-expense-tracker.onrender.com/

📜 License

This project is developed for educational and portfolio purposes.
