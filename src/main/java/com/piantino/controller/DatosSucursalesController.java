package com.piantino.controller;

import com.piantino.ejb.Cod_postalFacadeLocal;
import com.piantino.ejb.Dealer_licenceFacadeLocal;
import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.PaisFacadeLocal;
import com.piantino.ejb.StateFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Cod_postal;
import com.piantino.model.Dealer_licence;

import com.piantino.model.Marca;
import com.piantino.model.Pais;
import com.piantino.model.State;
import com.piantino.model.Sucursal;
import com.piantino.model.Usuario;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.LatLng;

@Named
@ViewScoped
public class DatosSucursalesController implements Serializable {

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    @EJB
    private MarcaFacadeLocal marcaEJB;

    @EJB
    private StateFacadeLocal stateEJB;

    @EJB
    private PaisFacadeLocal paisEJB;

    private String accion = "";

    private Sucursal sucursal;

    private Sucursal sucursalSeleccionada;

    private List<Marca> listaMarcas;

    private List<State> listaStates;

    private List<Pais> listaPaises;

    @Inject
    private GrillaSucursalesController comentarController;

    private List<Cod_postal> listaCodigoPostal = new ArrayList<>();

    private String codigo_postal = "";

    private String estado = "";

    private String centerGeoMap = "41.850033, -87.6500523";

    @EJB
    private Cod_postalFacadeLocal codPostalEJB;

    private List<Dealer_licence> listaDealerLicences = new ArrayList();

    @EJB
    private Dealer_licenceFacadeLocal dealerLicenceEJB;

    @PostConstruct
    private void init() {
        listaMarcas = marcaEJB.findAll();
        listaStates = stateEJB.findAll();
        listaPaises = paisEJB.findAll();
        listaDealerLicences = dealerLicenceEJB.findAll();
        sucursalSeleccionada = comentarController.getSucursalSeleccionada();
        if (sucursalSeleccionada == null) {
            sucursal = new Sucursal();
            this.setAccion("R");
        } else {

            try {
                sucursal = (Sucursal) sucursalSeleccionada.clone();
                if (sucursalSeleccionada.getCod_postal() == null) {
                    sucursalSeleccionada.setCod_postal(new Cod_postal());
                }
                sucursal.setCod_postal((Cod_postal) sucursalSeleccionada.getCod_postal().clone());
                if (sucursalSeleccionada.getPais() == null) {
                    sucursalSeleccionada.setPais(new Pais());
                }
                sucursal.setPais((Pais) sucursalSeleccionada.getPais().clone());

                this.listaCodigoPostal = codPostalEJB.getListaCodigoPostalPorCOdigo(sucursal.getCod_postal().getCodigo_postal() + "");
                this.codigo_postal = sucursal.getCod_postal().getCodigo_postal() + "";

            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DatosSucursalesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.setAccion("M");
        }
        if (sucursal.getLicence() == null) {
            sucursal.setLicence(new Dealer_licence());
        }

    }

    public void ingresoCodigoPostal() {
        if (codigo_postal != null && codigo_postal.trim().length() > 0) {
            this.listaCodigoPostal = codPostalEJB.getListaCodigoPostalPorCOdigo(codigo_postal);
            if (this.listaCodigoPostal.size() > 0) {
                this.estado = this.listaCodigoPostal.get(0).getEstado();
            }
        }
    }

    public void pedirCoordenadas() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public List<State> getListaStates() {
        return listaStates;
    }

    public void setListaStates(List<State> listaStates) {
        this.listaStates = listaStates;
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

    public void editarSucursal() {
        try {
            Sucursal sucursalExistente = sucursalEJB.buscarPorNombre(sucursal.getNombre());
            if(sucursal.getCod_postal()!=null && sucursal.getCod_postal().getId()==-1)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the postal code"));
                return;
            }
            if (sucursalExistente != null && sucursalExistente.getId() != sucursal.getId()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a branch with the name entered"));
            } else {
                if (sucursal.getLicence() != null && sucursal.getLicence().getId() < 1) {
                    sucursal.setLicence(null);
                }
                sucursalEJB.edit(sucursal);
                if (sucursal.getLicence() == null || sucursal.getLicence().getId() < 1) {
                    sucursal.setLicence(new Dealer_licence());
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited correctly"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Edit failed"));
        }
    }

    public String leer(Sucursal marSelecc) {
        sucursal = comentarController.getSucursalSeleccionada();
        this.setAccion("M");
        return "/pantallas/DatosSucursal";
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public void registrar() {
        try {
            Sucursal sucursalExistente = sucursalEJB.buscarPorNombre(sucursal.getNombre());
            if ((sucursal.getCod_postal() != null && sucursal.getCod_postal().getId() == -1)||(sucursal.getCod_postal() ==null)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter the postal code"));
                return;
            }
            if (sucursalExistente != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a branch with the name entered"));
            } else {
                if (sucursal.getLicence() != null && sucursal.getLicence().getId() < 1) {
                    sucursal.setLicence(null);
                }
                sucursalEJB.create(sucursal);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
                Sucursal sucursalSetear = new Sucursal();
                this.setSucursal(sucursalSetear);
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Registration failed"));
        }

    }

    public List<Cod_postal> getListaCodigoPostal() {
        return listaCodigoPostal;
    }

    public void setListaCodigoPostal(List<Cod_postal> listaCodigoPostal) {
        this.listaCodigoPostal = listaCodigoPostal;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getEstado() {
        if (this.sucursal.getCod_postal() != null && this.sucursal.getCod_postal().getId() > 0) {
            Cod_postal codPostal = this.codPostalEJB.find(this.sucursal.getCod_postal().getId());
            if (codPostal != null) {
                this.estado = codPostal.getEstado();
                return this.estado;
            }
        }
        return "";
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void eligioSuburbio() {

    }

    public void seleccionoPunto() {

    }

    public void seleccionoPunto(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();
        this.sucursal.setLatitud(latlng.getLat());
        this.sucursal.setLongitud(latlng.getLng());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Point Selected", "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng()));
    }

    public String getCenterGeoMap() {
        if (this.sucursal.getLatitud() != 0 && this.sucursal.getLongitud() != 0) {
            centerGeoMap = this.sucursal.getLatitud() + "," + this.sucursal.getLongitud();
        }
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }

    public List<Dealer_licence> getListaDealerLicences() {
        return listaDealerLicences;
    }

    public void setListaDealerLicences(List<Dealer_licence> listaDealerLicences) {
        this.listaDealerLicences = listaDealerLicences;
    }

}
