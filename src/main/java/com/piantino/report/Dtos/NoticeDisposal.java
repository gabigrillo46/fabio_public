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
public class NoticeDisposal {
    
    private List<NoticeDisposalDetalle> listaDetalles;

    public List<NoticeDisposalDetalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<NoticeDisposalDetalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }
    
}
