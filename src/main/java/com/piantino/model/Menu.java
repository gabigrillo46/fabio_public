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

@Entity
@Table(name="menu")
public class Menu implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id=-1;
    
    @Column(name="nombre")
    private String nombre="";
    
    @Column(name = "tipo")
    private String tipo="";
    
    @ManyToOne
    @JoinColumn(name = "codigo_submenu")
    private Menu submenu; 
    
    @Column(name = "url")
    private String url="";
    
    @Column(name = "super_usuario")
    private int super_usuario=0;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    public boolean esSuperUsuario()
    {
        if(this.getSuper_usuario()==1)
        {
            return true;
        }
        return false;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Menu getSubmenu() {
        return submenu;
    }

    public void setSubmenu(Menu submenu) {
        this.submenu = submenu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSuper_usuario() {
        return super_usuario;
    }

    public void setSuper_usuario(int super_usuario) {
        this.super_usuario = super_usuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
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
        final Menu other = (Menu) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Menu{" + "nombre=" + nombre + ", tipo=" + tipo + ", submenu=" + submenu + ", url=" + url + ", super_usuario=" + super_usuario + '}';
    }
    
    
    
    
}
