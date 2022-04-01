/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.controller.AccesoBD;
import com.piantino.model.PagoImportado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author gaby
 */
public class PagoImportadoSet implements InterfazSet{
    
        private String stmtTABLA=" pago_importado ";
    
    private String stmtCAMPOS= " monto , referencia , customer_name , respuesta_codigo , respuesta_texto , recibo_numero , fecha , status , estado_privado ,  ID ";

    private String stmtSelect="SELECT "+stmtCAMPOS+" FROM "+stmtTABLA+" ";
    
            
    private final String UPDATE = "UPDATE "+stmtTABLA+" SET monto=?, referencia=?, customer_name=?, respuesta_codigo=?, respuesta_texto=?, recibo_numero=?, fecha=?, status=? , estado_privado=?  WHERE ID=?";
    
    private final String INSERTAR="INSERT INTO "+ stmtTABLA+" ( monto , referencia , customer_name , respuesta_codigo , respuesta_texto , recibo_numero , fecha , status , estado_privado ) VALUES (?,?,?,?,?,?,?,?,?)";

    
    private String stmtSelectPorNroComprobante = stmtSelect+" WHERE recibo_numero = ";
    
    AccesoBD accesoBD;
    
    public PagoImportadoSet()
    {
        accesoBD = new AccesoBD();
    }
    
        public boolean insertar(PagoImportado pagoImp)
    {
    	   boolean resultado=false;
           
           resultado= accesoBD.insertar(this.INSERTAR, this, pagoImp);
   
           return resultado;
    }
        
          public boolean actualizar(PagoImportado usu)
    {
    	return accesoBD.modificar(this.UPDATE, this, usu);
    }
    
        
    public PagoImportado getPagoImportadoPorNumeroCompro(String numeroCompro)
    {
    	String consulta = stmtSelectPorNroComprobante+numeroCompro;
    	return (PagoImportado)accesoBD.getObjecto(consulta, this);
    }

    @Override
    public void prepararElementoEliminacion(PreparedStatement arg0, Object arg1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepararElementoModificacion(PreparedStatement prep, Object obj) throws SQLException {
        this.prepararElementoComun(prep, obj);
    }

    
    public void prepararElementoInsercion(PreparedStatement prep, Object obj) throws SQLException {
                PagoImportado pago=(PagoImportado)obj;
        prep.setString(1, pago.getMonto());
        prep.setString(2, pago.getReferencia());
        prep.setString(3, pago.getCustomerName());
        prep.setString(4, pago.getRespuestaCodigo());
        prep.setString(5, pago.getRespuestaTexto());
        prep.setString(6, pago.getReciboNumero());
        prep.setString(7, pago.getFecha());
        prep.setString(8, pago.getStatus());
        prep.setInt(9, pago.getEstadoPrivado());
    }
    
        private void prepararElementoComun(PreparedStatement prep, Object obj) throws SQLException
    {
        PagoImportado pago=(PagoImportado)obj;
        prep.setString(1, pago.getMonto());
        prep.setString(2, pago.getReferencia());
        prep.setString(3, pago.getCustomerName());
        prep.setString(4, pago.getRespuestaCodigo());
        prep.setString(5, pago.getRespuestaTexto());
        prep.setString(6, pago.getReciboNumero());
        prep.setString(7, pago.getFecha());
        prep.setString(8, pago.getStatus());
        prep.setInt(9, pago.getEstadoPrivado());
        prep.setLong(10, pago.getId());
    }

     public void rellenarElemento(ResultSet result, Object obj) 
            throws SQLException
    {
        PagoImportado pago=(PagoImportado)obj;
pago.setMonto(result.getString(1));
pago.setReferencia(result.getString(2));
pago.setCustomerName(result.getString(3));
pago.setRespuestaCodigo(result.getString(4));
pago.setRespuestaTexto(result.getString(5));
pago.setReciboNumero(result.getString(6));
pago.setFecha(result.getString(7));
pago.setStatus(result.getString(8));
pago.setEstadoPrivado(result.getInt(9));
pago.setId(Long.parseLong(result.getString(10)));
    }

    @Override
    public Object getInstance() {
        return new PagoImportado();
    }
    
        public Vector getListaPagoNOIngresados()
    {
        String consulta="";
        Vector resultados=new Vector();
        consulta=this.stmtSelect +" WHERE ESTADO_PRIVADO = "+PagoImportado.ESTADO_NO_INGRESADO;


        resultados=accesoBD.getListaObjetos(consulta, this);
        return resultados;
    }
    
}
