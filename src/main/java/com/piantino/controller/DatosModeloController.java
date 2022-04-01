package com.piantino.controller;


import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Marca;
import com.piantino.model.Modelo;

import com.piantino.model.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class DatosModeloController implements Serializable {

    @EJB
    private ModeloFacadeLocal modeloEJB;
    
    @EJB
    private MarcaFacadeLocal marcaEJB;

    private String accion="";

    private Modelo modelo;
    
    private Modelo modeloSelecc;
    
    private Marca marcaSeleccionada;
    
    private List<Marca> listaMarcas;
    
    @Inject
    private GrillaModelosController comentarController;
    
    @PostConstruct
    private void init()
    {
        listaMarcas = marcaEJB.findAll();
         modeloSelecc = comentarController.getModeloSeleccionada();
         if(modeloSelecc ==null)
         {
             modelo = new Modelo();
             this.setAccion("R");
         }
         else
         {
            try {
                modelo = (Modelo)modeloSelecc.clone();
                if(modeloSelecc.getMarca()==null)
                {
                    modeloSelecc.setMarca(new Marca());
                }
                modelo.setMarca((Marca)modeloSelecc.getMarca().clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DatosModeloController.class.getName()).log(Level.SEVERE, null, ex);
                modelo = modeloSelecc;
            }
          this.setAccion("M");
         }
         
    }

    public Marca getMarcaSeleccionada() {
        return marcaSeleccionada;
    }

    public void setMarcaSeleccionada(Marca marcaSeleccionada) {
        this.marcaSeleccionada = marcaSeleccionada;
    }

    public List<Marca> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marca> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }
    
    
    

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    public void editarModelo()
    {
        try
        {
            Modelo modeloExistente = modeloEJB.buscarPorNombre(modelo.getNombre(), modelo.getMarca().getId());
            if(modeloExistente != null && modeloExistente.getId() != modelo.getId())
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a model with the name entered"));                
            }
            else
            {
              modeloEJB.edit(modelo);    
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited correctly"));                
            }

        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Edit failed"));
        }
     //   Cuando haga enter tiene que tomar el editar, no el boton volver
       //         Hacer el eliminar
    }
    
      public String leer(Modelo marSelecc)
    {
        modelo = comentarController.getModeloSeleccionada();
        this.setAccion("M");
        return "/pantallas/DatosModelo";
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void registrar() {
        try {
            Modelo modeloExistente = modeloEJB.buscarPorNombre(modelo.getNombre(), modelo.getMarca().getId());
            if (modeloExistente != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a model with the name entered"));                
            } else {
                modeloEJB.create(modelo);
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
                            Modelo modeloSetear = new Modelo();
                            modeloSetear.getMarca().setId(modelo.getMarca().getId());
                            this.setModelo(modeloSetear);
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Registration failed"));
        }

    }

}
