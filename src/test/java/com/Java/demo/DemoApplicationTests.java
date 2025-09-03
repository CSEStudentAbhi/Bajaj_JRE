package com.Java.demo;

import com.Java.demo.dto.WebhookRequest;
import com.Java.demo.dto.WebhookResponse;
import com.Java.demo.dto.SolutionRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DemoApplicationTests {

	@Test
	void testWebhookRequest() {
		WebhookRequest request = new WebhookRequest("John Doe", "REG12347", "john@example.com");
		assertEquals("John Doe", request.getName());
		assertEquals("REG12347", request.getRegNo());
		assertEquals("john@example.com", request.getEmail());
	}

	@Test
	void testWebhookResponse() {
		WebhookResponse response = new WebhookResponse("https://example.com/webhook", "token123");
		assertEquals("https://example.com/webhook", response.getWebhook());
		assertEquals("token123", response.getAccessToken());
	}

	@Test
	void testSolutionRequest() {
		SolutionRequest solution = new SolutionRequest("SELECT * FROM EMPLOYEE");
		assertEquals("SELECT * FROM EMPLOYEE", solution.getFinalQuery());
	}
}
