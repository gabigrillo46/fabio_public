/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Cliente;
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
public class Alquiler_ClienteFacade extends AbstractFacade<Alquiler_Cliente> implements Alquiler_ClienteFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Alquiler_ClienteFacade() {
        super(Alquiler_Cliente.class);
    }

    @Override
    public List<Alquiler_Cliente> buscarPorAlquiler(int idAlquiler) {

        List<Alquiler_Cliente> listaAlquiler = new ArrayList();
        try {
            String consulta = "FROM Alquiler_Cliente ac WHERE  ac.alquiler.id = ?1";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAlquiler);

            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquiler;

    }

    @Override
    public Alquiler_Cliente buscarClientePrincipalPorAlquiler(int idAlquiler) {

        Alquiler_Cliente clienteEncontrado = null;
        try {
            String consulta = "FROM Alquiler_Cliente ac WHERE  ac.alquiler.id = ?1 and ac.es_principal =1 ";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAlquiler);

            List<Alquiler_Cliente> listaAlquiler = q.getResultList();

            if (listaAlquiler.size() > 0) {
                clienteEncontrado = listaAlquiler.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clienteEncontrado;

    }

    @Override
    public void eliminarClientePrincipalDelAlq(int idAlquiler) {

        List<Alquiler_Cliente> registrosDelAlq;
        boolean result = true;
        registrosDelAlq = this.buscarPorAlquiler(idAlquiler);
        if (registrosDelAlq != null) {
            for (int a = 0; a < registrosDelAlq.size(); a++) {
                Alquiler_Cliente alqCliBD = registrosDelAlq.get(a);
                if (alqCliBD.isEs_principal()) {
                    this.remove(alqCliBD);
                }
            }
        }
    }
    
    @Override
    public void agregarClientePrincipalAlAlq(Alquiler_Cliente alqCli) {
        this.eliminarClientePrincipalDelAlq(alqCli.getAlquiler().getId());
        this.create(alqCli);
    }

    @Override
    public void eliminarClientesAdicionales(int idAlquiler) {
        List<Alquiler_Cliente> registrosDelAlq;
        boolean result = true;
        registrosDelAlq = this.buscarPorAlquiler(idAlquiler);
        if (registrosDelAlq != null) {
            for (int a = 0; a < registrosDelAlq.size(); a++) {
                Alquiler_Cliente alqCliBD = registrosDelAlq.get(a);
                if (!alqCliBD.isEs_principal()) {
                    this.remove(alqCliBD);
                }
            }
        }
    }

    @Override
    public void agregarClienteAdicionalAlAlq(Alquiler_Cliente alqCli) {
        this.eliminarClientesAdicionales(alqCli.getAlquiler().getId());
        this.create(alqCli);
    }

    @Override
    public List<Alquiler_Cliente> buscarAlquileresDeCliente(int idCliente) {
        List<Alquiler_Cliente> listaAlquileres = new ArrayList<>();
        try {
            String consulta = "FROM Alquiler_Cliente ac WHERE  ac.cliente.id = " + idCliente + " and ac.es_principal =1 order by ac.alquiler.id asc ";
            Query q = em.createQuery(consulta);
            listaAlquileres = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquileres;
    }

}
