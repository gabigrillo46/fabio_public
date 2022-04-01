/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;


import com.piantino.ejb.TareaFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Tarea;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class GrillaTareaController implements Serializable {
    
    @EJB
    private TareaFacadeLocal tareaEJB;
    
    private List<Tarea> listaTareas;
    
    private String id_usuario="";
            
    private Date fechaVencimiento;
    
    private String caducado;
    
    private String realizado;
    
    private List<Usuario> listaUsuarios;
    
    @EJB
    private UsuarioFacadeLocal usuarioEJB;
    
    private String estadoStr="";
    
    private String caducadoStr="";
    
    private boolean rojo=false;
    
    @PostConstruct
    private void init()
    {
       this.listaTareas =new ArrayList();// alquilerEJB.findAll();
       this.listaUsuarios = usuarioEJB.findAll();
               }

    public List<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(List<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCaducado() {
        return caducado;
    }

    public void setCaducado(String caducado) {
        this.caducado = caducado;
    }

    public String getRealizado() {
        return realizado;
    }

    public void setRealizado(String realizado) {
        this.realizado = realizado;
    }





    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
        public void verDetalleTarea(int idTarea)
    {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idTareaDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idTareaDato", idTarea+"");

    }
        
        public void tareaRealizada(int idTarea)
        {
            Tarea tarea= tareaEJB.buscarTareaPorId(idTarea);
            if(tarea !=null)
            {
                tarea.setRealizado(true);
                tareaEJB.edit(tarea);
            }
            buscarPorFiltro();
        }
    
    public void buscarPorFiltro()
    {
        if(caducado.equalsIgnoreCase("-1"))
        {
            caducado = null;
        }
        if(realizado.equalsIgnoreCase("-1"))
        {
            realizado = null;
        }
        if(id_usuario.equalsIgnoreCase("-1"))
        {
            id_usuario= null;
        }
        this.listaTareas = tareaEJB.buscarPorFiltro(realizado, caducado, fechaVencimiento, id_usuario);
        boolean actualizar=false;
        Calendar c=Calendar.getInstance();
        Date hoy = c.getTime();
        for(int j=0;j<this.listaTareas.size();j++)
        {
            Tarea tar = this.listaTareas.get(j);
            if(tar.getFecha_vencimiento().before(hoy) && !tar.isCaducado())
            {
                tar.setCaducado(true);
                actualizar = true;
                this.tareaEJB.edit(tar);
            }
        }
        if(actualizar)
        {
          this.listaTareas = tareaEJB.buscarPorFiltro(realizado, caducado, fechaVencimiento, id_usuario);  
        }
    }

    public String getEstadoStr() {
        return estadoStr;
    }
    
    public String getEstadoStr(Tarea tar)
    {
        if(tar.isRealizado())
        {
            return "Done";
        }
        else
        {
            return "Pending";
        }
    }

    public void setEstadoStr(String estadoStr) {
        this.estadoStr = estadoStr;
    }

    public boolean isRojo() {
        return rojo;
    }
    
    public boolean esRojo(Tarea tar)
    {
        if(tar.isCaducado() && !tar.isRealizado())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setRojo(boolean rojo) {
        this.rojo = rojo;
    }

    public String getCaducadoStr() {
        return caducadoStr;
    }
    
    public String getCaducadoStr(Tarea tar)
    {
        if(tar.isCaducado())
        {
           return "Expired"; 
        }
        else
        {
            return "Valid";
        }
    }

    public void setCaducadoStr(String caducadoStr) {
        this.caducadoStr = caducadoStr;
    }
    
    

    
}
