
package com.Mercado.service;

import com.Mercado.entity.Usuario;
import com.Mercado.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private UsuarioRepository usuariorepository;
    
    @Override
    public Usuario findById(Integer id) {
       return usuariorepository.getOne(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
       return usuariorepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
       return usuariorepository.findByEmail(email);
    }

    @Override
    public List<Usuario> findAll() {
        return usuariorepository.findAll();
    }

  
    
}
