/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Alquiler;
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
public class AlquilerFacade extends AbstractFacade<Alquiler> implements AlquilerFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlquilerFacade() {
        super(Alquiler.class);
    }

    public Alquiler getUltimaReservaDelUsuario(int idUsuario) throws Exception {
        Alquiler alquilerResult = null;

        try {
            String consulta = "FROM Alquiler a WHERE a.usuario.id = ?1 AND a.estado = " + Alquiler.ESTADO_RESERVA;

            Query q = em.createQuery(consulta);
            q.setParameter(1, idUsuario);
            List<Alquiler> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                alquilerResult = listaResultado.get(listaResultado.size() - 1);
            }
        } catch (Exception e) {
            throw e;
        }
        return alquilerResult;
    }

    public Alquiler getReserevaAutoFecha(int idAuto, Date fecha) {
        Alquiler alquilerResult = null;
        try {
            String consulta = "FROM Alquiler a WHERE a.auto.id = ?1 AND a.fecha_inicio <= ?2 AND a.fecha_fin >=?2 AND (a.estado = " + Alquiler.ESTADO_RESERVA + " )";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAuto);
            q.setParameter(2, fecha);
            List<Alquiler> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                alquilerResult = listaResultado.get(listaResultado.size() - 1);
            }
        } catch (Exception e) {

        }
        return alquilerResult;
    }

    public float montoTotalMesActual() {
        Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH) + 1;
        String mesStr = "";
        if (mes < 10) {
            mesStr = "0";
        }
        mesStr = mesStr + mes;
        int dia = c.get(Calendar.DAY_OF_MONTH);
        String diaStr = "";
        if (dia < 10) {
            diaStr = "0";
        }
        diaStr = diaStr + dia;
        String primerDiaStr = "01";
        float monto = 0;
        List<Float> listaAlquiler = null;
        try {
            String consulta = "select a.gran_total from Alquiler a where a.fecha_inicio >= '" + anio + "-" + mesStr + "-" + primerDiaStr + "' and a.fecha_inicio <= '" + anio + "-" + mesStr + "-" + diaStr + "'";
            consulta = consulta + " and a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            for (int a = 0; a < listaAlquiler.size(); a++) {
                monto = monto + listaAlquiler.get(a);
            }
        }
        int montoInt = (int) (monto * 100);
        monto = ((float) montoInt) / 100;

        return monto;
    }

    public float montoTotalMesAnioAnterior() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH) + 1;
        String mesStr = "";
        if (mes < 10) {
            mesStr = "0";
        }
        mesStr = mesStr + mes;
        int dia = c.get(Calendar.DAY_OF_MONTH);
        String diaStr = "";
        if (dia < 10) {
            diaStr = "0";
        }
        diaStr = diaStr + dia;
        String primerDiaStr = "01";
        float monto = 0;
        List<Float> listaAlquiler = null;
        try {
            String consulta = "select a.gran_total from Alquiler a where a.fecha_inicio >= '" + anio + "-" + mesStr + "-" + primerDiaStr + "' and a.fecha_inicio <= '" + anio + "-" + mesStr + "-" + diaStr + "'";
            consulta = consulta + " and a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            for (int a = 0; a < listaAlquiler.size(); a++) {
                monto = monto + listaAlquiler.get(a);
            }
        }
        int montoInt = (int) (monto * 100);
        monto = ((float) montoInt) / 100;

        return monto;
    }

    public Alquiler getAlquilerAutoFecha(int idAuto, Date fecha) {
        Alquiler alquilerResult = null;
        try {
            String consulta = "FROM Alquiler a WHERE a.auto.id = ?1 AND a.fecha_inicio <= ?2 AND a.fecha_fin >=?2 AND ( a.estado = " + Alquiler.ESTADO_ALQUILADO + ")";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAuto);
            q.setParameter(2, fecha);
            List<Alquiler> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                alquilerResult = listaResultado.get(listaResultado.size() - 1);
            }
        } catch (Exception e) {

        }
        return alquilerResult;
    }

    @Override
    public Alquiler getAlquilerPorId(int id) {
        Alquiler alquilerResult = null;
        try {
            String consulta = "FROM Alquiler a WHERE a.id = ?1 ";
            Query q = em.createQuery(consulta);
            q.setParameter(1, id);
            List<Alquiler> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                alquilerResult = listaResultado.get(listaResultado.size() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alquilerResult;
    }

    @Override
    public Alquiler getAlquilerActivoPorId(int id) {
        Alquiler alquilerResult = null;
        try {
            String consulta = "FROM Alquiler a WHERE a.id = ?1 AND (a.estado = " + Alquiler.ESTADO_RESERVA + " OR a.estado = " + Alquiler.ESTADO_ALQUILADO + ")";
            Query q = em.createQuery(consulta);
            q.setParameter(1, id);
            List<Alquiler> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                alquilerResult = listaResultado.get(listaResultado.size() - 1);
            }
        } catch (Exception e) {

        }
        return alquilerResult;
    }

    @Override
    public List<Alquiler> getAlquileresSuperpuestos(Date fechaInicio, Date fechaFin, int idAuto, int id) throws Exception {
        List<Alquiler> listaAlquileresResult = new ArrayList();

        try {
            // ?1 es la fecha de inicio
            // ?2 es la fecha de fin
            String consulta = "FROM Alquiler a WHERE a.fecha_fin >= ?1 AND a.fecha_inicio <= ?2 AND a.auto.id = ?3 AND a.id != ?4 AND a.estado != " + Alquiler.ESTADO_CANCELADO + " AND a.estado != " + Alquiler.ESTADO_RETORNADO + " AND a.estado != " + Alquiler.ESTADO_REPO + " AND a.estado != " + Alquiler.ESTADO_VENDIDO;

            Query q = em.createQuery(consulta);
            q.setParameter(1, fechaInicio);
            q.setParameter(2, fechaFin);
            q.setParameter(3, idAuto);
            q.setParameter(4, id);
            listaAlquileresResult = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaAlquileresResult;
    }

    public List<Alquiler> getAlquilerPorFiltro(String rego, String lastname, Date fechaDesde, String referencia, String telefono, int idSucusal, Date fechaHasta, boolean deudaCurrentMayor0, int estado) {
        List<Alquiler> listaAlquileresResultado = new ArrayList();
        try {
            String consulta = "FROM Alquiler a WHERE  1= 1";
            boolean filtro = false;
            if (fechaDesde != null && fechaHasta != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaDesdeStr = sdf.format(fechaDesde);
                String fechaHastaStr = sdf.format(fechaHasta);
                consulta = consulta + " AND a.fecha_inicio >= '" + fechaDesdeStr + "' AND a.fecha_inicio <= '" + fechaHastaStr + "' ";
                filtro = true;
            }
            if (rego != null) {
                consulta = consulta + " AND a.rego like '%" + rego + "%' ";
                filtro = true;
            }
            if (lastname != null) {
                consulta = consulta + " AND a.apellido like '%" + lastname + "%' ";
                filtro = true;
            }
            if (referencia != null && referencia.trim().length() > 0) {
                consulta = consulta + " AND a.id =" + referencia;
                filtro = true;
            }
            if (telefono != null && telefono.trim().length() > 0) {
                consulta = consulta + " AND (a.telefono like '%" + telefono + "%' or a.movil like '%" + telefono + "%' or a.auto.number_gps like '%" + telefono + "%') ";
                filtro = true;
            }
            if (idSucusal > -1) {
                consulta = consulta + " AND a.sucursal_origen.id= " + idSucusal;
                filtro = true;
            }
            if (deudaCurrentMayor0) {
                consulta = consulta + " AND a.deudaCurrent>0 ";
                filtro = true;
            }
            if (estado > -1) {
                consulta = consulta + " AND a.estado = " + estado;
                filtro = true;
            }
            Query a = em.createQuery(consulta);
            if (filtro == false) {
                a.setMaxResults(50);
            }
            listaAlquileresResultado = a.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaAlquileresResultado;
    }

    @Override
    public int cantContratosMesActual() {
        Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH) + 1;
        String mesStr = "";
        if (mes < 10) {
            mesStr = "0";
        }
        mesStr = mesStr + mes;
        int dia = c.get(Calendar.DAY_OF_MONTH);
        String diaStr = "";
        if (dia < 10) {
            diaStr = "0";
        }
        diaStr = diaStr + dia;
        String primerDiaStr = "01";
        float monto = 0;
        List<Alquiler> listaAlquiler = null;
        try {
            String consulta = "select a.id from Alquiler a where a.fecha_inicio >= '" + anio + "-" + mesStr + "-" + primerDiaStr + "' and a.fecha_inicio <= '" + anio + "-" + mesStr + "-" + diaStr + "'";
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            return listaAlquiler.size();
        } else {
            return 0;
        }
    }

    @Override
    public int cantContratosMesAnioAnterior() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH) + 1;
        String mesStr = "";
        if (mes < 10) {
            mesStr = "0";
        }
        mesStr = mesStr + mes;
        int dia = c.get(Calendar.DAY_OF_MONTH);
        String diaStr = "";
        if (dia < 10) {
            diaStr = "0";
        }
        diaStr = diaStr + dia;
        String primerDiaStr = "01";
        float monto = 0;
        List<Alquiler> listaAlquiler = null;
        try {
            String consulta = "select a.id from Alquiler a where a.fecha_inicio >= '" + anio + "-" + mesStr + "-" + primerDiaStr + "' and a.fecha_inicio <= '" + anio + "-" + mesStr + "-" + diaStr + "'";
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            return listaAlquiler.size();
        } else {
            return 0;
        }
    }

    @Override
    public float getMontoAcumuladoActual() {
        Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH) + 1;
        String mesStr = "";
        if (mes < 10) {
            mesStr = "0";
        }
        mesStr = mesStr + mes;
        int dias = c.get(Calendar.DAY_OF_MONTH);
        String diaStr = "";
        if (dias < 10) {
            diaStr = "0";
        }
        diaStr = diaStr + dias;

        float monto = 0;
        List<Float> listaAlquiler = null;
        try {
            String consulta = "select a.gran_total from Alquiler a where a.fecha_inicio >= '" + anio + "-01-01' and a.fecha_inicio <= '" + anio + "-" + mesStr + "-" + diaStr + "'";
            consulta = consulta + " and a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            for (int a = 0; a < listaAlquiler.size(); a++) {
                monto = monto + listaAlquiler.get(a);
            }
        }
        int montoInt = (int) (monto * 100);
        monto = ((float) montoInt) / 100;

        return monto;
    }

    @Override
    public float getMontoAcumuladoAnterior() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH) + 1;
        String mesStr = "";
        if (mes < 10) {
            mesStr = "0";
        }
        mesStr = mesStr + mes;
        int dia = c.get(Calendar.DAY_OF_MONTH);
        String diaStr = "";
        if (dia < 10) {
            diaStr = "0";
        }
        diaStr = diaStr + dia;
        float monto = 0;
        List<Float> listaAlquiler = null;
        try {
            String consulta = "select a.gran_total from Alquiler a where a.fecha_inicio >= '" + anio + "-01-01' and a.fecha_inicio <= '" + anio + "-" + mesStr + "-" + diaStr + "'";
            consulta = consulta + " and a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            for (int a = 0; a < listaAlquiler.size(); a++) {
                monto = monto + listaAlquiler.get(a);
            }
        }
        int montoInt = (int) (monto * 100);
        monto = ((float) montoInt) / 100;

        return monto;
    }

    @Override
    public int getCantContratosAcumuladosActual() {
        Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH) + 1;
        String mesStr = "";
        if (mes < 10) {
            mesStr = "0";
        }
        mesStr = mesStr + mes;
        int dias = c.get(Calendar.DAY_OF_MONTH);
        String diaStr = "";
        if (dias < 10) {
            diaStr = "0";
        }
        diaStr = diaStr + dias;

        float monto = 0;
        List<Alquiler> listaAlquiler = null;
        try {
            String consulta = "select a.id from Alquiler a where a.fecha_inicio >= '" + anio + "-01-01' and a.fecha_inicio <= '" + anio + "-" + mesStr + "-" + diaStr + "'";
            consulta = consulta + " and a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            return listaAlquiler.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getCantContratosAcumuladosAnterior() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
        int anio = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH) + 1;
        String mesStr = "";
        if (mes < 10) {
            mesStr = "0";
        }
        mesStr = mesStr + mes;
        int dia = c.get(Calendar.DAY_OF_MONTH);
        String diaStr = "";
        if (dia < 10) {
            diaStr = "0";
        }
        diaStr = diaStr + dia;
        float monto = 0;
        List<Alquiler> listaAlquiler = null;
        try {
            String consulta = "select a.id from Alquiler a where a.fecha_inicio >= '" + anio + "-01-01' and a.fecha_inicio <= '" + anio + "-" + mesStr + "-" + diaStr + "'";
            consulta = consulta + " and a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            return listaAlquiler.size();
        } else {
            return 0;
        }
    }

    @Override
    public List<Alquiler> getAlquileresDeudaActualAntesFecha(Date date) {
        List<Alquiler> listaAlquileresResultado = new ArrayList();
        try {
            String consulta = "FROM Alquiler a WHERE  1=1 ";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaStr = sdf.format(date);
            consulta = consulta + " AND ( a.fecha_calculo_current is null or a.fecha_calculo_current <'" + fechaStr + "') ";
            consulta = consulta + " AND a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query a = em.createQuery(consulta);
            listaAlquileresResultado = a.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaAlquileresResultado;
    }

    @Override
    public List<Alquiler> getAlquileresDentroPeriodoConEstado(Date fechaDesde, Date fechaHasta, int estado, int idSucursal, Date fechaDesdeDrop, Date fechaHastaDrop) {
        List<Alquiler> listaAlquiler = new ArrayList();

        try {
            //select a.id,a.sucursal_origen,a.sucursal_destino,a.fecha_inicio, a.fecha_fin,   a.total, a.deuda,a.gran_total, a.rego, a.apellido  
            String consulta = "from Alquiler a where 1=1 ";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(fechaDesde !=null)
            {                
                consulta = consulta +" and a.fecha_inicio >= '"+sdf.format(fechaDesde)+"'";
            }
            if(fechaHasta !=null)
            {
                consulta = consulta +" and a.fecha_inicio <= '"+sdf.format(fechaHasta)+"'";
            }
            if(fechaDesdeDrop!=null)
            {
                consulta = consulta +" and a.fecha_fin >= '"+sdf.format(fechaDesdeDrop)+"'";
            }
            if(fechaHastaDrop!=null)
            {
                consulta = consulta +" and a.fecha_fin <= '"+sdf.format(fechaHastaDrop)+"'";
            }
            if (estado > -1) {
                consulta = consulta + " and a.estado = " + estado;
            }
            if (idSucursal > -1) {
                consulta = consulta + " and a.sucursal_origen.id = " + idSucursal;
            }
            consulta = consulta + " order by a.id asc ";
            Query q = em.createQuery(consulta);           
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquiler;
    }

    @Override
    public List<Alquiler> getAlquileresHired() {
        List<Alquiler> listaAlquiler = new ArrayList();

        try {
            String consulta = "from Alquiler a where a.estado =" + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquiler;

    }

    @Override
    public Alquiler getAlquilerAlquiladoAuto(int idAuto) {
        Alquiler alquilerResult = null;
        try {
            String consulta = "FROM Alquiler a WHERE a.auto.id = ?1  AND  a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAuto);
            List<Alquiler> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                alquilerResult = listaResultado.get(listaResultado.size() - 1);
            }
        } catch (Exception e) {

        }
        return alquilerResult;
    }

    @Override
    public List<Alquiler> getListaRegoAVencer() {
        List<Alquiler> listaAlquiler = new ArrayList();

        try {
            String consulta = "from Alquiler a where a.estado =" + Alquiler.ESTADO_ALQUILADO + " and a.auto.id>-1 order by a.auto.fecha_vencimiento_rego asc";
            Query q = em.createQuery(consulta);
            q.setMaxResults(8);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquiler;
    }

    @Override
    public List<Alquiler> getAlquileresLoading() {
        List<Alquiler> listaAlquileresResultado = new ArrayList();
        try {
            String consulta = "FROM Alquiler a WHERE  a.estado = " + Alquiler.ESTADO_CARGANDO;
            Query a = em.createQuery(consulta);
            listaAlquileresResultado = a.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaAlquileresResultado;
    }

    @Override
    public int getCantContratosActivos() {
        List<Alquiler> listaAlquiler = null;
        try {
            String consulta = "select a.id from Alquiler a where a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            return listaAlquiler.size();
        } else {
            return 0;
        }
    }

    @Override
    public float getMontoTotalAdeudado() {
        float monto = 0;
        List<Float> listaAlquiler = null;
        try {
            String consulta = "select a.deuda from Alquiler a where a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaAlquiler != null) {
            for (int a = 0; a < listaAlquiler.size(); a++) {
                monto = monto + listaAlquiler.get(a);
            }
        }
        int montoInt = (int) (monto * 100);
        monto = ((float) montoInt) / 100;

        return monto;
    }

    @Override
    public List<Alquiler> getListaAlquilerInsurance() {
        List<Alquiler> listaAlquileresResultado = new ArrayList();
        try {
            String consulta = "FROM Alquiler a WHERE  "
                    + " a.estado = " + Alquiler.ESTADO_ALQUILADO;
            Query a = em.createQuery(consulta);
            listaAlquileresResultado = a.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listaAlquileresResultado;
    }

    @Override
    public Alquiler getAlquilerClienteConDeuda(int idCliente) {
        Alquiler alquilerResultado = null;
        try {
            String consulta = "select a.id from alquiler a, alquiler_cliente ac"
                    + " where a.id = ac.id_alquiler "
                    + " and ac.id_cliente =" + idCliente
                    + " and a.estado !=" + Alquiler.ESTADO_ALQUILADO
                    + " and a.deuda >2";
            Query a = em.createNativeQuery(consulta);
            List<Integer> listaAlquileresResultado = a.getResultList();
            if (listaAlquileresResultado.size() > 0) {
                int idAlquiler = listaAlquileresResultado.get(0);
                alquilerResultado = this.getAlquilerPorId(idAlquiler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alquilerResultado;

    }

    @Override
    public List<Alquiler> getAlquileresDentroPeriodoSinEstado(Date fechaDesde, Date fechaHasta, int idSucursal) {
        List<Alquiler> listaAlquiler = new ArrayList();

        try {
            //select a.id,a.sucursal_origen,a.sucursal_destino,a.fecha_inicio, a.fecha_fin,   a.total, a.deuda,a.gran_total, a.rego, a.apellido  
            String consulta = "from Alquiler a where a.fecha_inicio >=  ?1 and a.fecha_inicio <= ?2 ";
            if (idSucursal > -1) {
                consulta = consulta + " and a.sucursal_origen.id = " + idSucursal;
            }
            consulta = consulta + " order by a.id asc ";
            Query q = em.createQuery(consulta);
            q.setParameter(1, fechaDesde);
            q.setParameter(2, fechaHasta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquiler;
    }

    @Override
    public List<Alquiler> getAlquileresActivosDelAuto(int idAuto) {
        List<Alquiler> listaAlquiler = new ArrayList();
        try {
            String consulta = "from Alquiler a where a.auto.id =" + idAuto + " and (a.estado =" + Alquiler.ESTADO_ALQUILADO + " or a.estado = " + Alquiler.ESTADO_CARGANDO + " "
                    + "or a.estado = " + Alquiler.ESTADO_RESERVA + ")";
            Query q = em.createQuery(consulta);
            q.setMaxResults(8);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquiler;

    }

    @Override
    public List<Alquiler> alquileresAutoRegoVencerPeriodo(Date fecha_desde, Date fecha_hasta) {
        List<Integer> listaIdAlquiler = null;
        List<Alquiler> listaAlquileres = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDesdeSTR = sdf.format(fecha_desde);
            String fechaHastaSTR = sdf.format(fecha_hasta);
            String consulta = "select distinct(al.id)"
                    + " from auto au, alquiler al"
                    + " where au.id=al.id_auto"
                    + " and al.estado = " + Alquiler.ESTADO_ALQUILADO
                    + " and au.fecha_vencimiento_rego <='" + fechaHastaSTR + "'"
                    + " and au.fecha_vencimiento_rego >='" + fechaDesdeSTR + "'"
                    + " order by au.fecha_vencimiento_rego asc";
            Query q = em.createNativeQuery(consulta);
            listaIdAlquiler = q.getResultList();
            for (int a = 0; a < listaIdAlquiler.size(); a++) {
                Alquiler alquilerEncontrado = this.getAlquilerPorId(listaIdAlquiler.get(a));
                if (alquilerEncontrado != null) {
                    listaAlquileres.add(alquilerEncontrado);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquileres;

    }

    @Override
    public List<Alquiler> getListaTerminando(Date fechaLimite) {
        List<Alquiler> listaAlquiler = new ArrayList<>();
        try {
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String hoy = sdf.format(c.getTime());
            String fechaLimiteStr = sdf.format(fechaLimite);
            //select a.id,a.sucursal_origen,a.sucursal_destino,a.fecha_inicio, a.fecha_fin,   a.total, a.deuda,a.gran_total, a.rego, a.apellido  
            String consulta = "from Alquiler a where a.fecha_fin <='" + fechaLimiteStr + "' and a.estado = " + Alquiler.ESTADO_ALQUILADO + " order by a.fecha_fin asc";
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquiler;

    }

    @Override
    public List<Alquiler> getAlquileresTerminanPeriodo(Date fechaDesde, Date fechaHasta) {
        List<Alquiler> listaAlquiler = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String consulta = "from Alquiler a where 1=1 ";// a.fecha_fin <='" + fechaLimiteStr + "' and a.estado = " + Alquiler.ESTADO_ALQUILADO + " order by a.fecha_fin asc";
            if (fechaDesde != null) {
                String fechaDesdeStr = sdf.format(fechaDesde);
                consulta = consulta + " and a.fecha_fin >= '" + fechaDesdeStr+"'";
            }
            if (fechaHasta != null) {
                String fechaHastaStr = sdf.format(fechaHasta);
                consulta = consulta + " and a.fecha_fin <= '" + fechaHastaStr+"'";
            }
            consulta = consulta + " and a.estado = " + Alquiler.ESTADO_ALQUILADO + " order by a.fecha_fin asc ";
            System.out.println(consulta);
            Query q = em.createQuery(consulta);
            listaAlquiler = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlquiler;
    }

    @Override
    public Alquiler getUltimoAlquilerAuto(int idAuto) {
        Alquiler alquilerResult = null;
        try {
            String consulta = "FROM Alquiler a WHERE a.auto.id = ?1 order by a.id desc";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAuto);
            List<Alquiler> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                alquilerResult = listaResultado.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alquilerResult;
    }

}
