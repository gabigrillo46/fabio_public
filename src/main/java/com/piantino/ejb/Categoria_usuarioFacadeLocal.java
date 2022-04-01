/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Categoria_usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Categoria_usuarioFacadeLocal {

    void create(Categoria_usuario categoria_usuario);

    void edit(Categoria_usuario categoria_usuario);

    void remove(Categoria_usuario categoria_usuario);

    Categoria_usuario find(Object id);

    List<Categoria_usuario> findAll();

    List<Categoria_usuario> findRange(int[] range);

    int count();
    
}
