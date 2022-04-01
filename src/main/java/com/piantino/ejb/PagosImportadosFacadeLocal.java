/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.PagoImportado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface PagosImportadosFacadeLocal {

    void create(PagoImportado pagosImportados);

    void edit(PagoImportado pagosImportados);

    void remove(PagoImportado pagosImportados);

    PagoImportado find(Object id);

    List<PagoImportado> findAll();

    List<PagoImportado> findRange(int[] range);

    int count();
    
    PagoImportado buscarPorNumeroRecibo(String numeroRecibo);
    
}
