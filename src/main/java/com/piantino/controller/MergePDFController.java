/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.MergeFacadeLocal;
import com.piantino.model.Merge;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import static org.eclipse.persistence.internal.helper.Helper.close;
import org.primefaces.event.FileUploadEvent;

import org.primefaces.event.ReorderEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Gabriel
 */
@Named
@ViewScoped
public class MergePDFController {

    private UploadedFile file;

    

    private List<File> listaFiles = new ArrayList<File>();

    private boolean mostrarBotonDescargar = false;

    private Merge mergeActual;

    private Calendar c;

    @EJB
    private MergeFacadeLocal mergeEJB;

    @PostConstruct
    private void init() {
        c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        System.out.println("Llego al init");
        mergeActual = new Merge();
    }

  

    public List<File> getListaFiles() {
        return listaFiles;
    }

    public void setListaFiles(List<File> listaFiles) {
        this.listaFiles = listaFiles;
    }

    public void merge() {
        try {
            if (this.mergeActual.getNombre() == null || this.mergeActual.getNombre().trim().length() == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to enter a name"));
                return;
            }

            Merge mergeBD = mergeEJB.buscarPorNombre(this.mergeActual.getNombre());
            if (mergeBD != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "There is another merge with the same name"));
                return;
            }

            List<File> listaPDFsAunir = new ArrayList<File>();
            String directorioDeTodo = "";
            if (this.listaFiles.size() < 2) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attention", "You have to upload at least two files to join"));
                return;
            }

            for (int u = 0; u < this.listaFiles.size(); u++) {
                File archivo = this.listaFiles.get(u);
                String nombreArchivo = archivo.getName();
                if (directorioDeTodo.trim().length() == 0) {
                    directorioDeTodo = archivo.getAbsolutePath().replace(archivo.getName(), "");
                }
                if (nombreArchivo.endsWith(".pdf")) {
                    listaPDFsAunir.add(archivo);
                    continue;
                }
                if (nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".png") || nombreArchivo.endsWith("jpeg")) {
                    String pathResultado = directorioDeTodo + u + ".pdf";
                    String resultado = this.convertImgToPDF(archivo.getAbsolutePath(), pathResultado);
                    if (resultado.trim().length() == 0) {
                        File archivoResultado = new File(pathResultado);
                        listaPDFsAunir.add(archivoResultado);
                    }
                }
            }

            PDFMergerUtility PDFmerger = new PDFMergerUtility();

            String directorioMerges = "merge//";

            File directorioMergeFile = new File(directorioMerges);
            if (directorioMergeFile.exists() == false) {
                directorioMergeFile.mkdir();
            }

            String archivoFinal = directorioMerges + this.mergeActual.getNombre() + ".pdf";
            File fileArchivoFinal = new File(archivoFinal);
            if (fileArchivoFinal.exists()) {
                fileArchivoFinal.delete();
            }
            PDFmerger.setDestinationFileName(archivoFinal);

            for (int j = 0; j < listaPDFsAunir.size(); j++) {
                File pdfUnir = listaPDFsAunir.get(j);
                if (pdfUnir.exists() == false) {
                    continue;
                }
                //Instantiating PDFMergerUtility class

                PDFmerger.addSource(pdfUnir);
                //Setting the destination file
                //adding the source files

            }

            //Merging the two documents
            PDFmerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

            for (int j = 0; j < listaFiles.size(); j++) {
                File archivo = listaFiles.get(j);
                if (archivo.exists()) {
                    archivo.delete();
                }
            }

            for (int k = 0; k < listaPDFsAunir.size(); k++) {
                File archivo = listaPDFsAunir.get(k);
                if (archivo.exists()) {
                    archivo.delete();
                }
            }
            this.listaFiles.clear();

            this.mostrarBotonDescargar = true;

            this.mergeActual.setFecha(c.getTime());
            this.mergeActual.setUrl(archivoFinal);
            mergeEJB.create(mergeActual);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attention", "Joined files"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void print() {
        if (this.mergeActual != null && this.mergeActual.getUrl() != null) {
            File donToy = new File("hola.pdf");
            System.out.println("Estoy aca" + donToy.getAbsolutePath());
            File archivo = new File(this.mergeActual.getUrl());
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

    public void onRowReorder(ReorderEvent event) {

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

    public void upload() {
        if (file != null && file.getFileName().trim().length() > 0) {
            File filearc = this.uploadedFileToFileConverter(file);
            this.listaFiles.add(filearc);
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void uploadMultiple() {
        /*if (files != null) {
            for (UploadedFile f : files.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }*/
    }


    


    public void handleFileUpload(FileUploadEvent event) {
        File filearc = this.uploadedFileToFileConverter(event.getFile());
        this.listaFiles.add(filearc);
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }



    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String convertImgToPDF(String imagePath, String pdfPath) {

        try {
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
                document.save(pdfPath);
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    // 8.5" * 72 points per inch, PDF Units
    private static final int MIN_BOUNDARY = 612;

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

    public boolean isMostrarBotonDescargar() {
        return mostrarBotonDescargar;
    }

    public void setMostrarBotonDescargar(boolean mostrarBotonDescargar) {
        this.mostrarBotonDescargar = mostrarBotonDescargar;
    }

    public Merge getMergeActual() {
        return mergeActual;
    }

    public void setMergeActual(Merge mergeActual) {
        this.mergeActual = mergeActual;
    }

}
