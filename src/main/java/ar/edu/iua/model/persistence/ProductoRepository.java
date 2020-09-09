package ar.edu.iua.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.model.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByDescripcion(String descripcionProducto);

    Optional<Producto> findByDescripcionContains(String descripcionProducto);

    Optional<Producto> findByPrecioListaAfter(double precio);


}
