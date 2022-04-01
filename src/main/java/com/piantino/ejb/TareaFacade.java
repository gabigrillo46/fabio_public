/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Tarea;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class TareaFacade extends AbstractFacade<Tarea> implements TareaFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareaFacade() {
        super(Tarea.class);
    }
    
    public Tarea buscarTareaPorId(int id)
    {
                        Tarea tareaResult =null;
        try
        {
            String consulta = "FROM Tarea a WHERE a.id = ?1 ";
            Query q = em.createQuery(consulta);
            q.setParameter(1, id);
            List<Tarea> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                tareaResult = listaResultado.get(listaResultado.size() - 1);
            }
        }
        catch(Exception e)
        {
            
        }
        return tareaResult;

    }
    
    public List<Tarea> buscarPorFiltro(String realizado, String caducado, Date fecha, String id_usuario)
    {
                List<Tarea> listaTareasResultado = new ArrayList();
                boolean realizadob=false;
                boolean caducadob=false;
        try {
            String consulta = "FROM Tarea a WHERE  1=1 ";
            if (fecha != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaStr = sdf.format(fecha);
                consulta = consulta + " AND a.fecha_vencimiento = '" + fechaStr + "'";
            }
            if (realizado != null) {
                if(realizado.equalsIgnoreCase("1"))
                {
                    realizadob=true;
                }
                consulta = consulta + " AND a.realizado =" + realizadob;
            }
            if (caducado != null) {
                if(caducado.equalsIgnoreCase("1"))
                {
                    caducadob=true;
                }
                consulta = consulta + " AND a.caducado ="+caducadob;
            }
            if(id_usuario !=null)
            {
                
                consulta = consulta +" AND a.usuarioAsignado.id ="+id_usuario;
            }
            Query a = em.createQuery(consulta);
            listaTareasResultado = a.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaTareasResultado;
    }
    
    public List<Tarea> buscarPendientesOrdenadas()
    {
                        List<Tarea> listaTareasResultado = new ArrayList();
                boolean realizadob=false;
                boolean caducadob=false;
        try {
            String consulta = "FROM Tarea a WHERE  a.realizado =0 order by a.fecha_vencimiento asc";
            Query a = em.createQuery(consulta);
            a.setMaxResults(10);
            listaTareasResultado = a.getResultList();
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaTareasResultado;
    }
    
}
