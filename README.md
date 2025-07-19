# job_application_tracker

A full-stack application to help users organize and manage their job search process.

## Features
- User authentication with JWT
- Save and track job applications
- Track application statuses (Applied, Interview Scheduled, Rejected, Offer Received, etc.)
- Set reminders for follow-ups and interviews
- Dashboard with insights on application progress
- Statistics on success rates and application outcomes
- Calendar for upcoming interviews
- Notifications for important events and deadlines

## Technology Stack
- **Frontend**: React, Redux Toolkit, Material-UI, Recharts
- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Node.js 14 or higher
- MySQL 8.0 or higher
- Maven

### Backend Setup
1. Navigate to the `backend` directory
2. Create a `.env` file in the backend directory with the following environment variables:
   ```
   DB_URL=jdbc:mysql://localhost:3306/job_tracker?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   JWT_SECRET=your_jwt_secret_key
   JWT_EXPIRATION=86400000
   ```
3. Run `mvn clean install` to build the project
4. Start the application with `mvn spring-boot:run`

### Frontend Setup
1. Navigate to the `frontend` directory
2. Create a `.env` file in the frontend directory with the following environment variables:
   ```
   REACT_APP_API_URL=http://localhost:8080/api
   REACT_APP_AUTH_URL=http://localhost:8080/api/auth
   REACT_APP_JOB_URL=http://localhost:8080/api/job-applications
   ```
3. Run `npm install` to install dependencies
4. Start the application with `npm start`

## Environment Variables

### Backend (.env)
- `DB_URL`: JDBC URL for your MySQL database
- `DB_USERNAME`: MySQL database username
- `DB_PASSWORD`: MySQL database password
- `JWT_SECRET`: Secret key for JWT token generation
- `JWT_EXPIRATION`: JWT token expiration time in milliseconds

### Frontend (.env)
- `REACT_APP_API_URL`: Base API URL
- `REACT_APP_AUTH_URL`: Authentication API URL
- `REACT_APP_JOB_URL`: Job applications API URL

## Git Configuration
The repository includes a comprehensive `.gitignore` file that excludes:
- Environment files (.env, .env.local, etc.)
- Build directories and compiled files
- IDE-specific files and logs
- Dependency directories (node_modules, Maven target)

## API Endpoints

### Authentication
- `POST /api/auth/register`: Register a new user
- `POST /api/auth/login`: Login and receive JWT token

### Job Applications
- `GET /api/job-applications`: Get all job applications for the authenticated user
- `GET /api/job-applications/{id}`: Get a specific job application
- `POST /api/job-applications`: Create a new job application
- `PUT /api/job-applications/{id}`: Update a job application
- `DELETE /api/job-applications/{id}`: Delete a job application

### Reminders
- `GET /api/reminders`: Get all reminders for the authenticated user
- `POST /api/reminders/job/{jobId}`: Create a new reminder for a job application
- `PUT /api/reminders/{id}`: Update a reminder
- `DELETE /api/reminders/{id}`: Delete a reminder

## License
This project is open-source software.



command to run backend

cd backend; java -jar target/job-application-tracker-0.0.1-SNAPSHOT.jar
