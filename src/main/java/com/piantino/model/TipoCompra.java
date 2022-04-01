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

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name="tipo_compra")
public class TipoCompra implements Serializable, Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;
    
    @Column(name = "nombre")
    private String nombre="";

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
    
    
}
