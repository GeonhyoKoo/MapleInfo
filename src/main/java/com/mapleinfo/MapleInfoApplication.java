package com.mapleinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MapleInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapleInfoApplication.class, args);
	}

}
