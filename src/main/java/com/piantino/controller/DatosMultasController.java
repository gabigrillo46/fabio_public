package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.Alquiler_ClienteFacadeLocal;
import com.piantino.ejb.Alquiler_ImpuestoFacadeLocal;
import com.piantino.ejb.AutoridadFacadeLocal;
import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.ejb.ImpuestoFacadeLocal;
import com.piantino.ejb.MultaFacadeLocal;
import com.piantino.ejb.PagoFacadeLocal;
import com.piantino.ejb.Tipo_MultaFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.ejb.WitnessFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Alquiler_Impuesto;
import com.piantino.model.Autoridad;
import com.piantino.model.Cliente;
import com.piantino.model.Impuesto;
import com.piantino.model.Multa;
import com.piantino.model.Pago;
import com.piantino.model.Tipo_multa;
import com.piantino.model.Usuario;
import com.piantino.model.Witness;
import java.io.File;
import java.io.Serializable;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

@Named
@ViewScoped
public class DatosMultasController implements Serializable {

    private Alquiler alquilerSeleccionado;

    private String idAlquiler = "";

    @EJB
    AlquilerFacadeLocal alquilerEJB;

    @EJB
    Alquiler_ClienteFacadeLocal alquilerClienteRJB;

    List<Alquiler_Cliente> listaAlquilerCliente;

    Cliente clientePrincipal;

    @EJB
    AutoridadFacadeLocal autoridadEJB;

    @EJB
    PagoFacadeLocal pagoEJB;

    @EJB
    ClienteFacadeLocal clienteEJB;

    @EJB
    WitnessFacadeLocal witnessEJB;

    List<Autoridad> listaAutoridades = new ArrayList();

    List<Witness> listaWitness = new ArrayList();

    Multa multaActual = new Multa();

    private String fechaNacimientoClientePrincipal = "";

    private String fechaVencimientoLicencia = "";

    @EJB
    UsuarioFacadeLocal usuarioEJB;

    List<Usuario> listaUsuarios = new ArrayList();

    private String accion = "";

    private List<Tipo_multa> listaTipoMulta = new ArrayList();

    @EJB
    private MultaFacadeLocal multaEJB;

    @EJB
    private Tipo_MultaFacadeLocal tipoMultaEJB;

    @EJB
    private Alquiler_ImpuestoFacadeLocal alquiler_impuestoEJB;

    @EJB
    private ImpuestoFacadeLocal impuestoEJB;

    private Usuario usuarioLogueado;

    @PostConstruct
    private void init() {
        this.idAlquiler = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("AlquilerSelect");
        usuarioLogueado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        listaTipoMulta = tipoMultaEJB.findAll();
        listaAutoridades = autoridadEJB.findAll();
        listaWitness = witnessEJB.findAll();
        listaUsuarios = usuarioEJB.findAll();
        if (idAlquiler != null) {
            alquilerSeleccionado = alquilerEJB.find(Integer.parseInt(idAlquiler));

            if (alquilerSeleccionado != null) {
                this.setAccion("R");

                listaAlquilerCliente = alquilerClienteRJB.buscarPorAlquiler(alquilerSeleccionado.getId());
                for (int k = 0; k < listaAlquilerCliente.size(); k++) {
                    Alquiler_Cliente alqClienteAlquiler = listaAlquilerCliente.get(k);
                    if (alqClienteAlquiler.isEs_principal()) {
                        clientePrincipal = alqClienteAlquiler.getCliente();
                    }
                }
                if (clientePrincipal != null) {
                    this.getMultaActual().setClientePrincipal(clientePrincipal);
                }
                if (usuarioLogueado != null) {
                    this.getMultaActual().setUsuario(usuarioLogueado);
                }
            }
        } else {
            String idMultaAVer = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("idMultaAVer");
            int idMultaAVerInt = Integer.parseInt(idMultaAVer);
            Multa multaEncontrada = multaEJB.find(idMultaAVerInt);
            if (multaEncontrada != null) {
                this.setMultaActual(this.getMultaClonada(multaEncontrada));
                this.setAlquilerSeleccionado(multaEncontrada.getAlquiler());
                listaAlquilerCliente = alquilerClienteRJB.buscarPorAlquiler(multaEncontrada.getAlquiler().getId());
                this.setClientePrincipal(multaEncontrada.getClientePrincipal());
            } else {
                this.setMultaActual(new Multa());
            }
            this.setAccion("M");
        }

    }

