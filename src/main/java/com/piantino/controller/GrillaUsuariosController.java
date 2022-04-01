/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class GrillaUsuariosController implements Serializable {

    @EJB
    private UsuarioFacadeLocal usuarioEJB;

    private List<Usuario> listaUsuario;

    @PostConstruct
    private void init() {
        listaUsuario = usuarioEJB.listaUsuarioActivos();
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public void verDetalleUsuario(int idUsuario) {

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idUsuarioDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idUsuarioDato", idUsuario + "");

    }

    public void eliminarUsuario(Usuario usu) {
        usu.setEstado(Constante.ESTADOS_USUARIOS.BAJA_LOGICA);
        usuarioEJB.edit(usu);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
        this.listaUsuario = usuarioEJB.listaUsuarioActivos();
    }

}
