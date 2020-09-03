package ar.edu.iua;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	public static void main(String[] args) {
	
		SpringApplication.run(BackendApplication.class, args);
	}

	@Autowired
	private IPruebaPerfil pruebaPerfil;
	
	@Value("${spring.datasource.url:pepe}")
	private String springDatasourceUrl;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("DataSource URL: {}", springDatasourceUrl);
		pruebaPerfil.mensaje();
	}
	
	
}

/*
Modelo
  | -- > Persistencia
----------------------------
Negocio
----------------------------
Servicios WEB (REST)
  | --> servicio 1   |
  | --> servicio 2   | seguridad
  | --> servicio N   V
*/
