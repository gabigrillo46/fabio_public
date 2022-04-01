/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Auto;
import com.piantino.model.Auto_precios;
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
public class Auto_preciosFacade extends AbstractFacade<Auto_precios> implements Auto_preciosFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Auto_preciosFacade() {
        super(Auto_precios.class);
    }

    @Override
    public List<Auto_precios> getPrecioAutosPorAuto(int idAuto) {
        List<Auto_precios> listaPrecioAuto = new ArrayList<Auto_precios>();
        try {
            String consulta = "FROM Auto_precios ap WHERE  ap.auto.id = ?1 ";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAuto);
            listaPrecioAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPrecioAuto;
    }

    @Override
    public void saveListaAutoPrecioDefinitiva(List<Auto_precios> listaPrecioAutoDefinitiva, Auto auto) {
        List<Auto_precios> listaPrecioAutoActual = this.getPrecioAutosPorAuto(auto.getId());
        for (int a = 0; a < listaPrecioAutoDefinitiva.size(); a++) {
            Auto_precios autoPrecioDefinitivo = listaPrecioAutoDefinitiva.get(a);
            boolean yaExiste = false;
            for (int b = 0; b < listaPrecioAutoActual.size(); b++) {
                Auto_precios autoPrecioActual = listaPrecioAutoActual.get(b);
                if (autoPrecioDefinitivo.getId() == autoPrecioActual.getId()) {
                    yaExiste = true;
                }
            }
            if (yaExiste == false) {
                autoPrecioDefinitivo.setAuto(auto);
                this.create(autoPrecioDefinitivo);
            } else {
                this.edit(autoPrecioDefinitivo);
            }
        }
        for (int a = 0; a < listaPrecioAutoActual.size(); a++) {
            Auto_precios autoPrecioActual = listaPrecioAutoActual.get(a);
            boolean yaExiste = false;
            for (int b = 0; b < listaPrecioAutoDefinitiva.size(); b++) {
                Auto_precios autoPrecioDefinitivo = listaPrecioAutoDefinitiva.get(b);
                if (autoPrecioActual.getId() == autoPrecioDefinitivo.getId()) {
                    yaExiste = true;
                }
            }
            if (yaExiste == false) {
                this.remove(autoPrecioActual);
            }
        }
    }

    @Override
    public Auto_precios getPrecioDefectoPrimeroAuto(int idAuto) {
        Auto_precios listaPrecioAutoDefecto = null;
        try {
            String consulta = "FROM Auto_precios ap WHERE  ap.auto.id = " + idAuto + " and  ap.defecto = true";
            Query q = em.createQuery(consulta);
            List<Auto_precios> listaPrecioAuto = q.getResultList();
            if (listaPrecioAuto.size() > 0) {
                listaPrecioAutoDefecto = listaPrecioAuto.get(0);
            } else {
                consulta = "FROM Auto_precios ap WHERE  ap.auto.id = " + idAuto;
                q = em.createQuery(consulta);
                listaPrecioAuto = q.getResultList();
                if(listaPrecioAuto.size()>0)
                {
                    listaPrecioAutoDefecto = listaPrecioAuto.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPrecioAutoDefecto;
    }

}
