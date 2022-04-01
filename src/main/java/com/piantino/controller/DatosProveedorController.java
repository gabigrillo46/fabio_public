/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.ejb.StateFacadeLocal;
import com.piantino.model.Cliente;
import com.piantino.model.State;
import java.io.Serializable;
import java.util.ArrayList;
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
public class DatosProveedorController implements Serializable {

    private Cliente proveedorActual = new Cliente();

    @EJB
    private ClienteFacadeLocal clienteEJB;

    @EJB
    private StateFacadeLocal stateEJB;

    private List<State> listaEstadosTerritorio = new ArrayList();
    
    private String accion = "";

    @PostConstruct
    private void init() {

        int idProveedor = -1;
        try {

            String idClienteDato = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idProveedorDato");
            if (idClienteDato != null && idClienteDato.trim().length() > 0) {
                idProveedor = Integer.parseInt(idClienteDato);
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idProveedorDato");

            Cliente clienteBD = clienteEJB.buscarPorID(idProveedor);
            if (clienteBD == null) {
                proveedorActual = new Cliente();
                  this.setAccion("R");
            } else {
                proveedorActual = (Cliente) clienteBD.clone();
                if (clienteBD.getState() == null) {
                    clienteBD.setState(new State());
                }
                proveedorActual.setState((State) clienteBD.getState().clone());
                  this.setAccion("M");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.listaEstadosTerritorio = stateEJB.findAll();
    }

    public Cliente getProveedorActual() {
        return proveedorActual;
    }

    public void setProveedorActual(Cliente proveedorActual) {
        this.proveedorActual = proveedorActual;
    }

    public List<State> getListaEstadosTerritorio() {
        return listaEstadosTerritorio;
    }

    public void setListaEstadosTerritorio(List<State> listaEstadosTerritorio) {
        this.listaEstadosTerritorio = listaEstadosTerritorio;
    }

    public void registrarProveedor() {
        if (this.validarRegistrarProveedor()) {
            this.proveedorActual.setTipo(Cliente.TIPO_DEALER);
            this.clienteEJB.create(this.proveedorActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Supplier successfully registered"));
            this.proveedorActual = new Cliente();
        }
    }

    public void editarProveedor() {
        if (this.validarEditarProveedor()) {
            this.clienteEJB.edit(proveedorActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Supplier successfully edited"));
        }
    }

    public boolean validarRegistrarProveedor() {
        if (this.proveedorActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The supplier is null"));
            return false;
        }

        if (this.proveedorActual.getApellido() == null || this.proveedorActual.getApellido().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the name of the supplier"));
            return false;
        }

        Cliente proveedorBD = clienteEJB.buscarProveedorPorNombre(this.proveedorActual.getApellido());
        if (proveedorBD != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another supplier with the same name"));
            return false;
        }

        if (this.proveedorActual.getLicencia_numero() != null && this.proveedorActual.getLicencia_numero().trim().length() > 0) {
            Cliente proveedorBDLic = clienteEJB.buscarProveedorPorNumeroLicencia(this.proveedorActual.getLicencia_numero());
            if (proveedorBDLic != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another supplier with the licence number"));
                return false;
            }
        }

        return true;
    }

    public boolean validarEditarProveedor() {
        if (this.proveedorActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The supplier is null"));
            return false;
        }

        if (this.proveedorActual.getApellido() == null || this.proveedorActual.getApellido().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the name of the supplier"));
            return false;
        }

        Cliente proveedorBD = clienteEJB.buscarProveedorPorNombre(this.proveedorActual.getApellido());
        if (proveedorBD != null && proveedorBD.getId() !=this.proveedorActual.getId()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another supplier with the same name"));
            return false;
        }

        if (this.proveedorActual.getLicencia_numero() != null && this.proveedorActual.getLicencia_numero().trim().length() > 0) {
            Cliente proveedorBDLic = clienteEJB.buscarProveedorPorNumeroLicencia(this.proveedorActual.getLicencia_numero());
            if (proveedorBDLic != null && proveedorBDLic.getId()!=this.proveedorActual.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another supplier with the licence number"));
                return false;
            }
        }
        
        return true;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    

}
