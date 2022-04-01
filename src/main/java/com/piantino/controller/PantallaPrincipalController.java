package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.Mensaje_InternoFacadeLocal;
import com.piantino.ejb.Mensaje_Interno_DestinoFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.TareaFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Auto;
import com.piantino.model.Mensaje_Interno;
import com.piantino.model.Tarea;
import com.piantino.model.Usuario;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.swing.text.html.HTMLDocument;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;

@Named
@ViewScoped
public class PantallaPrincipalController implements Serializable {

    private Usuario usuario = new Usuario();

    private DashboardModel model;

    private List<Tarea> listaTareas;

    private String mensajeDueTo = "";

    private String mensajeRego = "";

    @EJB
    private Mensaje_Interno_DestinoFacadeLocal mensajeInternoDestinoEJB;

    @EJB
    private TareaFacadeLocal tareaEJB;

    @EJB
    private AutoFacadeLocal autoEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    @EJB
    private PagoFacadeLocal pagoEJB;

    private List<Auto> listaAutoRego;

    private List<Alquiler> listaAlquileresTerminando = new ArrayList<Alquiler>();

    private List<Auto> listaAutosDeberiaTransferir = new ArrayList<Auto>();

    private BarChartModel barMes;
    private BarChartModel barAcumulado;
    private BarChartModel barPagoMes;

    private float montoMesAnioActual = 0;
    private String montoMesAnioActualFormat = "";
    private float montoMesAnioAnt = 0;
    private String montoMesAnioAntFormat = "";

    private float montoAcumuladoActual = 0;
    private String montoAcumuladoActualFormat = "";
    private float montoAcumuladoAnterior = 0;
    private String montoAcumuladoAnteriorFormat = "";

    private int contratosAcumuladosActual = 0;
    private int contratosAcumuladosAnterior = 0;

    private int contratosMesActual = 0;
    private int contratosMesAnterios = 0;

    private float montoPagoMesAnioActual = 0;
    private String montoPagoMesAnioActualFormat = "";
    private float montoPagoMesAnioAnterior = 0;
    private String montoPagoMesAnioAnteriorFormat = "";

    private int cantidadContratosActivos = 0;
    private float montoTotalAdeudado = 0;
    private String montoTotalAdeudadoFormat = "";

    private String tituloBarAcumulado = "";
    private String tituloBarModel = "";
    private String tituloBarPagoMes = "";

    private int cantidadMensajesNoLeidos = 0;
    
    private String txtComentario="";
    

    public Usuario getUsuario() {
        return usuario;
    }

    public String getMensajeDueTo() {
        return mensajeDueTo;
    }

    public void setMensajeDueTo(String mensajeDueTo) {
        this.mensajeDueTo = mensajeDueTo;
    }

