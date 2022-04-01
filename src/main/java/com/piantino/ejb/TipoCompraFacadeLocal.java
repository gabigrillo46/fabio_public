/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.TipoCompra;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface TipoCompraFacadeLocal {

    void create(TipoCompra tipoCompra);

    void edit(TipoCompra tipoCompra);

    void remove(TipoCompra tipoCompra);

    TipoCompra find(Object id);

    List<TipoCompra> findAll();

    List<TipoCompra> findRange(int[] range);

    int count();
    
}
