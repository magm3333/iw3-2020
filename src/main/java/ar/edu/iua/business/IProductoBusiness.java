package ar.edu.iua.business;

import java.util.List;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;

public interface IProductoBusiness {

	public Producto load(Long id) throws BusinessException, NotFoundException;

	public List<Producto> list() throws BusinessException;

	public Producto save(Producto producto) throws BusinessException;

	public void delete(Long id) throws BusinessException, NotFoundException;

}
