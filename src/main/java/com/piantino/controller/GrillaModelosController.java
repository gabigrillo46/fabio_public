package com.piantino.controller;

import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Auto;
import com.piantino.model.Modelo;
import com.piantino.model.Modelo;
import com.piantino.model.Usuario;
import java.io.Serializable;
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
import org.primefaces.model.LazyDataModel;

@Named
@RequestScoped
public class GrillaModelosController implements Serializable {

    @EJB
    private ModeloFacadeLocal ModeloEJB;

    @EJB
    private AutoFacadeLocal AutoEJB;

    private Modelo modeloSeleccionada = null;

    private List<Modelo> listaModelos;

    @PostConstruct
    private void init() {
        listaModelos = ModeloEJB.findAll();
    }

    public List<Modelo> getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(List<Modelo> listaModelos) {
        this.listaModelos = listaModelos;
    }

    public void setModeloSeleccionada(Modelo mar) {
        this.modeloSeleccionada = mar;
    }

    public Modelo getModeloSeleccionada() {
        return this.modeloSeleccionada;
    }

    public void eliminarModelo(Modelo modeloSelecc) {
        try {
            if (validarEliminarModelo(modeloSelecc)) {
                modeloSelecc.setEstado(Modelo.ESTADO_ELIMINADO);
                ModeloEJB.edit(modeloSelecc);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
                listaModelos = ModeloEJB.findAll();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while deleting"));
        }
    }

    public boolean validarEliminarModelo(Modelo modelo) {
        try {
            Auto autoDeMarca = AutoEJB.buscarPorModelo(modelo.getId());
            if (autoDeMarca != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There are active cars of the model"));
                return false;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while validation deleting"));
            return false;
        }
        return true;
    }

}
