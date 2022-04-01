/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Impuesto;
import com.piantino.model.Sucursal;
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
public class ImpuestoFacade extends AbstractFacade<Impuesto> implements ImpuestoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImpuestoFacade() {
        super(Impuesto.class);
    }
    
    @Override
    public List<Impuesto> findAll()
    {
        List<Impuesto> impuestosActivos;
        try
        {
            String consulta = "FROM Impuesto m WHERE m.estado != "+Impuesto.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            impuestosActivos = q.getResultList();
        }
        catch(Exception e)
        {
            impuestosActivos = new ArrayList<Impuesto>();
        }
        return impuestosActivos;
    }
    
    

    @Override
    public Impuesto buscarImpusetoPorNomber(String nombre) throws Exception {
               
               Impuesto impuestoEncontrado = null;
        try {
            String consulta = "FROM Impuesto a WHERE a.nombre = ?1 AND a.estado <> "+Impuesto.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            List<Impuesto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                impuestoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return impuestoEncontrado;
    }
    
    @Override
    public Impuesto getImpuestoParaToll()
    {
        Impuesto impuestoEncontrado=null;
        try {
            String consulta = "FROM Impuesto a WHERE a.tipo_asociado ="+Impuesto.TIPO_ASOCIADO_TOLL+" AND a.estado <> "+Impuesto.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            List<Impuesto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                impuestoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }        
        
        return impuestoEncontrado;
    }

    @Override
    public Impuesto getImpuestoParaLatePayment()
    {
        Impuesto impuestoEncontrado=null;
        try {
            String consulta = "FROM Impuesto a WHERE a.tipo_asociado ="+Impuesto.TIPO_ASOCIADO_LATE_PAYMENT+" AND a.estado <> "+Impuesto.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            List<Impuesto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                impuestoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }        
        
        return impuestoEncontrado;        
    }
   

    @Override
    public List<Impuesto> buscarImpuestosAuto(int idSucursal) throws Exception {
        List<Impuesto> listaImpuestosAuto = new ArrayList<Impuesto>();
        try
        {
            String consulta = "FROM Impuesto i WHERE (i.sucursal.id = ?1 OR i.sucursal.id = "+Sucursal.ID_TODAS+")  AND i.estado != "+Impuesto.ESTADO_ELIMINADO;
             Query q = em.createQuery(consulta);
            q.setParameter(1, idSucursal);
            List<Impuesto> listaResultado = q.getResultList();
            listaImpuestosAuto = listaResultado;
        }
        catch(Exception e)
        {
            throw e;
        }
        return listaImpuestosAuto;
    }
    
}
