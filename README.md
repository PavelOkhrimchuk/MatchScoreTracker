# **MatchScoreTracker** 🎾

## **Description** 📄

**MatchScoreTracker** is an application for tracking match results. The application allows users to create and view matches, as well as track their statistics.

## **Technologies Used** 🛠️

- **Java** ☕: The core programming language for the application.
- **Tomcat** 🐱: A web server and servlet container for deploying the Java application.
- **Hibernate** 🗄️: An ORM framework for managing database interactions.
- **H2 Database** 💾: An in-memory database for storing match data.
- **Gradle** 🚀: A build automation tool for compiling, testing, and packaging the application.
- **JUnit5** 🧪: A testing framework for running tests.
- **Docker** 🐋: A containerization platform for  deployment and scaling of the application.

## **Requirements** 🛠️

Before you begin, ensure you have the following installed:

- **Java 17** or higher ☕
- **Tomcat 10** or higher 🐱
- **Docker** 🐋
- **Docker Compose** 📦

## **Running the Project with Docker** 🐳

To simplify the setup and execution of the project in a containerized environment, Dockerfile and Docker Compose configurations are included. Follow the instructions below to deploy the application using Docker.

### **Setup and Execution** ⚙️

1. **Clone the Repository**:

    ```sh
    git clone <https://github.com/PavelOkhrimchuk/CurrencyExchangeApp.git>
    cd <REPOSITORY-FOLDER-NAME>
    ```

2. **Build and Start Containers with Docker Compose**:

    ```sh
    docker-compose up --build
    ```

    This command will build the images and start the containers defined in `docker-compose.yml`.

3. **Access the Application**:

    Open your browser and go to the following URL to access the application:

    ```arduino
    http://localhost:8082/MatchScoreTracker-1.0-SNAPSHOT/
    ```

    This URL will direct you to the **MatchScoreTracker** application running in the Docker container.


