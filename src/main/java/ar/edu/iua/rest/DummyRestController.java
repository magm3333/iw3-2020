package ar.edu.iua.rest;

import java.util.ArrayList;
import java.util.Date;
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
	public ResponseEntity<List<Banda>> listDummy() {
		List<Banda> lista = new ArrayList<Banda>();
		lista.add(new Banda("V8", new Date()));
		lista.add(new Banda("Malon", new Date()));
		lista.add(new Banda("Hermética", new Date()));

		return new ResponseEntity<List<Banda>>(lista, HttpStatus.OK);
	}

}

class Banda {

	private String nombre;
	private Date fecha;

	public Banda(String nombre, Date fecha) {
		super();
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
