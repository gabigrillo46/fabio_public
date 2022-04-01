/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Contact_us;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Contact_usFacadeLocal {

    void create(Contact_us contact_us);

    void edit(Contact_us contact_us);

    void remove(Contact_us contact_us);

    Contact_us find(Object id);

    List<Contact_us> findAll();

    List<Contact_us> findRange(int[] range);

    int count();
    
}
