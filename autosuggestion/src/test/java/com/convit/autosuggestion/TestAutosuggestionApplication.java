package com.convit.autosuggestion;

import org.springframework.boot.SpringApplication;

public class TestAutosuggestionApplication {

	public static void main(String[] args) {
		SpringApplication.from(AutosuggestionApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
