package ar.edu.iua.business;

import java.util.List;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;

public interface IProductoBusiness {

	public Producto load(Long id) throws NotFoundException, BusinessException;

	public List<Producto> list() throws BusinessException;
	
	public List<Producto> list(String parte) throws BusinessException;

	public Producto add(Producto producto) throws BusinessException;
	
	public Producto update(Producto producto) throws NotFoundException, BusinessException;

	public void delete(Long id) throws NotFoundException, BusinessException;
}
