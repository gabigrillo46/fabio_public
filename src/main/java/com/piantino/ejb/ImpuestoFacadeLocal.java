/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Impuesto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface ImpuestoFacadeLocal {

    void create(Impuesto impuesto);

    void edit(Impuesto impuesto);

    void remove(Impuesto impuesto);

    Impuesto find(Object id);
    
    Impuesto getImpuestoParaToll();
    
    Impuesto getImpuestoParaLatePayment();

    List<Impuesto> findAll();

    List<Impuesto> findRange(int[] range);

    int count();
    
    Impuesto buscarImpusetoPorNomber(String nombre) throws Exception; 
    
    List<Impuesto> buscarImpuestosAuto(int idSucursal) throws Exception;
    
    
    
    
    
}
