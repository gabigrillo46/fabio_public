/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Publicidad;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface PublicidadFacadeLocal {

    void create(Publicidad publicidad);

    void edit(Publicidad publicidad);

    void remove(Publicidad publicidad);

    Publicidad find(Object id);

    List<Publicidad> findAll();

    List<Publicidad> findRange(int[] range);

    int count();
    
}
