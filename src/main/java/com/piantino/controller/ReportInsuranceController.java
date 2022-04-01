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
public class ReportInsuranceController implements Serializable {
    
    @EJB
    private AlquilerFacadeLocal alquilerEJB;
    
    private List<Alquiler> listaAlquilerInsurance = new ArrayList<Alquiler>();
    
    private int cantidadResultado=0;
    
    @PostConstruct
    private void init() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        this.buscarPorFiltro();
    }

    public void buscarPorFiltro() {
        this.listaAlquilerInsurance.clear();
        this.cantidadResultado=0;
        listaAlquilerInsurance =alquilerEJB.getListaAlquilerInsurance();

        this.cantidadResultado=listaAlquilerInsurance.size();
        
    }

    public int getCantidadResultado() {
        return cantidadResultado;
    }

    public void setCantidadResultado(int cantidadResultado) {
        this.cantidadResultado = cantidadResultado;
    }

    public List<Alquiler> getListaAlquilerInsurance() {
        return listaAlquilerInsurance;
    }

    public void setListaAlquilerInsurance(List<Alquiler> listaAlquilerInsurance) {
        this.listaAlquilerInsurance = listaAlquilerInsurance;
    }
    
    

    
    
    
    
}
