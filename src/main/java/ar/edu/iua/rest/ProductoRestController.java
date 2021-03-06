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
import org.springframework.web.multipart.MultipartFile;

import ar.edu.iua.business.IProductoBusiness;
import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = Constantes.URL_PRODUCTOS)
@Api(value = "Productos", description = "Operaciones relacionadas con los productos", tags = { "Productos" })
public class ProductoRestController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IProductoBusiness productoBusiness;

	// curl "http://localhost:8080/api/v1/productos/1" -v
	@ApiOperation(value="Obtener un producto mediante el ID", response = Producto.class)

	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Operación exitosa"),
			@ApiResponse(code = 401, message = "No loguad@"),
			@ApiResponse(code = 403, message = "Acceso denegado por falta de permisos"),
			@ApiResponse(code = 404, message = "No se encuentra el producto"), 
			@ApiResponse(code = 500, message = "Error interno del servidor") 
	})

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Producto> load( @ApiParam(value = "El ID del producto que se desea obtener") @PathVariable("id") Long id) {

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

	@PostMapping(value = "/{id}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setFoto(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
		try {
			Producto p = productoBusiness.setFoto(id, file);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", Constantes.URL_PRODUCTOS + "/" + p.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}/foto")
	public ResponseEntity<byte[]> getFoto(@PathVariable("id") Long id) {
		try {
			Producto p = productoBusiness.load(id);
			if (p.getFoto() == null || p.getFoto().length == 0)
				return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Content-Type", p.getFotoMimeType());
			responseHeaders.set("Content-Disposition",
					String.format("attachment; filename=\"foto_%s.jpg\"", p.getId()));
			return new ResponseEntity<byte[]>(p.getFoto(), responseHeaders, HttpStatus.OK);
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * curl --location --request PUT 'http://localhost:8080/api/v1/productos' \
	 * --header 'Content-Type: application/json' \ --data-raw ' { "id": 1, "nombre":
	 * "Arroz", "descripcion": "Arroz barato", "precioLista": 16.0, "enStock": true,
	 * "comentarios": "{\"user\":\"magm\",\"comentario\":\"No se pasa\"}" }'
	 * 
	 * curl --location --request POST
	 * 'http://localhost:8080/api/v1/productos/1/foto' \ --form
	 * 'file=@/home/mariano/Descargas/linux.jpg' -v
	 */

//URL-> http://localhost:8080/api/v1/productos/1 --> IProductoBusiness.load(  1 ) 

}
