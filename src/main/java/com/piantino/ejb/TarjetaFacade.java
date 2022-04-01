/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tarjeta;
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
public class TarjetaFacade extends AbstractFacade<Tarjeta> implements TarjetaFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarjetaFacade() {
        super(Tarjeta.class);
    }
    
  @Override
    public Tarjeta buscarPorNumeroNombre(String numero, String nomber)
    {
        Tarjeta tarjetaEncontrada=null;    

        
        try
        {
            String consulta ="FROM Tarjeta m WHERE m.numero = ?1 and m.nombre = ?2";
            Query q = em.createQuery(consulta);
            q.setParameter(1, numero);
            q.setParameter(2, nomber);
            List<Tarjeta> listaResultado = q.getResultList();
            if(listaResultado.size()>0)
            {
                tarjetaEncontrada =  listaResultado.get(0);
            }
        }
        catch(Exception e)
        {
            throw e;
        }
        return tarjetaEncontrada;
    
    }
    
}
