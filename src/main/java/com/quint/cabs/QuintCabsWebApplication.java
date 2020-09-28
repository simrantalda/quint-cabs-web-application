package com.quint.cabs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.quint.cabs"})
public class QuintCabsWebApplication {
	
	 @Autowired
	 private ApplicationContext appContext;

	public static void main(String[] args) {
		SpringApplication.run(QuintCabsWebApplication.class, args);
	}

}
