package com.sparx.demo.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

	@Bean
	SparkSession getSession() {
		return SparkSession.builder().appName("SparkProject").master("local[*]") // Set the master URL here
				// .master("/home/inferyx/Downloads/username.csv")
				.getOrCreate();

	}

}
