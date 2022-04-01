package com.piantino.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
@Table(name = "cliente")
public class Cliente implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;

    @Column(name = "nombre")
    private String nombre = "";

    @Column(name = "apellido")
    private String apellido = "";

    @Column(name = "email")
    private String email = "";

    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date DOB;

    @Column(name = "telefono")
    private String telefono = "";

    @Column(name = "movil")
    private String movil = "";

    @Column(name = "licencia_numero")
    private String licencia_numero = "";

    @Column(name = "fecha_vencimiento_lic")
    @Temporal(TemporalType.DATE)
    private Date fecha_vencimiento_lic;

    @Column(name = "direccion")
    private String direccion = "";

    @Column(name = "suburbio")
    private String suburbio = "";

    @ManyToOne
    @JoinColumn(name = "id_state", nullable = false)
    private State state = new State();

    @Column(name = "codigo_postal")
    private String codigo_postal = "";

    @Column(name = "estado")
    private int estado = 0;

    @Column(name = "observacion")
    private String observacion = "";

    @Column(name = "lic_infinita")
    private boolean lic_infinita = false;

    @Column(name = "paisSTR")
    private String pais = "";

    @Column(name = "otorgadaEN")
    private String otorgadaEN = "";

    @Column(name = "good_customer")
    private boolean good_customer = false;
    
    @Column(name = "tipo")
    private int tipo=0;
    
    public static final int TIPO_CUSTOMER =0;
    public static final int TIPO_DEALER =1;
    public static final int TIPO_CORPORATION =2;
    
    @Column(name = "dealer_licence")
    private String dealer_licence="";
    
    @Column(name ="abn")
    private String abn="";
    
    @Column(name = "acn")
    private String acn ="";
    

    public static final int ESTADO_DISPONIBLE = 0;
    public static final int ESTADO_ELIMINADO = 1;

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
        if (nombre != null && nombre.trim().length() > 0) {
            this.nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1, nombre.trim().length()).toLowerCase();
        } else {
            this.nombre = nombre;
        }
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido != null && apellido.trim().length() > 0) {
            this.apellido = apellido.substring(0, 1).toUpperCase() + apellido.substring(1, apellido.trim().length()).toLowerCase();
        } else {
            this.apellido = apellido;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getLicencia_numero() {
        return licencia_numero;
    }

    public void setLicencia_numero(String licencia_numero) {
        this.licencia_numero = licencia_numero;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Date getFecha_vencimiento_lic() {
        return fecha_vencimiento_lic;
    }

    public void setFecha_vencimiento_lic(Date fecha_vencimiento_lic) {
        this.fecha_vencimiento_lic = fecha_vencimiento_lic;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSuburbio() {
        return suburbio;
    }

    public void setSuburbio(String suburbio) {
        this.suburbio = suburbio;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isLic_infinita() {
        return lic_infinita;
    }

    public void setLic_infinita(boolean lic_infinita) {
        this.lic_infinita = lic_infinita;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", DOB=" + DOB + ", telefono=" + telefono + ", movil=" + movil + ", licencia_numero=" + licencia_numero + ", fecha_vencimiento_lic=" + fecha_vencimiento_lic + ", direccion=" + direccion + ", suburbio=" + suburbio + ", state=" + state + ", codigo_postal=" + codigo_postal + ", estado=" + estado + ", observacion=" + observacion + ", lic_infinita=" + lic_infinita + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
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
        final Cliente other = (Cliente) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getOtorgadaEN() {
        return otorgadaEN;
    }

    public void setOtorgadaEN(String otorgadaEN) {
        this.otorgadaEN = otorgadaEN;
    }

    public boolean isGood_customer() {
        return good_customer;
    }

    public void setGood_customer(boolean good_customer) {
        this.good_customer = good_customer;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDealer_licence() {
        return dealer_licence;
    }

    public void setDealer_licence(String dealer_licence) {
        this.dealer_licence = dealer_licence;
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }

    public String getAcn() {
        return acn;
    }

    public void setAcn(String acn) {
        this.acn = acn;
    }
    
    

}
