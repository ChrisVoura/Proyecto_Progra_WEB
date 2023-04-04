
package com.Mercado.controller;

import com.Mercado.entity.Usuario;
import com.Mercado.service.IUsuarioService;
import static com.mysql.cj.conf.PropertyKey.logger;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger logger= LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private IUsuarioService usuarioservice;
    
    @GetMapping("/registro")
    public String crear(){
      return"usuario/registro";  
    }
    @PostMapping("/save")
    public String save(Usuario usuario){
        usuario.setTipo("USER");
        usuarioservice.save(usuario);
     return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
       
        return "usuario/login";
    }
    @PostMapping("/access")
    public String access(Usuario usuario, HttpSession session){
         Optional<Usuario> u= usuarioservice.findByEmail(usuario.getEmail());
         if(u.isPresent()){
         session.setAttribute("idusuario", u.get().getId());
         if(u.get().getTipo().equals("ADMIN")){
             return "redirect:/administrador";  
         }else{
             return"redirect:/";
         }
         }else{
             logger.info("Usuario no existe");
         }
        return "redirect:/";
    }
}
