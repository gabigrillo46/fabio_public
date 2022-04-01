/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.Dealer_licenceFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.VentaFacadeLocal;
import com.piantino.model.Auto;
import com.piantino.model.Cliente;
import com.piantino.model.Dealer_licence;
import com.piantino.model.Sucursal;
import com.piantino.model.Venta;
import com.piantino.report.Dtos.NoticeDisposal;
import com.piantino.report.Dtos.NoticeDisposalDetalle;
import com.piantino.report.Dtos.TransferRegoDealer;
import com.piantino.report.Dtos.TransferRegoDealerDetalle;
import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class ReportDisposalController implements Serializable {

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

    private String disposalImpreso = "";

    private boolean esImpresa = false;

    private List<Dealer_licence> listaDealerLicence = new ArrayList<>();

    @EJB
    private Dealer_licenceFacadeLocal dealerLicenceEJB;

    private String representante = "";

    private boolean mostrarBotonImprimir = false;

    @EJB
    private VentaFacadeLocal ventaEJB;

    @PostConstruct
    public void init() {
        listaSucursales = sucursalEJB.findAll();
    }

    public String getPalabraBuscar() {
        return palabraBuscar;
    }

    public void setPalabraBuscar(String palabraBuscar) {
        this.palabraBuscar = palabraBuscar;
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

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Auto> getListaAutoResultado() {
        return listaAutoResultado;
    }

    public void setListaAutoResultado(List<Auto> listaAutoResultado) {
        this.listaAutoResultado = listaAutoResultado;
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

    public String getDisposalImpreso() {
        return disposalImpreso;
    }

    public String getDisposalImpreso(Auto auto) {
        if (auto.isDisposalImpresa()) {
            return "Printed";
        } else {
            return "NO Printed";
        }
    }

    public void setDisposalImpreso(String transferenciaImpresa) {
        this.disposalImpreso = transferenciaImpresa;
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

    public void seleccionoUnRegistro(Auto autoSelecc) {
        if (this.listaAutoSeleccionados == null) {
            this.listaAutoSeleccionados = new ArrayList<>();
        }
        this.listaAutoSeleccionados.add(autoSelecc);
        System.out.println("autos seleccionados: " + this.listaAutoSeleccionados.size());
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

    public void buscarProFiltro() {
        if (idSucursalSelecc == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a dealer"));
            return;
        }
        this.listaAutoResultado = autoEJB.buscarAutoDisposal(idSucursalSelecc, palabraBuscar, autoTransferidos);
        this.listaAutoSeleccionados = new ArrayList<>();
        this.cantidadResultado = this.listaAutoResultado.size();
    }

    public void imprimir() {

        List<NoticeDisposalDetalle> listaNoticeDisposalDetalle = new ArrayList<>();
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        for (int a = 0; a < this.listaAutoSeleccionados.size(); a++) {
            NoticeDisposalDetalle noticeDetalle = new NoticeDisposalDetalle();
            Auto autoSelecc = this.listaAutoSeleccionados.get(a);
            noticeDetalle.setRego(autoSelecc.getRego());
            Venta ventaAuto = ventaEJB.getVentaPorAuto(autoSelecc.getId());
            if (ventaAuto != null) {
                NumberFormat n = NumberFormat.getCurrencyInstance();
                String valorMoneda = n.format(ventaAuto.getValor());
                noticeDetalle.setPrecioVenta(valorMoneda);
                if (ventaAuto.getFecha() != null) {
                    String fechaVentaFormat = simpleDateFormat.format(ventaAuto.getFecha());
                    noticeDetalle.setFechaVenta(fechaVentaFormat);
                }
                String fullnameComprador = ventaAuto.getCliente_nombre() + " " + ventaAuto.getCliente_apelllido();
                noticeDetalle.setCompradorFullName(fullnameComprador);
                String direccion = ventaAuto.getCliente_direccion() + " " + ventaAuto.getCliente_suburbio() + " " + ventaAuto.getCliente_state() + " " + ventaAuto.getCliente_codigo_postal();
                noticeDetalle.setCompradorDireccion(direccion);
                if (ventaAuto.getCliente_tipo() == Cliente.TIPO_CUSTOMER) {
                    noticeDetalle.setLicencia(ventaAuto.getCliente_licence());
                } else {
                    noticeDetalle.setLicencia(ventaAuto.getCliente_dealer_licence());
                }
            }
            noticeDetalle.setVin(autoSelecc.getVIN());

            String kilometrajeOriginal = ventaAuto.getCar_odometer();
            String resultado = "";
            int cantidadLetras = 0;
            for (int b = kilometrajeOriginal.length() - 1; b > -1; b--) {
                resultado = kilometrajeOriginal.charAt(b) + resultado;
                cantidadLetras++;
                if (cantidadLetras % 3 == 0 && cantidadLetras != kilometrajeOriginal.length()) {
                    resultado = "," + resultado;
                }
            }
            noticeDetalle.setOdometer(resultado + " Km");

            listaNoticeDisposalDetalle.add(noticeDetalle);
            autoSelecc.setDisposalImpresa(true);
            autoEJB.edit(autoSelecc);
        }
        List<NoticeDisposal> arraysDetails = new ArrayList<NoticeDisposal>();
        NoticeDisposal noticeDisposal = new NoticeDisposal();
        noticeDisposal.setListaDetalles(listaNoticeDisposalDetalle);
        arraysDetails.add(noticeDisposal);

        try {

            File sumary = new File("reportes\\Notice_disposal.jrxml");

            File sumary_taxes = new File("reportes\\Notice_disposal.jrxml");

            String reportPath_base = sumary.getAbsolutePath().replace("Notice_disposal.jrxml", "");
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
            String dealerDireccion = "";
            dealerDireccion = sucursalSelecc.getDireccion();
            if (sucursalSelecc.getCod_postal() != null) {
                dealerDireccion = dealerDireccion + " " + sucursalSelecc.getCod_postal().getSuburbio() + ", " + sucursalSelecc.getCod_postal().getEstado() + " " + sucursalSelecc.getCod_postal().getCodigo_postal();
            }
            params.put("dealerDireccion", dealerDireccion);
            params.put("representante", this.representante);

            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(arraysDetails);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, params,
                    jrBeanCollectionDataSource);

            // Export the report to a PDF file
            File pdfExportar = new File(reportPath.replace("Notice_disposal.jrxml", "") + "Notice_disposal.pdf");
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

    public boolean isEsImpresa() {
        return esImpresa;
    }

    public boolean isEsImpresa(Auto auto) {
        if (auto != null && auto.isDisposalImpresa()) {
            return true;
        } else {
            return false;
        }
    }

    public void setEsImpresa(boolean esImpresa) {
        this.esImpresa = esImpresa;
    }

}
