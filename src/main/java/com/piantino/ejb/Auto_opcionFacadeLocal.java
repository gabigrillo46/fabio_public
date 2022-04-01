/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Auto;
import com.piantino.model.Auto_opcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Auto_opcionFacadeLocal {

    void create(Auto_opcion auto_opcion);

    void edit(Auto_opcion auto_opcion);

    void remove(Auto_opcion auto_opcion);

    Auto_opcion find(Object id);

    List<Auto_opcion> findAll();

    List<Auto_opcion> findRange(int[] range);

    int count();

    void saveListaAutoPrecioDefinitiva(List<Auto_opcion> listaOpcionAuto, Auto auto);
    
    List<Auto_opcion> getOpcionesPorAuto(Auto auto);

}
