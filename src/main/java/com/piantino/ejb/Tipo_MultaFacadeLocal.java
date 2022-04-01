/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_multa;
import java.util.List;
import javax.ejb.Local;

@Local
public interface Tipo_MultaFacadeLocal {

    void create(Tipo_multa tipo_pago);

    void edit(Tipo_multa tipo_pago);

    void remove(Tipo_multa tipo_pago);

    Tipo_multa find(Object id);

    List<Tipo_multa> findAll();

    List<Tipo_multa> findRange(int[] range);

    int count();
    
}
