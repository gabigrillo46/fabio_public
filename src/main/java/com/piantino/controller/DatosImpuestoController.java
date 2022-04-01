package com.piantino.controller;

import com.piantino.ejb.ImpuestoFacadeLocal;
import com.piantino.ejb.SucursalFacadeLocal;
import com.piantino.ejb.Tipo_MultaFacadeLocal;
import com.piantino.ejb.Tipo_impuestoFacadeLocal;
import com.piantino.model.Impuesto;
import com.piantino.model.Sucursal;
import com.piantino.model.Tipo_impuesto;
import com.piantino.model.Tipo_multa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class DatosImpuestoController implements Serializable {

    private Impuesto impuestoActual;

    @EJB
    private SucursalFacadeLocal sucursalEJB;

    @EJB
    private Tipo_impuestoFacadeLocal tipoImpuestoEJB;

    @EJB
    private ImpuestoFacadeLocal impuestoEJB;

    List<Sucursal> listaSucursales = new ArrayList<Sucursal>();

    List<Tipo_impuesto> listaTiposImpuesto = new ArrayList<Tipo_impuesto>();

    private String accion = "";

    @Inject
    private GrillaImpuestoController grillaImpuesto;

    private Impuesto impuestoSeleccionado;

    @EJB
    private Tipo_MultaFacadeLocal tipoMultaEJB;

    private List<Tipo_multa> listaTipoMultas;

    @PostConstruct
    private void init() {
        listaSucursales = sucursalEJB.buscarTodes();
        listaTiposImpuesto = tipoImpuestoEJB.findAll();
        listaTipoMultas = tipoMultaEJB.findAll();

        impuestoSeleccionado = grillaImpuesto.getImpuestoSeleccionado();
        if (impuestoSeleccionado == null) {
            impuestoActual = new Impuesto();
            this.setAccion("R");
        } else {
            try {
                impuestoActual = (Impuesto) impuestoSeleccionado.clone();
                if (impuestoSeleccionado.getSucursal() == null) {
                    impuestoSeleccionado.setSucursal(new Sucursal());
                }
                impuestoActual.setSucursal((Sucursal) impuestoSeleccionado.getSucursal().clone());
                if (impuestoSeleccionado.getTipo_impuesto() == null) {
                    impuestoSeleccionado.setTipo_impuesto(new Tipo_impuesto());
                }
                impuestoActual.setTipo_impuesto((Tipo_impuesto) impuestoSeleccionado.getTipo_impuesto().clone());
                this.setAccion("M");
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(DatosImpuestoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Impuesto getImpuestoActual() {
        return impuestoActual;
    }

    public void setImpuestoActual(Impuesto impuestoActual) {
        this.impuestoActual = impuestoActual;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Tipo_impuesto> getListaTiposImpuesto() {
        return listaTiposImpuesto;
    }

    public void setListaTiposImpuesto(List<Tipo_impuesto> listaTiposImpuesto) {
        this.listaTiposImpuesto = listaTiposImpuesto;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    private boolean validarRegistroImpuesto() {
        boolean correcto = true;
        try {
            Impuesto impuestoExistente = null;
            if (impuestoActual.getNombre().trim().length() > 0) {
                impuestoExistente = impuestoEJB.buscarImpusetoPorNomber(impuestoActual.getNombre().trim());
                if (impuestoExistente != null && impuestoExistente.getId() > -1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a fee with name entered"));
                    return false;
                }
            }

            if (impuestoActual.getTipo_asociado() > -1) {
                if (impuestoActual.getTipo_asociado() == Impuesto.TIPO_ASOCIADO_TOLL) {
                    Impuesto impuestoExistenteToll = impuestoEJB.getImpuestoParaToll();
                    if (impuestoExistenteToll != null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a tax associated with tolls"));
                        return false;
                    }
                } else if (impuestoActual.getTipo_asociado() == Impuesto.TIPO_ASOCIADO_LATE_PAYMENT) {
                    Impuesto impuestoExistenteLatePayment = impuestoEJB.getImpuestoParaLatePayment();
                    if (impuestoExistenteLatePayment != null) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a tax associated with late payment"));
                        return false;
                    }
                }

            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Error validating registration"));
        }
        return correcto;

    }

    public void registrar() {
        try {
            if (validarRegistroImpuesto()) {
                impuestoEJB.create(impuestoActual);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Successfully registered"));
                impuestoActual = new Impuesto();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Registration failed"));
        }

    }

    public void editarImpuesto() {
        try {
            if (validarEditarImpuesto()) {
                impuestoEJB.edit(impuestoActual);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited correctly"));

            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "Edit failed"));
        }
        //   Cuando haga enter tiene que tomar el editar, no el boton volver
        //         Hacer el eliminar
    }

    private boolean validarEditarImpuesto() {
        boolean correcto = true;
        try {
            Impuesto impuestoExistente = null;
            if (impuestoActual != null && impuestoActual.getNombre().trim().length() > 0) {
                impuestoExistente = impuestoEJB.buscarImpusetoPorNomber(impuestoActual.getNombre().trim());
                if (impuestoExistente != null && impuestoExistente.getId() != impuestoActual.getId()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is a fee with name entered"));
                    correcto = false;
                }
            }

            if (impuestoActual.getTipo_asociado() > -1) {
                if (impuestoActual.getTipo_asociado() == Impuesto.TIPO_ASOCIADO_TOLL) {
                    Impuesto impuestoExistenteToll = impuestoEJB.getImpuestoParaToll();
                    if (impuestoExistenteToll != null && impuestoExistenteToll.getId()!=impuestoActual.getId()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a tax associated with tolls"));
                        return false;
                    }
                } else if (impuestoActual.getTipo_asociado() == Impuesto.TIPO_ASOCIADO_LATE_PAYMENT) {
                    Impuesto impuestoExistenteLatePayment = impuestoEJB.getImpuestoParaLatePayment();
                    if (impuestoExistenteLatePayment != null && impuestoExistenteLatePayment.getId()!=impuestoActual.getId()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is already a tax associated with late payment"));
                        return false;
                    }
                }

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "Error validating edit"));
        }
        return correcto;
    }

    public List<Tipo_multa> getListaTipoMultas() {
        return listaTipoMultas;
    }

    public void setListaTipoMultas(List<Tipo_multa> listaTipoMultas) {
        this.listaTipoMultas = listaTipoMultas;
    }

}
