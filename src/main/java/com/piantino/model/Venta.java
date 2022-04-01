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
@Table(name = "venta")
public class Venta implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "valor")
    private float valor = 0;

    @Column(name = "car_odometer")
    private String car_odometer = "";

    @Column(name = "proposal_date")
    @Temporal(TemporalType.DATE)
    private Date proposal_date;

    @Column(name = "rms_codigo")
    private String rms_codigo = "";

    @Column(name = "rms_fecha")
    @Temporal(TemporalType.DATE)
    private Date rms_fecha;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente clienteVenta = new Cliente();

    @ManyToOne
    @JoinColumn(name = "id_auto")
    private Auto auto = new Auto();

    @ManyToOne
    @JoinColumn(name = "id_formulario")
    private Formulario5 formulario = new Formulario5();

    @ManyToOne
    @JoinColumn(name = "id_invoice")
    private Invoice invoice = new Invoice();

    @Column(name = "estado")
    private int estado = 0;

    public final static int ESTADO_ACTIVO = 0;
    public final static int ESTADO_BAJA_LOGICA = 1;

    @Column(name = "cliente_nombre")
    private String cliente_nombre = "";

    @Column(name = "cliente_direccion")
    private String cliente_direccion = "";

    @Column(name = "cliente_mobile")
    private String cliente_mobile = "";

    @Column(name = "cliente_fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date cliente_fecha_nacimiento;

    @Column(name = "cliente_abn")
    private String cliente_abn = "";

    @Column(name = "cliente_email")
    private String cliente_email = "";

    @Column(name = "cliente_licence")
    private String cliente_licence = "";

    @Column(name = "car_make")
    private String car_make = "";

    @Column(name = "car_model")
    private String car_model = "";

    @Column(name = "car_body")
    private String car_body = "";

    @Column(name = "car_vin")
    private String car_vin = "";

    @Column(name = "car_motor_numero")
    private String car_motor_numero = "";

    @Column(name = "car_stock")
    private String car_stock = "";

    @Column(name = "car_color")
    private String car_color = "";

    @Column(name = "car_transmicion")
    private String car_transmicion = "";

    @Column(name = "car_descripcion")
    private String car_descripcion = "";

    @Column(name = "car_rego_exp")
    @Temporal(TemporalType.DATE)
    private Date car_rego_exp;

    @Column(name = "car_rego")
    private String car_rego = "";

    @Column(name = "car_built")
    @Temporal(TemporalType.DATE)
    private Date car_built;

    @Column(name = "car_compilance")
    @Temporal(TemporalType.DATE)
    private Date car_compilance;

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal = new Sucursal();

    @Column(name = "cliente_telefono")
    private String cliente_telefono = "";
    
    @Column(name = "monto_pagado")
    private float monto_pagado =0;
    
    @Column(name = "monto_adeudado")
    private float monto_adeudado=0;
    
    @Column(name ="fecha_delivery")
    @Temporal(TemporalType.DATE)
    private Date fecha_delivery;
    
    @Column(name = "cliente_suburbio")
    private String cliente_suburbio="";
    
    @Column(name = "cliente_state")
    private String cliente_state="";
    
    @Column(name = "cliente_codigo_postal")
    private String cliente_codigo_postal ="";
    
    @Column(name = "clietne_acn")
    private String cliente_acn ="";
    
    @Column(name = "cliente_dealer_licence")
    private String cliente_dealer_licence="";
    
    @Column(name = "cliente_tipo")
    private int cliente_tipo=-1;
    
    @Column(name = "car_year")
    private int car_year=0;
    
    @Column(name = "cliente_apellido")
    private String cliente_apelllido ="";

    @Column(name = "delivery_name")
    private String delivery_name ="";

    @Column(name = "delivery_address")
    private String delivery_address ="";

    @Column(name = "delivery_abn")
    private String delivery_abn ="";
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getProposal_date() {
        return proposal_date;
    }

    public void setProposal_date(Date proposal_date) {
        this.proposal_date = proposal_date;
    }

    public String getRms_codigo() {
        return rms_codigo;
    }

    public void setRms_codigo(String rms_codigo) {
        this.rms_codigo = rms_codigo;
    }

    public Date getRms_fecha() {
        return rms_fecha;
    }

    public void setRms_fecha(Date rms_fecha) {
        this.rms_fecha = rms_fecha;
    }

    public Cliente getClienteVenta() {
        return clienteVenta;
    }

    public void setClienteVenta(Cliente clienteVenta) {
        this.clienteVenta = clienteVenta;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Venta other = (Venta) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getCar_odometer() {
        return car_odometer;
    }

    public void setCar_odometer(String car_odometer) {
        this.car_odometer = car_odometer;
    }

    @Override
    public String toString() {
        return "Venta{" + "id=" + id + ", fecha=" + fecha + ", valor=" + valor + ", car_odometer=" + car_odometer + ", proposal_date=" + proposal_date + ", rms_codigo=" + rms_codigo + ", rms_fecha=" + rms_fecha + ", clienteVenta=" + clienteVenta + ", auto=" + auto + ", formulario=" + formulario + ", invoice=" + invoice + ", estado=" + estado + '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Formulario5 getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario5 formulario) {
        this.formulario = formulario;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getCliente_nombre() {
        return cliente_nombre;
    }

    public void setCliente_nombre(String cliente_nombre) {
        this.cliente_nombre = cliente_nombre;
    }

    public String getCliente_direccion() {
        return cliente_direccion;
    }

    public void setCliente_direccion(String cliente_direccion) {
        this.cliente_direccion = cliente_direccion;
    }

    public String getCliente_mobile() {
        return cliente_mobile;
    }

    public void setCliente_mobile(String cliente_mobile) {
        this.cliente_mobile = cliente_mobile;
    }

    public Date getCliente_fecha_nacimiento() {
        return cliente_fecha_nacimiento;
    }

    public void setCliente_fecha_nacimiento(Date cliente_fecha_nacimiento) {
        this.cliente_fecha_nacimiento = cliente_fecha_nacimiento;
    }

    public String getCliente_abn() {
        return cliente_abn;
    }

    public void setCliente_abn(String cliente_abn) {
        this.cliente_abn = cliente_abn;
    }

    public String getCliente_email() {
        return cliente_email;
    }

    public void setCliente_email(String cliente_email) {
        this.cliente_email = cliente_email;
    }

    public String getCliente_licence() {
        return cliente_licence;
    }

    public void setCliente_licence(String cliente_licence) {
        this.cliente_licence = cliente_licence;
    }

    public String getCar_make() {
        return car_make;
    }

    public void setCar_make(String car_make) {
        this.car_make = car_make;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_body() {
        return car_body;
    }

    public void setCar_body(String car_body) {
        this.car_body = car_body;
    }

    public String getCar_vin() {
        return car_vin;
    }

    public void setCar_vin(String car_vin) {
        this.car_vin = car_vin;
    }

    public String getCar_motor_numero() {
        return car_motor_numero;
    }

    public void setCar_motor_numero(String car_motor_numero) {
        this.car_motor_numero = car_motor_numero;
    }

    public String getCar_stock() {
        return car_stock;
    }

    public void setCar_stock(String car_stock) {
        this.car_stock = car_stock;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCar_transmicion() {
        return car_transmicion;
    }

    public void setCar_transmicion(String car_transmicion) {
        this.car_transmicion = car_transmicion;
    }

    public String getCar_descripcion() {
        return car_descripcion;
    }

    public void setCar_descripcion(String car_descripcion) {
        this.car_descripcion = car_descripcion;
    }

    public Date getCar_rego_exp() {
        return car_rego_exp;
    }

    public void setCar_rego_exp(Date car_rego_exp) {
        this.car_rego_exp = car_rego_exp;
    }

    public String getCar_rego() {
        return car_rego;
    }

    public void setCar_rego(String car_rego) {
        this.car_rego = car_rego;
    }

    public Date getCar_built() {
        return car_built;
    }

    public void setCar_built(Date car_built) {
        this.car_built = car_built;
    }

    public Date getCar_compilance() {
        return car_compilance;
    }

    public void setCar_compilance(Date car_compilance) {
        this.car_compilance = car_compilance;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public String getCliente_telefono() {
        return cliente_telefono;
    }

    public void setCliente_telefono(String cliente_telefono) {
        this.cliente_telefono = cliente_telefono;
    }

    public float getMonto_pagado() {
        return monto_pagado;
    }

    public void setMonto_pagado(float monto_pagado) {
        this.monto_pagado = monto_pagado;
    }

    public float getMonto_adeudado() {
        return monto_adeudado;
    }

    public void setMonto_adeudado(float monto_adeudado) {
        this.monto_adeudado = monto_adeudado;
    }

    public Date getFecha_delivery() {
        return fecha_delivery;
    }

    public void setFecha_delivery(Date fecha_delivery) {
        this.fecha_delivery = fecha_delivery;
    }

    public String getCliente_suburbio() {
        return cliente_suburbio;
    }

    public void setCliente_suburbio(String cliente_suburbio) {
        this.cliente_suburbio = cliente_suburbio;
    }

    public String getCliente_state() {
        return cliente_state;
    }

    public void setCliente_state(String cliente_state) {
        this.cliente_state = cliente_state;
    }

    public String getCliente_codigo_postal() {
        return cliente_codigo_postal;
    }

    public void setCliente_codigo_postal(String cliente_codigo_postal) {
        this.cliente_codigo_postal = cliente_codigo_postal;
    }

    public String getCliente_dealer_licence() {
        return cliente_dealer_licence;
    }

    public void setCliente_dealer_licence(String cliente_dealer_licence) {
        this.cliente_dealer_licence = cliente_dealer_licence;
    }

    public int getCliente_tipo() {
        return cliente_tipo;
    }

    public void setCliente_tipo(int cliente_tipo) {
        this.cliente_tipo = cliente_tipo;
    }

    public int getCar_year() {
        return car_year;
    }

    public void setCar_year(int car_year) {
        this.car_year = car_year;
    }

    public String getCliente_acn() {
        return cliente_acn;
    }

    public void setCliente_acn(String cliente_acn) {
        this.cliente_acn = cliente_acn;
    }

    public String getCliente_apelllido() {
        return cliente_apelllido;
    }

    public void setCliente_apelllido(String cliente_apelllido) {
        this.cliente_apelllido = cliente_apelllido;
    }

    public String getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getDelivery_abn() {
        return delivery_abn;
    }

    public void setDelivery_abn(String delivery_abn) {
        this.delivery_abn = delivery_abn;
    }


    
    
}
