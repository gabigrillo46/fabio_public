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
import com.piantino.model.Auto;
import com.piantino.model.Cliente;
import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Invoice_Mecanico_Detalle;
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
public class ExpensasAutoController implements Serializable {

    private Invoice_Mecanico invoiceMecanicoActual = new Invoice_Mecanico();

    private Invoice_Mecanico_Detalle invoiceMecanicoDetalleActual = new Invoice_Mecanico_Detalle();

    private Auto AutoActual = null;

    private Cliente proveedorActual = null;

    @EJB
    private Invoice_MecanicoFacadeLocal invoiceMecanicoEJB;

    @EJB
    private Invoice_Mecanico_DetalleFacadeLocal invoiceMecanicoDetalleEJB;

    @EJB
    private AutoFacadeLocal autoEJB;

    @EJB
    private ClienteFacadeLocal clienteEJB;

    private HashMap listaNombreContactoSimilar = new HashMap();

    @PostConstruct
    private void init() {
        try {
            String idAutoStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idAutoDatoExpensas");
            int idAuto = Integer.parseInt(idAutoStr);
            this.AutoActual = autoEJB.buscarPorId(idAuto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.proveedorActual = new Cliente();
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

    public Invoice_Mecanico getInvoiceMecanicoActual() {
        return invoiceMecanicoActual;
    }

    public void setInvoiceMecanicoActual(Invoice_Mecanico invoiceMecanicoActual) {
        this.invoiceMecanicoActual = invoiceMecanicoActual;
    }

    public Invoice_Mecanico_Detalle getInvoiceMecanicoDetalleActual() {
        return invoiceMecanicoDetalleActual;
    }

    public void setInvoiceMecanicoDetalleActual(Invoice_Mecanico_Detalle invoiceMecanicoDetalleActual) {
        this.invoiceMecanicoDetalleActual = invoiceMecanicoDetalleActual;
    }

    public Auto getAutoActual() {
        return AutoActual;
    }

    public void setAutoActual(Auto AutoActual) {
        this.AutoActual = AutoActual;
    }

    public Cliente getProveedorActual() {
        return proveedorActual;
    }

    public void setProveedorActual(Cliente proveedorActual) {
        this.proveedorActual = proveedorActual;
    }

    public List<String> completeTextNombreProveedor(String query) {
        List<String> results = new ArrayList<>();
        List<Cliente> listaProveedoresSimilares = new ArrayList();
        try {
            listaProveedoresSimilares = clienteEJB.buscarProveedoresPorNombreEmpezando(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listaProveedoresSimilares.size(); i++) {
            String stringHash = listaProveedoresSimilares.get(i).getApellido();
            listaNombreContactoSimilar.put(stringHash, listaProveedoresSimilares.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public void onProveedorSelectNombre(SelectEvent event) {
        Object obj = listaNombreContactoSimilar.get(event.getObject());

        if (obj != null) {
            this.proveedorActual = (Cliente) obj;
        }
    }

    public void seleccionoIncGST() {
        double gst = 0;
        double subtotalSinGST = 0;
        double subtotal = 0;
        if (this.invoiceMecanicoDetalleActual.isInc_gst()) {
            if (this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst() == 0 && this.invoiceMecanicoDetalleActual.getSubtotal() > 0) {
                gst = (this.invoiceMecanicoDetalleActual.getSubtotal() * 10) / 110;
                subtotalSinGST = this.invoiceMecanicoDetalleActual.getSubtotal() - gst;
                subtotal = this.invoiceMecanicoDetalleActual.getSubtotal();
            } else if (this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst() > 0 && this.invoiceMecanicoDetalleActual.getSubtotal() == 0) {
                gst = (this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst() * 10) / 100;
                subtotalSinGST = this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst();
                subtotal = subtotalSinGST + gst;
            } else if (this.invoiceMecanicoDetalleActual.getSubtotal() > 0) {
                gst = (this.invoiceMecanicoDetalleActual.getSubtotal() * 10) / 110;
                subtotalSinGST = this.invoiceMecanicoDetalleActual.getSubtotal() - gst;
                subtotal = this.invoiceMecanicoDetalleActual.getSubtotal();
            } else {
                gst = 0;
                subtotal = 0;
                subtotalSinGST = 0;
            }
        } else {
            if (this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst() == 0 && this.invoiceMecanicoDetalleActual.getSubtotal() > 0) {
                gst = 0;
                subtotalSinGST = this.invoiceMecanicoDetalleActual.getSubtotal();
                subtotal = this.invoiceMecanicoDetalleActual.getSubtotal();
            } else if (this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst() > 0 && this.invoiceMecanicoDetalleActual.getSubtotal() == 0) {
                gst = 0;
                subtotalSinGST = this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst();
                subtotal = this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst();
            } else if (this.invoiceMecanicoDetalleActual.getSubtotal() > 0) {
                gst = 0;
                subtotalSinGST = this.invoiceMecanicoDetalleActual.getSubtotal();
                subtotal = this.invoiceMecanicoDetalleActual.getSubtotal();
            }
        }

        int gstInt = (int) (gst * 100);
        gst = ((double) gstInt) / 100;

        int subtotalSinGSTInt = (int) (subtotalSinGST * 100);
        subtotalSinGST = ((double) subtotalSinGSTInt) / 100;

        int subtotalInt = (int) (subtotal * 100);
        subtotal = ((double) subtotalInt) / 100;

        this.invoiceMecanicoDetalleActual.setSubtotal(subtotal);
        this.invoiceMecanicoDetalleActual.setSubtotal_sin_gst(subtotalSinGST);
        this.invoiceMecanicoDetalleActual.setGst(gst);

    }

    public void confirmAndCloseDialog() {

        if (this.validarRegistroExpensa()) {
            
            this.invoiceMecanicoActual.setSentido(Invoice_Mecanico.SENTIDO_GASTO);
            this.invoiceMecanicoActual.setProveedor(this.proveedorActual);
            this.invoiceMecanicoActual.setGst(this.invoiceMecanicoDetalleActual.getGst());
            this.invoiceMecanicoActual.setTotal_sin_gst(this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst());
            this.invoiceMecanicoActual.setTotal(this.invoiceMecanicoDetalleActual.getSubtotal());
            
            this.invoiceMecanicoEJB.create(invoiceMecanicoActual);
            
            this.invoiceMecanicoDetalleActual.setAuto(this.AutoActual);
            this.invoiceMecanicoDetalleActual.setCantidad(1);
            this.invoiceMecanicoDetalleActual.setInvoiceMecanico(invoiceMecanicoActual);
            this.invoiceMecanicoDetalleActual.setTipo(Invoice_Mecanico_Detalle.TIPO_OUR_CAR);
            this.invoiceMecanicoDetalleActual.setColor(this.AutoActual.getColor());
            this.invoiceMecanicoDetalleActual.setMarca(this.AutoActual.getMarca());
            this.invoiceMecanicoDetalleActual.setModelo(this.AutoActual.getModelo());
            this.invoiceMecanicoDetalleActual.setYear(this.AutoActual.getAño());
            this.invoiceMecanicoDetalleActual.setRego(this.AutoActual.getRego());
            this.invoiceMecanicoDetalleActual.setVin(this.AutoActual.getVIN());
            this.invoiceMecanicoDetalleEJB.create(invoiceMecanicoDetalleActual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Expense registed sucesfully"));
            
            this.invoiceMecanicoActual=new Invoice_Mecanico();
            this.invoiceMecanicoDetalleActual= new Invoice_Mecanico_Detalle();
            this.proveedorActual = new Cliente();

        }
    }

    private boolean validarRegistroExpensa() {
        if (this.proveedorActual == null || this.proveedorActual.getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a supplier"));
            return false;
        }

        if (this.invoiceMecanicoDetalleActual.getSubtotal() == 0 || this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Subtotal can't be zero"));
            return false;
        }

        if (this.invoiceMecanicoActual.getInvoice_number() == null || this.invoiceMecanicoActual.getInvoice_number().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a invoice number"));
            return false;
        }

        Invoice_Mecanico invoiceMecanico = this.invoiceMecanicoEJB.getInvoicePorProveedorNumero(this.proveedorActual.getId(), this.invoiceMecanicoActual.getInvoice_number());
        if (invoiceMecanico != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Invoice number already registered to the supplier"));
            return false;
        }
        
        if(this.AutoActual == null || this.AutoActual.getId()==-1)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The car of the invoice is null"));
            return false;            
        }

        return true;
    }

}
