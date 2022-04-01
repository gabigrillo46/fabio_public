/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Witness;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface WitnessFacadeLocal {

    void create(Witness witness);

    void edit(Witness witness);

    void remove(Witness witness);

    Witness find(Object id);

    List<Witness> findAll();

    List<Witness> findRange(int[] range);

    int count();
    
    Witness buscarPorNombre(String nombre);
    
}
