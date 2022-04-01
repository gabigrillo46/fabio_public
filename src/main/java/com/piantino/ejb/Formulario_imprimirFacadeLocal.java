/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Formulario_imprimir;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface Formulario_imprimirFacadeLocal {

    void create(Formulario_imprimir formulario_imprimir);

    void edit(Formulario_imprimir formulario_imprimir);

    void remove(Formulario_imprimir formulario_imprimir);

    Formulario_imprimir find(Object id);

    List<Formulario_imprimir> findAll();

    List<Formulario_imprimir> findRange(int[] range);

    int count();
    
}
