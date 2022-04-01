/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Parametro;
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
public class ParametroFacade extends AbstractFacade<Parametro> implements ParametroFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametroFacade() {
        super(Parametro.class);
    }
    
    @Override
    public Parametro buscarPorGrupo(String grupo, String calif_grupo, String param, String calif_param)
    {
        Parametro parametroEncontrado=null;
        
        try
        {
            String consulta ="FROM Parametro p WHERE 1=1";
            if(grupo!=null && grupo.trim().length()>0)
            {
                consulta = consulta +" and p.grupo = '"+grupo+"' ";
            }
            if(calif_grupo !=null && calif_grupo.trim().length()>0)
            {
                consulta = consulta +" and p.calif_grupo = '"+calif_grupo+"' ";
            }
            if(param !=null && param.trim().length()>0)
            {
                consulta = consulta +" and p.param = '"+param+"' ";
            }
            if(calif_param !=null && calif_param.trim().length()>0)
            {
                consulta = consulta +" and p.calif_param = '"+calif_param+"' ";
            }
            Query q = em.createQuery(consulta);
            List<Parametro> listaResultado = q.getResultList();
            if(listaResultado.size()>0)
            {
                parametroEncontrado =  listaResultado.get(0);
            }
        }
        catch(Exception e)
        {
            throw e;
        }
        return parametroEncontrado;
    
    }

    @Override
    public float getValorFloat(String grupo, String calif_grupo, String param, String calif_param) {
        float resultado =-1;
        Parametro  parametro = this.buscarPorGrupo(grupo, calif_grupo, param, calif_param);
        if(parametro==null)
        {
            return resultado;
        }
        String valorString = parametro.getValor();
        if(valorString==null || valorString.trim().length()==0)
        {
            return resultado;
        }
        try
        {
            resultado = Float.valueOf(valorString);
            return resultado;
        }
        catch(Exception e)
        {
            return resultado;
        }
    }
    
}
