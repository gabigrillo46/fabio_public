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
@Table(name = "autoridad")
public class Autoridad implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;
    
    @Column(name = "nombre")
    private String nombre="";
    
    @Column(name = "tool_road_autoridad")
    private boolean tool_road_autoridad=false;
    
    @Column(name = "text_name")
    private String text_name="";
    
    @ManyToOne
    @JoinColumn(name = "id_pais", nullable = false)
    private Pais pais = new Pais();    
    
    @Column(name = "telefono")
    private String telefono="";
    
    @Column(name = "direccion")
    private String direccion="";
    
    @Column(name = "ciudad")
    private String ciudad="";
    
    @ManyToOne
    @JoinColumn(name = "id_state", nullable = false)
    private State state = new State();
    
    @Column(name = "codigo_postal")
    private String codigo_postal="";
    
    @Column(name = "email")
    private String email="";
    
    @Column(name = "website")
    private String website="";
    
    @Column(name = "estado")
    private int estado=0;
    
    public static final int ESTADO_HABILITADO=0;
    public static final int ESTADO_ELIMINADO=1;
    

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

    public boolean isTool_road_autoridad() {
        return tool_road_autoridad;
    }

    public void setTool_road_autoridad(boolean tool_road_autoridad) {
        this.tool_road_autoridad = tool_road_autoridad;
    }

    public String getText_name() {
        return text_name;
    }

    public void setText_name(String text_name) {
        this.text_name = text_name;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
      @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    
    
}
