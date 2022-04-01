/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_auto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Tipo_autoFacadeLocal {

    void create(Tipo_auto tipo_auto);

    void edit(Tipo_auto tipo_auto);

    void remove(Tipo_auto tipo_auto);

    Tipo_auto find(Object id);

    List<Tipo_auto> findAll();

    List<Tipo_auto> findRange(int[] range);

    int count();
    
    Tipo_auto buscarPorNombre(String nombre)throws Exception;
    
}
