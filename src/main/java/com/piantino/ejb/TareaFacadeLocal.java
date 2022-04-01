/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tarea;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface TareaFacadeLocal {

    void create(Tarea tarea);

    void edit(Tarea tarea);

    void remove(Tarea tarea);

    Tarea find(Object id);

    List<Tarea> findAll();

    List<Tarea> findRange(int[] range);

    int count();
    
    List<Tarea> buscarPorFiltro(String realizado, String caducado, Date fecha, String id_usuario);
    
    Tarea buscarTareaPorId(int id);
    
    List<Tarea> buscarPendientesOrdenadas();
    
}
