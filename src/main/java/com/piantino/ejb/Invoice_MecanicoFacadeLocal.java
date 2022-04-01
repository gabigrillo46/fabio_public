/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Invoice_Mecanico;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Invoice_MecanicoFacadeLocal {

    void create(Invoice_Mecanico invoice_Mecanico);

    void edit(Invoice_Mecanico invoice_Mecanico);

    void remove(Invoice_Mecanico invoice_Mecanico);

    Invoice_Mecanico find(Object id);

    List<Invoice_Mecanico> findAll();

    List<Invoice_Mecanico> findRange(int[] range);

    int count();
    
    Invoice_Mecanico getInvoicePorProveedorNumero(int idProveedor, String numero);
    
    Invoice_Mecanico getInvoiceIngresoPorNumero(String numero);
    
    
    
}
