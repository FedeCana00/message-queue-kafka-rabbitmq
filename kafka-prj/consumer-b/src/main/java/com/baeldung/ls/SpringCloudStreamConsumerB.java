package com.baeldung.ls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan("com.baeldung")
@EnableScheduling
public class SpringCloudStreamConsumerB {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamConsumerB.class, args);
	}

}
