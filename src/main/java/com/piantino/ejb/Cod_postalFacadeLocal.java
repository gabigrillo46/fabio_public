/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Cod_postal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Cod_postalFacadeLocal {

    void create(Cod_postal cod_postal);

    void edit(Cod_postal cod_postal);

    void remove(Cod_postal cod_postal);

    Cod_postal find(Object id);

    List<Cod_postal> findAll();
    
    List<Cod_postal> getListaCodigoPostalPorCOdigo(String codigo);

    List<Cod_postal> findRange(int[] range);

    int count();
    
}
