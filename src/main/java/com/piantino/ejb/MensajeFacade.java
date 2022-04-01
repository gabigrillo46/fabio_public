/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Mensaje;
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
public class MensajeFacade extends AbstractFacade<Mensaje> implements MensajeFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MensajeFacade() {
        super(Mensaje.class);
    }

    public List<Mensaje> getMensajesDeAlquiler(int idAlquiler) {
        List<Mensaje> mensajesDeAlquiler = new ArrayList<Mensaje>();
        try {
            String consulta = "FROM Mensaje m WHERE m.alquiler.id = " + idAlquiler + " order by m.fecha_hora asc";
            Query q = em.createQuery(consulta);
            mensajesDeAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajesDeAlquiler;
    }

    @Override
    public List<Mensaje> getMensajesPorFiltro(int idAlquiler, String palabra, String apellido, int idUsuario, Date fechaDesde, Date fechaHasta) {
        List<Mensaje> mensajesDeAlquiler = new ArrayList<Mensaje>();
        boolean filtro = false;
        try {
            String consulta = "FROM Mensaje m WHERE 1=1 ";
            if (idAlquiler > -1) {
                consulta = consulta + " and m.alquiler.id = " + idAlquiler;
                filtro = true;
            }
            if (palabra != null && palabra.trim().length() > 0) {
                consulta = consulta + " and m.mensaje like '%" + palabra + "%' ";
                filtro = true;
            }
            if (apellido != null && apellido.trim().length() > 0) {
                consulta = consulta + " and m.cliente.apellido = '" + apellido + "' ";
                filtro = true;
            }
            if (idUsuario > -1) {
                consulta = consulta + " and m.usuario.id = " + idUsuario;
                filtro = true;
            }
            if (fechaDesde != null && fechaHasta != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                consulta = consulta + " and m.fecha_hora >= '" + sdf.format(fechaDesde) + "' and m.fecha_hora <= '" + sdf.format(fechaHasta) + "' ";
                filtro = true;
            }
            Query q = em.createQuery(consulta);
            if (filtro == false) {
                q.setMaxResults(1000);
            }
            mensajesDeAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajesDeAlquiler;
    }

}
