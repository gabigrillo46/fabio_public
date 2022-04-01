/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Modelo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface ModeloFacadeLocal {

    void create(Modelo modelo);

    void edit(Modelo modelo);

    void remove(Modelo modelo);

    Modelo find(Object id);

    List<Modelo> findAll();

    List<Modelo> findRange(int[] range);

    int count();
    
    Modelo buscarPorNombre(String nombre, int idCategoria)throws Exception;
    
    List<Modelo> buscarPorMarca(int idMarca);
    
}
