/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.report.Dtos;

import com.lowagie.text.pdf.ArabicLigaturizer;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class ReporteCompleto {
    
    List<Form5Detalle> listaTopCopy;
    List<Form5Detalle> listaRmsCopy;
    List<Form5Detalle> listaCustomerCopy;
    List<Form5Detalle> listaFileCopy;
    List<InvoiceDetalle> listaInvoice;
    List<InvoiceDetalle> listaContrato;
    List<InvoiceDetalle> lista_transferencia;
    

    public List<Form5Detalle> getListaTopCopy() {
        return listaTopCopy;
    }

    public void setListaTopCopy(List<Form5Detalle> listaTopCopy) {
        this.listaTopCopy = listaTopCopy;
    }

    public List<Form5Detalle> getListaRmsCopy() {
        return listaRmsCopy;
    }

    public void setListaRmsCopy(List<Form5Detalle> listaRmsCopy) {
        this.listaRmsCopy = listaRmsCopy;
    }

    public List<Form5Detalle> getListaCustomerCopy() {
        return listaCustomerCopy;
    }

    public void setListaCustomerCopy(List<Form5Detalle> listaCustomerCopy) {
        this.listaCustomerCopy = listaCustomerCopy;
    }

    public List<Form5Detalle> getListaFileCopy() {
        return listaFileCopy;
    }

    public void setListaFileCopy(List<Form5Detalle> listaFileCopy) {
        this.listaFileCopy = listaFileCopy;
    }

    public List<InvoiceDetalle> getListaInvoice() {
        return listaInvoice;
    }

    public void setListaInvoice(List<InvoiceDetalle> listaInvoice) {
        this.listaInvoice = listaInvoice;
    }

    public List<InvoiceDetalle> getListaContrato() {
        return listaContrato;
    }

    public void setListaContrato(List<InvoiceDetalle> listaContrato) {
        this.listaContrato = listaContrato;
    }

    public List<InvoiceDetalle> getLista_transferencia() {
        return lista_transferencia;
    }

    public void setLista_transferencia(List<InvoiceDetalle> lista_transferencia) {
        this.lista_transferencia = lista_transferencia;
    }
    
    

    
}
