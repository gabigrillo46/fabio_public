package com.piantino.controller;

import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Auto;
import com.piantino.model.Marca;
import com.piantino.model.Modelo;
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
public class GrillaMarcasController implements Serializable {

    @EJB
    private MarcaFacadeLocal MarcaEJB;

    @EJB
    private ModeloFacadeLocal ModeloEJB;
    
    @EJB
    private AutoFacadeLocal AutoEJB;

    private Marca marcaSeleccionada = null;

    private List<Marca> listaMarcas;

    @PostConstruct
    private void init() {
        listaMarcas = MarcaEJB.findAll();
    }

    public List<Marca> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marca> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public void setMarcaSeleccionada(Marca mar) {
        this.marcaSeleccionada = mar;
    }

    public Marca getMarcaSeleccionada() {
        return this.marcaSeleccionada;
    }

    public void eliminarMarca(Marca marcaSelecc) {
        try {
            if(validarEliminarMarca(marcaSelecc))
            {
                marcaSelecc.setEstado(Marca.ESTADO_ELIMINADO);
                MarcaEJB.edit(marcaSelecc);
                //MarcaEJB.remove(marcaSelecc);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
                listaMarcas = MarcaEJB.findAll();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while deleting"));
        }
    }

    public boolean validarEliminarMarca(Marca marcaSelecc) {
        try {
            List<Modelo> mmodelosDeMarca = ModeloEJB.buscarPorMarca(marcaSelecc.getId());
            if(mmodelosDeMarca != null && mmodelosDeMarca.size()>0)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There are active models of the brand"));    
                return false;
            }
            
            Auto autoDeMarca = AutoEJB.buscarPorMarca(marcaSelecc.getId());
            if(autoDeMarca != null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There are active cars of the brand"));    
                return false;                
            }
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while validation deleting"));
            return false;
        }
        return true;

    }

}
