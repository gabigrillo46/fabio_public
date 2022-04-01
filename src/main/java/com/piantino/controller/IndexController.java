package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.EventoFacadeLocal;
import com.piantino.ejb.ParametroFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Evento;
import com.piantino.model.Parametro;

import com.piantino.model.Usuario;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@ManagedBean(name = "indexController")
@SessionScoped
public class IndexController implements Serializable {

    @EJB
    private UsuarioFacadeLocal usuarioEJB;

    @EJB
    private ParametroFacadeLocal parametroEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private Usuario usuario = new Usuario();

    private Usuario usuarioCambioContra = null;

    private Usuario usuarioNuevo = null;

    private String confirmacionPassword = "";

    private String codigoVerificacion = "";

    private boolean mostrarPregunta = false;

    private boolean mostrarLogin = true;

    private boolean mostrarRegistro = false;

    private boolean mostrarEmail = false;

    private boolean mostrarCodigoVerificacion = false;

    private boolean mostrarPassword = false;

    private Calendar c;

    @EJB
    private EventoFacadeLocal eventoEJB;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @PostConstruct
    private void init() {
        List<Usuario> lista = usuarioEJB.findAll();

        this.usuarioCambioContra = new Usuario();
        this.usuarioNuevo = new Usuario();
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        /*	boolean hayUnoProcesandoPagos = false;
        System.out.println("Started");
        ThreadMXBean managementFactory = ManagementFactory.getThreadMXBean();
        long threadIds[] = managementFactory.getAllThreadIds();
        for (int i = 0; i < threadIds.length; i++) {
            ThreadInfo info = managementFactory.getThreadInfo(threadIds[i]);
            if (info.getThreadName().equalsIgnoreCase("procesadorPago")) {
                hayUnoProcesandoPagos = true;
            }
            System.out.println("Thread name = " + info.getThreadName() + " Thread id = " + info.getThreadId() + " Thread state = " + info.getThreadState());
        }

       if (!hayUnoProcesandoPagos) {
           System.out.println("Lanzamos hilo de pagos");
           Thread threadPagos = new PagosController();
            threadPagos.start();
        }*/

        System.out.println("#############");
        System.out.println(Thread.currentThread().getAllStackTraces());
    }

    public void olvidoLaContrasena() {
        usuarioCambioContra = new Usuario();
        this.setMostrarPregunta(true);
        this.setMostrarLogin(false);
    }

    public void aceptoEmail() {
        this.setMostrarCodigoVerificacion(false);
        this.setMostrarEmail(false);
        this.setMostrarPassword(true);
    }

    public void aceptoRegistro() {
        if (this.mostrarEmail) {
            this.enviarEmail();
        } else if (this.mostrarCodigoVerificacion) {
            this.aceptoCodigoVerificacion();
        } else if (this.mostrarPassword) {
            this.aceptoPasswordRegistro();
        }
    }

