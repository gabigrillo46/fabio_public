/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.MensajeFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Mensaje;
import com.piantino.model.Pago;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class viewSMSController implements Serializable {

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    @EJB
    private MensajeFacadeLocal mensajeEJB;

    private Alquiler alquilerBD;

    private List<Mensaje> listaMensaje = new ArrayList<Mensaje>();
    
    private String fechaFormatMensaje;

    @PostConstruct
    private void init() {
        Calendar c = Calendar.getInstance();
        String idAlquilerStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idAlquilerSMS");
        alquilerBD = alquilerEJB.getAlquilerPorId(Integer.parseInt(idAlquilerStr));
        if (alquilerBD != null) {
            listaMensaje = mensajeEJB.getMensajesDeAlquiler(alquilerBD.getId());
        }
    }
    
    public String getFechaFormatMensaje(Mensaje m)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String resultado = sdf.format(m.getFecha_hora());
        return resultado;
    }

    public String getFechaFormatMensaje() {
        return fechaFormatMensaje;
    }

    public void setFechaFormatMensaje(String fechaFormatMensaje) {
        this.fechaFormatMensaje = fechaFormatMensaje;
    }

    
    
    

    public List<Mensaje> getListaMensaje() {
        return listaMensaje;
    }

    public void setListaMensaje(List<Mensaje> listaMensaje) {
        this.listaMensaje = listaMensaje;
    }

}
