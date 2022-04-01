package com.piantino.controller;

import com.opencsv.CSVWriter;
import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.Auto_imagenesFacadeLocal;
import com.piantino.ejb.Auto_opcionFacadeLocal;
import com.piantino.ejb.Auto_preciosFacadeLocal;
import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.ejb.DrivetrainFacadeLocal;
import com.piantino.ejb.Formulario5FacadeLocal;
import com.piantino.ejb.Formulario_imprimirFacadeLocal;
import com.piantino.ejb.InvoiceFacadeLocal;
import com.piantino.ejb.Invoice_MecanicoFacadeLocal;
import com.piantino.ejb.Invoice_Mecanico_DetalleFacadeLocal;
import com.piantino.ejb.LabelFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.ejb.OpcionFacadeLocal;
import com.piantino.ejb.PublicidadFacadeLocal;
import com.piantino.ejb.StateFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.TipoCompraFacadeLocal;
import com.piantino.ejb.Tipo_autoFacadeLocal;
import com.piantino.ejb.Tipo_combustibleFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.ejb.VentaFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Auto;
import com.piantino.model.Marca;
import com.piantino.model.Auto;
import com.piantino.model.Auto_imagenes;
import com.piantino.model.Auto_opcion;
import com.piantino.model.Auto_precios;
import com.piantino.model.Cliente;
import com.piantino.model.Cod_postal;

import com.piantino.model.Drivetrain;
import com.piantino.model.Formulario5;
import com.piantino.model.Formulario_imprimir;
import com.piantino.model.Invoice;
import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Invoice_Mecanico_Detalle;
import com.piantino.model.Label;
import com.piantino.model.Modelo;
import com.piantino.model.Opcion;
import com.piantino.model.Publicidad;
import com.piantino.model.State;
import com.piantino.model.Sucursal;
import com.piantino.model.TipoCompra;
import com.piantino.model.Tipo_auto;
import com.piantino.model.Tipo_combustible;
import com.piantino.model.Usuario;
import com.piantino.model.Venta;
import com.piantino.report.Dtos.Form5Detalle;
import com.piantino.report.Dtos.InvoiceDetalle;
import com.piantino.report.Dtos.ReporteCompleto;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.pdfbox.io.IOUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

@Named
@ViewScoped
public class DatosAutoController implements Serializable {

    private final Object key = new Object();

    //esto es rama 10
    @EJB
    private ModeloFacadeLocal modeloEJB;

    @EJB
    private MarcaFacadeLocal marcaEJB;

    @EJB
    private AutoFacadeLocal autoEJB;

    @EJB
    private StateFacadeLocal stateEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    @EJB
    private Tipo_combustibleFacadeLocal tipoCombustibleEJB;

    @EJB
    private Tipo_autoFacadeLocal tipoAutoEJB;

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    @EJB
    private Invoice_MecanicoFacadeLocal invoiceMecanicoEJB;

    private List<Sucursal> listaSucursales;

    private List<Drivetrain> listaDrivetrain;

    @EJB
    private DrivetrainFacadeLocal driveTrainEJB;

    private List<Tipo_auto> listaTipoAutos;

    private Auto auto;

    private Auto autoSeleccionado = null;

    private String accion = "";

    private List<Marca> listaMarcas;

    private List<Modelo> listaModelos;

    private List<State> listaStates;

    private List<Tipo_combustible> listaTipoCombustibles;

    private boolean yaExisteRego = false;
    private boolean RegoVencido = false;

    @Inject
    private GrillaAutosController comentarController;

    private Usuario usuarioLogueado;

    private Calendar c;

    private List<Auto_imagenes> listaAutoImagenes = new ArrayList<Auto_imagenes>();

    private byte[] imagen;

    private String pathResource = "";

    private UploadedFile file = null;

    private UploadedFiles files;

    @EJB
    private Auto_imagenesFacadeLocal autoImagenesEJB;

    private List<Auto_precios> listaAutosPrecio = new ArrayList();

    @EJB
    private Auto_preciosFacadeLocal autoPrecioEJB;

    private Auto_precios autoPrecioActual = new Auto_precios();

    @EJB
    private OpcionFacadeLocal opcionEJB;

    private List<Opcion> listaOpciones = new ArrayList<>();

    private List<String> listaOpcionesSeleccionadas = new ArrayList<>();

    @EJB
    private Auto_opcionFacadeLocal autoOpcionEJB;

    private List<Label> listaLabels = new ArrayList<>();

    @EJB
    private LabelFacadeLocal labelEJB;

    private String nombreContacto = "";

    private HashMap listaNombreContactoSimilar = new HashMap();

    private HashMap listaNombreProveedorExpensesSimilar = new HashMap();

    private Cliente clienteActualVenta = new Cliente();

    @EJB
    private ClienteFacadeLocal clienteEJB;

    private HashMap listaApellidoSimilar = new HashMap();

    private HashMap listaApellidoSimilarClienteInvoice = new HashMap();

    private List<Formulario5> listaFormulariosAuto = new ArrayList();

    private Formulario5 formularioActivoAuto = null;

    @EJB
    private Formulario5FacadeLocal formularioEJB;

    private boolean mostrarGenerarNotice = false;

    private String mensajeFormulario = "";

    private List<TipoCompra> listaTipoCompra;

    @EJB
    private TipoCompraFacadeLocal tipoCompraEJB;

    private boolean mostrarBotonNuevaVenta = false;

    private boolean mostrarBotonesAceptar = true;

    private boolean mostrarGrillaVenta = false;

    private boolean mostrarBotonCancelarVenta = true;

    private boolean mostrarBotonImprimirFormulario = false;

    @EJB
    private VentaFacadeLocal ventaEJB;

    @EJB
    private Formulario_imprimirFacadeLocal formularioImprimirEJB;

    private List<Formulario_imprimir> listaFormulariosImprimir = new ArrayList();

    private List<Formulario_imprimir> listaFormulariosImprimirSeleccionados = new ArrayList();

    private Venta ventaActual = new Venta();

    @EJB
    private InvoiceFacadeLocal invoiceEJB;

    private Cliente proveedorActual = null;

    private List<Cliente> listaProveedoresSimilares = new ArrayList();

    private boolean esCustomer = false;

    private boolean esDealer = false;

    private boolean esCorporation = false;

    private int tipoCliente = 0;

    private List<Invoice_Mecanico_Detalle> listaDetalleInvoiceAuto = null;

    @EJB
    private Invoice_Mecanico_DetalleFacadeLocal invoiceMecanicoDetalleEJB;

    private Cliente proveedorActualExpenses = new Cliente();

    private Invoice_Mecanico invoiceMecanicoActual = null;

    private Invoice_Mecanico_Detalle invoiceMecanicoDetalleActual = null;

    private boolean mostrarExpensesDatos = false;

    private boolean mostrarVerAlquiler = false;

    private Alquiler alquilerAuto = null;

    private String mensajeNuevaVenta = "";

    private boolean webActualizada = false;

    private List<Auto> listaAutosDisponiblesWeb = new ArrayList();

    @EJB
    private PublicidadFacadeLocal publicidadEJB;

    private float precioCostoAuto = 0;

    private float precioLoad = 0;

    private float precioWarranty = 0;

    private float precioExpenses = 0;

    private float precioTotalCost = 0;

    private float precioDefectoAuto = 0;

    private float precioProfitConGST = 0;

    private float precioGstCreditFromCost = 0;

    private float precioGstDebitFromSale = 0;

    private float precioProfitSinGST = 0;

    @PostConstruct
    private void init() {

        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        listaMarcas = marcaEJB.findAll();
        listaModelos = modeloEJB.findAll();
        listaStates = stateEJB.findAll();
        listaSucursales = sucursalEJB.findAll();
        listaTipoCombustibles = tipoCombustibleEJB.findAll();
        listaTipoAutos = tipoAutoEJB.findAll();
        listaDrivetrain = driveTrainEJB.getTodasActivas();
        listaOpciones = opcionEJB.findAll();
        listaLabels = labelEJB.findAll();
        listaTipoCompra = tipoCompraEJB.findAll();
        listaFormulariosImprimir = formularioImprimirEJB.findAll();

        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        String idAutoStr = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idAutoDato");
        if (idAutoStr != null && idAutoStr.trim().length() > 0) {
            int idAuto = Integer.parseInt(idAutoStr);
            autoSeleccionado = autoEJB.buscarPorId(idAuto);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAutoDato");
            listaAutosPrecio = this.autoPrecioEJB.getPrecioAutosPorAuto(idAuto);

        }
        if (autoSeleccionado == null) {
            autoSeleccionado = comentarController.getAutoSeleccionada();
        }
        if (autoSeleccionado == null) {
            auto = new Auto();
            this.setAccion("R");
            this.auto.setStock(this.autoEJB.getStockSugerido());
        } else {
            try {
                auto = (Auto) autoSeleccionado.clone();

                if (autoSeleccionado.getMarca() == null) {
                    autoSeleccionado.setMarca(new Marca());
                }
                auto.setMarca((Marca) autoSeleccionado.getMarca().clone());

                if (autoSeleccionado.getModelo() == null) {
                    autoSeleccionado.setModelo(new Modelo());
                }
                auto.setModelo((Modelo) autoSeleccionado.getModelo().clone());

                if (autoSeleccionado.getTipo_body() == null) {
                    autoSeleccionado.setTipo_body(new Tipo_auto());
                }
                auto.setTipo_body((Tipo_auto) autoSeleccionado.getTipo_body().clone());

                if (autoSeleccionado.getTipo_combustible() == null) {
                    autoSeleccionado.setTipo_combustible(new Tipo_combustible());
                }
                auto.setTipo_combustible((Tipo_combustible) autoSeleccionado.getTipo_combustible().clone());

                if (autoSeleccionado.getSucursal() == null) {
                    autoSeleccionado.setSucursal(new Sucursal());
                }
                auto.setSucursal((Sucursal) autoSeleccionado.getSucursal().clone());

                if (autoSeleccionado.getLabel() == null) {
                    autoSeleccionado.setLabel(new Label());
                }
                auto.setLabel((Label) autoSeleccionado.getLabel().clone());

                if (autoSeleccionado.getClienteProveedor() == null) {
                    autoSeleccionado.setClienteProveedor(new Cliente());
                }
                auto.setClienteProveedor((Cliente) autoSeleccionado.getClienteProveedor().clone());
                if (autoSeleccionado.getTipoCompra() == null) {
                    autoSeleccionado.setTipoCompra(new TipoCompra());
                }
                auto.setTipoCompra((TipoCompra) autoSeleccionado.getTipoCompra().clone());

            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DatosAutoController.class.getName()).log(Level.SEVERE, null, ex);
                auto = autoSeleccionado;
            }
            this.setAccion("M");
            this.setMostrarGenerarNotice(true);
            if (usuarioLogueado != null && auto.getVIN() != null) {
                String available = "";
                if (auto.isDisponible()) {
                    available = "true";
                } else {
                    available = "false";
                }
                System.out.println("El usuario: " + usuarioLogueado.getNombre() + " ingreso al auto: " + auto.getVIN() + " Available: " + available);

            }

            if (this.auto != null && this.auto.getId() > 0) {
                alquilerAuto = this.alquilerEJB.getUltimoAlquilerAuto(this.auto.getId());
            }

        }

        if (auto.getClienteProveedor() != null && auto.getClienteProveedor().getId() > 0) {
            this.proveedorActual = auto.getClienteProveedor();
        } else {
            this.proveedorActual = new Cliente();
        }

