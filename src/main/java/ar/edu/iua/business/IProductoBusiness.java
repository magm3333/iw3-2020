package ar.edu.iua.business;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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

	public Producto setFoto(Long id, MultipartFile file) throws NotFoundException, BusinessException;

	public Producto load(String codigoExterno) throws NotFoundException, BusinessException;

	public Producto asegurarProducto(Producto producto) throws BusinessException;

}
