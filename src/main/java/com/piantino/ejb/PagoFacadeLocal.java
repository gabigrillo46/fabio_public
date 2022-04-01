/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Pago;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface PagoFacadeLocal {

    void create(Pago pago);

    void edit(Pago pago);

    void remove(Pago pago);

    Pago find(Object id);

    List<Pago> findAll();

    List<Pago> findRange(int[] range);

    int count();
    
    float getMontoRecibidoDeAlquiler(int idAlquiler);
    
    float getMontoTotalMesAnioActual();
    
    float getMontoTotalMesAnioAnterior();
    
    List<Pago> buscarPorAlquiler(int idAlquiler);
    
    List<Pago> buscarAprovadosPorAlquiler(int idAlquiler);
    
    List<Pago> buscarPorFiltro(String referencia, String numeroComprobante, Date fechaDesde, Date fechaHasta, String status, Integer automatico);
    
    List<Pago> buscarPorFiltro2(Date fechaDesde, Date fechaHasta, String status, Integer idSucursal, Integer idForma, String referencia);
    
    List<Pago> listaPagosPorTipoDeAlquiler(int idAlquiler, int tipoPagoToll);
    
    List<Pago> listaPagosAprovadosNoTollsNoLate(int idAlquiler, int tipoPagoToll, int tipoPagoLate);
    
    void eliminarPorAlquiler(int idAlquiler);
}
