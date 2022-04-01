/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Parametro;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface ParametroFacadeLocal {

    void create(Parametro parametro);

    void edit(Parametro parametro);

    void remove(Parametro parametro);

    Parametro find(Object id);

    List<Parametro> findAll();

    List<Parametro> findRange(int[] range);

    int count();
    
    Parametro buscarPorGrupo(String grupo, String calif_grupo, String param, String calif_param);
    
    float getValorFloat(String grupo, String calif_grupo, String param, String calif_param);
    
    
    
}
