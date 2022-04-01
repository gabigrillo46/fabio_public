/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Auto;
import com.piantino.model.State;
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
public class StateFacade extends AbstractFacade<State> implements StateFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StateFacade() {
        super(State.class);
    }

    @Override
    public State getStatePorNombre(String nombre) {
        State stateEncontrado = null;
        try {
            String consulta = "FROM State s where s.nombre = '" + nombre + "'";
            Query q = em.createQuery(consulta);
            List<State> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                stateEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stateEncontrado;
    }


}
