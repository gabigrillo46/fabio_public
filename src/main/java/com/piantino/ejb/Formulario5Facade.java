/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;


import com.piantino.model.Formulario5;
import com.piantino.model.Modelo;
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
public class Formulario5Facade extends AbstractFacade<Formulario5> implements Formulario5FacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Formulario5Facade() {
        super(Formulario5.class);
    }

    @Override
    public List<Formulario5> getListaFormularioAuto(int idAuto) {
        List<Formulario5> listaFormulariosAuto = new ArrayList<Formulario5>();
        try {
            String consulta = "FROM Formulario5 f WHERE  f.auto.id = ?1 order by f.id desc";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAuto);
            listaFormulariosAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFormulariosAuto;        
    }

    @Override
    public Formulario5 getFormularioDeVenta(long idVenta) {
        Formulario5 formularioVenta = null;
        try {
            String consulta = "FROM Formulario5 f WHERE f.venta.id = "+idVenta+" and f.estado = "+Formulario5.ESTADO_ACTIVO;
            Query q = em.createQuery(consulta);
            List<Formulario5> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                formularioVenta = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return formularioVenta;
        
    }
    
}
