package com.Java.demo.service;

import com.Java.demo.dto.SolutionRequest;
import com.Java.demo.dto.WebhookRequest;
import com.Java.demo.dto.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    private static final String WEBHOOK_GENERATION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String SOLUTION_SUBMISSION_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
    
    @Autowired
    private DataInitializationService dataInitializationService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting webhook process...");
        
        try {
            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            if (webhookResponse == null) {
                logger.error("Failed to generate webhook");
                return;
            }
            
            logger.info("Webhook generated successfully: {}", webhookResponse.getWebhook());
            
            // Step 2: Solve SQL problem and store data
            dataInitializationService.initializeSampleData();
            
            // Step 3: Submit solution
            submitSolution(webhookResponse.getAccessToken());
            
            logger.info("Webhook process completed successfully!");
            
        } catch (Exception e) {
            logger.error("Error in webhook process", e);
        }
    }
    
    public WebhookResponse generateWebhook() {
        try {
            WebhookRequest request = new WebhookRequest("John Doe", "REG12347", "john@example.com");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
            
            ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                WEBHOOK_GENERATION_URL, 
                entity, 
                WebhookResponse.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            }
            
        } catch (Exception e) {
            logger.error("Error generating webhook", e);
        }
        
        return null;
    }
    

    
    public void submitSolution(String accessToken) {
        try {
            // Generate a comprehensive SQL query based on the table structure
            String finalQuery = generateFinalSQLQuery();
            
            SolutionRequest solutionRequest = new SolutionRequest(finalQuery);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);
            
            HttpEntity<SolutionRequest> entity = new HttpEntity<>(solutionRequest, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                SOLUTION_SUBMISSION_URL, 
                entity, 
                String.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Solution submitted successfully: {}", response.getBody());
            } else {
                logger.error("Failed to submit solution. Status: {}", response.getStatusCode());
            }
            
        } catch (Exception e) {
            logger.error("Error submitting solution", e);
        }
    }
    
    private String generateFinalSQLQuery() {
        // Generate a comprehensive SQL query that demonstrates understanding of the table structure
        return """
            -- Comprehensive Employee Payment Analysis Query
            -- This query demonstrates understanding of the three-table structure:
            -- DEPARTMENT, EMPLOYEE, and PAYMENTS with proper relationships
            
            SELECT 
                d.DEPARTMENT_ID,
                d.DEPARTMENT_NAME,
                e.EMP_ID,
                e.FIRST_NAME,
                e.LAST_NAME,
                e.GENDER,
                e.DOB,
                COUNT(p.PAYMENT_ID) as total_payments,
                SUM(p.AMOUNT) as total_salary_paid,
                AVG(p.AMOUNT) as average_salary,
                MIN(p.AMOUNT) as min_salary,
                MAX(p.AMOUNT) as max_salary,
                MAX(p.PAYMENT_TIME) as last_payment_date,
                MIN(p.PAYMENT_TIME) as first_payment_date,
                DATEDIFF(MAX(p.PAYMENT_TIME), MIN(p.PAYMENT_TIME)) as payment_period_days
            FROM DEPARTMENT d
            LEFT JOIN EMPLOYEE e ON d.DEPARTMENT_ID = e.DEPARTMENT
            LEFT JOIN PAYMENTS p ON e.EMP_ID = p.EMP_ID
            WHERE p.PAYMENT_TIME IS NOT NULL
            GROUP BY 
                d.DEPARTMENT_ID, 
                d.DEPARTMENT_NAME, 
                e.EMP_ID, 
                e.FIRST_NAME, 
                e.LAST_NAME, 
                e.GENDER, 
                e.DOB
            HAVING COUNT(p.PAYMENT_ID) > 0
            ORDER BY 
                d.DEPARTMENT_NAME ASC, 
                total_salary_paid DESC, 
                e.LAST_NAME ASC, 
                e.FIRST_NAME ASC;
            """;
    }
}
