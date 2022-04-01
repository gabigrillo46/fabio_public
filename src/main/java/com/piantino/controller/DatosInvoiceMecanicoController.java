/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.ejb.Invoice_MecanicoFacadeLocal;
import com.piantino.ejb.Invoice_Mecanico_DetalleFacadeLocal;
import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.model.Auto;
import com.piantino.model.Auto_precios;
import com.piantino.model.Cliente;
import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Invoice_Mecanico_Detalle;
import com.piantino.model.Marca;
import com.piantino.model.Modelo;
import java.io.Serializable;
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
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class DatosInvoiceMecanicoController implements Serializable {

    private int sentido = 0;

    private HashMap listaProveedoresSimilar = new HashMap();

    private Invoice_Mecanico invoiceActual = new Invoice_Mecanico();

    private Invoice_Mecanico_Detalle invoiceActualDetalle = new Invoice_Mecanico_Detalle();

    private Cliente proveedorActual = null;

    @EJB
    private ClienteFacadeLocal clienteEJB;

    @EJB
    private AutoFacadeLocal autoEJB;

    private Auto autoActual = null;

    private HashMap listaVINSimilar = new HashMap();

    private HashMap listaRegoSimilar = new HashMap();

    @EJB
    private MarcaFacadeLocal marcaEJB;

    @EJB
    private ModeloFacadeLocal modeloEJB;

    private List<Marca> listaMarca = new ArrayList<>();

    private List<Modelo> listaModelos = new ArrayList<>();

    private List<Invoice_Mecanico_Detalle> listaInvoiceDetalle = new ArrayList();

    private String accion = "";

    @EJB
    private Invoice_MecanicoFacadeLocal invoiceMecanicoEJB;

    @EJB
    private Invoice_Mecanico_DetalleFacadeLocal invoiceMecanicoDetalleEJB;

    @PostConstruct
    private void init() {
        System.out.print("Llego");
        listaMarca = marcaEJB.findAll();
        this.sentido = Invoice_Mecanico.SENTIDO_INGRESO;
        this.invoiceActualDetalle.setTipo(Invoice_Mecanico_Detalle.TIPO_INCOME);

        String idInvoiceMecanico = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idInvoiceMecanico");

        if (idInvoiceMecanico != null && idInvoiceMecanico.trim().length() > 0) {
            try {
                long idInvoice = Long.parseLong(idInvoiceMecanico);
                this.invoiceActual = this.invoiceMecanicoEJB.find(idInvoice);
                this.invoiceActual = this.getInvoiceClone(this.invoiceActual);

                if (this.invoiceActual != null && this.invoiceActual.getId() > -1) {
                    this.listaInvoiceDetalle = this.invoiceMecanicoDetalleEJB.listaDetallePorIdInvoice(idInvoice);
                    this.sentido = this.invoiceActual.getSentido();
                    if (this.sentido == Invoice_Mecanico.SENTIDO_INGRESO) {
                        this.invoiceActualDetalle.setTipo(Invoice_Mecanico_Detalle.TIPO_INCOME);
                    } else if (this.sentido == Invoice_Mecanico.SENTIDO_GASTO) {
                        if (this.invoiceActual.getProveedor() != null) {
                            this.proveedorActual = this.invoiceActual.getProveedor();
                        }
                        this.invoiceActualDetalle.setTipo(-1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //estan modificando una invoice
            this.setAccion("M");
        } else {
            //estan registrando un invoice
            this.setAccion("R");
        }
    }

    public Invoice_Mecanico getInvoiceClone(Invoice_Mecanico invoice) {
        Invoice_Mecanico invoiceMecanico = new Invoice_Mecanico();
        try {
            invoiceMecanico = (Invoice_Mecanico) invoice.clone();
            if (invoice.getProveedor() != null) {
                invoiceMecanico.setProveedor((Cliente) invoice.getProveedor().clone());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceMecanico;
    }

    public int getSentido() {
        return sentido;
    }

    public void setSentido(int sentido) {
        this.sentido = sentido;
    }

    public Invoice_Mecanico getInvoiceActual() {
        return invoiceActual;
    }

    public void setInvoiceActual(Invoice_Mecanico invoiceActual) {
        this.invoiceActual = invoiceActual;
    }

    public Cliente getProveedorActual() {
        return proveedorActual;
    }

    public void setProveedorActual(Cliente proveedorActual) {
        this.proveedorActual = proveedorActual;
    }

    public boolean esIngreso() {
        if (this.sentido == Invoice_Mecanico.SENTIDO_INGRESO) {
            return true;
        } else {
            return false;
        }
    }

    public boolean esGasto() {
        if (this.sentido == Invoice_Mecanico.SENTIDO_GASTO) {
            return true;
        } else {
            return false;
        }
    }

    public void eligioTipo() {
        if (this.sentido == Invoice_Mecanico.SENTIDO_GASTO) {
            this.proveedorActual = new Cliente();
            this.invoiceActualDetalle.setTipo(Invoice_Mecanico_Detalle.TIPO_STOCK);
        } else {
            this.proveedorActual = null;
            this.invoiceActualDetalle.setTipo(Invoice_Mecanico_Detalle.TIPO_INCOME);
        }
    }

    public void seleccionoTipoDetalle() {
        if (this.invoiceActualDetalle != null && this.invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_OUR_CAR) {
            this.autoActual = new Auto();
        } else {
            this.autoActual = null;
        }
    }

    public void onProveedorSelect(SelectEvent event) {
        Object obj = listaProveedoresSimilar.get(event.getObject());
        System.out.println("on proveedor selected");
        if (obj != null) {
            System.out.println("seteamos el proveedor: " + ((Cliente) obj).getNombre());
            this.proveedorActual = (Cliente) obj;
        }
    }

    public List<String> completeTextProveedor(String nombre) {
        List<String> results = new ArrayList<>();
        this.proveedorActual = new Cliente();
        List<Cliente> listaProveedores = new ArrayList<Cliente>();
        try {
            listaProveedores = clienteEJB.buscarProveedoresPorNombreEmpezando(nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("cantidad de proveedores encontrados: " + listaProveedores.size());
        for (int i = 0; i < listaProveedores.size(); i++) {
            String stringHash = listaProveedores.get(i).getApellido();
            listaProveedoresSimilar.put(stringHash, listaProveedores.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public void handleKeyEvent() {
        System.out.println("handle key event");
        String apellido = "";
        if (this.proveedorActual.getNombre().trim().length() > 0 && this.proveedorActual.getId() == -1) {
            apellido = this.proveedorActual.getApellido().trim();
            Cliente proveedor = clienteEJB.buscarProveedorPorNombre(apellido);

            if (proveedor != null) {
                this.proveedorActual = proveedor;
            }
        }
    }

    public void nuevoProveedor() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 1200);
        options.put("height", 800);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        PrimeFaces.current().dialog().openDynamic("DatosProveedor", options, null);
    }

    public Invoice_Mecanico_Detalle getInvoiceActualDetalle() {
        return invoiceActualDetalle;
    }

    public void setInvoiceActualDetalle(Invoice_Mecanico_Detalle invoiceActualDetalle) {
        this.invoiceActualDetalle = invoiceActualDetalle;
    }

    public boolean esAutoNuestro() {
        if (invoiceActualDetalle != null && invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_OUR_CAR) {
            return true;
        }
        return false;
    }

    public boolean esAutoExterno() {
        if (invoiceActualDetalle != null && invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_EXTERNAL_CAR) {
            return true;
        }
        return false;
    }

    public boolean esAuto() {
        if (invoiceActualDetalle != null && (invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_EXTERNAL_CAR || invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_OUR_CAR || invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_INCOME)) {
            return true;
        }
        return false;

    }

    public List<String> completeTextRego(String query) {
        List<String> results = new ArrayList<>();
        if (this.sentido == Invoice_Mecanico.SENTIDO_INGRESO || this.invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_EXTERNAL_CAR) {
            return results;
        }
        this.autoActual = new Auto();
        List<Auto> listaAuto = new ArrayList<Auto>();
        try {
            listaAuto = autoEJB.buscarPorRegoSimilar(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listaAuto.size(); i++) {
            String stringHash = listaAuto.get(i).getRego();
            listaRegoSimilar.put(stringHash, listaAuto.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public List<String> completeTextVIN(String query) {
        List<String> results = new ArrayList<>();
        Auto autoVIN = autoEJB.buscarPorVin(query);
        if (autoVIN != null) {
            this.autoActual = autoVIN;
            return results;
        }

        String reverse = "";
        for (int i = 0; i < query.trim().length(); i++) {
            reverse = query.charAt(i) + reverse;
        }
        this.autoActual = new Auto();
        List<Auto> listaAuto = new ArrayList<Auto>();
        try {
            listaAuto = autoEJB.buscarPorVINTerminando(reverse);
            listaAuto.addAll(autoEJB.buscarPorVINEmpezando(query));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listaAuto.size(); i++) {
            String stringHash = listaAuto.get(i).getVIN();
            listaVINSimilar.put(stringHash, listaAuto.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public void onAutoSelectVIN(SelectEvent event) {
        Object obj = listaVINSimilar.get(event.getObject());

        if (obj != null) {
            this.autoActual = (Auto) obj;
            this.invoiceActualDetalle.setRego(this.autoActual.getRego());
            this.invoiceActualDetalle.setYear(this.autoActual.getAño());
            this.invoiceActualDetalle.setColor(this.autoActual.getColor());
            this.invoiceActualDetalle.setMarca(this.autoActual.getMarca());
            this.invoiceActualDetalle.setModelo(this.autoActual.getModelo());
        }
    }

    public void onAutoSelectRego(SelectEvent event) {
        Object obj = listaRegoSimilar.get(event.getObject());

        if (obj != null) {
            this.autoActual = (Auto) obj;
            this.invoiceActualDetalle.setVin(this.autoActual.getVIN());
            this.invoiceActualDetalle.setYear(this.autoActual.getAño());
            this.invoiceActualDetalle.setColor(this.autoActual.getColor());
            this.invoiceActualDetalle.setMarca(this.autoActual.getMarca());
            this.invoiceActualDetalle.setModelo(this.autoActual.getModelo());
        }
    }

    public void handleKeyEventVIN() {
        String vin = "";
        if (this.autoActual.getVIN().trim().length() > 0) {
            vin = this.autoActual.getVIN().trim();
            vin = vin.toUpperCase();
            Auto autoVIN = autoEJB.buscarPorVin(vin);
            if (autoVIN != null) {
                this.autoActual = autoVIN;
            }
        }
    }

    public List<Marca> getListaMarca() {
        return listaMarca;
    }

    public void setListaMarca(List<Marca> listaMarca) {
        this.listaMarca = listaMarca;
    }

    public List<Modelo> getListaModelos() {
        if (this.invoiceActualDetalle != null && this.invoiceActualDetalle.getMarca() != null && this.invoiceActualDetalle.getMarca().getId() != -1) {
            listaModelos = modeloEJB.buscarPorMarca(this.invoiceActualDetalle.getMarca().getId());
        } else {
            listaModelos = modeloEJB.findAll();
        }
        return listaModelos;
    }

    public void setListaModelos(List<Modelo> listaModelos) {
        this.listaModelos = listaModelos;
    }

    public void seleccionoIncGST() {
        double gst = 0;
        double subtotalSinGST = 0;
        double subtotal = 0;
        if (this.invoiceActualDetalle.isInc_gst()) {
            if (this.invoiceActualDetalle.getSubtotal_sin_gst() == 0 && this.invoiceActualDetalle.getSubtotal() > 0) {
                gst = (this.invoiceActualDetalle.getSubtotal() * 10) / 110;
                subtotalSinGST = this.invoiceActualDetalle.getSubtotal() - gst;
                subtotal = this.invoiceActualDetalle.getSubtotal();
            } else if (this.invoiceActualDetalle.getSubtotal_sin_gst() > 0 && this.invoiceActualDetalle.getSubtotal() == 0) {
                gst = (this.invoiceActualDetalle.getSubtotal_sin_gst() * 10) / 100;
                subtotalSinGST = this.invoiceActualDetalle.getSubtotal_sin_gst();
                subtotal = subtotalSinGST + gst;
            } else if (this.invoiceActualDetalle.getSubtotal() > 0) {
                gst = (this.invoiceActualDetalle.getSubtotal() * 10) / 110;
                subtotalSinGST = this.invoiceActualDetalle.getSubtotal() - gst;
                subtotal = this.invoiceActualDetalle.getSubtotal();
            } else {
                gst = 0;
                subtotal = 0;
                subtotalSinGST = 0;
            }
        } else {
            if (this.invoiceActualDetalle.getSubtotal_sin_gst() == 0 && this.invoiceActualDetalle.getSubtotal() > 0) {
                gst = 0;
                subtotalSinGST = this.invoiceActualDetalle.getSubtotal();
                subtotal = this.invoiceActualDetalle.getSubtotal();
            } else if (this.invoiceActualDetalle.getSubtotal_sin_gst() > 0 && this.invoiceActualDetalle.getSubtotal() == 0) {
                gst = 0;
                subtotalSinGST = this.invoiceActualDetalle.getSubtotal_sin_gst();
                subtotal = this.invoiceActualDetalle.getSubtotal_sin_gst();
            } else if (this.invoiceActualDetalle.getSubtotal() > 0) {
                gst = 0;
                subtotalSinGST = this.invoiceActualDetalle.getSubtotal();
                subtotal = this.invoiceActualDetalle.getSubtotal();
            }
        }

        int gstInt = (int) (gst * 100);
        gst = ((double) gstInt) / 100;

        int subtotalSinGSTInt = (int) (subtotalSinGST * 100);
        subtotalSinGST = ((double) subtotalSinGSTInt) / 100;

        int subtotalInt = (int) (subtotal * 100);
        subtotal = ((double) subtotalInt) / 100;

        this.invoiceActualDetalle.setSubtotal(subtotal);
        this.invoiceActualDetalle.setSubtotal_sin_gst(subtotalSinGST);
        this.invoiceActualDetalle.setGst(gst);

    }

    public void calcularSubtotal() {
        double subtotalSinGSt = 0;
        int cantidad = 0;
        double precioUnitario = 0;
        if (this.invoiceActualDetalle.getCantidad() > 0 && this.invoiceActualDetalle.getPrecio_unitario() > 0) {
            subtotalSinGSt = this.invoiceActualDetalle.getCantidad() * this.invoiceActualDetalle.getPrecio_unitario();
            int subtotalSinGSTINt = (int) (subtotalSinGSt * 100);
            subtotalSinGSt = ((double) subtotalSinGSTINt) / 100;
            this.invoiceActualDetalle.setSubtotal_sin_gst(subtotalSinGSt);
        }
    }

    public void agregarDetalle() {
        if (validarDetalle() && this.validarInvoice()) {
            this.invoiceActualDetalle.setAuto(this.autoActual);

            if (this.sentido == Invoice_Mecanico.SENTIDO_INGRESO) {
                this.invoiceActualDetalle.setTipo(Invoice_Mecanico_Detalle.TIPO_INCOME);
            }

            this.listaInvoiceDetalle.add(invoiceActualDetalle);
            
            Invoice_Mecanico_Detalle InvoiceDetalleOriginal = this.invoiceActualDetalle;

            double totalSinGST = this.invoiceActual.getTotal_sin_gst();
            double gst = this.invoiceActual.getGst();
            double totalConGST = this.invoiceActual.getTotal();

            totalSinGST = totalSinGST + this.invoiceActualDetalle.getSubtotal_sin_gst();
            int totalSinGSTint = (int) (totalSinGST * 100);
            totalSinGST = ((double) totalSinGSTint) / 100;

            gst = gst + this.invoiceActualDetalle.getGst();
            int gstint = (int) (gst * 100);
            gst = ((double) gstint) / 100;

            totalConGST = totalConGST + this.invoiceActualDetalle.getSubtotal();
            int totalConGSTint = (int) (totalConGST * 100);
            totalConGST = ((double) totalConGSTint) / 100;

            this.invoiceActual.setTotal(totalConGST);
            this.invoiceActual.setTotal_sin_gst(totalSinGST);
            this.invoiceActual.setGst(gst);

            this.invoiceActualDetalle = new Invoice_Mecanico_Detalle();
            if(InvoiceDetalleOriginal.getTipo()==Invoice_Mecanico_Detalle.TIPO_EXTERNAL_CAR || InvoiceDetalleOriginal.getTipo()==Invoice_Mecanico_Detalle.TIPO_INCOME)
            {
                this.invoiceActualDetalle.setMarca(InvoiceDetalleOriginal.getMarca());
                this.invoiceActualDetalle.setModelo(InvoiceDetalleOriginal.getModelo());
                this.invoiceActualDetalle.setRego(InvoiceDetalleOriginal.getRego());
                this.invoiceActualDetalle.setColor(InvoiceDetalleOriginal.getColor());
                this.invoiceActualDetalle.setYear(InvoiceDetalleOriginal.getYear());
            }
            if (this.sentido == Invoice_Mecanico.SENTIDO_INGRESO) {
                this.invoiceActualDetalle.setTipo(Invoice_Mecanico_Detalle.TIPO_INCOME);
            }
            this.autoActual = null;
        }
    }

    public boolean validarInvoice() {
        if (this.invoiceActual.getFecha() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the invoice date"));
            return false;
        }
        if (this.invoiceActual.getInvoice_number() == null || this.invoiceActual.getInvoice_number().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the invoice number"));
            return false;
        }

        if (this.invoiceActual.getSentido() == Invoice_Mecanico.SENTIDO_GASTO) {
            if (this.proveedorActual == null || this.proveedorActual.getId() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The supplier is not defined"));
                return false;
            }
            Invoice_Mecanico invoiceEnBD = this.invoiceMecanicoEJB.getInvoicePorProveedorNumero(this.proveedorActual.getId(), this.invoiceActual.getInvoice_number());
            if (invoiceEnBD != null && invoiceEnBD.getId() != this.invoiceActual.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "A supplier invoice with the entered number has already been loaded"));
                return false;
            }
        } else if (this.invoiceActual.getSentido() == Invoice_Mecanico.SENTIDO_INGRESO) {
            Invoice_Mecanico invoiceEgreso = this.invoiceMecanicoEJB.getInvoiceIngresoPorNumero(this.invoiceActual.getInvoice_number());
            if (invoiceEgreso != null && invoiceEgreso.getId() != this.invoiceActual.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "A invoice with the entered number has already been loaded"));
                return false;
            }
        }
        return true;
    }

    public boolean validarDetalle() {
        if (this.invoiceActualDetalle.getTipo() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a type"));
            return false;
        }

        if (this.invoiceActualDetalle.getDescripcion() == null || this.invoiceActualDetalle.getDescripcion().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a description"));
            return false;
        }

        if (this.invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_OUR_CAR || this.invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_EXTERNAL_CAR) {
            if (this.invoiceActualDetalle.getRego() == null || this.invoiceActualDetalle.getRego().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the registration number"));
                return false;
            }
            if (this.invoiceActualDetalle.getYear() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the year of the car"));
                return false;
            }
            if (this.invoiceActualDetalle.getMarca() != null && this.invoiceActualDetalle.getMarca().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a brand"));
                return false;
            }
            if (this.invoiceActualDetalle.getModelo() != null && this.invoiceActualDetalle.getModelo().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a model"));
                return false;
            }
            if (this.invoiceActualDetalle.getDescripcion() == null || this.invoiceActualDetalle.getDescripcion().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a detail"));
                return false;
            }
            if (this.invoiceActualDetalle.getCantidad() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The quantity can't be less than 1"));
                return false;
            }

            if (this.invoiceActualDetalle.getTipo() == Invoice_Mecanico_Detalle.TIPO_OUR_CAR) {
                if (this.autoActual == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Our car is not defined"));
                    return false;
                }
                if (this.invoiceActualDetalle.getVin() == null || this.invoiceActualDetalle.getVin().trim().length() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the vin of the car"));
                    return false;
                }
            }

        }
        return true;
    }

    public List<Invoice_Mecanico_Detalle> getListaInvoiceDetalle() {
        return listaInvoiceDetalle;
    }

    public void setListaInvoiceDetalle(List<Invoice_Mecanico_Detalle> listaInvoiceDetalle) {
        this.listaInvoiceDetalle = listaInvoiceDetalle;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public void registrarInvoice() {
        if (this.validarInvoice() && this.validarRegistrar()) {
            this.invoiceActual.setSentido(this.sentido);
            if (this.invoiceActual.getSentido() == Invoice_Mecanico.SENTIDO_GASTO) {
                this.invoiceActual.setProveedor(this.proveedorActual);
            }
            this.invoiceMecanicoEJB.create(this.invoiceActual);
            for (int h = 0; h < this.listaInvoiceDetalle.size(); h++) {
                Invoice_Mecanico_Detalle invoiceDetalle = this.listaInvoiceDetalle.get(h);
                invoiceDetalle.setInvoiceMecanico(invoiceActual);
                if (invoiceDetalle.getMarca() != null && invoiceDetalle.getMarca().getId() == -1) {
                    invoiceDetalle.setMarca(null);
                }
                if (invoiceDetalle.getModelo() != null && invoiceDetalle.getModelo().getId() == -1) {
                    invoiceDetalle.setModelo(null);
                }
                this.invoiceMecanicoDetalleEJB.create(invoiceDetalle);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Invoice registered successfully"));
            this.invoiceActual = new Invoice_Mecanico();

            this.listaInvoiceDetalle.clear();
        }
    }

    public void editarInvoice() {
        if (this.validarInvoice() && this.validarEditar()) {
            if (this.invoiceActual.getSentido() == Invoice_Mecanico.SENTIDO_GASTO) {
                this.invoiceActual.setProveedor(this.proveedorActual);
            }
            this.invoiceMecanicoEJB.edit(invoiceActual);
            this.invoiceMecanicoDetalleEJB.saveListaDetalleInvoiceDefinitiva(listaInvoiceDetalle, invoiceActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Invoice edited succesfully"));
        }
    }

    private boolean validarEditar() {
        if (this.listaInvoiceDetalle.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Can't create invoice without detail."));
            return false;
        }

        return true;
    }

    private boolean validarRegistrar() {
        if (this.listaInvoiceDetalle.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Can't create invoice without detail."));
            return false;
        }

        return true;
    }

    public void eliminarDetalleFactura(Invoice_Mecanico_Detalle invoiceDetalle) {
        if (invoiceDetalle != null) {
            this.listaInvoiceDetalle.remove(invoiceDetalle);
            
            double TotalSinGst =this.invoiceActual.getTotal_sin_gst();
            double TotalGst = this.invoiceActual.getGst();
            double TotalConGst = this.invoiceActual.getTotal();
            
            TotalSinGst = TotalSinGst-invoiceDetalle.getSubtotal_sin_gst();
            TotalGst = TotalGst-invoiceDetalle.getGst();
            TotalConGst = TotalConGst-invoiceDetalle.getSubtotal();
            
            int TotalSinGstInt = (int)(TotalSinGst*100);
            int TotalGstInt = (int)(TotalGst*100);
            int TotalConGstInt = (int)(TotalConGst*100);
            
            TotalSinGst = ((double)TotalSinGstInt)/100;
            TotalGst = ((double)TotalGstInt)/100;
            TotalConGst = ((double)TotalConGstInt)/100;
            
            this.invoiceActual.setTotal_sin_gst(TotalSinGst);
            this.invoiceActual.setGst(TotalGst);
            this.invoiceActual.setTotal(TotalConGst);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Has been successfully removed"));
        }

    }

    public String getAuto(Invoice_Mecanico_Detalle invoiceMecanicoDetalle) {
        String resultado = "";
        if (invoiceMecanicoDetalle != null) {
            if (invoiceMecanicoDetalle.getRego() != null && invoiceMecanicoDetalle.getRego().trim().length() > 0) {
                resultado = resultado + " " + invoiceMecanicoDetalle.getRego();
            }
            if (invoiceMecanicoDetalle.getYear() > 0) {
                resultado = resultado + " " + invoiceMecanicoDetalle.getYear();
            }
            if (invoiceMecanicoDetalle.getMarca() != null && invoiceMecanicoDetalle.getMarca().getId() > -1) {
                Marca marcaDetalle = marcaEJB.find(invoiceMecanicoDetalle.getMarca().getId());
                if (marcaDetalle != null) {
                    resultado = resultado + " " + marcaDetalle.getNombre();
                }
            }
            if (invoiceMecanicoDetalle.getModelo() != null && invoiceMecanicoDetalle.getModelo().getId() > -1) {
                Modelo modeloDetalle = this.modeloEJB.find(invoiceMecanicoDetalle.getModelo().getId());
                if (modeloDetalle != null) {
                    resultado = resultado + " " + modeloDetalle.getNombre();
                }

            }
        }
        return resultado;
    }

}
