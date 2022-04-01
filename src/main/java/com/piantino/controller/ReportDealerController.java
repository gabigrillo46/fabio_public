/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.Dealer_licenceFacadeLocal;
import com.piantino.ejb.Formulario5FacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.VentaFacadeLocal;
import com.piantino.model.Auto;
import com.piantino.model.Cliente;
import com.piantino.model.Cod_postal;

import com.piantino.model.Dealer_licence;
import com.piantino.model.Formulario5;
import com.piantino.model.IntervalosDealerReport;
import com.piantino.model.Sucursal;
import com.piantino.model.Venta;
import com.piantino.report.Dtos.Form1Detalle;
import com.piantino.report.Dtos.Form1SubreportArray;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;
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
public class ReportDealerController implements Serializable {

    private List<Dealer_licence> listaLicence = new ArrayList();

    private List<Sucursal> listaSucursales = new ArrayList();

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    @EJB
    private Dealer_licenceFacadeLocal dealerLicenceEJB;

    private List<IntervalosDealerReport> listaIntervalosGrilla = new ArrayList();

    @EJB
    private AutoFacadeLocal autoEJB;

    @EJB
    private Formulario5FacadeLocal formularioEJB;

    Calendar c;

    private int dealerLicencieSeleccionado = -1;

    private int sucursalSeleccionada = -1;

    private List<Auto> listaAutosResultado = new ArrayList();

    private List<IntervalosDealerReport> intervalosSeleccionados = new ArrayList();

    @EJB
    private VentaFacadeLocal ventaEJB;

    @PostConstruct
    private void init() {
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        listaLicence = dealerLicenceEJB.findAll();
        listaSucursales = sucursalEJB.findAll();
    }

    public List<Dealer_licence> getListaLicence() {
        return listaLicence;
    }

    public void setListaLicence(List<Dealer_licence> listaLicence) {
        this.listaLicence = listaLicence;
    }

