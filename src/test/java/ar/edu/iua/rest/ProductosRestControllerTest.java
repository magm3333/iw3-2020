package ar.edu.iua.rest;


import ar.edu.iua.business.ProductoBusiness;
import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.model.Producto;
import ar.edu.iua.model.Proveedor;
import ar.edu.iua.model.persistence.ProductoRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductosRestController.class)
public class ProductosRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductoRepository productoDAO;

    @MockBean
    private ProductoBusiness productoBusiness;

    private static Producto prod1;


    @BeforeClass
    public static void setup() {

        prod1 = new Producto();
        prod1.setId(5L);
        prod1.setDescripcion("Lomito2") ;
        prod1.setPrecioLista(125.67);
        prod1.setEnStock(true);
        Proveedor prov = new Proveedor();
        prov.setNombre("NuevoProveedor2");
        prod1.setProveedor(prov);

    }


    @Test
    public void testListSuccess()
            throws BusinessException, Exception {

        List<Producto> allProducts = new ArrayList<Producto>();
        allProducts.add(prod1);


        when(productoBusiness.list()).thenReturn(allProducts);

        mvc.perform(get("/api/v1/productos/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(prod1.getId().intValue())));
    }

    @Test
    public void testLoadByDescription()
            throws BusinessException, Exception {

        Producto productoDescContains = prod1;
        String description = "mito";


        when(productoBusiness.findByDescripcionContains(description)).thenReturn(productoDescContains);

        mvc.perform(get("/api/v1/productos/description")
                .param("desc", description)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.id"),  is(prod1.getId()), Long.class))
                .andExpect(jsonPath("$.descripcion", is(prod1.getDescripcion())));

    }
}
