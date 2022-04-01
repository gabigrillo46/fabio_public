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
import javax.persistence.Table;

@Entity
@Table(name = "parametro")
public class Parametro implements Serializable, Cloneable {
    
    //dd/MM/yyyy
    public static final String CLAVE_PUBLICA="T4076";
    public static final String FICHERO_CONFIGURACION="Pagos\\payway-curl-config.txt";
    public static final String DIRECTORIO_DESCARGA="Pagos\\Descargados\\";
    public static final String DIRECTORIO_A_TRATAR="Pagos\\A_tratar\\";
    public static final String DIRECTORIO_IMPORTADOS="Pagos\\Procesados\\";
    public static final String USUARIO_BANCO="Q42023";
    public static final String LOAD = "LOAD";
    public static final String YEAR_COAST = "YEAR_COST";
    public static final String BOND = "BOND";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;

    @Column(name = "grupo")
    private String grupo = "";

    @Column(name = "calif_grupo")
    private String calif_grupo = "";

    @Column(name = "param")
    private String param = "";

    @Column(name = "calif_param")
    private String calif_param = "";

    @Column(name = "valor")
    private String valor = "";

    @Column(name = "tipo_dato")
    private String tipo_dato = "";

    @Column(name = "explicacion")
    private String explicacion = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCalif_grupo() {
        return calif_grupo;
    }

    public void setCalif_grupo(String calif_grupo) {
        this.calif_grupo = calif_grupo;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCalif_param() {
        return calif_param;
    }

    public void setCalif_param(String calif_param) {
        this.calif_param = calif_param;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo_dato() {
        return tipo_dato;
    }

    public void setTipo_dato(String tipo_dato) {
        this.tipo_dato = tipo_dato;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

}
