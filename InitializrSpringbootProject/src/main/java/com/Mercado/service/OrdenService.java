
package com.Mercado.service;

import com.Mercado.entity.Orden;
import com.Mercado.entity.Usuario;
import com.Mercado.repository.OrdenRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenService implements IOrdenService{
    @Autowired
    private OrdenRepository ordenrepository;
    
    
    @Override
    public Orden save(Orden orden) {
        
        return ordenrepository.save(orden);
        
    }

    @Override
    public List<Orden> findAll() {
     return ordenrepository.findAll();
    }
    
    @Override
    public String generarNO(){
      int num=0;
      String numeroCon=""; 
      
      List<Orden> ordenes = findAll();
      List<Integer> numeros=new ArrayList<Integer>();
        
      ordenes.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
      
      if(ordenes.isEmpty()){
          num=1;
      }else{
          num= numeros.stream().max(Integer::compare).get();
          num++;
      }
        if (num < 10) { //000001
            numeroCon = "00000" + String.valueOf(num);
        } else if (num < 100) {
            numeroCon = "0000" + String.valueOf(num);
        } else if (num < 1000) {
            numeroCon = "000" + String.valueOf(num);
        } else if (num < 10000) {
            numeroCon = "00" + String.valueOf(num);
        }
      
      
        return numeroCon;
    }

    @Override
    public List<Orden> findByUsuario(Usuario usuario) {
       return ordenrepository.findByUsuario(usuario);
    }
}
