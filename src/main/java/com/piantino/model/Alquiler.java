/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "alquiler")
public class Alquiler implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fecha_fin;

    @ManyToOne
    @JoinColumn(name = "id_auto")
    private Auto auto = null;

    @Column(name = "estado")
    private int estado = 0;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario = new Usuario();

    @ManyToOne
    @JoinColumn(name = "id_sucursal_origen")
    private Sucursal sucursal_origen = new Sucursal();

    @ManyToOne
    @JoinColumn(name = "id_source")
    private Source source = null;

    @ManyToOne
    @JoinColumn(name = "id_sucursal_destino")
    private Sucursal sucursal_destino = new Sucursal();

    @Column(name = "hora_inicio")
    private String hora_inicio = "09:00";

    @Column(name = "hora_fin")
    private String hora_fin = "09:00";

    @Column(name = "fuel_out")
    private int fuel_out = 0;

    @Column(name = "fuel_in")
    private int fuel_in = 0;

    @Column(name = "kms_out")
    private long kms_out = 0;

    @Column(name = "kms_in")
    private long kms_in = 0;

    @Column(name = "notes")
    private String notes = "";

    @Column(name = "total")
    private float total = 0;

    @Column(name = "extra")
    private float extra = 0;

    @Column(name = "gran_total")
    private float gran_total = 0;

    @Column(name = "dias")
    private int dias = 0;

    @Column(name = "semanas")
    private int semanas = 0;

    @Column(name = "rate_per_day")
    private float rate_per_day = 1;

    @Column(name = "deuda")
    private float deuda = 0;

    @Column(name = "rego")
    private String rego = "";

    @Column(name = "apellido")
    private String apellido = "";

    @Column(name = "telefono")
    private String telefono = "";

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "movil")
    private String movil;

    @Column(name = "deudaCurrent")
    private float deudaCurrent = 0;

    @Column(name = "dinero_ingresado")
    private float dineroIngresado = 0;

    @Column(name = "fecha_calculo_current")
    @Temporal(TemporalType.DATE)
    private Date fecha_calculo_current = null;

    @Column(name = "fortnightly")
    private boolean fortnightly;

    @Column(name = "fecha_primer_pago")
    @Temporal(TemporalType.DATE)
    private Date fecha_primer_pago = null;

    @Column(name = "agrupacion")
    private String agrupacion = "";
    
    @Column(name = "diario")
    private boolean diario = false;

    public static final int ESTADO_RESERVA = 1;
    public static final int ESTADO_ALQUILADO = 2;
    public static final int ESTADO_RETORNADO = 3;
    public static final int ESTADO_CANCELADO = 4;
    public static final int ESTADO_CARGANDO = 5;
    public static final int ESTADO_VENDIDO = 6;
    public static final int ESTADO_REPO = 7;
    public static final int ESTADO_COURTESY = 8;
    
    public static final int REGO_ESTABLECIDO =1;
    public static final int REGO_PREVIO =2;
    public static final int REGO_IMPORTADO=3;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Sucursal getSucursal_origen() {
        if(sucursal_origen==null)
        {
            return new Sucursal();
        }
        return sucursal_origen;
    }

    public void setSucursal_origen(Sucursal sucursal_origen) {
        this.sucursal_origen = sucursal_origen;
    }

    public Sucursal getSucursal_destino() {
        if(sucursal_destino==null)
        {
            return new Sucursal();
        }
        return sucursal_destino;
    }

    public void setSucursal_destino(Sucursal sucursal_destino) {
        this.sucursal_destino = sucursal_destino;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public int getFuel_out() {
        return fuel_out;
    }

    public void setFuel_out(int fuel_out) {
        this.fuel_out = fuel_out;
    }

    public int getFuel_in() {
        return fuel_in;
    }

    public void setFuel_in(int fuel_in) {
        this.fuel_in = fuel_in;
    }

    public long getKms_out() {
        return kms_out;
    }

    public void setKms_out(long kms_out) {
        this.kms_out = kms_out;
    }

    public long getKms_in() {
        return kms_in;
    }

    public void setKms_in(long kms_in) {
        this.kms_in = kms_in;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;

    }

    public float getExtra() {
        return extra;
    }

    public void setExtra(float extra) {
        this.extra = extra;
    }

    public float getGran_total() {
        return gran_total;
    }

    public void setGran_total(float gran_total) {
        this.gran_total = gran_total;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public float getRate_per_day() {
        return rate_per_day;
    }

    public void setRate_per_day(float rate_per_day) {
        this.rate_per_day = rate_per_day;
    }

    public float getDeuda() {
        return deuda;
    }

    public void setDeuda(float deuda) {
        this.deuda = deuda;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Source getSource() {
        if (source == null) {
            source = new Source();
            source.setId(1);
        }
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public float getDeudaCurrent() {
        return deudaCurrent;
    }

    public void setDeudaCurrent(float deudaCurrent) {
        this.deudaCurrent = deudaCurrent;
    }

    public Date getFecha_calculo_current() {
        return fecha_calculo_current;
    }

    public void setFecha_calculo_current(Date fecha_calculo_current) {
        this.fecha_calculo_current = fecha_calculo_current;
    }

    public boolean isFortnightly() {
        return fortnightly;
    }

    public void setFortnightly(boolean fortnightly) {
        this.fortnightly = fortnightly;
    }

    public Date getFecha_primer_pago() {
        return fecha_primer_pago;
    }

    public void setFecha_primer_pago(Date fecha_primer_pago) {
        this.fecha_primer_pago = fecha_primer_pago;
    }

    public float getDineroIngresado() {
        return dineroIngresado;
    }

    public void setDineroIngresado(float dineroIngresado) {
        this.dineroIngresado = dineroIngresado;
    }

    public String getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(String agrupacion) {
        this.agrupacion = agrupacion;
    }

    public boolean isDiario() {
        return diario;
    }

    public void setDiario(boolean diario) {
        this.diario = diario;
    }

}
