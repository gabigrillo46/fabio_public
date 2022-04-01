/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Auto_imagenes;
import java.util.ArrayList;
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
public class Auto_imagenesFacade extends AbstractFacade<Auto_imagenes> implements Auto_imagenesFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Auto_imagenesFacade() {
        super(Auto_imagenes.class);
    }

    @Override
    public List<Auto_imagenes> getListaPorAuto(int idAuto) {
        List<Auto_imagenes> listaAlquiler = new ArrayList<Auto_imagenes>();
        try {
            String consulta = "FROM Auto_imagenes ai WHERE  ai.auto.id = ?1 order by ai.orden asc";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAuto);

            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            
        }
        return listaAlquiler;
    }
    
}
