package com.task.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.task.scheduletask.FileScanScheduler;

@ComponentScan({ "com.task" })
@EntityScan("com.task.entity")
@EnableJpaRepositories("com.task.repository")
@SpringBootApplication
@EnableScheduling
public class ModelTrainerApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(ModelTrainerApplication.class);
	@Autowired
	FileScanScheduler fileScanScheduler;

	public static void main(String[] args) {
		SpringApplication.run(ModelTrainerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.debug("inside run() method");
		try {
			fileScanScheduler.DirectoryScanScheduledMethod();
		} catch (Exception e) {
			logger.error("Error occured",e);
		}
	}

}
