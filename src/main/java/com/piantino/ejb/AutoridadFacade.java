/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Autoridad;
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
public class AutoridadFacade extends AbstractFacade<Autoridad> implements AutoridadFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AutoridadFacade() {
        super(Autoridad.class);
    }
    
          @Override
    public List<Autoridad> findAll()
    {
        List<Autoridad> marcasActivas;
        try
        {
            String consulta = "FROM Autoridad a WHERE a.estado != "+Autoridad.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            marcasActivas = q.getResultList();
        }
        catch(Exception e)
        {
            marcasActivas = new ArrayList<Autoridad>();
        }
        return marcasActivas;
    }
    
    @Override
    public Autoridad find(Object id)
    {
              List<Autoridad> marcasActivas;
              Autoridad witnessID = null;
        try
        {
            String consulta = "FROM Autoridad a WHERE a.estado != "+Autoridad.ESTADO_ELIMINADO+" AND a.id = "+id;
            Query q = em.createQuery(consulta);
            marcasActivas = q.getResultList();
            if(marcasActivas.size()>0)
            {
                witnessID = marcasActivas.get(0);
            }
        }
        catch(Exception e)
        {
            witnessID = new Autoridad();
        }
        return witnessID;
    }
    
    
    @Override
    public Autoridad buscarPorNombre(String nombre)
    {
        List<Autoridad> marcasActivas;
        Autoridad autoridadID = null;
        try
        {
            String consulta = "FROM Autoridad a WHERE a.estado != "+Autoridad.ESTADO_ELIMINADO+" AND a.nombre = ?1";
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            marcasActivas = q.getResultList();
            if(marcasActivas.size()>0)
            {
                autoridadID = marcasActivas.get(0);
            }
        }
        catch(Exception e)
        {
            autoridadID = null;
        }
        return autoridadID;
    }    
    
}
