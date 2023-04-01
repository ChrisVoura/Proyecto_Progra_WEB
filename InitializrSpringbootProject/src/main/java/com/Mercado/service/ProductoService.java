
package com.Mercado.service;

import com.Mercado.entity.Producto;
import com.Mercado.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoService{

    @Autowired
      private ProductoRepository productorepository;
    
    @Override
    public Producto save(Producto producto) {
       return productorepository.save(producto);
    }
    @Override
    public void update(Producto producto) {
      productorepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productorepository.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
     return productorepository.findAll();
    }

    @Override
    public Producto getProductoById(Integer id) {
     return productorepository.findById(id).get();
    }
    
}
