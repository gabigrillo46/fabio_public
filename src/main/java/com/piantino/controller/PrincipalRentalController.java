package com.piantino.controller;

import com.piantino.ejb.AlquilerFacadeLocal;
import com.piantino.ejb.AutoFacadeLocal;
import com.piantino.ejb.MarcaFacadeLocal;
import com.piantino.ejb.ModeloFacadeLocal;
import com.piantino.ejb.UsuarioFacadeLocal;
import com.piantino.model.Alquiler;
import com.piantino.model.Auto;
import com.piantino.model.ColumnaFecha;
import com.piantino.model.Marca;
import com.piantino.model.Modelo;
import com.piantino.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

@Named
@RequestScoped
public class PrincipalRentalController implements Serializable {

    @EJB
    private AutoFacadeLocal autoEJB;

    @EJB
    private AlquilerFacadeLocal alquilerEJB;

    private List<Auto> listaAutosDisponibles;
    
      private List<Auto> filteredCars1;

    private List<ColumnaFecha> listaColumnas;

    private Auto autoSeleccionado;

    private int variable = 0;
    
    private Date primerFecha;

    private ColumnaFecha fechaSeleccionada;
    
    private int idAlquilerDatos =-1;

    @PostConstruct
    private void init() {
        try {
            listaAutosDisponibles = autoEJB.buscarAutosDisponibles();
        } catch (Exception e) {

        }
         Calendar c = Calendar.getInstance(Locale.UK);
        Date hoy = c.getTime();
        this.setPrimerFecha(hoy);
        this.cargarColumnas();
    }

    public int getIdAlquilerDatos() {
        return idAlquilerDatos;
    }

    public void setIdAlquilerDatos(int idAlquilerDatos) {
        this.idAlquilerDatos = idAlquilerDatos;
    }
    
    
    
