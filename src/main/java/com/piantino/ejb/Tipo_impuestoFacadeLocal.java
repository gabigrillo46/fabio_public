/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_impuesto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Tipo_impuestoFacadeLocal {

    void create(Tipo_impuesto tipo_impuesto);

    void edit(Tipo_impuesto tipo_impuesto);

    void remove(Tipo_impuesto tipo_impuesto);

    Tipo_impuesto find(Object id);

    List<Tipo_impuesto> findAll();

    List<Tipo_impuesto> findRange(int[] range);

    int count();
    
}
