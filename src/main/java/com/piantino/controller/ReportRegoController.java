/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Auto;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class ReportRegoController implements Serializable {

    private Date fechaDesde = null;

    private Date fechaHasta = null;

    @EJB
    private AutoFacadeLocal autoEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private List<Auto> listaAutoRegoVencer = new ArrayList<Auto>();

    private List<Alquiler> listaAlquileres = new ArrayList<Alquiler>();

    private int cantidadResultado = 0;

    private String fechaVencFormat = "";

    private String fechaFormat = "";
    
    private boolean regoFinalizaConAgreement = false;
    

    @PostConstruct
    private void init() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        fechaDesde = c.getTime();
        fechaHasta = c.getTime();
    }

    public void buscarPorFiltro2() {
        if (fechaDesde == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the date from"));
            return;
        }

        if (fechaHasta == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the date until"));
            return;
        }
        this.listaAutoRegoVencer.clear();
        this.cantidadResultado = 0;
        this.listaAlquileres = alquilerEJB.alquileresAutoRegoVencerPeriodo(fechaDesde, fechaHasta);
        this.cantidadResultado = listaAutoRegoVencer.size();
    }

    public List<Alquiler> getListaAlquileres() {
        return listaAlquileres;
    }

    public void setListaAlquileres(List<Alquiler> listaAlquileres) {
        this.listaAlquileres = listaAlquileres;
    }

    public void buscarPorFiltro() {
        if (fechaDesde == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the date from"));
            return;
        }

        if (fechaHasta == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the date until"));
            return;
        }
        this.listaAutoRegoVencer.clear();
        this.cantidadResultado = 0;
        List<Integer> listaIDAutosRego = autoEJB.buscarIDAutosRegoVencerPeriodo(fechaDesde, fechaHasta);
        for (int p = 0; p < listaIDAutosRego.size(); p++) {
            int idAuto = listaIDAutosRego.get(p);
            Auto autoCargar = autoEJB.buscarPorId(idAuto);
            listaAutoRegoVencer.add(autoCargar);
        }
        this.cantidadResultado = listaAutoRegoVencer.size();

    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<Auto> getListaAutoRegoVencer() {
        return listaAutoRegoVencer;
    }

    public void setListaAutoRegoVencer(List<Auto> listaAutoRegoVencer) {
        this.listaAutoRegoVencer = listaAutoRegoVencer;
    }

    public int getCantidadResultado() {
        return cantidadResultado;
    }

    public void setCantidadResultado(int cantidadResultado) {
        this.cantidadResultado = cantidadResultado;
    }

    public String getFechaVencFormat() {
        return fechaVencFormat;
    }

    public void setFechaVencFormat(String fechaVencFormat) {
        this.fechaVencFormat = fechaVencFormat;
    }

    public String getFechaVencFormat(Auto auto) {
        if (auto != null && auto.getRego() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(auto.getFecha_vencimiento_rego());
        }
        return "";
    }
    
    public void verDetalleContrato(int idAlquiler)
    {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerDato", idAlquiler + "");
    }
    

    public String getFechaFormat() {
        return fechaFormat;
    }

    public String getFechaFormat(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            return "";
        }
    }

    public void setFechaFormat(String fechaFormat) {
        this.fechaFormat = fechaFormat;
    }

    public boolean isRegoFinalizaConAgreement(Alquiler alquiler) {
        if (alquiler == null || alquiler.getAuto()==null || alquiler.getAuto().getFecha_vencimiento_rego()==null) {
            return false;
        }
        Date fechaVencimientoRego =alquiler.getAuto().getFecha_vencimiento_rego();
        Date fechaFinContrato = alquiler.getFecha_fin();

        int milisecondsByDay = 86400000;
        int dias = (int) ((fechaFinContrato.getTime() - fechaVencimientoRego.getTime()) / milisecondsByDay);

        if (dias < 3) {
            return true;
        } else {
            return false;
        }
    }

    public void setRegoFinalizaConAgreement(boolean regoFinalizaConAgreement) {
        this.regoFinalizaConAgreement = regoFinalizaConAgreement;
    }
    
    

}
