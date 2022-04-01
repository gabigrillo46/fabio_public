/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.Dealer_licenceFacadeLocal;
import com.piantino.model.Dealer_licence;
import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;
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
public class DatosDealerLicenceController implements Serializable {

    private Dealer_licence dealerLicenciaActual = null;

    @EJB
    private Dealer_licenceFacadeLocal dealerLicenceEJB;

    private String accion = "";

    @PostConstruct
    private void init() {
        Dealer_licence dealerSeleccionado = null;
        String idDealerSTR = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idDealerLicenceDato");
        if (idDealerSTR != null && idDealerSTR.trim().length() > 0) {
            int idDealerLicence = Integer.parseInt(idDealerSTR);
            dealerSeleccionado = this.dealerLicenceEJB.find(idDealerLicence);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idDealerLicenceDato");
        }
        if (dealerSeleccionado == null) {
            dealerLicenciaActual = new Dealer_licence();
            this.setAccion("R");
        } else {
            try {
                dealerLicenciaActual = (Dealer_licence) dealerSeleccionado.clone();
            } catch (CloneNotSupportedException ex) {
                dealerLicenciaActual = dealerSeleccionado;
            }
            this.setAccion("M");
        }
    }

    public Dealer_licence getDealerLicenciaActual() {
        return dealerLicenciaActual;
    }

    public void setDealerLicenciaActual(Dealer_licence dealerLicenciaActual) {
        this.dealerLicenciaActual = dealerLicenciaActual;
    }

    public void registrarLicencia() {
        if (this.validarDatosLicenciaRegistrar()) {
            this.dealerLicenceEJB.create(dealerLicenciaActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Licence has been registered successfully"));
            this.dealerLicenciaActual = new Dealer_licence();
        }
    }

    private boolean validarDatosLicenciaRegistrar() {
        if (this.dealerLicenciaActual == null || this.dealerLicenciaActual.getNombre() == null || this.dealerLicenciaActual.getNombre().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a name"));
            return false;
        }

        if (this.dealerLicenciaActual == null || this.dealerLicenciaActual.getNumero() == null || this.dealerLicenciaActual.getNumero().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a number"));
            return false;
        }

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        if (this.dealerLicenciaActual == null || this.dealerLicenciaActual.getFecha_vencimiento() == null || this.dealerLicenciaActual.getFecha_vencimiento().before(c.getTime())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The licence is expired"));
            return false;
        }

        Dealer_licence licenciaEnBD = this.dealerLicenceEJB.getLicenceByNumber(this.dealerLicenciaActual.getNumero());
        if (licenciaEnBD != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another licence with the number entered"));
            return false;
        }

        return true;
    }

    public void editarLicencia() {
        if (validarDatosLicenciaEditar()) {
            this.dealerLicenceEJB.edit(dealerLicenciaActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Licence has been edited successfully"));
        }
    }

    private boolean validarDatosLicenciaEditar() {
        if (this.dealerLicenciaActual == null || this.dealerLicenciaActual.getNombre() == null || this.dealerLicenciaActual.getNombre().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a name"));
            return false;
        }

        if (this.dealerLicenciaActual == null || this.dealerLicenciaActual.getNumero() == null || this.dealerLicenciaActual.getNumero().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a number"));
            return false;
        }

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        if (this.dealerLicenciaActual == null || this.dealerLicenciaActual.getFecha_vencimiento() == null || this.dealerLicenciaActual.getFecha_vencimiento().before(c.getTime())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The licence is expired"));
            return false;
        }

        Dealer_licence licenciaEnBD = this.dealerLicenceEJB.getLicenceByNumber(this.dealerLicenciaActual.getNumero());
        if (licenciaEnBD != null && licenciaEnBD.getId() != this.dealerLicenciaActual.getId()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another licence with the number entered"));
            return false;
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
