package ar.edu.iua.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.business.IOportunidadVentaBusiness;
import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.model.OportunidadVenta;
import ar.edu.iua.model.dto.MensajeRespuesta;

@RestController
@RequestMapping(value = Constantes.URL_OPORTUNIDAD_VENTAS)
public class OportunidadDeVentaController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOportunidadVentaBusiness ovBusiness;
	
	@PostMapping(value = "/integracion", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MensajeRespuesta> load(@RequestBody OportunidadVenta oportunidadVenta) {

		try {
			MensajeRespuesta r = ovBusiness.recibir(oportunidadVenta).getMensaje();
			if (r.getCodigo() == 0) {
				return new ResponseEntity<MensajeRespuesta>(r, HttpStatus.OK);
			} else {
				return new ResponseEntity<MensajeRespuesta>(r, HttpStatus.BAD_REQUEST);

			}
		} catch (BusinessException e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<MensajeRespuesta>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
