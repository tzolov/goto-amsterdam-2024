package com.example.gotodemo;

import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

@EnableAutoConfiguration
@SpringBootTest(classes = FunctionCallingTest.MyConfiguration.class)
public class FunctionCallingTest {

    private static final Logger logger = LoggerFactory.getLogger(FunctionCallingTest.class);

    @Autowired
    ChatClient.Builder chatClientBuider;

    @Test
    void functionCallTest() {

        String response = chatClientBuider.build().prompt()
                .user("What is the status of my payment transactions 001, 002 and 003?")
                .functions("paymentStatus")
                .call()
                .content();

        logger.info("\n Response: {} \n", response);

    }

    @TestConfiguration
    public static class MyConfiguration {

        record Transaction(String id) {
        }

        record Status(String name) {
        }

        @Bean
        @Description("Get the status of a single payment transaction")
        public Function<Transaction, Status> paymentStatus() {
            return transaction -> {
                logger.info("Single transaction: " + transaction);
                return DATASET.get(transaction);
            };
        }

        // format: off
        static final Map<Transaction, Status> DATASET = Map.of(
                new Transaction("001"), new Status("pending"),
                new Transaction("002"), new Status("approved"),
                new Transaction("003"), new Status("rejected"));
        // format: on

    }

}
