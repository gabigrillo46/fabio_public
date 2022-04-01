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
public class InvoiceDetalle {
    
    private String sucursalNombre="";
    private String sucursalAbn="";
    private String sucursalDireccion ="";
    private String sucursalTelefono="";
    private String sucursalEmail ="";
    private String licenceDealer="";
    private String invoiceDate ="";
    private String invoiceNumero="";
    private String clienteNumero="";
    private String clienteNombre="";
    private String clienteDireccion="";
    private String clienteMobile ="";
    private String clientePhone="";
    private String clienteDOB="";
    private String clienteLicence ="";
    private String clienteEmail ="";
    private String carMarca="";
    private String carModel ="";
    private String carBodyType="";
    private String carVIN ="";
    private String carNumeroMotor ="";
    private String carStockNumber="";
    private String carColor="";
    private String carOdometer ="";
    private String carTransmicion ="";
    private String carDescripcion ="";
    private String carRegoExp="";
    private String carRego="";
    private String carBuilt="";
    private String carCompliance ="";
    private String carMontoGST ="";
    private String carMontoSinGST ="";
    private String carMontoConGST="";
    private String monto_pagado="";
    private String monto_adeudado="";
    private String fecha_delivery="";
    
    private Integer cliente_tipo =0;
    private String cliente_apellido ="";
    private String cliente_state ="";
    private String cliente_codigo_postal ="";
    private String cliente_dob_dia ="";
    private String cliente_dob_mes ="";
    private String cliente_dob_year ="";
    private String cliente_abn="";
    private String cliente_acn="";
    private String cliente_dealer_licence="";
    private String venta_dia="";
    private String venta_mes ="";
    private String venta_year="";
    private int car_year=0;
    private String sucursal_state ="";
    private String sucursal_codigo_postal="";
    private String delivery_name ="";
    private String delivery_address="";
    private String delivery_abn ="";
    

    public String getSucursalNombre() {
        return sucursalNombre;
    }

    public void setSucursalNombre(String sucursalNombre) {
        this.sucursalNombre = sucursalNombre;
    }

    public String getSucursalAbn() {
        return sucursalAbn;
    }

    public void setSucursalAbn(String sucursalAbn) {
        this.sucursalAbn = sucursalAbn;
    }

    public String getSucursalDireccion() {
        return sucursalDireccion;
    }

    public void setSucursalDireccion(String sucursalDireccion) {
        this.sucursalDireccion = sucursalDireccion;
    }

    public String getSucursalTelefono() {
        return sucursalTelefono;
    }

    public void setSucursalTelefono(String sucursalTelefono) {
        this.sucursalTelefono = sucursalTelefono;
    }

    public String getSucursalEmail() {
        return sucursalEmail;
    }

    public void setSucursalEmail(String sucursalEmail) {
        this.sucursalEmail = sucursalEmail;
    }

    public String getLicenceDealer() {
        return licenceDealer;
    }

