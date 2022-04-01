/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Evento;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface EventoFacadeLocal {

    void create(Evento evento);

    void edit(Evento evento);

    void remove(Evento evento);

    Evento find(Object id);

    List<Evento> findAll();

    List<Evento> findRange(int[] range);

    int count();
    
    List<Evento> buscarPorFiltro(Date fechaDesde, Date fechaHasta, String email);
}
