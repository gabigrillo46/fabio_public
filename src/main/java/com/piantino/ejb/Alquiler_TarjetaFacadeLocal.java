/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler_Tarjeta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Alquiler_TarjetaFacadeLocal {

    void create(Alquiler_Tarjeta alquiler_Tarjeta);

    void edit(Alquiler_Tarjeta alquiler_Tarjeta);

    void remove(Alquiler_Tarjeta alquiler_Tarjeta);

    Alquiler_Tarjeta find(Object id);

    List<Alquiler_Tarjeta> findAll();

    List<Alquiler_Tarjeta> findRange(int[] range);

    int count();
    
    List<Alquiler_Tarjeta> buscarPorAlquiler(int idAlquiler);
    
    Alquiler_Tarjeta buscarPorAlquilerTarjeta(int idAlquiler, int idTarjeta);
    
    void eliminarTarjetasDeAlquiler(int idAlquiler);
    
    void agregarTodaAlquilerTarjetas(List<Alquiler_Tarjeta>listaAlquilerTarjetas);
    
}
