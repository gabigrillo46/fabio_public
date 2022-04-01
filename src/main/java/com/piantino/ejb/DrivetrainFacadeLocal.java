/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Drivetrain;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gabriel
 */
@Local
public interface DrivetrainFacadeLocal {

    void create(Drivetrain drivetrain);

    void edit(Drivetrain drivetrain);

    void remove(Drivetrain drivetrain);

    Drivetrain find(Object id);

    List<Drivetrain> findAll();

    List<Drivetrain> findRange(int[] range);

    int count();
    
    List<Drivetrain> getTodasActivas();
    
}
