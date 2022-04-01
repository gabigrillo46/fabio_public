package com.piantino.controller;

import com.opencsv.CSVWriter;
import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.Auto_imagenesFacadeLocal;
import com.piantino.ejb.Auto_opcionFacadeLocal;
import com.piantino.ejb.Auto_preciosFacadeLocal;
import com.piantino.ejb.EventoFacadeLocal;
import com.piantino.ejb.Invoice_Mecanico_DetalleFacadeLocal;
import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.ejb.PublicidadFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Auto;
import com.piantino.model.Auto;
import com.piantino.model.Auto_imagenes;
import com.piantino.model.Auto_opcion;
import com.piantino.model.Auto_precios;
import com.piantino.model.Evento;
import com.piantino.model.Marca;
import com.piantino.model.Modelo;
import com.piantino.model.Publicidad;
import com.piantino.model.Sucursal;
import com.piantino.model.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.primefaces.model.LazyDataModel;

@Named
@ViewScoped
public class GrillaAutosController implements Serializable {

    @EJB
    private AutoFacadeLocal AutosEJB;

    @EJB
    private Auto_imagenesFacadeLocal autoImagenesEJB;

    private Auto modeloSeleccionada = null;

    private List<Auto> listaAutos;

    private List<Auto> listaAutosSeleccionados = new ArrayList();

    private String rego;

    private String vin;

    private String stock = "";

    private boolean mostrarAutosWEB = false;

    private int idSucursal = -1;

    private int disponible = -1;

    private int idMarca = -1;

    private int idModelo = -1;

    private List<Sucursal> listaSucursales;

    private List<Marca> listaMarcas;

    private List<Modelo> listaModelo;

    private List<Auto> listaAutosDisponiblesWeb = new ArrayList();

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    @EJB
    private MarcaFacadeLocal marcaEJB;

    @EJB
    private ModeloFacadeLocal modeloEJB;

    @EJB
    private Auto_imagenesFacadeLocal autoImagenEJB;

    @EJB
    private Auto_preciosFacadeLocal autoPrecioEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private String descripcionAuto = "";

    private String transmisionAuto = "";

    private String fuel_type = "";

    private String autoImagenes = "";

    private String urlImagen = "";

    @EJB
    private PublicidadFacadeLocal publicidadEJB;

    private String etiquetaSold = "";

    private String pathResource = "";

    @EJB
    private Auto_opcionFacadeLocal autoOpcionEJB;

    private int cantidadResultado = 0;

    private Usuario usuarioLogueado;

    @EJB
    private Invoice_Mecanico_DetalleFacadeLocal invoiceMecanicoDetalleEJB;

    @EJB
    private EventoFacadeLocal eventoEJB;

