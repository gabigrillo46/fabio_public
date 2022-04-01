/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.MensajeFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Mensaje;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class GrillaMensajeController implements Serializable {

    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

    @EJB
    private UsuarioFacadeLocal usuarioEJB;

    private String apellido = null;

    private String referencia = null;

    private String palabra = null;

    private Date fecha_desde = null;

    private Date fecha_hasta = null;

    private int id_usuario_filtro = -1;

    private List<Mensaje> listaMensajeResultado = new ArrayList<Mensaje>();

    @EJB
    private MensajeFacadeLocal mensajeEJB;

    private int cantidadResultados = 0;

    private String fechaFormat = "";

    private String referenceNumberMensaje = "";

    @PostConstruct
    private void init() {
        listaUsuarios = usuarioEJB.listaUsuarioActivos();
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public int getId_usuario_filtro() {
        return id_usuario_filtro;
    }

    public void setId_usuario_filtro(int id_usuario_filtro) {
        this.id_usuario_filtro = id_usuario_filtro;
    }

    public void buscarPorFiltro() {
        int idAlquiler = -1;
        if (referencia != null && referencia.trim().length() > 0) {
            try {
                idAlquiler = Integer.parseInt(referencia);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The reference number has to be a number"));
                e.printStackTrace();
                return;
            }
        }

        this.listaMensajeResultado = mensajeEJB.getMensajesPorFiltro(idAlquiler, palabra, apellido, id_usuario_filtro, fecha_desde, fecha_hasta);
        if (this.listaMensajeResultado.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "No result found"));
        }
        this.cantidadResultados = this.listaMensajeResultado.size();
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(int cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    public List<Mensaje> getListaMensajeResultado() {
        return listaMensajeResultado;
    }

    public void setListaMensajeResultado(List<Mensaje> listaMensajeResultado) {
        this.listaMensajeResultado = listaMensajeResultado;
    }

    public String getFechaFormat() {
        return fechaFormat;
    }

    public String getFechaFormat(Mensaje m) {
        if (m != null && m.getFecha_hora() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(m.getFecha_hora());
        }
        return "";
    }

    public void setFechaFormat(String fechaFormat) {
        this.fechaFormat = fechaFormat;
    }

    public String getReferenceNumberMensaje() {
        return referenceNumberMensaje;
    }

    public String getReferenceNumberMensaje(Mensaje mensaje) {
        if (mensaje != null && mensaje.getAlquiler() != null && mensaje.getAlquiler().getId() > -1) {
            return mensaje.getAlquiler().getId() + "";
        }
        return "";
    }

    public void setReferenceNumberMensaje(String referenceNumberMensaje) {
        this.referenceNumberMensaje = referenceNumberMensaje;
    }

    public void verDetalleMensaje(int idMensaje) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idMensajeDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idMensajeDato", idMensaje + "");
    }

}
