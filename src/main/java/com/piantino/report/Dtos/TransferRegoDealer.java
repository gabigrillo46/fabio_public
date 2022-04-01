/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.report.Dtos;

import java.util.List;

/**
 *
 * @author Gabriel
 */
public class TransferRegoDealer {
    
    private List<TransferRegoDealerDetalle> listaDetalles;

    public List<TransferRegoDealerDetalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<TransferRegoDealerDetalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }
    
}
