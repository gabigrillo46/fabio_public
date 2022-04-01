/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Mensaje_Interno_Destino;
import java.text.SimpleDateFormat;
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
public class Mensaje_Interno_DestinoFacade extends AbstractFacade<Mensaje_Interno_Destino> implements Mensaje_Interno_DestinoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Mensaje_Interno_DestinoFacade() {
        super(Mensaje_Interno_Destino.class);
    }

    @Override
    public List<Mensaje_Interno_Destino> listaDestinosDeMensaje(long idMensaje) {
                List<Mensaje_Interno_Destino> listaDestinoResultado = new ArrayList<Mensaje_Interno_Destino>();
        try {
            String consulta = "FROM Mensaje_Interno_Destino mid WHERE  mid.mensajeInterno.id="+idMensaje;
            Query a = em.createQuery(consulta);
            listaDestinoResultado = a.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaDestinoResultado;
    }

    @Override
    public int cantidadMensajesNoLeidosUsuario(int i) {
                List<Long> listaMensajeInternos = new ArrayList();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String consulta = "select mi.id " +
" from mensaje_interno mi, mensaje_interno_destino mide " +
" where mi.id=mide.id_mensaje_interno" +
" and mide.id_usuario_destino=" +i+
" and mide.leido=0";
            Query q = em.createNativeQuery(consulta);
            listaMensajeInternos = q.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMensajeInternos.size();
    }
    
}
