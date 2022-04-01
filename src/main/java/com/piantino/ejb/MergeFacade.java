/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Merge;
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
public class MergeFacade extends AbstractFacade<Merge> implements MergeFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MergeFacade() {
        super(Merge.class);
    }

    @Override
    public List<Merge> buscarPorFiltro(String nombre, Date fechaInicio, Date fechaFin) {
        List<Merge> listaMergeResultado = new ArrayList();
        try {
            String consulta = "FROM Merge m WHERE  1=1 ";
            boolean filtro = false;
            if (fechaInicio != null && fechaFin != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaDesdeStr = sdf.format(fechaInicio);
                String fechaHastaStr = sdf.format(fechaFin);
                consulta = consulta + " AND m.fecha >= '" + fechaDesdeStr + "' AND m.fecha <= '" + fechaHastaStr + "' ";
                filtro = true;
            }
            if (nombre != null) {
                consulta = consulta + " AND m.nombre like '%" + nombre + "%' ";
                filtro = true;
            }
            Query a = em.createQuery(consulta);
            if (filtro == false) {
                a.setMaxResults(50);
            }
            listaMergeResultado = a.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaMergeResultado;
    }

    @Override
    public Merge buscarPorNombre(String nombre) {
        Merge mergeResultado = null;
        try {
            String consulta = "FROM Merge m WHERE  1=1 ";
            consulta = consulta + " AND m.nombre = '" + nombre + "' ";
            Query a = em.createQuery(consulta);
            List<Merge> listaResultado = a.getResultList();
            if (listaResultado.size() > 0) {
                mergeResultado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mergeResultado;
    }

}
