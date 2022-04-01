/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Venta;
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
public class VentaFacade extends AbstractFacade<Venta> implements VentaFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VentaFacade() {
        super(Venta.class);
    }

    @Override
    public Venta getVentaPorAuto(int idAuto) {
        Venta ventaDelAuto = null;
        String consulta = "";
        try {
            consulta = "FROM Venta v where v.estado = "+Venta.ESTADO_ACTIVO;

                consulta = consulta +" and v.auto.id = "+idAuto;
            Query query = em.createQuery(consulta);
            List<Venta> resultado = query.getResultList();
            if (!resultado.isEmpty()) {
                ventaDelAuto = resultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }

        return ventaDelAuto;
    }

}
