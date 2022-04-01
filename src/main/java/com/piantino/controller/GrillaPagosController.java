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
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Pago;
import com.piantino.model.Sucursal;
import com.piantino.model.Tipo_pago;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class GrillaPagosController implements Serializable {

    @EJB
    private PagoFacadeLocal PagoEJB;

    private Pago modeloSeleccionada = null;

    private String numeroComprobante;

    private String status;

    private Integer tipo = null;

    private Date fechaDesde;

    private Date fechaHasta;

    private String referencia;

    private String nombreCliente = "";

    private List<Pago> listaPagos;

    private boolean mostrarPago = false;

    private Pago pagoActual;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private Alquiler alquilerBD;

    private List<Sucursal> listaSucursales;

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    private List<Tipo_pago> listaTipoPago;

    @EJB
    private Tipo_pagoFacadeLocal tipoPagoEJB;

    private Usuario usuarioLogueado;

    @PostConstruct
    private void init() {
        listaSucursales = sucursalEJB.findAll();
        listaTipoPago = tipoPagoEJB.findAll();
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    }

    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<Pago> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean esPagoAprovado(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().startsWith("Approved") && !pago.getStatus().equalsIgnoreCase(Pago.Texto_Aprobado_warning) && !pago.getStatus().equalsIgnoreCase(Pago.Texto_Aprobado_warning_balanceado)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esPagoWarning(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().equalsIgnoreCase(Pago.Texto_Aprobado_warning)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esPagoWarningBalanceado(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().equalsIgnoreCase(Pago.Texto_Aprobado_warning_balanceado)) {
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

    public void verContrato(int idAlquiler) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerSummary");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerSummary", idAlquiler + "");
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void buscarPorFiltro() {
        if (referencia == null || referencia.trim().length() == 0) {
            referencia = null;
        }
        if (numeroComprobante == null || numeroComprobante.trim().length() == 0) {
            numeroComprobante = null;
        }

        if (status == null || status.trim().length() == 0) {
            status = null;
        }
        if (tipo == -1) {
            tipo = null;
        }
        this.listaPagos = PagoEJB.buscarPorFiltro(referencia, numeroComprobante, fechaDesde, fechaHasta, status, tipo);
        System.out.println(this.listaPagos.size());
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public boolean isMostrarPago() {
        return mostrarPago;
    }

    public void setMostrarPago(boolean mostrarPago) {
        this.mostrarPago = mostrarPago;
    }

    public Pago getPagoActual() {
        return pagoActual;
    }

    public void setPagoActual(Pago pagoActual) {
        this.pagoActual = pagoActual;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Tipo_pago> getListaTipoPago() {
        return listaTipoPago;
    }

    public void setListaTipoPago(List<Tipo_pago> listaTipoPago) {
        this.listaTipoPago = listaTipoPago;
    }

    public void guardarPago() {
        this.alquilerBD = alquilerEJB.getAlquilerActivoPorId(this.pagoActual.getAlquiler().getId());
        if (this.pagoActual != null && this.alquilerBD != null && this.alquilerBD.getId() > -1 && this.usuarioLogueado != null) {
            this.pagoActual.setAlquiler(this.alquilerBD);
            this.pagoActual.setUsuario(usuarioLogueado);
            if (this.pagoActual.getId() == -1) {
                this.pagoActual.setNota(this.pagoActual.getNota() + " (by " + this.usuarioLogueado.getNombre() + " " + this.usuarioLogueado.getApellido() + " )");
            }
            float pago = this.pagoActual.getMonto();

            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));

            int pagoInt = (int) (pago * 100);
            pago = ((float) pagoInt) / 100;

            this.pagoActual.setMonto(pago);
            if (this.pagoActual.getStatus().startsWith(Pago.Texto_Aprobado)) {
                if (this.pagoActual.getMonto() != alquilerBD.getRate_per_day()) {
                    if (alquilerBD.isFortnightly()) {
                        float montoQuincental = alquilerBD.getRate_per_day() * 2;
                        if (montoQuincental != this.pagoActual.getMonto()) {
                            this.pagoActual.setStatus(Pago.Texto_Aprobado_warning);
                        } else {
                            this.pagoActual.setStatus(Pago.Texto_Aprobado);
                        }
                    } else {
                        this.pagoActual.setStatus(Pago.Texto_Aprobado_warning);
                    }
                } else {
                    this.pagoActual.setStatus(Pago.Texto_Aprobado);
                }
            }
            if (pagoActual.getId() == -1) {
                PagoEJB.create(pagoActual);
            } else {
                PagoEJB.edit(pagoActual);
            }

            this.setMostrarPago(false);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successful registration"));
            this.pagoActual = new Pago();
            this.pagoActual.setFecha_hora(c.getTime());
            this.buscarPorFiltro();

        }
    }

    public void cancelarPago() {
        this.pagoActual = new Pago();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        this.pagoActual.setFecha_hora(c.getTime());
        this.setMostrarPago(false);
    }

    public void editarPago(long idPago) {
        this.pagoActual = PagoEJB.find(idPago);
        this.mostrarPago = true;

    }

}
