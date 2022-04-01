/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Auto;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gaby
 */
@Local
public interface AutoFacadeLocal {

    void create(Auto auto);

    void edit(Auto auto);

    void remove(Auto auto);

    Auto find(Object id);

    List<Auto> findAll();

    List<Auto> findRange(int[] range);

    int count();
    
    Auto buscarPorNroMotor(String nroMotor)throws Exception;
    
    Auto buscarPorId(int id);
    
    Auto buscarPorNroChasis(String nroChasis)throws Exception;
    
    Auto buscarPorVin(String VIN);
    
    Auto buscarPorStock(long stock);
    
    Auto buscarPorRego(String rego) ;
    
    List<Auto> buscarPorRegoSimilar(String rego);
    
    List<Auto> buscarPorVINTerminando(String rego);
    
    List<Auto> buscarPorVINEmpezando(String rego);
    
    Auto buscarPorMarca(int idMarca) throws Exception;
    
    Auto buscarPorModelo(int idModelo) throws Exception;
    
     Auto buscarPorSucursal(int idSucursal) throws Exception; 
     
     Auto buscarPorTipoAuto(int idTipoAuto) throws Exception; 
     
     Auto buscarPorCategoriaAuto(int idCategoria) throws Exception; 
    
    List<Auto> buscarAutosDisponibles() throws Exception;
    
    List<Auto> listaRegoVencidos() ;
    
    List<Auto> listaRegoVencidoConContratosActivos();
    
    List<Auto> buscarAutosPorFiltro(String rego, String vin, int idSucursal, int idMarca, int idModelo, int disponible, long stock);
    
    List<Auto> buscarPrimerosAutos();
    
    List<Integer> buscarIDAutosRegoVencerPeriodo(Date fecha_desde, Date fecha_hasta);
    
    List<Auto> getListaAutoDisponiblesWeb();
    
    List<Auto> getListaAutoConImagen();
    
    List<Auto> getAutosVendidosNoMarcados();
    
    boolean estaAutoAlquilado(Auto auto);
    
    boolean estaAutoDisponibleWeb(Auto auto);
    
    long getStockSugerido();
    
    List<Auto> buscarPorSucursalDealer(Integer idSucursal, Integer idDealer);
    
    List<Auto> buscarAutoTransferencia( int idSucursal, String palabra, boolean transferido);
    
    List<Auto> buscarAutoDisposal( int idSucursal, String palabra, boolean transferido);
    
    List<Auto> buscarAutosDeberiaTransferir();
    
    
}
