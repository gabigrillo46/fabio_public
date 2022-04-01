/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Mensaje_Interno;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Mensaje_InternoFacadeLocal {

    void create(Mensaje_Interno mensaje_Interno);

    void edit(Mensaje_Interno mensaje_Interno);

    void remove(Mensaje_Interno mensaje_Interno);

    Mensaje_Interno find(Object id);

    List<Mensaje_Interno> findAll();

    List<Mensaje_Interno> findRange(int[] range);

    int count();
    
    List<Mensaje_Interno> buscarMensajesInternosPorIdAlquiler(int idAlquiler);
    

    
    List<Mensaje_Interno> buscarMensajesInternosRelacionado(long idMensajePadre);
    
    Mensaje_Interno getMensajeInternoPorId(long id);
    
    List<Long> buscarMensajePorFiltro(Date fechaDesde, Date fechaHasta, int idUsuarioPara, int idUsuarioDesde, int idAlquiler, String palabra);
    
}
