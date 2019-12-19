package by.nc.tarazenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"by.nc.tarazenko.service.implementations",
		"by.nc.tarazenko.repository", "by.nc.tarazenko.controller",
		"by.nc.tarazenko.security"
})
@EnableSwagger2
public class HotelSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(HotelSystemApplication.class, args);
	}
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("by.nc.tarazenko.controller"))
				.build();
	}
}
