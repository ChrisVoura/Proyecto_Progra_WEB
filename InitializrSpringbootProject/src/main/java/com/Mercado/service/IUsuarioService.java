
package com.Mercado.service;

import com.Mercado.entity.Usuario;
import java.util.Optional;


public interface IUsuarioService {
    Usuario findById(Integer id);
    Usuario save(Usuario usuario);
    Optional<Usuario>findByEmail(String email);
}
