/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.controller.AccesoBD;
import com.piantino.model.Pago;
import com.piantino.model.PagoImportado;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author gaby
 */
public class PagoSet implements InterfazSet {

    private String stmtTABLA = " pago ";

    private String stmtCAMPOS = " id_alquiler , id_sucursal , id_tipo , fecha_hora , monto , id_usuario , nota , nombre_cliente , status ,  recibo_numero , automatico,agrupacion, id ";

    private String stmtSelect = "SELECT " + stmtCAMPOS + " FROM " + stmtTABLA + " ";

    private final String INSERTAR = "INSERT INTO " + stmtTABLA + " ( id_alquiler , id_sucursal , id_tipo , fecha_hora , monto , id_usuario , nota , nombre_cliente , status ,  recibo_numero, automatico,agrupacion ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

    private String stmtSelectPorNroComprobante = stmtSelect + " WHERE recibo_numero = ";

    AccesoBD accesoBD;

    public PagoSet() {
        accesoBD = new AccesoBD();
    }

    public boolean insertar(Pago pago) {
        boolean resultado = false;

        resultado = accesoBD.insertar(this.INSERTAR, this, pago);

        return resultado;
    }

    public Pago getPagoImportadoPorNumeroCompro(String numeroCompro) {
        String consulta = stmtSelectPorNroComprobante + numeroCompro;
        return (Pago) accesoBD.getObjecto(consulta, this);
    }

    @Override
    public void prepararElementoEliminacion(PreparedStatement arg0, Object arg1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prepararElementoModificacion(PreparedStatement arg0, Object arg1) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void prepararElementoInsercion(PreparedStatement prep, Object obj) throws SQLException {
        Pago pago = (Pago) obj;
        prep.setInt(1, pago.getAlquiler().getId());
        prep.setInt(2, pago.getSucursal().getId());
        prep.setInt(3, pago.getTipoPago().getId());
        Date d = new Date(pago.getFecha_hora().getTime());
        prep.setDate(4, d);
        prep.setFloat(5, pago.getMonto());
        prep.setInt(6, pago.getUsuario().getId());
        prep.setString(7, pago.getNota());
        prep.setString(8, pago.getNombre_cliente());
        prep.setString(9, pago.getStatus());
        prep.setString(10, pago.getRecibo_numero());
        prep.setInt(11, pago.getAutomatico());
        prep.setString(12, pago.getAgrupacion());
    }

    private void prepararElementoComun(PreparedStatement prep, Object obj) throws SQLException {
        Pago pago = (Pago) obj;
        prep.setInt(1, pago.getAlquiler().getId());
        prep.setInt(2, pago.getSucursal().getId());
        prep.setInt(3, pago.getTipoPago().getId());
        Date d = new Date(pago.getFecha_hora().getTime());
        prep.setDate(4, d);
        prep.setFloat(5, pago.getMonto());
        prep.setInt(6, pago.getUsuario().getId());
        prep.setString(7, pago.getNota());
        prep.setString(8, pago.getNombre_cliente());
        prep.setString(9, pago.getStatus());
        prep.setString(10, pago.getRecibo_numero());
        prep.setInt(11, pago.getAutomatico());
        prep.setString(12, pago.getAgrupacion());
        prep.setLong(13, pago.getId());
    }

    public void rellenarElemento(ResultSet result, Object obj)
            throws SQLException {
        Pago pago = (Pago) obj;
        pago.getAlquiler().setId(result.getInt(1));
        pago.getSucursal().setId(result.getInt(2));
        pago.getTipoPago().setId(result.getInt(3));
        java.util.Date d = new java.util.Date(result.getDate(4).getTime());
        pago.setFecha_hora(d);
        pago.setMonto(result.getFloat(5));
        pago.getUsuario().setId(result.getInt(6));
        pago.setNota(result.getString(7));
        pago.setNombre_cliente(result.getString(8));
        pago.setStatus(result.getString(9));
        pago.setRecibo_numero(result.getString(10));
        pago.setAutomatico(result.getInt(11));
        pago.setAgrupacion(result.getString(12));
        pago.setId(result.getLong(13));
    }

    @Override
    public Object getInstance() {
        return new Pago();
    }

    public Vector getListaPagoNOIngresados() {
        String consulta = "";
        Vector resultados = new Vector();
        consulta = this.stmtSelect + " WHERE ESTADO_PRIVADO = " + PagoImportado.ESTADO_NO_INGRESADO;

        resultados = accesoBD.getListaObjetos(consulta, this);
        return resultados;
    }

    public Vector getListaAprobadoPorIDAlquiler(int idAlquiler) {
        String consulta = "";
        Vector resultados = new Vector();
        consulta = this.stmtSelect + " WHERE id_alquiler = " + idAlquiler + " and status = '" + Pago.Texto_Aprobado + "'";
        resultados = accesoBD.getListaObjetos(consulta, this);
        return resultados;
    }

    public Pago getPagoPorNumeroComp(String numeroComp) {
        Pago pagoExist = null;
        String consulta = this.stmtSelectPorNroComprobante + numeroComp;
        pagoExist = (Pago) accesoBD.getObjecto(consulta, this);
        return pagoExist;
    }

}
