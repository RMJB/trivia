# Trivia App

This project is a simple trivia application with an Angular frontend and a Spring Boot backend.

## Prerequisites

Make sure you have the following installed on your machine:

- Node.js and npm 
- Angular CLI 
- Java Development Kit (JDK 17) 
- Maven 
- Docker (Optional)

Download/clone this repo

# Option 1
## Running the Backend (Spring Boot)

1. Navigate to the `triviabackend` directory.
2. Run the following commands
    ```bash
    cd triviabackend
    mvn clean install
    java -jar target/triviabackend-0.0.2-SNAPSHOT.jar
3. The backend will be accessible at http://localhost:8080.

## Running the Frontend (Angular)

1. Navigate to the `triviafrontend` directory.
2. Run the following commands
    ```bash
    cd triviafrontend
    npm install
    ng serve
3. The frontend will be accessible at http://localhost:4200.

# Option 2

## Package JAR
1. Run the following commands
    ```bash
    cd triviabackend
    mvn clean install

## Run with docker compose
1. Using the provided docker-compose.yml
2. Run the following command
    ```bash
    docker compose up -d

## Answer the questions
1. Go to  http://localhost:4200
2. Answer the questions
3. Want more questions refresh the page.

## Potential improvements
1. Deploy in cloud
2. Implement frontend tests
