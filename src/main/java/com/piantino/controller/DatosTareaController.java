/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.TareaFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Tarea;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.util.Calendar;
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
public class DatosTareaController implements Serializable {

    @EJB
    private TareaFacadeLocal tareaEJB;

    private Tarea tareaActual;

    private String accion = "";

    private List<Usuario> listausuarios;

    @EJB
    private UsuarioFacadeLocal usuarioEJB;

    @PostConstruct
    private void init() {
        listausuarios = usuarioEJB.findAll();
        String idTareaStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idTareaDato");
        if (idTareaStr == null) {
            tareaActual = new Tarea();
            this.setAccion("R");
        } else {
            int idTarea = Integer.parseInt(idTareaStr);
            Tarea tareaBD = tareaEJB.buscarTareaPorId(idTarea);
            if (tareaBD != null) {
                try {
                    this.tareaActual = (Tarea) tareaBD.clone();
                    if (tareaBD.getUsuarioAsignado() == null) {
                        tareaBD.setUsuarioAsignado(new Usuario());
                    }
                    this.tareaActual.setUsuarioAsignado((Usuario) tareaBD.getUsuarioAsignado().clone());

                    if (tareaBD.getUsuarioCreador() == null) {
                        tareaBD.setUsuarioCreador(new Usuario());
                    }
                    this.tareaActual.setUsuarioCreador((Usuario) tareaBD.getUsuarioCreador().clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(DatosTareaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.setAccion("M");
            }
        }
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Tarea getTareaActual() {
        return tareaActual;
    }

    public void setTareaActual(Tarea tareaActual) {
        this.tareaActual = tareaActual;
    }

    public List<Usuario> getListausuarios() {
        return listausuarios;
    }

    public void setListausuarios(List<Usuario> listausuarios) {
        this.listausuarios = listausuarios;
    }

    public void registrarTarea() {
        if (validarTarea()) {
            tareaEJB.create(this.tareaActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
            this.tareaActual = new Tarea();

        }
    }

    public void editarTarea() {
        if (validarEditarTarea()) {
            tareaEJB.edit(tareaActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully updated"));
            this.tareaActual = new Tarea();

        }
    }

    private boolean validarEditarTarea() {
        Calendar c = Calendar.getInstance();
        if (this.tareaActual.getFecha_vencimiento().before(c.getTime())) {
            this.tareaActual.setCaducado(true);
        } else {
            this.tareaActual.setCaducado(false);
        }
        return true;
    }

    public boolean validarTarea() {
        Usuario usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuarioLogueado == null) {
            return false;
        }
        this.tareaActual.setUsuarioCreador(usuarioLogueado);
        Calendar c = Calendar.getInstance();
        if (this.tareaActual.getFecha_vencimiento().before(c.getTime())) {
            this.tareaActual.setCaducado(true);
        }
        return true;
    }

}
