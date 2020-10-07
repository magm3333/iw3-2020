package ar.edu.iua.model.persistence;

import ar.edu.iua.model.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.iua.model.Producto;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByDescripcion(String descripcionProducto);

    Optional<Producto> findByDescripcionContains(String descripcionProducto);

    Optional<Producto> findByPrecioListaAfter(double precio);

    List<Producto> findByIngredienteListDescripcionIngrediente(String descripcionIngrediente);

  //  Page<Producto> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM productos where nombre like \"%galleta%\"", nativeQuery = true)
    public List<Producto> findNombreLikeGalleta();

    @Query(value = "select * from productos p inner join producto_ingrediente_detalle pid \n" +
            "on p.id = pid.producto_id\n" +
            "inner join ingredientes i on pid.ingrediente_id = i.id\n" +
            "where\n" +
            "i.descripcion = ?1", nativeQuery = true)
    public List<Producto> findProductoIngredienteHarina(String ingrediente);


    @Query(value = "select * from productos ORDER BY rand()",
            countQuery = "SELECT count(*) FROM productos ",
            nativeQuery = true)
    Page<Producto> findAll(Pageable pageable);


     List<Producto> findByElPrecio2(double price);

    @Query(nativeQuery = true)
    List<ProductoDTO> findByElPrecio(double price);


}
