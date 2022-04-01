/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Alquiler_ClienteFacadeLocal;
import com.piantino.ejb.Alquiler_ImpuestoFacadeLocal;
import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.ejb.MensajeFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.TemplateFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Alquiler_Impuesto;
import com.piantino.model.Cliente;
import com.piantino.model.Impuesto;
import com.piantino.model.Mensaje;
import com.piantino.model.Pago;
import com.piantino.model.Template;
import com.piantino.model.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class DatosMensajeController implements Serializable {

    @EJB
    private TemplateFacadeLocal templateEJB;

    private List<Template> listaTemplate;

    private List<Alquiler> listaAlquileres;

    private DualListModel<Alquiler> themes;

    private DualListModel<Cliente> themesCliente;

    @EJB
    private Alquiler_ImpuestoFacadeLocal impuestoAlquilerEJB;

    @EJB
    private PagoFacadeLocal pagoEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    @EJB
    private Alquiler_ClienteFacadeLocal alquilerClienteEJB;

    @EJB
    private MensajeFacadeLocal mensajeEJB;

    private Template templateSeleccionado = new Template();

    private boolean mostrarMensajeCustom = true;

    private String mensajeCustom = "";

    private UploadedFile file;

    private Usuario usuarioLogueado;

    private List<String> listaMensajes = new ArrayList<String>();

    private int filtro = 0;

    private int tipoMostrar = 0;

    private boolean mostrarBuenCliente = false;

    private boolean mostrarAlquileresActivos = true;

    @EJB
    private ClienteFacadeLocal clienteEJB;

    private List<Cliente> listaClientesActivos = new ArrayList<Cliente>();

    private File imagen;

    private String nombreImagen = "";
    
    private SenderSMSTelstra senderSMSTelstra;

    @PostConstruct
    private void init() {
        listaTemplate = templateEJB.buscarTemplateActivo();
        listaAlquileres = alquilerEJB.getAlquileresHired();
        listaClientesActivos = clienteEJB.getTodosLosBuenosClientes();
        List<Alquiler> citiesTarget = new ArrayList<Alquiler>();
        List<Cliente> clientesTarget = new ArrayList<Cliente>();
        themesCliente = new DualListModel<>(listaClientesActivos, clientesTarget);
        themes = new DualListModel<>(listaAlquileres, citiesTarget);
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (this.usuarioLogueado == null) {
            System.out.println("Perdimos el usuario");
            return;
        }
        
        senderSMSTelstra = new SenderSMSTelstra();
    }

    public List<Template> getListaTemplate() {
        return listaTemplate;
    }

    public void templateSelecc() {
        if (this.templateSeleccionado.getId() == -1) {
            this.mostrarMensajeCustom = true;
        } else {
            this.mostrarMensajeCustom = false;
        }
    }

    public void tipoSelecc() {
        if (tipoMostrar == 1) {
            mostrarBuenCliente = true;
            mostrarAlquileresActivos = false;
        } else if (tipoMostrar == 0) {
            mostrarAlquileresActivos = true;
            mostrarBuenCliente = false;
        }

    }

    public void setListaTemplate(List<Template> listaTemplate) {
        this.listaTemplate = listaTemplate;
    }

    public List<Alquiler> getListaAlquileres() {
        return listaAlquileres;
    }

    public void setListaAlquileres(List<Alquiler> listaAlquileres) {
        this.listaAlquileres = listaAlquileres;
    }

    public String valorListaAlq(Alquiler alq) {
        String mensaje = "";
        if (alq != null) {
            mensaje = alq.getId() + "";
        }
        Alquiler_Cliente alqCli = alquilerClienteEJB.buscarClientePrincipalPorAlquiler(alq.getId());
        if (alqCli != null) {
            mensaje = mensaje + " " + alqCli.getCliente().getNombre() + " - " + alqCli.getCliente().getApellido();
        }

        return mensaje;
    }

    public String valorListaCli(Cliente cli) {
        String mensaje = "";
        if (cli != null) {
            mensaje = cli.getNombre() + " " + cli.getApellido();
        }
        return mensaje;
    }

    public DualListModel<Alquiler> getThemes() {
        return themes;
    }

    public void setThemes(DualListModel<Alquiler> themes) {
        this.themes = themes;
    }

    public DualListModel<Cliente> getThemesCliente() {
        return themesCliente;
    }

    public void setThemesCliente(DualListModel<Cliente> themesCliente) {
        this.themesCliente = themesCliente;
    }

    public Template getTemplateSeleccionado() {
        return templateSeleccionado;
    }

    public void setTemplateSeleccionado(Template templateSeleccionado) {
        this.templateSeleccionado = templateSeleccionado;
    }

    public void summit() {
        String mensaje = "";
        this.listaMensajes.clear();
        if (this.mostrarMensajeCustom) {
            if (this.mostrarBuenCliente) {
                if (this.mensajeCustom.trim().length() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The message can't be empty"));
                    return;
                }
                mensaje = this.mensajeCustom;
                for (int k = 0; k < this.themesCliente.getTarget().size(); k++) {
                    Cliente cli = (Cliente) this.themesCliente.getTarget().get(k);
                    if (cli.getMovil() == null || cli.getMovil().trim().length() == 0) {
                        listaMensajes.add("Client: " +cli.getApellido() + " Does not have a defined mobile phone");
                        continue;
                    }
                    String movil = cli.getMovil();
                    String respuesta = this.senderSMSTelstra.enviarSMSTelstra(movil, mensaje);
                    if (respuesta.trim().length() > 0) {
                        listaMensajes.add("Client: " + cli.getApellido() + " Message: " + respuesta);
                    } else {
                        System.out.println("guardarSMSen el historial custom");
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
                        Mensaje men = new Mensaje();
                        men.setFecha_hora(c.getTime());
                        men.setCliente(cli);
                        men.setUsuario(usuarioLogueado);
                        men.setMensaje(mensaje);
                        men.setNumero(movil);
                        mensajeEJB.create(men);
                    }
                }
            } else {
                if (this.mensajeCustom.trim().length() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The message can't be empty"));
                    return;
                }
                mensaje = this.mensajeCustom;
                for (int k = 0; k < this.themes.getTarget().size(); k++) {
                    Alquiler alq = (Alquiler) this.themes.getTarget().get(k);
                    Alquiler_Cliente AlqCliente = this.alquilerClienteEJB.buscarClientePrincipalPorAlquiler(alq.getId());
                    if (AlqCliente == null || AlqCliente.getCliente() == null) {
                        this.listaMensajes.add("Reference: " + alq.getId() + " has no associated client");
                        continue;
                    }
                    if (AlqCliente.getCliente().getMovil().trim().length() == 0) {
                        listaMensajes.add("Client: " + AlqCliente.getCliente().getApellido() + " Reference: " + AlqCliente.getAlquiler().getId() + " Does not have a defined mobile phone");
                        continue;
                    }
                    String movil = AlqCliente.getCliente().getMovil();
                    String respuesta = this.senderSMSTelstra.enviarSMSTelstra(movil, mensaje);
                    if (respuesta.trim().length() > 0) {
                        listaMensajes.add("Client: " + AlqCliente.getCliente().getApellido() + " Reference: " + AlqCliente.getAlquiler().getId() + " Message: " + respuesta);
                    } else {
                        System.out.println("guardarSMSen el historial custom");
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
                        Mensaje men = new Mensaje();
                        men.setFecha_hora(c.getTime());
                        men.setCliente(AlqCliente.getCliente());
                        men.setUsuario(usuarioLogueado);
                        men.setMensaje(mensaje);
                        men.setNumero(movil);
                        men.setAlquiler(alq);
                        mensajeEJB.create(men);
                    }
                }
            }
        } else {
            Template temp = templateEJB.find(this.getTemplateSeleccionado().getId());
            if (temp == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The selected template does not exist"));
                return;
            }
            mensaje = temp.getTexto();
            if (mensaje.trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The selected template has an empty message defined, you have to define a message"));
                return;
            }
            for (int k = 0; k < this.themes.getTarget().size(); k++) {
                Alquiler alq = (Alquiler) this.themes.getTarget().get(k);
                Alquiler_Cliente AlqCliente = this.alquilerClienteEJB.buscarClientePrincipalPorAlquiler(alq.getId());
                if (AlqCliente == null || AlqCliente.getCliente() == null) {
                    this.listaMensajes.add("Reference: " + alq.getId() + " has no associated client");
                    continue;
                }
                if (AlqCliente.getCliente().getMovil().trim().length() == 0) {
                    listaMensajes.add("Client: " + AlqCliente.getCliente().getApellido() + " Reference: " + AlqCliente.getAlquiler().getId() + " Does not have a defined mobile phone");
                    continue;
                }
                mensaje = temp.getTexto();
                boolean enviar = true;

                if (!enviar) {
                    continue;
                } else {
                    String movil = AlqCliente.getCliente().getMovil();
                    String respuesta = this.senderSMSTelstra.enviarSMSTelstra(movil, mensaje);
                    if (respuesta.trim().length() > 0) {
                        listaMensajes.add("Client: " + AlqCliente.getCliente().getApellido() + " Reference: " + AlqCliente.getAlquiler().getId() + " Message: " + respuesta);
                    } else {
                        System.out.println("guardarSMSen el historial");
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
                        Mensaje men = new Mensaje();
                        men.setFecha_hora(c.getTime());
                        men.setCliente(AlqCliente.getCliente());
                        men.setUsuario(usuarioLogueado);
                        men.setMensaje(mensaje);
                        men.setNumero(movil);
                        men.setAlquiler(alq);
                        mensajeEJB.create(men);
                    }
                }
            }
        }
        if (listaMensajes.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Messages sent successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Messages sent with some errors"));
        }
    }


    public List<String> getListaMensajes() {
        return listaMensajes;
    }

    public void setListaMensajes(List<String> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    public boolean isMostrarMensajeCustom() {
        return mostrarMensajeCustom;
    }

    public void setMostrarMensajeCustom(boolean mostrarMensajeCustom) {
        this.mostrarMensajeCustom = mostrarMensajeCustom;
    }

    public String getMensajeCustom() {
        return mensajeCustom;
    }

    public void setMensajeCustom(String mensajeCustom) {
        this.mensajeCustom = mensajeCustom;
    }

    public boolean isMostrarBuenCliente() {
        return mostrarBuenCliente;
    }

    public void setMostrarBuenCliente(boolean mostrarBuenCliente) {
        this.mostrarBuenCliente = mostrarBuenCliente;
    }

    public boolean isMostrarAlquileresActivos() {
        return mostrarAlquileresActivos;
    }

    public void setMostrarAlquileresActivos(boolean mostrarAlquileresActivos) {
        this.mostrarAlquileresActivos = mostrarAlquileresActivos;
    }

    public int getTipoMostrar() {
        return tipoMostrar;
    }

    public void setTipoMostrar(int tipoMostrar) {
        this.tipoMostrar = tipoMostrar;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void fileUploadListener(FileUploadEvent e) {
        // Get uploaded file from the FileUploadEvent
        this.file = e.getFile();
        // Print out the information of the file
        System.out.println("Uploaded File Name Is :: " + file.getFileName() + " :: Uploaded File Size :: " + file.getSize());

    }

    public void upload() {
        if (file != null && file.getFileName().trim().length() > 0) {
            imagen = this.uploadedFileToFileConverter(file);
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public File uploadedFileToFileConverter(UploadedFile uf) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //Add you expected file encoding here:
        System.setProperty("file.encoding", "UTF-8");
        File newFile = new File(uf.getFileName());
        try {

            inputStream = null;//uf.getInputStream();
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            //Do something with the Exception (logging, etc.)
        }

        return newFile;
    }

    public String getNombreImagen() {
        if (imagen != null) {
            return imagen.getName();
        } else {
            nombreImagen = "";
        }
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

}
