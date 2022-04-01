/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Sucursal;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class BookingStatusReportController implements Serializable {

    private Date fechaDesde = null;

    private Date fechaHasta = null;
    
    private Date fechaDesdeDrop = null;

    private Date fechaHastaDrop = null;
    

    private int estado = -2;

    private int idSucursalFiltro = -1;

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    private List<Sucursal> listaSucursales;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    @EJB
    private PagoFacadeLocal pagoEJB;

    private List<Alquiler> alquileresResultado;

    private float totalGranTotal = 0;
    
    private float totalRentalFee = 0;

    private float totalDeuda = 0;

    private int cantidadResultado = 0;

    private float dineroIngresado = 0;

    @PostConstruct
    private void init() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        this.listaSucursales = sucursalEJB.findAll();

    }

    public void viewCarsCustomized(Alquiler alq) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 1200);
        options.put("height", 800);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerSummary");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerSummary", alq.getId() + "");
        PrimeFaces.current().dialog().openDynamic("SummaryAlquilerPagos", options, null);
    }

    public void verSummaryAlquiler(Alquiler alq) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerSummary");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerSummary", alq.getId() + "");

    }

    public void buscarPorFiltro() {

        if (estado == -2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select the state"));
            return;
        }

        alquileresResultado = alquilerEJB.getAlquileresDentroPeriodoConEstado(fechaDesde, fechaHasta, estado, idSucursalFiltro, fechaDesdeDrop, fechaHastaDrop);
        totalDeuda = 0;
        totalGranTotal = 0;
        dineroIngresado = 0;
        totalRentalFee =0;

        for (int j = 0; j < alquileresResultado.size(); j++) {
            Alquiler alquilerResul = (Alquiler) alquileresResultado.get(j);

            float montoIngresadoAlquiler = pagoEJB.getMontoRecibidoDeAlquiler(alquilerResul.getId());
            alquilerResul.setDineroIngresado(montoIngresadoAlquiler);
            float deuda = alquilerResul.getGran_total()-montoIngresadoAlquiler;
            int deudaInt = (int)(deuda*100);
            deuda = ((float)deudaInt)/100;
            alquilerResul.setDeuda(deuda);
            alquileresResultado.remove(alquilerResul);
            alquileresResultado.add(j, alquilerResul);
            totalGranTotal = totalGranTotal + alquilerResul.getGran_total();
            totalRentalFee = totalRentalFee + alquilerResul.getTotal();
            totalDeuda = totalDeuda + alquilerResul.getDeuda();
             dineroIngresado = dineroIngresado + montoIngresadoAlquiler;
        }
        
        /*List<Alquiler> listaAlquileresPeriodo = alquilerEJB.getAlquileresDentroPeriodoSinEstado(fechaDesde, fechaHasta, idSucursalFiltro);
        for(int h=0;h<listaAlquileresPeriodo.size();h++)
        {
            Alquiler alquilerRe = (Alquiler) listaAlquileresPeriodo.get(h);
            float montoIngresadoAlquiler = pagoEJB.getMontoRecibidoDeAlquiler(alquilerRe.getId());
            dineroIngresado = dineroIngresado + montoIngresadoAlquiler;
        }*/

        int totalGranTotalInt = (int) (totalGranTotal * 100);
        totalGranTotal = ((float) totalGranTotalInt) / 100;
        
        int totalRentalFeeInt = (int)(totalRentalFee*100);
        totalRentalFee= ((float)totalRentalFeeInt)/100;

        int totalDeudaInt = (int) (totalDeuda * 100);
        totalDeuda = ((float) totalDeudaInt) / 100;
        
        int dineroIngresadoInt =(int)(dineroIngresado*100);
        dineroIngresado=((float)dineroIngresadoInt)/100;

        cantidadResultado = alquileresResultado.size();
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Alquiler> getAlquileresResultado() {
        return alquileresResultado;
    }

    public boolean esAlquilerReturn(Alquiler alq) {
        if (alq != null) {
            return (alq.getEstado() == Alquiler.ESTADO_RETORNADO);
        } else {
            return false;
        }
    }

    public boolean esAlquilerRepo(Alquiler alq) {
        if (alq != null) {
            return (alq.getEstado() == Alquiler.ESTADO_REPO);
        } else {
            return false;
        }
    }
    
    public boolean esAlquilerCancelado(Alquiler alq)
    {
        if(alq!=null)
        {
            return (alq.getEstado() == Alquiler.ESTADO_CANCELADO);
        }
        else
        {
            return false;
        }
    }

    public String getEstadoStr(Alquiler alquiler) {
        if (alquiler.getEstado() == Alquiler.ESTADO_ALQUILADO) {
            return "Hired";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_RESERVA) {
            return "Reservation";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_CANCELADO) {
            return "Cancelled";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_RETORNADO) {
            return "Returned";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_CARGANDO) {
            if (alquiler.getUsuario() != null) {
                return "Loading by [" + alquiler.getUsuario().getNombre() + " " + alquiler.getUsuario().getApellido() + " on: " + alquiler.getFecha() + "]";
            } else {
                return "Loading";
            }
        } else if (alquiler.getEstado() == Alquiler.ESTADO_VENDIDO) {
            return "Sold";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_REPO) {
            return "Repo";
        } else if(alquiler.getEstado() == Alquiler.ESTADO_COURTESY){
            return "Courtesy";
        }
            

        return "";

    }

    public void setAlquileresResultado(List<Alquiler> alquileresResultado) {
        this.alquileresResultado = alquileresResultado;
    }

    public int getCantidadResultado() {
        return cantidadResultado;
    }

    public void setCantidadResultado(int cantidadResultado) {
        this.cantidadResultado = cantidadResultado;
    }

    public float getTotalGranTotal() {
        return totalGranTotal;
    }

    public void setTotalGranTotal(float totalGranTotal) {
        this.totalGranTotal = totalGranTotal;
    }

    public float getTotalDeuda() {
        return totalDeuda;
    }

    public void setTotalDeuda(float totalDeuda) {
        this.totalDeuda = totalDeuda;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public int getIdSucursalFiltro() {
        return idSucursalFiltro;
    }

    public void setIdSucursalFiltro(int idSucursalFiltro) {
        this.idSucursalFiltro = idSucursalFiltro;
    }

    public float getDineroIngresado() {
        return dineroIngresado;
    }

    public void setDineroIngresado(float dineroIngresado) {
        this.dineroIngresado = dineroIngresado;
    }

    public Date getFechaDesdeDrop() {
        return fechaDesdeDrop;
    }

    public void setFechaDesdeDrop(Date fechaDesdeDrop) {
        this.fechaDesdeDrop = fechaDesdeDrop;
    }

    public Date getFechaHastaDrop() {
        return fechaHastaDrop;
    }

    public void setFechaHastaDrop(Date fechaHastaDrop) {
        this.fechaHastaDrop = fechaHastaDrop;
    }

    public float getTotalRentalFee() {
        return totalRentalFee;
    }

    public void setTotalRentalFee(float totalRentalFee) {
        this.totalRentalFee = totalRentalFee;
    }
    
    public void limpiarFechaPickup()
    {
        this.fechaDesde=null;
        this.fechaHasta=null;
    }
    
    public void limpiarFechaDropoff()
    {
        this.fechaDesdeDrop=null;
        this.fechaHastaDrop=null;
    }
    
    

}
