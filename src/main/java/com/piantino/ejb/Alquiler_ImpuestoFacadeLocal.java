/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Impuesto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Alquiler_ImpuestoFacadeLocal {

    void create(Alquiler_Impuesto alquiler_Impuesto);

    void edit(Alquiler_Impuesto alquiler_Impuesto);

    void remove(Alquiler_Impuesto alquiler_Impuesto);

    Alquiler_Impuesto find(Object id);

    List<Alquiler_Impuesto> findAll();

    List<Alquiler_Impuesto> findRange(int[] range);

    int count();
    
    List<Alquiler_Impuesto> buscarImpuestosAlquiler(int idAlquiler);
    
    void eliminarPorAlquiler(int idAlquiler);
    
    void saveListaImpuestoDefinitiva(List<Alquiler_Impuesto> listaDefinitiva, int idAlquiler);
    
}
