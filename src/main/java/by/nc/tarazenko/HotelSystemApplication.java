package by.nc.tarazenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"by.nc.tarazenko.service.implementations",
		"by.nc.tarazenko.service", "by.nc.tarazenko.repository", "by.nc.tarazenko.controller"})
public class HotelSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(HotelSystemApplication.class, args);
	}

}
