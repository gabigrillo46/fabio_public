/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.TemplateFacadeLocal;
import com.piantino.model.Template;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class DatosTemplateController implements Serializable {

    private Template templateActual;

    private String campoSelecc;

    @EJB
    private TemplateFacadeLocal templateEJB;

    private List<String> listaCampos = new ArrayList<String>();

    private String accion = "";

    @PostConstruct
    private void init() {
        String idTemplateStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idTemplateDato");
        if (idTemplateStr != null && idTemplateStr.trim().length() > 0) {
            int idTemplate = Integer.parseInt(idTemplateStr);
            templateActual = templateEJB.find(idTemplate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idTemplateDato");
            accion = "M";
        } else {
            templateActual = new Template();
            accion = "R";
        }

        listaCampos = Arrays.asList(templateActual.getCampos().split("-"));

    }

    public Template getTemplateActual() {
        return templateActual;
    }

    public void setTemplateActual(Template templateActual) {
        this.templateActual = templateActual;
    }

    public List<String> getListaCampos() {
        return listaCampos;
    }

    public void setListaCampos(List<String> listaCampos) {
        this.listaCampos = listaCampos;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public void modificarTemplate() {
           if(this.templateActual!=null)
           {
               templateEJB.edit(templateActual);
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited correctly"));
           }
    }

    public void registrarTemplate() {

    }

    public void onRowDoubleClick(final SelectEvent event) {
        String obj = (String) event.getObject();
        
        this.templateActual.setTexto(this.templateActual.getTexto() + obj+" ");
        // rest of your logic
    }

    public String getCampoSelecc() {
        return campoSelecc;
    }

    public void setCampoSelecc(String campoSelecc) {
        this.campoSelecc = campoSelecc;
    }
    
    

}
