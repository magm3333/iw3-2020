package ar.edu.iua.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.eventos.ProductoEvent;
import ar.edu.iua.model.Producto;
import ar.edu.iua.model.persistence.ProductoRepository;

@Service
public class ProductoBusiness implements IProductoBusiness {

	@Autowired
	private ProductoRepository productoDAO;

	@Override
	public Producto load(Long id) throws NotFoundException, BusinessException {
		Optional<Producto> op;
		try {
			op = productoDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!op.isPresent()) {
			throw new NotFoundException("El producto con id " + id + " no se encuentra en la BD");
		}
		return op.get();
	}

	@Override
	public List<Producto> list() throws BusinessException {
		try {
			return productoDAO.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Producto add(Producto producto) throws BusinessException {
		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(Long id) throws NotFoundException, BusinessException {
		try {
			productoDAO.deleteById(id);
		} catch (EmptyResultDataAccessException e1) {
			throw new NotFoundException("No se encuentra el producto id=" + id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public Producto update(Producto producto) throws NotFoundException, BusinessException {
		Producto old = load(producto.getId());
		Producto p = producto;
		if (producto.getFoto() != null && producto.getFotoMimeType() != null && producto.getFoto().length > 0) {
			// Mejorar
		} else if (old.getFoto() != null && old.getFoto().length > 0) {
			p = producto;
			p.setFoto(old.getFoto());
			p.setFotoMimeType(old.getFotoMimeType());
		}

		if (old.getPrecioLista() < producto.getPrecioLista() * .9) {
			generaEvento(producto, ProductoEvent.Tipo.SUBE_PRECIO);
		}

		return add(p);
	}

	@Autowired
	private ApplicationEventPublisher appEventPublisher;

	private void generaEvento(Producto producto, ProductoEvent.Tipo tipo) {
		appEventPublisher.publishEvent(new ProductoEvent(producto, tipo));
	}

	@Override
	public List<Producto> list(String parte) throws BusinessException {
		try {
			return productoDAO.findByNombreContainingOrDescripcionContainingOrderByNombreDesc(parte, parte);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Producto setFoto(Long id, MultipartFile file) throws NotFoundException, BusinessException {
		Producto p = load(id);
		try {
			p.setFoto(file.getBytes());
			p.setFotoMimeType(file.getContentType());
			return productoDAO.save(p);
		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public Producto load(String codigoExterno) throws NotFoundException, BusinessException {
		Optional<Producto> op;
		try {
			op = productoDAO.findFirstByCodigoExterno(codigoExterno);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if (!op.isPresent()) {
			throw new NotFoundException(
					"El producto con código externo " + codigoExterno + " no se encuentra en la BD");
		}
		return op.get();
	}

	@Override
	public Producto asegurarProducto(Producto producto) throws BusinessException {
		Producto p = null;
		try {
			p = load(producto.getCodigoExterno());
			p.setNombre(producto.getNombre());
			p.setPrecioLista(producto.getPrecioLista());
			// Colocar aquí los datos recibidos no opcionales
		} catch (NotFoundException e) {
			p = new Producto(producto);
		}
		return productoDAO.save(p);
	}

}

//@Autowired
//private IProductoBusiness productoBusiness
