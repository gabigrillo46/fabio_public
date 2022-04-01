/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Template;
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
public class TemplateFacade extends AbstractFacade<Template> implements TemplateFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TemplateFacade() {
        super(Template.class);
    }
    
    

    @Override
    public List<Template> buscarPorFiltro(String nombre) {
        List<Template> listaTemplate = new ArrayList();
        try {
            String consulta = "from Template t where t.estado != " + Template.ESTADO_BAJA_LOGICA;
            if (nombre != null) {
                consulta = consulta + " t.nombre like '%" + nombre + "%'";
            }
            Query q = em.createQuery(consulta);
            listaTemplate = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTemplate;
    }

    @Override
    public List<Template> buscarTemplateActivo() {
        List<Template> listaTemplate = new ArrayList();
        try {
            String consulta = "from Template t where t.estado != " + Template.ESTADO_BAJA_LOGICA;
            Query q = em.createQuery(consulta);
            listaTemplate = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTemplate;
    }

}
