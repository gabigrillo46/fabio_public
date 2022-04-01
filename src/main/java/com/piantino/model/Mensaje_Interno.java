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
@Table(name = "mensaje_interno")
public class Mensaje_Interno implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    @ManyToOne
    @JoinColumn(name = "id_usuario_creador", nullable = false)
    private Usuario usuarioCreador = new Usuario();

    @Column(name = "id_alquiler")
    private int id_alquiler = -1;

    @Column(name = "texto")
    private String texto = "";

    @Column(name = "fecha_hora")
    private String fecha_hora = "";

    @Column(name = "estado")
    private int estado = -1;

    @Column(name = "id_primero")
    private long id_primero = -1;

    
    public static final int ESTADO_ACTIVO = 0;
    public static final int ESTADO_BAJA = 1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_alquiler() {
        return id_alquiler;
    }

    public void setId_alquiler(int id_alquiler) {
        this.id_alquiler = id_alquiler;
    }

    public long getId_primero() {
        return id_primero;
    }

    public void setId_primero(long id_primero) {
        this.id_primero = id_primero;
    }

}
