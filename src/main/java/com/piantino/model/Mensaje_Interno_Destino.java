
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
@Table(name = "mensaje_interno_destino")
public class Mensaje_Interno_Destino implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destino", nullable = false)
    private Usuario usuarioDestino = new Usuario();

    @ManyToOne
    @JoinColumn(name = "id_mensaje_interno", nullable = false)
    private Mensaje_Interno mensajeInterno = new Mensaje_Interno();

    @Column(name = "numero")
    private String numero = "";

    @Column(name = "leido")
    private int leido = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public Mensaje_Interno getMensajeInterno() {
        return mensajeInterno;
    }

    public void setMensajeInterno(Mensaje_Interno mensajeInterno) {
        this.mensajeInterno = mensajeInterno;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getLeido() {
        return leido;
    }

    public void setLeido(int leido) {
        this.leido = leido;
    }
    
    

}
