/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Mensaje_Interno_Destino;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Mensaje_Interno_DestinoFacadeLocal {

    void create(Mensaje_Interno_Destino mensaje_Interno_Destino);

    void edit(Mensaje_Interno_Destino mensaje_Interno_Destino);

    void remove(Mensaje_Interno_Destino mensaje_Interno_Destino);

    Mensaje_Interno_Destino find(Object id);

    List<Mensaje_Interno_Destino> findAll();

    List<Mensaje_Interno_Destino> findRange(int[] range);
    
    List<Mensaje_Interno_Destino> listaDestinosDeMensaje(long idMensaje);
     
    int cantidadMensajesNoLeidosUsuario(int idUsuario);
    

    int count();
    
}
