/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Sucursal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface SucursalFacadeLocal {

    void create(Sucursal sucursal);

    void edit(Sucursal sucursal);

    void remove(Sucursal sucursal);

    Sucursal find(Object id);

    List<Sucursal> findAll();
    
    List<Sucursal> buscarTodes();

    List<Sucursal> findRange(int[] range);

    int count();
    
    Sucursal buscarPorNombre(String nombre)throws Exception;
    
    List<Sucursal> buscarSucursalesPorNombreSimilar(String nombre);
    
    List<Sucursal> buscarSucursalesPorLicencia(int idLicence);
    
}