    public void verDetalleTarea(int idTarea) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idTareaDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idTareaDato", idTarea + "");

    }

    public void verDetalleAuto(int idAuto) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAutoDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAutoDato", idAuto + "");
    }

    public void verDetalleAlquiler(int idAlquiler) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAlquilerDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerDato", idAlquiler + "");
    }

    public void verTransferenciaSucursal(int idSucursal) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idSucursalTransferenciaDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idSucursalTransferenciaDato", idSucursal + "");
    }

    public boolean regoVenceJuntoContrato(Auto auto) {
        Alquiler alquilerAuto = alquilerEJB.getAlquilerAlquiladoAuto(auto.getId());
        if (alquilerAuto == null) {
            return false;
        }
        Date fechaVencimientoRego = auto.getFecha_vencimiento_rego();
        Date fechaFinContrato = alquilerAuto.getFecha_fin();

        int milisecondsByDay = 86400000;
        int dias = (int) ((fechaFinContrato.getTime() - fechaVencimientoRego.getTime()) / milisecondsByDay);

        if (dias < 3) {
            return true;
        } else {
            return false;
        }
    }

    public String getmensajeDueTo(Tarea tar) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        String mensaje = "Due to " + sdf.format(tar.getFecha_vencimiento());
        return mensaje;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(List<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }

    public void tareaRealizada(int idTarea) {
        Tarea tarea = tareaEJB.buscarTareaPorId(idTarea);
        if (tarea != null) {
            tarea.setRealizado(true);
            tareaEJB.edit(tarea);
        }
        listaTareas = tareaEJB.buscarPendientesOrdenadas();
    }

    public void convertImgToPDF() {
        try {
            String destDir = "D:/Gabriel/Proyectos/Fabio/UnirPdfs";
            String fileName = "ejemplo";
            String imagePath = "D:/Gabriel/Proyectos/Fabio/UnirPdfs/unnamed.jpg";
            try (PDDocument document = new PDDocument();
                    InputStream in = new FileInputStream(imagePath)) {
                PDImageXObject img = JPEGFactory.createFromStream(document, in);
                Dimension scaledSize = getScaledDimension(img.getWidth(),
                        img.getHeight());
                PDPage page = new PDPage(new PDRectangle(scaledSize.width, scaledSize.height));
                document.addPage(page);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.drawImage(img, 0, 0, scaledSize.width,
                            scaledSize.height);
                }
                document.save(destDir + "/" + fileName + ".pdf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// 8.5" * 72 points per inch, PDF Units
    private static final int MIN_BOUNDARY = 612;

    /**
     * Using this method to scale images to PDF units, maintaining orientation
     * as landscape or portrait.
     *
     * Inspired by a stack overflow post
     * http://stackoverflow.com/questions/23223716/scaled-image-blurry-in-pdfbox
     */
    private Dimension getScaledDimension(final int originalWidth, final int originalHeight) {
        int newWidth;
        int newHeight;

        if (originalWidth < originalHeight) {
            newWidth = MIN_BOUNDARY;
            // scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        } else {
            newHeight = MIN_BOUNDARY;
            // scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        return new Dimension(newWidth, newHeight);
    }

    private void unirPDFs() {
        try {
            File file1 = new File("D:\\Gabriel\\Proyectos\\Fabio\\UnirPdfs\\Estima Blue.pdf");
            File file2 = new File("D:\\Gabriel\\Proyectos\\Fabio\\UnirPdfs\\Estima EIP25V.pdf");
            File file3 = new File("D:\\Gabriel\\Proyectos\\Fabio\\UnirPdfs\\ejemplo.pdf");
            if (file1.exists() == false) {
                return;
            }
            //Instantiating PDFMergerUtility class
            PDFMergerUtility PDFmerger = new PDFMergerUtility();

            //Setting the destination file
            PDFmerger.setDestinationFileName("D:\\Gabriel\\Proyectos\\Fabio\\UnirPdfs\\merged.pdf");

            //adding the source files
            PDFmerger.addSource(file1);
            PDFmerger.addSource(file2);
            PDFmerger.addSource(file3);

            try {
                //Merging the two documents
                PDFmerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            } catch (IOException ex) {
                Logger.getLogger(PantallaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Documents merged");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PantallaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PostConstruct
    private void init() {
        // convertImgToPDF();
        //unirPDFs();
        Usuario usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (usuarioLogueado != null) {
            usuario = usuarioLogueado;
            cantidadMensajesNoLeidos = mensajeInternoDestinoEJB.cantidadMensajesNoLeidosUsuario(usuario.getId());
        } else {
            usuario = new Usuario();
        }
        

            this.setMontoMesAnioActual(alquilerEJB.montoTotalMesActual());
            this.setMontoMesAnioAnt(alquilerEJB.montoTotalMesAnioAnterior());
            this.setContratosMesActual(alquilerEJB.cantContratosMesActual());
            this.setContratosMesAnterios(alquilerEJB.cantContratosMesAnioAnterior());

            this.setMontoAcumuladoActual(alquilerEJB.getMontoAcumuladoActual());
            this.setMontoAcumuladoAnterior(alquilerEJB.getMontoAcumuladoAnterior());
            this.setContratosAcumuladosActual(alquilerEJB.getCantContratosAcumuladosActual());
            this.setContratosAcumuladosAnterior(alquilerEJB.getCantContratosAcumuladosAnterior());

            this.setCantidadContratosActivos(alquilerEJB.getCantContratosActivos());
            this.setMontoTotalAdeudado(alquilerEJB.getMontoTotalAdeudado());

            this.setMontoPagoMesAnioActual(pagoEJB.getMontoTotalMesAnioActual());
            this.setMontoPagoMesAnioAnterior(pagoEJB.getMontoTotalMesAnioAnterior());

            createBarModel();
            createBarAcumulado();
            createBarPagoMes();
        

        listaTareas = tareaEJB.buscarPendientesOrdenadas();
        //listaAutoRego = autoEJB.listaRegoVencidos();
        List<Alquiler> listaAlqConRegoProx = alquilerEJB.getListaRegoAVencer();
        listaAutoRego = new ArrayList<Auto>();
        for (int p = 0; p < listaAlqConRegoProx.size(); p++) {
            Alquiler alq = listaAlqConRegoProx.get(p);
            if (alq.getAuto() != null) {
                listaAutoRego.add(alq.getAuto());
            }
        }

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        c.add(Calendar.DAY_OF_YEAR, +10);
        Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        c2.add(Calendar.DAY_OF_YEAR, +14);
        
        this.listaAlquileresTerminando = alquilerEJB.getListaTerminando(c2.getTime());

        this.listaAutosDeberiaTransferir = this.autoEJB.buscarAutosDeberiaTransferir();

        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
        DashboardColumn column4 = new DefaultDashboardColumn();

        column1.addWidget("logo");

        column1.addWidget("tareas");
        column1.addWidget("terminando");
        column2.addWidget("regos");
        column2.addWidget("GPS");

        column2.addWidget("graficos");
        column3.addWidget("graficos1");
        column3.addWidget("revision");
        column3.addWidget("roadside");
        column4.addWidget("graficos2");
        column4.addWidget("totales");
        column4.addWidget("transferencia");
        
        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
        model.addColumn(column4);

    }

    public float getMontoMesAnioActual() {
        return montoMesAnioActual;
    }

    public void setMontoMesAnioActual(float montoMesAnioActual) {
        this.montoMesAnioActual = montoMesAnioActual;
    }

    public float getMontoMesAnioAnt() {
        return montoMesAnioAnt;
    }

    public void setMontoMesAnioAnt(float montoMesAnioAnt) {
        this.montoMesAnioAnt = montoMesAnioAnt;
    }

    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());

        addMessage(message);
    }

    public void handleClose(CloseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");

        addMessage(message);
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public DashboardModel getModel() {
        return model;
    }

    public List<Auto> getListaAutoRego() {
        return listaAutoRego;
    }

    public void setListaAutoRego(List<Auto> listaAutoRego) {
        this.listaAutoRego = listaAutoRego;
    }

    public String getMensajeRego() {
        return mensajeRego;
    }

    public void setMensajeRego(String mensajeRego) {
        this.mensajeRego = mensajeRego;
    }

    public String getMensajeRego(Auto car) {
        String mensaje = car.getMarca().getNombre() + " - " + car.getModelo().getNombre();
        if (car.getRego() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            mensaje = mensaje + " - " + car.getRego() + " - " + sdf.format(car.getFecha_vencimiento_rego());
        }
        return mensaje;
    }

    public String getMensajeTerminando(Alquiler alq)
    {
        String mensaje ="";
        if(alq !=null && alq.getFecha_fin()!=null)
        {
                       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            mensaje ="Agreement: #"+ alq.getId() + " - end date: " + sdf.format(alq.getFecha_fin()); 
        }
        return mensaje;
    }

    
    public String getMensajeTransferencia(Auto auto)
    {
        String mensaje ="";
        if(auto!=null && auto.getSucursal()!=null)
        {
            mensaje = auto.getRego();
        }
        if(auto.getModelo()!=null)
        {
            mensaje = mensaje +" - "+auto.getModelo().getNombre();
        }
        if(auto.getMarca()!=null)
        {
            mensaje = mensaje +" "+auto.getMarca().getNombre();
        }
        return mensaje;
    }

    public BarChartModel getBarMes() {
        return barMes;
    }

    public void setBarMes(BarChartModel barMes) {
        this.barMes = barMes;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        float montoActual = this.getMontoMesAnioActual();
        float montoAnterior = this.getMontoMesAnioAnt();
        boys.set("Amount", montoAnterior);

        boys.setLabel("Last year");

        ChartSeries girld = new ChartSeries();
        girld.set("Amount", montoActual);
        girld.setLabel("This Year");
        model.addSeries(boys);
        model.addSeries(girld);

        return model;
    }

    private BarChartModel initBarAcumulado() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.set("Amount", this.getMontoAcumuladoAnterior());

        boys.setLabel("Last year");

        ChartSeries girld = new ChartSeries();
        girld.set("Amount", this.getMontoAcumuladoActual());
        girld.setLabel("This Year");
        model.addSeries(boys);
        model.addSeries(girld);

        return model;
    }

    private BarChartModel initBarPagoMes() {
        BarChartModel model = new BarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.set("Amount", this.getMontoPagoMesAnioAnterior());

        boys.setLabel("Last year");

        ChartSeries girld = new ChartSeries();
        girld.set("Amount", this.getMontoPagoMesAnioActual());
        girld.setLabel("This Year");
        model.addSeries(boys);
        model.addSeries(girld);

        return model;
    }

    private void createBarModel() {
        barMes = initBarModel();
        barMes.setSeriesColors("33A5FF,56B21E");

        // barMes.setLegendPosition("e");
        //barMes.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        float diferencia = this.getMontoMesAnioActual() - this.getMontoMesAnioAnt();
        float porcentaje = (diferencia * 100) / this.getMontoMesAnioAnt();
        int porcentajeInt = (int) (porcentaje * 100);
        porcentaje = ((float) porcentajeInt) / 100;
        String signo = "";
        if (porcentaje >= 0) {
            signo = "+";
        }
        //    barMes.setTitle("$ Hired - Month to date "+signo+porcentaje+"%");
        Axis xAxis = barMes.getAxis(AxisType.X);
        Axis yAxis = barMes.getAxis(AxisType.Y);

    }

    private void createBarAcumulado() {
        barAcumulado = initBarAcumulado();
        barAcumulado.setSeriesColors("33A5FF,56B21E");

        //barAcumulado.setLegendPosition("e");
        //barAcumulado.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        float diferencia = this.getMontoAcumuladoActual() - this.getMontoAcumuladoAnterior();
        float porcentaje = (diferencia * 100) / this.getMontoAcumuladoAnterior();
        int porcentajeInt = (int) (porcentaje * 100);
        porcentaje = ((float) (porcentajeInt)) / 100;
        String signo = "";
        if (porcentaje >= 0) {
            signo = "+";
        }

        //    barAcumulado.setTitle("$ Hired - Year to date "+signo+porcentaje+"%");
        Axis xAxis = barAcumulado.getAxis(AxisType.X);
        Axis yAxis = barAcumulado.getAxis(AxisType.Y);

    }

    private void createBarPagoMes() {
        barPagoMes = this.initBarPagoMes();
        barPagoMes.setSeriesColors("33A5FF,56B21E");

        //barPagoMes.setLegendPosition("e");
        //barPagoMes.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
        float diferencia = this.getMontoPagoMesAnioActual() - this.getMontoPagoMesAnioAnterior();
        float porcentaje = (diferencia * 100) / this.getMontoPagoMesAnioAnterior();
        int porcentajeInt = (int) (porcentaje * 100);
        porcentaje = ((float) porcentajeInt) / 100;
        String signo = "";
        if (porcentaje >= 0) {
            signo = "+";
        }
        //  barPagoMes.setTitle("$ Payments - Month to date "+signo+porcentaje+"%");
        Axis xAxis = barPagoMes.getAxis(AxisType.X);
        Axis yAxis = barPagoMes.getAxis(AxisType.Y);

    }

    public int getContratosMesActual() {
        return contratosMesActual;
    }

    public void setContratosMesActual(int contratosMesActual) {
        this.contratosMesActual = contratosMesActual;
    }

    public int getContratosMesAnterios() {
        return contratosMesAnterios;
    }

    public void setContratosMesAnterios(int contratosMesAnterios) {
        this.contratosMesAnterios = contratosMesAnterios;
    }

    public BarChartModel getBarAcumulado() {
        return barAcumulado;
    }

    public void setBarAcumulado(BarChartModel barAcumulado) {
        this.barAcumulado = barAcumulado;
    }

    public float getMontoAcumuladoActual() {
        return montoAcumuladoActual;
    }

    public void setMontoAcumuladoActual(float montoAcumuladoActual) {
        this.montoAcumuladoActual = montoAcumuladoActual;
    }

    public float getMontoAcumuladoAnterior() {
        return montoAcumuladoAnterior;
    }

    public void setMontoAcumuladoAnterior(float montoAcumuladoAnterior) {
        this.montoAcumuladoAnterior = montoAcumuladoAnterior;
    }

    public int getContratosAcumuladosActual() {
        return contratosAcumuladosActual;
    }

    public void setContratosAcumuladosActual(int contratosAcumuladosActual) {
        this.contratosAcumuladosActual = contratosAcumuladosActual;
    }

    public int getContratosAcumuladosAnterior() {
        return contratosAcumuladosAnterior;
    }

    public void setContratosAcumuladosAnterior(int contratosAcumuladosAnterior) {
        this.contratosAcumuladosAnterior = contratosAcumuladosAnterior;
    }

    public float getMontoPagoMesAnioActual() {
        return montoPagoMesAnioActual;
    }

    public void setMontoPagoMesAnioActual(float montoPagoMesAnioActual) {
        this.montoPagoMesAnioActual = montoPagoMesAnioActual;
    }

    public float getMontoPagoMesAnioAnterior() {
        return montoPagoMesAnioAnterior;
    }

    public void setMontoPagoMesAnioAnterior(float montoPagoMesAnioAnterior) {
        this.montoPagoMesAnioAnterior = montoPagoMesAnioAnterior;
    }

    public BarChartModel getBarPagoMes() {
        return barPagoMes;
    }

    public void setBarPagoMes(BarChartModel barPagoMes) {
        this.barPagoMes = barPagoMes;
    }

    public String getTituloBarAcumulado() {
        float diferencia = this.getMontoAcumuladoActual() - this.getMontoAcumuladoAnterior();
        float porcentaje = (diferencia * 100) / this.getMontoAcumuladoAnterior();
        int porcentajeInt = (int) (porcentaje * 100);
        porcentaje = ((float) (porcentajeInt)) / 100;
        String signo = "";
        if (porcentaje >= 0) {
            signo = "+";
        }
        tituloBarAcumulado = "$ Hired - Year to date " + signo + porcentaje + "%";
        return tituloBarAcumulado;
    }

    public void setTituloBarAcumulado(String tituloBarAcumulado) {
        this.tituloBarAcumulado = tituloBarAcumulado;
    }

    public String getTituloBarModel() {
        float diferencia = this.getMontoMesAnioActual() - this.getMontoMesAnioAnt();
        float porcentaje = (diferencia * 100) / this.getMontoMesAnioAnt();
        int porcentajeInt = (int) (porcentaje * 100);
        porcentaje = ((float) porcentajeInt) / 100;
        String signo = "";
        if (porcentaje >= 0) {
            signo = "+";
        }
        tituloBarModel = "$ Hired - Month to date " + signo + porcentaje + "%";
        return tituloBarModel;
    }

    public void setTituloBarModel(String tituloBarModel) {
        this.tituloBarModel = tituloBarModel;
    }

    public String getTituloBarPagoMes() {
        float diferencia = this.getMontoPagoMesAnioActual() - this.getMontoPagoMesAnioAnterior();
        float porcentaje = (diferencia * 100) / this.getMontoPagoMesAnioAnterior();
        int porcentajeInt = (int) (porcentaje * 100);
        porcentaje = ((float) porcentajeInt) / 100;
        String signo = "";
        if (porcentaje >= 0) {
            signo = "+";
        }
        tituloBarPagoMes = "$ Payments - Month to date " + signo + porcentaje + "%";
        return tituloBarPagoMes;
    }

    public void setTituloBarPagoMes(String tituloBarPagoMes) {
        this.tituloBarPagoMes = tituloBarPagoMes;
    }

    public int getCantidadContratosActivos() {
        return cantidadContratosActivos;
    }

    public void setCantidadContratosActivos(int cantidadContratosActivos) {
        this.cantidadContratosActivos = cantidadContratosActivos;
    }

    public float getMontoTotalAdeudado() {
        return montoTotalAdeudado;
    }

    public void setMontoTotalAdeudado(float montoTotalAdeudado) {
        this.montoTotalAdeudado = montoTotalAdeudado;
    }
    
    public void enviarEmail()
    {
        
    }

    public String getMontoTotalAdeudadoFormat() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        montoTotalAdeudadoFormat = format.format(montoTotalAdeudado);
        return montoTotalAdeudadoFormat;
    }

    public void setMontoTotalAdeudadoFormat(String montoTotalAdeudadoFormat) {
        this.montoTotalAdeudadoFormat = montoTotalAdeudadoFormat;
    }

    public String getMontoMesAnioActualFormat() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        montoMesAnioActualFormat = format.format(montoMesAnioActual);
        return montoMesAnioActualFormat;
    }

    public void setMontoMesAnioActualFormat(String montoMesAnioActualFormat) {
        this.montoMesAnioActualFormat = montoMesAnioActualFormat;
    }

    public String getMontoMesAnioAntFormat() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        montoMesAnioAntFormat = format.format(montoMesAnioAnt);
        return montoMesAnioAntFormat;
    }

    public void setMontoMesAnioAntFormat(String montoMesAnioAntFormat) {
        this.montoMesAnioAntFormat = montoMesAnioAntFormat;
    }

    public String getMontoAcumuladoActualFormat() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        montoAcumuladoActualFormat = format.format(montoAcumuladoActual);
        return montoAcumuladoActualFormat;
    }

    public void setMontoAcumuladoActualFormat(String montoAcumuladoActualFormat) {
        this.montoAcumuladoActualFormat = montoAcumuladoActualFormat;
    }

    public String getMontoAcumuladoAnteriorFormat() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        montoAcumuladoAnteriorFormat = format.format(montoAcumuladoAnterior);
        return montoAcumuladoAnteriorFormat;
    }

    public void setMontoAcumuladoAnteriorFormat(String montoAcumuladoAnteriorFormat) {
        this.montoAcumuladoAnteriorFormat = montoAcumuladoAnteriorFormat;
    }

    public String getMontoPagoMesAnioActualFormat() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        montoPagoMesAnioActualFormat = format.format(montoPagoMesAnioActual);
        return montoPagoMesAnioActualFormat;
    }

    public void setMontoPagoMesAnioActualFormat(String montoPagoMesAnioActualFormat) {
        this.montoPagoMesAnioActualFormat = montoPagoMesAnioActualFormat;
    }

    public String getMontoPagoMesAnioAnteriorFormat() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        montoPagoMesAnioAnteriorFormat = format.format(montoPagoMesAnioAnterior);
        return montoPagoMesAnioAnteriorFormat;
    }

    public void setMontoPagoMesAnioAnteriorFormat(String montoPagoMesAnioAnteriorFormat) {
        this.montoPagoMesAnioAnteriorFormat = montoPagoMesAnioAnteriorFormat;
    }

    public int getCantidadMensajesNoLeidos() {
        return cantidadMensajesNoLeidos;
    }

    public void setCantidadMensajesNoLeidos(int cantidadMensajesNoLeidos) {
        this.cantidadMensajesNoLeidos = cantidadMensajesNoLeidos;
    }

    public List<Auto> getListaAutosDeberiaTransferir() {
        return listaAutosDeberiaTransferir;
    }

    public void setListaAutosDeberiaTransferir(List<Auto> listaAutosDeberiaTransferir) {
        this.listaAutosDeberiaTransferir = listaAutosDeberiaTransferir;
    }

    public List<Alquiler> getListaAlquileresTerminando() {
        return listaAlquileresTerminando;
    }

    public void setListaAlquileresTerminando(List<Alquiler> listaAlquileresTerminando) {
        this.listaAlquileresTerminando = listaAlquileresTerminando;
    }

    public String getTxtComentario() {
        return txtComentario;
    }

    public void setTxtComentario(String txtComentario) {
        this.txtComentario = txtComentario;
    }
    
    

}
