package com.sparx.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SparKProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparKProjectApplication.class, args);
	}

	@GetMapping("/hi")
	public String getName(@RequestParam(value = "name") String name) {

		return " Heello " + name + " welocome to sparx project";
	}
}
