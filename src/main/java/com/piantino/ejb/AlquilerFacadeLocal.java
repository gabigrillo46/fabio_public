/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface AlquilerFacadeLocal {

    void create(Alquiler alquiler);

    void edit(Alquiler alquiler);

    public float montoTotalMesActual();

    public int cantContratosMesActual();

    public int cantContratosMesAnioAnterior();

    public float montoTotalMesAnioAnterior();

    public float getMontoAcumuladoActual();

    public float getMontoAcumuladoAnterior();

    public int getCantContratosAcumuladosActual();

    public int getCantContratosAcumuladosAnterior();

    public int getCantContratosActivos();

    public float getMontoTotalAdeudado();

    void remove(Alquiler alquiler);

    Alquiler getAlquilerActivoPorId(int id);

    Alquiler getAlquilerPorId(int id);

    Alquiler find(Object id);

    List<Alquiler> findAll();

    List<Alquiler> findRange(int[] range);

    int count();

    List<Alquiler> getAlquileresDeudaActualAntesFecha(Date fecha);

    List<Alquiler> getAlquileresLoading();

    Alquiler getUltimaReservaDelUsuario(int idUsuario) throws Exception;

    List<Alquiler> getAlquileresSuperpuestos(Date fechaInicio, Date fechaFin, int idAuto, int id) throws Exception;

    List<Alquiler> getAlquilerPorFiltro(String rego, String lastname, Date fechaDesde, String referencia, String telefono, int idSucursal, Date fechaHasta, boolean deudaCurrentMayor0, int estado);

    Alquiler getAlquilerAutoFecha(int idAuto, Date fecha);

    Alquiler getAlquilerClienteConDeuda(int idCliente);

    Alquiler getAlquilerAlquiladoAuto(int idAuto);
    
    Alquiler getUltimoAlquilerAuto(int idAuto);

    List<Alquiler> getAlquileresActivosDelAuto(int idAuto);

    List<Alquiler> getListaRegoAVencer();

    List<Alquiler> getListaAlquilerInsurance();

    
    List<Alquiler> getListaTerminando(Date fechaLimite);

    Alquiler getReserevaAutoFecha(int idAuto, Date fecha);

    List<Alquiler> getAlquileresDentroPeriodoConEstado(Date fechaDesde, Date fechaHasta, int estado, int idSucursal, Date fechaDesdeDrop, Date fechaHastaDrop);

    List<Alquiler> getAlquileresDentroPeriodoSinEstado(Date fechaDesde, Date fechaHasta, int idSucursal);

    List<Alquiler> getAlquileresHired();
    
    List<Alquiler> alquileresAutoRegoVencerPeriodo(Date fecha_desde, Date fecha_hasta);
    
    List<Alquiler> getAlquileresTerminanPeriodo(Date fechaDesde, Date fechaHasta);

}