    public void setLicenceDealer(String licenceDealer) {
        this.licenceDealer = licenceDealer;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNumero() {
        return invoiceNumero;
    }

    public void setInvoiceNumero(String invoiceNumero) {
        this.invoiceNumero = invoiceNumero;
    }

    public String getClienteNumero() {
        return clienteNumero;
    }

    public void setClienteNumero(String clienteNumero) {
        this.clienteNumero = clienteNumero;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteDireccion() {
        return clienteDireccion;
    }

    public void setClienteDireccion(String clienteDireccion) {
        this.clienteDireccion = clienteDireccion;
    }

    public String getClienteMobile() {
        return clienteMobile;
    }

    public void setClienteMobile(String clienteMobile) {
        this.clienteMobile = clienteMobile;
    }

    public String getClientePhone() {
        return clientePhone;
    }

    public void setClientePhone(String clientePhone) {
        this.clientePhone = clientePhone;
    }

    public String getClienteDOB() {
        return clienteDOB;
    }

    public void setClienteDOB(String clienteDOB) {
        this.clienteDOB = clienteDOB;
    }

    public String getClienteLicence() {
        return clienteLicence;
    }

    public void setClienteLicence(String clienteLicence) {
        this.clienteLicence = clienteLicence;
    }

    public String getCarMarca() {
        return carMarca;
    }

    public void setCarMarca(String carMarca) {
        this.carMarca = carMarca;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarBodyType() {
        return carBodyType;
    }

    public void setCarBodyType(String carBodyType) {
        this.carBodyType = carBodyType;
    }

    public String getCarVIN() {
        return carVIN;
    }

    public void setCarVIN(String carVIN) {
        this.carVIN = carVIN;
    }

    public String getCarNumeroMotor() {
        return carNumeroMotor;
    }

    public void setCarNumeroMotor(String carNumeroMotor) {
        this.carNumeroMotor = carNumeroMotor;
    }

    public String getCarStockNumber() {
        return carStockNumber;
    }

    public void setCarStockNumber(String carStockNumber) {
        this.carStockNumber = carStockNumber;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarOdometer() {
        return carOdometer;
    }

    public void setCarOdometer(String carOdometer) {
        this.carOdometer = carOdometer;
    }

    public String getCarTransmicion() {
        return carTransmicion;
    }

    public void setCarTransmicion(String carTransmicion) {
        this.carTransmicion = carTransmicion;
    }

    public String getCarDescripcion() {
        return carDescripcion;
    }

    public void setCarDescripcion(String carDescripcion) {
        this.carDescripcion = carDescripcion;
    }

    public String getCarRegoExp() {
        return carRegoExp;
    }

    public void setCarRegoExp(String carRegoExp) {
        this.carRegoExp = carRegoExp;
    }

    public String getCarRego() {
        return carRego;
    }

    public void setCarRego(String carRego) {
        this.carRego = carRego;
    }

    public String getCarBuilt() {
        return carBuilt;
    }

    public void setCarBuilt(String carBuilt) {
        this.carBuilt = carBuilt;
    }

    public String getCarCompliance() {
        return carCompliance;
    }

    public void setCarCompliance(String carCompliance) {
        this.carCompliance = carCompliance;
    }

    public String getCarMontoGST() {
        return carMontoGST;
    }

    public void setCarMontoGST(String carMontoGST) {
        this.carMontoGST = carMontoGST;
    }

    public String getCarMontoSinGST() {
        return carMontoSinGST;
    }

    public void setCarMontoSinGST(String carMontoSinGST) {
        this.carMontoSinGST = carMontoSinGST;
    }

    public String getCarMontoConGST() {
        return carMontoConGST;
    }

    public void setCarMontoConGST(String carMontoConGST) {
        this.carMontoConGST = carMontoConGST;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public String getMonto_pagado() {
        return monto_pagado;
    }

    public void setMonto_pagado(String monto_pagado) {
        this.monto_pagado = monto_pagado;
    }

    public String getMonto_adeudado() {
        return monto_adeudado;
    }

    public void setMonto_adeudado(String monto_adeudado) {
        this.monto_adeudado = monto_adeudado;
    }

    public String getFecha_delivery() {
        return fecha_delivery;
    }

    public void setFecha_delivery(String fecha_delivery) {
        this.fecha_delivery = fecha_delivery;
    }

    public Integer getCliente_tipo() {
        return cliente_tipo;
    }

    public void setCliente_tipo(Integer cliente_tipo) {
        this.cliente_tipo = cliente_tipo;
    }



    public String getCliente_apellido() {
        return cliente_apellido;
    }

    public void setCliente_apellido(String cliente_apellido) {
        this.cliente_apellido = cliente_apellido;
    }

    public String getCliente_state() {
        return cliente_state;
    }

    public void setCliente_state(String cliente_state) {
        this.cliente_state = cliente_state;
    }

    public String getCliente_codigo_postal() {
        return cliente_codigo_postal;
    }

    public void setCliente_codigo_postal(String cliente_codigo_postal) {
        this.cliente_codigo_postal = cliente_codigo_postal;
    }

    public String getCliente_dob_dia() {
        return cliente_dob_dia;
    }

    public void setCliente_dob_dia(String cliente_dob_dia) {
        this.cliente_dob_dia = cliente_dob_dia;
    }

    public String getCliente_dob_mes() {
        return cliente_dob_mes;
    }

    public void setCliente_dob_mes(String cliente_dob_mes) {
        this.cliente_dob_mes = cliente_dob_mes;
    }

    public String getCliente_dob_year() {
        return cliente_dob_year;
    }

    public void setCliente_dob_year(String cliente_dob_year) {
        this.cliente_dob_year = cliente_dob_year;
    }

    public String getCliente_abn() {
        return cliente_abn;
    }

    public void setCliente_abn(String cliente_abn) {
        this.cliente_abn = cliente_abn;
    }

    public String getCliente_acn() {
        return cliente_acn;
    }

    public void setCliente_acn(String cliente_acn) {
        this.cliente_acn = cliente_acn;
    }

    public String getCliente_dealer_licence() {
        return cliente_dealer_licence;
    }

    public void setCliente_dealer_licence(String cliente_dealer_licence) {
        this.cliente_dealer_licence = cliente_dealer_licence;
    }

    public String getVenta_dia() {
        return venta_dia;
    }

    public void setVenta_dia(String venta_dia) {
        this.venta_dia = venta_dia;
    }

    public String getVenta_mes() {
        return venta_mes;
    }

    public void setVenta_mes(String venta_mes) {
        this.venta_mes = venta_mes;
    }

    public String getVenta_year() {
        return venta_year;
    }

    public void setVenta_year(String venta_year) {
        this.venta_year = venta_year;
    }

    public int getCar_year() {
        return car_year;
    }

    public void setCar_year(int car_year) {
        this.car_year = car_year;
    }

    public String getSucursal_state() {
        return sucursal_state;
    }

    public void setSucursal_state(String sucursal_state) {
        this.sucursal_state = sucursal_state;
    }

    public String getSucursal_codigo_postal() {
        return sucursal_codigo_postal;
    }

    public void setSucursal_codigo_postal(String sucursal_codigo_postal) {
        this.sucursal_codigo_postal = sucursal_codigo_postal;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getDelivery_abn() {
        return delivery_abn;
    }

    public void setDelivery_abn(String delivery_abn) {
        this.delivery_abn = delivery_abn;
    }

    public String getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }
    
    
}
