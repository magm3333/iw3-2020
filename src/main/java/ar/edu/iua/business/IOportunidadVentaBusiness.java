package ar.edu.iua.business;



import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.model.OportunidadVenta;
import ar.edu.iua.model.dto.RespuestaGenerica;

public interface IOportunidadVentaBusiness {
	public RespuestaGenerica<OportunidadVenta> recibir(OportunidadVenta oportunidadVenta) throws BusinessException;
}
