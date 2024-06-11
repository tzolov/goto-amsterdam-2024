package com.example.gotodemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GotodemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GotodemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ChatClient.Builder builder) {
		return (args) -> {
			ChatClient chatClient = builder.build();
			System.out.println(chatClient.prompt().user("tell me a joke?").call().content());
		};
	}
}
