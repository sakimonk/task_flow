### Environment Configuration
* JDK 21
* Mongo 8.0

### Build & Run
1. Run the build command in the project root directory: `./gradlew clean build`
2. Start the server: `java -jar ./task_flow_api/build/libs/task_flow_api-0.0.1-SNAPSHOT.jar`

### Test
Run the test command in the project root directory: `./gradlew clean test`

### Swagger UI Address
http://localhost:8080/swagger-ui/index.html#
#### OpenAPI Doc (for importing APIs into Postman)
http://localhost:8080/v3/api-docs

### Project Structure Overview
* **task_flow_api**: The task API server, providing interfaces for user registration, login, and TODO management.
* **task_flow_common**: A common module offering shared utilities, such as serialization tools.

### Fully Implemented Features
* TODOs CRUD (Create, Read, Update, Delete)
* Filtering (e.g., by status, due date)
* Sorting (e.g., by due date, status, name)
* Additional attributes for each TODO (e.g., priority, tags)
* User authentication and registration
* Team features - Authorization (e.g., role-based access control)

### Partially Implemented Features
* Team features - Real-time collaboration (e.g., shared TODO lists, activity feeds)
* DevOps (e.g., CI/CD pipeline, Docker, Kubernetes, Helm)