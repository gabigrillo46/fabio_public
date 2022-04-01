/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Alquiler_ClienteFacadeLocal;
import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.ejb.Invoice_MecanicoFacadeLocal;
import com.piantino.ejb.Invoice_Mecanico_DetalleFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.StateFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Auto;
import com.piantino.model.Cliente;
import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Invoice_Mecanico_Detalle;
import com.piantino.model.State;
import com.piantino.model.Usuario;
import java.io.IOException;
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
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DatosClienteController implements Serializable {

    private Cliente clientePrincipal;

    private List<State> listaEstadosTerritorio = new ArrayList<>();

    private List<Alquiler> listaAlquilerCliente = new ArrayList<>();

    @EJB
    private ClienteFacadeLocal clienteEJB;

    @EJB
    private StateFacadeLocal stateEJB;

    @EJB
    private Alquiler_ClienteFacadeLocal alquiler_clienteEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private Usuario usuarioLogueado;

    @EJB
    private PagoFacadeLocal pagoEJB;

    @EJB
    private Invoice_Mecanico_DetalleFacadeLocal invoiceMecanicoDetalleEJB;
    
    @EJB
    private Invoice_MecanicoFacadeLocal invoiceMecanicoEJB;

    private List<Invoice_Mecanico_Detalle> listaInvoiceDetalleCliente = new ArrayList<>();

    @PostConstruct
    private void init() {

        int idCliente = -1;
        try {

            String idClienteDato = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idClienteDato");
            if (idClienteDato != null && idClienteDato.trim().length() > 0) {
                idCliente = Integer.parseInt(idClienteDato);
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idClienteDato");

            Cliente clienteBD = clienteEJB.buscarPorID(idCliente);
            if (clienteBD == null) {
                clientePrincipal = new Cliente();
            } else {
                clientePrincipal = (Cliente) clienteBD.clone();
                if (clienteBD.getState() == null) {
                    clienteBD.setState(new State());
                }
                clientePrincipal.setState((State) clienteBD.getState().clone());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.clientePrincipal != null && this.clientePrincipal.getId() > -1) {
            this.cargarAlquileresDelCliente();
            this.cargarInvoicesCliente();
        }

        this.listaEstadosTerritorio = stateEJB.findAll();
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    }

    public void cargarAlquileresDelCliente() {
        List<Alquiler_Cliente> listaAlquileresCliente = this.alquiler_clienteEJB.buscarAlquileresDeCliente(this.clientePrincipal.getId());
        for (int a = 0; a < listaAlquileresCliente.size(); a++) {
            Alquiler_Cliente alquilerCliente = listaAlquileresCliente.get(a);
            Alquiler alquilerDeCliente = alquilerCliente.getAlquiler();
            float montoIngresadoAlquiler = pagoEJB.getMontoRecibidoDeAlquiler(alquilerDeCliente.getId());
            float deuda = alquilerDeCliente.getGran_total() - montoIngresadoAlquiler;
            int deudaInt = (int) (deuda * 100);
            deuda = ((float) deudaInt) / 100;
            alquilerDeCliente.setDeuda(deuda);
            if (alquilerDeCliente != null && alquilerDeCliente.getId() > -1) {
                this.listaAlquilerCliente.add(alquilerDeCliente);
            }
        }
    }

    public void cargarInvoicesCliente() {
        this.listaInvoiceDetalleCliente = this.invoiceMecanicoDetalleEJB.listaDetallePorIdCliente(this.clientePrincipal.getId());
    }

    public void verDetalleAlquiler(int idAlquiler) {
        Alquiler alq = alquilerEJB.getAlquilerPorId(idAlquiler);
        if (alq != null && alq.getId() > -1 && alq.getEstado() == Alquiler.ESTADO_CARGANDO
                && alq.getUsuario() != null && usuarioLogueado != null
                && alq.getUsuario().getId() != usuarioLogueado.getId()) {
            if (usuarioLogueado.getEs_super() != 4 && usuarioLogueado.getEs_super() != 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Agreement: " + idAlquiler + " is locked by:" + alq.getUsuario().getNombre() + " " + alq.getUsuario().getApellido() + " since: " + alq.getFecha()));
                return;
            }
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerDato", idAlquiler + "");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/pantallas/DatosRentalNew.piantino");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cliente getClientePrincipal() {
        return clientePrincipal;
    }

    public void setClientePrincipal(Cliente clientePrincipal) {
        this.clientePrincipal = clientePrincipal;
    }

    public String getFechaNacimientoCliente() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (this.clientePrincipal != null && this.clientePrincipal.getDOB() != null) {
            return sdf.format(this.clientePrincipal.getDOB());
        }
        {
            return "";
        }
    }

    public String getFechaVencimientoLicencia() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (this.clientePrincipal != null && this.clientePrincipal.getFecha_vencimiento_lic() != null) {
            return sdf.format(this.clientePrincipal.getFecha_vencimiento_lic());
        }
        {
            return "";
        }
    }

    public void registrarCliente() {
        this.clienteEJB.edit(clientePrincipal);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
    }

    public String getStateDireccion() {
        if (this.clientePrincipal != null && this.clientePrincipal.getState() != null) {
            return this.clientePrincipal.getState().getNombre();
        } else {
            return "";
        }
    }

    public String getPaisResidencia() {
        if (this.clientePrincipal != null && this.clientePrincipal.getPais() != null) {
            return this.clientePrincipal.getPais();
        } else {
            return "";
        }
    }

    public List<State> getListaEstadosTerritorio() {
        return listaEstadosTerritorio;
    }

    public void setListaEstadosTerritorio(List<State> listaEstadosTerritorio) {
        this.listaEstadosTerritorio = listaEstadosTerritorio;
    }

    public List<Alquiler> getListaAlquilerCliente() {
        return listaAlquilerCliente;
    }

    public void setListaAlquilerCliente(List<Alquiler> listaAlquilerCliente) {
        this.listaAlquilerCliente = listaAlquilerCliente;
    }
    
    
    public String getAutoInvoiceDetalle(Invoice_Mecanico_Detalle invoiceDetalle)
    {
        String auto ="";
        if(invoiceDetalle!=null && invoiceDetalle.getAuto()!=null)
        {
            Auto autoInvoice = invoiceDetalle.getAuto();
            auto += autoInvoice.getAño()+" ";
            if(autoInvoice.getMarca()!=null)
            {
                auto += autoInvoice.getMarca().getNombre()+" ";
            }
            if(autoInvoice.getModelo()!=null)
            {
                auto += autoInvoice.getModelo().getNombre()+" ";
            }
            auto += autoInvoice.getColor();
        }
        return auto;
    }

    public String getAutoAlquiler(Alquiler alq) {
        String auto = "";
        if (alq != null && alq.getAuto() != null) {
            Auto autoAlquiler = alq.getAuto();
            auto += autoAlquiler.getAño() + " ";
            if (autoAlquiler.getMarca() != null && autoAlquiler.getMarca().getId() > 0) {
                auto += autoAlquiler.getMarca().getNombre() + " ";
            }
            if (autoAlquiler.getModelo() != null && autoAlquiler.getModelo().getId() > 0) {
                auto += autoAlquiler.getModelo().getNombre() + " ";
            }

            auto += autoAlquiler.getColor();
        }
        return auto;
    }

    public String getCurrencyFormat(float monto) {
        String resultado = "";
        NumberFormat n = NumberFormat.getCurrencyInstance();
        resultado = n.format(monto);
        return resultado;
    }

    public List<Invoice_Mecanico_Detalle> getListaInvoiceDetalleCliente() {
        return listaInvoiceDetalleCliente;
    }

    public void setListaInvoiceDetalleCliente(List<Invoice_Mecanico_Detalle> listaInvoiceDetalleCliente) {
        this.listaInvoiceDetalleCliente = listaInvoiceDetalleCliente;
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
    
    public double getGastosTotal() {
        double resultado = 0;
        for (int a = 0; a < this.listaInvoiceDetalleCliente.size(); a++) {
            Invoice_Mecanico_Detalle detalle = this.listaInvoiceDetalleCliente.get(a);
            resultado = resultado + detalle.getSubtotal();
        }
        return resultado;
    }    
    

}
