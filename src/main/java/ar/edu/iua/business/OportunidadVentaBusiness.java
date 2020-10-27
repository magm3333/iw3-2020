package ar.edu.iua.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.model.OportunidadVenta;
import ar.edu.iua.model.dto.MensajeRespuesta;
import ar.edu.iua.model.dto.RespuestaGenerica;
import ar.edu.iua.model.persistence.OportunidadVentaRepository;

@Service
public class OportunidadVentaBusiness implements IOportunidadVentaBusiness {

	@Autowired
	private IProductoBusiness productoService;

	@Autowired
	private OportunidadVentaRepository oportunidadVentaDAO;
	
	@Override
	public RespuestaGenerica<OportunidadVenta> recibir(OportunidadVenta oportunidadVenta) throws BusinessException {

		MensajeRespuesta m = new MensajeRespuesta();
		RespuestaGenerica<OportunidadVenta> r = new RespuestaGenerica<OportunidadVenta>(oportunidadVenta, m);

		String mensajeCheck = oportunidadVenta.checkBasicData();
		if (mensajeCheck != null) {
			m.setCodigo(-1);
			m.setMensaje(mensajeCheck);
			return r;
		}

		try {
			oportunidadVenta.setProducto(productoService.asegurarProducto(oportunidadVenta.getProducto()));
			oportunidadVenta.setFechaHora(new Date());
			oportunidadVenta.setConcretada(false);
			oportunidadVentaDAO.save(oportunidadVenta);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

		return r;
	}

}
