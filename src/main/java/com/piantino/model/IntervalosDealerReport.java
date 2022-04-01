/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.model;

import java.util.Vector;

/**
 *
 * @author Gabriel
 */
public class IntervalosDealerReport {
    
    private int numero =0;
    
    private String rango ="";
    
    private long minimo=0;
    
    private long maximo =0;
    
    private boolean seleccionado =false;
    
    private Vector<Auto> autosAgregados =new Vector();

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public long getMinimo() {
        return minimo;
    }

    public void setMinimo(long minimo) {
        this.minimo = minimo;
    }

    public long getMaximo() {
        return maximo;
    }

    public void setMaximo(long maximo) {
        this.maximo = maximo;
    }

    

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Vector<Auto> getAutosAgregados() {
        return autosAgregados;
    }

    public void setAutosAgregados(Vector<Auto> autosAgregados) {
        this.autosAgregados = autosAgregados;
    }

    
    
}
