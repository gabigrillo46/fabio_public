/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_pago;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Tipo_pagoFacadeLocal {

    void create(Tipo_pago tipo_pago);

    void edit(Tipo_pago tipo_pago);

    void remove(Tipo_pago tipo_pago);

    Tipo_pago find(Object id);

    List<Tipo_pago> findAll();

    List<Tipo_pago> findRange(int[] range);

    int count();
    
    Tipo_pago getTipoPagoToll();
    
    Tipo_pago getTipoPagoLatePayment();
    
}
