/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Dealer_licence;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Dealer_licenceFacadeLocal {

    void create(Dealer_licence dealer_licence);

    void edit(Dealer_licence dealer_licence);

    void remove(Dealer_licence dealer_licence);

    Dealer_licence find(Object id);

    List<Dealer_licence> findAll();

    List<Dealer_licence> findRange(int[] range);

    int count();
    
    Dealer_licence getLicenceByNumber(String number);
    
    List<Dealer_licence> getListaLicenceByNumber(String number);
    
}
