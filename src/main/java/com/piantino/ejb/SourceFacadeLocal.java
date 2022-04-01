/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Source;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface SourceFacadeLocal {

    void create(Source source);

    void edit(Source source);

    void remove(Source source);

    Source find(Object id);

    List<Source> findAll();

    List<Source> findRange(int[] range);

    int count();
    
}
