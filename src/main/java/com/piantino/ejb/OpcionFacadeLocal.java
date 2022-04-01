/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Opcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface OpcionFacadeLocal {

    void create(Opcion opcion);

    void edit(Opcion opcion);

    void remove(Opcion opcion);

    Opcion find(Object id);

    List<Opcion> findAll();

    List<Opcion> findRange(int[] range);

    int count();
    
}
