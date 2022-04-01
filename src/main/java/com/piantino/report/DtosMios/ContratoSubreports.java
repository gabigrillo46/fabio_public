/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.report.DtosMios;

import java.util.List;

/**
 *
 * @author gaby
 */
public class ContratoSubreports {
    
    private List<PaymentsDetailsDTO> listaPagos;
    private List<ImpuestoDetailsDTO> listaImpuestos;

    public List<PaymentsDetailsDTO> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<PaymentsDetailsDTO> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public List<ImpuestoDetailsDTO> getListaImpuestos() {
        return listaImpuestos;
    }

    public void setListaImpuestos(List<ImpuestoDetailsDTO> listaImpuestos) {
        this.listaImpuestos = listaImpuestos;
    }

}
