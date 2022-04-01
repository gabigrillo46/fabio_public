/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author gaby
 */
public class ColumnaFecha {

    private Date fechaMostrar;

    private String nombreColumna = "";

    public Date getFechaMostrar() {
        return fechaMostrar;
    }

    public void setFechaMostrar(Date fechaMostrar) {
        this.fechaMostrar = fechaMostrar;
    }



    public String getNombreColumna() {
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E", Locale.UK); // the day of the week abbreviated
        String nombreDia = simpleDateformat.format(fechaMostrar);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM");
        String strDate = dateFormat.format(fechaMostrar);

        nombreColumna = nombreDia + " " + strDate;

        return nombreDia + " " + strDate;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

}
