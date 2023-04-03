
package com.Mercado.controller;

import com.Mercado.entity.DetalleOrden;
import com.Mercado.entity.Orden;
import com.Mercado.entity.Producto;
import com.Mercado.service.IProductoService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private IProductoService productoservice;
   
    List<DetalleOrden> detalles=new ArrayList<DetalleOrden>();
    
    Orden orden=new Orden();
    
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
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
         DetalleOrden  dOrden= new DetalleOrden(); 
         double sumaT=0.0;
       Producto producto= productoservice.getProductoById(id);
       dOrden.setCantidad(cantidad);
       dOrden.setPrecio(producto.getPrecio());
       dOrden.setNombre(producto.getNombre());
       dOrden.setTotal(producto.getPrecio()*cantidad);
       dOrden.setProducto(producto);
       
        Integer idProducto= producto.getId();
         boolean ingresado= detalles.stream().anyMatch(p -> p.getProducto().getId()== idProducto);
         if(!ingresado){
             detalles.add(dOrden);
         }
        sumaT = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaT);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }
    @GetMapping("/delete/cart/{id}")
    public String deletecart(@PathVariable Integer id, Model model){
        List<DetalleOrden> ordenN= new ArrayList<DetalleOrden>();
        
        for(DetalleOrden dO: detalles){
            if(dO.getProducto().getId()!= id){
                ordenN.add(dO);
            }
        }
        detalles=ordenN;
        double sumaT=0.0;
        
        sumaT = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaT);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        
        return"usuario/carrito";
    }
    
    @GetMapping("/getCart")
    public String getCart(Model model){
       
        
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
      return "/usuario/carrito";  
    }
    @GetMapping("/orden")
    public String orden(){
        return"usuario/Rorden";
    }
    
}
