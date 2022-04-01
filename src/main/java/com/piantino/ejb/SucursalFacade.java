/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

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
public class SucursalFacade extends AbstractFacade<Sucursal> implements SucursalFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SucursalFacade() {
        super(Sucursal.class);
    }

    @Override
    public List<Sucursal> findAll() {
        List<Sucursal> modelosActivos;
        try {
            String consulta = "FROM Sucursal a WHERE a.estado != " + Sucursal.ESTADO_ELIMINADA;
            Query q = em.createQuery(consulta);
            modelosActivos = q.getResultList();
        } catch (Exception e) {
            modelosActivos = new ArrayList<Sucursal>();
        }
        return modelosActivos;
    }

    @Override
    public List<Sucursal> buscarTodes() {
        List<Sucursal> modelosActivos;
        try {
            String consulta = "FROM Sucursal a WHERE a.id=1 OR a.estado != " + Sucursal.ESTADO_ELIMINADA;
            Query q = em.createQuery(consulta);
            modelosActivos = q.getResultList();
        } catch (Exception e) {
            modelosActivos = new ArrayList<Sucursal>();
        }
        return modelosActivos;
    }

    @Override
    public Sucursal buscarPorNombre(String nombre) throws Exception {
        Sucursal marcaEncontrada = null;

        try {
            String consulta = "FROM Sucursal m WHERE m.nombre = ?1 AND m.estado != " + Sucursal.ESTADO_ELIMINADA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            List<Sucursal> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                marcaEncontrada = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return marcaEncontrada;
    }

    @Override
    public List<Sucursal> buscarSucursalesPorNombreSimilar(String nombre) {
        List<Sucursal> listaSucursalesResultado = new ArrayList();
        String consulta = "FROM Sucursal s where s.estado != "+Sucursal.ESTADO_ELIMINADA;
        if (nombre != null) {
            consulta = consulta + " and s.nombre like '%" + nombre + "%' ";
        }
        Query q = em.createQuery(consulta);
        listaSucursalesResultado = q.getResultList();
        return listaSucursalesResultado;
    }

    @Override
    public List<Sucursal> buscarSucursalesPorLicencia(int idLicence) {
        List<Sucursal> listaSucursalesResultado = new ArrayList();
        String consulta = "FROM Sucursal s where s.estado != "+Sucursal.ESTADO_ELIMINADA+" and s.licence.id = "+idLicence;
        Query q = em.createQuery(consulta);
        listaSucursalesResultado = q.getResultList();
        return listaSucursalesResultado;
    }

}