        Venta ventaAuto = ventaEJB.getVentaPorAuto(this.auto.getId());
        if (ventaAuto != null) {
            try {
                this.ventaActual = (Venta) ventaAuto.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DatosAutoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (this.ventaActual != null && this.ventaActual.getClienteVenta() != null && this.ventaActual.getClienteVenta().getId() > 0) {
            this.clienteActualVenta = ventaAuto.getClienteVenta();
            if (this.clienteActualVenta != null && this.clienteActualVenta.getId() > 0) {
                this.tipoCliente = this.clienteActualVenta.getTipo();
            }
        }

        if ((ventaActual != null && ventaActual.getId() == -1) || ventaActual == null) {
            this.mostrarBotonNuevaVenta = true;
        }

        if (ventaActual != null && ventaActual.getId() > 0) {
            this.mostrarGenerarNotice = false;
        }

        this.listaFormulariosAuto = this.formularioEJB.getListaFormularioAuto(this.auto.getId());

        for (int a = 0; a < this.listaFormulariosAuto.size(); a++) {
            Formulario5 formularioAuto = this.listaFormulariosAuto.get(a);
            if (formularioAuto.getEstado() == Formulario5.ESTADO_ACTIVO) {
                formularioActivoAuto = formularioAuto;
            }
        }

        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();

        pathResource = ctx.getRealPath("/resources");

        List<Auto_opcion> listaAutoOpciones = autoOpcionEJB.getOpcionesPorAuto(auto);
        for (int a = 0; a < listaAutoOpciones.size(); a++) {
            Auto_opcion autoOpcion = listaAutoOpciones.get(a);
            if (autoOpcion.getOpcion() != null) {
                this.listaOpcionesSeleccionadas.add(autoOpcion.getOpcion().getId() + "");
            }
        }

        if (this.formularioNOActualizado()) {
            this.mensajeFormulario = "Notice is not updated";
        }

        this.cargarImagenesAuto();

        this.listaDetalleInvoiceAuto = this.invoiceMecanicoDetalleEJB.listaDetallePorIdAuto(this.auto.getId());

        this.invoiceMecanicoActual = new Invoice_Mecanico();
        this.invoiceMecanicoDetalleActual = new Invoice_Mecanico_Detalle();

    }

    public boolean isMostrarGenerarNotice() {
        return mostrarGenerarNotice;
    }

    public void setMostrarGenerarNotice(boolean mostrarGenerarNotice) {
        this.mostrarGenerarNotice = mostrarGenerarNotice;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    private void resize(InputStream input, Path target) throws IOException {

        BufferedImage originalImage = ImageIO.read(input);

        /**
         * SCALE_AREA_AVERAGING SCALE_DEFAULT SCALE_FAST SCALE_REPLICATE
         * SCALE_SMOOTH
         */
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        /*if (width < 900 || height < 900) {
            File archivoFinal = target.toFile();
            if (archivoFinal.exists()) {
                archivoFinal.delete();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The minimum resolution is 800 high by 800 wide"));
            return;
        }*/
        int newWidth = 0;
        int newHeight = 0;
        int MIN_BOUNDARY = 500;

        if (width < height) {
            if (height >= 900) {
                newHeight = 900;
                // scale height to maintain aspect ratio
                newWidth = (newHeight * width) / height;
            }
        } else if (width >= 900) {
            newWidth = 900;
            // scale width to maintain aspect ratio
            newHeight = (newWidth * height) / width;
        }

        if (newHeight == 0 || newWidth == 0) {
            newHeight = height;
            newWidth = width;
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics2D.dispose();

//        Image newResizedImage = originalImage
        //              .getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        String s = target.getFileName().toString();
        String fileExtension = s.substring(s.lastIndexOf(".") + 1);

        // we want image in png format
        ImageIO.write(resizedImage,
                fileExtension, target.toFile());

    }

    public BufferedImage convertToBufferedImage(Image img) {

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bi;
    }

    public void cargarImagenesAuto() {
        if (this.auto != null) {
            this.listaAutoImagenes = autoImagenesEJB.getListaPorAuto(this.auto.getId());
            for (int a = 0; a < this.listaAutoImagenes.size(); a++) {
                Auto_imagenes autoImagen = this.listaAutoImagenes.get(a);
                byte[] byteLeidos = autoImagen.getImagen();
                try {
                    File archivoImagen = new File(this.pathResource + "/" + autoImagen.getUrl());
                    if (archivoImagen.exists() == false) {
                        String pathDirectorio = archivoImagen.getAbsolutePath().replace(archivoImagen.getName(), "");
                        File directorioArchivo = new File(pathDirectorio);
                        if (directorioArchivo.exists() == false) {
                            if (directorioArchivo.mkdirs() == false) {
                                return;
                            }
                        }
                        if (archivoImagen.createNewFile()) {
                            FileOutputStream archivoOut = new FileOutputStream(archivoImagen);
                            archivoOut.write(byteLeidos);
                            archivoOut.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Tipo_auto> getListaTipoAutos() {
        return listaTipoAutos;
    }

    public void setListaTipoAutos(List<Tipo_auto> listaTipoAutos) {
        this.listaTipoAutos = listaTipoAutos;
    }

    public List<Tipo_combustible> getListaTipoCombustibles() {
        return listaTipoCombustibles;
    }

    public void setListaTipoCombustibles(List<Tipo_combustible> listaTipoCombustibles) {
        this.listaTipoCombustibles = listaTipoCombustibles;
    }

    public List<Marca> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marca> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public void onClienteSeleccionado(SelectEvent event) {
        Object obj = listaApellidoSimilar.get(event.getObject());
        Cliente clienteSelecc = null;
        if (obj != null) {
            clienteSelecc = this.getClienteClonado((Cliente) obj);
            this.setClienteActualVenta(clienteSelecc);
        }
    }

    public void buscarClienteApellidoNombre() {
        Cliente clienteExistAuto = null;
        try {
            clienteExistAuto = clienteEJB.buscarPorNombreYApellido(this.clienteActualVenta.getNombre(), this.clienteActualVenta.getApellido());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clienteExistAuto != null) {
            this.setClienteActualVenta(this.getClienteClonado(clienteExistAuto));
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

    public List<String> completeTextCliente(String query) {

        this.clienteActualVenta = new Cliente();
        State estadoTerri = new State();
        estadoTerri.setId(1);
        this.clienteActualVenta.setState(estadoTerri);
        this.clienteActualVenta.setPais("Australia");
        this.clienteActualVenta.setOtorgadaEN("Australia");

        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            if (this.tipoCliente == Cliente.TIPO_CUSTOMER) {
                listaClientes = clienteEJB.buscarPorApellidoSimilar(query);
            } else {
                listaClientes = clienteEJB.buscarProveedoresPorNombreEmpezando(query);
            }
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

    public HashMap getListaApellidoSimilar() {
        return listaApellidoSimilar;
    }

    public void setListaApellidoSimilar(HashMap listaApellidoSimilar) {
        this.listaApellidoSimilar = listaApellidoSimilar;
    }

    public void imprimirFormularios() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat sdfMesAnio = new SimpleDateFormat("MMMMMM yyyy");
        SimpleDateFormat sdfBarras = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfMesBarraAnio = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat sdfDia = new SimpleDateFormat("dd");
        SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
        SimpleDateFormat sdfAnio = new SimpleDateFormat("yyyy");
        List<ReporteCompleto> listaReporteCompleto = new ArrayList<>();
        List<Form5Detalle> listaForm5TopCopy = new ArrayList<Form5Detalle>();
        List<Form5Detalle> listaForm5RmsCopy = new ArrayList<Form5Detalle>();
        List<Form5Detalle> listaForm5CustomerCopy = new ArrayList<Form5Detalle>();
        List<Form5Detalle> listaForm5FileCopy = new ArrayList<Form5Detalle>();
        List<InvoiceDetalle> listaInvoice = new ArrayList<InvoiceDetalle>();
        List<InvoiceDetalle> listaContratos = new ArrayList<InvoiceDetalle>();
        List<InvoiceDetalle> listaTransferencia = new ArrayList<InvoiceDetalle>();

        Form5Detalle form5Detalle = new Form5Detalle();
        form5Detalle.setRegistroNumero(1);
        form5Detalle.setEntryNumber(this.auto.getStock());
        form5Detalle.setStockNumber(auto.getStock());
        form5Detalle.setSerialNumber(this.formularioActivoAuto.getId());
        if (this.formularioActivoAuto.getFecha_generacion() != null) {
            form5Detalle.setCreationDate(sdf.format(this.formularioActivoAuto.getSale_date()));
            form5Detalle.setCreationDateBarra(sdfBarras.format(this.formularioActivoAuto.getSale_date()));
        }
        form5Detalle.setDealerName(this.formularioActivoAuto.getDealer_name());
        form5Detalle.setDealerLicence(this.formularioActivoAuto.getDealer_licence());
        form5Detalle.setDealerAddress(this.formularioActivoAuto.getDealer_address());
        if (this.formularioActivoAuto.getFecha_fabricacion() != null) {
            form5Detalle.setDateFabricacion(sdfMesAnio.format(this.formularioActivoAuto.getFecha_fabricacion()));
        }

        form5Detalle.setMarca(this.formularioActivoAuto.getMake());
        form5Detalle.setModelo(this.formularioActivoAuto.getModel());
        NumberFormat n = NumberFormat.getCurrencyInstance();
        String valorMoneda = n.format(this.formularioActivoAuto.getPrecio_auto());
        form5Detalle.setPrecio(valorMoneda);
        String kilometrajeOriginal = formularioActivoAuto.getOdometer_reading();
        String resultado = "";
        int cantidadLetras = 0;
        for (int b = kilometrajeOriginal.length() - 1; b > -1; b--) {
            resultado = kilometrajeOriginal.charAt(b) + resultado;
            cantidadLetras++;
            if (cantidadLetras % 3 == 0 && cantidadLetras != kilometrajeOriginal.length()) {
                resultado = "," + resultado;
            }
        }
        form5Detalle.setKilometros(resultado + " Km");
        String regoYear = "";
        if (formularioActivoAuto.getRego() != null && formularioActivoAuto.getRego().trim().length() > 0 && formularioActivoAuto.getRego_exp_date() != null) {
            regoYear = formularioActivoAuto.getRego() + " " + sdf.format(formularioActivoAuto.getRego_exp_date());
        }

        form5Detalle.setRegistracion(regoYear);

        form5Detalle.setVin(this.formularioActivoAuto.getVin());
        form5Detalle.setNumeroMotor(this.formularioActivoAuto.getEngine_number());
        form5Detalle.setWrittenOff(this.formularioActivoAuto.isWritten_off());
        form5Detalle.setMajorModifications(this.formularioActivoAuto.isMajor_modifications());
        form5Detalle.setWaterDamage(this.formularioActivoAuto.isWater_damage());
        form5Detalle.setPpsr(this.formularioActivoAuto.getPpsr_code());
        form5Detalle.setGuarantee(this.formularioActivoAuto.isGuarantee());
        form5Detalle.setImportedSecondHand(this.formularioActivoAuto.isImported_second_hand());
        form5Detalle.setVentaFecha(sdfBarras.format(this.formularioActivoAuto.getSale_date()));
        n = NumberFormat.getCurrencyInstance();
        valorMoneda = n.format(this.formularioActivoAuto.getSale_price());
        form5Detalle.setVentaPrecio(valorMoneda);

        kilometrajeOriginal = this.formularioActivoAuto.getSale_odometer();
        resultado = "";
        cantidadLetras = 0;
        for (int b = kilometrajeOriginal.length() - 1; b > -1; b--) {
            resultado = kilometrajeOriginal.charAt(b) + resultado;
            cantidadLetras++;
            if (cantidadLetras % 3 == 0 && cantidadLetras != kilometrajeOriginal.length()) {
                resultado = "," + resultado;
            }
        }

        form5Detalle.setVentaKM(resultado + " Km");
        form5Detalle.setRmsNumber(this.formularioActivoAuto.getRms_numero());
        if (this.formularioActivoAuto.getRms_fecha() != null) {
            form5Detalle.setRmsDate(sdf.format(this.formularioActivoAuto.getRms_fecha()));
        }
        form5Detalle.setVentaCompradorDetalles(this.formularioActivoAuto.getDatosComprador());

        InvoiceDetalle invoiceDetalle = new InvoiceDetalle();
        if (this.ventaActual != null && this.ventaActual.getId() > -1 && this.ventaActual.getInvoice() != null && this.ventaActual.getInvoice().getId() > 0) {

            if (this.ventaActual.getInvoice() != null && this.ventaActual.getInvoice().getId() > -1) {
                String fechaInvoice = sdfBarras.format(this.ventaActual.getFecha());
                invoiceDetalle.setInvoiceDate(fechaInvoice);
                invoiceDetalle.setInvoiceNumero(this.ventaActual.getInvoice().getId() + "");
            }
            if (this.ventaActual.getSucursal() != null && this.ventaActual.getSucursal().getId() > -1) {
                Sucursal sucursal = this.ventaActual.getSucursal();

                String direccionSucursal = sucursal.getDireccion() + ", ";
                if (sucursal.getCod_postal() != null) {
                    Cod_postal codigoPostal = sucursal.getCod_postal();
                    direccionSucursal = direccionSucursal + codigoPostal.getSuburbio();
                }
                invoiceDetalle.setSucursalDireccion(direccionSucursal);
                invoiceDetalle.setSucursalTelefono(sucursal.getTelefono());
                invoiceDetalle.setSucursalAbn(sucursal.getAbn());
                invoiceDetalle.setSucursalEmail(sucursal.getEmail());
                String licenciaDealer = "";
                String nombreLicencia = "";
                if (sucursal.getLicence() != null && sucursal.getLicence().getId() > -1) {
                    licenciaDealer = sucursal.getLicence().getNumero();
                    nombreLicencia = sucursal.getLicence().getNombre();
                }
                invoiceDetalle.setSucursalNombre(nombreLicencia);
                invoiceDetalle.setLicenceDealer(licenciaDealer);
            }
            if (this.ventaActual.getClienteVenta() != null && this.ventaActual.getClienteVenta().getId() > 0) {
                invoiceDetalle.setClienteNumero(this.ventaActual.getClienteVenta().getId() + "");
            }
            if (this.ventaActual.getCliente_fecha_nacimiento() != null) {
                String DOBCliente = sdf.format(this.ventaActual.getCliente_fecha_nacimiento());
                invoiceDetalle.setClienteDOB(DOBCliente);
            }
            invoiceDetalle.setClienteDireccion(this.ventaActual.getCliente_direccion());

            if (this.ventaActual.getCliente_tipo() == Cliente.TIPO_CUSTOMER) {
                invoiceDetalle.setClienteLicence(this.ventaActual.getCliente_licence());
            } else {
                invoiceDetalle.setClienteLicence(this.ventaActual.getCliente_dealer_licence());
            }
            invoiceDetalle.setClienteNombre(this.ventaActual.getCliente_nombre() + " " + this.ventaActual.getCliente_apelllido());
            invoiceDetalle.setClienteEmail(this.ventaActual.getCliente_abn());
            invoiceDetalle.setCarMarca(this.ventaActual.getCar_make());
            invoiceDetalle.setCarModel(this.ventaActual.getCar_model());
            invoiceDetalle.setCarBodyType(this.ventaActual.getCar_body());
            invoiceDetalle.setCarVIN(this.ventaActual.getCar_vin());
            invoiceDetalle.setCarNumeroMotor(this.ventaActual.getCar_motor_numero());
            invoiceDetalle.setCarStockNumber(this.ventaActual.getCar_stock());
            invoiceDetalle.setCarColor(this.ventaActual.getCar_color());
            invoiceDetalle.setCarOdometer(this.ventaActual.getCar_odometer());
            invoiceDetalle.setCarTransmicion(this.ventaActual.getCar_transmicion());
            invoiceDetalle.setCarDescripcion(this.ventaActual.getCar_descripcion());
            if (this.ventaActual.getCar_rego_exp() != null) {
                String fechaRegoExp = sdf.format(this.ventaActual.getCar_rego_exp());
                invoiceDetalle.setCarRegoExp(fechaRegoExp);
            }
            invoiceDetalle.setCarRego(this.ventaActual.getCar_rego());
            if (this.ventaActual.getCar_built() != null) {
                String fechaBuilt = sdfMesBarraAnio.format(this.ventaActual.getCar_built());
                invoiceDetalle.setCarBuilt(fechaBuilt);
            }
            if (this.ventaActual.getCar_compilance() != null) {
                String fechaCompliance = sdfMesBarraAnio.format(this.ventaActual.getCar_compilance());
                invoiceDetalle.setCarCompliance(fechaCompliance);
            }
            invoiceDetalle.setCarMontoConGST("$" + this.ventaActual.getValor());
            float montoSinGST = (this.ventaActual.getValor() * 100) / 110;
            int montoSinGSTInt = (int) (montoSinGST * 100);
            montoSinGST = ((float) (montoSinGSTInt)) / 100;

            float montoGST = this.ventaActual.getValor() - montoSinGST;
            int montoGSTInt = (int) (montoGST * 100);
            montoGST = ((float) (montoGSTInt)) / 100;

            invoiceDetalle.setCarMontoGST("$" + montoGST);
            invoiceDetalle.setCarMontoSinGST("$" + montoSinGST);
            invoiceDetalle.setClientePhone(this.ventaActual.getCliente_telefono());
            invoiceDetalle.setMonto_pagado("$" + this.ventaActual.getMonto_pagado());
            invoiceDetalle.setMonto_adeudado("$" + this.ventaActual.getMonto_adeudado());
            String fechaDelivery = "";
            if (this.ventaActual.getFecha_delivery() != null) {
                fechaDelivery = sdfBarras.format(this.ventaActual.getFecha_delivery());
            }
            invoiceDetalle.setFecha_delivery(fechaDelivery);

            invoiceDetalle.setClienteMobile(this.ventaActual.getCliente_mobile());
            invoiceDetalle.setCliente_tipo(this.ventaActual.getCliente_tipo());
            invoiceDetalle.setCliente_apellido(this.ventaActual.getCliente_apelllido());
            invoiceDetalle.setCliente_state(this.ventaActual.getCliente_state());
            invoiceDetalle.setCliente_codigo_postal(this.ventaActual.getCliente_codigo_postal());
            if (this.ventaActual.getCliente_fecha_nacimiento() != null) {
                invoiceDetalle.setCliente_dob_dia(sdfDia.format(this.ventaActual.getCliente_fecha_nacimiento()));
                invoiceDetalle.setCliente_dob_mes(sdfMes.format(this.ventaActual.getCliente_fecha_nacimiento()));
                invoiceDetalle.setCliente_dob_year(sdfAnio.format(this.ventaActual.getCliente_fecha_nacimiento()));
            }
            invoiceDetalle.setCliente_abn(this.ventaActual.getCliente_abn());
            invoiceDetalle.setCliente_dealer_licence(this.ventaActual.getCliente_dealer_licence());
            invoiceDetalle.setCliente_acn(this.ventaActual.getCliente_acn());
            invoiceDetalle.setVenta_dia(sdfDia.format(this.ventaActual.getFecha()));
            invoiceDetalle.setVenta_mes(sdfMes.format(this.ventaActual.getFecha()));
            invoiceDetalle.setVenta_year(sdfAnio.format(this.ventaActual.getFecha()));
            invoiceDetalle.setCar_year(this.ventaActual.getCar_year());
            if (this.ventaActual.getSucursal().getCod_postal() != null) {
                invoiceDetalle.setSucursal_codigo_postal(this.ventaActual.getSucursal().getCod_postal().getCodigo_postal() + "");
                invoiceDetalle.setSucursal_state(this.ventaActual.getSucursal().getCod_postal().getEstado());
            }

            if (this.ventaActual.getDelivery_name() != null && this.ventaActual.getDelivery_name().trim().length() > 0) {
                invoiceDetalle.setDelivery_name(this.ventaActual.getDelivery_name());
                invoiceDetalle.setDelivery_address(this.ventaActual.getDelivery_address());
                invoiceDetalle.setDelivery_abn(this.ventaActual.getDelivery_abn());
            } else {
                invoiceDetalle.setDelivery_name(invoiceDetalle.getClienteNombre());
                invoiceDetalle.setDelivery_address(invoiceDetalle.getClienteDireccion());
                invoiceDetalle.setDelivery_abn(invoiceDetalle.getDelivery_abn());
            }
        }

        for (int l = 0; l < this.listaFormulariosImprimirSeleccionados.size(); l++) {
            Formulario_imprimir formularioImprimir = this.listaFormulariosImprimirSeleccionados.get(l);
            if (formularioImprimir.getNombre().trim().equalsIgnoreCase("topCopy")) {
                for (int a = 0; a < formularioImprimir.getCopias(); a++) {
                    listaForm5TopCopy.add(form5Detalle);
                }
            }
            if (formularioImprimir.getNombre().trim().equalsIgnoreCase("rmsCopy")) {
                for (int a = 0; a < formularioImprimir.getCopias(); a++) {
                    listaForm5RmsCopy.add(form5Detalle);
                }
            }
            if (formularioImprimir.getNombre().trim().equalsIgnoreCase("customerCopy")) {
                for (int a = 0; a < formularioImprimir.getCopias(); a++) {
                    listaForm5CustomerCopy.add(form5Detalle);
                }
            }
            if (formularioImprimir.getNombre().trim().equalsIgnoreCase("fileCopy")) {
                for (int a = 0; a < formularioImprimir.getCopias(); a++) {
                    listaForm5FileCopy.add(form5Detalle);
                }
            }
            if (formularioImprimir.getNombre().trim().equalsIgnoreCase("factura")) {
                for (int a = 0; a < formularioImprimir.getCopias(); a++) {
                    listaInvoice.add(invoiceDetalle);
                }
            }

            if (formularioImprimir.getNombre().trim().equalsIgnoreCase("contrato")) {
                for (int a = 0; a < formularioImprimir.getCopias(); a++) {
                    listaContratos.add(invoiceDetalle);
                }
            }
            if (formularioImprimir.getNombre().trim().equalsIgnoreCase("transferencia")) {
                for (int a = 0; a < formularioImprimir.getCopias(); a++) {
                    listaTransferencia.add(invoiceDetalle);
                }
            }

            System.out.println("Imprimir: " + formularioImprimir.getNombre() + " copias: " + formularioImprimir.getCopias());
        }
        ReporteCompleto reporteCompleto = new ReporteCompleto();
        reporteCompleto.setListaTopCopy(listaForm5TopCopy);
        reporteCompleto.setListaRmsCopy(listaForm5RmsCopy);
        reporteCompleto.setListaCustomerCopy(listaForm5CustomerCopy);
        reporteCompleto.setListaFileCopy(listaForm5FileCopy);
        reporteCompleto.setListaInvoice(listaInvoice);
        reporteCompleto.setListaContrato(listaContratos);
        reporteCompleto.setLista_transferencia(listaTransferencia);
        listaReporteCompleto.add(reporteCompleto);

        try {

            File sumary = new File("reportes\\completo.jrxml");

            File sumary_taxes = new File("reportes\\f5_topCopy.jrxml");

            String reportPath_base = sumary.getAbsolutePath().replace("completo.jrxml", "");
            String reportPath = sumary.getAbsolutePath();
            String reportPath_taxes = sumary_taxes.getAbsolutePath();

            JasperReport jasperMasterReport = JasperCompileManager.compileReport(reportPath);
            JasperReport jasperSubreportReport_taxes = JasperCompileManager.compileReport(reportPath_taxes);

            //Complete Params
            Map<String, Object> params = new HashMap<>();

            //SUBREPORT PARAMS
            params.put("subReportBeanList", jasperSubreportReport_taxes);
            params.put("SUBREPORT_DIR", reportPath_base);

            // Get your data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listaReporteCompleto);

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

    public void verDetalleAlquiler() {
        if (this.alquilerAuto == null || this.alquilerAuto.getId() < 1) {
            return;
        }

        if (this.alquilerAuto != null && this.alquilerAuto.getId() > -1 && this.alquilerAuto.getEstado() == Alquiler.ESTADO_CARGANDO
                && this.alquilerAuto.getUsuario() != null && usuarioLogueado != null
                && this.alquilerAuto.getUsuario().getId() != usuarioLogueado.getId()) {
            if (usuarioLogueado.getEs_super() != 4 && usuarioLogueado.getEs_super() != 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Agreement: " + this.alquilerAuto.getId() + " is locked by:" + this.alquilerAuto.getUsuario().getNombre() + " " + this.alquilerAuto.getUsuario().getApellido() + " since: " + this.alquilerAuto.getFecha()));
                return;
            }
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerDato", this.alquilerAuto.getId() + "");
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

    public void editarAuto(boolean mostrarMensaje) {
        try {
            if (validarEditarAuto()) {

                autoPrecioEJB.saveListaAutoPrecioDefinitiva(listaAutosPrecio, auto);
                List<Auto_opcion> listaOpcionesDefinitivas = new ArrayList<>();
                for (int j = 0; j < this.listaOpcionesSeleccionadas.size(); j++) {
                    int idOpcionSelect = Integer.parseInt(listaOpcionesSeleccionadas.get(j));
                    Opcion opcionSelecc = opcionEJB.find(idOpcionSelect);
                    if (opcionSelecc != null) {
                        Auto_opcion autoOpcion = new Auto_opcion();
                        autoOpcion.setAuto(auto);
                        autoOpcion.setOpcion(opcionSelecc);
                        listaOpcionesDefinitivas.add(autoOpcion);
                        System.out.println(opcionSelecc.getNombre());
                    }
                }
                autoOpcionEJB.saveListaAutoPrecioDefinitiva(listaOpcionesDefinitivas, auto);
                if (auto.getLabel() != null && auto.getLabel().getId() == -1) {
                    auto.setLabel(null);
                }
                if (this.clienteActualVenta != null && this.clienteActualVenta.getNombre() != null && this.clienteActualVenta.getNombre().trim().length() > 0
                        && this.clienteActualVenta.getApellido() != null && this.clienteActualVenta.getApellido().trim().length() > 0) {
                    if (this.clienteActualVenta.getId() == -1) {
                        this.clienteEJB.create(clienteActualVenta);
                    } else {
                        this.clienteEJB.edit(clienteActualVenta);
                    }
                    this.ventaActual.setClienteVenta(clienteActualVenta);
                }
                if (auto.getTipoCompra() != null && auto.getTipoCompra().getId() == -1) {
                    auto.setTipoCompra(null);
                }

                if (usuarioLogueado != null && auto.getVIN() != null) {
                    String available = "";
                    if (auto.isDisponible()) {
                        available = "true";
                    } else {
                        available = "false";
                    }
                    System.out.println("El usuario: " + usuarioLogueado.getNombre() + " modifico el auto: " + auto.getVIN() + " Available: " + available);

                }
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    if (this.auto.getFecha_vencimiento_rego() != null) {
                        System.out.println("se actualiza auto: " + auto.getRego() + " fecha rego: " + sdf.format(auto.getFecha_vencimiento_rego()) + " usuario: " + this.usuarioLogueado.getEmail());
                    } else {
                        System.out.println("se actualiza auto: " + auto.getRego() + "sin fecha rego usuario: " + this.usuarioLogueado.getEmail());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                autoEJB.edit(auto);

                if (this.auto.isDisponible() ^ this.autoSeleccionado.isDisponible()) {
                    if (this.webActualizada == false) {
                        this.webActualizada = true;
                        this.generarCSV();
                    }
                }

                if (auto.getLabel() == null) {
                    auto.setLabel(new Label());
                }
                if (auto.getTipoCompra() == null) {
                    auto.setTipoCompra(new TipoCompra());
                }

                if (mostrarMensaje) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited correctly"));
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Edit failed"));
            e.printStackTrace();
        }
        //   Cuando haga enter tiene que tomar el editar, no el boton volver
        //         Hacer el eliminar
    }

    public List<State> getListaStates() {
        return listaStates;
    }

    public void setListaStatess(List<State> listaStates) {
        this.listaStates = listaStates;
    }

    private List<String[]> createCsvDataCarDealer() {
        List<String[]> list = new ArrayList<>();
        String[] header = {"stock", "year", "make", "model", "body", "km", "drivetrain", "seat", "color", "images", "price", "fuel", "cilindros", "engine", "doors", "transmision", "video", "badge", "vin", "publication", "rego"};
        list.add(header);
        listaAutosDisponiblesWeb = autoEJB.getListaAutoDisponiblesWeb();
        if (listaAutosDisponiblesWeb == null) {
            listaAutosDisponiblesWeb = new ArrayList<>();
        }
        listaAutosDisponiblesWeb.addAll(autoEJB.getAutosVendidosNoMarcados());
        for (int a = 0; a < this.listaAutosDisponiblesWeb.size(); a++) {
            Auto auto = this.listaAutosDisponiblesWeb.get(a);
            String[] data = new String[21];
            data[0] = auto.getStock() + "";
            data[1] = auto.getAño() + "";
            data[2] = auto.getMarca().getNombre();
            data[3] = auto.getModelo().getNombre();
            data[4] = auto.getTipo_body().getNombre();
            data[5] = auto.getKilometraje();
            data[6] = auto.getDrivetrain().getNombre();
            data[7] = auto.getAsientos() + "";
            data[8] = auto.getColor();
            data[9] = this.getImagenesStringCarDealer(auto);
            data[10] = this.getPrecioAuto(auto);
            //data[11] = this.getDescripcionPrecios(auto);

            //data[13] = this.getOpciones(auto);
            //data[14] = this.getEtiquetaSold(auto);
            data[11] = auto.getTipo_combustible().getNombre();
            data[12] = auto.getCilindros() + "";
            data[13] = auto.getMotor();
            data[14] = auto.getPuertas() + "";
            data[15] = getTransmisionAuto(auto);
            data[16] = "";//getURLVideo();
            data[17] = getBadgeAuto(auto);
            data[18] = auto.getVIN();
            data[19] = this.getDetallePublicidad(auto);
            data[20] = auto.getRego();
            list.add(data);
            if (autoEJB.estaAutoAlquilado(auto)) {
                auto.setMarcado_vendido(true);
                try {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                    if (auto.getFecha_vencimiento_rego() != null) {
                        System.out.println("se actualiza auto: " + auto.getRego() + " creando CSV dealer fecha rego: " + sdf2.format(auto.getFecha_vencimiento_rego()) + " usuario: " + this.usuarioLogueado.getEmail());
                    } else {
                        System.out.println("se actualiza auto: " + auto.getRego() + " creando CSV dealer sin fecha rego usuario: " + this.usuarioLogueado.getEmail());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                autoEJB.edit(auto);
            }
        }
        return list;
    }

    public void generarCSVCarDealer() {
        try {
            String path = pathResource + "/" + "autosCarDealer.csv";
            File archivo = new File(path);
            if (archivo.exists()) {
                archivo.delete();
            }
            List<String[]> csvData = this.createCsvDataCarDealer();
            try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
                writer.writeAll(csvData);
            }
            FTPUploadFileDemo ftp = new FTPUploadFileDemo();
            ftp.subirArchivo(archivo);
            // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Cars updated correctly"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generarCSV() {
        try {
            this.cargarImagenesAuto();
            String path = pathResource + "/" + "autos.csv";
            File archivo = new File(path);
            if (archivo.exists()) {
                archivo.delete();
            }
            List<String[]> csvData = createCsvDataSimple();
            try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
                writer.writeAll(csvData);
            }
            this.generarCSVCarDealer();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Cars updated correctly"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImagenesStringCarDealer(Auto auto) {
        List<Auto_imagenes> listaImagenesAuto = autoImagenesEJB.getListaPorAuto(auto.getId());
        String campoImagenes = "";

        for (int a = 0; a < listaImagenesAuto.size(); a++) {
            Auto_imagenes autoImagen = listaImagenesAuto.get(a);
            if (autoImagen.isPublico()) {
                if (a > 0) {
                    campoImagenes = campoImagenes + "|";
                }
                campoImagenes = campoImagenes + "http://certifiedsystem.com.au/javax.faces.resource" + autoImagen.getUrl() + ".piantino";
            }
        }
        return campoImagenes;
    }

    public String getImagenesString(Auto auto) {
        List<Auto_imagenes> listaImagenesAuto = autoImagenesEJB.getListaPorAuto(auto.getId());
        String campoImagenes = "";

        for (int a = 0; a < listaImagenesAuto.size(); a++) {
            Auto_imagenes autoImagen = listaImagenesAuto.get(a);
            if (autoImagen.isPublico()) {
                if (a > 0) {
                    campoImagenes = campoImagenes + "-";
                }
                campoImagenes = campoImagenes + "http://certifiedsystem.com.au/javax.faces.resource" + autoImagen.getUrl() + ".piantino";
            }
        }
        return campoImagenes;
    }

    public String getPrecioAuto(Auto auto) {
        List<Auto_precios> listaPreciosAuto = autoPrecioEJB.getPrecioAutosPorAuto(auto.getId());
        String precioDefecto = "";
        for (int a = 0; a < listaPreciosAuto.size(); a++) {
            Auto_precios autoPrecio = listaPreciosAuto.get(a);
            if (autoPrecio.isDefecto()) {
                precioDefecto = autoPrecio.getMonto() + "";
            }
        }
        if (precioDefecto.trim().length() == 0 && listaPreciosAuto.size() > 0) {
            precioDefecto = listaPreciosAuto.get(0).getMonto() + "";
        }
        return precioDefecto;
    }

    public String getDetallePublicidad(Auto auto) {
        String detallePublicidad = "";
        if (auto.isUsoDescripcionEspecifica()) {
            detallePublicidad = auto.getDescripcion();
        } else {
            List<Publicidad> listaPublicidad = publicidadEJB.findAll();
            if (listaPublicidad.size() > 0) {
                detallePublicidad = listaPublicidad.get(0).getTexto();
            }
        }
        return detallePublicidad;
    }

    public String getOpciones(Auto auto) {
        String opciones = "";
        if (auto.getAire_acondicionado() == 0) {
            opciones = "Air Conditioning";
        }
        List<Auto_opcion> listaOpciones = autoOpcionEJB.getOpcionesPorAuto(auto);
        for (int a = 0; a < listaOpciones.size(); a++) {
            Auto_opcion autoOpc = listaOpciones.get(a);
            if (opciones.trim().length() > 0) {
                opciones = opciones + ";";
            }
            opciones = opciones + autoOpc.getOpcion().getNombre();
        }
        return opciones;
    }

    public String getEtiquetaSold(Auto auto) {
        if (autoEJB.estaAutoAlquilado(auto)) {
            return "sold";
        } else {
            return "";
        }
    }

    private List<String[]> createCsvDataSimple() {
        List<String[]> list = new ArrayList<>();
        String[] header = {"stock", "year", "make", "model", "body", "km", "drivetrain", "seat", "color", "images", "price", "secondary title", "overview", "options", "sold", "fuel", "cilindros", "engine", "doors", "transmision", "video", "badge"};
        list.add(header);
        listaAutosDisponiblesWeb = autoEJB.getListaAutoDisponiblesWeb();
        if (listaAutosDisponiblesWeb == null) {
            listaAutosDisponiblesWeb = new ArrayList<>();
        }
        listaAutosDisponiblesWeb.addAll(autoEJB.getAutosVendidosNoMarcados());
        for (int a = 0; a < this.listaAutosDisponiblesWeb.size(); a++) {
            Auto auto = this.listaAutosDisponiblesWeb.get(a);
            String[] data = new String[22];
            data[0] = auto.getStock() + "";
            data[1] = auto.getAño() + "";
            data[2] = auto.getMarca().getNombre();
            data[3] = auto.getModelo().getNombre();
            data[4] = auto.getTipo_body().getNombre();
            data[5] = auto.getKilometraje();
            data[6] = auto.getDrivetrain().getNombre();
            data[7] = auto.getAsientos() + "";
            data[8] = auto.getColor();
            data[9] = this.getImagenesString(auto);
            data[10] = this.getPrecioAuto(auto);
            data[11] = "";// this.getDescripcionPrecios(auto);
            data[12] = this.getDetallePublicidad(auto);
            data[13] = this.getOpciones(auto);
            data[14] = this.getEtiquetaSold(auto);
            data[15] = auto.getTipo_combustible().getNombre();
            data[16] = auto.getCilindros() + "";
            data[17] = auto.getMotor();
            data[18] = auto.getPuertas() + "";
            data[19] = getTransmisionAuto(auto);
            data[20] = "";//getURLVideo();
            data[21] = getBadgeAuto(auto);
            list.add(data);
            if (autoEJB.estaAutoAlquilado(auto)) {
                auto.setMarcado_vendido(true);
                try {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                    if (auto.getFecha_vencimiento_rego() != null) {
                        System.out.println("se actualiza auto: " + auto.getRego() + " creando CSV dealer fecha rego: " + sdf2.format(auto.getFecha_vencimiento_rego()) + " usuario: " + this.usuarioLogueado.getEmail());
                    } else {
                        System.out.println("se actualiza auto: " + auto.getRego() + " creando CSV dealer sin fecha rego usuario: " + this.usuarioLogueado.getEmail());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                autoEJB.edit(auto);
            }
        }
        return list;
    }

    public String getTransmisionAuto(Auto auto) {
        if (auto.getTransmision() == Auto.AUTOMATIC) {
            return Auto.automaticExcel;
        } else {
            return Auto.manualExcel;
        }
    }

    private String getBadgeAuto(Auto auto) {
        if (auto.getLabel() != null && auto.getLabel().getId() > -1) {
            return auto.getLabel().getNombre();
        } else {
            return "";
        }
    }

    public String leer(Auto marSelecc) {
        autoSeleccionado = comentarController.getAutoSeleccionada();
        this.setAccion("M");
        return "/pantallas/DatosAuto";
    }

    public Auto getAutoSeleccionado() {
        return autoSeleccionado;
    }

    public void setAutoSeleccionado(Auto auto) {
        this.autoSeleccionado = auto;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public List<Modelo> getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(List<Modelo> listaModelos) {
        this.listaModelos = listaModelos;
    }

    public List<Modelo> getModelosDeMarca() {
        try {
            if (this.auto.getMarca().getId() != -1) {
                this.listaModelos = modeloEJB.buscarPorMarca(this.auto.getMarca().getId());
            } else if (this.listaMarcas.size() > 0) {
                this.listaModelos = modeloEJB.buscarPorMarca(this.listaMarcas.get(0).getId());
            }
        } catch (Exception e) {

        }
        return listaModelos;
    }

    public void registrar() {
        try {
            if (validarRegistroAuto()) {
                if (auto.getLabel() != null && auto.getLabel().getId() == -1) {
                    auto.setLabel(null);
                }

                autoEJB.create(auto);

                String imagenURL = "";

                for (int a = 0; a < this.listaAutoImagenes.size(); a++) {
                    Auto_imagenes autoImagen = this.listaAutoImagenes.get(a);
                    if (a == 0) {
                        imagenURL = autoImagen.getUrl();
                    } else {
                        imagenURL = "," + autoImagen.getUrl();
                    }
                    imagenURL += autoImagen.getUrl() + ",";
                    autoImagen.setAuto(auto);
                    autoImagen.setOrden(a);
                    if (autoImagen.getId() == -1) {
                        autoImagenesEJB.create(autoImagen);
                    } else {
                        autoImagenesEJB.edit(autoImagen);
                    }
                }

                autoPrecioEJB.saveListaAutoPrecioDefinitiva(listaAutosPrecio, auto);
                List<Auto_opcion> listaOpcionesDefinitivas = new ArrayList<>();
                for (int j = 0; j < this.listaOpcionesSeleccionadas.size(); j++) {
                    int idOpcionSelect = Integer.parseInt(listaOpcionesSeleccionadas.get(j));
                    Opcion opcionSelecc = opcionEJB.find(idOpcionSelect);
                    if (opcionSelecc != null) {
                        Auto_opcion autoOpcion = new Auto_opcion();
                        autoOpcion.setAuto(auto);
                        autoOpcion.setOpcion(opcionSelecc);
                        listaOpcionesDefinitivas.add(autoOpcion);
                    }
                }
                autoOpcionEJB.saveListaAutoPrecioDefinitiva(listaOpcionesDefinitivas, auto);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
                auto = new Auto();
                auto.setStock(this.autoEJB.getStockSugerido());
                this.listaOpcionesSeleccionadas.clear();
                this.listaAutoImagenes.clear();
                this.autoPrecioActual = new Auto_precios();
                this.listaAutosPrecio.clear();
            } else {

                //    autoEJB.create(auto);
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "No fue registrado"));
                //   Auto autoSetear = new Auto();
                // this.setAuto(autoSetear);
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Registration failed"));
            e.printStackTrace();
        }

    }

    private boolean validarRegistroAuto() {
        boolean correcto = true;
        try {
            Auto autoExistente = null;

            if (this.listaAutosPrecio == null || this.listaAutosPrecio.size() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the sale price of the car"));
                return false;
            }

            if (auto.getNro_motor().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a engine number"));
                return false;
            }

            if (auto.getVIN().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a VIN number"));
                return false;
            }

            if (auto.getMarca() != null && auto.getMarca().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a brand"));
                return false;
            }

            if (auto.getModelo() != null && auto.getModelo().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a model"));
                return false;
            }

            if (auto.getNro_motor().trim().length() > 0) {
                autoExistente = autoEJB.buscarPorNroMotor(auto.getNro_motor().trim());
                if (autoExistente != null && autoExistente.getId() > -1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with engine number entered"));
                    correcto = false;
                }
            }

            if (auto.getStock() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a stock number"));
                correcto = false;
            }

            Auto autoStockExistente = autoEJB.buscarPorStock(auto.getStock());
            if (autoStockExistente != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with the stock number entered"));
                correcto = false;
            }

            if (auto.getNro_chasis().trim().length() > 0) {
                autoExistente = autoEJB.buscarPorNroChasis(auto.getNro_chasis().trim());
                if (autoExistente != null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with chassis number entered"));
                    correcto = false;
                }
            }
            if (auto.getRego().trim().length() > 0) {
                autoExistente = autoEJB.buscarPorRego(auto.getRego().trim());
                if (autoExistente != null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with Rego number entered"));
                    correcto = false;
                }
                if (auto.getFecha_vencimiento_rego() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the expiration date of the registration"));
                    return false;
                }
            }
            Date hoy = Calendar.getInstance().getTime();
            if (auto.getFecha_vencimiento_rego() != null) {
                if (auto.getRego().trim().length() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "If you enter due date, you must also enter the Rego number"));
                    correcto = false;
                }
                /* if (auto.getFecha_vencimiento_rego().before(hoy)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The expiration date of the Rego cannot be passed"));
                    correcto = false;
                }*/

            }
            if (auto.getVIN().trim().length() > 0) {
                autoExistente = autoEJB.buscarPorVin(auto.getVIN().trim());
                if (autoExistente != null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with VIN number entered"));
                    correcto = false;
                }
            }

            if (this.auto.getClienteProveedor() != null && this.auto.getClienteProveedor().getId() == -1 && this.auto.getClienteProveedor().getNombre().trim().length() > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a valid supplier or create a new one"));
                return false;
            }

            if (this.auto.getClienteProveedor() != null && this.auto.getClienteProveedor().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a valid supplier"));
                return false;
            }

            if (this.auto.getKilometraje().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the current mileage"));
                return false;
            }

            try {
                Long.parseLong(this.auto.getKilometraje());
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The current milage have to be a number"));
                return false;
            }

            if (this.auto.getBuildDate() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the build date"));
                return false;
            }

            if (this.auto.getAño() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The year of the car must be a valid number"));
                return false;
            }

            if (this.auto.getTipoCompra() == null || (this.auto.getTipoCompra() != null && this.auto.getTipoCompra().getId() == -1)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a type of purchase"));
                return false;
            }

            if (this.auto.getValor_compra() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a valid amount of the purchase"));
                return false;
            }

            if (this.auto.getFecha_compra() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the purchase date"));
                return false;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Error validating registration"));
        }
        return correcto;
    }

    private boolean validarEditarAuto() {
        boolean correcto = true;
        try {

            if (this.listaAutosPrecio == null || this.listaAutosPrecio.size() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the sale price of the car"));
                return false;
            }

            if (auto.getNro_motor().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a engine number"));
                return false;
            }

            if (auto.getVIN().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a VIN number"));
                return false;
            }

            if (auto.getMarca() != null && auto.getMarca().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a brand"));
                return false;
            }

            if (auto.getModelo() != null && auto.getModelo().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a model"));
                return false;
            }

            Auto autoExistente = null;

            if (auto.getStock() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the stock number"));
                return false;
            }

            Auto autoStock = autoEJB.buscarPorStock(auto.getStock());
            if (autoStock != null && autoStock.getId() != auto.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There are another car with the stock number entered"));
                return false;
            }

            if (auto.getNro_motor().trim().length() > 0) {
                autoExistente = autoEJB.buscarPorNroMotor(auto.getNro_motor().trim());
                if (autoExistente != null && autoExistente.getId() != auto.getId()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with engine number entered"));
                    correcto = false;
                }
            }
            if (auto.getNro_chasis().trim().length() > 0) {
                autoExistente = autoEJB.buscarPorNroChasis(auto.getNro_chasis().trim());
                if (autoExistente != null && autoExistente.getId() != auto.getId()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with chassis number entered"));
                    correcto = false;
                }
            }
            if (auto.getRego().trim().length() > 0) {
                autoExistente = autoEJB.buscarPorRego(auto.getRego().trim());
                if (autoExistente != null && autoExistente.getId() != auto.getId()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with Rego number entered"));
                    correcto = false;
                }
                if (auto.getFecha_vencimiento_rego() == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a date of expiration of the registration"));
                    correcto = false;
                }
            }

            if (this.auto.getClienteProveedor() != null && this.auto.getClienteProveedor().getId() == -1 && this.auto.getClienteProveedor().getNombre().trim().length() > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a valid supplier or create a new one"));
                return false;
            }

            if (this.auto.getClienteProveedor() != null && this.auto.getClienteProveedor().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a valid supplier"));
                return false;
            }

            Date hoy = Calendar.getInstance().getTime();
            if (auto.getFecha_vencimiento_rego() != null) {
                if (auto.getRego().trim().length() == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "If you enter due date, you must also enter the Rego number"));
                    correcto = false;
                }
                /*if (auto.getFecha_vencimiento_rego().before(hoy)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The expiration date of the Rego cannot be passed"));
                    correcto = false;
                }*/
            }
            if (auto.getVIN().trim().length() > 0) {
                autoExistente = autoEJB.buscarPorVin(auto.getVIN().trim());
                if (autoExistente != null && autoExistente.getId() != auto.getId()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a car with VIN number entered"));
                    correcto = false;
                }
            }

            if (this.auto.getKilometraje().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the current mileage"));
                return false;
            }

            try {
                Long.parseLong(this.auto.getKilometraje());
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The current milage have to be a number"));
                return false;
            }

            if (this.auto.getAño() < 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The year of the car must be a valid number"));
                return false;
            }

            if (this.auto.getBuildDate() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the build date"));
                return false;
            }

            if (this.auto.getSucursal() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to assign a branch"));
                return false;
            }

            if (this.auto.getSucursal().getId() == -1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to assign a branch"));
                return false;
            }

            if (this.auto.getSucursal().getLicence() == null || (this.auto.getSucursal().getLicence() != null && this.auto.getSucursal().getLicence().getId() == -1)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to assign a branch"));
                return false;
            }

            if (this.auto.getBuildDate() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a build date"));
                return false;
            }

            if (this.auto.getMarca() == null || (this.auto.getMarca() != null && this.auto.getMarca().getId() == -1)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to assign a brand"));
                return false;
            }

            if (this.auto.getModelo() == null || (this.auto.getModelo() != null && this.auto.getModelo().getId() == -1)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to assign a model"));
                return false;
            }

            if (this.auto.getAño() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the year of the car"));
                return false;
            }

            if (this.auto.getValor_compra() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the price of the purchase"));
                return false;
            }

            if (this.auto.getKilometraje().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter current mileage"));
                return false;
            }

            if (this.auto.getVIN().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter VIN number"));
                return false;
            }

            if (this.auto.getTipoCompra() == null || (this.auto.getTipoCompra() != null && this.auto.getTipoCompra().getId() == -1)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a type of purchase"));
                return false;
            }

            if (this.auto.getValor_compra() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a valid amount of the purchase"));
                return false;
            }

            if (this.auto.getFecha_compra() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the purchase date"));
                return false;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Error validating registration"));
        }
        return correcto;
    }

    public List<Drivetrain> getListaDrivetrain() {
        return listaDrivetrain;
    }

    public void setListaDrivetrain(List<Drivetrain> listaDrivetrain) {
        this.listaDrivetrain = listaDrivetrain;
    }

    public void cargarImagen() {
        System.out.println("Se va a cargar la imagen del file: " + file.getFileName());
        if (file == null) {
            return;
        }
        File archivoOriginal = this.uploadedFileToFileConverter(file);

        String carpetaArchivo = "/imagenes";

        File carpetaSource = new File(pathResource);
        if (carpetaSource.exists() == false) {
            if (carpetaSource.mkdir() == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "We can't create the folder:" + carpetaSource.getAbsolutePath()));
                return;
            }
        }

        File carpetaArchivoFile = new File(pathResource + "/" + carpetaArchivo);
        if (carpetaArchivoFile.exists() == false) {
            if (carpetaArchivoFile.mkdir() == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "We can't create the folder:" + carpetaArchivoFile.getAbsolutePath()));
                return;
            }
        }

        String fileName = archivoOriginal.toString();

        int index = fileName.lastIndexOf('.');
        String extension = "";
        if (index > 0) {
            extension = fileName.substring(index + 1);
            System.out.println("File extension is " + extension);
        }

        int nombreArchivoNumero = 0;
        File archivo = new File(pathResource + "/" + carpetaArchivo + "/" + nombreArchivoNumero + "." + extension);
        while (archivo.exists()) {
            nombreArchivoNumero++;
            System.out.println("Vamos a ver si existe: " + pathResource + "/" + carpetaArchivo + "/" + nombreArchivoNumero + "." + extension);
            archivo = new File(pathResource + "/" + carpetaArchivo + "/" + nombreArchivoNumero + "." + extension);
        }
        System.out.println("El nombre final es: " + pathResource + "/" + carpetaArchivo + "/" + nombreArchivoNumero + "." + extension);

        /*     String nombreArchivoOriginal = archivoOriginal.getName();
        
        File archivo = new File(pathResource + "/" + carpetaArchivo + "/" + nombreArchivoOriginal);
        int copias = 0;
        while (archivo.exists()) {
            copias++;
            String nombreArchivo = copias + nombreArchivoOriginal;
            archivo = new File(pathResource + "/" + carpetaArchivo + "/" + nombreArchivo);
        }*/
        InputStream input = null;
        try {
            input = new FileInputStream(archivoOriginal);
            this.resize(input, archivo.toPath());
            /* if (this.copyFile(input, archivo).trim().length() > 0) {
                return;
            }*/
            if (archivo.exists()) {
                InputStream archivoInputStream = new FileInputStream(archivo);
                Auto_imagenes autoImagenes = new Auto_imagenes();
                autoImagenes.setAuto(this.auto);
                autoImagenes.setUrl(carpetaArchivo + "/" + archivo.getName());
                autoImagenes.setImagen(IOUtils.toByteArray(archivoInputStream));
                if (this.auto != null && this.auto.getId() > -1) {
                    autoImagenesEJB.create(autoImagenes);
                }
                this.listaAutoImagenes.add(autoImagenes);
                if (this.auto != null && this.auto.getId() > -1) {
                    this.grabarOrden();
                }
                FacesMessage message = new FacesMessage("Successful", archivoOriginal.getName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Se termino de cargar la imagen del file: " + file.getFileName());
    }

    public String copyFile(InputStream in, File archivoDestino) {
        try {

            System.out.println("archivo sera creado: " + archivoDestino.getAbsolutePath());
            // write the inputStream to a FileOutputStream

            OutputStream out = new FileOutputStream(archivoDestino);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public File uploadedFileToFileConverter(UploadedFile uf) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //Add you expected file encoding here:
        System.setProperty("file.encoding", "UTF-8");
        File newFile = new File(uf.getFileName());
        try {

            inputStream = uf.getInputStream();
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            //Do something with the Exception (logging, etc.)
        }
        return newFile;
    }

    public List<Auto_imagenes> getListaAutoImagenes() {
        return listaAutoImagenes;
    }

    public int getAncho(Auto_imagenes autoImagen) {
        try {
            InputStream input = new FileInputStream(new File(pathResource + "/" + autoImagen.getUrl()));
            BufferedImage originalImage = ImageIO.read(input);
            input.close();
            return originalImage.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 800;
    }

    public int getAlto(Auto_imagenes autoImagen) {
        try {
            InputStream input = new FileInputStream(new File(pathResource + "/" + autoImagen.getUrl()));
            BufferedImage originalImage = ImageIO.read(input);
            input.close();
            return originalImage.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 800;
    }

    public void setListaAutoImagenes(List<Auto_imagenes> listaAutoImagenes) {
        this.listaAutoImagenes = listaAutoImagenes;
    }

    public void onRowReorder(ReorderEvent event) {
        grabarOrden();
    }

    public void grabarOrden() {
        for (int a = 0; a < this.listaAutoImagenes.size(); a++) {
            Auto_imagenes autoImagen = this.listaAutoImagenes.get(a);
            autoImagen.setOrden(a);
            autoImagenesEJB.edit(autoImagen);
            System.out.print(autoImagen.getUrl());
        }

    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public void armarTituloAuto() {
        String titulo = "";
        if (this.auto.getMarca() != null) {
            titulo = titulo + this.auto.getMarca().getNombre() + " ";
        }
        if (this.auto.getModelo() != null) {
            titulo = titulo + this.auto.getModelo().getNombre() + " ";
        }
        if (this.auto.getAño() > 0) {
            titulo = titulo + this.auto.getAño();
        }
        this.auto.setTitulo(titulo);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) {
        synchronized (key) {
            System.out.print("Se asigna el file: " + event.getFile().getFileName());
            this.file = event.getFile();
            this.cargarImagen();
            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void handleMultiple() {

        if (files != null) {
            for (UploadedFile f : files.getFiles()) {
                this.file = f;
                this.cargarImagen();
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public List<Auto_precios> getListaAutosPrecio() {
        return listaAutosPrecio;
    }

    public void setListaAutosPrecio(List<Auto_precios> listaAutosPrecio) {
        this.listaAutosPrecio = listaAutosPrecio;
    }

    public Auto_precios getAutoPrecioActual() {
        return autoPrecioActual;
    }

    public void setAutoPrecioActual(Auto_precios autoPrecioActual) {
        this.autoPrecioActual = autoPrecioActual;
    }

    public void agregarPrecioAuto() {
        if (this.autoPrecioActual.getDescripcion() == null || this.autoPrecioActual.getDescripcion().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The description can't be empty"));
            return;
        }
        if (this.autoPrecioActual.getMonto() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The amount can't be zero o minus zero"));
            return;
        }
        if (this.autoPrecioActual.getTipo_tiempo() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select a time type"));
            return;
        }

        if (this.autoPrecioActual.isDefecto()) {
            for (int a = 0; a < this.listaAutosPrecio.size(); a++) {
                Auto_precios autoPrecio = this.listaAutosPrecio.get(a);
                if (autoPrecio.isDefecto()) {
                    autoPrecio.setDefecto(false);
                }
            }
        }

        this.listaAutosPrecio.add(this.autoPrecioActual);
        this.autoPrecioActual = new Auto_precios();

    }

    public String getStrDefecto(Auto_precios precioAuto) {
        if (precioAuto.isDefecto()) {
            return "Yes";
        }
        return "";
    }

    public void eliminarImagenProducto(Auto_imagenes autoImagen) {
        if (autoImagen != null) {
            this.listaAutoImagenes.remove(autoImagen);
            String urlImagen = autoImagen.getUrl();
            urlImagen = this.pathResource + "/" + urlImagen;
            File fileImagen = new File(urlImagen);
            if (fileImagen.exists()) {
                fileImagen.delete();
            }
            this.autoImagenesEJB.remove(autoImagen);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Has been successfully removed"));
        }
    }

    public void eliminarPrecioAuto(Auto_precios autoPrecio) {
        if (autoPrecio != null) {
            this.listaAutosPrecio.remove(autoPrecio);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Has been successfully removed"));
        }
    }

    public String getDescripcionTipoTiempo(int tipoTiempo) {
        if (tipoTiempo == 1) {
            return "Daily";
        } else if (tipoTiempo == 2) {
            return "Weekly";
        } else if (tipoTiempo == 3) {
            return "Monthly";
        } else if (tipoTiempo == 4) {
            return "Annually";
        } else {
            return "";
        }
    }

    public List<Opcion> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<Opcion> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    public List<String> getListaOpcionesSeleccionadas() {
        return listaOpcionesSeleccionadas;
    }

    public void setListaOpcionesSeleccionadas(List<String> listaOpcionesSeleccionadas) {
        this.listaOpcionesSeleccionadas = listaOpcionesSeleccionadas;
    }

    public List<Label> getListaLabels() {
        return listaLabels;
    }

    public void setListaLabels(List<Label> listaLabels) {
        this.listaLabels = listaLabels;
    }

    public void ponerRegoMayuscula() {
        if (this.auto.getRego() != null && this.auto.getRego().trim().length() > 0) {
            String rego = this.auto.getRego();
            rego = rego.toUpperCase();
            this.auto.setRego(rego);

            if (formularioActivoAuto != null && !formularioActivoAuto.getRego().trim().equalsIgnoreCase(this.auto.getRego())) {
                this.mensajeFormulario = "Notice is not updated";
                this.auto.setFormulario_actualizado(false);
            }
        }

    }

    public void actualizarMensaje() {
        if (this.formularioNOActualizado()) {
            this.mensajeFormulario = "Notice is not updated";
        } else {
            this.mensajeFormulario = "";
        }
    }

    public boolean formularioNOActualizado() {
        if (this.auto != null && this.auto.getId() > -1 && this.formularioActivoAuto != null
                && this.auto.getEstado() != Auto.ESTADO_VENDIDO) {
            if (this.formularioActivoAuto.getStock_number() != this.auto.getStock()) {
                return true;
            }
            if (this.formularioActivoAuto.getFecha_fabricacion().getTime() != this.auto.getBuildDate().getTime()) {
                return true;
            }
            if (!this.formularioActivoAuto.getModel().equalsIgnoreCase(this.auto.getModelo().getNombre())) {
                return true;
            }
            if (!this.formularioActivoAuto.getMake().equalsIgnoreCase(this.auto.getMarca().getNombre())) {
                return true;
            }
            if (this.formularioActivoAuto.getYear() != this.auto.getAño()) {
                return true;
            }
            if (!this.formularioActivoAuto.getRego().equalsIgnoreCase(this.auto.getRego())) {
                return true;
            }
            if (this.formularioActivoAuto != null && this.formularioActivoAuto.getRego_exp_date() != null && this.auto.getFecha_vencimiento_rego() != null) {
                if (this.formularioActivoAuto.getRego_exp_date().getTime() != this.auto.getFecha_vencimiento_rego().getTime()) {
                    return true;
                }
            }
            Auto_precios autoPrecio = this.autoPrecioEJB.getPrecioDefectoPrimeroAuto(this.auto.getId());
            if (autoPrecio != null) {
                if (this.formularioActivoAuto.getPrecio_auto() != autoPrecio.getMonto()) {
                    return true;
                }
            }
            if (!this.formularioActivoAuto.getOdometer_reading().equalsIgnoreCase(this.auto.getKilometraje())) {
                return true;
            }
            if (!this.formularioActivoAuto.getVin().equalsIgnoreCase(this.auto.getVIN())) {
                return true;
            }
            if (!this.formularioActivoAuto.getEngine_number().equalsIgnoreCase(this.auto.getNro_motor())) {
                return true;
            }
            if (this.formularioActivoAuto.isWritten_off()) {
                if (this.auto.isWritten_off() == false) {
                    return true;
                }
            } else {
                if (this.auto.isWritten_off()) {
                    return true;
                }
            }
            if (this.formularioActivoAuto.isWater_damage()) {
                if (this.auto.isWater_damage() == false) {
                    return true;
                }
            } else {
                if (this.auto.isWater_damage()) {
                    return true;
                }
            }

            if (this.formularioActivoAuto.isMajor_modifications()) {
                if (this.auto.isMajor_damage() == false) {
                    return true;
                }
            } else {
                if (this.auto.isMajor_damage()) {
                    return true;
                }
            }
            if (!this.formularioActivoAuto.getPpsr_code().equalsIgnoreCase(this.auto.getCertificate_number())) {
                return true;
            }
            if (this.formularioActivoAuto.isImported_second_hand()) {
                if (this.auto.isImported_second_hand() == false) {
                    return true;
                }
            } else {
                if (this.auto.isImported_second_hand()) {
                    return true;
                }
            }
            if (this.ventaActual != null && this.ventaActual.getId() > -1) {
                if (this.formularioActivoAuto.getSale_price() != this.ventaActual.getValor()) {
                    return true;
                }
                if (!this.formularioActivoAuto.getSale_odometer().equalsIgnoreCase(this.ventaActual.getCar_odometer())) {
                    return true;
                }
                if (!this.formularioActivoAuto.getRms_numero().equalsIgnoreCase(this.ventaActual.getRms_codigo())) {
                    return true;
                }
                if (this.formularioActivoAuto.getRms_fecha() != null && this.ventaActual.getRms_fecha() != null && this.formularioActivoAuto.getRms_fecha().getTime() != this.ventaActual.getRms_fecha().getTime()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void ponerVINMayuscula() {
        String vin = this.auto.getVIN();
        vin = vin.toUpperCase();
        this.auto.setVIN(vin);
        this.auto.setNro_chasis(vin);

        if (formularioActivoAuto != null && !formularioActivoAuto.getVin().trim().equalsIgnoreCase(this.auto.getVIN())) {
            this.mensajeFormulario = "Notice is not updated";
            this.auto.setFormulario_actualizado(false);
        }
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void onProveedorSelectNombre(SelectEvent event) {
        Object obj = listaNombreContactoSimilar.get(event.getObject());

        if (obj != null) {
            this.proveedorActual = (Cliente) obj;
            this.auto.setClienteProveedor(this.proveedorActual);
        }
    }

    public void onProveedorExpensesSelectNombre(SelectEvent event) {
        Object obj = listaNombreProveedorExpensesSimilar.get(event.getObject());

        if (obj != null) {
            this.proveedorActualExpenses = (Cliente) obj;
        }
    }

    public Cliente getProveedorActual() {
        return proveedorActual;
    }

    public void setProveedorActual(Cliente proveedorActual) {
        this.proveedorActual = proveedorActual;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public List<Cliente> getListaProveedoresSimilares() {
        return listaProveedoresSimilares;
    }

    public void setListaProveedoresSimilares(List<Cliente> listaProveedoresSimilares) {
        this.listaProveedoresSimilares = listaProveedoresSimilares;
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

    public Cliente getClienteActualVenta() {
        return clienteActualVenta;
    }

    public void setClienteActualVenta(Cliente clienteActualVenta) {
        this.clienteActualVenta = clienteActualVenta;
    }

    public void generarNotice(boolean mostrarMensaje) {

        if (this.validarEditarAuto()) {

            Formulario5 f5 = new Formulario5();
            f5.setEntry_number(this.auto.getStock());
            f5.setStock_number(this.auto.getStock());
            f5.setFecha_generacion(c.getTime());
            f5.setDealer_name(this.auto.getSucursal().getLicence().getNombre());
            Sucursal sucursal = this.auto.getSucursal();
            String direccion = sucursal.getDireccion() + ", " + sucursal.getCod_postal().getSuburbio() + ", " + sucursal.getCod_postal().getEstado() + ", " + sucursal.getCod_postal().getCodigo_postal();
            f5.setDealer_address(direccion);
            f5.setDealer_licence(this.auto.getSucursal().getLicence().getNumero());
            f5.setFecha_fabricacion(this.auto.getBuildDate());
            f5.setMake(this.auto.getMarca().getNombre());
            String modelo = this.auto.getModelo().getNombre();
            f5.setModel(modelo);
            f5.setYear(this.auto.getAño());
            SimpleDateFormat sdfFecha = new SimpleDateFormat("dd-MMM-yyyy");
            String regoFecha = this.auto.getRego();
            f5.setRego(regoFecha);
            f5.setRego_exp_date(this.auto.getFecha_vencimiento_rego());
            Auto_precios autoPrecio = this.autoPrecioEJB.getPrecioDefectoPrimeroAuto(this.auto.getId());
            if (autoPrecio != null) {
                f5.setPrecio_auto(autoPrecio.getMonto());
            }
            f5.setOdometer_reading(this.auto.getKilometraje());
            f5.setVin(this.auto.getVIN());
            f5.setEngine_number(this.auto.getNro_motor());
            f5.setWritten_off(this.auto.isWritten_off());
            f5.setWater_damage(this.auto.isWater_damage());
            f5.setMajor_modifications(this.auto.isMajor_damage());
            f5.setPpsr_code(this.auto.getCertificate_number());
            f5.setGuarantee(this.tieneGarantiaAuto(auto));
            String datosComprador = "";
            if (this.ventaActual != null && this.ventaActual.getId() > -1) {
                f5.setSale_date(this.ventaActual.getFecha());
                f5.setSale_price(this.ventaActual.getValor());
                f5.setSale_odometer(this.ventaActual.getCar_odometer());
                f5.setRms_numero(this.ventaActual.getRms_codigo());
                f5.setRms_fecha(this.ventaActual.getRms_fecha());
                if (this.ventaActual.getClienteVenta() != null && this.ventaActual.getClienteVenta().getId() > -1) {
                    Cliente clienteComprados = this.ventaActual.getClienteVenta();
                    datosComprador = clienteComprados.getNombre() + " " + clienteComprados.getApellido() + " " + clienteComprados.getDireccion() + ", " + clienteComprados.getSuburbio() + ", " + clienteComprados.getState().getNombre() + " " + clienteComprados.getCodigo_postal() + ", " + clienteComprados.getPais();
                }
                f5.setDatosComprador(datosComprador);
            }

            f5.setAuto(this.auto);
            f5.setImported_second_hand(this.auto.isImported_second_hand());
            f5.setEstado(Formulario5.ESTADO_ACTIVO);

            formularioEJB.create(f5);

            try {
                System.out.println("Se creo el formulario: " + f5.getId() + " para el auto: " + f5.getRego());
                if (f5.getRego_exp_date() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    System.out.println("Se creo el formulario: " + f5.getId() + " para el auto: " + f5.getRego() + " fecha rego: " + sdf.format(f5.getRego_exp_date()) + " usuario:" + this.usuarioLogueado.getEmail());
                } else {
                    System.out.println("Se creo el formulario: " + f5.getId() + " para el auto: " + f5.getRego() + " sin fecha rego usuario:" + this.usuarioLogueado.getEmail());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mostrarMensaje) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Notice generated correctly"));
            }
            this.setMensajeFormulario("");
            this.auto.setFormulario_actualizado(true);
            if (auto.getLabel() != null && auto.getLabel().getId() == -1) {
                auto.setLabel(null);
            }
            if (this.auto.getTipoCompra() != null && this.auto.getTipoCompra().getId() == -1) {
                this.auto.setTipoCompra(null);
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (this.auto.getFecha_vencimiento_rego() != null) {
                    System.out.println("se actualiza auto: " + auto.getRego() + " fecha rego: " + sdf.format(auto.getFecha_vencimiento_rego()) + " usuario: " + this.usuarioLogueado.getEmail());
                } else {
                    System.out.println("se actualiza auto: " + auto.getRego() + "sin fecha rego usuario: " + this.usuarioLogueado.getEmail());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            autoEJB.edit(this.auto);

            if (auto.getLabel() == null) {
                auto.setLabel(new Label());
            }
            if (this.auto.getTipoCompra() == null) {
                auto.setTipoCompra(new TipoCompra());
            }
            long idReciencreado = f5.getId();
            this.listaFormulariosAuto = this.formularioEJB.getListaFormularioAuto(this.auto.getId());
            for (int a = 0; a < this.listaFormulariosAuto.size(); a++) {
                Formulario5 formularioAuto = this.listaFormulariosAuto.get(a);
                if (formularioAuto.getId() != idReciencreado) {
                    formularioAuto.setEstado(Formulario5.ESTADO_CANCELADO);
                    formularioEJB.edit(formularioAuto);
                } else {
                    this.formularioActivoAuto = formularioAuto;
                }
            }
        }
    }

    public boolean tieneVentaActiva() {
        if (this.ventaActual != null && this.ventaActual.getId() > -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean tieneGarantiaAuto(Auto auto) {
        Calendar calen = Calendar.getInstance();
        calen.set(Calendar.YEAR, auto.getAño());
        calen.set(Calendar.MONTH, 0);
        calen.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println(sdf.format(calen.getTime()));
        long diferencia = c.getTime().getTime() - calen.getTime().getTime();
        int days = (int) (diferencia / (1000 * 60 * 60 * 24));
        long kilometrosActualAuto = Long.parseLong(this.auto.getKilometraje());
        int anio = (days / 365);
        if (anio < 10 && kilometrosActualAuto < 160000) {
            return true;
        } else {
            return false;
        }
    }

    public List<Formulario5> getListaFormulariosAuto() {
        return listaFormulariosAuto;
    }

    public void setListaFormulariosAuto(List<Formulario5> listaFormulariosAuto) {
        this.listaFormulariosAuto = listaFormulariosAuto;
    }

    public String getEstadoStrFormulario(Formulario5 form) {
        if (form != null) {
            if (form.getEstado() == Formulario5.ESTADO_ACTIVO) {
                return "Active";
            } else {
                return "Cancel";
            }
        }
        return "";
    }

    public String getFechaCreacionFormulario(Formulario5 form) {
        if (form != null) {
            Date fechaFormulario = form.getFecha_generacion();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(fechaFormulario);
        }
        return "";
    }

    public void imprimir(long idFormulario) {
        Formulario5 f5 = formularioEJB.find(idFormulario);
        Map<String, Object> params = new HashMap<>();
        params.put("registroNumero", 1);
        params.put("serialNumber", idFormulario);
        params.put("entryNumber", f5.getStock_number());
        params.put("stockNumber", f5.getStock_number());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        params.put("creationDate", sdf.format(f5.getFecha_generacion()));
        params.put("dealerName", f5.getDealer_name());
        params.put("dealerLicence", f5.getDealer_licence());
        params.put("dealerAddress", f5.getDealer_address());
        SimpleDateFormat sdfMesAnio = new SimpleDateFormat("MMMMMM yyyy");
        params.put("dateFabricacion", sdfMesAnio.format(f5.getFecha_fabricacion()));
        params.put("marca", f5.getMake());
        String yearModelo = f5.getYear() + " " + f5.getModel();
        params.put("modelo", yearModelo);
        String regoYear = "";
        if (f5.getRego() != null && f5.getRego().trim().length() > 0) {
            regoYear = f5.getRego();
        }
        if (f5.getRego_exp_date() != null) {
            regoYear = regoYear + " " + sdf.format(f5.getRego_exp_date());
        }
        params.put("registracion", regoYear);

        NumberFormat n = NumberFormat.getCurrencyInstance();
        String valorMoneda = n.format(f5.getPrecio_auto());

        params.put("precio", valorMoneda);
        String kilometrajeOriginal = f5.getOdometer_reading();
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
        params.put("kilometros", resultado);
        params.put("vin", f5.getVin());
        params.put("numeroMotor", f5.getEngine_number());
        params.put("writtenOff", f5.isWritten_off());
        params.put("waterDamage", f5.isWater_damage());
        params.put("majorModifications", f5.isMajor_modifications());
        params.put("ppsr", f5.getPpsr_code());
        params.put("guarantee", f5.isGuarantee());
        params.put("importedSecondHand", f5.isImported_second_hand());
        SimpleDateFormat sdfDiaMesAnio = new SimpleDateFormat("dd/MM/yyyy");
        if (f5.getSale_date() != null) {
            params.put("ventaFecha", sdfDiaMesAnio.format(f5.getSale_date()));
        } else {
            params.put("ventaFecha", "");
        }

        kilometrajeOriginal = f5.getSale_odometer();
        resultado = "";
        cantidadLetras = 0;
        for (int b = kilometrajeOriginal.length() - 1; b > -1; b--) {
            resultado = kilometrajeOriginal.charAt(b) + resultado;
            cantidadLetras++;
            if (cantidadLetras % 3 == 0 && cantidadLetras != kilometrajeOriginal.length()) {
                resultado = "," + resultado;
            }
        }
        if (resultado.trim().length() > 0) {
            resultado = resultado + " Km";
        }
        params.put("ventaKM", resultado);

        if (f5.getSale_price() <= 0) {
            params.put("ventaPrecio", "");
        } else {

            valorMoneda = n.format(f5.getSale_price());
            params.put("ventaPrecio", valorMoneda);
        }
        params.put("ventaRMS", "");
        params.put("ventaTradeIN", "");
        params.put("ventaCompradorDetalles", f5.getDatosComprador());
        params.put("rmsNumber", f5.getRms_numero());
        String dateRMS = "";
        if (f5.getRms_fecha() != null) {
            dateRMS = sdf.format(f5.getRms_fecha());
        }
        params.put("rmsDate", dateRMS);
        boolean cancelado = false;
        if (f5.getEstado() == Formulario5.ESTADO_CANCELADO) {
            cancelado = true;
        }
        params.put("cancelado", cancelado);

        try {

            //SEARCH THE FILE (if has other path please change it!)*/
            File f = new File("reportes\\Form5.jrxml");

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

            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public List<TipoCompra> getListaTipoCompra() {
        return listaTipoCompra;
    }

    public void setListaTipoCompra(List<TipoCompra> listaTipoCompra) {
        this.listaTipoCompra = listaTipoCompra;
    }

    public void nuevaVenta() {
        List<Alquiler> listaAlquileresActivos = alquilerEJB.getAlquileresActivosDelAuto(this.auto.getId());
        if (listaAlquileresActivos.size() > 0) {
            Alquiler alquilerActivo = listaAlquileresActivos.get(0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You cannot sell a car that is on an active contract (" + alquilerActivo.getId() + ")."));
            return;
        }

        if (this.auto == null) {
            this.mensajeNuevaVenta = "The car is not defined";
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('myDialogVar').show();");
            return;
        }

        if (this.auto != null && this.auto.getRego().trim().length() == 0) {
            //aca tenemos que seguir
            this.mensajeNuevaVenta = "The car registration is not defined, do you want to continue?";
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('myDialogVar').show();");
            return;
        }

        if (this.auto != null && this.auto.getRego() != null && this.auto.getRego().trim().length() > 0 && this.auto.getFecha_vencimiento_rego() == null) {
            this.mensajeNuevaVenta = "The car has a registration defined but the expiration date is not defined, do you want to continue?";
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('myDialogVar').show();");
            return;
        }

        Date hoy = Calendar.getInstance().getTime();
        if (this.auto != null && this.auto.getFecha_vencimiento_rego().before(hoy)) {
            this.mensajeNuevaVenta = "The registration is expired, do you want to continue?";
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('myDialogVar').show();");
            return;
        }

        this.mostrarGrillaVenta = true;
        this.mostrarBotonesAceptar = false;
        this.mostrarBotonNuevaVenta = false;
    }

    public void cerroeldialogo() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String contextPath = origRequest.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(contextPath + "/pantallas/DatosAuto.piantino");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void aceptarVenta() {
        if (this.validarAceptarVenta() && this.validarEditarAuto()) {
            this.clienteActualVenta.setTipo(this.tipoCliente);
            if (this.clienteActualVenta.getId() == -1) {
                clienteEJB.create(clienteActualVenta);
                System.out.println("Se acaba de crear el cliente: " + this.clienteActualVenta.getId());
            } else {
                clienteEJB.edit(clienteActualVenta);
                System.out.println("Se acaba de editar el cliente: " + this.clienteActualVenta.getId());
            }
            this.ventaActual.setEstado(Venta.ESTADO_ACTIVO);
            this.ventaActual.setClienteVenta(this.clienteActualVenta);
            this.ventaActual.setAuto(this.auto);

            if (this.ventaActual.getMonto_pagado() >= this.ventaActual.getValor()) {
                this.ventaActual.setMonto_adeudado(0);
            } else {
                float montoAdeudado = this.ventaActual.getValor() - this.ventaActual.getMonto_pagado();
                int montoAdeudadoInt = (int) (montoAdeudado * 100);
                montoAdeudado = ((float) (montoAdeudadoInt)) / 100;
                this.ventaActual.setMonto_adeudado(montoAdeudado);
            }
            this.ventaActual.setCliente_direccion(this.clienteActualVenta.getDireccion() + ", " + this.clienteActualVenta.getSuburbio());
            this.ventaActual.setCliente_suburbio(this.clienteActualVenta.getSuburbio());

            if (this.clienteActualVenta.getState() != null && this.clienteActualVenta.getState().getId() > 0) {
                this.ventaActual.setCliente_state(this.clienteActualVenta.getState().getNombre());
            }
            this.ventaActual.setCliente_codigo_postal(this.clienteActualVenta.getCodigo_postal());
            this.ventaActual.setCliente_tipo(this.clienteActualVenta.getTipo());
            this.ventaActual.setCliente_abn(this.clienteActualVenta.getAbn());
            this.ventaActual.setCliente_acn(this.clienteActualVenta.getAcn());
            this.ventaActual.setCliente_email(this.clienteActualVenta.getEmail());
            this.ventaActual.setCliente_fecha_nacimiento(this.clienteActualVenta.getDOB());
            this.ventaActual.setCliente_licence(this.clienteActualVenta.getLicencia_numero());
            this.ventaActual.setCliente_dealer_licence(this.clienteActualVenta.getDealer_licence());
            this.ventaActual.setCliente_mobile(this.clienteActualVenta.getMovil());
            this.ventaActual.setCliente_telefono(this.clienteActualVenta.getTelefono());
            this.ventaActual.setCliente_nombre(this.clienteActualVenta.getNombre());
            this.ventaActual.setCliente_apelllido(this.clienteActualVenta.getApellido());

            if (this.auto.getTipo_body() != null) {
                this.ventaActual.setCar_body(this.auto.getTipo_body().getNombre());
            }
            this.ventaActual.setCar_year(this.auto.getAño());
            this.ventaActual.setCar_built(this.auto.getBuildDate());
            this.ventaActual.setCar_color(this.auto.getColor());
            this.ventaActual.setCar_compilance(this.auto.getCompliance());
            String descripcion = this.auto.getAño() + " " + this.auto.getMarca().getNombre() + " " + this.auto.getModelo().getNombre();
            this.ventaActual.setCar_descripcion(descripcion);
            String transmision = "";
            if (this.auto.getTransmision() == 0) {
                transmision = "Automatic";
            } else if (this.auto.getTransmision() == 1) {
                transmision = "Manual";
            }
            this.ventaActual.setCar_transmicion(transmision);
            String carMake = "";
            if (this.auto.getMarca() != null) {
                carMake = this.auto.getMarca().getNombre();
            }
            this.ventaActual.setCar_make(carMake);
            String modelo = "";
            if (this.auto.getModelo() != null) {
                modelo = this.auto.getModelo().getNombre();
            }
            this.ventaActual.setCar_model(modelo);
            this.ventaActual.setCar_motor_numero(this.auto.getNro_motor());
            //this.ventaActual.setCar_odometer(this.auto.getKilometraje());
            this.ventaActual.setCar_rego(this.auto.getRego());
            this.ventaActual.setCar_rego_exp(this.auto.getFecha_vencimiento_rego());
            this.ventaActual.setCar_stock(this.auto.getStock() + "");
            this.ventaActual.setCar_vin(this.auto.getVIN());

            if (this.ventaActual.getSucursal() != null && this.ventaActual.getSucursal().getId() == -1) {
                if (this.auto.getSucursal() != null && this.auto.getSucursal().getId() > -1) {
                    this.ventaActual.setSucursal(this.auto.getSucursal());
                } else {
                    this.ventaActual.setSucursal(null);
                }
            }

            if (this.ventaActual.getInvoice() == null || this.ventaActual.getInvoice().getId() == -1) {
                Invoice invoice = new Invoice();
                invoice.setFecha(c.getTime());
                invoice.setEstado(Invoice.INVOICE_ACTIVA);
                invoiceEJB.create(invoice);
                this.ventaActual.setInvoice(invoice);
            }

            if (this.ventaActual.getFormulario() != null && this.ventaActual.getFormulario().getId() == -1) {
                this.ventaActual.setFormulario(null);
            }
            if (this.ventaActual.getInvoice() != null && this.ventaActual.getInvoice().getId() == -1) {
                this.ventaActual.setInvoice(null);
            }

            if (this.ventaActual.getId() > -1) {
                ventaEJB.edit(ventaActual);
                System.out.println("Se acaba de editar la venta: " + this.ventaActual.getId());
            } else {
                ventaEJB.create(this.ventaActual);
                System.out.println("Se acaba de crear la venta: " + this.ventaActual.getId());
            }
            this.auto.setEstado(Auto.ESTADO_VENDIDO);
            this.auto.setDisponible(false);
            this.auto.setKilometraje(this.ventaActual.getCar_odometer());

            this.editarAuto(false);

            if (this.ventaActual.getFormulario() != null && this.ventaActual.getFormulario().getId() > -1) {
                Formulario5 formularioVenta = this.ventaActual.getFormulario();
                actualizarFormularioVenta(formularioVenta);
            } else {
                generarNotice(false);
            }
            this.ventaActual.setFormulario(formularioActivoAuto);
            this.ventaEJB.edit(ventaActual);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "The sale was registered successfully"));

            this.mostrarBotonCancelarVenta = true;
            this.mostrarBotonNuevaVenta = false;
            this.mostrarGenerarNotice = false;
            this.mostrarGrillaVenta = true;
            this.mostrarBotonesAceptar = true;

        }
    }

    public void actualizarFormularioVenta(Formulario5 formularioVenta) {

        if (this.validarEditarAuto()) {

            Formulario5 f5 = formularioVenta;
            f5.setEntry_number(this.auto.getStock());
            f5.setStock_number(this.auto.getStock());
            f5.setFecha_generacion(c.getTime());
            f5.setDealer_name(this.auto.getSucursal().getLicence().getNombre());
            Sucursal sucursal = this.auto.getSucursal();
            String direccion = sucursal.getDireccion() + ", " + sucursal.getCod_postal().getSuburbio() + ", " + sucursal.getCod_postal().getEstado() + ", " + sucursal.getCod_postal().getCodigo_postal();
            f5.setDealer_address(direccion);
            f5.setDealer_licence(this.auto.getSucursal().getLicence().getNumero());
            f5.setFecha_fabricacion(this.auto.getBuildDate());
            f5.setMake(this.auto.getMarca().getNombre());
            String modelo = this.auto.getModelo().getNombre();
            f5.setModel(modelo);
            f5.setYear(this.auto.getAño());
            SimpleDateFormat sdfFecha = new SimpleDateFormat("dd-MMM-yyyy");
            String regoFecha = this.auto.getRego();
            f5.setRego(regoFecha);
            f5.setRego_exp_date(this.auto.getFecha_vencimiento_rego());
            Auto_precios autoPrecio = this.autoPrecioEJB.getPrecioDefectoPrimeroAuto(this.auto.getId());
            if (autoPrecio != null) {
                f5.setPrecio_auto(autoPrecio.getMonto());
            }

            f5.setOdometer_reading(this.auto.getKilometraje());
            f5.setVin(this.auto.getVIN());
            f5.setEngine_number(this.auto.getNro_motor());
            f5.setWritten_off(this.auto.isWritten_off());
            f5.setWater_damage(this.auto.isWater_damage());
            f5.setMajor_modifications(this.auto.isMajor_damage());
            f5.setPpsr_code(this.auto.getCertificate_number());
            f5.setGuarantee(this.tieneGarantiaAuto(auto));
            String datosComprador = "";
            if (this.ventaActual != null && this.ventaActual.getId() > -1) {
                f5.setSale_date(this.ventaActual.getFecha());
                f5.setSale_price(this.ventaActual.getValor());
                f5.setSale_odometer(this.ventaActual.getCar_odometer());
                f5.setRms_numero(this.ventaActual.getRms_codigo());
                f5.setRms_fecha(this.ventaActual.getRms_fecha());
                if (this.ventaActual.getClienteVenta() != null && this.ventaActual.getClienteVenta().getId() > -1) {
                    Cliente clienteComprados = this.ventaActual.getClienteVenta();
                    datosComprador = clienteComprados.getNombre() + " " + clienteComprados.getApellido() + " " + clienteComprados.getDireccion() + ", " + clienteComprados.getSuburbio() + ", " + clienteComprados.getState().getNombre() + " " + clienteComprados.getCodigo_postal() + ", " + clienteComprados.getPais();
                }
                f5.setDatosComprador(datosComprador);
            }
            f5.setAuto(this.auto);
            f5.setImported_second_hand(this.auto.isImported_second_hand());
            f5.setEstado(Formulario5.ESTADO_ACTIVO);
            formularioEJB.edit(f5);
            this.setMensajeFormulario("");
            this.auto.setFormulario_actualizado(true);
            if (auto.getLabel() != null && auto.getLabel().getId() == -1) {
                auto.setLabel(null);
            }
            if (this.auto.getTipoCompra() != null && this.auto.getTipoCompra().getId() == -1) {
                this.auto.setTipoCompra(null);
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (this.auto.getFecha_vencimiento_rego() != null) {
                    System.out.println("se actualiza auto: " + auto.getRego() + " fecha rego: " + sdf.format(auto.getFecha_vencimiento_rego()) + " usuario: " + this.usuarioLogueado.getEmail());
                } else {
                    System.out.println("se actualiza auto: " + auto.getRego() + "sin fecha rego usuario: " + this.usuarioLogueado.getEmail());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            autoEJB.edit(this.auto);

            if (auto.getLabel() == null) {
                auto.setLabel(new Label());
            }
            if (this.auto.getTipoCompra() == null) {
                auto.setTipoCompra(new TipoCompra());
            }
            long idReciencreado = f5.getId();
            this.listaFormulariosAuto = this.formularioEJB.getListaFormularioAuto(this.auto.getId());
            for (int a = 0; a < this.listaFormulariosAuto.size(); a++) {
                Formulario5 formularioAuto = this.listaFormulariosAuto.get(a);
                if (formularioAuto.getId() != idReciencreado) {
                    formularioAuto.setEstado(Formulario5.ESTADO_CANCELADO);
                    formularioEJB.edit(formularioAuto);
                } else {
                    this.formularioActivoAuto = formularioAuto;
                }
            }
        }

    }

    public void cancelarVenta() {
        if (this.validarEditarAuto()) {
            this.clienteActualVenta = new Cliente();
            if (this.ventaActual != null && this.ventaActual.getId() > 0) {
                this.ventaActual.setEstado(Venta.ESTADO_BAJA_LOGICA);
                this.ventaEJB.edit(ventaActual);
                this.ventaActual = new Venta();
                this.clienteActualVenta = new Cliente();
                this.auto.setEstado(Auto.ESTADO_DISPONIBLE);
                this.editarAuto(false);
                this.generarNotice(false);
            }
            this.mostrarBotonesAceptar = true;
            this.mostrarGrillaVenta = false;
            this.mostrarBotonNuevaVenta = true;
            this.mostrarGenerarNotice = true;
        }
    }

    public boolean isMostrarBotonNuevaVenta() {
        if (this.auto == null) {
            return false;
        }

        if (this.auto.getId() == -1) {
            return false;
        }

        if (this.ventaActual != null && this.ventaActual.getId() > 0) {
            return false;
        }
        if (this.mostrarBotonNuevaVenta) {
            return true;
        } else {
            return false;
        }
    }

    public void setMostrarBotonNuevaVenta(boolean mostrarBotonNuevaVenta) {
        this.mostrarBotonNuevaVenta = mostrarBotonNuevaVenta;
    }

    public boolean isMostrarBotonesAceptar() {
        return mostrarBotonesAceptar;
    }

    public void setMostrarBotonesAceptar(boolean mostrarBotonesAceptar) {
        this.mostrarBotonesAceptar = mostrarBotonesAceptar;
    }

    public boolean isMostrarGrillaVenta() {
        if (this.auto == null) {
            return false;
        }
        if (this.ventaActual != null && this.ventaActual.getId() > 0) {
            return true;
        }
        if (this.ventaActual != null && mostrarGrillaVenta) {
            return true;
        } else {
            return false;
        }
    }

    public void setMostrarGrillaVenta(boolean mostrarGrillaVenta) {
        this.mostrarGrillaVenta = mostrarGrillaVenta;
    }

    public boolean isMostrarBotonCancelarVenta() {
        if (this.mostrarGrillaVenta && ventaActual != null && ventaActual.getId() == -1) {
            return true;
        } else {
            return false;
        }
    }

    public void setMostrarBotonCancelarVenta(boolean mostrarBotonCancelarVenta) {
        this.mostrarBotonCancelarVenta = mostrarBotonCancelarVenta;
    }

    public boolean validarAceptarVenta() {
        if (this.auto == null) {
            //esto no deberia pasar
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The car is null"));
            return false;
        }

        if (this.ventaActual == null) {
            //esto tampoco deberia pasar
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The sale is null"));
            return false;
        }

        if (this.ventaActual.getProposal_date() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the proposal date"));
            return false;
        }

        if (this.ventaActual.getFecha() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the sale date"));
            return false;
        }

        if (this.ventaActual.getValor() <= 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the sale price"));
            return false;
        }

        if (this.ventaActual.getCar_odometer() == null || this.ventaActual.getCar_odometer().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the millage of the car in the moment of the sale"));
            return false;
        }

        if (this.clienteActualVenta == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the customer data"));
            return false;
        }

        if (this.clienteActualVenta.getState() == null || this.clienteActualVenta.getState().getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to select the state of the client"));
            return false;
        }

        if (this.tipoCliente == Cliente.TIPO_CUSTOMER) {
            if (this.clienteActualVenta.getNombre().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the client name"));
                return false;
            }
        }

        if (this.clienteActualVenta.getApellido().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the client last name"));
            return false;
        }

        boolean telefono = false;
        if (this.clienteActualVenta.getTelefono() != null && this.clienteActualVenta.getTelefono().trim().length() > 0) {
            telefono = true;
        }

        boolean movil = false;
        if (this.clienteActualVenta.getMovil() != null && this.clienteActualVenta.getMovil().trim().length() > 0) {
            movil = true;
        }

        if (movil == false && telefono == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a phone or mobile number of the client"));
            return false;
        }

        if (this.clienteActualVenta.getDireccion() == null || this.clienteActualVenta.getDireccion().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the client address"));
            return false;
        }

        if (this.clienteActualVenta.getSuburbio() == null || this.clienteActualVenta.getSuburbio().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the client suburb"));
            return false;
        }

        if (this.clienteActualVenta.getCodigo_postal() == null || this.clienteActualVenta.getCodigo_postal().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the postal code where the client live"));
            return false;
        }

        if (this.clienteActualVenta.getPais() == null || this.clienteActualVenta.getPais().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the country where the client live"));
            return false;
        }

        List<Alquiler> listaAlquileresActivos = alquilerEJB.getAlquileresActivosDelAuto(this.auto.getId());
        if (listaAlquileresActivos.size() > 0) {
            Alquiler alquilerActivo = listaAlquileresActivos.get(0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You cannot sell a car that is on an active contract (" + alquilerActivo.getId() + ")."));
            return false;
        }

        if (this.auto.getClienteProveedor() != null && this.auto.getClienteProveedor().getId() == -1 && this.auto.getClienteProveedor().getNombre().trim().length() > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a valid supplier or create a new one"));
            return false;
        }

        if (this.auto.getClienteProveedor() != null && this.auto.getClienteProveedor().getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a valid supplier"));
            return false;
        }

        return true;

    }

    public List<Formulario_imprimir> getListaFormulariosImprimir() {
        return listaFormulariosImprimir;
    }

    public void setListaFormulariosImprimir(List<Formulario_imprimir> listaFormulariosImprimir) {
        this.listaFormulariosImprimir = listaFormulariosImprimir;
    }

    public List<Formulario_imprimir> getListaFormulariosImprimirSeleccionados() {
        return listaFormulariosImprimirSeleccionados;
    }

    public void setListaFormulariosImprimirSeleccionados(List<Formulario_imprimir> listaFormulariosImprimirSeleccionados) {
        this.listaFormulariosImprimirSeleccionados = listaFormulariosImprimirSeleccionados;
    }

    public boolean isMostrarBotonImprimirFormulario() {
        if (this.auto != null && this.auto.getId() > -1 && this.formularioActivoAuto != null
                && this.ventaActual != null && this.ventaActual.getId() > -1) {
            return true;
        } else {
            return false;
        }
    }

    public void setMostrarBotonImprimirFormulario(boolean mostrarBotonImprimirFormulario) {
        this.mostrarBotonImprimirFormulario = mostrarBotonImprimirFormulario;
    }

    public Venta getVentaActual() {
        return ventaActual;
    }

    public void setVentaActual(Venta ventaActual) {
        this.ventaActual = ventaActual;
    }

    public boolean isEsCustomer() {
        boolean resultado = false;
        if (this.tipoCliente == Cliente.TIPO_CUSTOMER) {
            resultado = true;
        }
        return resultado;
    }

    public void setEsCustomer(boolean esCustomer) {
        this.esCustomer = esCustomer;
    }

    public boolean isEsDealer() {
        boolean resultado = false;
        if (this.tipoCliente == Cliente.TIPO_DEALER) {
            resultado = true;
        }
        return resultado;
    }

    public void setEsDealer(boolean esDealer) {
        this.esDealer = esDealer;
    }

    public boolean isEsCorporation() {
        boolean resultado = false;
        if (this.tipoCliente == Cliente.TIPO_CORPORATION) {
            resultado = true;
        }
        return resultado;
    }

    public void setEsCorporation(boolean esCorporation) {
        this.esCorporation = esCorporation;
    }

    public int getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(int tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void editarExpenses(Invoice_Mecanico_Detalle invoiceMecanicoDetalle) {
        this.invoiceMecanicoActual = invoiceMecanicoDetalle.getInvoiceMecanico();
        this.invoiceMecanicoDetalleActual = invoiceMecanicoDetalle;
        this.proveedorActualExpenses = invoiceMecanicoActual.getProveedor();
        this.mostrarExpensesDatos = true;
    }

    public List<Invoice_Mecanico_Detalle> getListaDetalleInvoiceAuto() {
        return listaDetalleInvoiceAuto;
    }

    public void eliminarExpenses() {
        System.out.println("Eliminar expenses");
    }

    public void setListaDetalleInvoiceAuto(List<Invoice_Mecanico_Detalle> listaDetalleInvoiceAuto) {
        this.listaDetalleInvoiceAuto = listaDetalleInvoiceAuto;
    }

    public double getGastosTotal() {
        double resultado = 0;
        for (int a = 0; a < this.listaDetalleInvoiceAuto.size(); a++) {
            Invoice_Mecanico_Detalle detalle = this.listaDetalleInvoiceAuto.get(a);
            resultado = resultado + detalle.getSubtotal();
        }
        return resultado;
    }

    public void verDetalleInvoice(long id) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idInvoiceMecanico");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idInvoiceMecanico", id + "");
    }

    public void viewInvoice(long idInvoice) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 1200);
        options.put("height", 800);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idInvoiceMecanico");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idInvoiceMecanico", idInvoice + "");
        PrimeFaces.current().dialog().openDynamic("DatosInvoiceMecanico", options, null);
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

    public boolean isMostrarExpensesDatos() {
        return mostrarExpensesDatos;
    }

    public void setMostrarExpensesDatos(boolean mostrarExpensesDatos) {
        this.mostrarExpensesDatos = mostrarExpensesDatos;
    }

    public void nuevoExpense() {
        this.mostrarExpensesDatos = true;
    }

    public HashMap getListaNombreProveedorExpensesSimilar() {
        return listaNombreProveedorExpensesSimilar;
    }

    public void setListaNombreProveedorExpensesSimilar(HashMap listaNombreProveedorExpensesSimilar) {
        this.listaNombreProveedorExpensesSimilar = listaNombreProveedorExpensesSimilar;
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

        this.listaDetalleInvoiceAuto = this.invoiceMecanicoDetalleEJB.listaDetallePorIdAuto(this.auto.getId());

        this.mostrarExpensesDatos = false;

    }

    public void confirmAndCloseDialog() {

        if (this.validarExpensa()) {

            this.invoiceMecanicoActual.setSentido(Invoice_Mecanico.SENTIDO_GASTO);

            this.invoiceMecanicoActual.setProveedor(this.proveedorActualExpenses);
            this.invoiceMecanicoActual.setGst(this.invoiceMecanicoDetalleActual.getGst());
            this.invoiceMecanicoActual.setTotal_sin_gst(this.invoiceMecanicoDetalleActual.getSubtotal_sin_gst());
            this.invoiceMecanicoActual.setTotal(this.invoiceMecanicoDetalleActual.getSubtotal());

            this.invoiceMecanicoDetalleActual.setAuto(this.auto);
            this.invoiceMecanicoDetalleActual.setCantidad(1);
            this.invoiceMecanicoDetalleActual.setTipo(Invoice_Mecanico_Detalle.TIPO_OUR_CAR);
            this.invoiceMecanicoDetalleActual.setColor(this.auto.getColor());
            this.invoiceMecanicoDetalleActual.setMarca(this.auto.getMarca());
            this.invoiceMecanicoDetalleActual.setModelo(this.auto.getModelo());
            this.invoiceMecanicoDetalleActual.setYear(this.auto.getAño());
            this.invoiceMecanicoDetalleActual.setRego(this.auto.getRego());
            this.invoiceMecanicoDetalleActual.setVin(this.auto.getVIN());

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

            this.listaDetalleInvoiceAuto = this.invoiceMecanicoDetalleEJB.listaDetallePorIdAuto(this.auto.getId());

            this.mostrarExpensesDatos = false;
        }
    }

    public void closeDialogExpenses() {
        this.invoiceMecanicoActual = new Invoice_Mecanico();
        this.invoiceMecanicoDetalleActual = new Invoice_Mecanico_Detalle();
        this.proveedorActualExpenses = new Cliente();

        this.listaDetalleInvoiceAuto = this.invoiceMecanicoDetalleEJB.listaDetallePorIdAuto(this.auto.getId());

        this.mostrarExpensesDatos = false;
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

        if (this.auto == null || this.auto.getId() == -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The car of the invoice is null"));
            return false;
        }

        return true;
    }

    public void imagenInterna(Auto_imagenes autoimagen) {
        if (autoimagen != null && autoimagen.getId() > 0) {
            this.autoImagenesEJB.edit(autoimagen);
        }
    }

    public boolean isMostrarVerAlquiler() {
        mostrarVerAlquiler = false;
        if (this.auto != null && this.auto.getId() > 0) {

            if (this.alquilerAuto != null && this.alquilerAuto.getId() > 0) {
                mostrarVerAlquiler = true;
            }
        }
        return mostrarVerAlquiler;
    }

    public void setMostrarVerAlquiler(boolean mostrarVerAlquiler) {
        this.mostrarVerAlquiler = mostrarVerAlquiler;
    }

    public String getMensajeNuevaVenta() {
        return mensajeNuevaVenta;
    }

    public void setMensajeNuevaVenta(String mensajeNuevaVenta) {
        this.mensajeNuevaVenta = mensajeNuevaVenta;
    }

    public void nuevaVentaSinImportarNada() {
        this.mostrarGrillaVenta = true;
        this.mostrarBotonesAceptar = false;
        this.mostrarBotonNuevaVenta = false;
    }

    public boolean isWebActualizada() {
        return webActualizada;
    }

    public void setWebActualizada(boolean webActualizada) {
        this.webActualizada = webActualizada;
    }

    public List<String> completeTextClienteInvoice(String query) {

        State estadoTerri = new State();
        estadoTerri.setId(1);

        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            listaClientes = clienteEJB.buscarPorApellidoSimilar(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> results = new ArrayList<>();
        for (int i = 0; i < listaClientes.size(); i++) {
            String stringHash = listaClientes.get(i).getNombre() + " " + listaClientes.get(i).getApellido() + " [" + listaClientes.get(i).getEmail() + "]";
            listaApellidoSimilarClienteInvoice.put(stringHash, listaClientes.get(i));
            results.add(stringHash);
        }

        return results;
    }

    public HashMap getListaApellidoSimilarClienteInvoice() {
        return listaApellidoSimilarClienteInvoice;
    }

    public void setListaApellidoSimilarClienteInvoice(HashMap listaApellidoSimilarClienteInvoice) {
        this.listaApellidoSimilarClienteInvoice = listaApellidoSimilarClienteInvoice;
    }

    public float getPrecioCostoAuto() {
        this.precioCostoAuto = 0;
        if (this.auto != null && this.auto.getId() > -1) {
            this.precioCostoAuto = this.auto.getValor_compra();
        }
        return this.formatoPeso(this.precioCostoAuto);
    }

    public void setPrecioCostoAuto(float costoAuto) {
        this.precioCostoAuto = costoAuto;
    }

    public float getPrecioLoad() {
        this.precioLoad = 0;
        if (this.auto != null && this.auto.getId() > -1) {
            precioLoad = this.auto.getLoad();
        }
        return this.formatoPeso(precioLoad);
    }

    public void setPrecioLoad(float load) {
        this.precioLoad = load;
        if (this.auto != null && this.auto.getId() > -1) {
            this.auto.setLoad(load);
        }
    }

    public float getPrecioWarranty() {
        this.precioWarranty = 0;
        if (this.auto != null && this.auto.getId() > -1) {
            precioWarranty = this.auto.getWarranty();
        }
        return this.formatoPeso(this.precioWarranty);
    }

    public void setPrecioWarranty(float warranty) {
        this.precioWarranty = warranty;
        if (this.auto != null && this.auto.getId() > -1) {
            this.auto.setWarranty(warranty);
        }
    }

    public float getPrecioExpenses() {
        this.precioExpenses = 0;
        if (this.auto != null && this.auto.getId() > -1) {
            this.precioExpenses = this.invoiceMecanicoDetalleEJB.getExpensesDelAuto(this.auto.getId());
        }
        return precioExpenses;
    }

    public void setPrecioExpenses(float precioExpenses) {
        this.precioExpenses = precioExpenses;
    }

    public float getPrecioTotalCost() {
        this.precioTotalCost = 0;
        if (this.auto != null && this.auto.getId() > -1) {
            precioTotalCost = this.auto.getValor_compra();
            precioTotalCost += this.invoiceMecanicoDetalleEJB.getExpensesDelAuto(this.auto.getId());
            precioTotalCost += this.precioLoad;
            precioTotalCost += this.precioWarranty;
        }
        return this.formatoPeso(precioTotalCost);
    }

    public void setPrecioTotalCost(float totalCost) {
        this.precioTotalCost = totalCost;
    }

    public float getPrecioDefectoAuto() {
        this.precioDefectoAuto = 0;
        if (this.auto != null && this.auto.getId() > -1) {
            if (this.ventaActual != null && this.ventaActual.getId() > -1) {
                this.precioDefectoAuto = this.ventaActual.getValor();
            } else {
                List<Auto_precios> listaPreciosAuto = autoPrecioEJB.getPrecioAutosPorAuto(this.auto.getId());
                for (int a = 0; a < listaPreciosAuto.size(); a++) {
                    Auto_precios autoPrecio = listaPreciosAuto.get(a);
                    if (autoPrecio.isDefecto()) {
                        precioDefectoAuto = autoPrecio.getMonto();
                    }
                }
                if (precioDefectoAuto == 0 && listaPreciosAuto.size() > 0) {
                    precioDefectoAuto = listaPreciosAuto.get(0).getMonto();
                }
            }
        }
        return this.formatoPeso(precioDefectoAuto);
    }

    public void setPrecioDefectoAuto(float precioDefectoAuto) {
        this.precioDefectoAuto = precioDefectoAuto;
    }

    public float getPrecioProfitConGST() {
        this.precioProfitConGST = 0;
        this.precioProfitConGST = this.precioDefectoAuto - this.precioTotalCost;
        return this.formatoPeso(precioProfitConGST);
    }

    public void setPrecioProfitConGST(float precioProfitConGST) {
        this.precioProfitConGST = precioProfitConGST;
    }

    public float getPrecioGstCreditFromCost() {
        this.precioGstCreditFromCost = 0;
        if (this.auto != null && this.auto.getId() > -1) {
            this.precioGstCreditFromCost = ((this.precioCostoAuto * 100) / 110) * 0.1f;
        }
        return this.formatoPeso(precioGstCreditFromCost);
    }

    public void setPrecioGstCreditFromCost(float precioGstCreditFromCost) {
        this.precioGstCreditFromCost = precioGstCreditFromCost;
    }

    public float getPrecioGstDebitFromSale() {
        this.precioGstDebitFromSale = 0;
        if (this.auto != null && this.auto.getId() > -1) {
            this.precioGstDebitFromSale = ((this.precioDefectoAuto * 100) / 110) * 0.1f;
        }
        return this.formatoPeso(this.precioGstDebitFromSale);
    }

    public void setPrecioGstDebitFromSale(float precioGstDebitFromSale) {
        this.precioGstDebitFromSale = precioGstDebitFromSale;
    }

    public float getPrecioProfitSinGST() {
        this.precioProfitSinGST = (this.precioGstCreditFromCost - this.precioGstDebitFromSale) + this.precioProfitConGST;
        return this.formatoPeso(precioProfitSinGST);
    }

    public boolean esProfitPositivo() {
        if (this.precioProfitSinGST > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setPrecioProfitSinGST(float profitSinGST) {
        this.precioProfitSinGST = profitSinGST;
    }

    public String getCurrencyFormat(float monto) {
        String resultado = "";
        NumberFormat n = NumberFormat.getCurrencyInstance();
        resultado = n.format(monto);
        return resultado;
    }

    private float formatoPeso(float valor) {
        int valorInt = (int) (valor * 100);
        float valorResultado = (float) valorInt / 100;
        return valorResultado;
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }

}
