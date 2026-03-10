<b>Mini Banking Microservices</b>

<b>TO RUN</b><br>
mvn clean package -DskipTests<br>
docker compose up --build

Created using:<br>
Backend
- Java 17
- Spring Boot 3.5
- Spring Cloud Gateway

Databases
- PostgreSQL
- MongoDB (audit logging)

Messaging
- Apache Kafka

Cache
- Redis

Infrastructure
- Docker

Utilities
- Apache POI (Excel report generation)
- Maven
  

Services:
- banking-service 8080
  - Main business logic of the system.
  - Account management, transfers, withdraw, deposit, etc.
- audit-service 8081
  - Consumes Kafka transaction events and stores them for auditing.
  - Uses mongo
- master-data-service 8082
  - Stores configuration and reference data.
- gateway-service 8083
  - API entry point
  - Request routing
    - /banking/** -> banking-service
    - /master-data/** -> master-data-service
    - /audit/** -> audit-service


<b>ENDPOINTS</b>

All requests should go through the Gateway Service <b>http://localhost:8083</b><br>
add X-API-KEY:erick_test_gateway_777 to request headers

Withdraw<br>
POST /banking/transactions/withdraw
Payload: ("accountNumber": "BR00100000001", "amount": 100000 }

Deposits<br>
POST /banking/transactions/deposit
Payload {
  "accountNumber": "BR00200000001",
  "amount": 999
}

Transfers<br>
POST /banking/transactions/transfer
Payload 
{
  "fromAccountNumber": "BR00200000001",
  "toAccountNumber": "BR00100000001",
  "amount": 888,
  "transferType": "BI_FAST"
}

Transaction Report<br>
GET /banking/transactions/report<br>
Example:
/banking/transactions/report?branchId=BR001&startDate=2026-03-01&endDate=2026-03-10<br>
..and others


mongo collection example: <br>
<img width="494" height="249" alt="image" src="https://github.com/user-attachments/assets/ede6ebbf-ae58-4e64-a59e-1621240ea6f2" />

redis cache exampke: <br>
<img width="470" height="128" alt="image" src="https://github.com/user-attachments/assets/a9fe5912-2dd5-43e1-8630-4f4591a36c4a" />

kafka message exampe: <br>
<img width="1652" height="162" alt="image" src="https://github.com/user-attachments/assets/f969ca71-50e4-485d-8626-2c3e1548cab9" />

simple architecture design<br>
<img width="400" height="561" alt="image" src="https://github.com/user-attachments/assets/28f12172-4286-4492-abbd-d2b9e5cd0da6" />





