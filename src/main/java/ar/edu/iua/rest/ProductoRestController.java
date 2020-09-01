package ar.edu.iua.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.business.IProductoBusiness;
import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;

@RestController
@RequestMapping(value = Constantes.URL_PRODUCTOS)
public class ProductoRestController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IProductoBusiness productoBusiness;

	// curl "http://localhost:8080/api/v1/productos/1" -v
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Producto> load(@PathVariable("id") Long id) {

		try {
			return new ResponseEntity<Producto>(productoBusiness.load(id), HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}

	// curl "http://localhost:8080/api/v1/productos"
	// curl "http://localhost:8080/api/v1/productos?parte=arga"
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> list(
			@RequestParam(name = "parte", required = false, defaultValue = "*") String parte) {
		try {
			if (parte.equals("*")) {
				return new ResponseEntity<List<Producto>>(productoBusiness.list(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Producto>>(productoBusiness.list(parte), HttpStatus.OK);
			}
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// curl -X POST "http://localhost:8080/api/v1/productos" -H "Content-Type:
	// application/json" -d '{"nombre":"Leche","descripcion":"Larga
	// Vida","precioLista":69,"enStock":true}' -v
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> add(@RequestBody Producto producto) {
		try {
			productoBusiness.add(producto);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constantes.URL_PRODUCTOS + "/" + producto.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// curl -X PUT "http://localhost:8080/api/v1/productos" -H "Content-Type:
	// application/json" -d '{"id":2,"nombre":"Leche","descripcion":"Larga
	// Vida","precioLista":55,"enStock":false}' -v
	@PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody Producto producto) {
		try {
			productoBusiness.update(producto);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	// curl -X DELETE "http://localhost:8080/api/v1/productos/2" -v
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		try {
			productoBusiness.delete(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

//URL-> http://localhost:8080/api/v1/productos/1 --> IProductoBusiness.load(  1 ) 

}
