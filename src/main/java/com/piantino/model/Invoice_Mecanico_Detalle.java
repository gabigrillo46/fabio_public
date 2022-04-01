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
@Table(name = "invoice_mecanico_detalle")
public class Invoice_Mecanico_Detalle implements Serializable, Cloneable {    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    @ManyToOne
    @JoinColumn(name = "id_invoice")
    private Invoice_Mecanico invoiceMecanico=null;
    
    @Column(name = "nuestro")
    private boolean nuestro = false;
    
    @Column(name ="rego")
    private String rego = "";
    
    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca = new Marca();

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo = new Modelo();
    
    @Column(name = "year")
    private int year =0;
    
    @Column(name = "color")
    private String color ="";
    
    @Column(name = "tipo")
    private int tipo = -1;
    
    
    @ManyToOne
    @JoinColumn(name = "id_auto")
    private Auto auto = null;
    
        @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente = null;
        
    @Column(name = "descripcion")
    private String descripcion="";
    
    @Column(name = "cantidad")
    private int cantidad =0;
    
    @Column(name ="subtotal")
    private double subtotal =0;
    
    @Column(name = "gst")
    private double gst=0;
    
    @Column(name = "subtotal_sin_gst")
    private double subtotal_sin_gst=0;
    
    @Column(name = "vin")
    private String vin="";
    
    @Column(name = "inc_gst")
    private boolean inc_gst=false;
    
    @Column(name = "precio_unitario")
    private double precio_unitario=0;
    
    public static final int TIPO_OUR_CAR=1;
    public static final int TIPO_EXTERNAL_CAR=2;
    public static final int TIPO_STOCK=3;
    public static final int TIPO_INCOME=4;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNuestro() {
        return nuestro;
    }

    public void setNuestro(boolean nuestro) {
        this.nuestro = nuestro;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
    
    


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Invoice_Mecanico_Detalle other = (Invoice_Mecanico_Detalle) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public Invoice_Mecanico getInvoiceMecanico() {
        return invoiceMecanico;
    }

    public void setInvoiceMecanico(Invoice_Mecanico invoiceMecanico) {
        this.invoiceMecanico = invoiceMecanico;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSubtotal_sin_gst() {
        return subtotal_sin_gst;
    }

    public void setSubtotal_sin_gst(double subtotal_sin_gst) {
        this.subtotal_sin_gst = subtotal_sin_gst;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    @Override
    public String toString() {
        return "Invoice_Mecanico_Detalle{" + "id=" + id + ", invoiceMecanico=" + invoiceMecanico + ", nuestro=" + nuestro + ", rego=" + rego + ", marca=" + marca + ", modelo=" + modelo + ", year=" + year + ", color=" + color + ", tipo=" + tipo + ", auto=" + auto + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", subtotal=" + subtotal + ", gst=" + gst + ", subtotal_sin_gst=" + subtotal_sin_gst + ", vin=" + vin + ", inc_gst=" + inc_gst + ", precio_unitario=" + precio_unitario + '}';
    }       

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public boolean isInc_gst() {
        return inc_gst;
    }

    public void setInc_gst(boolean inc_gst) {
        this.inc_gst = inc_gst;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }    
}
