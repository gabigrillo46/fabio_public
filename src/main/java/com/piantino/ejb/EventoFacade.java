/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Evento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Gabriel
 */
@Stateless
public class EventoFacade extends AbstractFacade<Evento> implements EventoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoFacade() {
        super(Evento.class);
    }

    @Override
    public List<Evento> buscarPorFiltro(Date fechaDesde, Date fechaHasta, String email) {
        List<Evento> listaEventosResultados = new ArrayList<>();
        try {
            String consulta = " FROM Evento e WHERE 1=1 ";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (fechaDesde != null) {
                String fechaDesdeStr = sdf.format(fechaDesde);
                consulta += " AND e.fecha >= '" + fechaDesdeStr + "' ";
            }
            if (fechaHasta != null) {
                String fechaHastaStr = sdf.format(fechaHasta);
                consulta += " AND e.fecha <= '" + fechaHastaStr + "' ";
            }
            if (email != null && email.trim().length() > 0) {
                consulta += " AND e.usuario.email like '%" + email + "%' ";
            }
            Query a = em.createQuery(consulta);
            listaEventosResultados = a.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEventosResultados;
    }

}
