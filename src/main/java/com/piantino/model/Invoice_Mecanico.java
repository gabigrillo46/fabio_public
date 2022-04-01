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

/**
 *
 * @author Gabriel
 */
@Entity
@Table(name = "invoice_mecanico")
public class Invoice_Mecanico implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    //proveedor
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente proveedor = null;
    
    @Column(name ="invoice_number")
    private String invoice_number="";

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha = null;
    
    @Column(name = "total_sin_gst")
    private double total_sin_gst =0;
    
    @Column(name = "gst")
    private double gst=0;
    
    @Column(name = "total")
    private double total=0;
    
    @Column(name = "sentido")
    private int sentido=-1;
    
    public final static int SENTIDO_INGRESO=0;
    public final static int SENTIDO_GASTO=1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal_sin_gst() {
        return total_sin_gst;
    }

    public void setTotal_sin_gst(double total_sin_gst) {
        this.total_sin_gst = total_sin_gst;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getSentido() {
        return sentido;
    }

    public void setSentido(int sentido) {
        this.sentido = sentido;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Invoice_Mecanico other = (Invoice_Mecanico) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "invoice_mecanico{" + "id=" + id + ", cliente=" + proveedor + ", invoice_number=" + invoice_number + ", fecha=" + fecha + ", total_sin_gst=" + total_sin_gst + ", gst=" + gst + ", total=" + total + ", sentido=" + sentido + ", SENTIDO_INGRESO=" + SENTIDO_INGRESO + ", SENTIDO_GASTO=" + SENTIDO_GASTO + '}';
    }
    
        @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Cliente getProveedor() {
        return proveedor;
    }

    public void setProveedor(Cliente proveedor) {
        this.proveedor = proveedor;
    }

    
}
