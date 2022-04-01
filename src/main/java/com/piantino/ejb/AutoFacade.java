/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler;
import com.piantino.model.Auto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gaby
 */
@Stateless
public class AutoFacade extends AbstractFacade<Auto> implements AutoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AutoFacade() {
        super(Auto.class);
    }

    @Override
    public Auto buscarPorNroMotor(String nroMotor) throws Exception {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.nro_motor = ?1 and a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, nroMotor);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public List<Auto> findAll() {
        List<Auto> modelosActivos;
        try {
            String consulta = "FROM Auto a WHERE a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            modelosActivos = q.getResultList();
        } catch (Exception e) {
            modelosActivos = new ArrayList<Auto>();
        }
        return modelosActivos;
    }

    @Override
    public Auto buscarPorNroChasis(String nroChasis) throws Exception {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.nro_chasis = ?1 and a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, nroChasis);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public Auto buscarPorVin(String VIN) {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.VIN = ?1 and a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, VIN);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return autoEncontrado;
    }

    @Override
    public List<Auto> buscarPorRegoSimilar(String rego) {
        List<Auto> listaAuto = new ArrayList<Auto>();
        try {
            String consulta = "FROM Auto a where a.rego like '%" + rego + "%' and a.estado != " + Auto.ESTADO_BAJA + " and a.estado != " + Auto.ESTADO_VENDIDO;
            Query q = em.createQuery(consulta);
            listaAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAuto;
    }

    @Override
    public Auto buscarPorRego(String rego) {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.rego = ?1 and a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, rego);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return autoEncontrado;
    }

    @Override
    public List<Auto> buscarAutosDisponibles() throws Exception {
        List<Auto> listaAutosDisponibles;
        try {
            String consulta = "FROM Auto a WHERE a.disponible = " + Auto.ESTADO_DISPONIBLE;
            Query q = em.createQuery(consulta);
            listaAutosDisponibles = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaAutosDisponibles;
    }

    @Override
    public Auto buscarPorMarca(int idMarca) throws Exception {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.marca.id =  ?1 AND a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, idMarca);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public Auto buscarPorSucursal(int idSucursal) throws Exception {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.sucursal.id =  ?1 AND a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, idSucursal);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public Auto buscarPorModelo(int idModelo) throws Exception {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.modelo.id =  ?1 AND a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, idModelo);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public Auto buscarPorTipoAuto(int idTipoAuto) throws Exception {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.tipo_body.id =  ?1 AND a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, idTipoAuto);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public Auto buscarPorCategoriaAuto(int idCategoria) throws Exception {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.categoria.id =  ?1 AND a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, idCategoria);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public List<Auto> listaRegoVencidos() {
        List<Auto> listaAutosDisponibles = null;
        try {
            String consulta = "FROM Auto a WHERE a.estado != " + Auto.ESTADO_BAJA + " order by  a.fecha_vencimiento_rego asc ";
            Query q = em.createQuery(consulta);
            q.setMaxResults(5);
            listaAutosDisponibles = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosDisponibles;
    }

    @Override
    public Auto buscarPorId(int idAuto) {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.id =  ?1 ";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAuto);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public List<Auto> buscarAutosPorFiltro(String rego, String vin, int idSucursal, int idMarca, int idModelo, int disponible, long stock) {
        List<Auto> listaAutoResultado = new ArrayList();
        try {
            String consulta = "FROM Auto a WHERE  1=1 ";
            if (rego != null) {
                consulta = consulta + " AND a.rego like '%" + rego + "%' ";
            }
            if (vin != null) {
                consulta = consulta + " AND a.VIN like '%" + vin + "%' ";
            }
            if (idSucursal > -1) {
                consulta = consulta + " AND a.sucursal.id =" + idSucursal;
            }
            if (idModelo > 0) {
                consulta = consulta + " AND a.modelo.id = " + idModelo;
            }
            if (idMarca > 0) {
                consulta = consulta + " AND a.marca.id = " + idMarca;
            }
            if (disponible > -1) {
                if (disponible == 1) {
                    consulta = consulta + " AND a.disponible =" + true;
                } else if(disponible ==0){
                    consulta = consulta + " AND a.disponible =" + false;
                } else if(disponible == 2)
                {
                    consulta = consulta +" AND a.estado = "+Auto.ESTADO_VENDIDO;
                }
            }
            if(stock >-1)
            {
                consulta = consulta +" AND a.stock = "+stock;
            }
            consulta = consulta + " AND a.estado != " + Auto.ESTADO_BAJA;
            consulta = consulta +" order by a.fecha_compra desc";
            Query a = em.createQuery(consulta);
            listaAutoResultado = a.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaAutoResultado;
    }

    @Override
    public Auto buscarPorStock(long stock) {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a WHERE a.stock =  ?1 and a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            q.setParameter(1, stock);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return autoEncontrado;
    }

    @Override
    public List<Auto> listaRegoVencidoConContratosActivos() {
        List<Auto> listaAutosDisponibles = null;
        try {
            String consulta = "FROM Auto a, Alquiler al WHERE al.id_auto = a.id and  a.estado != " + Auto.ESTADO_BAJA + " and (al.estado = " + Alquiler.ESTADO_ALQUILADO + " or al.estado = " + Alquiler.ESTADO_RESERVA + ") order by  a.fecha_vencimiento_rego asc ";
            Query q = em.createQuery(consulta);
            q.setMaxResults(5);
            listaAutosDisponibles = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosDisponibles;
    }

    @Override
    public List<Integer> buscarIDAutosRegoVencerPeriodo(Date fecha_desde, Date fecha_hasta) {
        List<Integer> listaIdAuto = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDesdeSTR = sdf.format(fecha_desde);
            String fechaHastaSTR = sdf.format(fecha_hasta);
            String consulta = "select distinct(au.id)"
                    + " from auto au, alquiler al"
                    + " where au.id=al.id_auto"
                    + " and al.estado = " + Alquiler.ESTADO_ALQUILADO
                    + " and au.fecha_vencimiento_rego <='" + fechaHastaSTR + "'"
                    + " and au.fecha_vencimiento_rego >='" + fechaDesdeSTR + "'"
                    + " order by au.fecha_vencimiento_rego asc";
            Query q = em.createNativeQuery(consulta);

            listaIdAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaIdAuto;
    }

    @Override
    public List<Auto> getListaAutoDisponiblesWeb() {
        List<Auto> listaAutosDisponibles = new ArrayList<>();

        try {
            List<Integer> listaIdAuto = new ArrayList<>();
            String consulta = "select distinct(id) from auto au where au.id not in (select a.id_auto from alquiler a where (a.estado =" + Alquiler.ESTADO_ALQUILADO + " or a.estado =" + Alquiler.ESTADO_CARGANDO + " or a.estado =" + Alquiler.ESTADO_RESERVA + " or a.estado =" + Alquiler.ESTADO_VENDIDO + ") and a.id_auto != null) and au.disponible =true";
            Query q = em.createNativeQuery(consulta);
            listaIdAuto = q.getResultList();
            for (int a = 0; a < listaIdAuto.size(); a++) {
                Auto autoCargar = this.find(listaIdAuto.get(a));
                listaAutosDisponibles.add(autoCargar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosDisponibles;
    }

    @Override
    public List<Auto> getAutosVendidosNoMarcados() {
        List<Auto> listaAutosVendidos = new ArrayList();
        try {
            List<Integer> listaIdAuto = new ArrayList<>();
            String consulta = "select distinct(au.id) from auto au where au.id in (select a.id_auto from alquiler a where a.estado = " + Alquiler.ESTADO_ALQUILADO + " or a.estado =" + Alquiler.ESTADO_CARGANDO + " or a.estado =" + Alquiler.ESTADO_RESERVA + " or a.estado =" + Alquiler.ESTADO_VENDIDO + ") and au.marcado_vendido = false";
            Query q = em.createNativeQuery(consulta);
            listaIdAuto = q.getResultList();

            for (int a = 0; a < listaIdAuto.size(); a++) {
                Auto autoCargar = this.find(listaIdAuto.get(a));
                listaAutosVendidos.add(autoCargar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosVendidos;
    }

    @Override
    public boolean estaAutoAlquilado(Auto auto) {
        List<Auto> listaAutosVendidos = new ArrayList();
        try {
            List<Integer> listaIdAlquiler = new ArrayList<>();
            String consulta = "select distinct(a.id) from alquiler a where a.id_auto = " + auto.getId() + " and (a.estado = " + Alquiler.ESTADO_ALQUILADO + " or a.estado = " + Alquiler.ESTADO_CARGANDO + " or a.estado = " + Alquiler.ESTADO_RESERVA + " or a.estado = " + Alquiler.ESTADO_VENDIDO + ")";
            Query q = em.createNativeQuery(consulta);
            listaIdAlquiler = q.getResultList();
            if (listaIdAlquiler.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean estaAutoDisponibleWeb(Auto auto) {
        try {
            List<Integer> listaIdAuto = new ArrayList<>();
            String consulta = "select distinct(id) from auto au where au.id not in (select a.id_auto from alquiler a where a.estado =" + Alquiler.ESTADO_ALQUILADO + " or a.estado =" + Alquiler.ESTADO_CARGANDO + " or a.estado =" + Alquiler.ESTADO_RESERVA + " or a.estado =" + Alquiler.ESTADO_VENDIDO + ") and au.disponible =true and au.id = " + auto.getId();
            Query q = em.createNativeQuery(consulta);
            listaIdAuto = q.getResultList();
            if (listaIdAuto.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Auto> buscarPorVINTerminando(String vin) {
        List<Auto> listaAuto = new ArrayList<Auto>();
        try {
            String consulta = "FROM Auto a where a.VIN like '%" + vin + "' and a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            listaAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAuto;
    }

    @Override
    public List<Auto> buscarPorVINEmpezando(String vin) {
        List<Auto> listaAuto = new ArrayList<Auto>();
        try {
            String consulta = "FROM Auto a where a.VIN like '" + vin + "%' and a.estado != " + Auto.ESTADO_BAJA;
            Query q = em.createQuery(consulta);
            listaAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAuto;
    }

    @Override
    public long getStockSugerido() {
        Auto autoEncontrado = null;
        try {
            String consulta = "FROM Auto a where a.stock in (select max(au.stock) from Auto au)";
            Query q = em.createQuery(consulta);
            List<Auto> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                autoEncontrado = listaResultado.get(0);
                if (autoEncontrado != null) {
                    long stockSugerido = autoEncontrado.getStock() + 1;
                    return stockSugerido;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Auto> buscarPorSucursalDealer(Integer idSucursal, Integer idDealer) {
        List<Auto> listaAutosResultado = new ArrayList();
        try {
            List<Integer> listaIdAuto = new ArrayList<>();
            String consulta = "SELECT distinct(id) FROM auto a where 1=1 ";
            if (idDealer != null) {
                consulta = consulta + " and a.id_sucursal in (select s.id from sucursal s where s.id_dealer_licence=" + idDealer + ")";
            }
            if (idSucursal != null) {
                consulta = consulta + "and a.id_sucursal = " + idSucursal;
            }
            consulta = consulta + " order by a.stock asc ";
            Query q = em.createNativeQuery(consulta);
            listaIdAuto = q.getResultList();
            for (int a = 0; a < listaIdAuto.size(); a++) {
                Integer idAuto = listaIdAuto.get(a);
                Auto autoResultado = this.buscarPorId(idAuto);
                listaAutosResultado.add(autoResultado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosResultado;
    }

    @Override
    public List<Auto> buscarAutoTransferencia(int idSucursal, String palabra, boolean transferido) {
        List<Auto> listaAutosResultado = new ArrayList();
        try {
            String consulta = "FROM Auto a where 1=1 ";
            if (palabra != null && palabra.trim().length() > 0) {
                consulta = consulta + " and (a.rego like '%" + palabra + "%'  ";
                try {
                    Long.parseLong(palabra);
                    consulta = consulta + "or a.stock = " + palabra;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                consulta = consulta + " )";
            }
            if (idSucursal > 0) {
                consulta = consulta + " and a.sucursal.id = " + idSucursal;
            }
            consulta = consulta + " and a.transferenciaImpresa = " + transferido;
            consulta = consulta + " order by a.fecha_compra asc";
            Query q = em.createQuery(consulta);
            listaAutosResultado = q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosResultado;
    }

    @Override
    public List<Auto> buscarAutoDisposal(int idSucursal, String palabra, boolean transferido) {
        List<Auto> listaAutosResultado = new ArrayList();
        try {
            String consulta = "select distinct(id)  from auto a where 1=1  ";

            if (palabra != null && palabra.trim().length() > 0) {
                consulta = consulta + " and (a.rego like '%" + palabra + "%'  ";
                try {
                    Long.parseLong(palabra);
                    consulta = consulta + "or a.stock = " + palabra;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                consulta = consulta + " )";
            }
            if (idSucursal > 0) {
                consulta = consulta + " and a.id_sucursal = " + idSucursal;
            }
            consulta = consulta + " and a.id in (select v.id_auto from venta v)";
            consulta = consulta + " and a.disposal_impresa = " + transferido;
            consulta = consulta + " order by a.fecha_compra desc";
            Query a = em.createNativeQuery(consulta);
            List<Integer> listaAutosDisposal = a.getResultList();
            for (int b = 0; b < listaAutosDisposal.size(); b++) {
                int idAuto = listaAutosDisposal.get(b);
                Auto auto = this.buscarPorId(idAuto);
                if (auto != null) {
                    listaAutosResultado.add(auto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosResultado;
    }

    @Override
    public List<Auto> buscarAutosDeberiaTransferir() {
        List<Auto> listaAutosResultado = new ArrayList();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
            c.add(Calendar.DAY_OF_YEAR, 10);
            Date fechaLimiteRego = c.getTime();
            String fechaLimiteRegoStr = sdf.format(fechaLimiteRego);
            System.out.print(fechaLimiteRegoStr);
            c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
            c.add(Calendar.DAY_OF_YEAR, -7);
            Date fechaLimiteCompra = c.getTime();
            String fechaLimiteCompraStr = sdf.format(fechaLimiteCompra);
            System.out.println(fechaLimiteCompraStr);
            
            String consulta = "FROM Auto a where a.transferenciaImpresa = false and a.sucursal.id >0 and a.fecha_vencimiento_rego >'"+fechaLimiteRegoStr+"' and a.fecha_compra !=null and a.fecha_compra <'"+fechaLimiteCompraStr+"'";
            consulta = consulta + " order by a.fecha_compra asc";
            Query q = em.createQuery(consulta);
            listaAutosResultado = q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosResultado;
    }

    @Override
    public List<Auto> getListaAutoConImagen() {
        List<Auto> listaAutosConImagen = new ArrayList<>();
        try {
            List<Integer> listaIdAuto = new ArrayList<>();
            String consulta = "select distinct(id) from auto a where a.id in (select ai.id_auto from auto_imagenes ai)";
            Query q = em.createNativeQuery(consulta);
            listaIdAuto = q.getResultList();
            for(int a=0;a<listaIdAuto.size();a++)
            {
                Auto auto = this.buscarPorId(listaIdAuto.get(a));
                if(auto!=null)
                {
                    listaAutosConImagen.add(auto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosConImagen;
    }

    @Override
    public List<Auto> buscarPrimerosAutos() {
        List<Auto> listaAutosResultados = new ArrayList<>();
        try {
            List<Integer> listaIdAuto = new ArrayList<>();
            String consulta = "select a.id FROM Auto a where a.estado != "+Auto.ESTADO_BAJA+" order by a.fecha_compra desc";
            Query q = em.createNativeQuery(consulta);
            q.setMaxResults(200);
            listaIdAuto = q.getResultList();
            for(int a=0;a<listaIdAuto.size();a++)
            {
                Auto auto = this.buscarPorId(listaIdAuto.get(a));
                if(auto!=null)
                {
                    listaAutosResultados.add(auto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAutosResultados;
    }

}