    public void ingresoNumeroMulta() {
        if (this.multaActual != null && this.multaActual.getNumero() != null && this.multaActual.getNumero().trim().length() > 0) {
            int indiceGuion = this.multaActual.getNumero().trim().indexOf("-");
            if (indiceGuion > 0) {
                String numeroNuevo = this.multaActual.getNumero().substring(0, indiceGuion);
                this.multaActual.setNumero(numeroNuevo);
            }

        }
    }

    private Multa getMultaClonada(Multa multaAClonar) {
        Multa multaClonada = new Multa();
        if (multaAClonar != null) {
            try {
                multaClonada = (Multa) multaAClonar.clone();
                if (multaAClonar.getClienteInfraccion() == null) {
                    multaAClonar.setClienteInfraccion(new Cliente());
                }
                multaClonada.setClienteInfraccion((Cliente) multaAClonar.getClienteInfraccion().clone());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return multaClonada;
    }

    public void registrarMulta() {
        if (this.getMultaActual().getId() == -1) {
            if (validarRegistroMulta()) {
                this.getMultaActual().setClientePrincipal(clientePrincipal);
                this.getMultaActual().setAlquiler(alquilerSeleccionado);
                this.getMultaActual().setRego(this.alquilerSeleccionado.getRego());
                multaEJB.create(this.getMultaActual());

                if (multaActual.getTipo().getId() == Multa.ID_TIPO_TOLL) {
                    try {

                        Impuesto impuestoToll = impuestoEJB.getImpuestoParaToll();

                        boolean encontrado = false;
                        float totalExtras = 0;
                        float sumarADeuda = 0;
                        float montoTotalToll = 0;

                        if (impuestoToll != null) {
                            List<Alquiler_Impuesto> listaImpuestos = alquiler_impuestoEJB.buscarImpuestosAlquiler(this.getMultaActual().getAlquiler().getId());

                            for (int j = 0; j < listaImpuestos.size(); j++) {
                                Alquiler_Impuesto impuestoAlquiler = listaImpuestos.get(j);
                                if (impuestoAlquiler.getImpuesto().getTipo_asociado() > -1 && impuestoAlquiler.getImpuesto().getTipo_asociado() == Impuesto.TIPO_ASOCIADO_TOLL) {
                                    encontrado = true;
                                    impuestoAlquiler.setCantidad(impuestoAlquiler.getCantidad() + 1);
                                    float subtotal = impuestoAlquiler.getCantidad() * impuestoAlquiler.getPrecio();
                                    int subtotalInt = (int) (subtotal * 100);
                                    subtotal = ((float) subtotalInt) / 100;
                                    impuestoAlquiler.setSubtotal(subtotal);
                                    montoTotalToll = subtotal;
                                    alquiler_impuestoEJB.edit(impuestoAlquiler);
                                    sumarADeuda = impuestoAlquiler.getPrecio();
                                }
                                totalExtras = totalExtras + (((float) impuestoAlquiler.getCantidad()) * impuestoAlquiler.getPrecio());
                            }
                            if (!encontrado) {
                                Alquiler_Impuesto impuestoAcrear = new Alquiler_Impuesto();
                                impuestoAcrear.setAlquiler(this.alquilerSeleccionado);
                                impuestoAcrear.setImpuesto(impuestoToll);
                                impuestoAcrear.setCantidad(1);
                                impuestoAcrear.setPrecio(impuestoToll.getPrice());
                                impuestoAcrear.setSubtotal(impuestoToll.getPrice());
                                impuestoAcrear.setSeleccionado(true);
                                montoTotalToll = impuestoAcrear.getSubtotal();
                                alquiler_impuestoEJB.create(impuestoAcrear);
                                totalExtras = totalExtras + impuestoAcrear.getSubtotal();
                                sumarADeuda = impuestoAcrear.getPrecio();
                            }
                            int totalExtrasInt = (int) (totalExtras * 100);
                            totalExtras = ((float) totalExtrasInt) / 100;
                            this.alquilerSeleccionado.setExtra(totalExtras);

                            float granTotal = this.alquilerSeleccionado.getTotal() + totalExtras;
                            int granTotalInt = (int) (granTotal * 100);
                            granTotal = ((float) granTotalInt) / 100;
                            this.alquilerSeleccionado.setGran_total(granTotal);

                            float deudaTotal = this.alquilerSeleccionado.getDeuda() + sumarADeuda;
                            int deudaTotalInt = (int) (deudaTotal * 100);
                            deudaTotal = ((float) deudaTotalInt) / 100;
                            this.alquilerSeleccionado.setDeuda(deudaTotal);
                            alquilerEJB.edit(alquilerSeleccionado);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Registered Successfully"));
                listaTipoMulta = tipoMultaEJB.findAll();
                listaAutoridades = autoridadEJB.findAll();
                listaWitness = witnessEJB.findAll();

                this.setMultaActual(new Multa());
                if (usuarioLogueado != null) {
                    this.getMultaActual().setUsuario(usuarioLogueado);
                }
                //  imprimir();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Registered Successfully"));
            listaTipoMulta = tipoMultaEJB.findAll();
            listaAutoridades = autoridadEJB.findAll();
            listaWitness = witnessEJB.findAll();
            this.setMultaActual(new Multa());
            if (usuarioLogueado != null) {
                this.getMultaActual().setUsuario(usuarioLogueado);
            }
        }
    }

    public void imprimir() {

        if (this.getMultaActual().getId() == -1) {
            if (validarRegistroMulta()) {
                this.getMultaActual().setClientePrincipal(clientePrincipal);
                this.getMultaActual().setAlquiler(alquilerSeleccionado);
                this.getMultaActual().setRego(this.alquilerSeleccionado.getRego());
                this.getMultaActual().setImpreso(1);
                multaEJB.create(this.getMultaActual());

                if (multaActual.getTipo().getId() == Multa.ID_TIPO_TOLL) {
                    try {

                        Impuesto impuestoToll = impuestoEJB.getImpuestoParaToll();

                        boolean encontrado = false;
                        float totalExtras = 0;
                        float sumarADeuda = 0;
                           float montoTotalToll = 0;

                        if (impuestoToll != null) {
                            List<Alquiler_Impuesto> listaImpuestos = alquiler_impuestoEJB.buscarImpuestosAlquiler(this.getMultaActual().getAlquiler().getId());

                            for (int j = 0; j < listaImpuestos.size(); j++) {
                                Alquiler_Impuesto impuestoAlquiler = listaImpuestos.get(j);
                                if (impuestoAlquiler.getImpuesto().getTipo_asociado() > -1 && impuestoAlquiler.getImpuesto().getTipo_asociado() == Impuesto.TIPO_ASOCIADO_TOLL) {
                                    encontrado = true;
                                    impuestoAlquiler.setCantidad(impuestoAlquiler.getCantidad() + 1);
                                    float subtotal = impuestoAlquiler.getCantidad() * impuestoAlquiler.getPrecio();
                                    int subtotalInt = (int) (subtotal * 100);
                                    subtotal = ((float) subtotalInt) / 100;
                                    impuestoAlquiler.setSubtotal(subtotal);
                                    montoTotalToll=subtotal;
                                    alquiler_impuestoEJB.edit(impuestoAlquiler);
                                    sumarADeuda = impuestoAlquiler.getPrecio();
                                }
                                totalExtras = totalExtras + (((float) impuestoAlquiler.getCantidad()) * impuestoAlquiler.getPrecio());
                            }
                            if (!encontrado) {
                                Alquiler_Impuesto impuestoAcrear = new Alquiler_Impuesto();
                                impuestoAcrear.setAlquiler(this.alquilerSeleccionado);
                                impuestoAcrear.setImpuesto(impuestoToll);
                                impuestoAcrear.setCantidad(1);
                                impuestoAcrear.setPrecio(impuestoToll.getPrice());
                                impuestoAcrear.setSubtotal(impuestoToll.getPrice());
                                impuestoAcrear.setSeleccionado(true);
                                montoTotalToll=impuestoToll.getPrice();
                                alquiler_impuestoEJB.create(impuestoAcrear);
                                totalExtras = totalExtras + impuestoAcrear.getSubtotal();
                                sumarADeuda = impuestoAcrear.getPrecio();
                            }
                            int totalExtrasInt = (int) (totalExtras * 100);
                            totalExtras = ((float) totalExtrasInt) / 100;
                            this.alquilerSeleccionado.setExtra(totalExtras);

                            float granTotal = this.alquilerSeleccionado.getTotal() + totalExtras;
                            int granTotalInt = (int) (granTotal * 100);
                            granTotal = ((float) granTotalInt) / 100;
                            this.alquilerSeleccionado.setGran_total(granTotal);

                            float deudaTotal = this.alquilerSeleccionado.getDeuda() + sumarADeuda;
                            int deudaTotalInt = (int) (deudaTotal * 100);
                            deudaTotal = ((float) deudaTotalInt) / 100;
                            this.alquilerSeleccionado.setDeuda(deudaTotal);
                            alquilerEJB.edit(alquilerSeleccionado);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        } else {
            multaEJB.edit(multaActual);
        }
        Multa multaBD = multaEJB.buscarPorId(this.getMultaActual().getId());
        if (multaBD == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a fine in BD "));
            return;
        }
        int idMulta = multaBD.getId();
        if (multaBD.getAutoridad() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a authority in BD with fine id: " + idMulta));
            return;
        }

        if (multaBD.getAlquiler() == null) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a rental in BD with fine id: " + idMulta));
            return;
        }

        if (multaBD.getWitness() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a witness in BD with fine id: " + idMulta));
            return;
        }

        if (multaBD.getClienteInfraccion() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is not a client in BD with fine id: " + idMulta));
            return;
        }
        if (multaBD.getImpreso() == 0) {
            multaBD.setImpreso(1);
            multaEJB.edit(multaBD);
        }
        multaBD.setAutoridad(autoridadEJB.find(multaBD.getAutoridad().getId()));
        multaBD.setWitness(witnessEJB.find(multaBD.getWitness().getId()));
        multaBD.setClienteInfraccion(clienteEJB.buscarPorID(multaBD.getClienteInfraccion().getId()));

        Map<String, Object> params = new HashMap<>();
        params.put("NombreAutoridad", multaBD.getAutoridad().getNombre());
        params.put("DireccionAutoridad", multaBD.getAutoridad().getDireccion());
        params.put("suburbioAutoridad", multaBD.getAutoridad().getCiudad());
        params.put("estadoAutoridad", multaBD.getAutoridad().getState().getNombre());
        params.put("codAutoridad", multaBD.getAutoridad().getCodigo_postal());
        if (multaBD.getAutoridad().getPais() == null) {
            params.put("PaisAutoridad", "Australia");
        } else {
            params.put("PaisAutoridad", multaBD.getAutoridad().getPais().getNombre());
        }
        params.put("TextoAutoridad", multaBD.getAutoridad().getText_name());
        params.put("regoMulta", multaBD.getRego());
        params.put("numeroMulta", multaBD.getNumero());
        String fechaMultaStr = "";

        fechaMultaStr = multaBD.getFechaMulta();
        params.put("fechaMulta", fechaMultaStr);
        params.put("referencia", multaBD.getAlquiler().getId() + "");
        params.put("driverNombre", multaBD.getClienteInfraccion().getNombre() + " " + multaBD.getClienteInfraccion().getApellido());
        params.put("direccionDriver", multaBD.getClienteInfraccion().getDireccion());
        String dobStr = "";
        SimpleDateFormat sdfDob = new SimpleDateFormat("dd/MM/yyyy");
        if (multaBD.getClienteInfraccion().getDOB() != null) {

            dobStr = sdfDob.format(multaBD.getClienteInfraccion().getDOB());
        }
        params.put("DOBDriver", dobStr);
        params.put("numeroLicencia", multaBD.getClienteInfraccion().getLicencia_numero());
        params.put("licenciaInssueIn", multaBD.getClienteInfraccion().getOtorgadaEN());
        if (multaBD.getUsuario() != null) {
            params.put("declaradoBy", multaBD.getUsuario().getNombre() + " " + multaBD.getUsuario().getApellido());
        } else {
            params.put("declaradoBy", "");
        }

        params.put("declaradoCuando", sdfDob.format(multaBD.getFechaCargada()));
        params.put("nombreWitness", multaBD.getWitness().getNombre());
        params.put("tituloWitness", multaBD.getWitness().getTittle());
        if (multaBD.getClienteInfraccion().getState() != null) {
            params.put("StateDriver", multaBD.getClienteInfraccion().getState().getNombre());
        } else {
            params.put("StateDriver", "");
        }
        params.put("CodPostalDriver", multaBD.getClienteInfraccion().getCodigo_postal());
        if (multaBD.getClienteInfraccion().getPais() != null) {
            params.put("PaisDriver", multaBD.getClienteInfraccion().getPais());
        } else {
            params.put("PaisDriver", "");
        }
        params.put("SuburbDriver", multaBD.getClienteInfraccion().getSuburbio());
        params.put("DireccionWitness", multaBD.getWitness().getDireccion());

        try {

            /*SEARCH THE FILE (if has other path please change it!)*/
            File f = new File("reportes\\Multa.jrxml");

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

            //JasperPrintManager.printReport(jasperPrint, false);
            stream.flush();
            stream.close();

            FacesContext.getCurrentInstance().responseComplete();

            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void editarMulta() {
        if (validarEditarMulta()) {
            multaEJB.edit(this.getMultaActual());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Edited Successfully"));
        }
    }

    private boolean validarEditarMulta() {
        if (this.getMultaActual() == null || this.getMultaActual().getNumero() == null || this.getMultaActual().getNumero().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a number of infrigment"));
            return false;
        }
        Multa multaNumeroExistente = multaEJB.buscarPorNumero(this.getMultaActual().getNumero().trim());
        if (multaNumeroExistente != null && this.getMultaActual() != null && this.getMultaActual().getId() != multaNumeroExistente.getId()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Attention", "There is another infringement with the number entered"));
            return false;
        }
        return true;
    }

    private boolean validarRegistroMulta() {
        if (this.getMultaActual() == null || this.getMultaActual().getNumero() == null || this.getMultaActual().getNumero().trim().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a number of infrigment"));
            return false;
        }
        Multa multaNumeroExistente = multaEJB.buscarPorNumero(this.getMultaActual().getNumero().trim());
        if (multaNumeroExistente != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another infringement with the number entered"));
            return false;
        }
        return true;
    }

    public List<Alquiler_Cliente> getListaAlquilerCliente() {
        return listaAlquilerCliente;
    }

    public void setListaAlquilerCliente(List<Alquiler_Cliente> listaAlquilerCliente) {
        this.listaAlquilerCliente = listaAlquilerCliente;
    }

    public Cliente getClientePrincipal() {
        return clientePrincipal;
    }

    public void setClientePrincipal(Cliente clientePrincipal) {
        this.clientePrincipal = clientePrincipal;
    }

    public List<Autoridad> getListaAutoridades() {
        return listaAutoridades;
    }

    public List<Tipo_multa> getListaTipoMulta() {
        return listaTipoMulta;
    }

    public void setListaTipoMulta(List<Tipo_multa> listaTipoMulta) {
        this.listaTipoMulta = listaTipoMulta;
    }

    public void setListaAutoridades(List<Autoridad> listaAutoridades) {
        this.listaAutoridades = listaAutoridades;
    }

    public List<Witness> getListaWitness() {
        return listaWitness;
    }

    public void setListaWitness(List<Witness> listaWitness) {
        this.listaWitness = listaWitness;
    }

    public Alquiler getAlquilerSeleccionado() {
        return alquilerSeleccionado;
    }

    public void setAlquilerSeleccionado(Alquiler alquilerSeleccionado) {
        this.alquilerSeleccionado = alquilerSeleccionado;
    }

    public String getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(String idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Multa getMultaActual() {
        return multaActual;
    }

    public void setMultaActual(Multa multaActual) {
        this.multaActual = multaActual;
    }

    public String getFechaNacimientoClientePrincipal() {
        if (this.clientePrincipal != null && this.clientePrincipal.getDOB() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.fechaNacimientoClientePrincipal = sdf.format(this.clientePrincipal.getDOB());
        } else {
            this.fechaNacimientoClientePrincipal = "";
        }
        return fechaNacimientoClientePrincipal;
    }

    public void setFechaNacimientoClientePrincipal(String fechaNacimientoClientePrincipal) {
        this.fechaNacimientoClientePrincipal = fechaNacimientoClientePrincipal;
    }

    public String getFechaVencimientoLicencia() {
        if (this.clientePrincipal != null) {
            if (this.clientePrincipal.isLic_infinita()) {
                fechaVencimientoLicencia = "Lifetime";
            } else if (this.clientePrincipal.getFecha_vencimiento_lic() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                this.fechaVencimientoLicencia = sdf.format(this.clientePrincipal.getFecha_vencimiento_lic());
            } else {
                this.fechaVencimientoLicencia = "";
            }
        } else {
            this.fechaVencimientoLicencia = "";
        }
        return fechaVencimientoLicencia;
    }

    public void setFechaVencimientoLicencia(String fechaVencimientoLicencia) {
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
