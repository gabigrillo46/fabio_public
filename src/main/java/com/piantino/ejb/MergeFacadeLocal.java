/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Merge;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface MergeFacadeLocal {

    void create(Merge merge);

    void edit(Merge merge);

    void remove(Merge merge);

    Merge find(Object id);

    List<Merge> findAll();

    List<Merge> findRange(int[] range);
    
    List<Merge> buscarPorFiltro(String nombre, Date fechaDesde, Date fechaHasta);
    
    Merge buscarPorNombre(String nombre);

    int count();
    
}
