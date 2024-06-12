package com.baeldung.ls;

import static org.mockito.Mockito.*;

import com.baeldung.ls.events.model.Dress;
import com.baeldung.ls.events.model.Order;
import com.baeldung.ls.events.source.MessageSender;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

class MessageSenderTest {

	@Test
	void testSendMessage_RetryAttemptsExceeded() {
		// Mock KafkaTemplate
		KafkaTemplate<String, Order<String, Dress>> kafkaTemplateMock = mock(KafkaTemplate.class);

		// Create instance of MessageSender
		MessageSender messageSender = new MessageSender();
		messageSender.setKafkaTemplate(kafkaTemplateMock);

		// Mock failed send
		when(kafkaTemplateMock.send(anyString(), anyString(), any(Order.class))).thenThrow(new RuntimeException("Simulated Kafka error"));

		// Call the method under test
		try {
			messageSender.sendMessage();
		} catch (RuntimeException e) {
			// Verify that KafkaTemplate.send() was retried MAX_ATTEMPTS times
			verify(kafkaTemplateMock, times(MessageSender.MAX_ATTEMPTS)).send(anyString(), anyString(), any(Order.class));
		}
	}
}

