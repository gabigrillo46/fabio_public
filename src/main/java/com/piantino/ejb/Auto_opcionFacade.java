/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Auto;
import com.piantino.model.Auto_opcion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Gabriel
 */
@Stateless
public class Auto_opcionFacade extends AbstractFacade<Auto_opcion> implements Auto_opcionFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Auto_opcionFacade() {
        super(Auto_opcion.class);
    }

    @Override
    public void saveListaAutoPrecioDefinitiva(List<Auto_opcion> listaOpcionAutoDefinitiva, Auto auto) {
        List<Auto_opcion> listaOpcionAutoActual = this.getOpcionesPorAuto(auto);
        for(int a=0;a<listaOpcionAutoDefinitiva.size();a++)
        {
            Auto_opcion autoOpcionDefinitiva = listaOpcionAutoDefinitiva.get(a);
            boolean existe =false;
            for(int b=0;b<listaOpcionAutoActual.size();b++)
            {
                Auto_opcion autoOpcionActual = listaOpcionAutoActual.get(b);
                if(autoOpcionActual.getId() == autoOpcionDefinitiva.getId())
                {
                    existe=true;
                }
            }
            if(existe)
            {
                this.edit(autoOpcionDefinitiva);
            }
            else
            {
                this.create(autoOpcionDefinitiva);
            }
        }
        
        for(int a=0;a<listaOpcionAutoActual.size();a++)
        {
            Auto_opcion autoOpcionActual = listaOpcionAutoActual.get(a);
            boolean existe =false;
            for(int b=0;b<listaOpcionAutoDefinitiva.size();b++)
            {
                Auto_opcion autoOpcionDefinitiva = listaOpcionAutoDefinitiva.get(b);
                if(autoOpcionActual.getId()==autoOpcionDefinitiva.getId())
                {
                    existe = true;
                }
            }
            if(existe==false)
            {
                this.remove(autoOpcionActual);
            }
        }
    }

    @Override
    public List<Auto_opcion> getOpcionesPorAuto(Auto auto) {
        List<Auto_opcion> listaOpcionAuto = new ArrayList<Auto_opcion>();
        try {
            String consulta = "FROM Auto_opcion ao WHERE  ao.auto.id = ?1 ";
            Query q = em.createQuery(consulta);
            q.setParameter(1, auto.getId());
            listaOpcionAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaOpcionAuto;
    
    }
    
}
