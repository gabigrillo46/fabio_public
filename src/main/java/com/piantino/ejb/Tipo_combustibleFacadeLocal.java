/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_combustible;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Tipo_combustibleFacadeLocal {

    void create(Tipo_combustible tipo_combustible);

    void edit(Tipo_combustible tipo_combustible);

    void remove(Tipo_combustible tipo_combustible);

    Tipo_combustible find(Object id);

    List<Tipo_combustible> findAll();

    List<Tipo_combustible> findRange(int[] range);

    int count();
    
}
