
package com.Mercado.service;

import com.Mercado.entity.DetalleOrden;
import com.Mercado.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenService implements IDetalleOrden{

    @Autowired
    private DetalleRepository detalleordenR;
            
    @Override
    public DetalleOrden save(DetalleOrden detalleorden) {
       return detalleordenR.save(detalleorden);
    }
    
}
