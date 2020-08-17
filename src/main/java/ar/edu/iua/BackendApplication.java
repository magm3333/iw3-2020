package ar.edu.iua;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	@Value("${spring.datasource.url}")
	private String springDatasourceUrl;


	@Override
	public void run(String... args) throws Exception {
		log.info("DataSource URL: {}", springDatasourceUrl);
		
	}

}