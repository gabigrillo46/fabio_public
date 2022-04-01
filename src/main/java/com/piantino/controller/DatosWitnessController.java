package com.piantino.controller;

import com.piantino.ejb.PaisFacadeLocal;
import com.piantino.ejb.StateFacadeLocal;
import com.piantino.ejb.WitnessFacadeLocal;
import com.piantino.model.Pais;
import com.piantino.model.State;
import com.piantino.model.Witness;
import java.io.Serializable;
import java.util.ArrayList;
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
public class DatosWitnessController implements Serializable {
    
    private String idWitnessStr="";
    
    @EJB
    private WitnessFacadeLocal witnessEJB;
    
    private Witness witnessActual;
    
    private Witness witnessSeleccionado;
    
    private String accion ="";
    
    private List<State> listaStates = new ArrayList();
    
    @EJB
    private StateFacadeLocal stateEJB;
    
    private List<Pais> listaPaises = new ArrayList();
    
    @EJB
    private PaisFacadeLocal paisEJB;
    
        @PostConstruct
    private void init() {
        this.listaStates = stateEJB.findAll();
        this.listaPaises = paisEJB.findAll();
        this.idWitnessStr= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("WitnessSelecGrilla");
        
        
        if(idWitnessStr!=null)
        {
            witnessSeleccionado = witnessEJB.find(Integer.parseInt(idWitnessStr));
            if(witnessSeleccionado != null)
            {
                this.setAccion("M");
                try {
                    witnessActual = (Witness)witnessSeleccionado.clone();
                    
                    if(witnessSeleccionado.getState()==null)
                    {
                        witnessSeleccionado.setState( new State());
                    }
                    witnessActual.setState((State)witnessSeleccionado.getState().clone());
                    
                    if(witnessSeleccionado.getPais()==null)
                    {
                        witnessSeleccionado.setPais(new Pais());
                    }
                    witnessActual.setPais((Pais)witnessSeleccionado.getPais().clone());
                    
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(DatosWitnessController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            this.setWitnessActual(new Witness());
            this.setAccion("R");
        }
    }

    public String getIdWitnessStr() {
        return idWitnessStr;
    }

    public void setIdWitnessStr(String idWitnessStr) {
        this.idWitnessStr = idWitnessStr;
    }

    public Witness getWitnessActual() {
        return witnessActual;
    }

    public void setWitnessActual(Witness witnessActual) {
        this.witnessActual = witnessActual;
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
    
    public void registrarWitness()
    {
        Witness witnessExistente = witnessEJB.buscarPorNombre(this.getWitnessActual().getNombre().trim());
        if(witnessExistente!=null)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a witness with name entered"));
            return;
        }
        else
        {
            witnessEJB.create(this.getWitnessActual());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
        }
    }
    
    public void editarWitness()
    {
        Witness witnessExistente = witnessEJB.buscarPorNombre(this.getWitnessActual().getNombre().trim());
        if(witnessExistente!=null &&  this.witnessActual.getId() != witnessExistente.getId())
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a witness with name entered"));
            return;
        } 
        else 
        {
            witnessEJB.edit(this.getWitnessActual());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited successfully"));
        }
    }
    
    
}
