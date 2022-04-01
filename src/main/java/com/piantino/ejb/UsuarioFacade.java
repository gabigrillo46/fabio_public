/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.controller.Constante;
import com.piantino.model.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario iniciarSesion(Usuario usuarioParam) {
        Usuario usu = null;
        String consulta = "";
        try {
            consulta = "FROM Usuario u WHERE u.email = '"+usuarioParam.getEmail()+"'";
            Query query = em.createQuery(consulta);
            List<Usuario> resultado = query.getResultList();
            if (!resultado.isEmpty()) {
                usu = resultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }

        return usu;
    }

    @Override
    public Usuario getUsuarioPorId(int id) {
        Usuario usu = null;
        String consulta = "";
        try {
            consulta = "FROM Usuario u WHERE u.id =?1 AND u.estado = " + Constante.ESTADOS_USUARIOS.ACTIVO;
            Query query = em.createQuery(consulta);
            query.setParameter(1, id);
            List<Usuario> resultado = query.getResultList();
            if (!resultado.isEmpty()) {
                usu = resultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }

        return usu;
    }

    @Override
    public List<Usuario> listaUsuarioActivos() {
        List<Usuario> resultado = new ArrayList();
        String consulta ="";
        try {
            consulta = "FROM Usuario u WHERE  u.estado != " + Constante.ESTADOS_USUARIOS.BAJA_LOGICA;
            Query query = em.createQuery(consulta);
            
            resultado = query.getResultList();
            
        } catch (Exception e) {
            throw e;
        }
        return resultado;

    }

    @Override
    public Usuario getUsuarioPorEmail(String email) {
        Usuario usu = null;
        String consulta = "";
        try {
            consulta = "FROM Usuario u WHERE u.email =?1 ";
            Query query = em.createQuery(consulta);
            query.setParameter(1, email);
            List<Usuario> resultado = query.getResultList();
            if (!resultado.isEmpty()) {
                usu = resultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }

        return usu;
    }

}
