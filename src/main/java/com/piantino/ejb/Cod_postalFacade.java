/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Cod_postal;
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
public class Cod_postalFacade extends AbstractFacade<Cod_postal> implements Cod_postalFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Cod_postalFacade() {
        super(Cod_postal.class);
    }

    @Override
    public List<Cod_postal> getListaCodigoPostalPorCOdigo(String codigo) {
              List<Cod_postal> listaCodigoPostal = new ArrayList<Cod_postal>();
        try {
            String consulta = "FROM Cod_postal cp WHERE cp.codigo_postal = "+codigo;
            Query q = em.createQuery(consulta);
            listaCodigoPostal = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCodigoPostal;
    }
    
}