    @PostConstruct
    private void init() {
        listaAutos = AutosEJB.buscarPrimerosAutos(); //AutosEJB.findAll();
        listaSucursales = sucursalEJB.findAll();
        listaModelo = modeloEJB.findAll();
        listaMarcas = marcaEJB.findAll();
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();

        pathResource = ctx.getRealPath("/resources");
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        Evento evento = new Evento();
        evento.setFecha(c.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        
        evento.setFecha_hora(sdf.format(c.getTime()));
        evento.setTipo(Evento.TYPE_CAR_LIST);
        evento.setUsuario(usuarioLogueado);
        this.eventoEJB.create(evento);
    }

    public List<Auto> getListaAutos() {
        return listaAutos;
    }

    public void setListaAutos(List<Auto> listaAutos) {
        this.listaAutos = listaAutos;
    }

    public void setAutoSeleccionada(Auto mar) {
        this.modeloSeleccionada = mar;
    }

    public Auto getAutoSeleccionada() {
        return this.modeloSeleccionada;
    }

    public void eliminarAuto(Auto marcaSelecc) {
        try {
            List<Alquiler> listaAlquileresDelAuto = alquilerEJB.getAlquileresActivosDelAuto(marcaSelecc.getId());
            if (listaAlquileresDelAuto.size() > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The car cannot be removed because it is assigned to the rental: " + listaAlquileresDelAuto.get(0).getId()));
                return;
            }
            marcaSelecc.setEstado(Auto.ESTADO_BAJA);
            AutosEJB.edit(marcaSelecc);
            //aca no tenemos que eliminarlo, tenemos que cambiarle de estado...

            //AutosEJB.remove(marcaSelecc);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully removed"));
            listaAutos = AutosEJB.findAll();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Error detected while deleting"));
        }
    }

    public void todosDisponibles() {
        System.out.println(this.listaAutosSeleccionados.size());
        for (int a = 0; a < this.listaAutosSeleccionados.size(); a++) {
            Auto auto = this.listaAutosSeleccionados.get(a);
            auto.setDisponible(true);
            AutosEJB.edit(auto);
        }
        this.listaAutosSeleccionados.clear();
        this.buscarPorFiltro();
        this.generarCSV();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully edited"));
    }

    public void todosNODisponibles() {
        System.out.println(this.listaAutosSeleccionados.size());
        for (int a = 0; a < this.listaAutosSeleccionados.size(); a++) {
            Auto auto = this.listaAutosSeleccionados.get(a);
            auto.setDisponible(false);
            AutosEJB.edit(auto);
        }
        this.listaAutosSeleccionados.clear();
        this.buscarPorFiltro();
        this.generarCSV();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully edited"));
    }

    public String getDescripcionAuto(Auto auto) {

        if (auto != null) {
            if (auto.getMarca() != null) {
                descripcionAuto = auto.getMarca().getNombre() + " ";
            }
            if (auto.getModelo() != null) {
                descripcionAuto = descripcionAuto + auto.getModelo().getNombre() + " ";
            }
            if (auto.getAño() > 0) {
                descripcionAuto = descripcionAuto + auto.getAño() + " ";
            }
        }
        return descripcionAuto;
    }

    public void setDescripcionAuto(String descripcionAuto) {
        this.descripcionAuto = descripcionAuto;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Marca> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marca> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public List<Modelo> getListaModelo() {
        if (idMarca > 0) {
            listaModelo = modeloEJB.buscarPorMarca(idMarca);
        } else {
            listaModelo = modeloEJB.findAll();
        }
        return listaModelo;
    }

    public void setListaModelo(List<Modelo> listaModelo) {
        this.listaModelo = listaModelo;
    }

    public void mostrarPrimerosAutos() {

    }

    public void buscarPorFiltro() {
        long stockNumber = -1;
        if (stock != null && stock.trim().length() > 0) {
            try {
                stockNumber = Long.parseLong(stock);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "The stock number have to be a number"));
                return;
            }
        }

        if (this.vin == null || this.vin.trim().length() == 0) {
            this.vin = null;
        }
        if (rego == null || rego.trim().length() == 0) {
            rego = null;
        }
        this.listaAutos = new ArrayList<>();
        List<Auto> listaAutoPrimerFiltro = AutosEJB.buscarAutosPorFiltro(rego, vin, idSucursal, idMarca, idModelo, disponible, stockNumber);
        if (this.mostrarAutosWEB) {
            for (int a = 0; a < listaAutoPrimerFiltro.size(); a++) {
                Auto autoFiltro = listaAutoPrimerFiltro.get(a);
                if (AutosEJB.estaAutoAlquilado(autoFiltro) == false) {
                    this.listaAutos.add(autoFiltro);
                }
            }
        } else {
            this.listaAutos = listaAutoPrimerFiltro;
        }
        this.cantidadResultado = this.listaAutos.size();
    }

