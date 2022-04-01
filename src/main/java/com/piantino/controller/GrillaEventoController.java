/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.EventoFacadeLocal;
import com.piantino.model.Evento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class GrillaEventoController implements Serializable {

    private Date fechaDesde = new Date();

    private Date fechaHasta = new Date();

    private Calendar c;

    private String email = "";

    private List<Evento> listaEventos = new ArrayList();

    @EJB
    private EventoFacadeLocal eventoEJB;

    private int cantidadResultados = 0;

    @PostConstruct
    public void init() {
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        this.fechaDesde.setTime(calendar.getTimeInMillis());
    }

    public void buscarPorFiltro() {
        this.listaEventos = this.eventoEJB.buscarPorFiltro(fechaDesde, fechaHasta, email);
        this.cantidadResultados = this.listaEventos.size();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(int cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public String getTipoEvento(Evento evento) {
        if (evento.getTipo() == Evento.TYPE_LOGIN) {
            return "Login";
        } else if (evento.getTipo() == Evento.TYPE_CAR_LIST) {
            return "Car list";
        }
        return "";
    }

}
