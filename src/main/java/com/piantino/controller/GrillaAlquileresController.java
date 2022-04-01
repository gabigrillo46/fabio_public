package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Alquiler_ClienteFacadeLocal;
import com.piantino.ejb.Alquiler_ImpuestoFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.model.Alquiler;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Alquiler_Impuesto;
import com.piantino.model.Cliente;
import com.piantino.model.Impuesto;
import com.piantino.model.Pago;
import com.piantino.model.Sucursal;
import com.piantino.model.Usuario;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.primefaces.event.SelectEvent;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;

@Named
@ViewScoped
public class GrillaAlquileresController implements Serializable {

    @EJB
    private AlquilerFacadeLocal PagoEJB;

    @EJB
    private Alquiler_ClienteFacadeLocal alquilerClienteEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private List<Sucursal> listaSucursales;

    private int resultado = 0;

    private List<Alquiler> listaAlquileres;

    private Alquiler alquilerSeleccionado = new Alquiler();

    private String estadoStr = "";

    private String referencia;

    private String apellido;

    private String rego;

    private Date fechaDesde;

    private Date fechaHasta;

    private boolean deudaCurrentMayor0 = false;
    
    private String telefono;

    private int estado = -1;

    private int idSucural = -1;

    private Alquiler_Cliente clientePrincipal;

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    @EJB
    private Alquiler_ImpuestoFacadeLocal impuestoAlquilerEJB;

    @EJB
    private PagoFacadeLocal pagoEJB;

    private boolean loadingDisabled = true;
    
    private boolean preguntaDisabled = false;
    
    private String mensaje ="";
    
    
    private Usuario usuarioLogueado;

    @PostConstruct
    private void init() {
        this.listaAlquileres = new ArrayList();// alquilerEJB.findAll();
        this.listaSucursales = sucursalEJB.findAll();
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        calcularCurrentDeuda();
        verificarContratosLoading();
    }

