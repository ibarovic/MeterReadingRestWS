package com.typeqast.mr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.typeqast.mr.config.MessageSourceConfig;

@SpringBootApplication
@Import({MessageSourceConfig.class})
public class MeterReadingRestWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeterReadingRestWsApplication.class, args);
	}
}
