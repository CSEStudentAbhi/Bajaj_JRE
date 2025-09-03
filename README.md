# Webhook SQL Problem Solver

This Spring Boot application implements a webhook-based system that:
1. Generates a webhook on startup
2. Solves a SQL problem and stores sample data
3. Submits the SQL solution to the webhook URL

## Features

- **Automatic Webhook Generation**: Sends POST request to generate webhook on application startup
- **SQL Problem Solving**: Creates and stores sample data based on the given table structure
- **Solution Submission**: Automatically submits the final SQL query to the webhook URL
- **JWT Authentication**: Uses the received access token for authorization

## Table Structure

The application works with three main tables:

### 1. DEPARTMENT
- `DEPARTMENT_ID` (Primary Key)
- `DEPARTMENT_NAME`

### 2. EMPLOYEE
- `EMP_ID` (Primary Key)
- `FIRST_NAME`
- `LAST_NAME`
- `DOB` (Date of Birth)
- `GENDER`
- `DEPARTMENT` (Foreign Key referencing DEPARTMENT_ID)

### 3. PAYMENTS
- `PAYMENT_ID` (Primary Key)
- `EMP_ID` (Foreign Key referencing EMP_ID)
- `AMOUNT` (Salary credited)
- `PAYMENT_TIME` (Date and time of transaction)

## API Endpoints

### Webhook Generation
- **URL**: `https://localhost:8080/hiring/generateWebhook/JAVA`
- **Method**: POST
- **Body**:
```json
{
  "name": "John Doe",
  "regNo": "REG12347",
  "email": "john@example.com"
}
```

### Solution Submission
- **URL**: `https://localhost:8080/hiring/testWebhook/JAVA`
- **Method**: POST
- **Headers**:
  - `Authorization`: `<accessToken>`
  - `Content-Type`: `application/json`
- **Body**:
```json
{
  "finalQuery": "YOUR_SQL_QUERY_HERE"
}
```

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

## Configuration

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.url=jdbc:mysql://localhost:3306/demo_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
```

## Running the Application

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd demo
   ```

2. **Configure database**:
   - Update `application.properties` with your MySQL credentials
   - Ensure MySQL is running on localhost:3306

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Or build and run**:
   ```bash
   mvn clean package
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

## What Happens on Startup

1. **Webhook Generation**: Application automatically sends a POST request to generate a webhook
2. **Data Population**: Creates sample departments, employees, and payment records
3. **SQL Solution**: Generates a comprehensive SQL query demonstrating table relationships
4. **Solution Submission**: Submits the SQL query to the webhook URL using the received JWT token

## Sample SQL Query Generated

The application generates a comprehensive SQL query that:
- Joins all three tables (DEPARTMENT, EMPLOYEE, PAYMENTS)
- Calculates payment statistics (count, sum, average)
- Filters recent payments (last 30 days)
- Groups results by department and employee
- Orders results logically

## Logging

The application provides detailed logging for:
- Webhook generation process
- Data storage operations
- Solution submission
- Error handling

Check the console output for detailed information about each step.

## Troubleshooting

- **Database Connection**: Ensure MySQL is running and credentials are correct
- **Network Issues**: Check if the webhook URLs are accessible from your network
- **JWT Token**: Verify the access token is received and valid
- **Logs**: Check console output for detailed error messages

## Project Structure

```
src/main/java/com/Java/demo/
├── DemoApplication.java          # Main application class
├── config/
│   └── AppConfig.java           # Configuration beans
├── dto/
│   ├── WebhookRequest.java      # Webhook generation request DTO
│   ├── WebhookResponse.java     # Webhook generation response DTO
│   └── SolutionRequest.java     # Solution submission DTO
├── entity/
│   ├── Department.java          # Department entity
│   ├── Employee.java            # Employee entity
│   └── Payment.java             # Payment entity
├── repository/
│   ├── DepartmentRepository.java # Department data access
│   ├── EmployeeRepository.java   # Employee data access
│   └── PaymentRepository.java    # Payment data access
└── service/
    └── WebhookService.java      # Main business logic service
```

## Dependencies

- Spring Boot 3.5.5
- Spring Data JPA
- Spring Web
- MySQL Connector
- Spring Boot DevTools

## License

This project is licensed under the MIT License.
