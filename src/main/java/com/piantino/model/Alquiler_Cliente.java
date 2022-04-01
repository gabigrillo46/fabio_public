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
@Table(name = "alquiler_cliente")
public class Alquiler_Cliente implements Serializable, Cloneable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;
     
   @ManyToOne
     @JoinColumn(name = "id_cliente", nullable = false)
     private Cliente cliente = new Cliente();
   
      @ManyToOne
     @JoinColumn(name = "id_alquiler", nullable = false)
     private Alquiler alquiler = new Alquiler();
      
      @Column(name = "es_principal")
      private boolean es_principal = false;

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isEs_principal() {
        return es_principal;
    }

    public void setEs_principal(boolean es_principal) {
        this.es_principal = es_principal;
    }
      
      
    
}
