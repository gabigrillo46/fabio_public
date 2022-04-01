/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.Mensaje_InternoFacadeLocal;
import com.piantino.ejb.Mensaje_Interno_DestinoFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Mensaje;
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
public class DatosMensajeInternoController implements Serializable {

    private Mensaje_Interno mensajeInternoConsulta;

    private boolean mostrarNuevaNota = false;

    private String accion = "";

    private boolean mostrarMensajeConsulta = false;

    private Mensaje_Interno mensajeInternoActual = new Mensaje_Interno();

    @EJB
    private Mensaje_Interno_DestinoFacadeLocal mensajeInternoDestinoEJB;

    @EJB
    private Mensaje_InternoFacadeLocal mensajeInternoEJB;

    private List<String> listaUsuarioMensajeCOnsulta = new ArrayList<String>();

    private List<Mensaje_Interno> listaMensajesInternosRelacionados;

    private List<String> listaIDUsuariosDestinatarios;

    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

    @EJB
    private UsuarioFacadeLocal usuarioEJB;

    private Usuario usuarioLogueado;

    private List<String> listaMensajes = new ArrayList<String>();

    Calendar c;
    
    private SenderSMSTelstra senderSMSTeltra;

    @PostConstruct
    private void init() {
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        String idMensajeInternoSTR = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idMensajeInternoDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idMensajeInternoDato");
        if (idMensajeInternoSTR != null && idMensajeInternoSTR.length() > 0) {
            int idMensajeInterno = Integer.parseInt(idMensajeInternoSTR);
            this.mensajeInternoConsulta = mensajeInternoEJB.getMensajeInternoPorId(idMensajeInterno);
            this.listaUsuarioMensajeCOnsulta.clear();
            if (mensajeInternoConsulta == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Message not found"));
                return;
            }
            List<Mensaje_Interno_Destino> listaDestinos = mensajeInternoDestinoEJB.listaDestinosDeMensaje(mensajeInternoConsulta.getId());
            for (int l = 0; l < listaDestinos.size(); l++) {
                Mensaje_Interno_Destino mid = listaDestinos.get(l);
                this.listaUsuarioMensajeCOnsulta.add(mid.getUsuarioDestino().getNombre());
            }

            if (this.mensajeInternoConsulta.getId_alquiler() > -1) {
                this.listaMensajesInternosRelacionados = mensajeInternoEJB.buscarMensajesInternosPorIdAlquiler(this.mensajeInternoConsulta.getId_alquiler());
            } else if (this.mensajeInternoConsulta.getId_primero() > -1) {
                this.listaMensajesInternosRelacionados = mensajeInternoEJB.buscarMensajesInternosRelacionado(this.mensajeInternoConsulta.getId_primero());
            }
            List<Mensaje_Interno_Destino> listaMensajeInternoDestino = mensajeInternoDestinoEJB.listaDestinosDeMensaje(this.mensajeInternoConsulta.getId());
            for (int k = 0; k < listaMensajeInternoDestino.size(); k++) {
                Mensaje_Interno_Destino mensajeInternoDestino = listaMensajeInternoDestino.get(k);
                if (mensajeInternoDestino.getUsuarioDestino() != null) {
                    if (mensajeInternoDestino.getUsuarioDestino().getId() == usuarioLogueado.getId()) {
                        mensajeInternoDestino.setLeido(1);
                        mensajeInternoDestinoEJB.edit(mensajeInternoDestino);
                    }
                }
            }

            for (int l = 0; l < this.listaMensajesInternosRelacionados.size(); l++) {
                Mensaje_Interno mensaje = this.listaMensajesInternosRelacionados.get(l);
                List<Mensaje_Interno_Destino> listaDestinosMensaje = mensajeInternoDestinoEJB.listaDestinosDeMensaje(mensaje.getId());
                for (int p = 0; p < listaDestinosMensaje.size(); p++) {
                    Mensaje_Interno_Destino mensajeDestino = listaDestinosMensaje.get(p);
                    if (mensajeDestino.getUsuarioDestino() != null) {
                        if (mensajeDestino.getUsuarioDestino().getId() == usuarioLogueado.getId()) {
                            mensajeDestino.setLeido(1);
                            mensajeInternoDestinoEJB.edit(mensajeDestino);
                        }
                    }
                }
            }
            accion = "M";
        } else {
            this.mostrarMensajeConsulta = false;
            this.mostrarNuevaNota = true;
            accion = "R";
        }
        this.listaUsuarios = usuarioEJB.listaUsuarioActivos();
        senderSMSTeltra=new SenderSMSTelstra();
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

    public void verMensaje(int id) {
        this.mensajeInternoConsulta = mensajeInternoEJB.getMensajeInternoPorId(id);
        this.listaUsuarioMensajeCOnsulta.clear();
        if (mensajeInternoConsulta == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Message not found"));
            return;
        }
        List<Mensaje_Interno_Destino> listaDestinos = mensajeInternoDestinoEJB.listaDestinosDeMensaje(mensajeInternoConsulta.getId());
        for (int l = 0; l < listaDestinos.size(); l++) {
            Mensaje_Interno_Destino mid = listaDestinos.get(l);
            this.listaUsuarioMensajeCOnsulta.add(mid.getUsuarioDestino().getNombre());
        }
        this.mostrarMensajeConsulta = true;
        this.mostrarNuevaNota = false;

    }

    public Mensaje_Interno getMensajeInternoConsulta() {
        return mensajeInternoConsulta;
    }

    public void setMensajeInternoConsulta(Mensaje_Interno mensajeInternoConsulta) {
        this.mensajeInternoConsulta = mensajeInternoConsulta;
    }

    public List<String> getListaUsuarioMensajeCOnsulta() {
        return listaUsuarioMensajeCOnsulta;
    }

    public void setListaUsuarioMensajeCOnsulta(List<String> listaUsuarioMensajeCOnsulta) {
        this.listaUsuarioMensajeCOnsulta = listaUsuarioMensajeCOnsulta;
    }

    public List<Mensaje_Interno> getListaMensajesInternosRelacionados() {
        return listaMensajesInternosRelacionados;
    }

    public void setListaMensajesInternosRelacionados(List<Mensaje_Interno> listaMensajesInternosRelacionados) {
        this.listaMensajesInternosRelacionados = listaMensajesInternosRelacionados;
    }

    public boolean isMostrarNuevaNota() {
        return mostrarNuevaNota;
    }

    public void setMostrarNuevaNota(boolean mostrarNuevaNota) {
        this.mostrarNuevaNota = mostrarNuevaNota;
        this.mostrarMensajeConsulta = !mostrarNuevaNota;
    }

    public boolean isMostrarMensajeConsulta() {
        return mostrarMensajeConsulta;
    }

    public void setMostrarMensajeConsulta(boolean mostrarMensajeConsulta) {
        this.mostrarMensajeConsulta = mostrarMensajeConsulta;
    }

    public List<String> getListaIDUsuariosDestinatarios() {
        return listaIDUsuariosDestinatarios;
    }

    public void setListaIDUsuariosDestinatarios(List<String> listaIDUsuariosDestinatarios) {
        this.listaIDUsuariosDestinatarios = listaIDUsuariosDestinatarios;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Mensaje_Interno getMensajeInternoActual() {
        return mensajeInternoActual;
    }

    public void setMensajeInternoActual(Mensaje_Interno mensajeInternoActual) {
        this.mensajeInternoActual = mensajeInternoActual;
    }
    
    public String getReference(Mensaje_Interno mensajeInterno)
    {
        if(mensajeInterno!=null && mensajeInterno.getId_alquiler()>-1)
        {
            return mensajeInterno.getId_alquiler()+"";
        }
        return "";
    }

    public void crearMensaje() {
        if (this.mensajeInternoActual.getTexto().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The message can't be empty"));
            return;
        }

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
                    todosEnviados = false;
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
                        todosEnviados = false;
                        continue;
                    }
                    listaUsuarioDestinatarios.add(usuarioDestino);
                }
            }
        }

        List<Mensaje_Interno_Destino> listaDestinos = new ArrayList<Mensaje_Interno_Destino>();

        for (int l = 0; l < listaUsuarioDestinatarios.size(); l++) {
            Usuario usuarioDestinoCOnNumero = listaUsuarioDestinatarios.get(l);
            String respuesta = this.senderSMSTeltra.enviarSMSTelstra(usuarioDestinoCOnNumero.getMobile(), this.mensajeInternoActual.getTexto());
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
            mensajeInternoEJB.create(mensajeInternoActual);
            mensajeInternoActual.setId_primero(mensajeInternoActual.getId());
            mensajeInternoEJB.edit(mensajeInternoActual);
            for (int p = 0; p < listaDestinos.size(); p++) {
                Mensaje_Interno_Destino mensajeInterno = listaDestinos.get(p);
                mensajeInterno.setMensajeInterno(mensajeInternoActual);
                mensajeInterno.setNumero(mensajeInterno.getUsuarioDestino().getMobile());
                mensajeInternoDestinoEJB.create(mensajeInterno);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "All messages have been sent correctly"));
            this.mensajeInternoActual = new Mensaje_Interno();
            this.listaMensajes.clear();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Message not registered, please check the reply messages"));
        }

    }

    public void enviarMensaje() {

        if (this.mensajeInternoConsulta == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Cannot find the original message"));
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

        if (this.mensajeInternoConsulta.getId_primero() > -1) {
            this.mensajeInternoActual.setId_primero(this.mensajeInternoConsulta.getId_primero());
        } else if (this.mensajeInternoConsulta.getId_alquiler() > -1) {
            this.mensajeInternoActual.setId_alquiler(this.mensajeInternoConsulta.getId_alquiler());
        }

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
                    todosEnviados = false;
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
                        todosEnviados = false;
                        continue;
                    }
                    listaUsuarioDestinatarios.add(usuarioDestino);
                }
            }
        }

        List<Mensaje_Interno_Destino> listaDestinos = new ArrayList<Mensaje_Interno_Destino>();

        for (int l = 0; l < listaUsuarioDestinatarios.size(); l++) {
            Usuario usuarioDestinoCOnNumero = listaUsuarioDestinatarios.get(l);
            String respuesta = this.senderSMSTeltra.enviarSMSTelstra(usuarioDestinoCOnNumero.getMobile(), this.mensajeInternoActual.getTexto());
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
            mensajeInternoEJB.create(mensajeInternoActual);
            for (int p = 0; p < listaDestinos.size(); p++) {
                Mensaje_Interno_Destino mensajeInterno = listaDestinos.get(p);
                mensajeInterno.setMensajeInterno(mensajeInternoActual);
                mensajeInterno.setNumero(mensajeInterno.getUsuarioDestino().getMobile());
                mensajeInternoDestinoEJB.create(mensajeInterno);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "All messages have been sent correctly"));
            this.mensajeInternoActual = new Mensaje_Interno();
            if (this.mensajeInternoConsulta.getId_alquiler() > -1) {
                this.listaMensajesInternosRelacionados = mensajeInternoEJB.buscarMensajesInternosPorIdAlquiler(this.mensajeInternoConsulta.getId_alquiler());
            } else if (this.mensajeInternoConsulta.getId_primero() > -1) {
                this.listaMensajesInternosRelacionados = mensajeInternoEJB.buscarMensajesInternosRelacionado(this.mensajeInternoConsulta.getId_primero());
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Message not registered, please check the reply messages"));
        }
        this.listaIDUsuariosDestinatarios.clear();
        this.mostrarNuevaNota = false;
    }

    public void cancelatEnviarMensaje() {
        this.mensajeInternoActual = new Mensaje_Interno();
        this.listaIDUsuariosDestinatarios.clear();
        this.mostrarNuevaNota = false;
    }


    public List<String> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(List<String> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
