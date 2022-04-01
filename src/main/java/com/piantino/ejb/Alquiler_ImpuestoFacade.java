/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Impuesto;
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
public class Alquiler_ImpuestoFacade extends AbstractFacade<Alquiler_Impuesto> implements Alquiler_ImpuestoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Alquiler_ImpuestoFacade() {
        super(Alquiler_Impuesto.class);
    }

    
    @Override
    public List<Alquiler_Impuesto> buscarImpuestosAlquiler(int idAlquiler){
                List<Alquiler_Impuesto> listaImpuestosAuto = new ArrayList<Alquiler_Impuesto>();
        try
        {
            String consulta = "FROM Alquiler_Impuesto i WHERE i.alquiler.id = ?1";
             Query q = em.createQuery(consulta);
            q.setParameter(1, idAlquiler);
            
            List<Alquiler_Impuesto> listaResultado = q.getResultList();
            listaImpuestosAuto = listaResultado;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaImpuestosAuto;

    }
    
    public void eliminarPorAlquiler(int idAlquiler)
    {
        try
        {
            List<Alquiler_Impuesto> listaPorAlquiler = this.buscarImpuestosAlquiler(idAlquiler);
            for(int a=0;a<listaPorAlquiler.size();a++)
            {
                Alquiler_Impuesto impuestoAEliminar = listaPorAlquiler.get(a);
                this.remove(impuestoAEliminar);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void saveListaImpuestoDefinitiva(List<Alquiler_Impuesto> listaAlquilerImpuestoDefinitiva, int idAlquiler) {
        List<Alquiler_Impuesto> listaAlquilerImpuestoActual = this.buscarImpuestosAlquiler(idAlquiler);
        for(int a=0;a<listaAlquilerImpuestoDefinitiva.size();a++)
        {
            Alquiler_Impuesto alqImpuestoDefinitivo = listaAlquilerImpuestoDefinitiva.get(a);
            boolean existe =false;
            for(int b=0;b<listaAlquilerImpuestoActual.size();b++)
            {
                Alquiler_Impuesto alqImpuestoActual = listaAlquilerImpuestoActual.get(b);
                if(alqImpuestoActual.getId()==alqImpuestoDefinitivo.getId())
                {
                    existe =true;
                }
            }
            if(existe)
            {
                this.edit(alqImpuestoDefinitivo);
            }
            else
            {
                this.create(alqImpuestoDefinitivo);
            }
        }
        for(int a=0;a<listaAlquilerImpuestoActual.size();a++)
        {
            Alquiler_Impuesto alqImpuestoActual =listaAlquilerImpuestoActual.get(a);
            boolean existe =false;
            for(int b=0;b<listaAlquilerImpuestoDefinitiva.size();b++)
            {
                Alquiler_Impuesto alqImpuestoDefinitivo = listaAlquilerImpuestoDefinitiva.get(b);                
                if(alqImpuestoActual.getId()==alqImpuestoDefinitivo.getId())
                {
                    existe=true;
                }
            }
            if(existe==false)
            {
                this.remove(alqImpuestoActual);
            }
        }        
    }
    
}
