/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.ImpuestoFacadeLocal;
import com.piantino.model.Impuesto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class GrillaImpuestoController implements Serializable {

    @EJB
    private ImpuestoFacadeLocal impuestoEJB;

    private List<Impuesto> listaImpuestos=new <Impuesto>ArrayList();
    
    private String siono="";
    
    private Impuesto impuestoSeleccionado;
    

    @PostConstruct
    private void init() {
        listaImpuestos = impuestoEJB.findAll();
    }

    public List<Impuesto> getListaImpuestos() {
        return listaImpuestos;
    }

    public void setListaImpuestos(List<Impuesto> listaImpuestos) {
        this.listaImpuestos = listaImpuestos;
    }

    public String getSiono() {
        return siono;
    }

    public void setSiono(String siono) {
        this.siono = siono;
    }
    
    public String getSiono(boolean valor)
    {
        if(valor)
        {
            return "Yes";
        }
        return "Not";
    }


    public Impuesto getImpuestoSeleccionado() {
        return impuestoSeleccionado;
    }

    public void setImpuestoSeleccionado(Impuesto impuestoSeleccionado) {
        this.impuestoSeleccionado = impuestoSeleccionado;
    }
    
        public void eliminarImpuesto(Impuesto impSelecc)
    {
        try
        {
            impSelecc.setEstado(Impuesto.ESTADO_ELIMINADO);
            impuestoEJB.edit(impSelecc);
            //aca no tenemos que eliminarlo, tenemos que cambiarle de estado...
            
        //AutosEJB.remove(marcaSelecc);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
        listaImpuestos = impuestoEJB.findAll();
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while deleting"));
        }
    }
    

    
    
    
    
}
