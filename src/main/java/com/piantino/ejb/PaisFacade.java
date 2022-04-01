/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Pais;
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
public class PaisFacade extends AbstractFacade<Pais> implements PaisFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaisFacade() {
        super(Pais.class);
    }
    
        @Override
    public List<Pais> findAll() {
        List<Pais> paisesActivos;
        try {
            String consulta = "FROM Pais p WHERE 1=1 ";
            Query q = em.createQuery(consulta);
            paisesActivos = q.getResultList();
        } catch (Exception e) {
            paisesActivos = new ArrayList<Pais>();
        }
        return paisesActivos;
    }
    
}
