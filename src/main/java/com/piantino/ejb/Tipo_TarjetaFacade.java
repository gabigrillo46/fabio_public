/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tipo_Tarjeta;
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
public class Tipo_TarjetaFacade extends AbstractFacade<Tipo_Tarjeta> implements Tipo_TarjetaFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Tipo_TarjetaFacade() {
        super(Tipo_Tarjeta.class);
    }
    
    
        @Override
    public List<Tipo_Tarjeta> findAll()
    {
        List<Tipo_Tarjeta> marcasActivas;
        try
        {
            String consulta = "FROM Tipo_Tarjeta m WHERE m.estado != "+Tipo_Tarjeta.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            marcasActivas = q.getResultList();
        }
        catch(Exception e)
        {
            marcasActivas = new ArrayList<Tipo_Tarjeta>();
        }
        return marcasActivas;
    }
    
}
