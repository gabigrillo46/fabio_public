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
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;

    @Column(name = "email")
    private String email = "";

    @Column(name = "password")
    private String password = "";

    @Column(name = "nombre")
    private String nombre = "";

    @Column(name = "apellido")
    private String apellido = "";

    @Column(name = "estado")
    private int estado;

    @Column(name = "es_super")
    private int es_super = 0;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria_usuario categoria;

    @Column(name ="pregunta")
    private String pregunta="";

    @Column(name="respuesta")
    private String respuesta="";

    @Column(name="mobile")
    private String mobile="";

    @Column(name = "validado")
    private boolean validado=false;

    @Column(name = "codigo_validacion")
    private String codigo_validacion = "";

    public boolean esSuperUsuario() {
        if (this.getEs_super() > 0) {
            return true;
        }
        return false;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEs_super() {
        return es_super;
    }

    public void setEs_super(int es_super) {
        this.es_super = es_super;
    }

    public Categoria_usuario getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria_usuario categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public String getCodigo_validacion() {
        return codigo_validacion;
    }

    public void setCodigo_validacion(String codigo_validacion) {
        this.codigo_validacion = codigo_validacion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", email=" + email + ", password=" + password + ", nombre=" + nombre + ", apellido=" + apellido + ", estado=" + estado + ", es_super=" + es_super + ", categoria=" + categoria + ", pregunta=" + pregunta + ", respuesta=" + respuesta + ", mobile=" + mobile + ", validado=" + validado + ", codigo_validacion=" + codigo_validacion + '}';
    }    
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
