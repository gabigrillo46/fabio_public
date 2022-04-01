/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Invoice_Mecanico_Detalle;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Invoice_Mecanico_DetalleFacadeLocal {

    void create(Invoice_Mecanico_Detalle invoice_Mecanico_Detalle);

    void edit(Invoice_Mecanico_Detalle invoice_Mecanico_Detalle);

    void remove(Invoice_Mecanico_Detalle invoice_Mecanico_Detalle);

    Invoice_Mecanico_Detalle find(Object id);

    List<Invoice_Mecanico_Detalle> findAll();

    List<Invoice_Mecanico_Detalle> findRange(int[] range);

    int count();

    List<Invoice_Mecanico_Detalle> buscarPorFiltro(String rego, int tipo, Date fechaDesde, Date fechaHasta, String invoiceNumber);

    List<Invoice_Mecanico_Detalle> listaDetallePorIdInvoice(long idInvoice);
    
    void saveListaDetalleInvoiceDefinitiva(List<Invoice_Mecanico_Detalle> listaDetalleInvoice, Invoice_Mecanico invoiceMecanico);
    
    List<Invoice_Mecanico_Detalle> listaDetallePorIdAuto(int idAuto);
    
    List<Invoice_Mecanico_Detalle> listaDetallePorIdCliente(int idCliente);
    
    float getExpensesDelAuto(int idAuto);
    
    float getExpensesDelCliente(int idCliente);
}
