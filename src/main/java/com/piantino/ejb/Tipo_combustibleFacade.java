/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_combustible;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gaby
 */
@Stateless
public class Tipo_combustibleFacade extends AbstractFacade<Tipo_combustible> implements Tipo_combustibleFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Tipo_combustibleFacade() {
        super(Tipo_combustible.class);
    }
    
}
