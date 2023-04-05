
package com.Mercado.repository;

import com.Mercado.entity.Orden;
import com.Mercado.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Orden,Integer>{
    List<Orden>findByUsuario(Usuario usuario);
}
