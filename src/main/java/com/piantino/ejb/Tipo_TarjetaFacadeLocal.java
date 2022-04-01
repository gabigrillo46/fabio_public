/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_Tarjeta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Tipo_TarjetaFacadeLocal {

    void create(Tipo_Tarjeta tipo_Tarjeta);

    void edit(Tipo_Tarjeta tipo_Tarjeta);

    void remove(Tipo_Tarjeta tipo_Tarjeta);

    Tipo_Tarjeta find(Object id);

    List<Tipo_Tarjeta> findAll();

    List<Tipo_Tarjeta> findRange(int[] range);

    int count();
    
}
