/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gaby
 */
/**
 *
 * @author gabriel
 */
public interface InterfazSet {
    
    public void prepararElementoEliminacion(PreparedStatement prep,Object obj)
            throws SQLException;;
    
    public void prepararElementoModificacion(PreparedStatement prep,Object obj)
            throws SQLException;;
    
    public void prepararElementoInsercion(PreparedStatement prep,Object obj)
            throws SQLException;;
    
    public void rellenarElemento(ResultSet result,Object obj)
              throws SQLException;
    
    public Object getInstance();
    
}