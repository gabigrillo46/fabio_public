/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.PagoImportado;
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
public class PagosImportadosFacade extends AbstractFacade<PagoImportado> implements PagosImportadosFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagosImportadosFacade() {
        super(PagoImportado.class);
    }
    
    public PagoImportado buscarPorNumeroRecibo(String numeroRecibo)
    {
                PagoImportado pagoEncontrado=null;    

        
        try
        {
            String consulta ="FROM PagoImportado m WHERE m.reciboNumero = ?1";
            Query q = em.createQuery(consulta);
            q.setParameter(1, numeroRecibo);
            List<PagoImportado> listaResultado = q.getResultList();
            if(listaResultado.size()>0)
            {
                pagoEncontrado =  listaResultado.get(0);
            }
        }
        catch(Exception e)
        {
            throw e;
        }
        return pagoEncontrado;
    }
    
}
