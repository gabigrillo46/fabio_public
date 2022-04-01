/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.Dealer_licenceFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.model.Sucursal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.piantino.model.Auto;
import com.piantino.model.Dealer_licence;
import com.piantino.report.Dtos.Form1Detalle;
import com.piantino.report.Dtos.TransferRegoDealer;
import com.piantino.report.Dtos.TransferRegoDealerDetalle;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class ReportTransferController implements Serializable {

    private String palabraBuscar = "";

    private int idSucursalSelecc = 0;

    private boolean autoTransferidos = false;

    private List<Sucursal> listaSucursales = new ArrayList();

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    @EJB
    private AutoFacadeLocal autoEJB;

    private List<Auto> listaAutoResultado = new ArrayList();

    private List<Auto> listaAutoSeleccionados = new ArrayList();

    private int cantidadResultado = 0;

    private String transferenciaImpresa = "";

    private boolean esImpresa = false;

    private List<Dealer_licence> listaDealerLicence = new ArrayList<>();

    @EJB
    private Dealer_licenceFacadeLocal dealerLicenceEJB;

    private String representante = "";

    private boolean mostrarBotonImprimir = false;

    private String fechaFormateada = "";

    @PostConstruct
    public void init() {
        listaDealerLicence = dealerLicenceEJB.findAll();

        String idSucursalTransferStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idSucursalTransferenciaDato");
        if (idSucursalTransferStr != null && idSucursalTransferStr.trim().length() > 0) {
            int idSucursalTransfer = Integer.parseInt(idSucursalTransferStr);
            this.idSucursalSelecc = idSucursalTransfer;
            this.buscarProFiltro();
        }

    }

    public List<Sucursal> getListaSucursales() {
        this.listaSucursales = this.sucursalEJB.findAll();
        return listaSucursales;

    }

    public void seleccionoUnRegistro(Auto autoSelecc) {
        if (this.listaAutoSeleccionados == null) {
            this.listaAutoSeleccionados = new ArrayList<>();
        }
        this.listaAutoSeleccionados.add(autoSelecc);
        System.out.println("autos seleccionados: " + this.listaAutoSeleccionados.size());
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public String getPalabraBuscar() {
        return palabraBuscar;
    }

    public void setPalabraBuscar(String palabraBuscar) {
        this.palabraBuscar = palabraBuscar;
    }

    public void buscarProFiltro() {
        if (idSucursalSelecc == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a dealer"));
            return;
        }
        this.representante = "";
        this.listaAutoSeleccionados.clear();
        this.listaAutoResultado = autoEJB.buscarAutoTransferencia(idSucursalSelecc, palabraBuscar, autoTransferidos);
        this.cantidadResultado = this.listaAutoResultado.size();
    }

    public void imprimir() {

        List<TransferRegoDealerDetalle> listaTransferRegoDetalle = new ArrayList<>();
        if (idSucursalSelecc == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a branch"));
            return;
        }

        if (this.listaAutoResultado.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "No results available"));
            return;
        }

        if (this.listaAutoSeleccionados.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not car selected"));
            return;
        }

        Sucursal sucursalSelecc = sucursalEJB.find(this.idSucursalSelecc);
        if (sucursalSelecc == null) {
            //esto no deberia pasar
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Branch selected is null"));
            return;
        }

        if ((sucursalSelecc.getLicence() != null && sucursalSelecc.getLicence().getId() == -1) || sucursalSelecc.getLicence() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The branch don't have a dealer licence defined"));
            return;
        }

        if (this.representante == null || this.representante.trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a name of representative"));
            return;
        }

        Dealer_licence dealer = sucursalSelecc.getLicence();

        for (int a = 0; a < this.listaAutoSeleccionados.size(); a++) {
            TransferRegoDealerDetalle transferRegoDetalle = new TransferRegoDealerDetalle();
            Auto autoSelecc = this.listaAutoSeleccionados.get(a);
            transferRegoDetalle.setRego(autoSelecc.getRego());
            if (autoSelecc.getFecha_compra() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                String fechaCompra = sdf.format(autoSelecc.getFecha_compra());
                transferRegoDetalle.setFechaCompra(fechaCompra);
            }
            transferRegoDetalle.setVIN(autoSelecc.getVIN());
            if (autoSelecc.getMarca() != null && autoSelecc.getMarca().getId() > 0) {
                transferRegoDetalle.setMake(autoSelecc.getMarca().getNombre());
            }
            if (autoSelecc.getKilometraje() != null && autoSelecc.getKilometraje().trim().length() > 0) {
                // transferRegoDetalle.setOdometer("211,147" + " Km");
                String kilometrajeOriginal = autoSelecc.getKilometraje();
                String resultado = "";
                int cantidadLetras = 0;
                for (int b = kilometrajeOriginal.length() - 1; b > -1; b--) {
                    resultado = kilometrajeOriginal.charAt(b) + resultado;
                    cantidadLetras++;
                    if (cantidadLetras % 3 == 0 && cantidadLetras != kilometrajeOriginal.length()) {
                        resultado = "," + resultado;
                    }
                }
                //System.out.println(resultado);
                transferRegoDetalle.setOdometer(resultado + " Km");
            }
            if (autoSelecc.getClienteProveedor() != null && autoSelecc.getClienteProveedor().getId() > -1) {
                String proveedorDetails = autoSelecc.getClienteProveedor().getApellido() + " " + autoSelecc.getClienteProveedor().getSuburbio();
                transferRegoDetalle.setVendedorInfo(proveedorDetails);
                if (autoSelecc.getClienteProveedor().getLicencia_numero() != null && autoSelecc.getClienteProveedor().getLicencia_numero().trim().length() > 0) {
                    transferRegoDetalle.setVendedorLicencia(autoSelecc.getClienteProveedor().getLicencia_numero());
                }
            }
            //transferRegoDetalle.setMarketValue("$ 10,597.00" );
            NumberFormat n = NumberFormat.getCurrencyInstance();
            String valorMoneda = n.format(autoSelecc.getValor_compra());
            //System.out.println(valorMoneda);
            transferRegoDetalle.setMarketValue(valorMoneda);
            listaTransferRegoDetalle.add(transferRegoDetalle);
            autoSelecc.setTransferenciaImpresa(true);
            autoEJB.edit(autoSelecc);
        }
        List<TransferRegoDealer> arraysDetails = new ArrayList<TransferRegoDealer>();
        TransferRegoDealer transferRegoDealer = new TransferRegoDealer();
        transferRegoDealer.setListaDetalles(listaTransferRegoDetalle);
        arraysDetails.add(transferRegoDealer);

        try {

            File sumary = new File("reportes\\registrationTransfer.jrxml");

            File sumary_taxes = new File("reportes\\registrationTransfer.jrxml");

            String reportPath_base = sumary.getAbsolutePath().replace("registrationTransfer.jrxml", "");
            String reportPath = sumary.getAbsolutePath();
            String reportPath_taxes = sumary_taxes.getAbsolutePath();

            JasperReport jasperMasterReport = JasperCompileManager.compileReport(reportPath);
            JasperReport jasperSubreportReport_taxes = JasperCompileManager.compileReport(reportPath_taxes);

            //Complete Params
            Map<String, Object> params = new HashMap<>();

            //SUBREPORT PARAMS
            params.put("subReportBeanList", jasperSubreportReport_taxes);
            params.put("SUBREPORT_DIR", reportPath_base);
            params.put("dealerName", dealer.getNombre());
            params.put("dealerLicence", dealer.getNumero());
            params.put("dealerPhone", sucursalSelecc.getTelefono());
            params.put("representante", this.representante);

            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(arraysDetails);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, params,
                    jrBeanCollectionDataSource);

            // Export the report to a PDF file
            File pdfExportar = new File(reportPath.replace("registrationTransfer.jrxml", "") + "registrationTransfer.pdf");
            if (pdfExportar.exists()) {
                pdfExportar.delete();
            }
            //         JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath.replace("sumary.jrxml", "") + "sumary.pdf");

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "inline; filename=jsfReporte.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
            stream.close();

            FacesContext.getCurrentInstance().responseComplete();

            System.out.println("Done");

            //return "Report successfully generated @path= " + reportPath.replace("sumary.jrxml", "");
        } catch (Exception e) {
            e.printStackTrace();
            //return e.getMessage();
        }

        this.buscarProFiltro();

        System.out.println("Listado de autos seleccion: " + this.listaAutoSeleccionados.size());
    }

    public List<Auto> getListaAutoResultado() {
        return listaAutoResultado;
    }

    public void setListaAutoResultado(List<Auto> listaAutoResultado) {
        this.listaAutoResultado = listaAutoResultado;
    }

    public int getIdSucursalSelecc() {
        return idSucursalSelecc;
    }

    public void setIdSucursalSelecc(int idSucursalSelecc) {
        this.idSucursalSelecc = idSucursalSelecc;
    }

    public boolean isAutoTransferidos() {
        return autoTransferidos;
    }

    public void setAutoTransferidos(boolean autoTransferidos) {
        this.autoTransferidos = autoTransferidos;
    }

    public List<Auto> getListaAutoSeleccionados() {
        return listaAutoSeleccionados;
    }

    public void setListaAutoSeleccionados(List<Auto> listaAutoSeleccionados) {
        this.listaAutoSeleccionados = listaAutoSeleccionados;
    }

    public int getCantidadResultado() {
        return cantidadResultado;
    }

    public void setCantidadResultado(int cantidadResultado) {
        this.cantidadResultado = cantidadResultado;
    }

    public String getTransferenciaImpresa() {
        return transferenciaImpresa;
    }

    public void setTransferenciaImpresa(String transferenciaImpresa) {
        this.transferenciaImpresa = transferenciaImpresa;
    }

    public String getTransferenciaImpresa(Auto auto) {
        if (auto.isTransferenciaImpresa()) {
            return "Printed";
        } else {
            return "NO Printed";
        }
    }

    public boolean isEsImpresa() {
        return esImpresa;
    }

    public boolean isEsImpresa(Auto auto) {
        if (auto != null && auto.isTransferenciaImpresa()) {
            return true;
        } else {
            return false;
        }
    }

    public void setEsImpresa(boolean esImpresa) {
        this.esImpresa = esImpresa;
    }

    public List<Dealer_licence> getListaDealerLicence() {
        return listaDealerLicence;
    }

    public void setListaDealerLicence(List<Dealer_licence> listaDealerLicence) {
        this.listaDealerLicence = listaDealerLicence;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public boolean isMostrarBotonImprimir() {
        if (idSucursalSelecc == -1) {
            return false;
        }

        if (this.listaAutoResultado.size() == 0) {
            return false;
        }

        if (this.listaAutoSeleccionados.size() == 0) {
            return false;
        }

        Sucursal sucursalSelecc = sucursalEJB.find(this.idSucursalSelecc);
        if (sucursalSelecc == null) {
            //esto no deberia pasar
            return false;
        }

        if ((sucursalSelecc.getLicence() != null && sucursalSelecc.getLicence().getId() == -1) || sucursalSelecc.getLicence() == null) {
            return false;
        }

        if (this.representante == null || this.representante.trim().length() == 0) {
            return false;
        }

        return true;
    }

    public void setMostrarBotonImprimir(boolean mostrarBotonImprimir) {
        this.mostrarBotonImprimir = mostrarBotonImprimir;
    }

    public String getFechaFormateada(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (fecha != null) {
            this.fechaFormateada = sdf.format(fecha);
        }
        return fechaFormateada;
    }

    public String getFechaRegoFormateada(Auto auto) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String resultado = "";
        if (auto.getFecha_vencimiento_rego()!=null) {
            resultado = sdf.format(auto.getFecha_vencimiento_rego());
        }
        return resultado;
    }
    
    public String getRegistracionAuto(Auto auto)
    {
        if(auto.getRego()!=null && auto.getRego().trim().length()>0)
        {
            return auto.getRego();
        }
        else
        {
            return "UNREG";
        }
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }

}
