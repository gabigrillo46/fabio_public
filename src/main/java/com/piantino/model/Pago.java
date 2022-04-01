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

@Entity
@Table(name = "pago")
public class Pago implements Serializable, Cloneable {

    public static final String Texto_Aprobado = "Approved";
    public static final String Texto_Fallado = "Declined";
    public static final String Texto_Aprobado_warning = "Approved Warning";
    public static final String Texto_Aprobado_warning_balanceado = "Approved Warning (Balanced)";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;

    @ManyToOne
    @JoinColumn(name = "id_alquiler", nullable = false)
    private Alquiler alquiler = new Alquiler();

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal = new Sucursal();

    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private Tipo_pago tipoPago = new Tipo_pago();

    @Column(name = "fecha_hora")
    @Temporal(TemporalType.DATE)
    private Date fecha_hora;

    @Column(name = "monto")
    private float monto = 0;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario = new Usuario();

    @Column(name = "nota")
    private String nota = "";

    @Column(name = "nombre_cliente")
    private String nombre_cliente = "";

    @Column(name = "status")
    private String status = "";

    @Column(name = "recibo_numero")
    private String recibo_numero = "";

    @Column(name = "automatico")
    private int automatico = 0;

    @Column(name = "agrupacion")
    private String agrupacion = "";

    public static final int ID_TIPO_LATE_PAYMENT = 10;
    public static final String NOMBRE_TIPO_PAGO_TOLL = "Toll";
    public static final String NOMBRE_TIPO_PAGO_LATE_PAYMENT = "Late Payment";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean esPagoAprobado() {
        if (this.status.startsWith(Pago.Texto_Aprobado)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esPagoDeclinado() {
        if (this.status.equalsIgnoreCase(Pago.Texto_Fallado)) {
            return true;
        } else {
            return false;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Tipo_pago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Tipo_pago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecibo_numero() {
        return recibo_numero;
    }

    public void setRecibo_numero(String recibo_numero) {
        this.recibo_numero = recibo_numero;
    }

    public int getAutomatico() {
        return automatico;
    }

    public void setAutomatico(int automatico) {
        this.automatico = automatico;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getAgrupacion() {
        return agrupacion;
    }

    public void setAgrupacion(String agrupacion) {
        this.agrupacion = agrupacion;
    }

}
