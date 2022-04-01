/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler;
import com.piantino.model.Cliente;
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
public class ClienteFacade extends AbstractFacade<Cliente> implements ClienteFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

    @Override
    public List<Cliente> buscarPorApellidoSimilar(String nombre) throws Exception {
        List<Cliente> listaClientes;
        try {
            String consulta = "FROM Cliente c WHERE  c.apellido like ?1 and c.estado != " + Cliente.ESTADO_ELIMINADO + "";
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre + "%");
            listaClientes = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaClientes;

    }

    @Override
    public Cliente buscarPorNombreYApellido(String nombre, String apellido) {
        Cliente clienteEncontrado = null;
        try {
            String consulta = "FROM Cliente c WHERE  c.nombre = ?1 and c.apellido = ?2 and c.estado != " + Cliente.ESTADO_ELIMINADO + "";
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            q.setParameter(2, apellido);
            List<Cliente> listaClientes = q.getResultList();
            if (listaClientes.size() > 0) {
                clienteEncontrado = listaClientes.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clienteEncontrado;

    }

    public Cliente buscarPorID(int id) {
        Cliente clienteEncontrado = null;
        try {
            String consulta = "FROM Cliente c WHERE  c.id = ?1";
            Query q = em.createQuery(consulta);
            q.setParameter(1, id);
            List<Cliente> listaClientes = q.getResultList();
            if (listaClientes.size() > 0) {
                clienteEncontrado = listaClientes.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return clienteEncontrado;
    }

    @Override
    public Cliente buscarPorNombreYApellidoTodos(String nombre, String apellido) {
        Cliente clienteEncontrado = null;
        try {
            String consulta = "FROM Cliente c WHERE  c.nombre = ?1 and c.apellido = ?2";
            Query q = em.createQuery(consulta);
            q.setParameter(1, nombre);
            q.setParameter(2, apellido);
            List<Cliente> listaClientes = q.getResultList();
            if (listaClientes.size() > 0) {
                clienteEncontrado = listaClientes.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clienteEncontrado;
    }

    @Override
    public List<Cliente> getTodosLosBuenosClientes() {
        List<Cliente> listaClientes = new ArrayList<Cliente>();
        try {
            String consulta = "select distinct(c.id)"
                    + " from cliente c, alquiler_cliente ac, alquiler a"
                    + " where c.id = ac.id_cliente"
                    + " and ac.id_alquiler = a.id"
                    + " and a.estado!=" + Alquiler.ESTADO_ALQUILADO
                    + " and c.good_customer=" + true
                    + " and c.estado != " + Cliente.ESTADO_ELIMINADO;
            Query q = em.createNativeQuery(consulta);
            List<Integer> listaIdCliente = q.getResultList();
            for (int o = 0; o < listaIdCliente.size(); o++) {
                int idCliente = listaIdCliente.get(o);
                Cliente clienteAgregar = this.buscarPorID(idCliente);
                listaClientes.add(clienteAgregar);
            }
        } catch (Exception e) {
            throw e;
        }
        return listaClientes;

    }

    @Override
    public List<Cliente> buscarPorFiltro(String nombre, String telefono) {
        List<Cliente> listaClientes = new ArrayList();
        try {
            String consulta = "FROM Cliente c where c.tipo = "+Cliente.TIPO_CUSTOMER;
            if (nombre != null && nombre.trim().length() > 0) {
                consulta = consulta + " and (c.apellido like '%" + nombre + "%' or c.nombre like '%" + nombre + "%') ";
            }
            if (telefono != null && telefono.trim().length() > 0) {
                consulta = consulta + " and (c.telefono like '%" + telefono + "%' or c.movil like '%" + telefono + "%')";
            }
            Query q = em.createQuery(consulta);
            listaClientes = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaClientes;
    }

    @Override
    public List<Cliente> buscarProveedoresPorNombreEmpezando(String nombre) {
        List<Cliente> listaClientes = new ArrayList();
        try {
            String consulta = "FROM Cliente c where c.tipo = " + Cliente.TIPO_DEALER;
            if (nombre != null && nombre.trim().length() > 0) {
                consulta = consulta + " and (c.nombre like '" + nombre + "%' or c.apellido like '" + nombre + "%')";
            }
            Query q = em.createQuery(consulta);
            listaClientes = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaClientes;
    }

    @Override
    public List<Cliente> buscarProveedoresPorFiltro(String abn, String nombre) {
        List<Cliente> listaClientes = new ArrayList();
        try {
            String consulta = "FROM Cliente c where c.tipo = " + Cliente.TIPO_DEALER;
            if (nombre != null && nombre.trim().length() > 0) {
                consulta = consulta + " and (c.nombre like '%" + nombre + "%' or c.apellido like '%" + nombre + "%')";
            }
            if(abn !=null && abn.trim().length()>0)
            {
                consulta = consulta +" and c.abn like '%"+abn+"%'";
            }
            Query q = em.createQuery(consulta);
            listaClientes = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaClientes;
    }

    @Override
    public Cliente buscarProveedorPorNombre(String nombre) {
        Cliente proveedorEncontrado=null;
        List<Cliente> listaClientes = new ArrayList();
        try {
            String consulta = "FROM Cliente c where c.tipo = " + Cliente.TIPO_DEALER;
            if (nombre != null && nombre.trim().length() > 0) {
                consulta = consulta + " and c.apellido = '" + nombre+"'";
            }
            Query q = em.createQuery(consulta);
            listaClientes = q.getResultList();
            if(listaClientes.size()>1)
            {
                proveedorEncontrado = listaClientes.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return proveedorEncontrado;
    }

    @Override
    public Cliente buscarProveedorPorNumeroLicencia(String licencia) {
        Cliente proveedorEncontrado=null;
        List<Cliente> listaClientes = new ArrayList();
        try {
            String consulta = "FROM Cliente c where c.tipo = " + Cliente.TIPO_DEALER;
            if (licencia != null && licencia.trim().length() > 0) {
                consulta = consulta + " and c.licencia_numero = '" + licencia+"'";
            }
            Query q = em.createQuery(consulta);
            listaClientes = q.getResultList();
            if(listaClientes.size()>1)
            {
                proveedorEncontrado = listaClientes.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return proveedorEncontrado;
    }

}
