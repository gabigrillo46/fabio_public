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
import javax.persistence.Table;

@Entity
@Table(name="tipo_impuesto")
public class Tipo_impuesto implements Serializable, Cloneable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;
    
    @Column(name = "nombre")
    private String nombre="";
    
    @Column(name = "estado")
    private int estado=0;
    
    public static final int  ESTADO_ELIMINADO =1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    
}