    public void verDetalleAuto(int idAuto) {

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idAutoDato");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAutoDato", idAuto + "");

    }

    public String getTransmisionAuto(Auto auto) {
        if (auto.getTransmision() == Auto.AUTOMATIC) {
            return Auto.automaticExcel;
        } else {
            return Auto.manualExcel;
        }
    }

    public void setTransmisionAuto(String transmisionAuto) {
        this.transmisionAuto = transmisionAuto;
    }

    public String getFuel_type(Auto auto) {
        if (auto.getTipo_combustible().getNombre().toUpperCase().startsWith("DIESEL")) {
            return Auto.diesel;
        } else if (auto.getTipo_combustible().getNombre().toUpperCase().startsWith("UNLEADED")) {
            return Auto.unleaded;
        } else if (auto.getTipo_combustible().getNombre().toUpperCase().startsWith("HYBRID")) {
            return Auto.hybrid;
        } else {
            return Auto.otro;
        }
    }

    public String getImagenesStringCarDealer(Auto auto) {
        List<Auto_imagenes> listaImagenesAuto = autoImagenEJB.getListaPorAuto(auto.getId());
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
        List<Auto_imagenes> listaImagenesAuto = autoImagenEJB.getListaPorAuto(auto.getId());
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

    public String getDescripcionPrecios(Auto auto) {
        List<Auto_precios> listaPreciosAuto = autoPrecioEJB.getPrecioAutosPorAuto(auto.getId());
        String descripcionPrecios = "";
        for (int a = 0; a < listaPreciosAuto.size(); a++) {
            Auto_precios autoPrecio = listaPreciosAuto.get(a);
            if (a > 0) {
                descripcionPrecios = descripcionPrecios + " -";
            }
            descripcionPrecios = descripcionPrecios + " " + autoPrecio.getDescripcion() + " $" + autoPrecio.getMonto() + " " + this.getDescripcionTipoTiempo(autoPrecio.getTipo_tiempo());
        }
        return descripcionPrecios;
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

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getAutoImagenes() {
        return autoImagenes;
    }

    public void setAutoImagenes(String autoImagenes) {
        this.autoImagenes = autoImagenes;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlImagen(Auto auto) {
        String resultado = "/imagenes/logo.png";
        if (auto != null) {
            List<Auto_imagenes> listaAutoImagen = this.autoImagenEJB.getListaPorAuto(auto.getId());
            if (listaAutoImagen.size() > 0) {
                resultado = listaAutoImagen.get(0).getUrl();
            }
        }
        return resultado;
    }

    public List<Auto> getListaAutosDisponiblesWeb() {
        return listaAutosDisponiblesWeb;
    }

    public void setListaAutosDisponiblesWeb(List<Auto> listaAutosDisponiblesWeb) {
        this.listaAutosDisponiblesWeb = listaAutosDisponiblesWeb;
    }

    public String getEtiquetaSold() {
        return etiquetaSold;
    }

    public void setEtiquetaSold(String etiquetaSold) {
        this.etiquetaSold = etiquetaSold;
    }

    public String getEtiquetaSold(Auto auto) {
        if (AutosEJB.estaAutoAlquilado(auto)) {
            return "sold";
        } else {
            return "";
        }
    }

    private List<String[]> createCsvDataCarDealer() {
        List<String[]> list = new ArrayList<>();
        String[] header = {"stock", "year", "make", "model", "body", "km", "drivetrain", "seat", "color", "images", "price", "fuel", "cilindros", "engine", "doors", "transmision", "video", "badge", "vin", "publication", "rego"};
        list.add(header);
        listaAutosDisponiblesWeb = AutosEJB.getListaAutoDisponiblesWeb();
        if (listaAutosDisponiblesWeb == null) {
            listaAutosDisponiblesWeb = new ArrayList<>();
        }
        listaAutosDisponiblesWeb.addAll(AutosEJB.getAutosVendidosNoMarcados());
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
            if (AutosEJB.estaAutoAlquilado(auto)) {
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

                AutosEJB.edit(auto);
            }
        }
        return list;
    }

    private List<String[]> createCsvDataSimple() {
        List<String[]> list = new ArrayList<>();
        String[] header = {"stock", "year", "make", "model", "body", "km", "drivetrain", "seat", "color", "images", "price", "secondary title", "overview", "options", "sold", "fuel", "cilindros", "engine", "doors", "transmision", "video", "badge"};
        list.add(header);
        listaAutosDisponiblesWeb = AutosEJB.getListaAutoDisponiblesWeb();
        if (listaAutosDisponiblesWeb == null) {
            listaAutosDisponiblesWeb = new ArrayList<>();
        }
        listaAutosDisponiblesWeb.addAll(AutosEJB.getAutosVendidosNoMarcados());
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
            if (AutosEJB.estaAutoAlquilado(auto)) {
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
                AutosEJB.edit(auto);
            }
        }
        return list;
    }

    public void cargarImagenesAuto() {

        List<Auto> listaAutosConImagen = AutosEJB.getListaAutoConImagen();
        for (int a = 0; a < listaAutosConImagen.size(); a++) {
            Auto autoImagenCarga = listaAutosConImagen.get(a);
            List<Auto_imagenes> listaAutoImagenes = autoImagenesEJB.getListaPorAuto(autoImagenCarga.getId());
            for (int b = 0; b < listaAutoImagenes.size(); b++) {
                Auto_imagenes autoImagen = listaAutoImagenes.get(b);
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

    private String getBadgeAuto(Auto auto) {
        if (auto.getLabel() != null && auto.getLabel().getId() > -1) {
            return auto.getLabel().getNombre();
        } else {
            return "";
        }
    }

    private String getURLVideo() {
        return "https://youtu.be/4aR36am_Qz0";
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

    public String getPrecioAutoCurrent(int idAuto) {
        String resultado = "";
        NumberFormat n = NumberFormat.getCurrencyInstance();
        List<Auto_precios> listaPrecios = this.autoPrecioEJB.getPrecioAutosPorAuto(idAuto);
        for (int a = 0; a < listaPrecios.size(); a++) {
            Auto_precios autoPrecio = listaPrecios.get(a);
            if (autoPrecio.isDefecto()) {
                resultado = n.format(autoPrecio.getMonto());
                return resultado;
            } else {
                resultado = n.format(autoPrecio.getMonto());
            }
        }
        return resultado;
    }

    public float getPrecioAutoFloat(int idAuto) {
        float precio = 0;
        List<Auto_precios> listaPrecios = this.autoPrecioEJB.getPrecioAutosPorAuto(idAuto);
        for (int a = 0; a < listaPrecios.size(); a++) {
            Auto_precios autoPrecio = listaPrecios.get(a);
            if (autoPrecio.isDefecto()) {
                precio = autoPrecio.getMonto();
                return precio;
            } else {
                precio = autoPrecio.getMonto();
            }
        }
        return precio;
    }

    public String getCostoAutoCurrent(int idAuto) {
        String resultado = "";
        NumberFormat n = NumberFormat.getCurrencyInstance();
        Auto auto = this.AutosEJB.buscarPorId(idAuto);
        if (auto != null && auto.getId() > -1) {
            float costo = auto.getValor_compra();
            costo += this.invoiceMecanicoDetalleEJB.getExpensesDelAuto(idAuto);
            resultado = n.format(costo);
        }
        return resultado;
    }

    public float getCostoAutoCurrentFloat(int idAuto) {
        float costo = 0;
        Auto auto = this.AutosEJB.buscarPorId(idAuto);
        if (auto != null && auto.getId() > -1) {
            costo = auto.getValor_compra();
            costo += this.invoiceMecanicoDetalleEJB.getExpensesDelAuto(idAuto);
        }
        return costo;
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

    public boolean isMostrarAutosWEB() {
        return mostrarAutosWEB;
    }

    public void setMostrarAutosWEB(boolean mostrarAutosWEB) {
        this.mostrarAutosWEB = mostrarAutosWEB;
    }

    public List<Auto> getListaAutosSeleccionados() {
        return listaAutosSeleccionados;
    }

    public void setListaAutosSeleccionados(List<Auto> listaAutosSeleccionados) {
        this.listaAutosSeleccionados = listaAutosSeleccionados;
    }

    public String getDisponibilidad(Auto auto) {
        String resultado = "Not available";
        if (auto != null && auto.isDisponible()) {
            resultado = "Available";
        }
        if (auto != null && auto.getEstado() == Auto.ESTADO_VENDIDO) {
            resultado = "Sold";
        }
        return resultado;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public int getCantidadResultado() {
        return cantidadResultado;
    }

    public void setCantidadResultado(int cantidadResultado) {
        this.cantidadResultado = cantidadResultado;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

}
