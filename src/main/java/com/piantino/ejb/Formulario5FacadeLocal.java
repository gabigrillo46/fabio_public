/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Formulario5;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Formulario5FacadeLocal {

    void create(Formulario5 formulario5);

    void edit(Formulario5 formulario5);

    void remove(Formulario5 formulario5);

    Formulario5 find(Object id);

    List<Formulario5> findAll();

    List<Formulario5> findRange(int[] range);

    int count();
    
    List<Formulario5> getListaFormularioAuto(int idAuto);
    
    Formulario5 getFormularioDeVenta(long idVenta);
    
    
}
