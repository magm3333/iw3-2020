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
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductoBusinessTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductoBusiness productoBusiness;


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @MockBean
    ProductoRepository productDAOMock;

    private static String description = "Lomito2";
    private static Producto prod1;


    @BeforeClass
    public static void setup() {

        prod1 = new Producto();
        prod1.setId(15L);
        prod1.setDescripcion(description) ;
        prod1.setPrecioLista(125.67);
        prod1.setEnStock(true);
        Proveedor prov = new Proveedor();
        prov.setNombre("NuevoProveedor1");
        prod1.setProveedor(prov);

    }

    @Test
    public void testLoadSuccess() throws BusinessException, NotFoundException {
        long id = 15L;

         //  assertEquals("sopa knorr", productoBusiness.load(id).getDescripcion());


        Mockito.when(productDAOMock.findById(id)).thenReturn(Optional.ofNullable(prod1));

        Producto prodReceived = productoBusiness.load(id);


        assertEquals(prod1.getDescripcion(), prodReceived.getDescripcion());

    }

    @Test
    public void testLoadFailure() throws  BusinessException, NotFoundException  {
        long id = 15L;

        Mockito.when(productDAOMock.findById(id)).thenReturn(Optional.ofNullable(prod1));

        Producto prodReceived = productoBusiness.load(id);
        assertNotEquals("Descripcion distinta", prodReceived.getDescripcion());
    }


    @Test(expected = ar.edu.iua.business.exception.NotFoundException.class)
    public void testLoadNotFoundException() throws  BusinessException, NotFoundException  {
        long id = 128;
        productoBusiness.load(id);
        expectedEx.expect(ar.edu.iua.business.exception.NotFoundException.class);
        expectedEx.expectMessage("No se encuentra el producto con id="+id);
    }

 /*   @Test(expected = NumberFormatException.class)
    public void testFailureLoadNotFoundException() throws  BusinessException, NumberFormatException  {
        long id = 128;
        productoBusiness.load(id);
        expectedEx.expect(NumberFormatException.class);
        expectedEx.expectMessage("No se encuentra el producto con id="+id);
    }
*/

    @Test
    public void testfindByPrecioListaAfter() throws BusinessException, NotFoundException {
        double price  = 120;

        when(productDAOMock.findByPrecioListaAfter(price)).thenReturn(Optional.ofNullable(prod1));

        Producto prodReceived = productoBusiness.findByPrecioListaAfter(price);

        assertSame( prod1.getId(), prodReceived.getId());
        assertSame(prod1.getDescripcion(), prodReceived.getDescripcion());
        assertTrue( prodReceived.getPrecioLista() == prod1.getPrecioLista());

    }

}
