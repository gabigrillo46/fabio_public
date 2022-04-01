/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Categoria_usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gaby
 */
@Stateless
public class Categoria_usuarioFacade extends AbstractFacade<Categoria_usuario> implements Categoria_usuarioFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Categoria_usuarioFacade() {
        super(Categoria_usuario.class);
    }
    
}
