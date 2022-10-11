package com.signature.sender;

import com.signature.config.JmsConfig;
import com.signature.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class MessageSender {

    private final JmsTemplate jmsTemplate;

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
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
}