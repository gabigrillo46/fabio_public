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
public class Form5Detalle {
    
    private int registroNumero=0;
    private long serialNumber=0;
    private long entryNumber=0;
    private long stockNumber =0;
    private String creationDate="";
    private String dealerName="";
    private String dealerLicence="";
    private String dealerAddress="";
    private String dateFabricacion="";
    private String marca="";
    private String modelo="";
    private String registracion="";
    private String precio="";
    private String kilometros="";
    private String vin="";
    private String numeroMotor="";
    private boolean writtenOff=false;
    private boolean waterDamage=false;
    private boolean majorModifications=false;
    private String ppsr="";
    private boolean guarantee=false;
    private boolean importedSecondHand=false;
    private String ventaFecha="";
    private String ventaKM="";
    private String ventaPrecio="";
    private String ventaRMS="";
    private String ventaTradeIN="";
    private String ventaCompradorDetalles="";
    private boolean cancelado=false;
    private String rmsDate="";
    private String rmsNumber="";
    private String creationDateBarra="";

    public int getRegistroNumero() {
        return registroNumero;
    }

    public void setRegistroNumero(int registroNumero) {
        this.registroNumero = registroNumero;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public long getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(long entryNumber) {
        this.entryNumber = entryNumber;
    }

    public long getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(long stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerLicence() {
        return dealerLicence;
    }

    public void setDealerLicence(String dealerLicence) {
        this.dealerLicence = dealerLicence;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getDateFabricacion() {
        return dateFabricacion;
    }

    public void setDateFabricacion(String dateFabricacion) {
        this.dateFabricacion = dateFabricacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getRegistracion() {
        return registracion;
    }

    public void setRegistracion(String registracion) {
        this.registracion = registracion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }



    public String getKilometros() {
        return kilometros;
    }

    public void setKilometros(String kilometros) {
        this.kilometros = kilometros;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public boolean isWrittenOff() {
        return writtenOff;
    }

    public void setWrittenOff(boolean writtenOff) {
        this.writtenOff = writtenOff;
    }

    public boolean isWaterDamage() {
        return waterDamage;
    }

    public void setWaterDamage(boolean waterDamage) {
        this.waterDamage = waterDamage;
    }

    public boolean isMajorModifications() {
        return majorModifications;
    }

    public void setMajorModifications(boolean majorModifications) {
        this.majorModifications = majorModifications;
    }

    public String getPpsr() {
        return ppsr;
    }

    public void setPpsr(String ppsr) {
        this.ppsr = ppsr;
    }

    public boolean isGuarantee() {
        return guarantee;
    }

    public void setGuarantee(boolean guarantee) {
        this.guarantee = guarantee;
    }

    public boolean isImportedSecondHand() {
        return importedSecondHand;
    }

    public void setImportedSecondHand(boolean importedSecondHand) {
        this.importedSecondHand = importedSecondHand;
    }

    public String getVentaFecha() {
        return ventaFecha;
    }

    public void setVentaFecha(String ventaFecha) {
        this.ventaFecha = ventaFecha;
    }

    public String getVentaKM() {
        return ventaKM;
    }

    public void setVentaKM(String ventaKM) {
        this.ventaKM = ventaKM;
    }

    public String getVentaPrecio() {
        return ventaPrecio;
    }

    public void setVentaPrecio(String ventaPrecio) {
        this.ventaPrecio = ventaPrecio;
    }

    public String getVentaRMS() {
        return ventaRMS;
    }

    public void setVentaRMS(String ventaRMS) {
        this.ventaRMS = ventaRMS;
    }

    public String getVentaTradeIN() {
        return ventaTradeIN;
    }

    public void setVentaTradeIN(String ventaTradeIN) {
        this.ventaTradeIN = ventaTradeIN;
    }

    public String getVentaCompradorDetalles() {
        return ventaCompradorDetalles;
    }

    public void setVentaCompradorDetalles(String ventaCompradorDetalles) {
        this.ventaCompradorDetalles = ventaCompradorDetalles;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public String getRmsDate() {
        return rmsDate;
    }

    public void setRmsDate(String rmsDate) {
        this.rmsDate = rmsDate;
    }

    public String getRmsNumber() {
        return rmsNumber;
    }

    public void setRmsNumber(String rmsNumber) {
        this.rmsNumber = rmsNumber;
    }

    public String getCreationDateBarra() {
        return creationDateBarra;
    }

    public void setCreationDateBarra(String creationDateBarra) {
        this.creationDateBarra = creationDateBarra;
    }
    
    
    
    
}
