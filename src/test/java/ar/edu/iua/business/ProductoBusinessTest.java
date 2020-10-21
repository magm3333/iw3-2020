package ar.edu.iua.business;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.Producto;
import ar.edu.iua.model.Proveedor;
import ar.edu.iua.model.persistence.ProductoRepository;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductoBusinessTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductoBusiness productoBusiness;


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();



    @Test
    public void testLoadSuccess() throws BusinessException, NotFoundException {
        long id = 5;
        assertEquals("sopa knorr", productoBusiness.load(id).getDescripcion());
    }

    @Test
    public void testLoadFailure() throws  BusinessException, NotFoundException  {
        long id = 5;
        assertNotEquals("Descripcion distinta", productoBusiness.load(id).getDescripcion());
    }


    @Test(expected = ar.edu.iua.business.exception.NotFoundException.class)
    public void testLoadNotFoundException() throws  BusinessException, NotFoundException  {
        long id = 128;
        productoBusiness.load(id);
        expectedEx.expect(ar.edu.iua.business.exception.NotFoundException.class);
        expectedEx.expectMessage("No se encuentra el producto con id="+id);
    }
/*
    @Test(expected = ar.edu.iua.business.exception.BusinessException.class)
    public void testFailureLoadNotFoundException() throws  BusinessException, NotFoundException  {
        long id = 128;
        productoBusiness.load(id);
        expectedEx.expect(ar.edu.iua.business.exception.BusinessException.class);
        expectedEx.expectMessage("No se encuentra el producto con id="+id);
    }
*/
}
