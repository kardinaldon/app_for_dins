package com.dins.kafka_app;

import com.dins.kafka_app.service.DataTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.util.*;


@SpringBootApplication
public class KafkaApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory
			.getLogger(KafkaApplication.class);

	@Autowired
	private DataTransportService dataTransportService;

	private boolean stepOne = false;

	public static void main(String[] args) {
		LOG.info("--STARTING THE APPLICATION--");
		SpringApplication app = new SpringApplication(KafkaApplication.class);
		app.run(args);
		LOG.info("--APPLICATION FINISHED--");
	}

	@Override
	public void run(String... args) throws InterruptedException {
		Scanner scan = new Scanner(System.in);
		try {
			while (scan.hasNextLine()){
				String line = scan.nextLine().toLowerCase();
				switch (line) {
					case ("1"):
						LOG.info("--loading data from file into database table one--");
						dataTransportService.fromFileToTableOne();
						LOG.info("--loading data from file into database table one completed--");
						LOG.info("--loading data from table one to kafka topic--");
						dataTransportService.fromTableOneToKafka();
						LOG.info("--loading data from table one to kafka topic completed--");
						stepOne = true;
						break;
					case ("2"):
						if(stepOne){
							LOG.info("--loading data from kafka topic to table 2--");
							dataTransportService.fromKafkaToTableTwo();
							LOG.info("--loading data from kafka topic to table 2 completed--");
						}
						else {
							LOG.info("At the beginning, enter 1, to fill the first table with data and send them to the kafka topic");
						}
						break;
					case ("3"):
						System.exit(0);
					default:
						System.out.println("enter 1 to send records to kafka, 2 to upload records from kafka to the database, 3 to end the program");
						break;
				}
			}

		} finally {
			scan.close();
		}
	}
}
