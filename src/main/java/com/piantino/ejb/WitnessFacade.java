/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Witness;
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
public class WitnessFacade extends AbstractFacade<Witness> implements WitnessFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WitnessFacade() {
        super(Witness.class);
    }
    
        @Override
    public List<Witness> findAll()
    {
        List<Witness> marcasActivas;
        try
        {
            String consulta = "FROM Witness w WHERE w.estado != "+Witness.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            marcasActivas = q.getResultList();
        }
        catch(Exception e)
        {
            marcasActivas = new ArrayList<Witness>();
        }
        return marcasActivas;
    }
    
    @Override
    public Witness buscarPorNombre(String nombre)
    {
        List<Witness> marcasActivas;
        Witness witnessID = null;
        try
        {
            String consulta = "FROM Witness w WHERE w.estado != "+Witness.ESTADO_ELIMINADO+" AND w.nombre = ?1";
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            marcasActivas = q.getResultList();
            if(marcasActivas.size()>0)
            {
                witnessID = marcasActivas.get(0);
            }
        }
        catch(Exception e)
        {
            witnessID = new Witness();
        }
        return witnessID;
    }
    
    @Override
    public Witness find(Object id)
    {
        List<Witness> marcasActivas;
        Witness witnessID = null;
        try
        {
            String consulta = "FROM Witness w WHERE w.estado != "+Witness.ESTADO_ELIMINADO+" AND w.id = "+id;
            Query q = em.createQuery(consulta);
            marcasActivas = q.getResultList();
            if(marcasActivas.size()>0)
            {
                witnessID = marcasActivas.get(0);
            }
        }
        catch(Exception e)
        {
            witnessID = new Witness();
        }
        return witnessID;
    }
    
}
