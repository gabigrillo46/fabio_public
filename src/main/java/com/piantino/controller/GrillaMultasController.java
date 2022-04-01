package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Alquiler_ImpuestoFacadeLocal;
import com.piantino.ejb.AutoridadFacadeLocal;
import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.ejb.ImpuestoFacadeLocal;
import com.piantino.ejb.MultaFacadeLocal;
import com.piantino.ejb.WitnessFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Impuesto;
import com.piantino.model.Impuesto;
import com.piantino.model.Multa;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class GrillaMultasController implements Serializable {

    private List<Multa> listaMultas = new ArrayList();

    @EJB
    private MultaFacadeLocal multaEJB;

    @EJB
    private AutoridadFacadeLocal autoridadEJB;

    @EJB
    private WitnessFacadeLocal witnessEJB;

    @EJB
    private ClienteFacadeLocal clienteEJB;

    private Multa multaSeleccionada;

    private String fechaMostrar;

    private int idMulta = 0;

    private String referencia;

    private String apellido;

    private String rego;

    private String impreso = null;

    private String numeroMulta;

    @EJB
    ImpuestoFacadeLocal impuestoEJB;

    @EJB
    Alquiler_ImpuestoFacadeLocal alquiler_impuestoEJB;

    @EJB
    AlquilerFacadeLocal alquilerEJB;

    @PostConstruct
    public void init() {
        //listaMultas = multaEJB.findAll();
    }

    public List<Multa> getListaMultas() {
        return listaMultas;
    }

    public void setListaMultas(List<Multa> listaMultas) {
        this.listaMultas = listaMultas;
    }

    public Multa getMultaSeleccionada() {
        return multaSeleccionada;
    }

    public void setMultaSeleccionada(Multa multaSeleccionada) {
        this.multaSeleccionada = multaSeleccionada;
    }

    public String getFechaMostrar() {
        return fechaMostrar;
    }

    public void setFechaMostrar(String fechaMostrar) {
        this.fechaMostrar = fechaMostrar;
    }

    public String getFechaMostrar(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
            this.fechaMostrar = sdf.format(fecha);
        } else {
            this.fechaMostrar = "";
        }
        return this.fechaMostrar;
    }

    public String setIdMulta() {
        Multa multaSeleccionada = this.getMultaSeleccionada();
        if (multaSeleccionada != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("idMultaAVer", (this.getMultaSeleccionada().getId() + ""));
            String redireccion = "/pantallas/DatosMulta";
            return redireccion;
        } else {
            return "";
        }
    }

    public void eliminarMulta() {
        Multa multaSeleccionadaa = this.getMultaSeleccionada();
        if (multaSeleccionadaa != null) {
            multaSeleccionadaa.setEstado(Multa.ESTADO_ELIMINADA);
            multaEJB.edit(multaSeleccionadaa);
            this.buscarPorFiltro();

            if (multaSeleccionadaa.getTipo().getId() == Multa.ID_TIPO_TOLL) {
                try {

                    Impuesto impuestoToll = impuestoEJB.getImpuestoParaToll();
                    Alquiler alquilerMulta = multaSeleccionadaa.getAlquiler();

                    boolean encontrado = false;
                    float totalExtras = 0;
                    float restarADeuda = 0;

                    if (impuestoToll != null) {
                        List<Alquiler_Impuesto> listaImpuestos = alquiler_impuestoEJB.buscarImpuestosAlquiler(multaSeleccionadaa.getAlquiler().getId());

                        for (int j = 0; j < listaImpuestos.size(); j++) {
                            Alquiler_Impuesto impuestoAlquiler = listaImpuestos.get(j);
                            if (impuestoAlquiler.getImpuesto().getTipo_asociado() > -1 && impuestoAlquiler.getImpuesto().getTipo_asociado() == Impuesto.TIPO_ASOCIADO_TOLL) {
                                encontrado = true;
                                impuestoAlquiler.setCantidad(impuestoAlquiler.getCantidad() - 1);
                                float subtotal = impuestoAlquiler.getCantidad() * impuestoAlquiler.getPrecio();
                                int subtotalInt = (int) (subtotal * 100);
                                subtotal = ((float) subtotalInt) / 100;
                                impuestoAlquiler.setSubtotal(subtotal);
                                alquiler_impuestoEJB.edit(impuestoAlquiler);
                                restarADeuda = impuestoAlquiler.getPrecio();
                            }
                            totalExtras = totalExtras + (((float) impuestoAlquiler.getCantidad()) * impuestoAlquiler.getPrecio());
                        }
                        if (encontrado) {

                            int totalExtrasInt = (int) (totalExtras * 100);
                            totalExtras = ((float) totalExtrasInt) / 100;
                            alquilerMulta.setExtra(totalExtras);

                            float granTotal = alquilerMulta.getTotal() + totalExtras;
                            int granTotalInt = (int) (granTotal * 100);
                            granTotal = ((float) granTotalInt) / 100;
                            alquilerMulta.setGran_total(granTotal);

                            float deudaTotal = alquilerMulta.getDeuda() - restarADeuda;
                            int deudaTotalInt = (int) (deudaTotal * 100);
                            deudaTotal = ((float) deudaTotalInt) / 100;
                            alquilerMulta.setDeuda(deudaTotal);

                            alquilerEJB.edit(alquilerMulta);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public int getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(int idMulta) {
        this.idMulta = idMulta;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public String getNumeroMulta() {
        return numeroMulta;
    }

    public void setNumeroMulta(String numeroMulta) {
        this.numeroMulta = numeroMulta;
    }

    public void buscarPorFiltro() {
        this.listaMultas = multaEJB.buscarPorFiltro(rego, numeroMulta, apellido, referencia, impreso);
    }

    public void imprimir(int idMulta) {
        Multa multaBD = multaEJB.buscarPorId(idMulta);
        if (multaBD == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a fine in BD with id: " + idMulta));
            return;
        }

        if (multaBD.getAutoridad() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a authority in BD with fine id: " + idMulta));
            return;
        }

        if (multaBD.getAlquiler() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a rental in BD with fine id: " + idMulta));
            return;
        }

        if (multaBD.getWitness() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a witness in BD with fine id: " + idMulta));
            return;
        }

        if (multaBD.getClienteInfraccion() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a client in BD with fine id: " + idMulta));
            return;
        }
        if (multaBD.getImpreso() == 0) {
            multaBD.setImpreso(1);
            multaEJB.edit(multaBD);
        }

        multaBD.setAutoridad(autoridadEJB.find(multaBD.getAutoridad().getId()));
        multaBD.setWitness(witnessEJB.find(multaBD.getWitness().getId()));
        multaBD.setClienteInfraccion(clienteEJB.buscarPorID(multaBD.getClienteInfraccion().getId()));

        Map<String, Object> params = new HashMap<>();
        params.put("NombreAutoridad", multaBD.getAutoridad().getNombre());
        params.put("DireccionAutoridad", multaBD.getAutoridad().getDireccion());
        params.put("suburbioAutoridad", multaBD.getAutoridad().getCiudad());
        params.put("estadoAutoridad", multaBD.getAutoridad().getState().getNombre());
        params.put("codAutoridad", multaBD.getAutoridad().getCodigo_postal());
        if (multaBD.getAutoridad().getPais() == null) {
            params.put("PaisAutoridad", "Australia");
        } else {
            params.put("PaisAutoridad", multaBD.getAutoridad().getPais().getNombre());
        }
        params.put("TextoAutoridad", multaBD.getAutoridad().getText_name());
        params.put("regoMulta", multaBD.getRego());
        params.put("numeroMulta", multaBD.getNumero());
        String fechaMultaStr = "";

        fechaMultaStr = multaBD.getFechaMulta();
        params.put("fechaMulta", fechaMultaStr);
        params.put("referencia", multaBD.getAlquiler().getId() + "");
        params.put("driverNombre", multaBD.getClienteInfraccion().getNombre() + " " + multaBD.getClienteInfraccion().getApellido());
        params.put("direccionDriver", multaBD.getClienteInfraccion().getDireccion());
        String dobStr = "";
        SimpleDateFormat sdfDob = new SimpleDateFormat("dd/MM/yyyy");
        if (multaBD.getClienteInfraccion().getDOB() != null) {

            dobStr = sdfDob.format(multaBD.getClienteInfraccion().getDOB());
        }
        params.put("DOBDriver", dobStr);
        params.put("numeroLicencia", multaBD.getClienteInfraccion().getLicencia_numero());
        params.put("licenciaInssueIn", multaBD.getClienteInfraccion().getOtorgadaEN());
        if (multaBD.getUsuario() != null) {
            params.put("declaradoBy", multaBD.getUsuario().getNombre() + " " + multaBD.getUsuario().getApellido());
        } else {
            params.put("declaradoBy", "");
        }

        params.put("declaradoCuando", sdfDob.format(multaBD.getFechaCargada()));
        params.put("nombreWitness", multaBD.getWitness().getNombre());
        params.put("tituloWitness", multaBD.getWitness().getTittle());
        if (multaBD.getClienteInfraccion().getState() != null) {
            params.put("StateDriver", multaBD.getClienteInfraccion().getState().getNombre());
        } else {
            params.put("StateDriver", "");
        }
        params.put("CodPostalDriver", multaBD.getClienteInfraccion().getCodigo_postal());
        if (multaBD.getClienteInfraccion().getPais() != null) {
            params.put("PaisDriver", multaBD.getClienteInfraccion().getPais());
        } else {
            params.put("PaisDriver", "");
        }
        params.put("SuburbDriver", multaBD.getClienteInfraccion().getSuburbio());
        params.put("DireccionWitness", multaBD.getWitness().getDireccion());

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
            stream.flush();
            stream.close();

            FacesContext.getCurrentInstance().responseComplete();

            if (impreso != null && (impreso.equalsIgnoreCase("1") || impreso.equalsIgnoreCase("0"))) {
                this.buscarPorFiltro();
            }

            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getImpreso() {
        return impreso;
    }

    public void setImpreso(String impreso) {
        this.impreso = impreso;
    }

}
