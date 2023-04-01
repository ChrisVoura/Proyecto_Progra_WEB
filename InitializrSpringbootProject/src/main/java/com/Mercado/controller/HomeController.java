
package com.Mercado.controller;

import com.Mercado.entity.Producto;
import com.Mercado.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alimentos")
public class HomeController {
    @Autowired
    private IProductoService productoservice;
    @GetMapping("")
    public String home(Model model){
       
        model.addAttribute("productos", productoservice.findAll());
        return "usuario/home";
    }
    
   @GetMapping("/detalleProducto/{id}")
    public String detalleProducto(@PathVariable("id") Integer id, Model model){
           Producto producto= productoservice.getProductoById(id);
         model.addAttribute("titulo", "Detalle Productos");
         model.addAttribute("producto", producto);
        return"usuario/detalleProducto";
    }
    @PostMapping("/cart")
    public String addCart(){
       
        
        return"usuario/carrito";
    }
    
}