    private void enviarEmail() {
        if (this.usuarioNuevo == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The user is null"));
            return;
        }

        if (this.usuarioNuevo.getEmail() == null || this.usuarioNuevo.getEmail().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter your email"));
            return;
        }

        if (this.usuarioNuevo.getEmail().contains("@") == false || this.usuarioNuevo.getEmail().contains(".") == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The email entered is invalid"));
            return;
        }

        Usuario usuario = this.usuarioEJB.getUsuarioPorEmail(this.usuarioNuevo.getEmail().trim());
        if (usuario != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "User is already registered, the password will be sent to your email."));
            String mensaje="Your password is:";
            String codigo =usuario.getPassword();
            Thread emailThread = new EMAIL(mensaje, codigo, this.usuarioNuevo.getEmail(), "Password");
            emailThread.start();
            this.setMostrarRegistro(false);
            this.setMostrarLogin(true);
            return;
        }

        Random random = new Random();
        String codigoVerificacion = "";
        while (codigoVerificacion.trim().length() < 6) {
            codigoVerificacion += random.nextInt(9);
        }

        this.codigoVerificacion = codigoVerificacion;
        
        String mensaje ="The verification code is:";
        String codigo = codigoVerificacion;

        Thread threadPagos = new EMAIL(mensaje, codigo, this.usuarioNuevo.getEmail(), "test");
        threadPagos.start();
        this.setMostrarEmail(false);
        this.setMostrarCodigoVerificacion(true);
        this.setMostrarPassword(false);
    }

    public void aceptoCodigoVerificacion() {
        if (this.usuarioNuevo.getCodigo_validacion() == null || this.usuarioNuevo.getCodigo_validacion().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a verification code"));
            return;
        }

        if (!this.usuarioNuevo.getCodigo_validacion().equalsIgnoreCase(this.codigoVerificacion)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "THe verificacion code is wrong"));
            return;
        }

        this.setMostrarCodigoVerificacion(false);
        this.setMostrarPassword(true);
    }

    public void aceptoPasswordRegistro() {
        if (this.usuarioNuevo.getPassword() == null || this.usuarioNuevo.getPassword().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a passport"));
            return;
        }

        if (this.confirmacionPassword == null || this.confirmacionPassword.trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to confirm your password"));
            return;
        }
        if (!this.usuarioNuevo.getPassword().trim().equalsIgnoreCase(this.confirmacionPassword.trim())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The password and confirmation don't match"));
            return;
        }
        this.usuarioEJB.create(this.usuarioNuevo);
        this.setMostrarLogin(true);
        this.setMostrarRegistro(false);
    }

    public void cancelarRegistro() {
        this.setMostrarCodigoVerificacion(false);
        this.setMostrarPassword(false);
        this.setMostrarEmail(true);
        this.setMostrarRegistro(false);
        this.setMostrarLogin(true);
    }

    public void aceptoRespuestaPregunta() {
        if (usuarioCambioContra == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is no user"));
            return;
        }

        Usuario usuarioBD = usuarioEJB.getUsuarioPorEmail(this.usuarioCambioContra.getEmail());
        if (usuarioBD == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is no user with the email entered"));
            return;
        }

        if (usuarioBD != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The password will be sent to your email."));
                        String mensaje="Your password is:";
            String codigo =usuarioBD.getPassword();

            Thread emailThread = new EMAIL(mensaje, codigo, usuarioBD.getEmail(), "Password");
            emailThread.start();
            this.setMostrarPregunta(false);
            this.setMostrarLogin(true);
        }
    }

    public void cancelarPreguntaRespuesta() {
        this.setMostrarPregunta(false);
        this.setMostrarLogin(true);
    }

    public void registroNuevoUsuario() {
        this.setMostrarLogin(false);
        this.setMostrarPregunta(false);
        this.setMostrarRegistro(true);
        this.setMostrarEmail(true);
        this.setMostrarCodigoVerificacion(false);
        this.setMostrarPassword(false);
    }

    public String iniciarSesion() {
        Usuario usuarioLogin = null;
        String redireccion = null;
        try {
            if (usuario == null || usuario.getEmail().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter your user name"));
                return null;
            }

            usuarioLogin = usuarioEJB.iniciarSesion(usuario);
            
            
            if (usuarioLogin != null && usuarioLogin.getPassword().trim().equalsIgnoreCase(this.usuario.getPassword())) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioLogin);
                Evento evento = new Evento();
                evento.setFecha(c.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                evento.setFecha_hora(sdf.format(c.getTime()));
                evento.setTipo(Evento.TYPE_LOGIN);
                evento.setUsuario(usuarioLogin);
                this.eventoEJB.create(evento);
                redireccion = "/pantallas/pantallaPrincipal";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Incorrect username or password"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Login failed"));
        }
        return redireccion;
    }

    public Usuario getUsuarioCambioContra() {
        return usuarioCambioContra;
    }

    public void setUsuarioCambioContra(Usuario usuarioCambioContra) {
        this.usuarioCambioContra = usuarioCambioContra;
    }

    public boolean isMostrarPregunta() {
        return mostrarPregunta;
    }

    public void setMostrarPregunta(boolean mostrarPregunta) {
        this.mostrarPregunta = mostrarPregunta;
    }

    public boolean isMostrarLogin() {
        return mostrarLogin;
    }

    public void setMostrarLogin(boolean mostrarLogin) {
        this.mostrarLogin = mostrarLogin;
    }

    public boolean isMostrarRegistro() {
        return mostrarRegistro;
    }

    public void setMostrarRegistro(boolean mostrarRegistro) {
        this.mostrarRegistro = mostrarRegistro;
    }

    public boolean isMostrarEmail() {
        return mostrarEmail;
    }

    public void setMostrarEmail(boolean mostrarEmail) {
        this.mostrarEmail = mostrarEmail;
    }

    public boolean isMostrarCodigoVerificacion() {
        return mostrarCodigoVerificacion;
    }

    public void setMostrarCodigoVerificacion(boolean mostrarCodigoVerificacion) {
        this.mostrarCodigoVerificacion = mostrarCodigoVerificacion;
    }

    public boolean isMostrarPassword() {
        return mostrarPassword;
    }

    public void setMostrarPassword(boolean mostrarPassword) {
        this.mostrarPassword = mostrarPassword;
    }

    public Usuario getUsuarioNuevo() {
        return usuarioNuevo;
    }

    public void setUsuarioNuevo(Usuario usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }

    public String getConfirmacionPassword() {
        return confirmacionPassword;
    }

    public void setConfirmacionPassword(String confirmacionPassword) {
        this.confirmacionPassword = confirmacionPassword;
    }

}
