/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Dealer_licence;
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
public class Dealer_licenceFacade extends AbstractFacade<Dealer_licence> implements Dealer_licenceFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Dealer_licenceFacade() {
        super(Dealer_licence.class);
    }

    @Override
    public Dealer_licence getLicenceByNumber(String number) {
        Dealer_licence licenceEncontrada = null;
        try {
            String consulta = "FROM Dealer_licence d WHERE d.numero = '"+number+"'";
            Query q = em.createQuery(consulta);
            List<Dealer_licence> listaLicenceResultado = q.getResultList();
            if (listaLicenceResultado.size() > 0) {
                licenceEncontrada = listaLicenceResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return licenceEncontrada;

    }

    @Override
    public List<Dealer_licence> getListaLicenceByNumber(String number) {
        List<Dealer_licence> listaLicenceEncontrada = null;
        try {
            String consulta = "FROM Dealer_licence d WHERE d.numero like '%" + number + "%'";
            Query q = em.createQuery(consulta);
            listaLicenceEncontrada = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaLicenceEncontrada;
    }

}
