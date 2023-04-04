
package com.Mercado.service;

import com.Mercado.entity.Orden;
import java.util.List;


public interface IOrdenService {
      List<Orden> findAll();
    Orden save(Orden orden);
       String generarNO();
}
