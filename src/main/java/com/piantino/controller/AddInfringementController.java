package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Sucursal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;


@Named
@ViewScoped
public class AddInfringementController implements Serializable {

    private String referencia;

    private String apellido;

    private String rego;

    private Date fechaDesde;

    private Date fechaHasta;

    private String telefono;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private List<Alquiler> listaAlquileresResultado = new <Alquiler>ArrayList();

    private Alquiler alquilerSeleccionado = null;

    private List<Sucursal> listaSucursales;

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    private int idSucursal = -1;

    @PostConstruct
    private void init() {
        listaSucursales = sucursalEJB.findAll();

    }

    public boolean esCancelado(int estado) {
        return (estado == Alquiler.ESTADO_CANCELADO);
    }

    public boolean estaNoCancelado(int estado) {
        return (estado != Alquiler.ESTADO_CANCELADO);
    }

    public boolean esAlquilado(int estado) {
        return (estado == Alquiler.ESTADO_ALQUILADO);
    }

    public boolean esReservado(int estado) {
        return (estado == Alquiler.ESTADO_RESERVA);
    }

    public String getEstadoStr(int estado) {
        if (estado == Alquiler.ESTADO_ALQUILADO) {
            return "Hired";
        } else if (estado == Alquiler.ESTADO_RESERVA) {
            return "Reservation";
        } else if (estado == Alquiler.ESTADO_CANCELADO) {
            return "Cancelled";
        } else if (estado == Alquiler.ESTADO_RETORNADO) {
            return "Returned";
        } else if (estado == Alquiler.ESTADO_CARGANDO) {
            return "Loading";
        } else if (estado == Alquiler.ESTADO_VENDIDO) {
            return "Sold";
        } else if (estado == Alquiler.ESTADO_REPO) {
            return "Repo";
        } else if (estado == Alquiler.ESTADO_COURTESY){
            return "Courtesy";
        }
        

        return "";

    }

    public void buscarAlquilerPorFiltro() {

        if (referencia == null || referencia.trim().length() == 0) {
            referencia = null;
        }
        if (rego == null || rego.trim().length() == 0) {
            rego = null;
        }
        if (fechaDesde == null || fechaHasta == null) {
            fechaDesde = null;
            fechaHasta = null;
        }
        if (apellido == null || apellido.trim().length() == 0) {
            apellido = null;
        }
        if (telefono == null || telefono.trim().length() == 0) {
            telefono = null;
        }
        this.listaAlquileresResultado = alquilerEJB.getAlquilerPorFiltro(rego, apellido, fechaDesde, referencia, telefono, idSucursal, fechaHasta, false,-1);
        if (this.listaAlquileresResultado.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "No result found"));
        }
        System.out.println(this.listaAlquileresResultado.size());
    }

    public List<Alquiler> getListaAlquileresResultado() {
        return listaAlquileresResultado;
    }

    public void setListaAlquileresResultado(List<Alquiler> listaAlquileresResultado) {
        this.listaAlquileresResultado = listaAlquileresResultado;
    }

    public Alquiler getAlquilerSeleccionado() {
        return alquilerSeleccionado;
    }

    public void setAlquilerSeleccionado(Alquiler alquilerSeleccionado) {
        this.alquilerSeleccionado = alquilerSeleccionado;
    }

    public void onRowSelect(SelectEvent event) {
        this.setAlquilerSeleccionado((Alquiler) event.getObject());
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public Date getFecha() {
        return fechaDesde;
    }

    public void setFecha(Date fecha) {
        this.fechaDesde = fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
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

}
