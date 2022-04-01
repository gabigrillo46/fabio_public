/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.Mensaje_InternoFacadeLocal;
import com.piantino.ejb.Mensaje_Interno_DestinoFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Mensaje_Interno;
import com.piantino.model.Usuario;
import com.piantino.model.Mensaje_Interno_Destino;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
public class GrillaMensajeInternoController implements Serializable {

    private Date fechaDesde = null;

    private Date fechaHasta = null;

    private int idUsuarioDe = -1;

    private int idUsuarioPara = -1;

    private String idAlquiler = null;

    private String palabra = null;

    private List<Mensaje_Interno> listaMensajesGrilla = new ArrayList<Mensaje_Interno>();
    
    private boolean faltaLeer=false;
    
    private String reference="";

    @EJB
    private UsuarioFacadeLocal usuarioEJB;

    @EJB
    private Mensaje_InternoFacadeLocal mensajeInternoEJB;
    
    @EJB
    private Mensaje_Interno_DestinoFacadeLocal mensajeInternoDestinoEJB;

    private List<Usuario> listaUsuariosActivos;

    private Usuario usuarioLogueado;
    
    private boolean usuarioSuper=false;

    @PostConstruct
    private void init() {
        listaUsuariosActivos = usuarioEJB.listaUsuarioActivos();
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuarioLogueado != null) {
            idUsuarioPara = usuarioLogueado.getId();
            this.buscarMensajesPorFiltro();
        }
        
    }

    public boolean isUsuarioSuper() {
        if(usuarioLogueado!=null && usuarioLogueado.esSuperUsuario())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setUsuarioSuper(boolean usuarioSuper) {
        this.usuarioSuper = usuarioSuper;
    }
    
    

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public int getIdUsuarioDe() {
        return idUsuarioDe;
    }

    public void setIdUsuarioDe(int idUsuarioDe) {
        this.idUsuarioDe = idUsuarioDe;
    }

    public int getIdUsuarioPara() {
        return idUsuarioPara;
    }

    public void setIdUsuarioPara(int idUsuarioPara) {
        this.idUsuarioPara = idUsuarioPara;
    }

    public String getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(String idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public void buscarMensajesPorFiltro() {
        int idAlquilerInt = -1;
        if (idAlquiler != null && idAlquiler.trim().length() > 0) {
            try {
                idAlquilerInt = Integer.parseInt(idAlquiler);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The reference number have to be a number"));
                return;
            }
        }
        this.listaMensajesGrilla.clear();
        List<Long> listaIdMensajes = mensajeInternoEJB.buscarMensajePorFiltro(fechaDesde, fechaHasta, idUsuarioPara, idUsuarioDe, idAlquilerInt, palabra);
        for (int h = 0; h < listaIdMensajes.size(); h++) {
            Long idMensaje = listaIdMensajes.get(h);
            Mensaje_Interno mensajeAgregar = mensajeInternoEJB.getMensajeInternoPorId(idMensaje);
            if (mensajeAgregar != null) {
                this.listaMensajesGrilla.add(mensajeAgregar);
            }
        }
    }

    public List<Mensaje_Interno> getListaMensajesGrilla() {
        return listaMensajesGrilla;
    }

    public void setListaMensajesGrilla(List<Mensaje_Interno> listaMensajesGrilla) {
        this.listaMensajesGrilla = listaMensajesGrilla;
    }

    public List<Usuario> getListaUsuariosActivos() {
        return listaUsuariosActivos;
    }

    public void setListaUsuariosActivos(List<Usuario> listaUsuariosActivos) {
        this.listaUsuariosActivos = listaUsuariosActivos;
    }

    public String getFechaFormato(Mensaje_Interno m) {
        String resultado = "";
        if (m != null && m.getFecha_hora() != null) {
            try {
                SimpleDateFormat sdfDesdeBD = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date fechaDate = sdfDesdeBD.parse(m.getFecha_hora());

                SimpleDateFormat sdfMostrar = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                resultado = sdfMostrar.format(fechaDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public void verMensaje(int id) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idMensajeInternoDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idMensajeInternoDato", id + "");
    }
    


    public boolean isFaltaLeer() {
        
        return faltaLeer;
    }

    public void setFaltaLeer(boolean faltaLeer) {
        this.faltaLeer = faltaLeer;
    }
    
    public String getReference(Mensaje_Interno mensajeInterno)
    {
        if(mensajeInterno!=null && mensajeInterno.getId_alquiler()>-1)
        {
            return mensajeInterno.getId_alquiler()+"";
        }
        return "";
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    
    public boolean faltaLeer(Mensaje_Interno mensajeInt)
    {
        if(mensajeInt==null)
        {
            return false;
        }
        if(usuarioLogueado==null)
        {
            return false;            
        }
        
        List<Mensaje_Interno_Destino> listaDestinos= mensajeInternoDestinoEJB.listaDestinosDeMensaje(mensajeInt.getId());
        for(int l=0;l<listaDestinos.size();l++)
        {
            Mensaje_Interno_Destino mensajeDestino= listaDestinos.get(l);
            if(mensajeDestino.getUsuarioDestino()!=null && mensajeDestino.getUsuarioDestino().getId()==usuarioLogueado.getId() && mensajeDestino.getLeido()==0)
            {
                return true;
            }
        }
        return false;
        
    }

    

}
