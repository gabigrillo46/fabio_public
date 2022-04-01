/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.MergeFacadeLocal;
import com.piantino.model.Merge;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.io.IOUtils;
import static org.eclipse.persistence.internal.helper.Helper.close;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class GrillaMergeController implements Serializable {

        private String nombre="";

    private Date fecha_desde;

    private Date fecha_hasta;

    private Calendar c;

    private List<Merge> listaMergeResultado = new ArrayList<Merge>();

    @EJB
    private MergeFacadeLocal mergeEJB;

    private String fechaFormat = "";



    @PostConstruct
    private void init() {
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        
  
    }

    
        private StreamedContent file;


    public StreamedContent getFile() {
        return file;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public void buscarPorFiltro() {
        listaMergeResultado = mergeEJB.buscarPorFiltro(nombre, fecha_desde, fecha_hasta);
    }

    public void eliminarMerge(Merge m) {
        if (m != null) {
            File archivo = new File(m.getUrl());
            if (archivo.exists()) {
                archivo.delete();
            }
            mergeEJB.remove(m);
        }

        this.buscarPorFiltro();
    }

    public List<Merge> getListaMergeResultado() {
        return listaMergeResultado;
    }

    public void setListaMergeResultado(List<Merge> listaMergeResultado) {
        this.listaMergeResultado = listaMergeResultado;
    }

    public String getFechaFormat() {
        return fechaFormat;
    }

    public void setFechaFormat(String fechaFormat) {
        this.fechaFormat = fechaFormat;
    }

    public String getFechaFormat(Merge m) {
        String resultado = "";
        if (m != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            resultado = sdf.format(m.getFecha());
        }
        return resultado;
    }

    public void prueba(Merge m) {
        if(m!=null)
        {
        File donToy = new File("hola.pdf");
        System.out.println("Estoy aca" + donToy.getAbsolutePath());
        File archivo = new File(m.getUrl());
        System.out.println(archivo.getAbsolutePath());
        
        if (archivo.exists()) {
            show(archivo);
            //openFile(archivo);
        }
        }
    }

    private void show(File archivo) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        try {
            input = new BufferedInputStream(new FileInputStream(archivo),
                    10240);
            if (response.isCommitted()) {
                return;
            }
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Cache-Control", "no-cache");
            response.addHeader("Content-disposition", "inline; filename=jsfReporte.pdf");
            response.setHeader("Content-Length", String.valueOf(archivo.length()));

            output = new BufferedOutputStream(response.getOutputStream(),
                    10240);

            byte[] buffer = new byte[10240];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();

            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ioe) {

        } finally {
            close(output);
            close(input);
        }
        facesContext.responseComplete();
    }

    public void openFile(File file) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), 10240);

            // Init servlet response.
            response.reset();
            // lire un fichier pdf
            response.setHeader("Content-type", "application/pdf");
            response.setContentLength((int) file.length());

            //response.setHeader("Content-disposition", "attachment; filename=" + "hola.pdf");
            response.addHeader("Content-disposition", "inline; filename=jsfReporte.pdf");
            response.setHeader("pragma", "public");
            output = new BufferedOutputStream(response.getOutputStream(), 10240);

            // Write file contents to response.
            byte[] buffer = new byte[10240];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();

            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Gently close streams.

        }
    }

}
