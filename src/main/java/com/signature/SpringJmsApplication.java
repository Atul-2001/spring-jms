package com.signature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJmsApplication {

    public static void main(String[] args) throws Exception {

//        ActiveMQServer activeMQServer = ActiveMQServers
//                .newActiveMQServer(new ConfigurationImpl()
//                        .setPersistenceEnabled(false)
//                        .setJournalDirectory("target/data/journal")
//                        .setSecurityEnabled(false)
//                        .addAcceptorConfiguration("invm", "vm://0")
//                );
//
//        activeMQServer.start();

        SpringApplication.run(SpringJmsApplication.class, args);
    }

}
