/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AutoridadFacadeLocal;
import com.piantino.ejb.Tipo_MultaFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.ejb.WitnessFacadeLocal;
import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Autoridad;
import com.piantino.model.Multa;
import com.piantino.model.Tipo_multa;
import com.piantino.model.Usuario;
import com.piantino.model.Witness;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class DatosMultaBlancoController implements Serializable {

    private String nombreCompleto = "";

    private String direccion = "";

    private String suburb = "";

    private String state = "";

    private String codPost = "";

    private String pais = "";

    private Date dob = null;

    private String licencia = "";

    private String paisIssued = "";

    private String rentalNumber = "";

    private String rego = "";

    private Usuario usuarioLogueado;

    @EJB
    private Tipo_MultaFacadeLocal tipoMultaEJB;

    List<Tipo_multa> listaTipoMulta = new ArrayList<Tipo_multa>();

    @EJB
    private AutoridadFacadeLocal autoridadEJB;

    List<Autoridad> listaAutoridades = new ArrayList<Autoridad>();

    @EJB
    private WitnessFacadeLocal witnessEJB;

    private List<Witness> listaWitness = new ArrayList<Witness>();

    @EJB
    private UsuarioFacadeLocal usuarioEJB;

    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

    private Multa multaActual = new Multa();

    @PostConstruct
    private void init() {

        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        listaTipoMulta = tipoMultaEJB.findAll();
        listaAutoridades = autoridadEJB.findAll();
        listaWitness = witnessEJB.findAll();
        listaUsuarios = usuarioEJB.findAll();
        System.out.println("prueba");
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCodPost() {
        return codPost;
    }

    public void setCodPost(String codPost) {
        this.codPost = codPost;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getPaisIssued() {
        return paisIssued;
    }

    public void setPaisIssued(String paisIssued) {
        this.paisIssued = paisIssued;
    }

    public String getRentalNumber() {
        return rentalNumber;
    }

    public void setRentalNumber(String rentalNumber) {
        this.rentalNumber = rentalNumber;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public List<Tipo_multa> getListaTipoMulta() {
        return listaTipoMulta;
    }

    public void setListaTipoMulta(List<Tipo_multa> listaTipoMulta) {
        this.listaTipoMulta = listaTipoMulta;
    }

    public List<Autoridad> getListaAutoridades() {
        return listaAutoridades;
    }

    public void setListaAutoridades(List<Autoridad> listaAutoridades) {
        this.listaAutoridades = listaAutoridades;
    }

    public List<Witness> getListaWitness() {
        return listaWitness;
    }

    public void setListaWitness(List<Witness> listaWitness) {
        this.listaWitness = listaWitness;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Multa getMultaActual() {
        return multaActual;
    }

    public void setMultaActual(Multa multaActual) {
        this.multaActual = multaActual;
    }

    public void imprimir() {

        if (this.multaActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a fine in BD "));
            return;
        }
        if (this.multaActual.getAutoridad() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a authority"));
            return;
        }

        if (this.multaActual.getWitness() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a witness "));
            return;
        }
        
        this.multaActual.setAutoridad(autoridadEJB.find(this.multaActual.getAutoridad().getId()));
        this.multaActual.setWitness(witnessEJB.find(this.multaActual.getWitness().getId()));
        this.multaActual.setUsuario(usuarioLogueado);
        
        

        Map<String, Object> params = new HashMap<>();
        params.put("NombreAutoridad", this.multaActual.getAutoridad().getNombre());
        params.put("DireccionAutoridad", this.multaActual.getAutoridad().getDireccion());
        params.put("suburbioAutoridad", this.multaActual.getAutoridad().getCiudad());
        params.put("estadoAutoridad", this.multaActual.getAutoridad().getState().getNombre());
        params.put("codAutoridad", this.multaActual.getAutoridad().getCodigo_postal());
        if (this.multaActual.getAutoridad().getPais() == null) {
            params.put("PaisAutoridad", "Australia");
        } else {
            params.put("PaisAutoridad", this.multaActual.getAutoridad().getPais().getNombre());
        }
        params.put("TextoAutoridad", this.multaActual.getAutoridad().getText_name());
        params.put("regoMulta", this.rego);
        params.put("numeroMulta", this.multaActual.getNumero());
        String fechaMultaStr = "";

        fechaMultaStr = this.multaActual.getFechaMulta();
        params.put("fechaMulta", fechaMultaStr);
        params.put("referencia", this.rentalNumber);
        params.put("driverNombre", this.nombreCompleto);
        params.put("direccionDriver", this.direccion);
        String dobStr = "";
        SimpleDateFormat sdfDob = new SimpleDateFormat("dd/MM/yyyy");
        if (this.dob != null) {

            dobStr = sdfDob.format(this.dob);
        }
        params.put("DOBDriver", dobStr);
        params.put("numeroLicencia", this.licencia);
        params.put("licenciaInssueIn", this.paisIssued);
        if (this.multaActual.getUsuario() != null) {
            params.put("declaradoBy", this.multaActual.getUsuario().getNombre() + " " + this.multaActual.getUsuario().getApellido());
        } else {
            params.put("declaradoBy", "");
        }

        params.put("declaradoCuando", sdfDob.format(this.multaActual.getFechaCargada()));
        params.put("nombreWitness", this.multaActual.getWitness().getNombre());
        params.put("tituloWitness", this.multaActual.getWitness().getTittle());
        params.put("StateDriver", this.state);
        params.put("CodPostalDriver", this.codPost);
        params.put("PaisDriver", this.pais);
        params.put("SuburbDriver", this.suburb);
        params.put("DireccionWitness", this.multaActual.getWitness().getDireccion());

        try {

            /*SEARCH THE FILE (if has other path please change it!)*/
            File f = new File("reportes\\Multa.jrxml");

            if (f.exists()) {
                System.out.println("Existe " + f.getAbsolutePath());
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(f.getAbsolutePath());
            JRDataSource vacio = new JREmptyDataSource(1);
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, vacio);

            // Export the report to a PDF file
            //JasperExportManager.exportReportToPdfFile(jasperPrint, reportPathBase + "conr.pdf");
            // Export the report to a PDF file
            //    JasperExportManager.exportReportToPdfFile(jasperPrint, "reportes\\Multa.pdf");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "inline; filename=jsfReporte.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            //JasperPrintManager.printReport(jasperPrint, false);
            stream.flush();
            stream.close();

            FacesContext.getCurrentInstance().responseComplete();

            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
