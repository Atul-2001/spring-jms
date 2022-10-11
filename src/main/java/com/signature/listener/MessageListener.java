package com.signature.listener;

import com.signature.config.JmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Slf4j
@Component
public class MessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload final com.signature.model.Message payload,
                       @Headers MessageHeaders messageHeaders, Message message) {
        log.info("I got a message!!!");
        log.info(payload.toString());
    }
}