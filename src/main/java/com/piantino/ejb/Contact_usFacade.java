/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Contact_us;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Gabriel
 */
@Stateless
public class Contact_usFacade extends AbstractFacade<Contact_us> implements Contact_usFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Contact_usFacade() {
        super(Contact_us.class);
    }
    
}
