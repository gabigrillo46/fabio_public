/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Auto;
import com.piantino.model.Auto_precios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Auto_preciosFacadeLocal {

    void create(Auto_precios auto_precios);

    void edit(Auto_precios auto_precios);

    void remove(Auto_precios auto_precios);

    Auto_precios find(Object id);

    List<Auto_precios> findAll();

    List<Auto_precios> findRange(int[] range);
    
    List<Auto_precios> getPrecioAutosPorAuto(int idAuto);
    
    void saveListaAutoPrecioDefinitiva(List<Auto_precios> listaPrecioAuto, Auto auto);

    int count();
    
    Auto_precios getPrecioDefectoPrimeroAuto(int idAuto);
    
}
