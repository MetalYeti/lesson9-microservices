package com.geekbrains.geekmarketsummer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EntityScan({"contract.entities", "com.geekbrains.geekmarketsummer.entities"})
public class GeekMarketSummerApplication {
	public static void main(String[] args) {
		SpringApplication.run(GeekMarketSummerApplication.class, args);
	}
}