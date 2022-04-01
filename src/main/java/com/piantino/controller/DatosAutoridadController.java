package com.piantino.controller;

import com.piantino.ejb.AutoridadFacadeLocal;
import com.piantino.ejb.PaisFacadeLocal;
import com.piantino.ejb.StateFacadeLocal;
import com.piantino.model.Autoridad;
import com.piantino.model.Pais;
import com.piantino.model.State;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class DatosAutoridadController implements Serializable {
    
    private Autoridad autoridadActual;
    
    @EJB
    private AutoridadFacadeLocal autoridadEJB;
    
    private String idAutoridadSTR ="";
    
    private String accion="";
    
    @EJB
    private StateFacadeLocal stateEJB;
    
    private List<State> listaStates;
    
    @EJB
    private PaisFacadeLocal paisEJB;
    
    private List<Pais> listaPaises;
    
    private Autoridad autoridadSeleccionada;
    
    @PostConstruct
    private void init() {    
        listaStates = stateEJB.findAll();
        listaPaises = paisEJB.findAll();
        this.idAutoridadSTR= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("AutoridadSelecGrilla");
        if(idAutoridadSTR!=null)
        {
            autoridadSeleccionada = autoridadEJB.find(Integer.parseInt(this.idAutoridadSTR));
            if(this.autoridadSeleccionada!=null)
            {
                try {
                    autoridadActual = (Autoridad)autoridadSeleccionada.clone();
                    
                    if(autoridadSeleccionada.getState()==null)
                    {
                        autoridadSeleccionada.setState( new State());
                    }
                    autoridadActual.setState((State)autoridadSeleccionada.getState().clone());
                    
                    if(autoridadSeleccionada.getPais()==null)
                    {
                        autoridadSeleccionada.setPais(new Pais());
                    }
                    autoridadActual.setPais((Pais)autoridadSeleccionada.getPais().clone());
                    
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(DatosWitnessController.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.setAccion("M");
            }
        }
        else
        {
            this.setAutoridadActual(new Autoridad());
            this.setAccion("R");
        }
        
    }

    public Autoridad getAutoridadActual() {
        return autoridadActual;
    }

    public void setAutoridadActual(Autoridad autoridadActual) {
        this.autoridadActual = autoridadActual;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public List<State> getListaStates() {
        return listaStates;
    }

    public void setListaStates(List<State> listaStates) {
        this.listaStates = listaStates;
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }
    
    public void registrarAutoridad()
    {
        Autoridad autoridadExistente = autoridadEJB.buscarPorNombre(this.getAutoridadActual().getNombre().trim());
        if(autoridadExistente!=null)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a authority with name entered"));
            return;
        }
        else
        {
            autoridadEJB.create(this.getAutoridadActual());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
        }
    }
    
    public void editarAutoridad()
    {
        Autoridad autoridadExistente = autoridadEJB.buscarPorNombre(this.getAutoridadActual().getNombre().trim());
        if(autoridadExistente!=null &&  this.getAutoridadActual().getId() != autoridadExistente.getId())
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a authority with name entered"));
            return;
        } 
        else 
        {
            autoridadEJB.edit(this.getAutoridadActual());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited successfully"));
        }
    }    
    
    
    
}
