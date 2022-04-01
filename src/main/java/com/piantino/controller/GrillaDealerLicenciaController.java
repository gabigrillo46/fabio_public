/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.Dealer_licenceFacadeLocal;
import com.piantino.model.Dealer_licence;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class GrillaDealerLicenciaController implements Serializable {

    private List<Dealer_licence> listaLicencias = new ArrayList();

    @EJB
    private Dealer_licenceFacadeLocal dealerLicenciaEJB;
    
    private String numero="";

    @PostConstruct
    public void init() {
        listaLicencias = dealerLicenciaEJB.findAll();
    }

    public List<Dealer_licence> getListaLicencias() {
        return listaLicencias;
    }

    public void setListaLicencias(List<Dealer_licence> listaLicencias) {
        this.listaLicencias = listaLicencias;
    }
    
    public void verDetalleDealerLicence(int idLicence) 
    {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idDealerLicenceDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idDealerLicenceDato", idLicence + "");
    }   

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
    public void buscarPorFiltro()
    {
        String numeroBuscar = null;
        if(this.numero!=null && this.numero.trim().length()>0)
        {
            numeroBuscar = this.numero;
        }
        this.listaLicencias = this.dealerLicenciaEJB.getListaLicenceByNumber(this.numero);
    }
    
    
    
    

}
