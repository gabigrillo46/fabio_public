package com.piantino.model;

import java.io.Serializable;
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

@Entity
@Table(name="sucursal")
public class Sucursal implements Serializable, Cloneable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id=-1;
    
    @Column(name="nombre")
    private String nombre="";

    @Column(name = "direccion")
    private String direccion ="";
    
    @Column(name = "telefono")
    private String telefono ="";
    
    @Column(name = "estado")
    private int estado =0;
    
    @Column(name = "ciudad")
    private String ciudad="";
    
    @Column(name = "abn")
    private String abn="";
    
    @ManyToOne
    @JoinColumn(name = "id_codigo_postal", nullable = false)    
    private Cod_postal cod_postal=new Cod_postal();
    
    @Column(name="latitud")
    private double latitud=0;
    
    @Column(name="longitud")
    private double longitud=0;

    @ManyToOne
    @JoinColumn(name = "id_pais", nullable = false)    
    private Pais pais=new Pais();
    
        @ManyToOne
    @JoinColumn(name = "id_dealer_licence", nullable = false)    
    private Dealer_licence licence=new Dealer_licence();
    
    
    @Column(name="facebook_page_id")
    private String facebook_page_id="";
    
    @Column(name ="email")
    private String email="";
    
    
    public final static int ESTADO_DISPONIBLE =0;
    public final static int ESTADO_ELIMINADA = 1;
    
    public final static int ID_TODAS = 1;
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.id;
        return hash;
    }


    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Cod_postal getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(Cod_postal cod_postal) {
        this.cod_postal = cod_postal;
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
        final Sucursal other = (Sucursal) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sucursal{" + "id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", estado=" + estado + '}';
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getFacebook_page_id() {
        return facebook_page_id;
    }

    public void setFacebook_page_id(String facebook_page_id) {
        this.facebook_page_id = facebook_page_id;
    }

    public Dealer_licence getLicence() {
        return licence;
    }

    public void setLicence(Dealer_licence licence) {
        this.licence = licence;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
