/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Drivetrain;
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
public class DrivetrainFacade extends AbstractFacade<Drivetrain> implements DrivetrainFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DrivetrainFacade() {
        super(Drivetrain.class);
    }

    @Override
    public List<Drivetrain> getTodasActivas() {
        List<Drivetrain> listaDrivetrain;
        try {
            String consulta = "FROM Drivetrain d WHERE  d.estado != " + Drivetrain.ESTADO_ELIMINADO + "";
            Query q = em.createQuery(consulta);
            listaDrivetrain = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaDrivetrain;
    }

}
