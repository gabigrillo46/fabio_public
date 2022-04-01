/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler_Tarjeta;
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
public class Alquiler_TarjetaFacade extends AbstractFacade<Alquiler_Tarjeta> implements Alquiler_TarjetaFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Alquiler_TarjetaFacade() {
        super(Alquiler_Tarjeta.class);
    }
    
        @Override
    public List<Alquiler_Tarjeta> buscarPorAlquiler(int idAlquiler){

        List<Alquiler_Tarjeta> listaAlquiler = new ArrayList<Alquiler_Tarjeta>();
        try {
            String consulta = "FROM Alquiler_Tarjeta ac WHERE  ac.alquiler.id = ?1";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAlquiler);

            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            
        }
        return listaAlquiler;

    }
    
    public Alquiler_Tarjeta buscarPorAlquilerTarjeta(int idAlquiler, int idTarjeta)
    {
                List<Alquiler_Tarjeta> listaAlquiler = new ArrayList<Alquiler_Tarjeta>();
                Alquiler_Tarjeta alqTarjeta=null;
        try {
            String consulta = "FROM Alquiler_Tarjeta ac WHERE  ac.alquiler.id = ?1 and ac.tarjeta.id =?2";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAlquiler);
            q.setParameter(2, idTarjeta);
            listaAlquiler = q.getResultList();
            if(listaAlquiler.size()>0)
            {
                alqTarjeta = listaAlquiler.get(0);
            }
        } catch (Exception e) {
            
        }
        return alqTarjeta;
    }
    
    @Override
        public void eliminarTarjetasDeAlquiler(int idAlquiler)
        {
            List<Alquiler_Tarjeta> listaTarjetasAlquiler = new ArrayList<Alquiler_Tarjeta>();
            listaTarjetasAlquiler = this.buscarPorAlquiler(idAlquiler);
            if(listaTarjetasAlquiler!=null && listaTarjetasAlquiler.size()>0)
            {
                for(int u=0;u<listaTarjetasAlquiler.size();u++)
                {
                    Alquiler_Tarjeta alqTarjeta = listaTarjetasAlquiler.get(u);
                    this.remove(alqTarjeta);
                }
            }
        }
        
        @Override
        public void agregarTodaAlquilerTarjetas(List<Alquiler_Tarjeta>listaAlquilerTarjetas)
        {
            if(listaAlquilerTarjetas!=null && listaAlquilerTarjetas.size()>0)
            {
                for(int y=0;y<listaAlquilerTarjetas.size();y++)
                {
                    Alquiler_Tarjeta alquilerTarjeta = listaAlquilerTarjetas.get(y);
                    this.create(alquilerTarjeta);
                }
            }
        }
    
}
