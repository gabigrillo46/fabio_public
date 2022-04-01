/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Pago;
import com.piantino.model.Tipo_pago;
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
public class Tipo_pagoFacade extends AbstractFacade<Tipo_pago> implements Tipo_pagoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Tipo_pagoFacade() {
        super(Tipo_pago.class);
    }

    @Override
    public Tipo_pago getTipoPagoToll() {
        Tipo_pago tipoPagoENcontrado = null;
        try {
            String consulta = "FROM Tipo_pago t WHERE t.nombre = '"+Pago.NOMBRE_TIPO_PAGO_TOLL+"' AND t.estado != " + Tipo_pago.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            List<Tipo_pago> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                tipoPagoENcontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return tipoPagoENcontrado;
    }
    
    @Override
    public Tipo_pago getTipoPagoLatePayment()
    {
                Tipo_pago tipoPagoENcontrado = null;
        try {
            String consulta = "FROM Tipo_pago t WHERE t.nombre = '"+Pago.NOMBRE_TIPO_PAGO_LATE_PAYMENT+"' AND t.estado != " + Tipo_pago.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            List<Tipo_pago> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                tipoPagoENcontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return tipoPagoENcontrado;
    }

}
