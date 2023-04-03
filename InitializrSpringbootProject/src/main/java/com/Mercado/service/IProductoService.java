
package com.Mercado.service;

import com.Mercado.entity.Producto;
import java.util.List;



public interface IProductoService {
    
    public Producto save(Producto producto);
    public Producto getProductoById (Integer id);
    public void update(Producto producto);
    public void delete(Integer id);
    public List<Producto> findAll();
}
