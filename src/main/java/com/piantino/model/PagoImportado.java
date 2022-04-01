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
@Table(name = "pago_importado")
public class PagoImportado implements Serializable, Cloneable {
    
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;
      
      @Column(name = "monto")
      private String monto="";//posicion 12

      @Column(name = "referencia")
      private String referencia="";// 15
      
      @Column(name = "customer_name")
      private String customerName="";// 16
      
      @Column(name = "respuesta_codigo")
      private String respuestaCodigo="";// 23
      
      @Column(name = "respuesta_texto")
      private String respuestaTexto="";// 24
      
      @Column(name = "recibo_numero")
      private String reciboNumero="";// 25
      
      @Column(name = "fecha")
      private String fecha="";// 29
      
      @Column(name = "status")
      private String status="";// 30
      
      @Column(name ="estado_privado")
      private int estadoPrivado=0;
      
      public static final int ESTADO_NO_INGRESADO=0;
      
      public static final int ESTADO_INGRESADO=1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


      

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRespuestaCodigo() {
        return respuestaCodigo;
    }

    public void setRespuestaCodigo(String respuestaCodigo) {
        this.respuestaCodigo = respuestaCodigo;
    }

    public String getRespuestaTexto() {
        return respuestaTexto;
    }

    public void setRespuestaTexto(String respuestaTexto) {
        this.respuestaTexto = respuestaTexto;
    }

    public String getReciboNumero() {
        return reciboNumero;
    }

    public void setReciboNumero(String reciboNumero) {
        this.reciboNumero = reciboNumero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEstadoPrivado() {
        return estadoPrivado;
    }

    public void setEstadoPrivado(int estadoPrivado) {
        this.estadoPrivado = estadoPrivado;
    }
      
      
      
}
