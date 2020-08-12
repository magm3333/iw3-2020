package ar.edu.iua;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendApplication.class, args);

        JSONObject myJson = new JSONObject("{\"hola\": \"mundo\"}");
        System.out.println(myJson.get("hola"));
	}

}
