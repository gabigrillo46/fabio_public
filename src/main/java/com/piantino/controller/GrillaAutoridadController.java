package com.piantino.controller;

import com.piantino.ejb.AutoridadFacadeLocal;
import com.piantino.model.Autoridad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class GrillaAutoridadController implements Serializable {  
    
    private List<Autoridad> listaAutoridades= new ArrayList();
    
    @EJB
    private AutoridadFacadeLocal autoridadEJB;
    
    private Autoridad autoridadSeleccionada;
    
     @PostConstruct
    public void init()
    {
        listaAutoridades = autoridadEJB.findAll();
    }

    public List<Autoridad> getListaAutoridades() {
        return listaAutoridades;
    }

    public void setListaAutoridades(List<Autoridad> listaAutoridades) {
        this.listaAutoridades = listaAutoridades;
    }

    public Autoridad getAutoridadSeleccionada() {
        return autoridadSeleccionada;
    }

    public void setAutoridadSeleccionada(Autoridad autoridadSeleccionada) {
        this.autoridadSeleccionada = autoridadSeleccionada;
    }
    
        public void eliminarAutoridad(Autoridad w)
    {
        w.setEstado(Autoridad.ESTADO_ELIMINADO);
        autoridadEJB.edit(w);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
        listaAutoridades = autoridadEJB.findAll();
    }
    
}
