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
public class TransferRegoDealerDetalle {
    
    private String rego="";
    private String fechaCompra ="";
    private String VIN="";
    private String make="";
    private String odometer ="";
    private String vendedorInfo="";
    private String vendedorLicencia="";
    private String marketValue="";

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getVendedorInfo() {
        return vendedorInfo;
    }

    public void setVendedorInfo(String vendedorInfo) {
        this.vendedorInfo = vendedorInfo;
    }

    public String getVendedorLicencia() {
        return vendedorLicencia;
    }

    public void setVendedorLicencia(String vendedorLicencia) {
        this.vendedorLicencia = vendedorLicencia;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }
    
    
    
    
}
