package com.piantino.controller;

import com.piantino.ejb.WitnessFacadeLocal;
import com.piantino.model.Witness;
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
public class GrillaWitnessController implements Serializable {
    
    private List<Witness> listaWitness = new ArrayList();
    
    @EJB
    private WitnessFacadeLocal witnessEJB;
    
    private Witness witnessSelecionado;
    
    @PostConstruct
    public void init()
    {
        listaWitness = witnessEJB.findAll();
    }

    public List<Witness> getListaWitness() {
        return listaWitness;
    }

    public void setListaWitness(List<Witness> listaWitness) {
        this.listaWitness = listaWitness;
    }

    public Witness getWitnessSelecionado() {
        return witnessSelecionado;
    }

    public void setWitnessSelecionado(Witness witnessSelecionado) {
        this.witnessSelecionado = witnessSelecionado;
    }
    
    public void eliminarWitness(Witness w)
    {
        w.setEstado(Witness.ESTADO_ELIMINADO);
        witnessEJB.edit(w);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
        listaWitness = witnessEJB.findAll();
    }
    
    
    
    
}
