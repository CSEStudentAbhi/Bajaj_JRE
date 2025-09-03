# API Examples - Webhook SQL Problem Solver

This document provides examples of how to use the REST API endpoints using cURL commands.

## Base URL
```
http://localhost:8080/api/webhook
```

## 1. Check Application Status

### GET /api/webhook/status
```bash
curl -X GET http://localhost:8080/api/webhook/status
```

**Response:**
```json
{
  "status": "running",
  "application": "Webhook SQL Problem Solver",
  "version": "1.0.0",
  "timestamp": "2025-09-03T21:20:00",
  "endpoints": [
    "POST /api/webhook/trigger - Trigger complete webhook process",
    "POST /api/webhook/generate - Generate webhook only",
    "POST /api/webhook/submit - Submit SQL solution",
    "POST /api/webhook/init-data - Initialize sample data",
    "GET /api/webhook/status - Get application status"
  ]
}
```

## 2. Trigger Complete Webhook Process

### POST /api/webhook/trigger
This endpoint triggers the complete workflow automatically.

```bash
curl -X POST http://localhost:8080/api/webhook/trigger \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Webhook process completed successfully",
  "timestamp": "2025-09-03T21:20:00"
}
```

## 3. Generate Webhook

### POST /api/webhook/generate
This endpoint generates a webhook with custom user data.

```bash
curl -X POST http://localhost:8080/api/webhook/generate \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "regNo": "REG12347",
    "email": "john@example.com"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Webhook generated successfully",
  "webhook": "https://example.com/webhook/abc123",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "timestamp": "2025-09-03T21:20:00"
}
```

## 4. Initialize Sample Data

### POST /api/webhook/init-data
This endpoint creates sample departments, employees, and payment records.

```bash
curl -X POST http://localhost:8080/api/webhook/init-data \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Sample data initialized successfully",
  "timestamp": "2025-09-03T21:20:00"
}
```

## 5. Submit SQL Solution

### POST /api/webhook/submit
This endpoint submits a SQL solution with authorization.

```bash
curl -X POST http://localhost:8080/api/webhook/submit \
  -H "Content-Type: application/json" \
  -H "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -d '{
    "finalQuery": "SELECT * FROM EMPLOYEE WHERE DEPARTMENT = 1;"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Solution submitted successfully",
  "query": "SELECT * FROM EMPLOYEE WHERE DEPARTMENT = 1;",
  "timestamp": "2025-09-03T21:20:00"
}
```

## Complete Workflow Example

Here's how to execute the complete workflow step by step:

### Step 1: Check Status
```bash
curl -X GET http://localhost:8080/api/webhook/status
```

### Step 2: Initialize Sample Data
```bash
curl -X POST http://localhost:8080/api/webhook/init-data \
  -H "Content-Type: application/json"
```

### Step 3: Generate Webhook
```bash
curl -X POST http://localhost:8080/api/webhook/generate \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "regNo": "REG12347",
    "email": "john@example.com"
  }'
```

### Step 4: Submit Solution (using token from step 3)
```bash
curl -X POST http://localhost:8080/api/webhook/submit \
  -H "Content-Type: application/json" \
  -H "Authorization: YOUR_ACCESS_TOKEN_HERE" \
  -d '{
    "finalQuery": "SELECT d.DEPARTMENT_NAME, COUNT(e.EMP_ID) as employee_count FROM DEPARTMENT d LEFT JOIN EMPLOYEE e ON d.DEPARTMENT_ID = e.DEPARTMENT GROUP BY d.DEPARTMENT_ID, d.DEPARTMENT_NAME;"
  }'
```

## Alternative: One-Click Complete Process

If you want to execute everything automatically:

```bash
curl -X POST http://localhost:8080/api/webhook/trigger \
  -H "Content-Type: application/json"
```

## Error Handling

All endpoints return consistent error responses:

```json
{
  "success": false,
  "message": "Error description here",
  "timestamp": "2025-09-03T21:20:00"
}
```

Common HTTP status codes:
- `200 OK` - Success
- `400 Bad Request` - Invalid input data
- `401 Unauthorized` - Missing or invalid authorization
- `500 Internal Server Error` - Server-side error

## Testing with Different Tools

### Using Postman
1. Import the endpoints into Postman
2. Set the base URL to `http://localhost:8080`
3. Use the request bodies and headers from the examples above

### Using JavaScript/Fetch
```javascript
const response = await fetch('http://localhost:8080/api/webhook/status');
const data = await response.json();
console.log(data);
```

### Using Python Requests
```python
import requests

response = requests.get('http://localhost:8080/api/webhook/status')
data = response.json()
print(data)
```

## Notes

- The application must be running on port 8080
- MySQL database must be configured and accessible
- All endpoints support CORS for cross-origin requests
- Responses include timestamps for debugging and logging
- The webhook generation endpoint makes actual HTTP calls to external services
