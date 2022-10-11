package com.signature.listener;

import com.signature.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Slf4j
@Component
public class MessageListener {

    private final JmsTemplate jmsTemplate;

    public MessageListener(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload final com.signature.model.Message payload,
                       @Headers MessageHeaders messageHeaders, Message message) {
        log.info("I got a message!!!");
        log.info(payload.toString());
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(@Payload final com.signature.model.Message payload,
                               @Headers MessageHeaders messageHeaders, Message message) throws JMSException {
        var replyMessage = com.signature.model.Message.builder()
                .id(UUID.randomUUID()).message("World!").build();

        log.info("I got a message!!!, sending reply");
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyMessage);
    }
}