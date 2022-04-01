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
@Table(name = "formulario5")
public class Formulario5 implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    @Column(name = "entry_number")
    private long entry_number = 0;

    @Column(name = "stock_number")
    private long stock_number = 0;

    @Column(name = "fecha_generation")
    @Temporal(TemporalType.DATE)
    private Date fecha_generacion = null;

    @Column(name = "dealer_name")
    private String dealer_name = "";

    @Column(name = "dealer_address")
    private String dealer_address = "";

    @Column(name = "dealer_licence")
    private String dealer_licence = "";

    @Column(name = "date_manufacture")
    @Temporal(TemporalType.DATE)
    private Date fecha_fabricacion = null;

    @Column(name = "make")
    private String make = "";

    @Column(name = "model")
    private String model = "";
    
    @Column(name="year")
    private int year=0;

    @Column(name = "rego")
    private String rego = "";

    @Column(name = "rego_exp_date")
    @Temporal(TemporalType.DATE)
    private Date rego_exp_date = null;

    @Column(name = "car_price")
    private float precio_auto = 0;

    @Column(name = "odometer_reading")
    private String odometer_reading = "";

    @Column(name = "vin")
    private String vin = "";

    @Column(name = "engine_number")
    private String engine_number = "";

    @Column(name = "written_off")
    private boolean written_off = false;

    @Column(name = "water_damage")
    private boolean water_damage = false;

    @Column(name = "major_modifications")
    private boolean major_modifications = false;

    @Column(name = "ppsr_code")
    private String ppsr_code = "";

    @Column(name = "guarantee")
    private boolean guarantee = false;

    @Column(name = "imported_second_hand")
    private boolean imported_second_hand = false;

    @Column(name = "sale_date")
    @Temporal(TemporalType.DATE)
    private Date sale_date = null;

    @Column(name = "sale_price")
    private float sale_price = 0;

    @Column(name = "sale_odometer")
    private String sale_odometer = "";

    @ManyToOne
    @JoinColumn(name = "id_auto", nullable = false)
    private Auto auto = new Auto();
    
    @Column(name ="estado")
    private int estado=0;
    
    @Column(name ="datos_comprador")
    private String datosComprador ="";
    
    @Column(name = "rms_numero")
    private String rms_numero="";
    
    @Column(name ="rms_fecha")
    @Temporal(TemporalType.DATE)
    private Date rms_fecha;
    
    
    public static final int ESTADO_ACTIVO =0;
    public static final int ESTADO_CANCELADO=1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEntry_number() {
        return entry_number;
    }

    public void setEntry_number(long entry_number) {
        this.entry_number = entry_number;
    }

    public long getStock_number() {
        return stock_number;
    }

    public void setStock_number(long stock_number) {
        this.stock_number = stock_number;
    }

    public Date getFecha_generacion() {
        return fecha_generacion;
    }

    public void setFecha_generacion(Date fecha_generacion) {
        this.fecha_generacion = fecha_generacion;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getDealer_address() {
        return dealer_address;
    }

    public void setDealer_address(String dealer_address) {
        this.dealer_address = dealer_address;
    }

    public String getDealer_licence() {
        return dealer_licence;
    }

    public void setDealer_licence(String dealer_licence) {
        this.dealer_licence = dealer_licence;
    }

    public Date getFecha_fabricacion() {
        return fecha_fabricacion;
    }

    public void setFecha_fabricacion(Date fecha_fabricacion) {
        this.fecha_fabricacion = fecha_fabricacion;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public Date getRego_exp_date() {
        return rego_exp_date;
    }

    public void setRego_exp_date(Date rego_exp_date) {
        this.rego_exp_date = rego_exp_date;
    }

    public float getPrecio_auto() {
        return precio_auto;
    }

    public void setPrecio_auto(float precio_auto) {
        this.precio_auto = precio_auto;
    }

    public String getOdometer_reading() {
        return odometer_reading;
    }

    public void setOdometer_reading(String odometer_reading) {
        this.odometer_reading = odometer_reading;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEngine_number() {
        return engine_number;
    }

    public void setEngine_number(String engine_number) {
        this.engine_number = engine_number;
    }

    public boolean isWritten_off() {
        return written_off;
    }

    public void setWritten_off(boolean written_off) {
        this.written_off = written_off;
    }

    public boolean isWater_damage() {
        return water_damage;
    }

    public void setWater_damage(boolean water_damage) {
        this.water_damage = water_damage;
    }

    public boolean isMajor_modifications() {
        return major_modifications;
    }

    public void setMajor_modifications(boolean major_modifications) {
        this.major_modifications = major_modifications;
    }

    public String getPpsr_code() {
        return ppsr_code;
    }

    public void setPpsr_code(String ppsr_code) {
        this.ppsr_code = ppsr_code;
    }

    public boolean isGuarantee() {
        return guarantee;
    }

    public void setGuarantee(boolean guarantee) {
        this.guarantee = guarantee;
    }

    public boolean isImported_second_hand() {
        return imported_second_hand;
    }

    public void setImported_second_hand(boolean imported_second_hand) {
        this.imported_second_hand = imported_second_hand;
    }

    public Date getSale_date() {
        return sale_date;
    }

    public void setSale_date(Date sale_date) {
        this.sale_date = sale_date;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public String getSale_odometer() {
        return sale_odometer;
    }

    public void setSale_odometer(String sale_odometer) {
        this.sale_odometer = sale_odometer;
    }


    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Formulario5 other = (Formulario5) obj;
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

    public String getDatosComprador() {
        return datosComprador;
    }

    public void setDatosComprador(String datosComprador) {
        this.datosComprador = datosComprador;
    }

    public String getRms_numero() {
        return rms_numero;
    }

    public void setRms_numero(String rms_numero) {
        this.rms_numero = rms_numero;
    }

    public Date getRms_fecha() {
        return rms_fecha;
    }

    public void setRms_fecha(Date rms_fecha) {
        this.rms_fecha = rms_fecha;
    }

    @Override
    public String toString() {
        return "Formulario5{" + "id=" + id + ", entry_number=" + entry_number + ", stock_number=" + stock_number + ", fecha_generacion=" + fecha_generacion + ", dealer_name=" + dealer_name + ", dealer_address=" + dealer_address + ", dealer_licence=" + dealer_licence + ", fecha_fabricacion=" + fecha_fabricacion + ", make=" + make + ", model=" + model + ", rego=" + rego + ", rego_exp_date=" + rego_exp_date + ", precio_auto=" + precio_auto + ", odometer_reading=" + odometer_reading + ", vin=" + vin + ", engine_number=" + engine_number + ", written_off=" + written_off + ", water_damage=" + water_damage + ", major_modifications=" + major_modifications + ", ppsr_code=" + ppsr_code + ", guarantee=" + guarantee + ", imported_second_hand=" + imported_second_hand + ", sale_date=" + sale_date + ", sale_price=" + sale_price + ", sale_odometer=" + sale_odometer + ", auto=" + auto + ", estado=" + estado + ", datosComprador=" + datosComprador + ", rms_numero=" + rms_numero + ", rms_fecha=" + rms_fecha + '}';
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    

    
}
