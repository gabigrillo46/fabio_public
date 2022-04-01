/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Invoice;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface InvoiceFacadeLocal {

    void create(Invoice invoice);

    void edit(Invoice invoice);

    void remove(Invoice invoice);

    Invoice find(Object id);

    List<Invoice> findAll();

    List<Invoice> findRange(int[] range);

    int count();
    
}
