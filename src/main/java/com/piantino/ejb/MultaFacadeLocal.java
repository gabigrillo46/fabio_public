/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Multa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface MultaFacadeLocal {

    void create(Multa multa);

    void edit(Multa multa);

    void remove(Multa multa);

    Multa find(Object id);

    List<Multa> findAll();

    List<Multa> findRange(int[] range);
    
    List<Multa> buscarPorFiltro(String rego, String numero, String lastName, String referencia, String impreso);

    int count();
    
    
    Multa buscarPorNumero(String numero);
    
    Multa buscarPorId(int id);
    
    List<Multa> getListMultasPorIdAlquiler(int idAlquiler);
    
}
