/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.Invoice_Mecanico_DetalleFacadeLocal;
import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Invoice_Mecanico_Detalle;
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
public class GrillaInvoiceMecanicoController implements Serializable {

    private String rego = "";

    private int id_tipo = -1;

    private Date fechaDesde;

    private Date fechaHasta;
    
    private int cantidadResultados=0;
    
    private List<Invoice_Mecanico_Detalle> listaInvoiceMecanicoDetalle = new ArrayList<>();
    
    @EJB
    private Invoice_Mecanico_DetalleFacadeLocal invoiceMecanicoDetalleEJB;
    
    private Calendar c;
    
    private double dineroIngresado=0;
    
    private double dineroEgresado = 0;
    
    private double balance= 0;
    
    private String invoiceNumber ="";
    
    
    @PostConstruct
    private void init() {
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        fechaDesde = null;
        fechaHasta = null;
    }
    
    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
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

    public List<Invoice_Mecanico_Detalle> getListaInvoiceMecanicoDetalle() {
        return listaInvoiceMecanicoDetalle;
    }

    public void setListaInvoiceMecanicoDetalle(List<Invoice_Mecanico_Detalle> listaInvoiceMecanicoDetalle) {
        this.listaInvoiceMecanicoDetalle = listaInvoiceMecanicoDetalle;
    }

    
    public void buscarPorFiltro()
    {
        this.listaInvoiceMecanicoDetalle = this.invoiceMecanicoDetalleEJB.buscarPorFiltro(rego, id_tipo, fechaDesde, fechaHasta, invoiceNumber);
        this.cantidadResultados=this.listaInvoiceMecanicoDetalle.size();
        this.dineroEgresado=0;
        this.dineroIngresado=0;
        this.balance=0;
        for(int g=0;g<this.listaInvoiceMecanicoDetalle.size();g++)
        {
            Invoice_Mecanico_Detalle invoiceMecanicoDetalle = this.listaInvoiceMecanicoDetalle.get(g);
            if(this.esGasto(invoiceMecanicoDetalle))
            {
                this.dineroEgresado=this.dineroEgresado+invoiceMecanicoDetalle.getSubtotal();                        
            }
            else if(this.esIngreso(invoiceMecanicoDetalle))
            {
                this.dineroIngresado=this.dineroIngresado+invoiceMecanicoDetalle.getSubtotal();
            }
        }
        this.balance = this.dineroIngresado-this.dineroEgresado;
        
        int dineroIngresadoInt = (int)(this.dineroIngresado*100);
        this.dineroIngresado = ((float)dineroIngresadoInt)/100;
        
        int dineroEgresadoInt = (int)(this.dineroEgresado*100);
        this.dineroEgresado = ((float)dineroEgresadoInt)/100;
        
        int balanceInt = (int)(this.balance*100);
        this.balance =((float)balanceInt)/100;
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(int cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }
    
    public void ingresarInvoice()
    {
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idInvoiceMecanico");
        System.out.println("ingresar invoice");
    }
    
    public void verDetalleInvoice(long id)
    {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idInvoiceMecanico");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idInvoiceMecanico", id + "");
        
    }
    
    public String getDescripcionTipo(int tipo)
    {
        if(tipo == Invoice_Mecanico_Detalle.TIPO_STOCK)
        {
            return "Stock";
        }
        else if(tipo == Invoice_Mecanico_Detalle.TIPO_INCOME)
        {
            return "Income";
        }
        else if(tipo == Invoice_Mecanico_Detalle.TIPO_OUR_CAR)
        {
            return "Our car";
        }
        else if(tipo == Invoice_Mecanico_Detalle.TIPO_EXTERNAL_CAR)
        {
            return "External car";
        }
        else
        {
            return "";            
        }
    }
    
    public boolean esIngreso(Invoice_Mecanico_Detalle invoiceMecanicoDetalle)
    {
        if(invoiceMecanicoDetalle!=null && invoiceMecanicoDetalle.getInvoiceMecanico()!=null && invoiceMecanicoDetalle.getInvoiceMecanico().getSentido()==Invoice_Mecanico.SENTIDO_INGRESO)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean esGasto(Invoice_Mecanico_Detalle invoiceMecanicoDetalle)
    {
        
        if(invoiceMecanicoDetalle!=null && invoiceMecanicoDetalle.getInvoiceMecanico()!=null &&  invoiceMecanicoDetalle.getInvoiceMecanico().getSentido()==Invoice_Mecanico.SENTIDO_GASTO)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public String getDineroIngresado(Invoice_Mecanico_Detalle invoiceMecanicoDetalle)
    {
        if(invoiceMecanicoDetalle!=null && invoiceMecanicoDetalle.getInvoiceMecanico()!=null && invoiceMecanicoDetalle.getInvoiceMecanico().getSentido()==Invoice_Mecanico.SENTIDO_INGRESO)
        {
            return "$"+invoiceMecanicoDetalle.getSubtotal();                 
        }
        else
        {
            return "";
        }
    }
    
    public String getDineroGastado(Invoice_Mecanico_Detalle invoiceMecanicoDetalle)
    {
        if(invoiceMecanicoDetalle!=null && invoiceMecanicoDetalle.getInvoiceMecanico()!=null && invoiceMecanicoDetalle.getInvoiceMecanico().getSentido()==Invoice_Mecanico.SENTIDO_GASTO)
        {
            return "$"+invoiceMecanicoDetalle.getSubtotal();
        }
        else
        {
            return "";
        }
    }

    public double getDineroIngresado() {
        return dineroIngresado;
    }

    public void setDineroIngresado(double dineroIngresado) {
        this.dineroIngresado = dineroIngresado;
    }

    public double getDineroEgresado() {
        return dineroEgresado;
    }

    public void setDineroEgresado(double dineroEgresado) {
        this.dineroEgresado = dineroEgresado;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public String balanceString()
    {
        int balanceInt  = (int)(balance*100);
        balance = (double)balanceInt/100;
        String balanceStr = "$";
        balanceStr=balanceStr+balance;
        return balanceStr;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    
    
    
    

}
