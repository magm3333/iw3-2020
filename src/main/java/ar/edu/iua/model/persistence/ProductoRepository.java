package ar.edu.iua.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}


