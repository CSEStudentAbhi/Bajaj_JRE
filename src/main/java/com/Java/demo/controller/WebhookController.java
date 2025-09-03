package com.Java.demo.controller;

import com.Java.demo.dto.SolutionRequest;
import com.Java.demo.dto.WebhookRequest;
import com.Java.demo.dto.WebhookResponse;
import com.Java.demo.service.DataInitializationService;
import com.Java.demo.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
@CrossOrigin(origins = "*")
public class WebhookController {
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    
    @Autowired
    private WebhookService webhookService;
    
    @Autowired
    private DataInitializationService dataInitializationService;
    
    /**
     * POST endpoint to manually trigger the complete webhook process
     * POST /api/webhook/trigger
     */
    @PostMapping("/trigger")
    public ResponseEntity<Map<String, Object>> triggerWebhookProcess() {
        logger.info("Manual webhook process triggered via API");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Trigger the webhook process
            webhookService.run();
            
            response.put("success", true);
            response.put("message", "Webhook process completed successfully");
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error in manual webhook process", e);
            
            response.put("success", false);
            response.put("message", "Error occurred: " + e.getMessage());
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * POST endpoint to generate a webhook
     * POST /api/webhook/generate
     */
    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateWebhook(@RequestBody WebhookRequest request) {
        logger.info("Webhook generation requested for: {}", request.getName());
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Call the webhook generation logic
            WebhookResponse webhookResponse = webhookService.generateWebhook();
            
            if (webhookResponse != null) {
                response.put("success", true);
                response.put("message", "Webhook generated successfully");
                response.put("webhook", webhookResponse.getWebhook());
                response.put("accessToken", webhookResponse.getAccessToken());
                response.put("timestamp", java.time.LocalDateTime.now());
                
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Failed to generate webhook");
                response.put("timestamp", java.time.LocalDateTime.now());
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
        } catch (Exception e) {
            logger.error("Error generating webhook", e);
            
            response.put("success", false);
            response.put("message", "Error occurred: " + e.getMessage());
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * POST endpoint to submit a solution
     * POST /api/webhook/submit
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitSolution(
            @RequestBody SolutionRequest solutionRequest,
            @RequestHeader("Authorization") String authorization) {
        
        logger.info("Solution submission requested");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (authorization == null || authorization.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Authorization header is required");
                response.put("timestamp", java.time.LocalDateTime.now());
                
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // Extract token from "Bearer <token>" format
            String token = authorization;
            if (authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
            }
            
            // Submit the solution
            webhookService.submitSolution(token);
            
            response.put("success", true);
            response.put("message", "Solution submitted successfully");
            response.put("query", solutionRequest.getFinalQuery());
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error submitting solution", e);
            
            response.put("success", false);
            response.put("message", "Error occurred: " + e.getMessage());
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * POST endpoint to initialize sample data
     * POST /api/webhook/init-data
     */
    @PostMapping("/init-data")
    public ResponseEntity<Map<String, Object>> initializeSampleData() {
        logger.info("Sample data initialization requested");
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            dataInitializationService.initializeSampleData();
            
            response.put("success", true);
            response.put("message", "Sample data initialized successfully");
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error initializing sample data", e);
            
            response.put("success", false);
            response.put("message", "Error occurred: " + e.getMessage());
            response.put("timestamp", java.time.LocalDateTime.now());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * GET endpoint to check application status
     * GET /api/webhook/status
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("status", "running");
        response.put("application", "Webhook SQL Problem Solver");
        response.put("version", "1.0.0");
        response.put("timestamp", java.time.LocalDateTime.now());
        response.put("endpoints", new String[]{
            "POST /api/webhook/trigger - Trigger complete webhook process",
            "POST /api/webhook/generate - Generate webhook only",
            "POST /api/webhook/submit - Submit SQL solution",
            "POST /api/webhook/init-data - Initialize sample data",
            "GET /api/webhook/status - Get application status"
        });
        
        return ResponseEntity.ok(response);
    }
}
