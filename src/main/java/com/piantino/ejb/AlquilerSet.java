/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.controller.AccesoBD;
import com.piantino.model.Alquiler;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gaby
 */
public class AlquilerSet  implements InterfazSet{
    
            private String stmtTABLA=" alquiler ";
    
    

    private String stmtSelect="SELECT id , gran_total, deuda, rate_per_day FROM "+stmtTABLA+" ";
    
    private String stmtSelectPorId=this.stmtSelect+" where id = ";
    
   
    
    AccesoBD accesoBD;
    
    public AlquilerSet()
    {
        accesoBD = new AccesoBD();
    }
    
    public Alquiler getAlquierPorId(int id)
    {
        Alquiler alq=null;
        String consulta =this.stmtSelectPorId+id;
        ResultSet resultado=accesoBD.getResult(consulta);
        if(resultado != null)
        {
            try
            {
               while(resultado.next())
	        {
                    alq = new Alquiler();
                    alq.setId(resultado.getInt(1));
                    alq.setGran_total(resultado.getFloat(2));
                    alq.setDeuda(resultado.getFloat(3));
                    alq.setRate_per_day(resultado.getFloat(4));
                    return alq;
	        }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return alq;
    }
    
    public boolean actualizarDeudaAlquiler(Alquiler alq)
    {
        String consulta ="update "+this.stmtTABLA+" set deuda ="+alq.getDeuda()+" where id="+alq.getId();
        
        return accesoBD.modificar(consulta);
    }


    @Override
    public void prepararElementoEliminacion(PreparedStatement arg0, Object arg1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepararElementoModificacion(PreparedStatement arg0, Object arg1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepararElementoInsercion(PreparedStatement arg0, Object arg1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rellenarElemento(ResultSet arg0, Object arg1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getInstance() {
        return new Alquiler();
    }
    
}
