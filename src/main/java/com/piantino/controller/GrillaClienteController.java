/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.model.Cliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class GrillaClienteController implements Serializable {

    private String nombre = "";

    private String telefono = "";

    @EJB
    private ClienteFacadeLocal clienteEJB;

    private int cantidadResultados = 0;

    private List<Cliente> listaClientesResultado = new ArrayList();

    @PostConstruct
    public void init() {

    }

    public void buscarPorFiltro() {
        listaClientesResultado = clienteEJB.buscarPorFiltro(nombre, telefono);
        cantidadResultados = listaClientesResultado.size();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(int cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    public List<Cliente> getListaClientesResultado() {
        return listaClientesResultado;
    }

    public void setListaClientesResultado(List<Cliente> listaClientesResultado) {
        this.listaClientesResultado = listaClientesResultado;
    }

    public void verDetalleCliente(int idCliente) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idClienteDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idClienteDato", idCliente + "");
    }

}
