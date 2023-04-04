
package com.Mercado.controller;

import com.Mercado.entity.DetalleOrden;
import com.Mercado.entity.Orden;
import com.Mercado.entity.Producto;
import com.Mercado.entity.Usuario;
import com.Mercado.service.IDetalleOrden;
import com.Mercado.service.IOrdenService;
import com.Mercado.service.IProductoService;
import com.Mercado.service.IUsuarioService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private IUsuarioService usuarioservice;
    @Autowired
    private IOrdenService  ordenservice;      
    @Autowired
    private IDetalleOrden detalleordenservice;
   
    List<DetalleOrden> detalles=new ArrayList<DetalleOrden>();
    
    Orden orden=new Orden();
    
    @GetMapping("")
    public String home(Model model, HttpSession session){       
        model.addAttribute("productos", productoservice.findAll());
        model.addAttribute("sesion", session.getAttribute("idusuario"));
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
    public String getCart(Model model, HttpSession session){ 
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("sesion", session.getAttribute("idusuario"));
      return "/usuario/carrito";  
    }
    @GetMapping("/orden")
    public String orden(Model model, HttpSession session){
        Usuario usuario= usuarioservice.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
        
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return"usuario/Rorden";
    }
    @GetMapping("/saveOrden")
    public String saveOrden(HttpSession session){
        Date fechaCreacion=new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenservice.generarNO());
        
        //Usuario
        Usuario usuario= usuarioservice.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
        orden.setUsuario(usuario);
        ordenservice.save(orden);
        //Detalles
        for(DetalleOrden dt: detalles){
          dt.setOrden(orden);
          detalleordenservice.save(dt);
        }
        
        orden=new Orden();
        detalles.clear();
        return "redirect:/";
    }
    
    @PostMapping("/search")
    public String searchP(@RequestParam String nombre, Model model){
        List<Producto> productos= productoservice.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "usuario/home";
    }
}
