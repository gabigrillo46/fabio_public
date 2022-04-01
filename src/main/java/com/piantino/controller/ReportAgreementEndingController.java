/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Alquiler_ClienteFacadeLocal;
import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Cliente;
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
public class ReportAgreementEndingController implements Serializable {

    private Date fechaDesde = new Date();

    private Date fechaHasta = new Date();

    private List<Alquiler> listaAlquileresResultado = new ArrayList<>();

    private int cantidadResultados = 0;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private Calendar c;
    
    @EJB
    private Alquiler_ClienteFacadeLocal alquilerClienteEJB;

    @PostConstruct
    private void init() {
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        fechaDesde.setTime(c.getTime().getTime());
        fechaHasta.setTime(c.getTime().getTime());

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

    public List<Alquiler> getListaAlquileresResultado() {
        return listaAlquileresResultado;
    }

    public void setListaAlquileresResultado(List<Alquiler> listaAlquileresResultado) {
        this.listaAlquileresResultado = listaAlquileresResultado;
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(int cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    public void buscarAlquileresPorFiltro() {
        if (this.fechaDesde != null && this.fechaHasta != null && this.fechaHasta.before(this.fechaDesde)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The date from cannot be greater than the date to"));
            return;
        }
        this.listaAlquileresResultado = alquilerEJB.getAlquileresTerminanPeriodo(fechaDesde, fechaHasta);
        this.cantidadResultados = this.listaAlquileresResultado.size();

    }

    public void verDetalleContrato(int idAlquiler) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerDato", idAlquiler + "");
    }

    public String getFechaFormat(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fecha);
        } else {
            return "";
        }
    }
    
    public String nombreCliente(int idAlquiler)
    {
        String resultado = "";
       Alquiler_Cliente alquilerCliente = alquilerClienteEJB.buscarClientePrincipalPorAlquiler(idAlquiler);
       if(alquilerCliente!=null && alquilerCliente.getCliente()!=null )
       {
           Cliente cliente = alquilerCliente.getCliente();
           resultado = cliente.getNombre()+" "+cliente.getApellido();           
       }
       return resultado;
                
    }
    
    

}
