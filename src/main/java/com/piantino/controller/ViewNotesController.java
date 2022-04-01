/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Mensaje_InternoFacadeLocal;
import com.piantino.ejb.Mensaje_Interno_DestinoFacadeLocal;

import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Mensaje_Interno;
import com.piantino.model.Mensaje_Interno_Destino;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class ViewNotesController implements Serializable {

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    @EJB
    private UsuarioFacadeLocal usuarioEJB;

    private List<Usuario> listaUsuario;

    private List<String> listaIDUsuariosDestinatarios;

    private Alquiler alquilerBD;

    private List<Mensaje_Interno> listaMensajesInternos;

    @EJB
    private Mensaje_InternoFacadeLocal mensajesInternosEJB;

    @EJB
    private Mensaje_Interno_DestinoFacadeLocal mensajeInternoDestinoEJB;

    private String fechaFormato = "";

    private boolean mostrarNuevaNota = false;

    private Mensaje_Interno mensajeInternoActual = new Mensaje_Interno();
    
    private Mensaje_Interno mensajeInternoConsulta = new Mensaje_Interno();
    
    private List<String> listaUsuarioMensajeCOnsulta = new ArrayList<String>();
    
    private boolean mostrarMensajeConsulta=false;

    private Usuario usuarioLogueado;

    private List<String> listaMensajes = new ArrayList<String>();

    Calendar c;
    
    private SenderSMSTelstra senderSMSTelstra;

    @PostConstruct
    private void init() {
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        String idAlquilerStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idAlquilerSMS");
        alquilerBD = alquilerEJB.getAlquilerPorId(Integer.parseInt(idAlquilerStr));
        if (alquilerBD != null) {
            this.listaMensajesInternos = mensajesInternosEJB.buscarMensajesInternosPorIdAlquiler(alquilerBD.getId());
        }
        this.listaUsuario = usuarioEJB.listaUsuarioActivos();
        this.senderSMSTelstra = new SenderSMSTelstra();
        // this.mensajeInternoActual.setUsuarioDestino(new Usuario());
    }

    public String getFechaFormato(Mensaje_Interno m) {
        String resultado = "";
        try {
            SimpleDateFormat sdfDesdeBD = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date fechaDate = sdfDesdeBD.parse(m.getFecha_hora());

            SimpleDateFormat sdfMostrar = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            resultado = sdfMostrar.format(fechaDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public List<Mensaje_Interno> getListaMensajesInternos() {
        return listaMensajesInternos;
    }

    public void setListaMensajesInternos(List<Mensaje_Interno> listaMensajesInternos) {
        this.listaMensajesInternos = listaMensajesInternos;
    }

    public String getFechaFormato() {
        return fechaFormato;
    }

    public void setFechaFormato(String fechaFormato) {
        this.fechaFormato = fechaFormato;
    }

    public boolean isMostrarNuevaNota() {
        return mostrarNuevaNota;
    }

    public void setMostrarNuevaNota(boolean mostrarNuevaNota) {
        this.mostrarNuevaNota = mostrarNuevaNota;
        this.listaMensajes.clear();
        this.mostrarMensajeConsulta=false;
    }

    public Mensaje_Interno getMensajeInternoActual() {
        return mensajeInternoActual;
    }

    public void setMensajeInternoActual(Mensaje_Interno mensajeInternoActual) {
        this.mensajeInternoActual = mensajeInternoActual;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public void cancelatEnviarMensaje() {
        this.mensajeInternoActual = new Mensaje_Interno();
        this.listaIDUsuariosDestinatarios.clear();
        this.setMostrarNuevaNota(false);
    }
    
    public void OKConsulta()
    {
        this.mensajeInternoConsulta=new Mensaje_Interno();
        this.listaUsuarioMensajeCOnsulta.clear();
        this.mostrarMensajeConsulta=false;        
    }

    public void enviarMensaje() {

        if (this.alquilerBD == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Cannot find agreement internally"));
            return;
        }

        if (this.usuarioLogueado == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Cannot find the user login internally"));
            return;
        }
        if (this.mensajeInternoActual.getTexto().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The message can't be empty"));
            return;
        }

        this.mensajeInternoActual.setId_alquiler(this.alquilerBD.getId());
        this.mensajeInternoActual.setUsuarioCreador(usuarioLogueado);

        SimpleDateFormat sdfBD = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.mensajeInternoActual.setFecha_hora(sdfBD.format(c.getTime()));
        this.mensajeInternoActual.setEstado(Mensaje_Interno.ESTADO_ACTIVO);

        List<Usuario> listaUsuarioDestinatarios = new ArrayList();

        boolean todos = false;
        for (int k = 0; k < this.listaIDUsuariosDestinatarios.size(); k++) {
            String idUsuarioStr = this.listaIDUsuariosDestinatarios.get(k);
            if (idUsuarioStr.equalsIgnoreCase("-1")) {
                todos = true;
            }
        }
        boolean todosEnviados = true;
        if (todos) {
            List<Usuario> listaUsuariosActivos = usuarioEJB.listaUsuarioActivos();
            for (int a = 0; a < listaUsuariosActivos.size(); a++) {
                Usuario usuarioDestino = listaUsuariosActivos.get(a);
                if (usuarioDestino.getMobile() == null || usuarioDestino.getMobile().trim().length() == 0) {
                    listaMensajes.add("User: " + usuarioDestino.getNombre() + " doesn't have a defined mobile number");
                    todosEnviados=false;
                    continue;
                }
                listaUsuarioDestinatarios.add(usuarioDestino);
            }
        } else {
            for (int k = 0; k < this.listaIDUsuariosDestinatarios.size(); k++) {
                String idUsuarioStr = this.listaIDUsuariosDestinatarios.get(k);
                Usuario usuarioDestino = usuarioEJB.getUsuarioPorId(Integer.parseInt(idUsuarioStr));
                if (usuarioDestino != null) {
                    if (usuarioDestino.getMobile() == null || usuarioDestino.getMobile().trim().length() == 0) {
                        listaMensajes.add("User: " + usuarioDestino.getNombre() + " doesn't have a defined mobile number");
                        todosEnviados=false;
                        continue;
                    }
                    listaUsuarioDestinatarios.add(usuarioDestino);
                }
            }
        }

        List<Mensaje_Interno_Destino> listaDestinos = new ArrayList<Mensaje_Interno_Destino>();

        for (int l = 0; l < listaUsuarioDestinatarios.size(); l++) {
            Usuario usuarioDestinoCOnNumero = listaUsuarioDestinatarios.get(l);
            String respuesta = this.senderSMSTelstra.enviarSMSTelstra(usuarioDestinoCOnNumero.getMobile(), this.mensajeInternoActual.getTexto());
            //String respuesta = this.enviarMensaje(this.mensajeInternoActual.getTexto(), usuarioDestinoCOnNumero.getMobile());
            if (respuesta.trim().length() == 0) {
                Mensaje_Interno_Destino mensajeInternoDestino = new Mensaje_Interno_Destino();
                mensajeInternoDestino.setUsuarioDestino(usuarioDestinoCOnNumero);
                listaDestinos.add(mensajeInternoDestino);
            } else {
                todosEnviados = false;
                respuesta = usuarioDestinoCOnNumero.getNombre() + " " + respuesta;
                listaMensajes.add(respuesta);
            }
        }

        if (todosEnviados && listaUsuarioDestinatarios.size() > 0) {
            mensajesInternosEJB.create(mensajeInternoActual);
            for (int p = 0; p < listaDestinos.size(); p++) {
                Mensaje_Interno_Destino mensajeInterno = listaDestinos.get(p);
                mensajeInterno.setMensajeInterno(mensajeInternoActual);
                mensajeInterno.setNumero(mensajeInterno.getUsuarioDestino().getMobile());
                mensajeInternoDestinoEJB.create(mensajeInterno);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "All messages have been sent correctly"));
            this.mensajeInternoActual = new Mensaje_Interno();
            this.listaMensajesInternos=mensajesInternosEJB.buscarMensajesInternosPorIdAlquiler(alquilerBD.getId());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Message not registered, please check the reply messages"));
        }
        this.listaIDUsuariosDestinatarios.clear();
        this.mostrarNuevaNota = false;
    }



    public List<String> getListaUsuariosDestinatarios() {
        return listaIDUsuariosDestinatarios;
    }

    public void setListaUsuariosDestinatarios(List<String> listaUsuariosDestinatarios) {
        this.listaIDUsuariosDestinatarios = listaUsuariosDestinatarios;
    }

    public List<String> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(List<String> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }
    
    public void verMensaje(int id)
    {
        this.mensajeInternoConsulta=mensajesInternosEJB.getMensajeInternoPorId(id);
        this.listaUsuarioMensajeCOnsulta.clear();
        this.listaMensajes.clear();
        if(mensajeInternoConsulta==null)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Message not found"));
            return;
        }
        List<Mensaje_Interno_Destino> listaDestinos=mensajeInternoDestinoEJB.listaDestinosDeMensaje(mensajeInternoConsulta.getId());
        for(int l=0;l<listaDestinos.size();l++)
        {
            Mensaje_Interno_Destino mid = listaDestinos.get(l);
            this.listaUsuarioMensajeCOnsulta.add(mid.getUsuarioDestino().getNombre());
        }
        this.mostrarMensajeConsulta=true;
        this.mostrarNuevaNota=false;
    }

    public Mensaje_Interno getMensajeInternoConsulta() {
        return mensajeInternoConsulta;
    }

    public void setMensajeInternoConsulta(Mensaje_Interno mensajeInternoConsulta) {
        this.mensajeInternoConsulta = mensajeInternoConsulta;
    }

    public boolean isMostrarMensajeConsulta() {
        return mostrarMensajeConsulta;
    }

    public void setMostrarMensajeConsulta(boolean mostrarMensajeConsulta) {
        this.mostrarMensajeConsulta = mostrarMensajeConsulta;
    }

    public List<String> getListaUsuarioMensajeCOnsulta() {
        return listaUsuarioMensajeCOnsulta;
    }

    public void setListaUsuarioMensajeCOnsulta(List<String> listaUsuarioMensajeCOnsulta) {
        this.listaUsuarioMensajeCOnsulta = listaUsuarioMensajeCOnsulta;
    }

    
    
    

}
