package br.com.jeffersonbraga.tickers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TickersApplication {

	public static void main(String[] args) {
		SpringApplication.run(TickersApplication.class, args);
	}

}