    private void calcularCurrentDeuda() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        List<Alquiler> listaAlquileres = alquilerEJB.getAlquileresDeudaActualAntesFecha(c.getTime());
        for (int k = 0; k < listaAlquileres.size(); k++) {
            Alquiler alquiler = listaAlquileres.get(k);
            float sumaTodosImpuestos = 0;
            float pagoGeneral = 0;
            List<Alquiler_Impuesto> listaAlquilerImpuestos = impuestoAlquilerEJB.buscarImpuestosAlquiler(alquiler.getId());
            for (int a = 0; a < listaAlquilerImpuestos.size(); a++) {
                Alquiler_Impuesto alqImp = listaAlquilerImpuestos.get(a);
                sumaTodosImpuestos = sumaTodosImpuestos + alqImp.getSubtotal();
            }

            List<Pago> listaPagos = pagoEJB.buscarAprovadosPorAlquiler(alquiler.getId());
            for (int l = 0; l < listaPagos.size(); l++) {
                Pago pagoRegistrado = listaPagos.get(l);
                pagoGeneral = pagoGeneral + pagoRegistrado.getMonto();
            }
            Date hoy = c.getTime();
            int dias = (int) ((hoy.getTime() - alquiler.getFecha_inicio().getTime()) / 86400000);
            int semanasCurrent = (int) (dias / 7);
            float totalSemanaCurrentPorRate = semanasCurrent * alquiler.getRate_per_day();
            int totalSemanaCurrentPorRateInt = (int) (totalSemanaCurrentPorRate * 100);
            totalSemanaCurrentPorRate = ((float) totalSemanaCurrentPorRateInt) / 100;

            int pagoGeneralInt = (int) (pagoGeneral * 100);
            pagoGeneral = ((float) pagoGeneralInt) / 100;

            int sumaTodosImpuestosInt = (int) (sumaTodosImpuestos * 100);
            sumaTodosImpuestos = ((float) sumaTodosImpuestosInt) / 100;

            float deudaTotalCurrent = totalSemanaCurrentPorRate + sumaTodosImpuestos;
            int deudaTotalCurrentInt = (int) (deudaTotalCurrent * 100);
            deudaTotalCurrent = ((float) deudaTotalCurrentInt) / 100;

            float deudaNetoCurrent = deudaTotalCurrent - pagoGeneral;
            int deudaNetoCurrentInt = (int) (deudaNetoCurrent * 100);
            deudaNetoCurrent = ((float) deudaNetoCurrentInt) / 100;
            

            alquiler.setFecha_calculo_current(c.getTime());
            alquiler.setDeudaCurrent(deudaNetoCurrent);
            alquilerEJB.edit(alquiler);
        }
    }

    public List<Alquiler> getListaAlquileres() {
        return listaAlquileres;
    }

    public void setListaAlquileres(List<Alquiler> listaAlquileres) {
        this.listaAlquileres = listaAlquileres;
    }

    public String getEstadoStr() {
        return estadoStr;
    }

    public void setEstadoStr(String estadoStr) {
        this.estadoStr = estadoStr;
    }

    public boolean esCancelado(int estado) {
        return (estado == Alquiler.ESTADO_CANCELADO);
    }

    public boolean estaNoCancelado(int estado) {
        return (estado != Alquiler.ESTADO_CANCELADO);
    }

    public boolean esAlquilado(int estado) {
        return (estado == Alquiler.ESTADO_ALQUILADO);
    }

    public boolean esReservado(int estado) {
        return (estado == Alquiler.ESTADO_RESERVA);
    }

    public String getEstadoStr(Alquiler alquiler) {
        if (alquiler.getEstado() == Alquiler.ESTADO_ALQUILADO) {
            return "Hired";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_RESERVA) {
            return "Reservation";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_CANCELADO) {
            return "Cancelled";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_RETORNADO) {
            return "Returned";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_CARGANDO) {
            if (alquiler.getUsuario() != null) {
                return "Loading by [" + alquiler.getUsuario().getNombre() + " " + alquiler.getUsuario().getApellido() + " on: " + alquiler.getFecha() + "]";
            } else {
                return "Loading";
            }
        } else if (alquiler.getEstado() == Alquiler.ESTADO_VENDIDO) {
            return "Sold";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_REPO) {
            return "Repo";
        } else if (alquiler.getEstado() == Alquiler.ESTADO_COURTESY) {
            return "Courtesy";
        }

        return "";

    }

    public String getNombreClientePrincipal(int idAlquiler) {
        Alquiler_Cliente clientePrincAlquiler = alquilerClienteEJB.buscarClientePrincipalPorAlquiler(idAlquiler);
        if (clientePrincAlquiler != null) {
            this.setClientePrincipal(clientePrincAlquiler);
            return (clientePrincAlquiler.getCliente().getNombre() + " " + clientePrincAlquiler.getCliente().getApellido());
        } else {
            return "";
        }
    }

    public Alquiler_Cliente getClientePrincipal() {
        return clientePrincipal;
    }

    public void setClientePrincipal(Alquiler_Cliente clientePrincipal) {
        this.clientePrincipal = clientePrincipal;
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

    public Date getFecha() {
        return fechaDesde;
    }

    public void setFecha(Date fecha) {
        this.fechaDesde = fecha;
    }

    public void prueba() {
        System.out.println("pruebaaaaaaaaaa");
    }

    public void crearNuevoContrato() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerDato");
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

    public void verClienteAlquiler(int idAlquiler) {
        int idCliente = -1;
        Alquiler_Cliente alqCliente = alquilerClienteEJB.buscarClientePrincipalPorAlquiler(idAlquiler);
        if (alqCliente != null && alqCliente.getCliente() != null) {
            idCliente = alqCliente.getCliente().getId();
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idClienteDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idClienteDato", idCliente + "");
    }

    public void buscarPorFiltro() {
        boolean filtro = false;
        if (referencia == null || referencia.trim().length() == 0) {
            referencia = null;
        }

        if (rego == null || rego.trim().length() == 0) {
            rego = null;
        }
        if (fechaDesde == null || fechaHasta == null) {
            fechaDesde = null;
            fechaHasta = null;
        }

        if (apellido == null || apellido.trim().length() == 0) {
            apellido = null;
        }
        if (telefono == null || telefono.trim().length() == 0) {
            telefono = null;
        }

        this.listaAlquileres = alquilerEJB.getAlquilerPorFiltro(rego, apellido, fechaDesde, referencia, telefono, this.idSucural, this.fechaHasta, this.deudaCurrentMayor0, this.estado);
        this.setResultado(this.listaAlquileres.size());
        if (this.listaAlquileres.size() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "No result found"));
        }
        this.verificarContratosLoading();
    }

    public void crearNuevoAlquiler() {

    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Alquiler getAlquilerSeleccionado() {
        return alquilerSeleccionado;
    }

    public void setAlquilerSeleccionado(Alquiler alquilerSeleccionado) {
        this.alquilerSeleccionado = alquilerSeleccionado;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cancelarAlquiler(int idAlquiler) {
        Alquiler alq = alquilerEJB.getAlquilerPorId(idAlquiler);
        if (alq != null) {
            alq.setEstado(Alquiler.ESTADO_CANCELADO);
            alquilerEJB.edit(alq);
            this.buscarPorFiltro();
        }
    }

    public void registrarPago(int idAlquiler) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerSummary");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerSummary", idAlquiler + "");

        //idAlquilerSummary
    }

    public int getIdSucural() {
        return idSucural;
    }

    public void setIdSucural(int idSucural) {
        this.idSucural = idSucural;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public boolean isDeudaCurrentMayor0() {
        return deudaCurrentMayor0;
    }

    public void setDeudaCurrentMayor0(boolean deudaCurrentMayor0) {
        this.deudaCurrentMayor0 = deudaCurrentMayor0;
    }

    public void crearContratoNuevo() {
        System.out.println("crear uno nuevo");
    }

    private void verificarContratosLoading() {
        List<Alquiler> listaAlquileresLoading = alquilerEJB.getAlquileresLoading();
        if (listaAlquileresLoading.size() > 0) {
            loadingDisabled = false;
            preguntaDisabled=true;
            mensaje = "There are agreements in LOADING   are you sure to create a new one?";
            
        } else {
            loadingDisabled = true;
            preguntaDisabled=false;
            mensaje="Are you sure to create a new one?";
        }
    }

    public boolean isPreguntaDisabled() {
        return preguntaDisabled;
    }

    public void setPreguntaDisabled(boolean preguntaDisabled) {
        this.preguntaDisabled = preguntaDisabled;
    }
    
    

    public boolean isLoadingDisabled() {
        return loadingDisabled;
    }

    public void setLoadingDisabled(boolean loadingDisabled) {
        this.loadingDisabled = loadingDisabled;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
    

}
