/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.Contact_usFacadeLocal;
import com.piantino.model.Contact_us;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
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
public class ContactUsController implements Serializable {

    private Contact_us contactUs = new Contact_us();

    private Usuario usuarioLogueado;

    private Calendar c;

    @EJB
    private Contact_usFacadeLocal contact_usEJB;
    
    private boolean mostrarMensajeEnviado = false;

    @PostConstruct
    private void init() {
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(usuarioLogueado!=null)
        {
            contactUs.setUsuario(usuarioLogueado);
        }
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
    }

    public Contact_us getContactUs() {
        return contactUs;
    }
    
    public String emailUsuario()
    {
        if(contactUs != null && contactUs.getUsuario()!=null )
        {
            return contactUs.getUsuario().getEmail();
        }
        return "";
    }

    public void setContactUs(Contact_us contactUs) {
        this.contactUs = contactUs;
    }

    public void registrarContact() {
        if (this.validarRegistroContact()) {
            this.contactUs.setUsuario(usuarioLogueado);
            this.contactUs.setFecha(c.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            this.contactUs.setFecha_hora(sdf.format(c.getTime()));
            this.contact_usEJB.create(contactUs);

            String mensaje = "Cliente: " + contactUs.getUsuario().getEmail()  ;
            
            String codigo = " Nota: " + contactUs.getNota();

            Thread threadPagos = new EMAIL(mensaje,codigo,  "gabigrillo46@gmail.com", "Contact us");
            threadPagos.start();
            
            this.mostrarMensajeEnviado = true;
        }
    }

    public boolean validarRegistroContact() {
        if (this.usuarioLogueado == null || this.usuarioLogueado.getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The user is null"));
            return false;
        }
        if (this.contactUs == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The contact is null"));
            return false;
        }

        if (this.contactUs.getNota() == null || this.contactUs.getNota().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a note"));
            return false;
        }

        return true;
    }

    public boolean isMostrarMensajeEnviado() {
        return mostrarMensajeEnviado;
    }

    public void setMostrarMensajeEnviado(boolean mostrarMensajeEnviado) {
        this.mostrarMensajeEnviado = mostrarMensajeEnviado;
    }
    
    

}