    public List<Sucursal> getListaSucursales() {
        if (this.dealerLicencieSeleccionado > 0) {
            this.listaSucursales = this.sucursalEJB.buscarSucursalesPorLicencia(this.dealerLicencieSeleccionado);
        } else {
            this.listaSucursales = this.sucursalEJB.findAll();
        }
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<IntervalosDealerReport> getListaIntervalosGrilla() {
        return listaIntervalosGrilla;
    }

    public void setListaIntervalosGrilla(List<IntervalosDealerReport> listaIntervalosGrilla) {
        this.listaIntervalosGrilla = listaIntervalosGrilla;
    }

    public void seleccionoUnDealerLicence() {

    }

    public void imprimir() {
        System.out.println("Cantidad de intervalos seleccionados: " + this.intervalosSeleccionados.size());
        List<Form1Detalle> listaDetalleF1 = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfMesAnio = new SimpleDateFormat("MM/yy");
        for (int a = 0; a < this.intervalosSeleccionados.size(); a++) {
            IntervalosDealerReport intervalo = this.intervalosSeleccionados.get(a);
            Vector autos = intervalo.getAutosAgregados();
            for (int b = 0; b < autos.size(); b++) {
                Auto autoAgregar = (Auto) autos.get(b);
                Form1Detalle form1Detalle = new Form1Detalle();
                form1Detalle.setEntryNumber(autoAgregar.getStock() + "");
                String fechaCompra = "";
                if (autoAgregar.getFecha_compra() != null) {
                    fechaCompra = sdf.format(autoAgregar.getFecha_compra());
                }
                form1Detalle.setFechaCompra(fechaCompra);
                String datosProveedor = "";
                if (autoAgregar.getClienteProveedor() != null) {
                    datosProveedor = autoAgregar.getClienteProveedor().getNombre() + " " + autoAgregar.getClienteProveedor().getDireccion();
                    datosProveedor = datosProveedor + " " + autoAgregar.getClienteProveedor().getSuburbio() + ", ";
                    if (autoAgregar.getClienteProveedor().getState() != null) {
                        datosProveedor = datosProveedor + autoAgregar.getClienteProveedor().getState().getNombre() + " ";
                    }
                    datosProveedor = datosProveedor + autoAgregar.getClienteProveedor().getCodigo_postal() + ", " + autoAgregar.getClienteProveedor().getPais();
                    if (autoAgregar.getClienteProveedor().getDealer_licence() != null && autoAgregar.getClienteProveedor().getDealer_licence().trim().length() > 0) {
                        datosProveedor = datosProveedor + ", MDL:" + autoAgregar.getClienteProveedor().getDealer_licence();
                    }
                }
                form1Detalle.setDatosProveedor(datosProveedor);
                String adquisicion = "";
                if (autoAgregar.getTipoCompra() != null && autoAgregar.getTipoCompra().getNombre().trim().length() > 0) {
                    adquisicion = autoAgregar.getTipoCompra().getNombre().substring(0, 1);
                }
                form1Detalle.setTipoCompra(adquisicion);
                String marca = "";
                if (autoAgregar.getMarca() != null) {
                    marca = autoAgregar.getMarca().getNombre();
                }
                if (autoAgregar.getTipo_body() != null && autoAgregar.getTipo_body().getNombre().trim().length() > 0) {
                    marca = marca + " " + autoAgregar.getTipo_body().getNombre();
                }
                form1Detalle.setMarcaTipoBody(marca);
                String modelo = "";
                if (autoAgregar.getModelo() != null && autoAgregar.getModelo().getNombre().trim().length() > 0) {
                    modelo = autoAgregar.getModelo().getNombre();
                }
                if (autoAgregar.getBuildDate() != null) {
                    modelo = modelo + " " + sdfMesAnio.format(autoAgregar.getBuildDate());
                }
                form1Detalle.setModelo(modelo);
                String rego = "";
                if (autoAgregar.getRego() != null && autoAgregar.getRego().trim().length() > 0) {
                    rego = autoAgregar.getRego();
                }
                form1Detalle.setRego(rego);
                String motor = "";
                if (autoAgregar.getNro_motor() != null && autoAgregar.getNro_motor().trim().length() > 0) {
                    motor = autoAgregar.getNro_motor();
                }
                form1Detalle.setMotor(motor);
                String vin = "";
                if (autoAgregar.getVIN() != null && autoAgregar.getVIN().trim().length() > 0) {
                    vin = autoAgregar.getVIN();
                }
                form1Detalle.setVin(vin);
                String odometer = "";
                if (autoAgregar.getKilometraje() != null && autoAgregar.getKilometraje().trim().length() > 0) {
                    odometer = autoAgregar.getKilometraje();
                    if (odometer.trim().length() > 3) {
                        odometer = odometer.substring(0, 3) + "," + odometer.substring(3, odometer.trim().length());
                    }
                    odometer = odometer + " Km";
                }
                form1Detalle.setOdometer(odometer);
                String fechaVenta = "";
                Venta ventaAuto = ventaEJB.getVentaPorAuto(autoAgregar.getId());
                if (ventaAuto != null && ventaAuto.getFecha() != null) {
                    fechaVenta = sdf.format(ventaAuto.getFecha());
                    form1Detalle.setEstadoAuto("Sold");//aca tenemos que hacer un desarrollo
                }
                form1Detalle.setFechaVenta(fechaVenta);

                String form5 = "";
                if (ventaAuto != null && ventaAuto.getId() > -1) {
                    Formulario5 formularioVenta = ventaAuto.getFormulario();
                    if (formularioVenta != null) {
                        form5 = " Form5 " + formularioVenta.getId();
                    }
                }
                form1Detalle.setNumeroForm5(form5);

                String precioVenta = "";
                if (ventaAuto != null && ventaAuto.getValor() > 0) {
                    precioVenta = "$" + ventaAuto.getValor();
                }
                form1Detalle.setPrecioVenta(precioVenta);
                String odometerVenta = "";
                if (ventaAuto != null && ventaAuto.getCar_odometer() != null
                        && ventaAuto.getCar_odometer().trim().length() > 0) {
                    odometerVenta = ventaAuto.getCar_odometer();
                    if (odometerVenta.trim().length() > 3) {
                        odometerVenta = odometerVenta.substring(0, 3) + ", " + odometerVenta.substring(3, odometerVenta.trim().length());
                    }
                }
                form1Detalle.setOdometerVenta(odometerVenta);
                String datosComprador = "";
                if (ventaAuto != null && ventaAuto.getClienteVenta() != null
                        && ventaAuto.getClienteVenta().getId() > -1) {
                    Cliente comprador = ventaAuto.getClienteVenta();
                    datosComprador = comprador.getNombre() + " " + comprador.getApellido() + " " + comprador.getDireccion() + ", " + comprador.getSuburbio() + ", ";
                    if (comprador.getState() != null && comprador.getState().getId() > -1) {
                        datosComprador = datosComprador + comprador.getState().getNombre() + " ";
                    }
                    datosComprador = datosComprador + comprador.getCodigo_postal() + ", " + comprador.getPais();
                }
                form1Detalle.setDatosComprador(datosComprador);
                listaDetalleF1.add(form1Detalle);
            }
        }
        List<Form1SubreportArray> arraysDetails = new ArrayList<Form1SubreportArray>();
        Form1SubreportArray form1Subreport = new Form1SubreportArray();
        form1Subreport.setListaDetalles(listaDetalleF1);
        arraysDetails.add(form1Subreport);

        try {

            File sumary = new File("reportes\\Form1.jrxml");

            File sumary_taxes = new File("reportes\\Form1_detalle.jrxml");

            String reportPath_base = sumary.getAbsolutePath().replace("Form1.jrxml", "");
            String reportPath = sumary.getAbsolutePath();
            String reportPath_taxes = sumary_taxes.getAbsolutePath();

            JasperReport jasperMasterReport = JasperCompileManager.compileReport(reportPath);
            JasperReport jasperSubreportReport_taxes = JasperCompileManager.compileReport(reportPath_taxes);

            //Complete Params
            Map<String, Object> params = new HashMap<>();

            //SUBREPORT PARAMS
            params.put("subReportBeanList", jasperSubreportReport_taxes);
            params.put("SUBREPORT_DIR", reportPath_base);
            Dealer_licence dealerLicence = this.dealerLicenceEJB.find(dealerLicencieSeleccionado);
            String nombreDealer = "";
            String licenciaDealer = "";
            if (dealerLicence != null) {
                nombreDealer = dealerLicence.getNombre();
                licenciaDealer = dealerLicence.getNumero();
            }
            params.put("licenceName", nombreDealer);
            params.put("licenceNumber", licenciaDealer);
            params.put("cantidadRegistros", Integer.valueOf(listaDetalleF1.size()));

            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(arraysDetails);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, params,
                    jrBeanCollectionDataSource);

            // Export the report to a PDF file
            File pdfExportar = new File(reportPath.replace("Form1.jrxml", "") + "Form1.pdf");
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

    }

    public void buscarPorFiltro() {
        Integer idSucursal = null;
        Integer idDealer = null;
        if (this.dealerLicencieSeleccionado == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a dealer licence"));
            return;
        }
        idDealer = this.dealerLicencieSeleccionado;
        if (this.sucursalSeleccionada > -1) {
            idSucursal = this.sucursalSeleccionada;
        }
        this.listaIntervalosGrilla.clear();
        this.listaAutosResultado = autoEJB.buscarPorSucursalDealer(idSucursal, idDealer);
        System.out.println(this.listaAutosResultado.size());
        int agregados = 0;
        int numeroIntervalo = 1;
        IntervalosDealerReport intervalo = null;
        for (int p = 0; p < this.listaAutosResultado.size(); p++) {
            Auto autoAgregar = this.listaAutosResultado.get(p);
            if (agregados == 0) {
                intervalo = new IntervalosDealerReport();
                intervalo.setNumero(numeroIntervalo);
                numeroIntervalo++;
                intervalo.setMinimo(autoAgregar.getStock());
                intervalo.setMaximo(autoAgregar.getStock());
                String rango = intervalo.getMinimo() + " - " + intervalo.getMaximo();
                intervalo.setRango(rango);
                intervalo.getAutosAgregados().add(autoAgregar);
                agregados++;
            } else if (agregados == 7) {
                intervalo.setMaximo(autoAgregar.getStock());
                intervalo.getAutosAgregados().add(autoAgregar);
                String rango = intervalo.getMinimo() + " - " + intervalo.getMaximo();
                intervalo.setRango(rango);
                this.listaIntervalosGrilla.add(intervalo);
                agregados = 0;
                intervalo = null;
            } else  {
                intervalo.setMaximo(autoAgregar.getStock());
                String rango = intervalo.getMinimo() + " - " + intervalo.getMaximo();
                intervalo.setRango(rango);
                intervalo.getAutosAgregados().add(autoAgregar); 
                agregados++;
            }
        }
        if(intervalo != null)
        {
             this.listaIntervalosGrilla.add(intervalo);
        }
        System.out.println("Cantidad de intervalos: " + this.listaIntervalosGrilla.size());
    }

    public int getDealerLicencieSeleccionado() {
        return dealerLicencieSeleccionado;
    }

    public void setDealerLicencieSeleccionado(int dealerLicencieSeleccionado) {
        this.dealerLicencieSeleccionado = dealerLicencieSeleccionado;
    }

    public int getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    public void setSucursalSeleccionada(int sucursalSeleccionada) {
        this.sucursalSeleccionada = sucursalSeleccionada;
    }

    public List<IntervalosDealerReport> getIntervalosSeleccionados() {
        return intervalosSeleccionados;
    }

    public void setIntervalosSeleccionados(List<IntervalosDealerReport> intervalosSeleccionados) {
        this.intervalosSeleccionados = intervalosSeleccionados;
    }

}
