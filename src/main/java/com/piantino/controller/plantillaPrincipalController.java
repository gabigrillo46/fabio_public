/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.model.Usuario;
import java.io.Serializable;


import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class plantillaPrincipalController implements Serializable{
    
    private String usuario="";
    
    
    
    public void verificarSesion()
    {
        try
        {
            if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap()==null)
            {
                System.out.println("La sesion map is null");
            }
            else
            {
                System.out.println("sesion map*******************************************************************");
                       
                System.out.println(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().toString());
                System.out.println("sesion map*******************************************************************");
            }
            Usuario usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            if(usuarioLogueado==null)
            {
                System.out.println("No encuentra el usuario !!!!!!!!!!!!!!");
                FacesContext.getCurrentInstance().getExternalContext().redirect("pantallas/index.piantino");
            }
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
    }

    public String getUsuario() {
        Usuario usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(usuarioLogueado!=null)
        {
            return usuarioLogueado.getEmail();
        }
        else
        {
            this.verificarSesion();
        }
        return "";
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
}
