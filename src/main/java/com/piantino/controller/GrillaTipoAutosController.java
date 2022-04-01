package com.piantino.controller;

import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.Tipo_autoFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Auto;
import com.piantino.model.Tipo_auto;

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
public class GrillaTipoAutosController implements Serializable{
    
    @EJB
    private Tipo_autoFacadeLocal Tipo_autoEJB;
    
    @EJB
    private AutoFacadeLocal AutoEJB;
    
    private Tipo_auto marcaSeleccionada=null;

    
    private List<Tipo_auto> listaTipo_autos;

    @PostConstruct
    private void init()
    {
        listaTipo_autos = Tipo_autoEJB.findAll();
    }

    public List<Tipo_auto> getListaTipo_autos() {
        return listaTipo_autos;
    }

    public void setListaTipo_autos(List<Tipo_auto> listaTipo_auto) {
        this.listaTipo_autos = listaTipo_auto;
    }
    
    public void setTipo_autoSeleccionada(Tipo_auto mar)
    {
        this.marcaSeleccionada=mar;
    }
    
    public Tipo_auto getTipo_autoSeleccionada()
    {
        return this.marcaSeleccionada;
    }
    
    public void eliminarTipo_auto(Tipo_auto marcaSelecc)
    {
        try
        {
            if(validarEliminarTipoAuto(marcaSelecc))
            {
                marcaSelecc.setEstado(Tipo_auto.ESTADO_BAJA);
                Tipo_autoEJB.edit(marcaSelecc);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
                listaTipo_autos = Tipo_autoEJB.findAll();
            }
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while deleting"));
        }
    }
    
         public boolean validarEliminarTipoAuto(Tipo_auto tipoAuto) {
        try {
            Auto autoDeMarca = AutoEJB.buscarPorTipoAuto(tipoAuto.getId());
            if (autoDeMarca != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There are active cars of the body type"));
                return false;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while validation deleting"));
            return false;
        }
        return true;
    }
    
    
    
    
}
