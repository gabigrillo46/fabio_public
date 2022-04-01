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
@Table(name="tarea")
public class Tarea implements Serializable, Cloneable{

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id=-1;
    
    @Column(name="nombre")
    private String nombre="";
    
    @Column(name="descripcion")
    private String descripcion="";
    
    @Column(name="estado")
    private int estado=0;
    
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fecha_vencimiento;

    @ManyToOne
    @JoinColumn(name = "id_usuario_asignado")
    private Usuario usuarioAsignado = new Usuario();
    
    @Column(name ="realizado")
    private boolean realizado=false;
    
    @Column(name = "caducado")
    private boolean caducado=false;
    
    @Column(name = "nota_resolucion")
    private String nota_resolucion="";
    
      @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuarioCreador = new Usuario();

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Usuario getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(Usuario usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }


    
    
                  @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public boolean isCaducado() {
        return caducado;
    }

    public void setCaducado(boolean caducado) {
        this.caducado = caducado;
    }

    public String getNota_resolucion() {
        return nota_resolucion;
    }

    public void setNota_resolucion(String nota_resolucion) {
        this.nota_resolucion = nota_resolucion;
    }
    
    
    
    
    
    
    
}