    public boolean claseColumna(ColumnaFecha fecha, Auto car)
    {
        return true;
    }
    
    
    public void setDatosSeleccionado(int columna, Auto autoSelecc) {
        
        if(autoSelecc == null)
        {
            return;
        }
        Date fecha = this.listaColumnas.get(columna).getFechaMostrar();
        Alquiler alq = alquilerEJB.getAlquilerAutoFecha(autoSelecc.getId(), fecha);
        if(alq==null)
        {
            alq = alquilerEJB.getReserevaAutoFecha(autoSelecc.getId(), fecha);
        }
        Usuario usuarioReserva = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(usuarioReserva==null)
        {
            return;
        }
        
        if(alq !=null && (alq.getEstado() == Alquiler.ESTADO_RESERVA || alq.getEstado() == Alquiler.ESTADO_ALQUILADO))
        {
            
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerDato", alq.getId()+"");
        }
        else
        {
            if(usuarioReserva!=null)
            {
                Alquiler alquiler = new Alquiler();
                alquiler.setAuto(autoSelecc);
                if(autoSelecc!=null && autoSelecc.getRego()!=null  && autoSelecc.getRego().trim().length()>0)
                {
                    alquiler.setRego(autoSelecc.getRego().trim());
                }
                alquiler.setFecha_inicio(fecha);
                alquiler.setFecha_fin(fecha);
                alquiler.setUsuario(usuarioReserva);
                alquiler.setEstado(Alquiler.ESTADO_RESERVA);
                alquiler.setSucursal_origen(null);
                alquiler.setSucursal_destino(null);
                alquiler.setSource(null);
                alquilerEJB.create(alquiler);
                alq = alquilerEJB.getReserevaAutoFecha(autoSelecc.getId(), fecha);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idAlquilerDato", alq.getId()+"");
            }
        }
           FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
                String contextPath = origRequest.getContextPath();
                
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect(contextPath + "/pantallas/DatosRental.piantino");
                } catch (IOException e) {
                    e.printStackTrace();
                }

    }
    
    

    public void setDatosSeleccionado(int columna) {
        
        fechaSeleccionada = this.listaColumnas.get(columna);

        String regoAuto = "";
        if (autoSeleccionado != null) {
            regoAuto = autoSeleccionado.getRego();

            Usuario usuarioReserva = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                    
            if(usuarioReserva!=null)
            {
                Alquiler alquiler = new Alquiler();
                alquiler.setAuto(autoSeleccionado);
                if(autoSeleccionado!=null && autoSeleccionado.getRego()!=null  && autoSeleccionado.getRego().trim().length()>0)
                {
                    alquiler.setRego(autoSeleccionado.getRego().trim());
                }
                alquiler.setFecha_inicio(fechaSeleccionada.getFechaMostrar());
                alquiler.setFecha_fin(fechaSeleccionada.getFechaMostrar());
                alquiler.setUsuario(usuarioReserva);
                alquiler.setEstado(Alquiler.ESTADO_RESERVA);
                alquiler.setSucursal_origen(null);
                alquiler.setSucursal_destino(null);
                alquilerEJB.create(alquiler);
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();
                String contextPath = origRequest.getContextPath();
                try {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .redirect(contextPath + "/pantallas/DatosRental.piantino");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        FacesMessage msg = new FacesMessage("Car Selected", regoAuto + " - " + fechaSeleccionada.getNombreColumna());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Auto getAutoSeleccionado() {
        return autoSeleccionado;
    }

    public void setAutoSeleccionado(Auto autoSeleccionado) {
        this.autoSeleccionado = autoSeleccionado;
    }

    public void prueba(ColumnaFecha colum) {
        this.fechaSeleccionada = colum;
    }

    public ColumnaFecha getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(ColumnaFecha fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }
    
    public void cargarColumnas()
    {
        Calendar c = Calendar.getInstance(Locale.UK);
        Date hoy = this.getPrimerFecha();
        c.setTime(hoy);
        ColumnaFecha columnaHoy = new ColumnaFecha();
        columnaHoy.setFechaMostrar(hoy);
        listaColumnas = new ArrayList<>();
        listaColumnas.add(columnaHoy);
        for (int i = 0; i < 15; i++) {
            ColumnaFecha columnaNueva = new ColumnaFecha();
            c.add(Calendar.DAY_OF_MONTH, 1);
            columnaNueva.setFechaMostrar(c.getTime());
            listaColumnas.add(columnaNueva);
        } 
        
    }
    
    public String nombreColumna(int colum)
    {
        if(colum<this.listaColumnas.size())
        {
        ColumnaFecha columna=this.listaColumnas.get(colum);
        if(columna!=null)
        {
            return columna.getNombreColumna();
        }
        }
        return "";
        
    }
    
        public boolean reservadoAuto(int columna, Auto autoFila)
    {
        Date fecha = this.listaColumnas.get(columna).getFechaMostrar();
        Alquiler alq = alquilerEJB.getReserevaAutoFecha(autoFila.getId(), fecha);
        if(alq != null)
        {
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean alquiladoAuto(int columna, Auto autoFila)
    {
        Date fecha = this.listaColumnas.get(columna).getFechaMostrar();
        Alquiler alq = alquilerEJB.getAlquilerAutoFecha(autoFila.getId(), fecha);
        if(alq != null)
        {
            return true;
        }
        else{
            return false;
        }
    }
    
    

    public List<ColumnaFecha> getListaColumnas() {

        Calendar c = Calendar.getInstance(Locale.UK);
        Date hoy = c.getTime();
        ColumnaFecha columnaHoy = new ColumnaFecha();
        columnaHoy.setFechaMostrar(hoy);
        listaColumnas = new ArrayList<>();
        listaColumnas.add(columnaHoy);
        for (int i = 0; i < 8; i++) {
            ColumnaFecha columnaNueva = new ColumnaFecha();
            c.add(Calendar.DAY_OF_MONTH, 1);
            columnaNueva.setFechaMostrar(c.getTime());
            listaColumnas.add(columnaNueva);
        }
        return listaColumnas;
    }

    public void setListaColumnas(List<ColumnaFecha> listaColumnas) {
        this.listaColumnas = listaColumnas;
    }

    public List<Auto> getListaAutosDisponibles() {
        return listaAutosDisponibles;
    }

    public void setListaAutosDisponibles(List<Auto> listaAutosDisponibles) {
        this.listaAutosDisponibles = listaAutosDisponibles;
    }

    public List<Auto> getFilteredCars1() {
        return filteredCars1;
    }

    public void setFilteredCars1(List<Auto> filteredCars1) {
        this.filteredCars1 = filteredCars1;
    }

    public Date getPrimerFecha() {
        return primerFecha;
    }

    public void setPrimerFecha(Date primerFecha) {
        this.primerFecha = primerFecha;
    }

  
    
    

}
