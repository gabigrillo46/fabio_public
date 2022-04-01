/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.Tipo_pagoFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Pago;
import com.piantino.model.Sucursal;
import com.piantino.model.Tipo_pago;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class ReportPaymentsController implements Serializable {  
    
        @EJB
    private PagoFacadeLocal PagoEJB;

    private String status;

    private Date fechaDesde;

    private Date fechaHasta;

    private List<Pago> listaPagos= new ArrayList<>();
    
    private int idSucursalFiltro = -1;
    
    private Integer idFormaPago = -1;
    
    private String referencia = null;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private List<Sucursal> listaSucursales;

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    private Usuario usuarioLogueado;
    
    private Double dineroIngresado = 0.0;
    
    private int cantidad = 0;
    
    private List<Tipo_pago> listaTipoPago = new ArrayList<>();
    
    @EJB
    private Tipo_pagoFacadeLocal tipoPagoEJB;
    
    @PostConstruct
    private void init() {
        listaSucursales = sucursalEJB.findAll();
        listaTipoPago = tipoPagoEJB.findAll();
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        fechaDesde = c.getTime();
        fechaHasta = c.getTime();
    }

    public String getStatus() {
        return status;
    }
    
    public boolean esPagoAprovado(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().startsWith("Approved") ) {
            return true;
        } else {
            return false;
        }
    }
    
        public boolean esPagoDeclined(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().startsWith("Declined")) {
            return true;
        } else {
            return false;
        }
    }
    
    
    public void buscarPorFiltro() {
        int idForma=-1;
        if (status == null || status.trim().length() == 0) {
            status = null;
        }
        if(idFormaPago>-1)
        {
            idForma = idFormaPago;
        }
        if(referencia!=null && referencia.trim().length()==0)
        {
            referencia = null;
        }
        
        this.listaPagos.clear();
        this.listaPagos = PagoEJB.buscarPorFiltro2(fechaDesde, fechaHasta, status, idSucursalFiltro, idFormaPago, referencia);
        this.cantidad = this.listaPagos.size();
        this.dineroIngresado=0.0;
        for(int l=0;l<this.listaPagos.size();l++)
        {
            Pago pago = this.listaPagos.get(l);
            if(pago.getTipoPago()!=null && pago.getTipoPago().getNombre().startsWith("adjust") && idFormaPago==-1)
            {
                continue;
            }
            dineroIngresado = dineroIngresado+pago.getMonto();
        }        
        int dineroIngresadoInt =(int)(dineroIngresado*100) ;
        dineroIngresado =((double) dineroIngresadoInt)/100;
        
    }    

    public void setStatus(String status) {
        this.status = status;
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

    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<Pago> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public int getIdSucursalFiltro() {
        return idSucursalFiltro;
    }

    public void setIdSucursalFiltro(int idSucursalFiltro) {
        this.idSucursalFiltro = idSucursalFiltro;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public Double getDineroIngresado() {
        return dineroIngresado;
    }

    public void setDineroIngresado(Double dineroIngresado) {
        this.dineroIngresado = dineroIngresado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(Integer idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public List<Tipo_pago> getListaTipoPago() {
        return listaTipoPago;
    }

    public void setListaTipoPago(List<Tipo_pago> listaTipoPago) {
        this.listaTipoPago = listaTipoPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

}
