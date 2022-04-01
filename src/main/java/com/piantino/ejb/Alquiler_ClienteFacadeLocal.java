/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler;
import com.piantino.model.Alquiler_Cliente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface Alquiler_ClienteFacadeLocal {

    void create(Alquiler_Cliente alquiler_Cliente);

    void edit(Alquiler_Cliente alquiler_Cliente);

    void remove(Alquiler_Cliente alquiler_Cliente);

    Alquiler_Cliente find(Object id);

    List<Alquiler_Cliente> findAll();

    List<Alquiler_Cliente> findRange(int[] range);

    int count();
    
    List<Alquiler_Cliente> buscarPorAlquiler(int idAlquiler);
    
    List<Alquiler_Cliente> buscarAlquileresDeCliente(int idCliente);
    
    Alquiler_Cliente buscarClientePrincipalPorAlquiler(int idAlquiler);
    
    void agregarClientePrincipalAlAlq(Alquiler_Cliente alqCli) ;
    
    void agregarClienteAdicionalAlAlq(Alquiler_Cliente alqCli);
    
    void eliminarClientesAdicionales(int idAlquiler);
    
    void eliminarClientePrincipalDelAlq(int idAlquiler);
    
}
