/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Marca;
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
public class MarcaFacade extends AbstractFacade<Marca> implements MarcaFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MarcaFacade() {
        super(Marca.class);
    }
    
    @Override
    public List<Marca> findAll()
    {
        List<Marca> marcasActivas;
        try
        {
            String consulta = "FROM Marca m WHERE m.estado != "+Marca.ESTADO_ELIMINADO+" order by m.nombre asc";
            Query q = em.createQuery(consulta);
            marcasActivas = q.getResultList();
        }
        catch(Exception e)
        {
            marcasActivas = new ArrayList<Marca>();
        }
        return marcasActivas;
    }

    @Override
    public Marca buscarPorNombre(String nombre) throws Exception {
        Marca marcaEncontrada=null;    

        
        try
        {
            String consulta ="FROM Marca m WHERE m.nombre = ?1 and m.estado != "+Marca.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            List<Marca> listaResultado = q.getResultList();
            if(listaResultado.size()>0)
            {
                marcaEncontrada =  listaResultado.get(0);
            }
        }
        catch(Exception e)
        {
            throw e;
        }
        return marcaEncontrada;
    
    }
    
}
