/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Modelo;
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
public class ModeloFacade extends AbstractFacade<Modelo> implements ModeloFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModeloFacade() {
        super(Modelo.class);
    }

    @Override
    public Modelo buscarPorNombre(String nombre, int idCategoria) throws Exception {
        Modelo marcaEncontrada = null;

        try {
            String consulta = "FROM Modelo m WHERE m.nombre = ?1 AND m.marca.id = ?2 and m.estado != "+Modelo.ESTADO_ELIMINADO;
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            q.setParameter(2, idCategoria);
            List<Modelo> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                marcaEncontrada = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return marcaEncontrada;

    }

    @Override
    public List<Modelo> findAll() {
        List<Modelo> modelosActivos;
        try {
            String consulta = "FROM Modelo m WHERE m.estado != " + Modelo.ESTADO_ELIMINADO+" order by m.nombre asc";
            Query q = em.createQuery(consulta);
            modelosActivos = q.getResultList();
        } catch (Exception e) {
            modelosActivos = new ArrayList<Modelo>();
        }
        return modelosActivos;
    }

    @Override
    public List<Modelo> buscarPorMarca(int idMarca){
        List<Modelo> listaModeloMarca = new ArrayList<Modelo>();
        try {
            String consulta = "FROM Modelo m WHERE  m.marca.id = ?1 AND m.estado != " + Modelo.ESTADO_ELIMINADO + " order by m.nombre asc";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idMarca);

            listaModeloMarca = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaModeloMarca;
    }

}
