/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Alquiler_ClienteFacadeLocal;
import com.piantino.ejb.Alquiler_ImpuestoFacadeLocal;
import com.piantino.ejb.ImpuestoFacadeLocal;
import com.piantino.ejb.Invoice_Mecanico_DetalleFacade;
import com.piantino.ejb.Invoice_Mecanico_DetalleFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.Tipo_pagoFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Alquiler_Impuesto;
import com.piantino.model.Cliente;
import com.piantino.model.Impuesto;
import com.piantino.model.Pago;
import com.piantino.model.Sucursal;
import com.piantino.model.Tipo_pago;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped

public class SummaryAlquilerPagos implements Serializable {

    private Alquiler alquilerBD;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    @EJB
    private PagoFacadeLocal pagoEJB;

    private List<Pago> listaPagosAprovados = new ArrayList<Pago>();

    private List<Pago> todosLosPagos;

    private Usuario usuarioLogueado;

    @EJB
    private Alquiler_ClienteFacadeLocal alquilerClienteEJB;

    private Cliente clientePrincipal;

    @EJB
    private Alquiler_ImpuestoFacadeLocal impuestoAlquilerEJB;

    @EJB
    private Tipo_pagoFacadeLocal tipoPagoEJB;

    @EJB
    private ImpuestoFacadeLocal impuestoEJB;

    List<Alquiler_Impuesto> listaAlquilerImpuestos;

    private int cantidadLateASumar = 0;

    private float totalGeneral = 0;

    private float pagoGeneral = 0;

    private float deudaGeneral = 0;

    private int cantidadPagosGeneral = 0;

    private boolean mostrarPago = false;

    private Pago pagoActual;

    private List<Sucursal> listaSucursales;

    private List<Tipo_pago> listaTipoPago;

    private String semanasCurrentPorRate = "";

    private float totalSemanaCurrentPorRate = 0;

    private float sumaTodosImpuestos = 0;

    private float deudaTotalCurrent = 07;

    private float deudaNetoCurrent = 0;

    private String cabecera = "";

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    @EJB
    private Alquiler_ImpuestoFacadeLocal alquiler_impuestoEJB;

    private Calendar c;

    @EJB
    private Alquiler_ImpuestoFacadeLocal alquilerImpuestoEJB;

    private float valorBond = 0;

    @EJB
    private Invoice_Mecanico_DetalleFacadeLocal invoiceMecanicoDetalleEJB;

    @PostConstruct
    private void init() {

        listaSucursales = sucursalEJB.findAll();
        listaTipoPago = tipoPagoEJB.findAll();
        this.pagoActual = new Pago();
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        this.pagoActual.setFecha_hora(c.getTime());
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        String idAlquilerStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idAlquilerSummary");
        alquilerBD = alquilerEJB.getAlquilerPorId(Integer.parseInt(idAlquilerStr));
        if (alquilerBD == null) {
            alquilerBD = new Alquiler();
        } else {
            this.actualizarPagosDeAlquiler();
            this.calcularDeudaCurrent();

        }
        clientePrincipal = new Cliente();
        if (alquilerBD != null) {
            Alquiler_Cliente alqCliente = alquilerClienteEJB.buscarClientePrincipalPorAlquiler(alquilerBD.getId());
            if (alqCliente != null && alqCliente.getCliente() != null) {
                clientePrincipal = alqCliente.getCliente();
            }

            List<Alquiler_Impuesto> listaImpuestosAlquiler = alquilerImpuestoEJB.buscarImpuestosAlquiler(this.alquilerBD.getId());
            for (int h = 0; h < listaImpuestosAlquiler.size(); h++) {
                Alquiler_Impuesto alquilerImpuesto = listaImpuestosAlquiler.get(h);
                if (alquilerImpuesto.getImpuesto().getTipo_asociado() == Impuesto.TIPO_ASOCIADO_BOND) {
                    valorBond = alquilerImpuesto.getSubtotal();
                }
            }
        }
    }

