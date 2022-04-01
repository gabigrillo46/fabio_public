/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Cliente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface ClienteFacadeLocal {

    void create(Cliente cliente);

    void edit(Cliente cliente);

    void remove(Cliente cliente);

    Cliente find(Object id);

    List<Cliente> findAll();

    List<Cliente> findRange(int[] range);
    
    List<Cliente> getTodosLosBuenosClientes();
    
    List<Cliente> buscarPorApellidoSimilar(String nombre)throws Exception;

    int count();
    
    Cliente buscarPorNombreYApellido(String nombre, String apellido);
    
    Cliente buscarPorNombreYApellidoTodos(String nombre, String apellido);
    
    Cliente buscarPorID(int id);
    
    List<Cliente> buscarPorFiltro(String nombre, String telefono);
    
    List<Cliente> buscarProveedoresPorFiltro(String abn, String nombre);
    
    List<Cliente> buscarProveedoresPorNombreEmpezando(String nombre);
    
    Cliente buscarProveedorPorNombre(String nombre);
    
    Cliente buscarProveedorPorNumeroLicencia(String licencia);
    
}
