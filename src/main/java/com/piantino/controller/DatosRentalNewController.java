/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Alquiler_ClienteFacadeLocal;
import com.piantino.ejb.Alquiler_ImpuestoFacadeLocal;
import com.piantino.ejb.Alquiler_TarjetaFacadeLocal;
import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.ejb.DrivetrainFacadeLocal;
import com.piantino.ejb.ImpuestoFacadeLocal;
import com.piantino.ejb.Invoice_MecanicoFacadeLocal;
import com.piantino.ejb.Invoice_Mecanico_DetalleFacadeLocal;
import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.ejb.MultaFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.PaisFacadeLocal;
import com.piantino.ejb.ParametroFacadeLocal;
import com.piantino.ejb.SourceFacadeLocal;
import com.piantino.ejb.StateFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.TarjetaFacadeLocal;
import com.piantino.ejb.Tipo_TarjetaFacadeLocal;
import com.piantino.ejb.Tipo_autoFacadeLocal;
import com.piantino.ejb.Tipo_combustibleFacadeLocal;
import com.piantino.ejb.Tipo_pagoFacadeLocal;
import com.piantino.report.DtosMios.ContratoSubreportsTerm;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Alquiler_Impuesto;
import com.piantino.model.Alquiler_Tarjeta;
import com.piantino.model.Auto;
import com.piantino.model.Cliente;
import com.piantino.model.Drivetrain;
import com.piantino.model.Impuesto;
import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Invoice_Mecanico_Detalle;
import com.piantino.model.Marca;
import com.piantino.model.Modelo;
import com.piantino.model.Multa;
import com.piantino.model.Pago;
import com.piantino.model.Pais;
import com.piantino.model.Parametro;
import com.piantino.model.Source;
import com.piantino.model.State;
import com.piantino.model.Sucursal;
import com.piantino.model.Tarjeta;
import com.piantino.model.Tipo_Tarjeta;
import com.piantino.model.Tipo_auto;
import com.piantino.model.Tipo_combustible;
import com.piantino.model.Tipo_pago;
import com.piantino.model.Usuario;
import com.piantino.report.Dtos.RentalAgreement;
import com.piantino.report.Dtos.SumaryDetailsDTO;
import com.piantino.report.Dtos.SumaryHeaderDTO;
import com.piantino.report.Dtos.SumarySubreportArray;
import com.piantino.report.DtosMios.ContratoSubreports;
import com.piantino.report.DtosMios.PaymentsDetailsDTO;
import com.piantino.report.DtosMios.ImpuestoDetailsDTO;
import com.piantino.report.DtosMios.ImpuestosDetailsDTOTerm;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class DatosRentalNewController implements Serializable {

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private List<Multa> listaMultas;

    @EJB
    private MultaFacadeLocal multaEJB;

    private Alquiler alquilerActual;

    private List<Tipo_pago> listaTipoPago;

    @EJB
    private Tipo_pagoFacadeLocal tipoPagoEJB;

    private Usuario usuarioLogueado;

    @EJB
    private AutoFacadeLocal autoEJB;

    private Auto autoActual;

    private Pago pagoActual = null;

    private Tarjeta tarjetaActual = new Tarjeta();

    @EJB
    private Tipo_TarjetaFacadeLocal tipoTarjetaEJB;

    private List<Tipo_Tarjeta> tiposTarjeta = new ArrayList<>();

    @EJB
    private StateFacadeLocal statesEJB;

    private List<State> listaEstadosTerritorio;

    @EJB
    private MarcaFacadeLocal marcaEJB;

    private List<Marca> listaMarca;

    @EJB
    private ModeloFacadeLocal modeloEJB;

    private List<Modelo> listaModelosMarca;

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    private List<Sucursal> listaSucursales;

    @EJB
    private DrivetrainFacadeLocal driveTrainEJB;

    private List<Drivetrain> listaDriveTrain;

    @EJB
    private Tipo_combustibleFacadeLocal tipoCombustibleEJB;

    private List<Tipo_combustible> listaTipoCombustible;

    @EJB
    private Tipo_autoFacadeLocal tipoAutoEJB;

    private List<Tipo_auto> listaTipoAuto;

    private HashMap listaRegoSimilar = new HashMap();

    private HashMap listaVINSimilar = new HashMap();

    private HashMap listaApellidoSimilar = new HashMap();

    private HashMap listaApellidoSimilarAdicional = new HashMap();

    private int tabIndex = 0;

    private Cliente clientePrincipal;

    private Cliente clienteAdicional;

    @EJB
    private ClienteFacadeLocal clienteEJB;

    @EJB
    private Alquiler_ClienteFacadeLocal alquilerClienteEJB;

    private List<Alquiler> listaAlquilerCliente = new ArrayList<>();

    private List<Cliente> clientesAdicionales = new ArrayList<Cliente>();

    private boolean mostrarDriverAdicional = false;

    private Cliente clienteAdicionalSeleccionado;

    private String descripcionLargaAuto = "";

    @EJB
    private PaisFacadeLocal paisEJB;

    @EJB
    private SourceFacadeLocal sourceEJB;

    private List<Source> listaSource;

    private List<Alquiler_Impuesto> listaAlqImp = new ArrayList<Alquiler_Impuesto>();

    @EJB
    private Alquiler_ImpuestoFacadeLocal alquilerImpuestoEJB;

    private List<Impuesto> listaImpuestos;

    @EJB
    private ImpuestoFacadeLocal impuestoEJB;

    private List<Pago> listaPagoAlquiler;

    private List<Alquiler_Tarjeta> tarjetasAlquiler = new ArrayList();

    @EJB
    private Alquiler_TarjetaFacadeLocal alquilerTarjetaEJB;

    @EJB
    private TarjetaFacadeLocal tarjetaEJB;

    @EJB
    private PagoFacadeLocal pagoEJB;

    private List<Alquiler_Impuesto> listaImpuestoSeleccAlq;

    private List<Alquiler_Impuesto> listaImpuestoTerm;

    private boolean mostrarPago = false;

    private boolean mostrarTarjeta = false;

    private boolean mostrarTarjetasCredito = false;

    private boolean ocultarTarjetasCredito = false;

    private Alquiler_Tarjeta tarjetasSeleccionada;

    private boolean mostrarBotonSummary = false;

    private String fechaInicioOriginal = null;
    private String fechaFinOriginal = null;

    private String TAB_AUTO = "Car Detail";
    private String TAB_CLIENTE = "Client detail";
    private String TAB_BOOKING_DETAIL = "Booking detail";
    private String TAB_FEE = "Booking fee";
    private String TAB_PAYMENTS = "Booking Payments";
    private String TAB_DOCUMENTS = "Booking Documents";
    private String TAB_CLIENT_HISTORY = "Client History";
    private String kmsInStr = "";
    private String kmsOutStr = "";
    private String pickupDateStr = "";
    private String vehiculoStr = "";
    private String dropoffDateStr = "";

    private boolean mostrarLimpiar = false;

    private String tabAnterior = "";
    Calendar c;

    private boolean fechaVencimientoHabilitada = true;

    private boolean tieneTermino = false;

    private int semanasTerm = 0;

    private float totalTerm = 0;

    private float granTotalTerm = 0;

    @EJB
    private ParametroFacadeLocal parametroEJB;

    private boolean mostrarVerAuto = false;

    @EJB
    private Invoice_Mecanico_DetalleFacadeLocal invoiceMecanicoDetalleEJB;

    @EJB
    private Invoice_MecanicoFacadeLocal invoiceMecanicoEJB;

    private List<Invoice_Mecanico_Detalle> listaInvoiceDetalleCliente = new ArrayList<>();

    private boolean mostrarExpensesDatos = false;

    private Cliente proveedorActualExpenses = new Cliente();

    private Invoice_Mecanico invoiceMecanicoActual = null;

    private Invoice_Mecanico_Detalle invoiceMecanicoDetalleActual = null;

    private HashMap listaNombreProveedorExpensesSimilar = new HashMap();

    private Date fechaInicioHistory = null;

    private List<Alquiler_Impuesto> listaAlquilerImpuestos = new ArrayList<>();

    @PostConstruct
    private void init() {
        listaImpuestoTerm = new ArrayList<Alquiler_Impuesto>();
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        tabAnterior = this.TAB_AUTO;
        try {

            usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            String idAlquilerStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idAlquilerDato");
            //  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerDato");
            if (idAlquilerStr != null && idAlquilerStr.trim().length() > 0) {
                int idAlquiler = Integer.parseInt(idAlquilerStr);
                this.alquilerActual = alquilerEJB.getAlquilerPorId(idAlquiler);
                System.out.println("Existe un alquiler en la sesion");
            } else {
                this.alquilerActual = new Alquiler();
                this.alquilerActual.setUsuario(usuarioLogueado);
                this.alquilerActual.setEstado(Alquiler.ESTADO_CARGANDO);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                this.alquilerActual.setFecha(sdf.format(c.getTime()));

                alquilerEJB.create(alquilerActual);
                System.out.println("Alquiler nuevo: " + this.alquilerActual.getId());
            }
            if (this.alquilerActual.getFecha_inicio() == null) {
                System.out.println("No tiene fecha de inicio le ponemos una");
                this.alquilerActual.setFecha_inicio(c.getTime());
            }
            if (this.alquilerActual.getFecha_fin() == null) {
                System.out.println("No tiene fecha de fin le ponemos una");
                this.alquilerActual.setFecha_fin(c.getTime());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.fechaInicioOriginal = sdf.format(this.alquilerActual.getFecha_inicio());
            this.fechaFinOriginal = sdf.format(this.alquilerActual.getFecha_fin());
            System.out.println("formateamos la fecha de inicio y fin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.alquilerActual == null) {
            System.out.println("el alquiler actual es null return");
            return;
        }

        if (this.alquilerActual != null && this.alquilerActual.getUsuario() == null) {
            this.alquilerActual.setUsuario(usuarioLogueado);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            this.alquilerActual.setFecha(sdf.format(c.getTime()));
            this.alquilerEJB.edit(alquilerActual);
            System.out.println("Le ponemos el usuario actual");
        }

        if (this.usuarioLogueado == null) {
            System.out.println("el usuario logueado es null return");
            return;
        }
        this.kmsOutStr = this.alquilerActual.getKms_out() + "";
        this.kmsInStr = this.alquilerActual.getKms_in() + "";

        this.listaMultas = this.multaEJB.getListMultasPorIdAlquiler(this.alquilerActual.getId());

        this.listaEstadosTerritorio = statesEJB.findAll();
        this.listaMarca = marcaEJB.findAll();
        this.listaSucursales = sucursalEJB.findAll();
        this.listaTipoAuto = tipoAutoEJB.findAll();
        this.listaTipoCombustible = tipoCombustibleEJB.findAll();
        this.listaSource = sourceEJB.findAll();
        this.listaTipoPago = tipoPagoEJB.findAll();
        this.listaDriveTrain = driveTrainEJB.getTodasActivas();
        System.out.println("cargamos todas las listas");
        if (this.alquilerActual.getAuto() != null) {
            System.out.println("el alquiler tiene un auto lo clonamos");
            this.autoActual = this.getAutoClonado(this.alquilerActual.getAuto());
            if (this.autoActual.getRego().trim().length() > 0 && this.autoActual.getFecha_vencimiento_rego() != null) {
                this.fechaVencimientoHabilitada = false;
            }
        } else {
            this.autoActual = new Auto();
            /*    System.out.println("Creamos nuevo auto");
            this.statesEJB.getStatePorNombre("");
            State estadoTerri = new State();
            estadoTerri.setId(1);
            Tipo_combustible tipoCombustible = new Tipo_combustible();
            tipoCombustible.setId(1);
            Tipo_auto tipoAuto = new Tipo_auto();
            tipoAuto.setId(1);
            Sucursal sucursal = new Sucursal();
            sucursal.setId(2);
            Marca marca = new Marca();
            Modelo modelo = new Modelo();
            this.autoActual.setSucursal(sucursal);
            this.autoActual.setTipo_combustible(tipoCombustible);
            this.autoActual.setTipo_body(tipoAuto);
            this.autoActual.setState(estadoTerri);
            this.autoActual.setModelo(modelo);
            this.autoActual.setMarca(marca);*/
        }

        List<Alquiler_Cliente> listaAlquilerCliente = null;
        try {
            listaAlquilerCliente = alquilerClienteEJB.buscarPorAlquiler(this.alquilerActual.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.clienteAdicional = new Cliente();
        State estadoTerri = new State();
        estadoTerri.setId(1);
        this.clienteAdicional.setState(estadoTerri);
        this.clienteAdicional.setPais("Australia");
        this.clienteAdicional.setOtorgadaEN("Australia");

        if (listaAlquilerCliente != null && listaAlquilerCliente.size() > 0) {
            for (int j = 0; j < listaAlquilerCliente.size(); j++) {
                Alquiler_Cliente alq_cliente = listaAlquilerCliente.get(j);
                if (alq_cliente.isEs_principal()) {
                    Cliente clientePrincipalBD = clienteEJB.find(alq_cliente.getCliente().getId());
                    if (clientePrincipalBD != null) {
                        this.clientePrincipal = this.getClienteClonado(clientePrincipalBD);
                    } else {
                        this.clientePrincipal = new Cliente();
                        this.clientePrincipal.setPais("Australia");
                        this.clientePrincipal.setOtorgadaEN("Australia");
                        estadoTerri = new State();
                        estadoTerri.setId(1);
                        this.clientePrincipal.setState(estadoTerri);
                    }
                } else {
                    Cliente clienteAdicional = clienteEJB.find(alq_cliente.getCliente().getId());
                    if (clienteAdicional != null) {
                        this.setClienteAdicional(this.getClienteClonado(clienteAdicional));
                        this.clienteAdicional.setPais("Australia");
                        this.clienteAdicional.setOtorgadaEN("Australia");
                        this.setMostrarDriverAdicional(true);
                    }
                }
            }
        } else {
            System.out.println("el alquiler no tiene cliente principal creamos uno");
            clientePrincipal = new Cliente();
            this.clientePrincipal.setPais("Australia");
            this.clientePrincipal.setOtorgadaEN("Australia");
            estadoTerri = new State();
            estadoTerri.setId(1);
            this.clientePrincipal.setState(estadoTerri);
        }
        this.listaPagoAlquiler = pagoEJB.buscarAprovadosPorAlquiler(this.alquilerActual.getId());
        this.tarjetasAlquiler = alquilerTarjetaEJB.buscarPorAlquiler(this.alquilerActual.getId());
        this.listaAlqImp = alquilerImpuestoEJB.buscarImpuestosAlquiler(this.alquilerActual.getId());
        if (this.alquilerActual != null && this.alquilerActual.getId() > -1 && listaPagoAlquiler != null && this.listaPagoAlquiler.size() > 0) {
            System.out.println("El alquiler tiene pagos, lo actualizamos");
            this.actualizarDeuda();
        }

        this.pagoActual = new Pago();
        tiposTarjeta = tipoTarjetaEJB.findAll();

        this.pagoActual.setFecha_hora(c.getTime());
        this.tarjetaActual.setFechaVenc(c.getTime());
        this.refrescatBotonDesbloquear();

        if (this.clientePrincipal != null && this.clientePrincipal.getId() > -1) {
            this.cargarInvoicesCliente();
            this.cargarAlquileresDelCliente();
        }

        this.invoiceMecanicoActual = new Invoice_Mecanico();
        this.invoiceMecanicoDetalleActual = new Invoice_Mecanico_Detalle();

        System.out.println("Refrescamos boton refrescar");
    }

    public void refrescatBotonDesbloquear() {
        if (this.alquilerActual != null && this.alquilerActual.getId() > -1
                && this.alquilerActual.getEstado() == Alquiler.ESTADO_CARGANDO
                && this.alquilerActual.getUsuario() != null && this.alquilerActual.getUsuario().getId() > -1) {
            this.mostrarLimpiar = true;
        } else {
            this.mostrarLimpiar = false;
        }
    }

    public boolean isMostrarLimpiar() {
        return mostrarLimpiar;
    }

    public void setMostrarLimpiar(boolean mostrarLimpiar) {
        this.mostrarLimpiar = mostrarLimpiar;
    }

    public List<String> completeTextCliente(String query) {

        this.clientePrincipal = new Cliente();
        State estadoTerri = new State();
        estadoTerri.setId(1);
        this.clientePrincipal.setState(estadoTerri);
        this.clientePrincipal.setPais("Australia");
        this.clientePrincipal.setOtorgadaEN("Australia");

        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            listaClientes = clienteEJB.buscarPorApellidoSimilar(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> results = new ArrayList<>();
        for (int i = 0; i < listaClientes.size(); i++) {
            String stringHash = listaClientes.get(i).getNombre() + " " + listaClientes.get(i).getApellido() + " [" + listaClientes.get(i).getEmail() + "]";
            listaApellidoSimilar.put(stringHash, listaClientes.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public List<String> completeTextClienteAdicional(String query) {

        this.clienteAdicional = new Cliente();
        State estadoTerri = new State();
        estadoTerri.setId(1);
        this.clienteAdicional.setState(estadoTerri);
        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            listaClientes = clienteEJB.buscarPorApellidoSimilar(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> results = new ArrayList<>();
        for (int i = 0; i < listaClientes.size(); i++) {
            String stringHash = listaClientes.get(i).getNombre() + " " + listaClientes.get(i).getApellido() + " [" + listaClientes.get(i).getEmail() + "]";
            listaApellidoSimilarAdicional.put(stringHash, listaClientes.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public void onClienteSeleccionado(SelectEvent event) {
        Object obj = listaApellidoSimilar.get(event.getObject());
        Cliente clienteSelecc = null;
        if (obj != null) {
            clienteSelecc = this.getClienteClonado((Cliente) obj);
            this.setClientePrincipal(clienteSelecc);
        }
    }

    public void onClienteAdiciionalSeleccionado(SelectEvent event) {
        Object obj = listaApellidoSimilarAdicional.get(event.getObject());
        Cliente clienteSelecc = null;
        if (obj != null) {
            clienteSelecc = this.getClienteClonado((Cliente) obj);
            this.setClienteAdicional(clienteSelecc);
        }
    }

    public void buscarClienteApellidoNombre() {
        Cliente clienteExistAuto = null;
        try {
            clienteExistAuto = clienteEJB.buscarPorNombreYApellido(this.clientePrincipal.getNombre(), this.clientePrincipal.getApellido());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clienteExistAuto != null) {
            this.setClientePrincipal(this.getClienteClonado(clienteExistAuto));
        }

    }

    public void buscarClienteApellidoNombreAdicional() {
        Cliente clienteExistAuto = null;
        try {
            clienteExistAuto = clienteEJB.buscarPorNombreYApellido(this.clienteAdicional.getNombre(), this.clienteAdicional.getApellido());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clienteExistAuto != null) {
            this.setClienteAdicional(this.getClienteClonado(clienteExistAuto));
        }

    }

    public void limpiarCliente() {
        this.clientePrincipal = new Cliente();
        State estadoTerri = new State();
        estadoTerri.setId(1);
        this.clientePrincipal.setState(estadoTerri);
    }

    public void limpiarClienteAdicional() {
        this.clienteAdicional = new Cliente();
        State estadoTerri = new State();
        estadoTerri.setId(1);
        this.clienteAdicional.setState(estadoTerri);
        this.setMostrarDriverAdicional(false);
    }

    public boolean isCliente() {
        return (this.clientePrincipal.getId() != -1);
    }

    public Alquiler getAlquilerActual() {
        return alquilerActual;
    }

    public void setAlquilerActual(Alquiler alquilerActual) {
        this.alquilerActual = alquilerActual;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public void desbloquear() {
        if (this.alquilerActual == null || this.alquilerActual.getId() == -1) {
            return;
        }
        System.out.println("****************************************************************************************");
        System.out.println("Se va a limpiar el contrato: " + this.alquilerActual.getId());
        System.out.println("****************************************************************************************");
        alquilerClienteEJB.eliminarClientesAdicionales(this.alquilerActual.getId());
        alquilerClienteEJB.eliminarClientePrincipalDelAlq(this.alquilerActual.getId());
        alquilerImpuestoEJB.eliminarPorAlquiler(this.alquilerActual.getId());
        alquilerTarjetaEJB.eliminarTarjetasDeAlquiler(this.alquilerActual.getId());
        pagoEJB.eliminarPorAlquiler(this.alquilerActual.getId());
        this.alquilerActual.setAuto(null);
        this.alquilerActual.setFecha_inicio(null);
        this.alquilerActual.setFecha_fin(null);
        this.alquilerActual.setSource(null);
        this.alquilerActual.setMovil(null);
        this.alquilerActual.setFecha_calculo_current(null);
        this.alquilerActual.setFecha_primer_pago(null);
        this.alquilerActual.setSucursal_origen(null);
        this.alquilerActual.setSucursal_destino(null);
        this.alquilerActual.setUsuario(null);
        this.alquilerActual.setFecha(null);
        this.alquilerActual.setRego("");
        this.alquilerActual.setApellido("");
        this.alquilerActual.setTelefono("");
        this.alquilerEJB.edit(alquilerActual);
    }

    public void summit() {
        if (validarAuto() == false) {
            this.setTabIndex(0);
            return;
        }
        if (validarCliente() == false) {
            this.setTabIndex(1);
            return;
        }
        if (validarBookingDetails() == false) {
            this.setTabIndex(2);
            return;
        }
        registrarAuto();
        registrarCliente();
        registrarBookingDetails();
        registrarImpuestos();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Registered Successfully"));

    }

    public void actualizarTotal() {
        int cantidadtiempo = 0;
        if (this.alquilerActual.isDiario()) {
            cantidadtiempo = this.alquilerActual.getDias();
        } else {
            cantidadtiempo = this.alquilerActual.getSemanas();
        }
        float rate = this.alquilerActual.getRate_per_day();
        int totalInt = (int) ((rate * cantidadtiempo) * 100);
        float total = ((float) totalInt) / 100;

        float granTotal = total + this.alquilerActual.getExtra();
        int granTotalInt = (int) (granTotal * 100);
        granTotal = ((float) granTotalInt) / 100;
        this.alquilerActual.setGran_total(granTotal);
        this.alquilerActual.setTotal(total);
    }

    private void actualizarDeuda() {
        if (this.alquilerActual != null) {
            float deuda = this.alquilerActual.getGran_total();
            if (this.listaPagoAlquiler != null && this.listaPagoAlquiler.size() > 0) {
                for (int a = 0; a < this.listaPagoAlquiler.size(); a++) {
                    Pago pagoAlquiler = this.listaPagoAlquiler.get(a);
                    if (pagoAlquiler.esPagoAprobado()) {
                        deuda = deuda - pagoAlquiler.getMonto();
                    }
                }
            }
            int deudaInt = (int) (deuda * 100);
            deuda = ((float) (deudaInt)) / 100;

            this.alquilerActual.setDeuda(deuda);

            alquilerEJB.edit(this.alquilerActual);
        }

    }

    public void actualizarRate() {
        int cantidadtiempo = 0;
        if (this.alquilerActual.isDiario()) {
            cantidadtiempo = this.alquilerActual.getDias();
        } else {
            cantidadtiempo = this.alquilerActual.getSemanas();
        }
        float total = this.alquilerActual.getTotal();
        int rateInt = (int) ((total / cantidadtiempo) * 100);
        float rate = ((float) rateInt) / 100;
        this.alquilerActual.setRate_per_day(rate);

        float granTotal = total + this.alquilerActual.getExtra();
        int granTotalInt = (int) (granTotal * 100);
        granTotal = ((float) granTotalInt) / 100;
        this.alquilerActual.setGran_total(granTotal);
    }

    private void registrarBookingDetails() {

        long diferenciaTime = this.alquilerActual.getFecha_fin().getTime() - this.alquilerActual.getFecha_inicio().getTime();
        float diasf = (float) diferenciaTime / 86400000;
        float semanasf = diasf / 7;
        int semanas = Math.round(semanasf);
        int dias = Math.round(diasf);

        /*int dias = (int) ((this.alquilerActual.getFecha_fin().getTime() - this.alquilerActual.getFecha_inicio().getTime()) / 86400000);
        int semanas = (int) (dias / 7);*/
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaInicioActual = sdf.format(this.alquilerActual.getFecha_inicio());
        String fechaFinActual = sdf.format(this.alquilerActual.getFecha_fin());
        if (this.fechaInicioOriginal == null || this.fechaFinOriginal == null || !fechaInicioActual.equalsIgnoreCase(this.fechaInicioOriginal)
                || !fechaFinActual.equalsIgnoreCase(this.fechaFinOriginal)) {
            this.alquilerActual.setDias(dias);
            this.alquilerActual.setSemanas(semanas);
            this.fechaInicioOriginal = fechaInicioActual;
            this.fechaFinOriginal = fechaFinActual;
        }
        Sucursal sucursalOrigen = sucursalEJB.find(this.alquilerActual.getSucursal_origen().getId());
        this.alquilerActual.setSucursal_origen(sucursalOrigen);
        Sucursal sucursalDestino = sucursalEJB.find(this.alquilerActual.getSucursal_destino().getId());
        this.alquilerActual.setSucursal_destino(sucursalDestino);
        if (alquilerActual.getFecha_primer_pago() == null) {
            this.alquilerActual.setFecha_primer_pago(this.alquilerActual.getFecha_inicio());
        }
        alquilerEJB.edit(this.alquilerActual);
        this.actualizarTotal();
        this.actualizarDeuda();

        if (this.alquilerActual.getEstado() == Alquiler.ESTADO_CANCELADO || this.alquilerActual.getEstado() == Alquiler.ESTADO_REPO || this.alquilerActual.getEstado() == Alquiler.ESTADO_RETORNADO) {
            if (this.alquilerActual.getAuto() != null && this.alquilerActual.getKms_in() > 0 && this.alquilerActual.getAuto().getId() > -1) {
                Auto autoAlquiler = this.alquilerActual.getAuto();
                autoAlquiler.setKilometraje(this.alquilerActual.getKms_in() + "");
                try {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                    if (autoAlquiler.getFecha_vencimiento_rego() != null) {
                        System.out.println("se actualiza auto: " + autoAlquiler.getRego() + " desde alquiler fecha rego: " + sdf2.format(autoAlquiler.getFecha_vencimiento_rego()) + " usuario: " + this.usuarioLogueado.getEmail());
                    } else {
                        System.out.println("se actualiza auto: " + autoAlquiler.getRego() + " desde alquiler sin fecha rego usuario: " + this.usuarioLogueado.getEmail());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                autoEJB.edit(autoAlquiler);
            }
        }
        alquilerEJB.edit(this.alquilerActual);
    }

    private void registrarCliente() {
        if (this.clientePrincipal.getId() == -1) {
            clienteEJB.create(this.clientePrincipal);
        } else {
            clienteEJB.edit(this.clientePrincipal);
        }
        Alquiler_Cliente alqCli = new Alquiler_Cliente();
        alqCli.setAlquiler(alquilerActual);
        alqCli.setCliente(clientePrincipal);
        alqCli.setEs_principal(true);
        alquilerClienteEJB.agregarClientePrincipalAlAlq(alqCli);

        if (this.mostrarDriverAdicional) {

            if (this.clienteAdicional.getId() == -1) {
                clienteEJB.create(clienteAdicional);
            } else {
                clienteEJB.edit(clienteAdicional);
            }
            Alquiler_Cliente alqCliAdicional = new Alquiler_Cliente();
            alqCliAdicional.setAlquiler(alquilerActual);
            alqCliAdicional.setCliente(clienteAdicional);
            alqCliAdicional.setEs_principal(false);
            alquilerClienteEJB.agregarClienteAdicionalAlAlq(alqCliAdicional);
        } else {
            alquilerClienteEJB.eliminarClientesAdicionales(alquilerActual.getId());
        }

        this.alquilerActual.setApellido(clientePrincipal.getApellido());
        this.alquilerActual.setTelefono(clientePrincipal.getTelefono());
        this.alquilerActual.setMovil(clientePrincipal.getMovil());
        alquilerEJB.edit(alquilerActual);

    }

    public Auto getAutoActual() {
        return autoActual;
    }

    public void setAutoActual(Auto autoActual) {
        this.autoActual = autoActual;
    }

    public List<State> getListaEstadosTerritorio() {
        return listaEstadosTerritorio;
    }

    public void setListaEstadosTerritorio(List<State> listaEstadosTerritorio) {
        this.listaEstadosTerritorio = listaEstadosTerritorio;
    }

    public List<Marca> getListaMarca() {
        return listaMarca;
    }

    public void setListaMarca(List<Marca> listaMarca) {
        this.listaMarca = listaMarca;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Tipo_combustible> getListaTipoCombustible() {
        return listaTipoCombustible;
    }

    public void setListaTipoCombustible(List<Tipo_combustible> listaTipoCombustible) {
        this.listaTipoCombustible = listaTipoCombustible;
    }

    public List<Tipo_auto> getListaTipoAuto() {
        return listaTipoAuto;
    }

    public void setListaTipoAuto(List<Tipo_auto> listaTipoAuto) {
        this.listaTipoAuto = listaTipoAuto;
    }

    public List<Modelo> getListaModelosMarca() {
        if (this.listaModelosMarca == null) {
            this.listaModelosMarca = new ArrayList<Modelo>();
        }
        while (this.listaModelosMarca.size() > 0) {
            this.listaModelosMarca.remove(0);
        }
        if (this.autoActual.getMarca() != null && this.autoActual.getMarca().getId() > -1) {
            this.listaModelosMarca = modeloEJB.buscarPorMarca(this.getAutoActual().getMarca().getId());
            /* if (this.autoActual.getModelo() != null && this.autoActual.getModelo().getId() > -1) {
                if (this.modeloEsDeMarca(this.autoActual.getMarca(), this.autoActual.getModelo()) == false) {
                    if (this.listaModelosMarca.size() > 0) {
                        this.autoActual.setModelo(this.listaModelosMarca.get(0));
                    } else {
                        this.autoActual.setModelo(null);
                    }
                }
            }*/
        } else if (this.listaMarca != null && this.listaMarca.size() > 0) {
            //      this.listaModelosMarca = modeloEJB.buscarPorMarca(this.listaMarca.get(0).getId());
        }
        return listaModelosMarca;
    }

    private boolean modeloEsDeMarca(Marca marca, Modelo modelo) {
        List<Modelo> listaModeloDeMarca = this.modeloEJB.buscarPorMarca(marca.getId());
        boolean encontrado = false;
        for (int a = 0; a < listaModeloDeMarca.size(); a++) {
            Modelo modeloDeMarca = listaModeloDeMarca.get(a);
            if (modeloDeMarca.getId() == modelo.getId()) {
                encontrado = true;
            }
        }
        return encontrado;
    }

    public List<String> completeTextVIN(String query) {
        List<String> results = new ArrayList<>();
        Auto autoVIN = autoEJB.buscarPorVin(query);
        if (autoVIN != null) {
            this.autoActual = this.getAutoClonado(autoVIN);
            return results;
        }

        String reverse = "";
        for (int i = 0; i < query.trim().length(); i++) {
            reverse = query.charAt(i) + reverse;
        }
        this.fechaVencimientoHabilitada = true;
        this.autoActual = new Auto();
        State estadoTerri = new State();
        estadoTerri.setId(1);
        Tipo_combustible tipoCombustible = new Tipo_combustible();
        tipoCombustible.setId(2);
        Tipo_auto tipoAuto = new Tipo_auto();
        tipoAuto.setId(1);
        Sucursal sucursal = new Sucursal();
        sucursal.setId(2);
        /*Marca marca = new Marca();
        marca.setId(1);
        Modelo modelo = new Modelo();
        modelo.setId(1);*/
        this.autoActual.setSucursal(sucursal);
        this.autoActual.setTipo_combustible(tipoCombustible);
        this.autoActual.setTipo_body(tipoAuto);
        this.autoActual.setState(estadoTerri);
        //this.autoActual.setModelo(modelo);
        //this.autoActual.setMarca(marca);
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

    public List<String> completeTextRego(String query) {
        this.fechaVencimientoHabilitada = true;
        this.autoActual = new Auto();
        State estadoTerri = new State();
        estadoTerri.setId(1);
        Tipo_combustible tipoCombustible = new Tipo_combustible();
        tipoCombustible.setId(1);
        Tipo_auto tipoAuto = new Tipo_auto();
        tipoAuto.setId(1);
        Sucursal sucursal = new Sucursal();
        sucursal.setId(2);
        Marca marca = new Marca();
        marca.setId(1);
        Modelo modelo = new Modelo();
        modelo.setId(1);
        this.autoActual.setSucursal(sucursal);
        this.autoActual.setTipo_combustible(tipoCombustible);
        this.autoActual.setTipo_body(tipoAuto);
        this.autoActual.setState(estadoTerri);
        this.autoActual.setModelo(modelo);
        this.autoActual.setMarca(marca);
        List<Auto> listaAuto = new ArrayList<Auto>();
        try {
            listaAuto = autoEJB.buscarPorRegoSimilar(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> results = new ArrayList<>();
        for (int i = 0; i < listaAuto.size(); i++) {
            String stringHash = listaAuto.get(i).getRego();
            listaRegoSimilar.put(stringHash, listaAuto.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public void onAutoSelect(SelectEvent event) {
        Object obj = listaRegoSimilar.get(event.getObject());

        if (obj != null) {
            this.autoActual = this.getAutoClonado((Auto) obj);
            if (this.autoActual.getId() > -1 && this.autoActual.getRego() != null && this.autoActual.getFecha_vencimiento_rego() != null) {
                fechaVencimientoHabilitada = false;
            }
        }
    }

    public void onAutoSelectVIN(SelectEvent event) {
        Object obj = listaVINSimilar.get(event.getObject());

        if (obj != null) {
            this.autoActual = this.getAutoClonado((Auto) obj);
            if (this.autoActual.getId() > -1 && this.autoActual.getRego() != null && this.autoActual.getFecha_vencimiento_rego() != null) {
                fechaVencimientoHabilitada = false;
            }
        }
    }

    public Cliente getClienteClonado(Cliente clienteAClonar) {
        Cliente clienteClonado = new Cliente();
        try {
            clienteClonado = (Cliente) clienteAClonar.clone();

            if (clienteAClonar.getState() == null) {
                clienteAClonar.setState(new State());
            }
            clienteClonado.setState((State) clienteAClonar.getState().clone());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clienteClonado;
    }

    public Auto getAutoClonado(Auto autoaclonar) {
        Auto autoclonado = new Auto();
        try {

            autoclonado = (Auto) autoaclonar.clone();

            if (autoaclonar.getState() == null) {
                autoaclonar.setState(new State());
            }
            autoclonado.setState((State) autoaclonar.getState().clone());

            if (autoaclonar.getMarca() == null) {
                autoaclonar.setMarca(new Marca());
            }
            autoclonado.setMarca((Marca) autoaclonar.getMarca().clone());

            if (autoaclonar.getModelo() == null) {
                autoaclonar.setModelo(new Modelo());
            }
            autoclonado.setModelo((Modelo) autoaclonar.getModelo().clone());

            if (autoaclonar.getTipo_combustible() == null) {
                autoaclonar.setTipo_combustible(new Tipo_combustible());
            }
            autoclonado.setTipo_combustible((Tipo_combustible) autoaclonar.getTipo_combustible().clone());

            if (autoaclonar.getTipo_body() == null) {
                autoaclonar.setTipo_body(new Tipo_auto());
            }
            autoclonado.setTipo_body((Tipo_auto) autoaclonar.getTipo_body().clone());

            if (autoaclonar.getSucursal() == null) {
                autoaclonar.setSucursal(new Sucursal());
            }
            autoclonado.setSucursal((Sucursal) autoaclonar.getSucursal().clone());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return autoclonado;
    }

    public void setListaModelosMarca(List<Modelo> listaModelosMarca) {
        this.listaModelosMarca = listaModelosMarca;
    }

    public void cambioTab(TabChangeEvent event) {

        System.out.println(event.getTab().getTitle());

    }

    private void registrarAuto() {
        this.autoActual.setEstado(Auto.ESTADO_DISPONIBLE);
        if (this.autoActual.getLabel() != null && this.autoActual.getLabel().getId() < 1) {
            this.autoActual.setLabel(null);
        }
        if (this.autoActual.getClienteProveedor() != null && this.autoActual.getClienteProveedor().getId() < 1) {
            this.autoActual.setClienteProveedor(null);
        }
        if (this.autoActual.getTipoCompra() != null && this.autoActual.getTipoCompra().getId() < 1) {
            this.autoActual.setTipoCompra(null);
        }
        if (this.alquilerActual.getEstado() == Alquiler.ESTADO_ALQUILADO || this.alquilerActual.getEstado() == Alquiler.ESTADO_RESERVA || this.alquilerActual.getEstado() == Alquiler.ESTADO_CARGANDO) {
            this.autoActual.setDisponible(false);
        }

        if (this.autoActual.getId() > -1) {
            try {
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                if (autoActual.getFecha_vencimiento_rego() != null) {
                    System.out.println("se actualiza auto: " + autoActual.getRego() + " desde alquiler fecha rego: " + sdf2.format(autoActual.getFecha_vencimiento_rego()) + " usuario: " + this.usuarioLogueado.getEmail());
                } else {
                    System.out.println("se actualiza auto: " + autoActual.getRego() + " desde alquiler sin fecha rego usuario: " + this.usuarioLogueado.getEmail());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            autoEJB.edit(autoActual);
            this.alquilerActual.setAuto(autoActual);
        } else {
            autoEJB.create(autoActual);
            this.alquilerActual.setAuto(autoActual);
        }
        this.alquilerActual.setAuto(autoActual);
        try {
            if (this.alquilerActual.getKms_out() == 0) {
                long kmsOut = Long.parseLong(autoActual.getKilometraje());
                this.alquilerActual.setKms_out(kmsOut);
            }
        } catch (Exception e) {

        }

        this.alquilerActual.setRego(autoActual.getRego());

        /*if (this.alquilerActual.getTipo_rego() != 0 && this.alquilerActual.getFecha_revision() == null) {
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
            Date hoy = c.getTime();
            Date fechaRevision = null;
            if (this.alquilerActual.getTipo_rego() == Alquiler.REGO_ESTABLECIDO) {
                c.add(Calendar.DAY_OF_YEAR, 365);
                fechaRevision = c.getTime();
            } else if (this.alquilerActual.getTipo_rego() == Alquiler.REGO_IMPORTADO) {
                c.add(Calendar.DAY_OF_YEAR, 183);
                fechaRevision = c.getTime();
            } else if (this.alquilerActual.getTipo_rego() == Alquiler.REGO_PREVIO) {
                if (this.autoActual.getRego() != null && this.autoActual.getFecha_vencimiento_rego() != null) {
                    fechaRevision = this.autoActual.getFecha_vencimiento_rego();
                }
            }
            this.alquilerActual.setFecha_revision(fechaRevision);
            if (fechaRevision != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println("Fecha revision: " + sdf.format(fechaRevision));
            }
        }*/
        alquilerEJB.edit(alquilerActual);

    }

    public String getTAB_AUTO() {
        return TAB_AUTO;
    }

    public void setTAB_AUTO(String TAB_AUTO) {
        this.TAB_AUTO = TAB_AUTO;
    }

    public String getTAB_CLIENTE() {
        return TAB_CLIENTE;
    }

    public void setTAB_CLIENTE(String TAB_CLIENTE) {
        this.TAB_CLIENTE = TAB_CLIENTE;
    }

    public String getTAB_BOOKING_DETAIL() {
        return TAB_BOOKING_DETAIL;
    }

    public void setTAB_BOOKING_DETAIL(String TAB_BOOKING_DETAIL) {
        this.TAB_BOOKING_DETAIL = TAB_BOOKING_DETAIL;
    }

    public String getTAB_FEE() {
        return TAB_FEE;
    }

    public void setTAB_FEE(String TAB_FEE) {
        this.TAB_FEE = TAB_FEE;
    }

    public String getTAB_PAYMENTS() {
        return TAB_PAYMENTS;
    }

    public void setTAB_PAYMENTS(String TAB_PAYMENTS) {
        this.TAB_PAYMENTS = TAB_PAYMENTS;
    }

    public String getTAB_DOCUMENTS() {
        return TAB_DOCUMENTS;
    }

    public void setTAB_DOCUMENTS(String TAB_DOCUMENTS) {
        this.TAB_DOCUMENTS = TAB_DOCUMENTS;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    private boolean validarBookingDetails() {
        if (this.alquilerActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The rent is null"));
            return false;
        }

        if (this.alquilerActual.getFecha_inicio() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to define a start date"));
            return false;
        }

        if (this.alquilerActual.getFecha_fin() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to define a end date"));
            return false;
        }

        if (this.alquilerActual.getFecha_inicio().after(this.alquilerActual.getFecha_fin())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The start date cannot be later than the end date"));
            return false;
        }

        int dias = (int) ((this.alquilerActual.getFecha_fin().getTime() - this.alquilerActual.getFecha_inicio().getTime()) / 86400000);
        if (dias < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "At least it must be 1 day of rental"));
            return false;
        }

        String horaInicioIngresado = this.alquilerActual.getHora_inicio();
        String horaInicioStr = horaInicioIngresado.substring(0, 2);
        int horaInicioInt = Integer.parseInt(horaInicioStr);
        if (horaInicioInt < 0 || horaInicioInt > 23) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Pickup time with incorrect format"));
            return false;
        } else {
            String minutoInicioStr = horaInicioIngresado.substring(3, 5);
            int minutoInicioInt = Integer.parseInt(minutoInicioStr);
            if (minutoInicioInt < 0 || minutoInicioInt > 59) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Pickup time with incorrect format"));
                return false;
            }
        }

        String horaFinIngresado = this.alquilerActual.getHora_fin();
        String horaFinStr = horaFinIngresado.substring(0, 2);
        int horaFinInt = Integer.parseInt(horaFinStr);
        if (horaFinInt < 0 || horaFinInt > 23) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Drop off time with incorrect format"));
            return false;
        } else {
            String minutoFinStr = horaFinIngresado.substring(3, 5);
            int minutoFinInt = Integer.parseInt(minutoFinStr);
            if (minutoFinInt < 0 || minutoFinInt > 59) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Drop off time with incorrect format"));
                return false;
            }
        }

        if (this.kmsInStr.trim().length() > 0) {
            long kmsInLong = 0;
            try {
                kmsInLong = Long.parseLong(this.kmsInStr);
                this.alquilerActual.setKms_in(kmsInLong);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The kms in has to be just numbers"));
                return false;
            }
        }

        if (this.kmsOutStr.trim().length() > 0) {
            long kmsOutLong = 0;
            try {
                kmsOutLong = Long.parseLong(this.kmsOutStr);
                this.alquilerActual.setKms_out(kmsOutLong);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The kms out has to be just numbers"));
                return false;
            }
        }

        if (this.alquilerActual.getAuto() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to define the car before defining the rental start dates"));
            return false;
        }

        if (this.alquilerActual.getEstado() == Alquiler.ESTADO_CANCELADO || this.alquilerActual.getEstado() == Alquiler.ESTADO_REPO || this.alquilerActual.getEstado() == Alquiler.ESTADO_RETORNADO) {
            if (this.kmsInStr.trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the current km of the car"));
                return false;
            }
            if (this.alquilerActual.getKms_in() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the current km of the car"));
                return false;
            }
            if (this.alquilerActual.getKms_in() < this.alquilerActual.getKms_out()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "                The number of kms with which the car enters cannot be less than the number of km when it left"));
                return false;
            }
        }

        List<Alquiler> listaAlquileresPospuestos = new ArrayList();
        if (this.alquilerActual.getEstado() == Alquiler.ESTADO_ALQUILADO || this.alquilerActual.getEstado() == Alquiler.ESTADO_CARGANDO || this.alquilerActual.getEstado() == Alquiler.ESTADO_RESERVA) {
            try {
                listaAlquileresPospuestos = alquilerEJB.getAlquileresSuperpuestos(this.alquilerActual.getFecha_inicio(), this.alquilerActual.getFecha_fin(), this.alquilerActual.getAuto().getId(), this.alquilerActual.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listaAlquileresPospuestos.size() > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There are rentals for the same car with the dates superimposed"));
                this.alquilerActual = alquilerEJB.getAlquilerPorId(this.alquilerActual.getId());
                return false;
            }
        }

        return true;
    }

    private boolean validarCliente() {
        if (this.clientePrincipal == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The client is null"));
            return false;
        }
        if (this.clientePrincipal.getNombre() == null || this.clientePrincipal.getNombre().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You must enter the name of the client"));
            return false;
        }
        if (this.clientePrincipal.getApellido() == null || this.clientePrincipal.getNombre().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You must enter the last name of the client"));
            return false;
        }

        if (this.clientePrincipal.getEmail() == null || this.clientePrincipal.getEmail().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You must enter en email"));
            return false;
        }

        Cliente client = clienteEJB.buscarPorNombreYApellido(this.clientePrincipal.getNombre(), this.clientePrincipal.getApellido());
        if ((client != null && this.clientePrincipal.getId() == -1) || (client != null && this.clientePrincipal.getId() > -1 && this.clientePrincipal.getId() != client.getId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "we load the existing client in BD with the name and surname entered"));
            this.setClientePrincipal(this.getClienteClonado(client));
            return false;
        }

        Cliente clientNombreApellido = clienteEJB.buscarPorNombreYApellidoTodos(this.clientePrincipal.getNombre(), this.clientePrincipal.getApellido());

        if (clientNombreApellido != null && clientNombreApellido.getId() > -1) {
            Alquiler alquilerAdeudado = this.alquilerEJB.getAlquilerClienteConDeuda(clientNombreApellido.getId());
            if (alquilerAdeudado != null && this.alquilerActual != null && this.alquilerActual.getId() != alquilerAdeudado.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The client owes the contract " + alquilerAdeudado.getId() + " in the amount of: $" + alquilerAdeudado.getDeuda()));
                return false;
            }
        }

        if (this.mostrarDriverAdicional) {
            if (this.clienteAdicional.getNombre() == null || this.clienteAdicional.getNombre().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You must enter the last name of the client"));
                return false;
            }
            if (this.clienteAdicional.getApellido() == null || this.clienteAdicional.getApellido().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You must enter the  name of the client"));
                return false;
            }
            if (this.clienteAdicional.getEmail() == null || this.clienteAdicional.getEmail().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You must enter en email of additional driver"));
                return false;
            }
            client = clienteEJB.buscarPorNombreYApellido(this.clienteAdicional.getNombre(), this.clienteAdicional.getApellido());
            if ((client != null && this.clienteAdicional.getId() == -1) || (client != null && this.clienteAdicional.getId() > -1 && this.clienteAdicional.getId() != client.getId())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "we load the existing client in BD with the name and surname entered as additional client"));
                this.setClienteAdicional(this.getClienteClonado(client));
                return false;
            }
        }
        return true;
    }

    private boolean validarAuto() {
        if (this.autoActual == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The car is null"));
            return false;
        }

        if (this.autoActual.getRego() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The rego is null"));
            return false;
        }

        if (this.autoActual.getRego() == null || this.autoActual.getRego().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a rego"));
            return false;
        }

        Auto autoRegoBD = autoEJB.buscarPorRego(this.autoActual.getRego());
        if ((autoRegoBD != null && this.autoActual.getId() == -1) || (autoRegoBD != null && this.autoActual.getId() > -1 && this.autoActual.getId() != autoRegoBD.getId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another car with the Rego entered"));
            return false;
        }

        if (this.autoActual.getFecha_vencimiento_rego() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the expiration date of the rego"));
            return false;
        }

        if (this.autoActual.getDrivetrain() != null && this.autoActual.getDrivetrain().getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a type of Drivetrain"));
            return false;
        }

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        if (this.autoActual.getFecha_vencimiento_rego().before(c.getTime()) && this.alquilerActual.getEstado() != Alquiler.ESTADO_CANCELADO && this.alquilerActual.getEstado() != Alquiler.ESTADO_REPO && this.alquilerActual.getEstado() != Alquiler.ESTADO_RETORNADO && this.alquilerActual.getEstado() != Alquiler.ESTADO_VENDIDO) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You cannot rent a car with expired rego"));
            return false;
        }

        if (this.autoActual.getVIN() == null || this.autoActual.getVIN().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You must enter the VIN"));
            return false;
        }

        Auto autoBDVIn = autoEJB.buscarPorVin(this.autoActual.getVIN().trim());
        if ((autoBDVIn != null && this.autoActual.getId() == -1) || (autoBDVIn != null && this.autoActual.getId() > -1 && this.autoActual.getId() != autoBDVIn.getId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another car with the same VIN"));
            return false;
        }

        if (this.autoActual.getStock() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a stock number"));
            return false;
        }

        if (this.autoActual.getMarca() == null || this.autoActual.getMarca().getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a brand"));
            return false;
        }

        if (this.autoActual.getModelo() == null || this.autoActual.getModelo().getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a model"));
            return false;
        }

        List<Modelo> listaModeloMarca = this.modeloEJB.buscarPorMarca(this.autoActual.getMarca().getId());
        boolean modeloMarca = false;
        for (int k = 0; k < listaModeloMarca.size(); k++) {
            Modelo modeloMarcaSupu = listaModeloMarca.get(k);
            if (modeloMarcaSupu.getId() == this.autoActual.getModelo().getId()) {
                modeloMarca = true;
            }
        }
        if (modeloMarca == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a model"));
            return false;
        }

        Auto autoBDStock = autoEJB.buscarPorStock(this.autoActual.getStock());
        if ((autoBDStock != null && this.autoActual.getId() == -1) || (autoBDStock != null && this.autoActual.getId() > -1 && this.autoActual.getId() != autoBDStock.getId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another car with the same stock number"));
            return false;
        }
        return true;
    }

    public Cliente getClientePrincipal() {
        return clientePrincipal;
    }

    public void setClientePrincipal(Cliente clientePrincipal) {
        this.clientePrincipal = clientePrincipal;
    }

    public boolean isMostrarDriverAdicional() {
        return mostrarDriverAdicional;
    }

    public void setMostrarDriverAdicional(boolean mostrarDriverAdicional) {
        this.mostrarDriverAdicional = mostrarDriverAdicional;
    }

    public List<Cliente> getClientesAdicionales() {
        return clientesAdicionales;
    }

    public void setClientesAdicionales(List<Cliente> clientesAdicionales) {
        this.clientesAdicionales = clientesAdicionales;
    }

    public Cliente getClienteAdicionalSeleccionado() {
        return clienteAdicionalSeleccionado;
    }

    public void setClienteAdicionalSeleccionado(Cliente clienteAdicionalSeleccionado) {
        this.clienteAdicionalSeleccionado = clienteAdicionalSeleccionado;
    }

    public Cliente getClienteAdicional() {
        return clienteAdicional;
    }

    public void setClienteAdicional(Cliente clienteAdicional) {
        this.clienteAdicional = clienteAdicional;
    }

    public void verClienteAdicionalSeleccionado(Cliente cli) {
        this.setClienteAdicionalSeleccionado(cli);
        Cliente clienteAdicSelecc = this.getClienteAdicionalSeleccionado();
        this.setClienteAdicional(clienteAdicSelecc);
        List<Cliente> clientesAdicionalActual = this.getClientesAdicionales();
        List<Cliente> clienteResultado = new ArrayList<Cliente>();
        for (int a = 0; a < clientesAdicionalActual.size(); a++) {
            Cliente clienteAdic = clientesAdicionalActual.get(a);
            if (clienteAdic.getId() != clienteAdicSelecc.getId()) {
                clienteResultado.add(clienteAdic);
            }
        }
        this.setClientesAdicionales(clienteResultado);
        this.setMostrarDriverAdicional(true);
    }

    public void eliminarClienteAdicional(Cliente cli) {
        this.setClienteAdicionalSeleccionado(cli);
        Cliente clienteAdicSelecc = this.getClienteAdicionalSeleccionado();
        List<Cliente> clientesAdicionalActual = this.getClientesAdicionales();
        List<Cliente> clienteResultado = new ArrayList<Cliente>();
        for (int a = 0; a < clientesAdicionalActual.size(); a++) {
            Cliente clienteAdic = clientesAdicionalActual.get(a);
            if (clienteAdic.getId() != clienteAdicSelecc.getId()) {
                clienteResultado.add(clienteAdic);
            }
        }
        this.setClientesAdicionales(clienteResultado);

    }

    public String getDescripcionLargaAuto() {
        this.descripcionLargaAuto = "";
        if (this.alquilerActual.getAuto() != null) {
            Auto auto = this.alquilerActual.getAuto();
            auto = autoEJB.buscarPorId(this.alquilerActual.getAuto().getId());

            if (auto.getMarca() != null) {
                this.descripcionLargaAuto = this.descripcionLargaAuto + " " + auto.getMarca().getNombre();
            }
            if (auto.getModelo() != null) {
                this.descripcionLargaAuto = this.descripcionLargaAuto + " - " + auto.getModelo().getNombre();
            }

            if (auto.getRego() != null) {
                this.descripcionLargaAuto = this.descripcionLargaAuto + " - " + auto.getRego();
            }

        }
        return this.descripcionLargaAuto;
    }

    public void setDescripcionLargaAuto(String descripcionLargaAuto) {
        this.descripcionLargaAuto = descripcionLargaAuto;
    }

    public void agragarClienteAdicional() {
        boolean loggedIn = true;
        if (clienteAdicional != null) {
            this.clientesAdicionales.add(clienteAdicional);
            this.setMostrarDriverAdicional(false);
            this.setClienteAdicional(new Cliente());
        }
    }

    public void cancelarClienteAdicional() {
        this.setClienteAdicional(new Cliente());
        this.setMostrarDriverAdicional(false);
    }

    public List<Source> getListaSource() {
        return listaSource;
    }

    public void setListaSource(List<Source> listaSource) {
        this.listaSource = listaSource;
    }

    public String getKmsInStr() {
        return kmsInStr;
    }

    public void setKmsInStr(String kmsInStr) {
        this.kmsInStr = kmsInStr;
    }

    public String getKmsOutStr() {
        return kmsOutStr;
    }

    public void setKmsOutStr(String kmsOutStr) {
        this.kmsOutStr = kmsOutStr;
    }

    public List<Alquiler_Impuesto> getListaAlqImp() {
        return listaAlqImp;
    }

    public void setListaAlqImp(List<Alquiler_Impuesto> listaAlqImp) {
        this.listaAlqImp = listaAlqImp;
    }

    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equals("auto") && event.getNewStep().equals("cliente")) {
            if (validarAuto()) {
                registrarAuto();
            } else {
                return event.getOldStep();
            }
        } else if (event.getOldStep().equals("cliente") && event.getNewStep().equals("booking")) {
            if (validarCliente()) {
                registrarCliente();
            } else {
                return event.getOldStep();
            }
        } else if (event.getOldStep().equals("booking") && event.getNewStep().equals("fee")) {
            if (validarBookingDetails()) {
                registrarBookingDetails();
                this.cargarImpuestos();
                this.actualizarMontoExtras();
                this.actualizarTotal();
            } else {
                return event.getOldStep();
            }
        } else if (event.getOldStep().equals("fee") && event.getNewStep().equals("payments")) {
            registrarImpuestos();
        } else if (event.getOldStep().equals("payments") && event.getNewStep().equals("documents")) {
            alquilerTarjetaEJB.eliminarTarjetasDeAlquiler(this.alquilerActual.getId());
            alquilerTarjetaEJB.agregarTodaAlquilerTarjetas(this.tarjetasAlquiler);
        }

        return event.getNewStep();

    }

    private void cargarImpuestos() {
        try {
            listaAlqImp = alquilerImpuestoEJB.buscarImpuestosAlquiler(this.alquilerActual.getId());
            if (this.autoActual != null && this.autoActual.getSucursal() != null) {
                listaImpuestos = impuestoEJB.buscarImpuestosAuto(this.autoActual.getSucursal().getId());
            } else {
                listaImpuestos = impuestoEJB.findAll();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (int a = 0; a < listaImpuestos.size(); a++) {
            Impuesto impACargar = listaImpuestos.get(a);
            boolean yaEsta = false;
            for (int b = 0; b < listaAlqImp.size(); b++) {
                Alquiler_Impuesto alqImpCargado = listaAlqImp.get(b);
                if (alqImpCargado.getImpuesto().getId() == impACargar.getId()) {
                    yaEsta = true;
                }
            }
            if (!yaEsta) {
                Alquiler_Impuesto alqImp = new Alquiler_Impuesto();
                alqImp.setAlquiler(this.alquilerActual);
                alqImp.setImpuesto(impACargar);
                alqImp.setSeleccionado(impACargar.isObligatorio());
                alqImp.setPrecio(impACargar.getPrice());
                alqImp.setCantidad(1);
                if (alqImp.isSeleccionado()) {
                    alqImp.setSubtotal(impACargar.getPrice());
                } else {
                    alqImp.setSubtotal(0);
                }
                listaAlqImp.add(alqImp);
            }
        }
    }

    public void actualizarMontoExtras() {
        float totalExtras = 0;
        for (int a = 0; a < this.listaAlqImp.size(); a++) {
            Alquiler_Impuesto alqImp = this.listaAlqImp.get(a);
            if (alqImp.isSeleccionado()) {
                totalExtras = totalExtras + (((float) alqImp.getCantidad()) * alqImp.getPrecio());
            }
        }
        int totalExtrasInt = (int) (totalExtras * 100);
        totalExtras = ((float) totalExtrasInt) / 100;
        this.alquilerActual.setExtra(totalExtras);

        float granTotal = this.alquilerActual.getTotal() + totalExtras;
        int granTotalInt = (int) (granTotal * 100);
        granTotal = ((float) granTotalInt) / 100;
        this.alquilerActual.setGran_total(granTotal);

    }

    public void nuevoExpense() {
        this.mostrarExpensesDatos = true;
    }

    private void registrarImpuestos() {
        List<Alquiler_Impuesto> listaDefinitiva = new ArrayList<>();
        // alquilerImpuestoEJB.eliminarPorAlquiler(this.alquilerActual.getId());
        listaImpuestoSeleccAlq = new ArrayList<Alquiler_Impuesto>();
        for (int j = 0; j < this.listaAlqImp.size(); j++) {
            Alquiler_Impuesto alqImpuesto = this.listaAlqImp.get(j);
            if (alqImpuesto.isSeleccionado()) {
                listaImpuestoSeleccAlq.add(alqImpuesto);
                float subtotal = alqImpuesto.getCantidad() * alqImpuesto.getPrecio();
                int subtotalInt = (int) (subtotal * 100);
                subtotal = ((float) subtotalInt) / 100;
                alqImpuesto.setSubtotal(subtotal);
                listaDefinitiva.add(alqImpuesto);
                //   alquilerImpuestoEJB.create(alqImpuesto);
            }
        }
        alquilerImpuestoEJB.saveListaImpuestoDefinitiva(listaDefinitiva, this.alquilerActual.getId());
        this.actualizarDeuda();
        alquilerEJB.edit(this.alquilerActual);
    }

    public void cargarAlquileresDelCliente() {
        List<Alquiler_Cliente> listaAlquileresCliente = this.alquilerClienteEJB.buscarAlquileresDeCliente(this.clientePrincipal.getId());
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

    private void cargarImpuestosDeAlquilerActual() {
        if (this.alquilerActual != null && this.alquilerActual.getId() > -1) {
            listaImpuestoSeleccAlq = alquilerImpuestoEJB.buscarImpuestosAlquiler(this.alquilerActual.getId());
            //tambien actualizamos la pantalla anterior, por si el usuario vuelve para atras...
            this.cargarImpuestos();
        }
    }

    public String getPickupDateStr() {
        Date inicio = this.alquilerActual.getFecha_inicio();
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy", new Locale("AU"));
        pickupDateStr = sdf.format(inicio);
        return pickupDateStr + " " + this.alquilerActual.getHora_inicio() + " - " + this.alquilerActual.getSucursal_origen().getNombre();
    }

    public void setPickupDateStr(String pickupDateStr) {
        this.pickupDateStr = pickupDateStr;
    }

    public String getDropoffDateStr() {
        Date fin = this.alquilerActual.getFecha_fin();
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy", new Locale("AU"));
        dropoffDateStr = sdf.format(fin);
        return dropoffDateStr + " " + this.alquilerActual.getHora_fin() + " - " + this.alquilerActual.getSucursal_destino().getNombre();
    }

    public void setDropoffDateStr(String dropoffDateStr) {
        this.dropoffDateStr = dropoffDateStr;
    }

    public String getVehiculoStr() {
        this.vehiculoStr = "";
        if (this.alquilerActual.getAuto() != null) {
            Auto auto = this.alquilerActual.getAuto();
            auto = autoEJB.buscarPorId(this.alquilerActual.getAuto().getId());
            if (auto != null) {
                if (auto.getMarca() != null && auto.getModelo() != null) {
                    vehiculoStr = vehiculoStr + this.alquilerActual.getAuto().getMarca().getNombre() + " " + this.alquilerActual.getAuto().getModelo().getNombre();
                }
                if (this.alquilerActual.getAuto().getRego() != null) {
                    vehiculoStr = vehiculoStr + " - " + this.alquilerActual.getAuto().getRego();
                }
            }
        }

        return vehiculoStr;
    }

    public void setVehiculoStr(String vehiculoStr) {
        this.vehiculoStr = vehiculoStr;
    }

    public List<Pago> getListaPagoAlquiler() {
        return listaPagoAlquiler;
    }

    public void setListaPagoAlquiler(List<Pago> listaPagoAlquiler) {
        this.listaPagoAlquiler = listaPagoAlquiler;
    }

    public List<Alquiler_Impuesto> getListaImpuestoSeleccAlq() {
        return listaImpuestoSeleccAlq;
    }

    public void setListaImpuestoSeleccAlq(List<Alquiler_Impuesto> listaImpuestoSeleccAlq) {
        this.listaImpuestoSeleccAlq = listaImpuestoSeleccAlq;
    }

    public boolean isMostrarPago() {
        return mostrarPago;
    }

    public void setMostrarPago(boolean mostrarPago) {
        this.mostrarPago = mostrarPago;
        if (mostrarPago) {
            this.mostrarTarjeta = false;
            this.mostrarTarjetasCredito = false;
        }

    }

    public boolean isMostrarTarjeta() {
        return mostrarTarjeta;
    }

    public void setMostrarTarjeta(boolean mostrarTarjeta) {
        this.mostrarTarjeta = mostrarTarjeta;
        if (mostrarTarjeta) {
            this.mostrarTarjetasCredito = true;
            this.mostrarPago = false;
        }
    }

    public boolean isMostrarTarjetasCredito() {
        return mostrarTarjetasCredito;
    }

    public boolean isOcultarTarjetasCredito() {
        return !mostrarTarjetasCredito;
    }

    public void setOcultarTarjetasCredito(boolean ocultarTarjetasCredito) {
        this.ocultarTarjetasCredito = ocultarTarjetasCredito;
    }

    public void setMostrarTarjetasCredito(boolean mostrarTarjetasCredito) {
        this.mostrarTarjetasCredito = mostrarTarjetasCredito;
        if (this.mostrarTarjetasCredito) {
            this.setMostrarPago(false);
        }
    }

    public void guardarPago() {
        if (this.pagoActual != null && this.alquilerActual != null && this.alquilerActual.getId() > -1 && this.usuarioLogueado != null) {
            this.pagoActual.setAlquiler(this.alquilerActual);
            this.pagoActual.setUsuario(usuarioLogueado);

            float pago = this.pagoActual.getMonto();

            int pagoInt = (int) (pago * 100);
            pago = ((float) pagoInt) / 100;

            this.pagoActual.setMonto(pago);
            if (this.pagoActual.getMonto() != this.alquilerActual.getRate_per_day()) {
                if (this.alquilerActual.isFortnightly()) {
                    float montoQuincental = this.alquilerActual.getRate_per_day() * 2;
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
            pagoEJB.create(pagoActual);

            float deuda = this.alquilerActual.getDeuda();
            deuda = deuda - pago;

            int deudaInt = (int) (deuda * 100);
            deuda = ((float) (deudaInt)) / 100;

            this.alquilerActual.setDeuda(deuda);
            alquilerEJB.edit(this.alquilerActual);

            /*if (this.pagoActual.getTipoPago().getId() == Pago.ID_TIPO_LATE_PAYMENT) {
                Impuesto impuestoLatePayment = impuestoEJB.getImpuestoParaLatePayment();
                if (impuestoLatePayment != null) {

                    boolean encontrado = false;
                    float totalExtras = 0;
                    float sumarADeuda = 0;

                    List<Alquiler_Impuesto> listaImpuestos = alquilerImpuestoEJB.buscarImpuestosAlquiler(this.alquilerActual.getId());

                    for (int j = 0; j < listaImpuestos.size(); j++) {
                        Alquiler_Impuesto impuestoAlquiler = listaImpuestos.get(j);
                        if (impuestoAlquiler.getImpuesto().getTipo_asociado() > -1 && impuestoAlquiler.getImpuesto().getTipo_asociado() == Impuesto.TIPO_ASOCIADO_LATE_PAYMENT) {
                            encontrado = true;
                            impuestoAlquiler.setCantidad(impuestoAlquiler.getCantidad() + 1);
                            float subtotal = impuestoAlquiler.getCantidad() * impuestoAlquiler.getPrecio();
                            int subtotalInt = (int) (subtotal * 100);
                            subtotal = ((float) subtotalInt) / 100;
                            impuestoAlquiler.setSubtotal(subtotal);
                            alquilerImpuestoEJB.edit(impuestoAlquiler);
                            sumarADeuda = impuestoAlquiler.getPrecio();
                        }
                        totalExtras = totalExtras + (((float) impuestoAlquiler.getCantidad()) * impuestoAlquiler.getPrecio());
                    }
                    if (!encontrado) {
                        Alquiler_Impuesto impuestoAcrear = new Alquiler_Impuesto();
                        impuestoAcrear.setAlquiler(this.alquilerActual);
                        impuestoAcrear.setImpuesto(impuestoLatePayment);
                        impuestoAcrear.setCantidad(1);
                        impuestoAcrear.setPrecio(impuestoLatePayment.getPrice());
                        impuestoAcrear.setSubtotal(impuestoLatePayment.getPrice());
                        impuestoAcrear.setSeleccionado(true);
                        alquilerImpuestoEJB.create(impuestoAcrear);
                        totalExtras = totalExtras + impuestoAcrear.getSubtotal();
                        sumarADeuda = impuestoAcrear.getPrecio();
                    }
                    int totalExtrasInt = (int) (totalExtras * 100);
                    totalExtras = ((float) totalExtrasInt) / 100;
                    this.alquilerActual.setExtra(totalExtras);

                    float granTotal = this.alquilerActual.getTotal() + totalExtras;
                    int granTotalInt = (int) (granTotal * 100);
                    granTotal = ((float) granTotalInt) / 100;
                    this.alquilerActual.setGran_total(granTotal);

                    float deudaTotal = this.alquilerActual.getDeuda() + sumarADeuda;
                    int deudaTotalInt = (int) (deudaTotal * 100);
                    deudaTotal = ((float) deudaTotalInt) / 100;
                    this.alquilerActual.setDeuda(deudaTotal);

                    alquilerEJB.edit(alquilerActual);
                    this.cargarImpuestosDeAlquilerActual();
                }
            }*/
            this.setMostrarPago(false);
            this.listaPagoAlquiler = pagoEJB.buscarAprovadosPorAlquiler(this.alquilerActual.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successful registration"));
            this.pagoActual = new Pago();
            Calendar c = Calendar.getInstance();
            this.pagoActual.setFecha_hora(c.getTime());
        }
    }

    public void cancelarPago() {
        this.pagoActual = new Pago();
        this.setMostrarPago(false);
    }

    public void guardarTarjeta() {
        Calendar c = Calendar.getInstance();
        Date hoy = c.getTime();
        if (this.alquilerActual != null && this.tarjetaActual != null && this.usuarioLogueado != null) {

            if (this.tarjetaActual.getFechaVenc().before(hoy)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The expiration date cannot be a past date"));
                return;
            }
            Tarjeta tarjetaExiste = tarjetaEJB.buscarPorNumeroNombre(this.tarjetaActual.getNumero(), this.tarjetaActual.getNombre());
            Alquiler_Tarjeta alquilerTarjetaAgregada = null;
            if (tarjetaExiste == null) {
                tarjetaEJB.create(this.tarjetaActual);
            } else {
                tarjetaEJB.edit(this.tarjetaActual);
            }
            tarjetaExiste = tarjetaEJB.buscarPorNumeroNombre(this.tarjetaActual.getNumero(), this.tarjetaActual.getNombre());
            if (tarjetaExiste != null) {
                boolean tarjetaExisteEnGrilla = false;
                for (int u = 0; u < this.tarjetasAlquiler.size(); u++) {
                    Alquiler_Tarjeta alquilerTarjetaCargada = this.tarjetasAlquiler.get(u);
                    if (alquilerTarjetaCargada.getTarjeta().getNombre().equals(tarjetaExiste.getNombre()) && alquilerTarjetaCargada.getTarjeta().getNumero().equals(tarjetaExiste.getNumero())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The card already exists in the rental"));
                        return;
                    }
                }
                Alquiler_Tarjeta alquilerTarjetaCrear = new Alquiler_Tarjeta();
                alquilerTarjetaCrear.setAlquiler(this.alquilerActual);
                alquilerTarjetaCrear.setTarjeta(tarjetaExiste);
                this.tarjetasAlquiler.add(alquilerTarjetaCrear);
            }
        }
        this.tarjetaActual = new Tarjeta();
        this.tarjetaActual.setFechaVenc(hoy);
        this.setMostrarTarjeta(false);
    }

    public void cancelarTarjeta() {
        this.tarjetaActual = new Tarjeta();
        this.setMostrarTarjeta(false);
    }

    public List<Tipo_Tarjeta> getTiposTarjeta() {
        return tiposTarjeta;
    }

    public void setTiposTarjeta(List<Tipo_Tarjeta> tiposTarjeta) {
        this.tiposTarjeta = tiposTarjeta;
    }

    public void editTarjeta() {
        Alquiler_Tarjeta tarjetaSeleccionada = null;
        if (this.tarjetasSeleccionada != null) {
            tarjetaSeleccionada = this.tarjetasSeleccionada;
            this.tarjetasAlquiler.remove(tarjetaSeleccionada);
            this.setTarjetaActual(tarjetaSeleccionada.getTarjeta());
        }
    }

    public void eliminarTarjeta() {
        Alquiler_Tarjeta tarjetaSeleccionada = null;
        if (this.tarjetasSeleccionada != null) {
            tarjetaSeleccionada = this.tarjetasSeleccionada;
            this.tarjetasAlquiler.remove(tarjetaSeleccionada);

        }
    }

    public Alquiler_Tarjeta getTarjetasSeleccionada() {
        return tarjetasSeleccionada;
    }

    public void setTarjetasSeleccionada(Alquiler_Tarjeta tarjetasSeleccionada) {
        this.tarjetasSeleccionada = tarjetasSeleccionada;
    }

    public void onRowSelect(SelectEvent event) {
        this.setTarjetasSeleccionada((Alquiler_Tarjeta) event.getObject());
    }

    public void onRowDoubleClick(SelectEvent event) {

        Alquiler_Tarjeta tarjetaSeleccionada = (Alquiler_Tarjeta) event.getObject();
        if (tarjetaSeleccionada != null) {
            this.tarjetasAlquiler.remove(tarjetaSeleccionada);
            this.setTarjetaActual(tarjetaSeleccionada.getTarjeta());
            this.setMostrarTarjeta(true);
        }
    }

    public Pago getPagoActual() {
        return pagoActual;
    }

    public void setPagoActual(Pago pagoActual) {
        this.pagoActual = pagoActual;
    }

    public Tarjeta getTarjetaActual() {
        return tarjetaActual;
    }

    public void setTarjetaActual(Tarjeta tarjetaActual) {
        this.tarjetaActual = tarjetaActual;
    }

    public List<Alquiler_Tarjeta> getTarjetasAlquiler() {
        return tarjetasAlquiler;
    }

    public void setTarjetasAlquiler(List<Alquiler_Tarjeta> tarjetasAlquiler) {
        this.tarjetasAlquiler = tarjetasAlquiler;
    }

    public void generateReportConr() {

        RentalAgreement rental = new RentalAgreement();
        /* GET DATA FROM QUERY HERE*/
        rental.setRental_agreement_id(343l);
        rental.setHirer_name("Gabriel Grillo");
        rental.setDob("1986-08-09");
        rental.setLicence_no("321321");
        rental.setIssued_in(10);
        rental.setExpiry_date("2024-10-16");
        rental.setAddress("10 / 20 Monomeeth");
        rental.setPhone("0400913385");
        rental.setMobile("");
        rental.setEmail("gabigrillo46@gmail.com");
        rental.setMake("Volkswagen");
        rental.setModel("Golf");
        rental.setLicence_plate("kam013");
        rental.setKms_out(15564d);
        rental.setKms_in(0d);
        rental.setFuel_type("Diesel");
        rental.setFuel_out(0);
        rental.setFuel_in(0);
        rental.setStart_date_time("2020-10-26 09:00");
        rental.setPickup_location("Bexley");
        rental.setReturn_date_time("2021-10-05 09:00");
        rental.setReturn_location("Bexley");
        rental.setRental_fees_days(344);
        rental.setRental_fees_rate(1d);
        rental.setRental_fees_total(49d);
        rental.setFirst_payment_qty(1d);
        rental.setFirst_payment_rate(1420d);
        rental.setFirst_payment_total(1420d);
        rental.setTotal(1469d);
        rental.setBalance_due(62d);

        this.alquilerActual = this.alquilerEJB.getAlquilerPorId(this.alquilerActual.getId());
        this.clientePrincipal = this.getClienteClonado(this.clienteEJB.buscarPorID(this.clientePrincipal.getId()));
        if (this.clienteAdicional != null && this.clienteAdicional.getId() > -1) {
            this.clienteAdicional = this.getClienteClonado(this.clienteEJB.buscarPorID(this.clienteAdicional.getId()));
        }
        this.autoActual = this.getAutoClonado(this.autoEJB.buscarPorId(this.autoActual.getId()));

        List<PaymentsDetailsDTO> paymentDetails = new ArrayList<PaymentsDetailsDTO>();
        /* GET DATA FROM QUERY HERE*/

        for (int a = 0; a < this.listaPagoAlquiler.size(); a++) {
            Pago pagoAlq = this.listaPagoAlquiler.get(a);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String descripcion = sdf.format(pagoAlq.getFecha_hora()) + " Paid by " + pagoAlq.getTipoPago().getNombre();
            if (pagoAlq.getUsuario() != null && pagoAlq.getUsuario() != null) {
                descripcion = descripcion + " ( " + pagoAlq.getUsuario().getNombre() + " " + pagoAlq.getUsuario().getApellido() + " )";
            }
            PaymentsDetailsDTO p1 = new PaymentsDetailsDTO();
            p1.setDetallePago(descripcion);
            p1.setMontoPago(pagoAlq.getMonto());
            paymentDetails.add(p1);

        }
        List<ImpuestoDetailsDTO> impuestosDetails = new ArrayList<ImpuestoDetailsDTO>();

        if (this.alquilerActual != null) {
            ImpuestoDetailsDTO imp1 = new ImpuestoDetailsDTO();
            imp1.setNombre_impuesto("Rental Fees");
            int cantidadtiempo = 0;
            if (this.alquilerActual.isDiario()) {
                cantidadtiempo = this.alquilerActual.getDias();
            } else {
                cantidadtiempo = this.alquilerActual.getSemanas();
            }
            imp1.setSemanas(cantidadtiempo + "");
            imp1.setSubtotal(this.alquilerActual.getRate_per_day());
            imp1.setTotal(this.alquilerActual.getTotal());
            impuestosDetails.add(imp1);
        }
        for (int a = 0; a < this.listaImpuestoSeleccAlq.size(); a++) {
            Alquiler_Impuesto alquilerImp = this.listaImpuestoSeleccAlq.get(a);
            if (alquilerImp.getImpuesto() != null) {
                ImpuestoDetailsDTO imp1 = new ImpuestoDetailsDTO();
                imp1.setNombre_impuesto(alquilerImp.getImpuesto().getNombre());
                imp1.setCantidad(alquilerImp.getCantidad() + "");
                imp1.setSubtotal(alquilerImp.getPrecio());
                imp1.setTotal(alquilerImp.getSubtotal());
                impuestosDetails.add(imp1);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // PUT PARAMETERS (WARNING, THIS IS REALLY NECESARY)
        Map<String, Object> params = new HashMap<>();
        if (this.alquilerActual != null) {
            params.put("rental_agreement_id", this.alquilerActual.getId());
        } else {
            params.put("rental_agreement_id", -1);
        }
        if (this.clientePrincipal != null) {
            String nombre = this.clientePrincipal.getNombre() + " " + this.clientePrincipal.getApellido();
            if (this.mostrarDriverAdicional && this.clienteAdicional.getId() > -1) {
                nombre = nombre + " Additional: " + this.clienteAdicional.getNombre() + " " + this.clienteAdicional.getApellido();
            }
            params.put("hirer_name", nombre);
            if (this.clientePrincipal.getDOB() != null) {
                params.put("dob", sdf.format(this.clientePrincipal.getDOB()));
            } else {
                params.put("dob", "");
            }
            params.put("licence_no", this.clientePrincipal.getLicencia_numero());
            if (this.clientePrincipal.getOtorgadaEN() != null) {
                params.put("issued_in", this.clientePrincipal.getOtorgadaEN());
            } else {
                params.put("issued_in", "");
            }
            if (this.clientePrincipal.getFecha_vencimiento_lic() != null) {
                params.put("expiry_date", sdf.format(this.clientePrincipal.getFecha_vencimiento_lic()));
            } else {
                params.put("expiry_date", "");
            }
            if (this.clientePrincipal.getState() != null) {
                params.put("address", this.clientePrincipal.getDireccion() + " " + this.clientePrincipal.getSuburbio() + " " + this.clientePrincipal.getState().getNombre() + " " + this.clientePrincipal.getCodigo_postal());
            } else {
                params.put("address", "");
            }
            params.put("phone", this.clientePrincipal.getTelefono());
            params.put("mobile", this.clientePrincipal.getMovil());
            params.put("email", this.clientePrincipal.getEmail());

        } else {
            params.put("hirer_name", "");
            params.put("dob", "");
            params.put("licence_no", "");
            params.put("issued_in", "");
            params.put("expiry_date", "");
            params.put("address", "");
            params.put("phone", "");
            params.put("mobile", "");
            params.put("email", "");
        }

        if (this.alquilerActual != null && this.alquilerActual.getAuto() != null && this.alquilerActual.getAuto().getMarca() != null && this.alquilerActual.getAuto().getModelo() != null) {
            params.put("make", this.alquilerActual.getAuto().getMarca().getNombre());
            params.put("model", this.alquilerActual.getAuto().getModelo().getNombre());
            params.put("licence_plate", this.alquilerActual.getRego());
            params.put("kms_out", this.alquilerActual.getKms_out());
            params.put("kms_in", this.alquilerActual.getKms_in());
            if (this.alquilerActual.getAuto().getTipo_combustible() != null) {
                params.put("fuel_type", this.alquilerActual.getAuto().getTipo_combustible().getNombre());
            } else {
                params.put("fuel_type", "");
            }
            params.put("fuel_out", this.getFuelDescription(this.alquilerActual.getFuel_out()));
            params.put("fuel_in", this.getFuelDescription(this.alquilerActual.getFuel_in()));

            String fechaInicio = sdf.format(this.alquilerActual.getFecha_inicio()) + " " + this.alquilerActual.getHora_inicio();
            params.put("start_date_time", fechaInicio);

            if (this.alquilerActual.getSucursal_origen() != null) {
                params.put("pickup_location", this.alquilerActual.getSucursal_origen().getNombre());
            } else {
                params.put("pickup_location", "");
            }
            String fechaFin = sdf.format(this.alquilerActual.getFecha_fin()) + " " + this.alquilerActual.getHora_fin();
            params.put("return_date_time", fechaFin);

            if (this.alquilerActual.getSucursal_destino() != null) {
                params.put("return_location", this.alquilerActual.getSucursal_destino().getNombre());
            } else {
                params.put("return_location", "");
            }
            params.put("balance_due", this.alquilerActual.getDeuda());

        } else {
            params.put("make", "");
            params.put("model", "");
            params.put("licence_plate", "");
            params.put("kms_out", "");
            params.put("kms_in", "");
            params.put("fuel_type", "");
            params.put("fuel_out", "");
            params.put("fuel_in", "");
            params.put("start_date_time", "");
            params.put("pickup_location", "");
            params.put("return_date_time", "");
            params.put("return_location", "");
            params.put("balance_due", "");

        }
        /* GABRIEL: Esto es importante (cuenta el numero de detalles, para imprimir texto fijo)
		 * Si no hay detalles, es necesario que se env�e al menos un elemento de detalle con valores vacios o en cero
		 *  */
        params.put("counter_record", paymentDetails != null ? paymentDetails.size() : 0);

        List<ContratoSubreports> arraysDetails = new ArrayList<ContratoSubreports>();
        ContratoSubreports contratosSubreportes = new ContratoSubreports();
        contratosSubreportes.setListaImpuestos(impuestosDetails);
        contratosSubreportes.setListaPagos(paymentDetails);
        arraysDetails.add(contratosSubreportes);

        try {

            /*SEARCH THE FILE (if has other path please change it!)*/
            File f = new File("reportes\\conrMio.jrxml");
            File subreport = new File("reportes\\SubReportImpuestos.jrxml");
            File subreport2 = new File("reportes\\SubReportPagos.jrxml");

            String reportPath_base = f.getAbsolutePath().replace("conrMio.jrxml", "");
            String reportPath = f.getAbsolutePath();
            String reportPathBase = f.getAbsolutePath().replace("conr.jrxml", "");

            JasperReport jasperReport = JasperCompileManager.compileReport(f.getAbsolutePath());
            JasperReport jasperSubReport = JasperCompileManager.compileReport(subreport.getAbsolutePath());
            JasperReport jasperSubReport2 = JasperCompileManager.compileReport(subreport2.getAbsolutePath());

            params.put("subReportBeanList", jasperSubReport);
            params.put("subReportBeanList2", jasperSubReport2);
            params.put("SUBREPORT_DIR", reportPath_base);

            // Get data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(arraysDetails);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,
                    jrBeanCollectionDataSource);

            // Export the report to a PDF file
            //JasperExportManager.exportReportToPdfFile(jasperPrint, reportPathBase + "conr.pdf");
            // Export the report to a PDF file
            //         JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath.replace("sumary.jrxml", "") + "sumary.pdf");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.addHeader("Content-disposition", "inline; filename=jsfReporte.pdf");
            ServletOutputStream stream = response.getOutputStream();

            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
            stream.close();

            FacesContext.getCurrentInstance().responseComplete();

            System.out.println("Done");

            //return "Report successfully generated @path= " + reportPathBase;
        } catch (Exception e) {
            e.printStackTrace();
            //return e.getMessage();
        }

    }

    public void generateReportSumary() {

        SumaryHeaderDTO sumaryHeaderDTO = new SumaryHeaderDTO();
        /* GET DATA FROM QUERY HERE*/
        sumaryHeaderDTO.setDate_printed((new SimpleDateFormat("dd/MM/yy hh:mm:ss a")).format(new Date()));//Enviamos fecha actual
        sumaryHeaderDTO.setNombre_cliente("Gabriel Grillo");
        sumaryHeaderDTO.setReservation_no(343);
        sumaryHeaderDTO.setBooking_status("Hired by Gabriel Grillo");
        sumaryHeaderDTO.setVehicle("Volkswagen Golf - Black");
        sumaryHeaderDTO.setAuto_rego("kam013");
        sumaryHeaderDTO.setPickup("Bexley 2020-10-26 09:00");
        sumaryHeaderDTO.setDropoff("Bexley 2021-10-05 09:00");
        sumaryHeaderDTO.setEntered_by("grillo gabriel");
        sumaryHeaderDTO.setDate_entered("2020-10-26");
        sumaryHeaderDTO.setSource_by("Internet search");
        sumaryHeaderDTO.setRate_detail("49 days x @$1  $49");
        sumaryHeaderDTO.setRate_total("$1.469,00");
        sumaryHeaderDTO.setKms_out(15564);
        sumaryHeaderDTO.setBalance_due(62);
        sumaryHeaderDTO.setRenter_name("Gabriel Grillo");
        sumaryHeaderDTO.setRenter_email("gabigrillo46@gmail.com");
        sumaryHeaderDTO.setLicence_no("321321");
        sumaryHeaderDTO.setExpiry_date("2024-10-16");
        sumaryHeaderDTO.setDob("1986-08-09");
        sumaryHeaderDTO.setAddress("10 / 20 Monomeeth");
        sumaryHeaderDTO.setPhone("0400913385");
        sumaryHeaderDTO.setMobile("");

        /* 
		 *  * FOR TAXES
         */
        List<SumaryDetailsDTO> sumaryDetailsTaxes = new ArrayList<SumaryDetailsDTO>();
        /* GET DATA FROM QUERY HERE*/

        for (int a = 0; a < this.listaImpuestoSeleccAlq.size(); a++) {
            Alquiler_Impuesto alquilerImp = this.listaImpuestoSeleccAlq.get(a);
            if (alquilerImp.getImpuesto() != null) {
                SumaryDetailsDTO summ = new SumaryDetailsDTO();
                summ.setSumary_detail_name(alquilerImp.getImpuesto().getNombre());
                String detalle = "QTY " + alquilerImp.getCantidad() + " " + alquilerImp.getImpuesto().getTipo_impuesto().getNombre() + " @ " + alquilerImp.getPrecio();
                summ.setSumary_detail_detail(detalle);
                summ.setSumary_detail_subtotal(alquilerImp.getSubtotal());
                sumaryDetailsTaxes.add(summ);
            }
        }

        /* 
		 *  * FOR PAYMENT DETAIL
         */
        List<SumaryDetailsDTO> sumaryDetailsPayment = new ArrayList<SumaryDetailsDTO>();

        for (int a = 0; a < this.listaPagoAlquiler.size(); a++) {
            Pago pagoAlq = this.listaPagoAlquiler.get(a);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SumaryDetailsDTO p1 = new SumaryDetailsDTO();
            p1.setSumary_detail_name(pagoAlq.getTipoPago().getNombre());
            String detalle = sdf.format(pagoAlq.getFecha_hora());
            if (pagoAlq.getSucursal() != null) {
                detalle = detalle + " " + pagoAlq.getSucursal().getNombre();
            }
            p1.setSumary_detail_detail(detalle);
            p1.setSumary_detail_subtotal(pagoAlq.getMonto());
            sumaryDetailsPayment.add(p1);

        }


        /* 
		 *  * FOR PAYMENT TYPE
         */
        List<SumaryDetailsDTO> sumaryDetailsType = new ArrayList<SumaryDetailsDTO>();

        for (int a = 0; a < this.listaMultas.size(); a++) {
            Multa mult = this.listaMultas.get(a);
            SumaryDetailsDTO type1 = new SumaryDetailsDTO();
            type1.setSumary_detail_name(mult.getTipo().getNombre());
            type1.setSumary_detail_detail("#" + mult.getNumero());
            sumaryDetailsType.add(type1);
        }
        /* GET DATA FROM QUERY HERE*/

        List<SumarySubreportArray> arraysDetails = new ArrayList<SumarySubreportArray>();
        SumarySubreportArray sumarySubReportArray = new SumarySubreportArray();
        sumarySubReportArray.setTaxesDetail(sumaryDetailsTaxes);
        sumarySubReportArray.setPaymentDetail(sumaryDetailsPayment);
        sumarySubReportArray.setPaymentTypeDetail(sumaryDetailsType);
        arraysDetails.add(sumarySubReportArray);

        try {

            File sumary = new File("reportes\\sumary.jrxml");
            File sumary_taxes = new File("reportes\\sumary-taxes.jrxml");

            String reportPath_base = sumary.getAbsolutePath().replace("sumary.jrxml", "");
            String reportPath = sumary.getAbsolutePath();
            String reportPath_taxes = sumary_taxes.getAbsolutePath();

            JasperReport jasperMasterReport = JasperCompileManager.compileReport(reportPath);
            JasperReport jasperSubreportReport_taxes = JasperCompileManager.compileReport(reportPath_taxes);

            //Complete Params
            Map<String, Object> params = new HashMap<>();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            SimpleDateFormat sdfSoloFechas = new SimpleDateFormat("dd/MM/yyyy");

            params.put("date_printed", sdf.format(c.getTime()));
            if (this.alquilerActual != null && this.alquilerActual.getAuto() != null) {
                if (this.clientePrincipal != null) {
                    params.put("nombre_cliente", this.clientePrincipal.getNombre() + " " + this.clientePrincipal.getApellido());
                } else {
                    params.put("nombre_cliente", "");
                }
                params.put("reservation_no", this.alquilerActual.getId());
                params.put("booking_status", this.getEstadoStr(this.alquilerActual.getEstado()));
                params.put("vehicle", this.alquilerActual.getAuto().getMarca().getNombre() + " " + this.alquilerActual.getAuto().getModelo().getNombre());
                params.put("auto_rego", this.alquilerActual.getRego());
                String pickupDesc = "";
                String dropoffDesc = "";
                if (this.alquilerActual.getSucursal_origen() != null && this.alquilerActual.getSucursal_destino() != null) {
                    pickupDesc = this.alquilerActual.getSucursal_origen().getNombre() + " " + sdfSoloFechas.format(this.alquilerActual.getFecha_inicio()) + " " + this.alquilerActual.getHora_inicio();
                    params.put("pickup", pickupDesc);
                    dropoffDesc = this.alquilerActual.getSucursal_destino().getNombre() + " " + sdfSoloFechas.format(this.alquilerActual.getFecha_fin()) + " " + this.alquilerActual.getHora_fin();
                    params.put("dropoff", dropoffDesc);
                } else {
                    params.put("pickup", pickupDesc);
                    params.put("dropoff", dropoffDesc);
                }
                String usuarioIngreso = "";
                if (this.alquilerActual.getUsuario() != null) {
                    usuarioIngreso = this.alquilerActual.getUsuario().getNombre() + " " + this.alquilerActual.getUsuario().getApellido();
                }
                params.put("entered_by", usuarioIngreso);

                params.put("date_entered", this.alquilerActual.getFecha());
                String sourceName = "";
                if (this.alquilerActual.getSource() != null) {
                    sourceName = this.alquilerActual.getSource().getNombre();
                }

                params.put("source_by", sourceName);
                String rateDetail = "";
                if (this.alquilerActual.isDiario()) {
                    rateDetail = this.alquilerActual.getDias() + " days x @ $" + this.alquilerActual.getRate_per_day();
                } else {
                    rateDetail = this.alquilerActual.getSemanas() + " weeks x @ $" + this.alquilerActual.getRate_per_day();
                }
                params.put("rate_detail", rateDetail);
                params.put("rate_total", "$" + this.alquilerActual.getTotal());
                params.put("kms_out", this.alquilerActual.getKms_out());

                params.put("balance_due", this.alquilerActual.getDeuda());
                params.put("gran_total", this.alquilerActual.getGran_total());

                if (this.clientePrincipal != null) {
                    params.put("renter_name", this.clientePrincipal.getNombre() + " " + this.clientePrincipal.getApellido());
                    params.put("renter_email", this.clientePrincipal.getEmail());
                    params.put("licence_no", this.clientePrincipal.getLicencia_numero());
                    if (this.clientePrincipal.getFecha_vencimiento_lic() != null) {
                        params.put("expiry_date", sdf.format(this.clientePrincipal.getFecha_vencimiento_lic()));
                    } else {
                        params.put("expiry_date", "");
                    }
                    if (this.clientePrincipal.getDOB() != null) {
                        params.put("dob", sdf.format(this.clientePrincipal.getDOB()));
                    } else {
                        params.put("dob", "");
                    }
                    String address = this.clientePrincipal.getDireccion() + " " + this.clientePrincipal.getSuburbio() + " ";
                    if (this.clientePrincipal.getState() != null) {
                        address = address + this.clientePrincipal.getState().getNombre();
                    }
                    params.put("address", address);
                    params.put("phone", this.clientePrincipal.getTelefono());
                    params.put("mobile", this.clientePrincipal.getMovil());
                }

                params.put("notaContrato", this.alquilerActual.getNotes());
                params.put("kms_in", this.alquilerActual.getKms_in());
                long distancia = this.alquilerActual.getKms_in() - this.alquilerActual.getKms_out();
                if (distancia < 0) {
                    distancia = 0;
                }
                params.put("travelled", distancia);
                params.put("gst", this.getGSTAlquiler());

            } else {
                params.put("nombre_cliente", "");
                params.put("reservation_no", sumaryHeaderDTO.getReservation_no());
                params.put("booking_status", sumaryHeaderDTO.getBooking_status());
                params.put("vehicle", sumaryHeaderDTO.getVehicle());
                params.put("auto_rego", sumaryHeaderDTO.getAuto_rego());
                params.put("pickup", sumaryHeaderDTO.getPickup());
                params.put("dropoff", sumaryHeaderDTO.getDropoff());
                params.put("entered_by", sumaryHeaderDTO.getEntered_by());
                params.put("date_entered", sumaryHeaderDTO.getDate_entered());
                params.put("source_by", sumaryHeaderDTO.getSource_by());
                params.put("rate_detail", sumaryHeaderDTO.getRate_detail());
                params.put("rate_total", sumaryHeaderDTO.getRate_total());
                params.put("kms_out", sumaryHeaderDTO.getKms_out());
                params.put("balance_due", sumaryHeaderDTO.getBalance_due());
                params.put("gran_total", sumaryHeaderDTO.getBalance_due());
                params.put("renter_name", sumaryHeaderDTO.getRenter_name());
                params.put("renter_email", sumaryHeaderDTO.getRenter_email());
                params.put("licence_no", sumaryHeaderDTO.getLicence_no());
                params.put("expiry_date", sumaryHeaderDTO.getExpiry_date());
                params.put("dob", sumaryHeaderDTO.getDob());
                params.put("address", sumaryHeaderDTO.getAddress());
                params.put("phone", sumaryHeaderDTO.getPhone());
                params.put("mobile", sumaryHeaderDTO.getMobile());
                params.put("notaContrato", "");
                long cero = 0;
                params.put("kms_in", cero);
                params.put("travelled", cero);
                params.put("gst", "");
            }

            //SUBREPORT PARAMS
            params.put("subReportBeanList", jasperSubreportReport_taxes);
            params.put("SUBREPORT_DIR", reportPath_base);

            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(arraysDetails);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, params,
                    jrBeanCollectionDataSource);

            // Export the report to a PDF file
            File pdfExportar = new File(reportPath.replace("sumary.jrxml", "") + "sumary.pdf");
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

    public String getEstadoStr(int estado) {
        if (estado == Alquiler.ESTADO_ALQUILADO) {
            return "Hired";
        } else if (estado == Alquiler.ESTADO_RESERVA) {
            return "Reservation";
        } else if (estado == Alquiler.ESTADO_CANCELADO) {
            return "Cancelled";
        } else if (estado == Alquiler.ESTADO_RETORNADO) {
            return "Returned";
        } else if (estado == Alquiler.ESTADO_CARGANDO) {
            return "Loading";
        } else if (estado == Alquiler.ESTADO_VENDIDO) {
            return "Sold";
        } else if (estado == Alquiler.ESTADO_REPO) {
            return "Repo";
        } else if (estado == Alquiler.ESTADO_COURTESY) {
            return "Courtesy";
        }

        return "";

    }

    public List<Tipo_pago> getListaTipoPago() {
        return listaTipoPago;
    }

    public void setListaTipoPago(List<Tipo_pago> listaTipoPago) {
        this.listaTipoPago = listaTipoPago;
    }

    private String getGSTAlquiler() {
        String textoGst = "";
        float gst = 0;
        try {
            if (this.alquilerActual != null) {
                float sumaImpConGST = 0;
                List<Alquiler_Impuesto> listaAlquileresImpuestos = alquilerImpuestoEJB.buscarImpuestosAlquiler(this.alquilerActual.getId());
                for (int l = 0; l < listaAlquileresImpuestos.size(); l++) {
                    Alquiler_Impuesto alqImpuesto = listaAlquileresImpuestos.get(l);
                    if (alqImpuesto.getImpuesto().isGst_inc()) {
                        sumaImpConGST = sumaImpConGST + alqImpuesto.getSubtotal();
                    }
                }
                sumaImpConGST = sumaImpConGST + this.alquilerActual.getTotal();
                float valorSinGST = (100 * sumaImpConGST) / 110;
                gst = (valorSinGST * 10) / 100;

                int gstInt = (int) (gst * 100);
                gst = ((float) gstInt) / 100;
            }
        } catch (Exception e) {

        }
        textoGst = "(inc GST of $" + gst + ")";
        return textoGst;
    }

    public void onTabChange(TabChangeEvent event) {
        if (tengoQValidarAuto(event.getTab())) {
            if (!this.validarAuto()) {
                this.setTabIndex(0);
                return;
            } else {
                if (this.tabAnterior.equalsIgnoreCase(this.TAB_AUTO)) {
                    this.registrarAuto();
                }
            }
        }
        if (tengoQValidarCliente(event.getTab())) {
            if (!this.validarCliente()) {
                this.setTabIndex(1);
                return;
            } else {
                if (this.tabAnterior.equalsIgnoreCase(this.TAB_CLIENTE)) {
                    this.registrarCliente();
                }
            }
        }
        if (tengoQValidarBookingDetails(event.getTab())) {
            if (!validarBookingDetails()) {
                this.setTabIndex(2);
                return;
            } else {
                if (this.tabAnterior.equalsIgnoreCase(this.TAB_BOOKING_DETAIL)) {
                    this.registrarBookingDetails();
                }
            }
        }
        if (event.getTab().getTitle().equalsIgnoreCase(this.TAB_FEE)) {
            this.cargarImpuestos();
            this.actualizarMontoExtras();
            this.actualizarTotal();
        }
        if (event.getTab().getTitle().equalsIgnoreCase(this.TAB_PAYMENTS) || event.getTab().getTitle().equalsIgnoreCase(this.TAB_DOCUMENTS)) {
            this.actualizarMontoExtras();
            this.actualizarTotal();
            this.registrarImpuestos();
        }
        this.refrescatBotonDesbloquear();
        this.tabAnterior = event.getTab().getTitle();
        //  FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        // FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onTabClose(TabCloseEvent event) {
        FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean tengoQValidarAuto(Tab tab) {
        if (!tab.getTitle().equalsIgnoreCase(this.TAB_AUTO)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean tengoQValidarCliente(Tab tab) {
        if (tab.getTitle().equalsIgnoreCase(this.TAB_BOOKING_DETAIL) || tab.getTitle().equalsIgnoreCase(this.TAB_FEE) || tab.getTitle().equalsIgnoreCase(this.TAB_PAYMENTS) || tab.getTitle().equalsIgnoreCase(this.TAB_DOCUMENTS)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean tengoQValidarBookingDetails(Tab tab) {
        if (tab.getTitle().equalsIgnoreCase(this.TAB_FEE) || tab.getTitle().equalsIgnoreCase(this.TAB_PAYMENTS) || tab.getTitle().equalsIgnoreCase(this.TAB_DOCUMENTS)) {
            return true;
        } else {
            return false;
        }
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void fechaInicioIngresada() {
        if (this.alquilerActual.getFecha_inicio() != null && this.alquilerActual.getFecha_primer_pago() == null) {
            this.alquilerActual.setFecha_primer_pago(this.alquilerActual.getFecha_inicio());
        }
    }

    public boolean isMostrarBotonSummary() {
        if (this.alquilerActual != null) {
            if (this.alquilerActual.getId() > -1) {
                if (this.alquilerActual.getEstado() == Alquiler.ESTADO_ALQUILADO) {
                    this.mostrarBotonSummary = true;
                    return true;
                }
            }
        }
        this.mostrarBotonSummary = false;
        return this.mostrarBotonSummary;
    }

    public void setMostrarBotonSummary(boolean mostrarBotonSummary) {
        this.mostrarBotonSummary = mostrarBotonSummary;
    }

    public void viewSummary() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerSummary");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerSummary", this.alquilerActual.getId() + "");

        //idAlquilerSummary
    }

    public boolean isFechaVencimientoHabilitada() {
        return fechaVencimientoHabilitada;
    }

    public void setFechaVencimientoHabilitada(boolean fechaVencimientoHabilitada) {
        this.fechaVencimientoHabilitada = fechaVencimientoHabilitada;
    }

    public List<Drivetrain> getListaDriveTrain() {
        return listaDriveTrain;
    }

    public void setListaDriveTrain(List<Drivetrain> listaDriveTrain) {
        this.listaDriveTrain = listaDriveTrain;
    }

    public void ingresoValor() {
        if (this.autoActual.getVIN() != null) {
            System.out.println(this.autoActual.getVIN());
        }
    }

    public void handleKeyEvent() {
        String vin = "";
        if (this.autoActual.getVIN().trim().length() > 0) {
            vin = this.autoActual.getVIN().trim();
            vin = vin.toUpperCase();
            Auto autoVIN = autoEJB.buscarPorVin(vin);
            if (autoVIN != null) {
                if (this.autoActual.getRego() != null && this.autoActual.getRego().trim().length() > 0) {
                    autoVIN.setRego(this.autoActual.getRego());
                }
                this.autoActual = this.getAutoClonado(autoVIN);
            } else {
                this.autoActual.setNro_chasis(vin);
                this.autoActual.setVIN(vin);
            }
        }
    }

    public void perdioFocusVIN() {
        if (this.autoActual != null && this.autoActual.getVIN().trim().length() > 0) {
            Auto autoVIN = autoEJB.buscarPorVin(this.autoActual.getVIN());
            if (autoVIN != null) {
                this.autoActual = autoVIN;
            }
        }
    }

    public void ponerMayusculaRego() {
        String rego = "";
        if (this.autoActual.getRego().trim().length() > 0) {
            rego = this.autoActual.getRego().trim().toUpperCase();
            this.autoActual.setRego(rego);
        }
    }

    public void setTieneTermino(boolean tieneTermino) {
        this.tieneTermino = tieneTermino;
    }

    public float getTotalTerm() {
        this.totalTerm = this.semanasTerm * this.alquilerActual.getRate_per_day();
        int totalTermInt = (int) (this.totalTerm * 100);
        this.totalTerm = ((float) totalTermInt) / 100;
        return totalTerm;
    }

    public void setTotalTerm(float totalTerm) {

        this.totalTerm = totalTerm;
    }

    public void setSemanasTerm(int semanasTerm) {
        this.semanasTerm = semanasTerm;
    }

    public List<Alquiler_Impuesto> getListaImpuestoTerm() {
        listaImpuestoTerm = new ArrayList<Alquiler_Impuesto>();
        if (this.listaImpuestoSeleccAlq == null) {
            return listaImpuestoTerm;
        }

        List<Pago> listaPagoAprobado = this.pagoEJB.buscarAprovadosPorAlquiler(this.alquilerActual.getId());

        for (int a = 0; a < this.listaImpuestoSeleccAlq.size(); a++) {
            Alquiler_Impuesto alquilerImpuesto = this.listaImpuestoSeleccAlq.get(a);
            Alquiler_Impuesto alquilerImpuestoCreado = new Alquiler_Impuesto();
            float subtotalFinal = alquilerImpuesto.getSubtotal();
            alquilerImpuestoCreado.setImpuesto(alquilerImpuesto.getImpuesto());
            alquilerImpuestoCreado.setSubtotal(subtotalFinal);
            listaImpuestoTerm.add(alquilerImpuestoCreado);
        }
        return listaImpuestoTerm;
    }

    public void setListaImpuestoTerm(List<Alquiler_Impuesto> listaImpuestoTerm) {
        this.listaImpuestoTerm = listaImpuestoTerm;
    }

    public float getGranTotalTerm() {

        this.granTotalTerm = this.totalTerm;
        for (int a = 0; a < this.listaImpuestoTerm.size(); a++) {
            Alquiler_Impuesto alquilerImpuesto = this.listaImpuestoTerm.get(a);
            this.granTotalTerm = this.granTotalTerm + alquilerImpuesto.getSubtotal();
        }
        int granTotalTermInt = (int) (this.granTotalTerm * 100);
        this.granTotalTerm = ((float) granTotalTermInt) / 100;
        return this.granTotalTerm;
    }

    public void setGranTotalTerm(float granTotalTerm) {
        this.granTotalTerm = granTotalTerm;
    }

    public String getFuelDescription(int fuelInt) {

        if (fuelInt == 1) {
            return "Full";
        } else if (fuelInt == 2) {
            return "7/8";
        } else if (fuelInt == 3) {
            return "3/4";
        } else if (fuelInt == 4) {
            return "5/8";
        } else if (fuelInt == 5) {
            return "1/2";
        } else if (fuelInt == 6) {
            return "3/8";
        } else if (fuelInt == 7) {
            return "1/4";
        } else if (fuelInt == 8) {
            return "1/8";
        } else if (fuelInt == 9) {
            return "Empty";
        }
        return "";
    }

    public boolean isMostrarVerAuto() {
        if (this.autoActual != null && this.autoActual.getId() > -1) {
            mostrarVerAuto = true;
        } else {
            mostrarVerAuto = false;
        }
        return mostrarVerAuto;
    }

    public void setMostrarVerAuto(boolean mostrarVerAuto) {
        this.mostrarVerAuto = mostrarVerAuto;
    }

    public void verDetalleAuto() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAutoDato");
        if (this.autoActual != null && this.autoActual.getId() > 0) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAutoDato", this.autoActual.getId() + "");
        }
    }

    public List<Invoice_Mecanico_Detalle> getListaInvoiceDetalleCliente() {
        return listaInvoiceDetalleCliente;
    }

    public void setListaInvoiceDetalleCliente(List<Invoice_Mecanico_Detalle> listaInvoiceDetalleCliente) {
        this.listaInvoiceDetalleCliente = listaInvoiceDetalleCliente;
    }

    public void editarExpenses(Invoice_Mecanico_Detalle invoiceMecanicoDetalle) {
        this.invoiceMecanicoActual = invoiceMecanicoDetalle.getInvoiceMecanico();
        this.invoiceMecanicoDetalleActual = invoiceMecanicoDetalle;
        this.proveedorActualExpenses = invoiceMecanicoActual.getProveedor();
        this.mostrarExpensesDatos = true;
    }

    public String getAutoInvoiceDetalle(Invoice_Mecanico_Detalle invoiceDetalle) {
        String auto = "";
        if (invoiceDetalle != null && invoiceDetalle.getAuto() != null) {
            Auto autoInvoice = invoiceDetalle.getAuto();
            auto += autoInvoice.getAño() + " ";
            if (autoInvoice.getMarca() != null) {
                auto += autoInvoice.getMarca().getNombre() + " ";
            }
            if (autoInvoice.getModelo() != null) {
                auto += autoInvoice.getModelo().getNombre() + " ";
            }
            auto += autoInvoice.getColor();
        } else {
            auto += invoiceDetalle.getYear() + " ";
            if (invoiceDetalle.getMarca() != null && invoiceDetalle.getMarca().getId() > -1) {
                auto += invoiceDetalle.getMarca().getNombre() + " ";
            }
            if (invoiceDetalle.getModelo() != null && invoiceDetalle.getModelo().getId() > -1) {
                auto += invoiceDetalle.getModelo().getNombre() + " ";
            }
            auto += invoiceDetalle.getColor();
        }
        return auto;
    }

    public String getCurrencyFormat(float monto) {
        String resultado = "";
        NumberFormat n = NumberFormat.getCurrencyInstance();
        resultado = n.format(monto);
        return resultado;
    }

    public double getGastosTotal() {
        double resultado = 0;
        for (int a = 0; a < this.listaInvoiceDetalleCliente.size(); a++) {
            Invoice_Mecanico_Detalle detalle = this.listaInvoiceDetalleCliente.get(a);
            resultado = resultado + detalle.getSubtotal();
        }

        return resultado;
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

    public float getCarCost(Alquiler alq) {
        float costo = 0;
        if (alq != null && alq.getAuto() != null && alq.getAuto().getId() > -1) {
            Auto autoAlquiler = alq.getAuto();
            costo = autoAlquiler.getValor_compra();
            float expensesAuto = this.invoiceMecanicoDetalleEJB.getExpensesDelAuto(autoAlquiler.getId());
            costo += expensesAuto;
        }
        return costo;
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

    public List<Alquiler> getListaAlquilerCliente() {
        return listaAlquilerCliente;
    }

    public void setListaAlquilerCliente(List<Alquiler> listaAlquilerCliente) {
        this.listaAlquilerCliente = listaAlquilerCliente;
    }

    public boolean isMostrarExpensesDatos() {
        return mostrarExpensesDatos;
    }

    public void setMostrarExpensesDatos(boolean mostrarExpensesDatos) {
        this.mostrarExpensesDatos = mostrarExpensesDatos;
    }

    public Cliente getProveedorActualExpenses() {
        return proveedorActualExpenses;
    }

    public void setProveedorActualExpenses(Cliente proveedorActualExpenses) {
        this.proveedorActualExpenses = proveedorActualExpenses;
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

    public void onProveedorExpensesSelectNombre(SelectEvent event) {
        Object obj = listaNombreProveedorExpensesSimilar.get(event.getObject());

        if (obj != null) {
            this.proveedorActualExpenses = (Cliente) obj;
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

    private boolean validarExpensa() {
        if (this.proveedorActualExpenses == null || this.proveedorActualExpenses.getId() == -1) {
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

        Invoice_Mecanico invoiceMecanico = this.invoiceMecanicoEJB.getInvoicePorProveedorNumero(this.proveedorActualExpenses.getId(), this.invoiceMecanicoActual.getInvoice_number());
        if (invoiceMecanico != null && invoiceMecanico.getId() != this.invoiceMecanicoActual.getId()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Invoice number already registered to the supplier"));
            return false;
        }

        if (this.autoActual == null || this.autoActual.getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The car of the invoice is null"));
            return false;
        }

        if (this.clientePrincipal == null || this.clientePrincipal.getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The client is not defined"));
            return false;
        }

        return true;
    }

    public void confirmAndCloseDialog() {

        if (this.validarExpensa()) {

            this.invoiceMecanicoActual.setSentido(Invoice_Mecanico.SENTIDO_GASTO);
            this.invoiceMecanicoActual.setProveedor(this.proveedorActualExpenses);
            this.invoiceMecanicoActual.setGst(this.invoiceMecanicoDetalleActual.getGst());
            this.invoiceMecanicoActual.setTotal_sin_gst(this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst());
            this.invoiceMecanicoActual.setTotal(this.invoiceMecanicoDetalleActual.getSubtotal());
            this.invoiceMecanicoDetalleActual.setCliente(this.clientePrincipal);
            this.invoiceMecanicoDetalleActual.setCantidad(1);
            this.invoiceMecanicoDetalleActual.setTipo(Invoice_Mecanico_Detalle.TIPO_OUR_CAR);
            this.invoiceMecanicoDetalleActual.setColor(this.autoActual.getColor());
            this.invoiceMecanicoDetalleActual.setMarca(this.autoActual.getMarca());
            this.invoiceMecanicoDetalleActual.setModelo(this.autoActual.getModelo());
            this.invoiceMecanicoDetalleActual.setYear(this.autoActual.getAño());
            this.invoiceMecanicoDetalleActual.setRego(this.autoActual.getRego());
            this.invoiceMecanicoDetalleActual.setVin(this.autoActual.getVIN());

            if (this.invoiceMecanicoActual.getId() == -1 && this.invoiceMecanicoDetalleActual.getId() == -1) {
                this.invoiceMecanicoEJB.create(invoiceMecanicoActual);
                this.invoiceMecanicoDetalleActual.setInvoiceMecanico(invoiceMecanicoActual);
                this.invoiceMecanicoDetalleEJB.create(invoiceMecanicoDetalleActual);
            } else {
                this.invoiceMecanicoEJB.edit(invoiceMecanicoActual);
                this.invoiceMecanicoDetalleEJB.edit(invoiceMecanicoDetalleActual);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Expense registed sucesfully"));

            this.invoiceMecanicoActual = new Invoice_Mecanico();
            this.invoiceMecanicoDetalleActual = new Invoice_Mecanico_Detalle();
            this.proveedorActualExpenses = new Cliente();

            this.listaInvoiceDetalleCliente = this.invoiceMecanicoDetalleEJB.listaDetallePorIdCliente(this.clientePrincipal.getId());

            this.mostrarExpensesDatos = false;
        }
    }

    public List<String> completeTextNombreProveedorExpenses(String query) {
        List<String> results = new ArrayList<>();
        List<Cliente> listaProveedoresSimilares = new ArrayList();
        try {
            listaProveedoresSimilares = clienteEJB.buscarProveedoresPorNombreEmpezando(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listaProveedoresSimilares.size(); i++) {
            String stringHash = listaProveedoresSimilares.get(i).getApellido();
            listaNombreProveedorExpensesSimilar.put(stringHash, listaProveedoresSimilares.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public void closeDialogExpenses() {
        this.invoiceMecanicoActual = new Invoice_Mecanico();
        this.invoiceMecanicoDetalleActual = new Invoice_Mecanico_Detalle();
        this.proveedorActualExpenses = new Cliente();

        if (this.clientePrincipal != null && this.clientePrincipal.getId() > -1) {
            this.listaInvoiceDetalleCliente = this.invoiceMecanicoDetalleEJB.listaDetallePorIdCliente(this.clientePrincipal.getId());
        }

        this.mostrarExpensesDatos = false;
    }

    public boolean mostrarClientHistory() {
        if (this.clientePrincipal != null && this.clientePrincipal.getId() > -1) {
            return true;
        }
        return false;
    }

    public void deleteExpense() {
        if (this.invoiceMecanicoActual != null && this.invoiceMecanicoDetalleActual != null
                && this.invoiceMecanicoActual.getId() > -1 && this.invoiceMecanicoDetalleActual.getId() > -1) {
            this.invoiceMecanicoDetalleEJB.remove(invoiceMecanicoDetalleActual);
            List<Invoice_Mecanico_Detalle> listaDeInvoiceDetalleRemaining = this.invoiceMecanicoDetalleEJB.listaDetallePorIdInvoice(this.invoiceMecanicoActual.getId());
            if (listaDeInvoiceDetalleRemaining == null || listaDeInvoiceDetalleRemaining.size() == 0) {
                this.invoiceMecanicoEJB.remove(invoiceMecanicoActual);
            }
        }
        this.invoiceMecanicoActual = new Invoice_Mecanico();
        this.invoiceMecanicoDetalleActual = new Invoice_Mecanico_Detalle();
        this.proveedorActualExpenses = new Cliente();

        this.listaInvoiceDetalleCliente = this.invoiceMecanicoDetalleEJB.listaDetallePorIdCliente(this.clientePrincipal.getId());
        if (this.listaInvoiceDetalleCliente != null && this.listaInvoiceDetalleCliente.size() == 0) {
            this.getGastosTotal();
        }
        this.mostrarExpensesDatos = false;
    }

    public Date getFechaInicioHistory() {
        return fechaInicioHistory;
    }

    public void setFechaInicioHistory(Date fechaInicioHistory) {
        this.fechaInicioHistory = fechaInicioHistory;
    }

    public String getTAB_CLIENT_HISTORY() {
        return TAB_CLIENT_HISTORY;
    }

    public void setTAB_CLIENT_HISTORY(String TAB_CLIENT_HISTORY) {
        this.TAB_CLIENT_HISTORY = TAB_CLIENT_HISTORY;
    }

    public String getMontoPagadoAlquiler(int idAlquiler) {
        return this.getCurrencyFormat(this.pagoEJB.getMontoRecibidoDeAlquiler(idAlquiler));
    }

    public boolean mostrarWorkout() {
        if (this.alquilerActual != null && this.alquilerActual.getId() > 850 && this.alquilerActual.getId() != 901) {
            return true;
        }
        return false;
    }

}
