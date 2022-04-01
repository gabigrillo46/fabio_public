package com.piantino.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "multa")
public class Multa implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente clienteInfraccion = new Cliente();    
    
    @ManyToOne
    @JoinColumn(name = "id_cliente_principal", nullable = false)
    private Cliente clientePrincipal = new Cliente();    
    
    @ManyToOne
    @JoinColumn(name = "id_alquiler", nullable = false)
    private Alquiler alquiler = new Alquiler();    
    
    @Column(name = "rego")
    private String rego="";
    
    @ManyToOne
    @JoinColumn(name = "id_autoridad", nullable = false)
    private Autoridad autoridad = new Autoridad();    
    
    @ManyToOne
    @JoinColumn(name = "id_witness", nullable = false)
    private Witness witness = new Witness();    
    
     @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario = new Usuario();    
     
    @Column(name = "fecha_multa")
    private String fechaMulta="";
    
    @Column(name = "fecha_cargada")
    @Temporal(TemporalType.DATE)
    private Date fechaCargada=new Date();
    
    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private Tipo_multa tipo=new Tipo_multa();
    
    @Column(name = "letra_cliente")
    private String letra_cliente="";
    
    @Column(name = "internal_notes")
    private String internal_notes="";
    
    @Column(name = "estado")
    private int estado=0;
    
    @Column(name = "numero")
    private String numero="";
    
    @Column(name = "impreso")
    private int impreso=0;
    
    
    public final static int ESTADO_ELIMINADA = 1;
    
    public final static int ID_TIPO_TOLL =1;

    public int getId() {
        return id;
    }
    
       @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getClienteInfraccion() {
        return clienteInfraccion;
    }

    public void setClienteInfraccion(Cliente clienteInfraccion) {
        this.clienteInfraccion = clienteInfraccion;
    }

    public Cliente getClientePrincipal() {
        return clientePrincipal;
    }

    public void setClientePrincipal(Cliente clientePrincipal) {
        this.clientePrincipal = clientePrincipal;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public Autoridad getAutoridad() {
        return autoridad;
    }

    public void setAutoridad(Autoridad autoridad) {
        this.autoridad = autoridad;
    }

    public Witness getWitness() {
        return witness;
    }

    public void setWitness(Witness witness) {
        this.witness = witness;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(String fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    

    public Date getFechaCargada() {
        return fechaCargada;
    }

    public void setFechaCargada(Date fechaCargada) {
        this.fechaCargada = fechaCargada;
    }



    public String getLetra_cliente() {
        return letra_cliente;
    }

    public void setLetra_cliente(String letra_cliente) {
        this.letra_cliente = letra_cliente;
    }

    public String getInternal_notes() {
        return internal_notes;
    }

    public void setInternal_notes(String internal_notes) {
        this.internal_notes = internal_notes;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Tipo_multa getTipo() {
        return tipo;
    }

    public void setTipo(Tipo_multa tipo) {
        this.tipo = tipo;
    }

    

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getImpreso() {
        return impreso;
    }

    public void setImpreso(int impreso) {
        this.impreso = impreso;
    }

    
}