    public boolean esPagoAprovado(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().startsWith("Approved") && !pago.getStatus().equalsIgnoreCase(Pago.Texto_Aprobado_warning) && !pago.getStatus().equalsIgnoreCase(Pago.Texto_Aprobado_warning_balanceado)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esPagoWarning(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().equalsIgnoreCase(Pago.Texto_Aprobado_warning)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esPagoWarningBalanceado(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().equalsIgnoreCase(Pago.Texto_Aprobado_warning_balanceado)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esPagoDeclined(Pago pago) {
        if (pago != null && pago.getStatus() != null && pago.getStatus().startsWith("Declined")) {
            return true;
        } else {
            return false;
        }
    }

    private void calcularDeudaCurrent() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        Date hoy = c.getTime();
        int datyOfWeek = c.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(c.getTime());
        Date finalSemanaDate = c.getTime();
        if (this.alquilerBD.getFecha_primer_pago() == null) {
            this.alquilerBD.setFecha_primer_pago(this.alquilerBD.getFecha_inicio());
        }
        int dias = (int) ((hoy.getTime() - this.alquilerBD.getFecha_primer_pago().getTime()) / 86400000);
        int semanasCurrent = 0;
        if (dias > 0) {
            semanasCurrent = ((int) Math.floor(dias / 7)) + 1;//le sumo uno, porque la fecha de pago, ya paso una semana teoricamente
        }

        semanasCurrentPorRate = semanasCurrent + " X $" + this.alquilerBD.getRate_per_day() + " (From: " + sdf.format(this.alquilerBD.getFecha_primer_pago()) + ")";
        totalSemanaCurrentPorRate = semanasCurrent * this.alquilerBD.getRate_per_day();
        int totalSemanaCurrentPorRateInt = (int) (totalSemanaCurrentPorRate * 100);
        totalSemanaCurrentPorRate = ((float) totalSemanaCurrentPorRateInt) / 100;

        int sumaTodosImpuestosInt = (int) (sumaTodosImpuestos * 100);
        sumaTodosImpuestos = ((float) sumaTodosImpuestosInt) / 100;

        deudaTotalCurrent = totalSemanaCurrentPorRate + sumaTodosImpuestos;
        int deudaTotalCurrentInt = (int) (this.deudaTotalCurrent * 100);
        deudaTotalCurrent = ((float) deudaTotalCurrentInt) / 100;

        deudaNetoCurrent = deudaTotalCurrent - pagoGeneral;
        int deudaNetoCurrentInt = (int) (deudaNetoCurrent * 100);
        deudaNetoCurrent = ((float) deudaNetoCurrentInt) / 100;
        alquilerBD.setDeudaCurrent(deudaNetoCurrent);
        alquilerBD.setFecha_calculo_current(hoy);
        alquilerEJB.edit(alquilerBD);

    }

    public String getSemanasCurrentPorRate() {
        return semanasCurrentPorRate;
    }

    public void setSemanasCurrentPorRate(String semanasCurrentPorRate) {
        this.semanasCurrentPorRate = semanasCurrentPorRate;
    }

    public float getTotalSemanaCurrentPorRate() {
        return totalSemanaCurrentPorRate;
    }

    public void setTotalSemanaCurrentPorRate(float totalSemanaCurrentPorRate) {
        this.totalSemanaCurrentPorRate = totalSemanaCurrentPorRate;
    }

    public float getSumaTodosImpuestos() {
        return sumaTodosImpuestos;
    }

    public void setSumaTodosImpuestos(float sumaTodosImpuestos) {
        this.sumaTodosImpuestos = sumaTodosImpuestos;
    }

    public float getDeudaTotalCurrent() {
        return deudaTotalCurrent;
    }

    public void setDeudaTotalCurrent(float deudaTotalCurrent) {
        this.deudaTotalCurrent = deudaTotalCurrent;
    }

    public float getDeudaNetoCurrent() {
        return deudaNetoCurrent;
    }

    public void setDeudaNetoCurrent(float deudaNetoCurrent) {
        this.deudaNetoCurrent = deudaNetoCurrent;
    }

    private void actualizarPagosDeAlquiler() {
        if (this.alquilerBD != null) {
            pagoGeneral = 0;
            sumaTodosImpuestos = 0;

            cantidadPagosGeneral = 0;

            listaPagosAprovados = pagoEJB.buscarAprovadosPorAlquiler(alquilerBD.getId());

            totalGeneral = this.alquilerBD.getGran_total();

            listaAlquilerImpuestos = impuestoAlquilerEJB.buscarImpuestosAlquiler(alquilerBD.getId());
            for (int a = 0; a < listaAlquilerImpuestos.size(); a++) {
                Alquiler_Impuesto alqImp = listaAlquilerImpuestos.get(a);
                sumaTodosImpuestos = sumaTodosImpuestos + alqImp.getSubtotal();

            }

            for (int c = 0; c < listaPagosAprovados.size(); c++) {
                Pago pagoAprovado = this.listaPagosAprovados.get(c);
                pagoGeneral = pagoGeneral + pagoAprovado.getMonto();
                cantidadPagosGeneral = cantidadPagosGeneral + 1;
            }

            int sumaTodosImpuestosInt = (int) (sumaTodosImpuestos * 100);
            sumaTodosImpuestos = ((float) sumaTodosImpuestosInt) / 100;

            todosLosPagos = new ArrayList<Pago>();

            todosLosPagos.addAll(this.listaPagosAprovados);

            deudaGeneral = totalGeneral - pagoGeneral;

            int deudaGeneralInt = (int) (deudaGeneral * 100);
            deudaGeneral = ((float) deudaGeneralInt) / 100;

            int totalGeneralInt = (int) (totalGeneral * 100);
            totalGeneral = ((float) totalGeneralInt) / 100;

            int pagoGeneralInt = (int) (pagoGeneral * 100);
            pagoGeneral = ((float) pagoGeneralInt) / 100;

            this.alquilerBD.setDeuda(deudaGeneral);
            alquilerEJB.edit(this.alquilerBD);

        }
    }

    public float getTotalGeneral() {
        return totalGeneral;
    }

    public void setTotalGeneral(float totalGeneral) {
        this.totalGeneral = totalGeneral;
    }

    public float getPagoGeneral() {
        return pagoGeneral;
    }

    public void setPagoGeneral(float pagoGeneral) {
        this.pagoGeneral = pagoGeneral;
    }

    public float getDeudaGeneral() {
        return deudaGeneral;
    }

    public void setDeudaGeneral(float deudaGeneral) {
        this.deudaGeneral = deudaGeneral;
    }

    public int getCantidadPagosGeneral() {
        return cantidadPagosGeneral;
    }

    public void setCantidadPagosGeneral(int cantidadPagosGeneral) {
        this.cantidadPagosGeneral = cantidadPagosGeneral;
    }

    public Alquiler getAlquilerBD() {
        return alquilerBD;
    }

    public void setAlquilerBD(Alquiler alquilerBD) {
        this.alquilerBD = alquilerBD;
    }

    public List<Alquiler_Impuesto> getListaAlquilerImpuestos() {
        return listaAlquilerImpuestos;
    }

    public void setListaAlquilerImpuestos(List<Alquiler_Impuesto> listaAlquilerImpuestos) {
        this.listaAlquilerImpuestos = listaAlquilerImpuestos;
    }

    public List<Pago> getListaPagosAprovados() {
        return listaPagosAprovados;
    }

    public void setListaPagosAprovados(List<Pago> listaPagosAprovados) {
        this.listaPagosAprovados = listaPagosAprovados;
    }

    public List<Pago> getTodosLosPagos() {
        return todosLosPagos;
    }

    public void setTodosLosPagos(List<Pago> todosLosPagos) {
        this.todosLosPagos = todosLosPagos;
    }

    public boolean isMostrarPago() {
        return mostrarPago;
    }

    public void setMostrarPago(boolean mostrarPago) {
        this.mostrarPago = mostrarPago;
    }

    public void sumarLatePayments() {
        if (cantidadLateASumar > 0) {
            Impuesto impuestoLatePayment = impuestoEJB.getImpuestoParaLatePayment();
            if (impuestoLatePayment != null) {

                boolean encontrado = false;
                float totalExtras = 0;
                float sumarADeuda = 0;

                List<Alquiler_Impuesto> listaImpuestos = alquiler_impuestoEJB.buscarImpuestosAlquiler(this.alquilerBD.getId());

                for (int j = 0; j < listaImpuestos.size(); j++) {
                    Alquiler_Impuesto impuestoAlquiler = listaImpuestos.get(j);
                    if (impuestoAlquiler.getImpuesto().getTipo_asociado() > -1 && impuestoAlquiler.getImpuesto().getTipo_asociado() == Impuesto.TIPO_ASOCIADO_LATE_PAYMENT) {
                        encontrado = true;
                        impuestoAlquiler.setCantidad(impuestoAlquiler.getCantidad() + cantidadLateASumar);
                        float subtotal = impuestoAlquiler.getCantidad() * impuestoAlquiler.getPrecio();
                        int subtotalInt = (int) (subtotal * 100);
                        subtotal = ((float) subtotalInt) / 100;
                        impuestoAlquiler.setSubtotal(subtotal);
                        alquiler_impuestoEJB.edit(impuestoAlquiler);
                        sumarADeuda = impuestoAlquiler.getPrecio();
                    }
                    totalExtras = totalExtras + (((float) impuestoAlquiler.getCantidad()) * impuestoAlquiler.getPrecio());
                }
                if (!encontrado) {
                    Alquiler_Impuesto impuestoAcrear = new Alquiler_Impuesto();
                    impuestoAcrear.setAlquiler(this.alquilerBD);
                    impuestoAcrear.setImpuesto(impuestoLatePayment);
                    impuestoAcrear.setCantidad(cantidadLateASumar);
                    impuestoAcrear.setPrecio(impuestoLatePayment.getPrice());
                    float subtotal = impuestoAcrear.getCantidad() * impuestoAcrear.getPrecio();
                    int subtotalInt = (int) (subtotal * 100);
                    subtotal = ((float) subtotalInt) / 100;
                    impuestoAcrear.setSubtotal(subtotal);
                    impuestoAcrear.setSeleccionado(true);
                    alquiler_impuestoEJB.create(impuestoAcrear);
                    totalExtras = totalExtras + impuestoAcrear.getSubtotal();
                    sumarADeuda = impuestoAcrear.getPrecio();
                }
                int totalExtrasInt = (int) (totalExtras * 100);
                totalExtras = ((float) totalExtrasInt) / 100;
                this.alquilerBD.setExtra(totalExtras);

                float granTotal = this.alquilerBD.getTotal() + totalExtras;
                int granTotalInt = (int) (granTotal * 100);
                granTotal = ((float) granTotalInt) / 100;
                this.alquilerBD.setGran_total(granTotal);

                float deudaTotal = this.alquilerBD.getDeuda() + sumarADeuda;
                int deudaTotalInt = (int) (deudaTotal * 100);
                deudaTotal = ((float) deudaTotalInt) / 100;
                this.alquilerBD.setDeuda(deudaTotal);

                alquilerEJB.edit(alquilerBD);

            }
            cantidadLateASumar = 0;
            this.actualizarPagosDeAlquiler();
            this.calcularDeudaCurrent();
        }

    }

    public Pago getPagoActual() {
        return pagoActual;
    }

    public void setPagoActual(Pago pagoActual) {
        this.pagoActual = pagoActual;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Tipo_pago> getListaTipoPago() {
        return listaTipoPago;
    }

    public void setListaTipoPago(List<Tipo_pago> listaTipoPago) {
        this.listaTipoPago = listaTipoPago;
    }

    public void guardarPago() {
        if (this.pagoActual != null && this.alquilerBD != null && this.alquilerBD.getId() > -1 && this.usuarioLogueado != null) {
            this.pagoActual.setAlquiler(this.alquilerBD);
            this.pagoActual.setUsuario(usuarioLogueado);
            float montoGrande = 2000000;
            if (this.pagoActual.getMonto() == montoGrande) {
                this.pagoActual.setMonto(0);
            }

            float pago = this.pagoActual.getMonto();

            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));

            int pagoInt = (int) (pago * 100);
            pago = ((float) pagoInt) / 100;

            this.pagoActual.setMonto(pago);
            if (this.pagoActual.getMonto() != alquilerBD.getRate_per_day()) {
                if (alquilerBD.isFortnightly()) {
                    float montoQuincental = alquilerBD.getRate_per_day() * 2;
                    if (montoQuincental != this.pagoActual.getMonto()) {
                        this.pagoActual.setStatus(Pago.Texto_Aprobado_warning);
                    } else {
                        this.pagoActual.setStatus(Pago.Texto_Aprobado);
                    }
                } else {
                    this.pagoActual.setStatus(Pago.Texto_Aprobado_warning);
                }
            } else {
                this.pagoActual.setStatus(Pago.Texto_Aprobado);
            }
            if (pagoActual.getId() == -1) {
                pagoEJB.create(pagoActual);
            } else {
                pagoEJB.edit(pagoActual);
            }

            this.setMostrarPago(false);

            /*if (this.pagoActual.getTipoPago().getId() == Pago.ID_TIPO_LATE_PAYMENT) {
                Impuesto impuestoLatePayment = impuestoEJB.getImpuestoParaLatePayment();
                if (impuestoLatePayment != null) {

                    boolean encontrado = false;
                    float totalExtras = 0;
                    float sumarADeuda = 0;

                    List<Alquiler_Impuesto> listaImpuestos = alquiler_impuestoEJB.buscarImpuestosAlquiler(this.alquilerBD.getId());

                    for (int j = 0; j < listaImpuestos.size(); j++) {
                        Alquiler_Impuesto impuestoAlquiler = listaImpuestos.get(j);
                        if (impuestoAlquiler.getImpuesto().getTipo_asociado() > -1 && impuestoAlquiler.getImpuesto().getTipo_asociado() == Impuesto.TIPO_ASOCIADO_LATE_PAYMENT) {
                            encontrado = true;
                            impuestoAlquiler.setCantidad(impuestoAlquiler.getCantidad() + 1);
                            float subtotal = impuestoAlquiler.getCantidad() * impuestoAlquiler.getPrecio();
                            int subtotalInt = (int) (subtotal * 100);
                            subtotal = ((float) subtotalInt) / 100;
                            impuestoAlquiler.setSubtotal(subtotal);
                            alquiler_impuestoEJB.edit(impuestoAlquiler);
                            sumarADeuda = impuestoAlquiler.getPrecio();
                        }
                        totalExtras = totalExtras + (((float) impuestoAlquiler.getCantidad()) * impuestoAlquiler.getPrecio());
                    }
                    if (!encontrado) {
                        Alquiler_Impuesto impuestoAcrear = new Alquiler_Impuesto();
                        impuestoAcrear.setAlquiler(this.alquilerBD);
                        impuestoAcrear.setImpuesto(impuestoLatePayment);
                        impuestoAcrear.setCantidad(1);
                        impuestoAcrear.setPrecio(impuestoLatePayment.getPrice());
                        impuestoAcrear.setSubtotal(impuestoLatePayment.getPrice());
                        impuestoAcrear.setSeleccionado(true);
                        alquiler_impuestoEJB.create(impuestoAcrear);
                        totalExtras = totalExtras + impuestoAcrear.getSubtotal();
                        sumarADeuda = impuestoAcrear.getPrecio();
                    }
                    int totalExtrasInt = (int) (totalExtras * 100);
                    totalExtras = ((float) totalExtrasInt) / 100;
                    this.alquilerBD.setExtra(totalExtras);

                    float granTotal = this.alquilerBD.getTotal() + totalExtras;
                    int granTotalInt = (int) (granTotal * 100);
                    granTotal = ((float) granTotalInt) / 100;
                    this.alquilerBD.setGran_total(granTotal);

                    float deudaTotal = this.alquilerBD.getDeuda() + sumarADeuda;
                    int deudaTotalInt = (int) (deudaTotal * 100);
                    deudaTotal = ((float) deudaTotalInt) / 100;
                    this.alquilerBD.setDeuda(deudaTotal);

                    alquilerEJB.edit(alquilerBD);

                }
            }*/
            this.actualizarPagosDeAlquiler();
            this.calcularDeudaCurrent();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successful registration"));
            this.pagoActual = new Pago();
            this.pagoActual.setFecha_hora(c.getTime());

        }
    }

    public void cancelarPago() {
        this.pagoActual = new Pago();
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        this.pagoActual.setFecha_hora(c.getTime());
        this.setMostrarPago(false);
    }


    public void editarPago(long idPago) {

        this.pagoActual = pagoEJB.find(idPago);
        try {
            this.pagoActual = (Pago) this.pagoActual.clone();

            this.pagoActual.setTipoPago((Tipo_pago) pagoActual.getTipoPago().clone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mostrarPago = true;
    }

    public Cliente getClientePrincipal() {
        return clientePrincipal;
    }

    public void setClientePrincipal(Cliente clientePrincipal) {
        this.clientePrincipal = clientePrincipal;
    }

    public String getCabecera() {
        if (this.alquilerBD != null) {
            cabecera = "Booking number: " + this.alquilerBD.getId();
        }
        if (this.clientePrincipal != null) {
            cabecera = cabecera + "    Client name: " + this.clientePrincipal.getNombre() + " " + this.clientePrincipal.getApellido();
        }
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public void viewCarsCustomized() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 640);
        options.put("height", 340);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerSMS");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerSMS", this.alquilerBD.getId() + "");
        PrimeFaces.current().dialog().openDynamic("ViewSMS", options, null);
    }

    public void viewInternalNotes() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 750);
        options.put("height", 340);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerSMS");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerSMS", this.alquilerBD.getId() + "");
        PrimeFaces.current().dialog().openDynamic("ViewNotes", options, null);
    }

    public void verDetalleAlquiler() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerDato", this.alquilerBD.getId() + "");
    }

    public String getCurrencyFormat(float monto) {
        String resultado = "";
        NumberFormat n = NumberFormat.getCurrencyInstance();
        resultado = n.format(monto);
        return resultado;
    }

    public String getExpensesCliente() {
        String resultado = "";
        float expensesDelCliente = 0;
        if (this.clientePrincipal != null && this.clientePrincipal.getId() > -1) {
            expensesDelCliente = this.invoiceMecanicoDetalleEJB.getExpensesDelCliente(this.clientePrincipal.getId());
        }
        return this.getCurrencyFormat(expensesDelCliente);
    }

}
