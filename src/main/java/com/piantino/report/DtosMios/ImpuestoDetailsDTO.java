/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.report.DtosMios;

/**
 *
 * @author gaby
 */
public class ImpuestoDetailsDTO {
    
    private String nombre_impuesto;
    private String cantidad="";
    private String semanas="";
    private float subtotal;
    private float total;

    public String getNombre_impuesto() {
        return nombre_impuesto;
    }

    public void setNombre_impuesto(String nombre_impuesto) {
        this.nombre_impuesto = nombre_impuesto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getSemanas() {
        return semanas;
    }

    public void setSemanas(String semanas) {
        this.semanas = semanas;
    }


    
    
    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    

}
