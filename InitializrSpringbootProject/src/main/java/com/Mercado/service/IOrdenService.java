
package com.Mercado.service;

import com.Mercado.entity.Orden;
import com.Mercado.entity.Usuario;
import java.util.List;


public interface IOrdenService {
      List<Orden> findAll();
    Orden save(Orden orden);
       String generarNO();
     List<Orden> findByUsuario(Usuario usuario);
}
