/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.report.Dtos;

/**
 *
 * @author Gabriel
 */
public class NoticeDisposalDetalle {
    
    private String rego ="";
    private String fechaVenta ="";
    private String precioVenta ="";
    private String vin ="";
    private String odometer ="";
    private String compradorFullName="";
    private String compradorDireccion ="";
    private String licencia ="";

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getCompradorFullName() {
        return compradorFullName;
    }

    public void setCompradorFullName(String compradorFullName) {
        this.compradorFullName = compradorFullName;
    }

    public String getCompradorDireccion() {
        return compradorDireccion;
    }

    public void setCompradorDireccion(String compradorDireccion) {
        this.compradorDireccion = compradorDireccion;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
    
    
    
    
    
    
}
