# Card Management System with Fraud Detection

This project includes two Spring Boot microservices:

- **card-management**: Handles account creation, card issuance, and transaction processing.
- **fraud-detection**: Detects potentially fraudulent transactions based on amount and transaction frequency.

---

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- [Docker](https://www.docker.com/)

---

### 🐳 Running the Services

From the root directory (where `docker-compose.yml` is located), run:

```bash
docker-compose up --build
This command will:

Start PostgreSQL on port 5434

Start Card Management Service on port 8080

Start Fraud Detection Service on port 8081

📘 API Documentation
Once services are running, access Swagger UI for the Card Management microservice:

👉 http://localhost:8080/swagger-ui/index.html

⛔ Stopping the Services
To stop all running containers:

docker-compose down

Let me know if you'd like a version badge, contribution guide, or deployment section added.