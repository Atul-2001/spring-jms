package com.signature.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signature.config.JmsConfig;
import com.signature.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import java.util.UUID;

@Slf4j
@Component
public class MessageSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public MessageSender(JmsTemplate jmsTemplate,
                         ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        log.info("I'm sending a message.");

        final Message message = Message
                .builder().id(UUID.randomUUID())
                .message("Hello World!").build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        log.info("Message sent.");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        final Message message = Message
                .builder().id(UUID.randomUUID())
                .message("Hello!").build();

        var receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, session -> {
            try {
                var textMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                textMessage.setStringProperty("_type", Message.class.getCanonicalName());
                log.info("Sending message: " + textMessage.getText());
                return textMessage;
            } catch (final Exception ex) {
                throw new JMSException(ex.getMessage());
            }
        });

        log.info("Received message: " + receivedMsg.getBody(String.class));
    }
}