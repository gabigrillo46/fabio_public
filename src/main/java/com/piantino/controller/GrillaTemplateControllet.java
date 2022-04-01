/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.TemplateFacadeLocal;
import com.piantino.model.Template;
import java.io.Serializable;
import java.util.List;
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
public class GrillaTemplateControllet implements Serializable {

    @EJB
    private TemplateFacadeLocal templateEJB;

    private List<Template> listaTemplateResultado;

    private String nombreFiltro = null;

    @PostConstruct
    private void init() {

    }

    public void buscarPorFiltro() {
        if (nombreFiltro.trim().length() == 0) {
            nombreFiltro = null;
        }
        listaTemplateResultado = templateEJB.buscarPorFiltro(nombreFiltro);
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public List<Template> getListaTemplateResultado() {
        return listaTemplateResultado;
    }

    public void setListaTemplateResultado(List<Template> listaTemplateResultado) {
        this.listaTemplateResultado = listaTemplateResultado;
    }

    public void verDetalleTemplate(int idTemplate) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idTemplateDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idTemplateDato", idTemplate + "");
    }
    
        public void eliminarTemplate(Template tempSelecc) {
        try {
            templateEJB.remove(tempSelecc);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
            this.buscarPorFiltro();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Error detected while deleting"));
        }
    }

}
