package com.piantino.controller;

import com.piantino.ejb.Tipo_autoFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Tipo_auto;
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
public class DatosTipoAutoController implements Serializable {

    @EJB
    private Tipo_autoFacadeLocal Tipo_autoEJB;

    private String accion = "";

    private Tipo_auto Tipo_auto;

    @Inject
    private GrillaTipoAutosController comentarController;

    @PostConstruct
    private void init() {

        Tipo_auto = comentarController.getTipo_autoSeleccionada();
        if (Tipo_auto == null) {
            Tipo_auto = new Tipo_auto();
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

    public void editarTipo_auto() {
        try {
            Tipo_auto Tipo_autoExistente = Tipo_autoEJB.buscarPorNombre(Tipo_auto.getNombre());
            if (Tipo_autoExistente != null && Tipo_autoExistente.getId()!=Tipo_auto.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a type with the name entered"));
            }                
            else
            {
                Tipo_autoEJB.edit(Tipo_auto);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited correctly"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Edit failed"));
        }
        //   Cuando haga enter tiene que tomar el editar, no el boton volver
        //         Hacer el eliminar
    }

    public String leer(Tipo_auto marSelecc) {
        Tipo_auto = comentarController.getTipo_autoSeleccionada();
        this.setAccion("M");
        return "/pantallas/DatosTipo_auto";
    }

    public Tipo_auto getTipo_auto() {
        return Tipo_auto;
    }

    public void setTipo_auto(Tipo_auto Tipo_auto) {
        this.Tipo_auto = Tipo_auto;
    }

    public void registrar() {
        try {
            Tipo_auto Tipo_autoExistente = Tipo_autoEJB.buscarPorNombre(Tipo_auto.getNombre());
            if (Tipo_autoExistente != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a type with the name entered"));
            } else {
                Tipo_autoEJB.create(Tipo_auto);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
                this.setTipo_auto(new Tipo_auto());
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Registration failed"));
        }

    }

}
