/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.State;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface StateFacadeLocal {

    void create(State state);

    void edit(State state);

    void remove(State state);

    State find(Object id);

    List<State> findAll();

    List<State> findRange(int[] range);

    int count();
    
    State getStatePorNombre(String nombre);
    
}
