/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Multa;
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
public class MultaFacade extends AbstractFacade<Multa> implements MultaFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MultaFacade() {
        super(Multa.class);
    }
    
    @Override
    public List<Multa> buscarPorFiltro(String rego, String numero, String lastName, String referencia, String impreso)
    {
        List<Multa> resultado=new ArrayList<Multa>();
        
        try
        {
            
            String consulta ="FROM Multa m WHERE 1=1 ";
            if(rego!=null && rego.trim().length()>0)
            {
                consulta = consulta +" AND m.rego like '%"+rego+"%' ";
            }
            if(numero !=null && numero.trim().length()>0)
            {
                consulta = consulta +" AND m.numero like '%"+numero+"%' ";
            }
            if(lastName!=null && lastName.trim().length()>0)
            {
                consulta = consulta +" AND m.clienteInfraccion.apellido like '%"+lastName+"%' ";
            }
            if(impreso!=null)
            {
                if(impreso.equalsIgnoreCase("1"))
                {
                    consulta = consulta +" AND m.impreso = 1 ";
                }
                else if(impreso.equalsIgnoreCase("0"))
                {
                    consulta = consulta +" AND m.impreso = 0 ";
                }
            }
            
            
            
            if(referencia !=null && referencia.trim().length()>0)
            {
                try
                {
                    int referenciaInt = Integer.parseInt(referencia);
                    consulta = consulta +" AND m.alquiler.id = "+referenciaInt;
                    
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            consulta = consulta +" AND m.estado != "+Multa.ESTADO_ELIMINADA;
             Query q = em.createQuery(consulta);
            
            resultado = q.getResultList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return resultado;
    }
    
    @Override
    public Multa buscarPorNumero(String numero)
    {
        Multa multaEncontrada=null;
        try
        {
            String consulta ="FROM Multa m WHERE m.numero = ?1 and m.estado != "+Multa.ESTADO_ELIMINADA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, numero);
            List<Multa> listaResultado = q.getResultList();
            if(listaResultado.size()>0)
            {
                multaEncontrada =  listaResultado.get(0);
            }
        }
        catch(Exception e)
        {
            multaEncontrada = null;
        }
        return multaEncontrada;
    }
    
            @Override
    public List<Multa> findAll()
    {
        List<Multa> marcasActivas;
        try
        {
            String consulta = "FROM Multa w WHERE w.estado != "+Multa.ESTADO_ELIMINADA;
            Query q = em.createQuery(consulta).setMaxResults(100);
            marcasActivas = q.getResultList();
        }
        catch(Exception e)
        {
            marcasActivas = new ArrayList<Multa>();
        }
        return marcasActivas;
    }
    
    public Multa find(Object id)
    {
        Multa multaEncontrada = null;
                List<Multa> marcasActivas;
        try
        {
            String consulta = "FROM Multa w WHERE w.estado != "+Multa.ESTADO_ELIMINADA +" AND w.id = ?1";
            
            Query q = em.createQuery(consulta).setMaxResults(100);
            q.setParameter(1, id);
            marcasActivas = q.getResultList();
            if(marcasActivas.size()>0)
            {
                multaEncontrada = marcasActivas.get(0);
            }
        }
        catch(Exception e)
        {
            multaEncontrada = null;
        }
        return multaEncontrada;
    }

    @Override
    public List<Multa> getListMultasPorIdAlquiler(int idAlquiler) {
                List<Multa> multasActivasDeAlquiler=new ArrayList<Multa>();
        try
        {
            String consulta = "FROM Multa w WHERE w.estado != "+Multa.ESTADO_ELIMINADA+ " AND w.alquiler.id="+idAlquiler;
            Query q = em.createQuery(consulta).setMaxResults(100);
            multasActivasDeAlquiler = q.getResultList();
        }
        catch(Exception e)
        {
            multasActivasDeAlquiler = new ArrayList<Multa>();
        }
        return multasActivasDeAlquiler;

    }

    @Override
    public Multa buscarPorId(int i) {
        Multa multaEncontrada=null;
        try
        {
            String consulta ="FROM Multa m WHERE m.id = ?1 and m.estado != "+Multa.ESTADO_ELIMINADA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, i);
            List<Multa> listaResultado = q.getResultList();
            if(listaResultado.size()>0)
            {
                multaEncontrada =  listaResultado.get(0);
            }
        }
        catch(Exception e)
        {
            multaEncontrada = null;
        }
        return multaEncontrada;
    }
    
}
