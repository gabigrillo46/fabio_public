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
@Table(name = "impuesto")
public class Impuesto implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;

    @Column(name = "nombre")
    private String nombre = "";

    @Column(name = "price")
    private float price = 0;

    @Column(name = "max_price")
    private float max_price = 0;

    @Column(name = "obligatorio")
    private boolean  obligatorio = false;

    @Column(name = "gst_inc")
    private boolean gst_inc =false;

    @Column(name = "editable")
    private boolean editable = false;

       @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private Tipo_impuesto tipo_impuesto = new Tipo_impuesto();

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal = new Sucursal();

    @Column(name = "estado")
    private int estado=0;
    
   @Column(name="tipo_asociado")
   private int tipo_asociado=-1;
    
    public static final int ESTADO_ELIMINADO=1;
    public static final int TIPO_ASOCIADO_TOLL=0;
    public static final int TIPO_ASOCIADO_LATE_PAYMENT=1;
    public static final int TIPO_ASOCIADO_BOND=2;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMax_price() {
        return max_price;
    }

    public void setMax_price(float max_price) {
        this.max_price = max_price;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public boolean isGst_inc() {
        return gst_inc;
    }

    public void setGst_inc(boolean gst_inc) {
        this.gst_inc = gst_inc;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    

    public Tipo_impuesto getTipo_impuesto() {
        return tipo_impuesto;
    }

    public void setTipo_impuesto(Tipo_impuesto tipo_impuesto) {
        this.tipo_impuesto = tipo_impuesto;
    }



    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        return "Impuesto{" + "id=" + id + ", nombre=" + nombre + ", price=" + price + ", max_price=" + max_price + ", obligatorio=" + obligatorio + ", gst_inc=" + gst_inc + ", editable=" + editable + ", tipo_impuesto=" + tipo_impuesto + ", sucursal=" + sucursal + ", estado=" + estado + ", tipo_asociado=" + tipo_asociado + '}';
    }    
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.id;
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
        final Impuesto other = (Impuesto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTipo_asociado() {
        return tipo_asociado;
    }

    public void setTipo_asociado(int tipo_asociado) {
        this.tipo_asociado = tipo_asociado;
    }


    

}
