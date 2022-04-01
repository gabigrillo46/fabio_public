/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Auto_imagenes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Auto_imagenesFacadeLocal {

    void create(Auto_imagenes auto_imagenes);

    void edit(Auto_imagenes auto_imagenes);

    void remove(Auto_imagenes auto_imagenes);

    Auto_imagenes find(Object id);

    List<Auto_imagenes> findAll();

    List<Auto_imagenes> findRange(int[] range);
    
    List<Auto_imagenes> getListaPorAuto(int idAuto);

    int count();
    
}
