## Dino Ventures – Internal Wallet Service

This project is a backend "Wallet Service" built as part of the "Dino Ventures Backend Engineer assignment".  
It manages virtual credits (like Gold Coins or Reward Points) in a closed-loop system with strong guarantees on data integrity, concurrency, and idempotency.

--------------------------------------------------------

## Tech Stack

- Java 17
- Spring Boot
- Spring JDBC (JdbcOperations)
- PostgreSQ
- Maven
- Postman (for API testing)

--------------------------------------------------------
## Design Highlights

- Ledger-based architecture for auditability
- Database-level locking using `FOR UPDATE`
- Idempotent APIs using idempotency keys
- ACID-compliant transactions
- Prevents negative balances
- Safe under concurrent requests

--------------------------------------------------------

## Features Implemented

- Wallet Top-up (Purchase)
- Bonus / Incentive credit
- Spend / Purchase within app
- Balance consistency under high traffic
- System (Treasury) wallet support

--------------------------------------------------------

## Application Configuration

spring.application.name=wallet
server.port=8090

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.sql.init.mode=never
