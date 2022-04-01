/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tarjeta")
public class Tarjeta implements Serializable, Cloneable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id=-1;
    
    @Column(name="numero")
    private String numero="";

    @Column(name = "nombre")
    private String nombre="";
    
    @Column(name = "fechaVenc")
    @Temporal(TemporalType.DATE)
    private Date fechaVenc;
    
       @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)    
    private Tipo_Tarjeta tipoTarjeta = new  Tipo_Tarjeta();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public Tipo_Tarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(Tipo_Tarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }
       
       
    
}
