package com.piantino.controller;

import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Marca;
import com.piantino.model.Usuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class DatosMarcaController implements Serializable {

    @EJB
    private MarcaFacadeLocal marcaEJB;

    private String accion = "";

    private Marca marca;

    @Inject
    private GrillaMarcasController comentarController;

    @PostConstruct
    private void init() {

        marca = comentarController.getMarcaSeleccionada();
        if (marca == null) {
            marca = new Marca();
            this.setAccion("R");
        } else {
            this.setAccion("M");
        }

    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public void editarMarca() {
        try {
            Marca marcaExistente = marcaEJB.buscarPorNombre(marca.getNombre());
            if (marcaExistente != null && marcaExistente.getId() != marca.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "A brand with the name entered already exists"));
            } else {
                marcaEJB.edit(marca);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited correctly"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Edit failed"));
        }
        //   Cuando haga enter tiene que tomar el editar, no el boton volver
        //         Hacer el eliminar
    }

    public String leer(Marca marSelecc) {
        marca = comentarController.getMarcaSeleccionada();
        this.setAccion("M");
        return "/pantallas/DatosMarca";
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void registrar() {
        try {
            Marca marcaExistente = marcaEJB.buscarPorNombre(marca.getNombre());
            if (marcaExistente != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a mark with the name entered"));
            } else {
                marcaEJB.create(marca);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
                this.setMarca(new Marca());
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Registration failed"));
        }

    }

}
