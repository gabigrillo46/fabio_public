/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alquiler_impuesto")
public class Alquiler_Impuesto implements Serializable, Cloneable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;
     
   @ManyToOne
     @JoinColumn(name = "id_alquiler", nullable = false)
   private Alquiler alquiler = new Alquiler();  
   
   
      @ManyToOne
     @JoinColumn(name = "id_impuesto", nullable = false)
     private Impuesto impuesto = new Impuesto();
      
      @Column(name = "cantidad")
      private int cantidad = 0;
      
      @Column(name = "precio")
      private float precio=0;
      
      @Column(name = "subtotal")
      private float subtotal=0;
      
      @Column(name = "seleccionado")
      private boolean seleccionado=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
    
    
    
}
