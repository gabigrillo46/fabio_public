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

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "auto_precios")
public class Auto_precios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    @Column(name = "descripcion")
    private String descripcion = "";

    @Column(name = "tipo_tiempo")
    private int tipo_tiempo = -1;

    @Column(name = "monto")
    private float monto = 0;

    @ManyToOne
    @JoinColumn(name = "id_auto", nullable = false)
    private Auto auto = new Auto();

    @Column(name = "defecto")
    private boolean defecto = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo_tiempo() {
        return tipo_tiempo;
    }

    public void setTipo_tiempo(int tipo_tiempo) {
        this.tipo_tiempo = tipo_tiempo;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auto_precios other = (Auto_precios) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Auto_precios{" + "id=" + id + ", descripcion=" + descripcion + ", tipo_tiempo=" + tipo_tiempo + ", monto=" + monto + ", auto=" + auto + '}';
    }

    public boolean isDefecto() {
        return defecto;
    }

    public void setDefecto(boolean defecto) {
        this.defecto = defecto;
    }
    
    

}
