/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Template;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface TemplateFacadeLocal {

    void create(Template template);

    void edit(Template template);

    void remove(Template template);

    Template find(Object id);

    List<Template> findAll();

    List<Template> findRange(int[] range);

    int count();
    
    List<Template> buscarPorFiltro(String nombre); 
    
    List<Template> buscarTemplateActivo(); 
    
}
