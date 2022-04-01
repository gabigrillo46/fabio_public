/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler_Cliente;
import com.piantino.model.Mensaje_Interno;
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
public class Mensaje_InternoFacade extends AbstractFacade<Mensaje_Interno> implements Mensaje_InternoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Mensaje_InternoFacade() {
        super(Mensaje_Interno.class);
    }

    @Override
    public List<Mensaje_Interno> buscarMensajesInternosPorIdAlquiler(int idAlquiler) {
        List<Mensaje_Interno> listaMensajeInternos = new ArrayList();
        try {
            String consulta = "FROM Mensaje_Interno mi WHERE  mi.id_alquiler = ?1 order by mi.fecha_hora desc";
            Query q = em.createQuery(consulta);

            q.setParameter(1, idAlquiler);
            listaMensajeInternos = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMensajeInternos;
    }

    @Override
    public Mensaje_Interno getMensajeInternoPorId(long id) {
        Mensaje_Interno mensajeEncontrado = null;
        try {
            String consulta = "FROM Mensaje_Interno mi WHERE mi.id=" + id;
            Query q = em.createQuery(consulta);
            List<Mensaje_Interno> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                mensajeEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajeEncontrado;
    }

    @Override
    public List<Long> buscarMensajePorFiltro(Date fechaDesde, Date fechaHasta, int idUsuarioPara, int idUsuarioDesde, int idAlquiler, String palabra) {
        List<Long> listaMensajeInternos = new ArrayList();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            
            String consulta = "select distinct(mi.id) from mensaje_interno mi, mensaje_interno_destino mide where mi.id = mide.id_mensaje_interno";
            if (fechaDesde != null) {
                String fechaDesdeStr = sdf.format(fechaDesde);
                consulta = consulta + " and mi.fecha_hora >= '" + fechaDesdeStr + "'";
            }
            if (fechaHasta != null) {
                String fechaHastaStr = sdf.format(fechaHasta);
                consulta = consulta + " and mi.fecha_hora <= '" + fechaHastaStr + "'";
            }
            if (idUsuarioDesde > -1) {
                consulta = consulta + " and mi.id_usuario_creador = " + idUsuarioDesde;
            }
            if (palabra != null && palabra.trim().length() > 0) {
                consulta = consulta + " and mi.texto like '%" + palabra + "%' ";
            }
            if (idAlquiler > -1) {
                consulta = consulta + " and mi.id_alquiler = " + idAlquiler;
            }
            if (idUsuarioPara > -1) {
                consulta = consulta + " and mide.id_usuario_destino = " + idUsuarioPara;
            }
            consulta = consulta + " order by mide.leido asc, mi.fecha_hora desc";
            Query q = em.createNativeQuery(consulta);
            listaMensajeInternos = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMensajeInternos;
    }

    @Override
    public List<Mensaje_Interno> buscarMensajesInternosRelacionado(long idPrimero) {
        List<Mensaje_Interno> listaMensajeInternos = new ArrayList();
        try {
            String consulta = "FROM Mensaje_Interno mi WHERE  mi.id_primero = ?1 order by mi.fecha_hora desc";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idPrimero);
            listaMensajeInternos = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMensajeInternos;

    }

}
