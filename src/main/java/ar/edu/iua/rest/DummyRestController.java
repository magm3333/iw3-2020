package ar.edu.iua.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constantes.URL_DUMMY)
public class DummyRestController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> listDummy() {
		List<String> lista=new ArrayList<String>();
		lista.add("V8");
		lista.add("Malon");
		lista.add("Hermetica");
		

		return new ResponseEntity<List<String>>(lista, HttpStatus.OK);
	}



}
