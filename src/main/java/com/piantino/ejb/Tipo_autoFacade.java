/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_auto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gaby
 */
@Stateless
public class Tipo_autoFacade extends AbstractFacade<Tipo_auto> implements Tipo_autoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Tipo_autoFacade() {
        super(Tipo_auto.class);
    }

    @Override
    public Tipo_auto buscarPorNombre(String nombre) throws Exception {
        Tipo_auto marcaEncontrada = null;

        try {
            String consulta = "FROM Tipo_auto m WHERE m.nombre = ?1 AND m.estado != " + Tipo_auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            List<Tipo_auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                marcaEncontrada = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return marcaEncontrada;

    }

    @Override
    public List<Tipo_auto> findAll() {
        List<Tipo_auto> modelosActivos;
        try {
            String consulta = "FROM Tipo_auto a WHERE a.estado != " + Tipo_auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            modelosActivos = q.getResultList();
        } catch (Exception e) {
            modelosActivos = new ArrayList<Tipo_auto>();
        }
        return modelosActivos;
    }

}
