package com.piantino.controller;

import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Auto;
import com.piantino.model.Sucursal;
import com.piantino.model.Sucursal;
import com.piantino.model.Sucursal;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;

@Named
@RequestScoped
public class GrillaSucursalesController implements Serializable {

    @EJB
    private SucursalFacadeLocal SucursalEJB;

    @EJB
    private AutoFacadeLocal AutoEJB;

    private Sucursal modeloSeleccionada = null;

    private List<Sucursal> listaSucursales;

    private String nombre = "";

    @PostConstruct
    private void init() {
        listaSucursales = SucursalEJB.findAll();
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public void setSucursalSeleccionada(Sucursal mar) {
        this.modeloSeleccionada = mar;
    }

    public Sucursal getSucursalSeleccionada() {
        return this.modeloSeleccionada;
    }

    public void eliminarSucursal(Sucursal marcaSelecc) {
        try {
            if (validarEliminarSucursal(marcaSelecc)) {
                marcaSelecc.setEstado(Sucursal.ESTADO_ELIMINADA);
                SucursalEJB.edit(marcaSelecc);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
                listaSucursales = SucursalEJB.findAll();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while deleting"));
        }
    }

    public boolean validarEliminarSucursal(Sucursal sucursal) {
        try {
            Auto autoDeMarca = AutoEJB.buscarPorSucursal(sucursal.getId());
            if (autoDeMarca != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There are active cars of the branch"));
                return false;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while validation deleting"));
            return false;
        }
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void buscarPorFiltro() {
        String nombreBuscar = null;
        if(this.nombre!=null && this.nombre.trim().length()>0)
        {
            nombreBuscar = this.nombre;
        }
        this.listaSucursales =this.SucursalEJB.buscarSucursalesPorNombreSimilar(nombreBuscar);
    }

}
