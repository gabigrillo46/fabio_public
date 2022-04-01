/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Mensaje;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface MensajeFacadeLocal {

    void create(Mensaje mensaje);

    void edit(Mensaje mensaje);

    void remove(Mensaje mensaje);

    Mensaje find(Object id);

    List<Mensaje> findAll();

    List<Mensaje> findRange(int[] range);

    List<Mensaje> getMensajesDeAlquiler(int idAlquiler);
    
    int count();
    
    List<Mensaje> getMensajesPorFiltro(int idAlquiler, String palabra,String apellido, int idUsuario, Date fechaDesde, Date fechaHasta);
    
}
