/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.PublicidadFacadeLocal;
import com.piantino.model.Publicidad;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class DatosPublicidadController implements Serializable {

    @EJB
    private PublicidadFacadeLocal publicidadEJB;

    private Publicidad publicidadActual = new Publicidad();

    @PostConstruct
    private void init() {
        List<Publicidad> listaPublicidadesCreadas = publicidadEJB.findAll();
        if(listaPublicidadesCreadas.size()>0)
        {
            publicidadActual = listaPublicidadesCreadas.get(0);
        }
        else
        {
            publicidadActual = new Publicidad();
        }        
    }

    public Publicidad getPublicidadActual() {
        return publicidadActual;
    }

    public void setPublicidadActual(Publicidad publicidadActual) {
        this.publicidadActual = publicidadActual;
    }

    public void registrar() {
        if(validarCampos())
        {
            if(this.publicidadActual.getId()>0)
            {
                this.publicidadEJB.edit(this.publicidadActual);
            }
            else 
            {
                this.publicidadEJB.create(publicidadActual);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
        }
        
    }

    public boolean validarCampos() {
        if (this.publicidadActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a text"));
            return false;
        }
        
        if(this.publicidadActual.getTexto().trim().length()==0)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a text"));
            return false;            
        }

        return true;
    }

}
