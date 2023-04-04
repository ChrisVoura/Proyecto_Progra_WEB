
package com.Mercado.controller;


import com.Mercado.entity.Producto;
import com.Mercado.entity.Usuario;
import com.Mercado.service.IProductoService;
import com.Mercado.service.IUsuarioService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private IProductoService productoservice; 
   @Autowired
    private IUsuarioService usuarioservice;
    
    @GetMapping("")
    public String Mostrar(Model model){
    List<Producto> listaP =  productoservice.findAll();
        model.addAttribute("titulo", "Lista Productos");
        model.addAttribute("productos", listaP );
        return "productos/Mostrar";
    }
    @GetMapping("/crear")
    public String crear(Model model){
         model.addAttribute("producto", new Producto());  
         
        return "productos/crear";
    }
     @PostMapping("/save")
    public String save(Producto producto,@RequestParam("file")MultipartFile imagen, HttpSession session){
        Usuario u= usuarioservice.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
        producto.setUsuario(u);
        if(!imagen.isEmpty()){
            Path directorioImg= Paths.get("src//main//resources//static/images");
            String rutaAbsoluta=directorioImg.toFile().getAbsolutePath();
            try {
                byte[] bytesImg=imagen.getBytes();
                Path rutaCompleta= Paths.get( rutaAbsoluta+"//"+imagen.getOriginalFilename());
                Files.write(rutaCompleta,bytesImg);
                
                producto.setImagen(imagen.getOriginalFilename());
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
        productoservice.save(producto);
        return "redirect:/productos";
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model){
           Producto producto= productoservice.getProductoById(id);
         model.addAttribute("producto", producto);
        return"productos/editar";
    }
    @PostMapping("/update")
    public String update(Producto producto,@RequestParam("file")MultipartFile imagen){
    Usuario u= new Usuario(1,"","","","","","","");
        if(!imagen.isEmpty()){
            Path directorioImg= Paths.get("src//main//resources//static/images");
            String rutaAbsoluta=directorioImg.toFile().getAbsolutePath();
            try {
                byte[] bytesImg=imagen.getBytes();
                Path rutaCompleta= Paths.get( rutaAbsoluta+"//"+imagen.getOriginalFilename());
                Files.write(rutaCompleta,bytesImg);
                
                producto.setImagen(imagen.getOriginalFilename());
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
        producto.setUsuario(u);
        productoservice.update(producto);
        return "redirect:/productos";
    }
 
    @GetMapping("/delete/{id}")
   public String eliminarEvento(@PathVariable Integer id){
       productoservice.delete(id);
       return "redirect:/productos";
   }
}
