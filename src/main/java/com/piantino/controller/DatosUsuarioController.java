/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Auto;
import com.piantino.model.Usuario;
import java.io.Serializable;
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
public class DatosUsuarioController implements Serializable {

    private Usuario usuarioActual = null;

    private String passwordActual = "";

    private String newPass = "";

    private String newPass2 = "";

    private String accion = "";

    @EJB
    UsuarioFacadeLocal usuarioEJB;

    private boolean cambiandoContrasena = false;

    @PostConstruct
    private void init() {
        String idAutoStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuarioDato");
        if (idAutoStr != null && idAutoStr.trim().length() > 0) {
            usuarioActual = usuarioEJB.getUsuarioPorId(Integer.parseInt(idAutoStr));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idUsuarioDato");
        }

        if (usuarioActual == null) {
            usuarioActual = new Usuario();
            this.accion = "R";
        } else {
            this.accion = "M";
        }
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean esMoficiacion() {
        return (this.accion.equalsIgnoreCase("M"));
    }

    public boolean esAlta() {
        return (this.accion.equalsIgnoreCase("R"));
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    private boolean validarCampos() {
        if (this.usuarioActual == null || this.usuarioActual.getEmail() == null) {
            return false;
        }
        Usuario usuarioBD = usuarioEJB.getUsuarioPorEmail(this.usuarioActual.getEmail());
        if ((usuarioBD != null && this.accion.equalsIgnoreCase("R")) || (usuarioBD != null && this.accion.equalsIgnoreCase("M") && usuarioBD.getId() != this.usuarioActual.getId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a user with username entered"));
            return false;
        }

        return true;

    }

    public void registrar() {
        try {
            if (validarCampos()) {
                usuarioEJB.create(usuarioActual);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
                this.usuarioActual = new Usuario();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Registration failed"));
        }

    }

    public void cambiarContra() {
        if (this.esAlta()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Can not change password in a new user"));
            return;
        }
        if (this.usuarioActual != null && this.esMoficiacion()) {
            this.setCambiandoContrasena(true);
        }
    }

    public void aceptoCambioContrasena() {
        if (this.usuarioActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is no user to change password"));
            return;
        }

        if (this.passwordActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter current password"));
            return;
        }

        if (!this.usuarioActual.getPassword().equalsIgnoreCase(this.passwordActual)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The current password is wrong"));
            return;
        }

        if (this.newPass == null || this.newPass.trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a new password"));
            return;
        }

        if (this.newPass2 == null || this.newPass2.trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a new password again"));
            return;
        }

        if (!this.newPass.equals(this.newPass2)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The new password and the confirmation do not match"));
            return;
        }
        this.usuarioActual.setPassword(this.newPass);
        usuarioEJB.edit(this.usuarioActual);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Password changed"));
        this.setCambiandoContrasena(false);
    }

    public void cancelarCambioContrasena() {
        this.setCambiandoContrasena(false);
    }

    public void editar() {
        try {
            if (validarCampos()) {
                usuarioEJB.edit(usuarioActual);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
                this.usuarioActual = new Usuario();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public boolean isCambiandoContrasena() {
        return cambiandoContrasena;
    }

    public void setCambiandoContrasena(boolean cambiandoContrasena) {
        this.cambiandoContrasena = cambiandoContrasena;
    }

    public boolean noEstaCambiandoContra() {
        return (!this.cambiandoContrasena);
    }

    public String getPasswordActual() {
        return passwordActual;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPass2() {
        return newPass2;
    }

    public void setNewPass2(String newPass2) {
        this.newPass2 = newPass2;
    }

}
