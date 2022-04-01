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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class GrillaProveedoresController implements Serializable {

    private String abnBuscar = "";

    private String nombreBuscar = "";

    private List<Cliente> listaProveedoresResultado = new ArrayList();

    @EJB
    private ClienteFacadeLocal clienteEJB;

    private int cantidadResultados = 0;

    private String address = "";

    @PostConstruct
    public void init() {

    }

    public String getAbnBuscar() {
        return abnBuscar;
    }

    public void setAbnBuscar(String abnBuscar) {
        this.abnBuscar = abnBuscar;
    }

    public String getNombreBuscar() {
        return nombreBuscar;
    }

    public void setNombreBuscar(String nombreBuscar) {
        this.nombreBuscar = nombreBuscar;
    }

    public List<Cliente> getListaProveedoresResultado() {
        return listaProveedoresResultado;
    }

    public void setListaProveedoresResultado(List<Cliente> listaProveedoresResultado) {
        this.listaProveedoresResultado = listaProveedoresResultado;
    }

    public void verDetalleProveedor(int idProveedor) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idProveedorDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idProveedorDato", idProveedor + "");
    }

    public void buscarPorFiltro() {
        this.listaProveedoresResultado = clienteEJB.buscarProveedoresPorFiltro(abnBuscar, nombreBuscar);
        if (this.listaProveedoresResultado != null) {
            this.cantidadResultados = this.listaProveedoresResultado.size();
        }
    }
    
    public void eliminarProveedor()
    {
        
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(int cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress(Cliente proveedor) {
        this.address = "";
        if (proveedor != null) {
            address = proveedor.getDireccion() + " " + proveedor.getSuburbio();
            if (proveedor.getState() != null) {
                address = address + " " + proveedor.getState().getNombre();
            }
            address = address + " " + proveedor.getCodigo_postal() + " " + proveedor.getPais();
        }
        return address;
    }

}
