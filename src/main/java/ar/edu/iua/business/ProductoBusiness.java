package ar.edu.iua.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
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
			op=productoDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		if(!op.isPresent()) {
			throw new NotFoundException("El producto con id "+id+" no se encuentra en la BD");
		}
		return op.get();
	}

	@Override
	public List<Producto> list() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Producto save(Producto producto) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws NotFoundException, BusinessException {
		// TODO Auto-generated method stub

	}

}

//@Autowired
//private IProductoBusiness productoBusiness
