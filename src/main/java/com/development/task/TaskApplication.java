package com.development.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class TaskApplication {
	public static void main(String[] args) {

		SpringApplication.run(TaskApplication.class, args);
	}

}
