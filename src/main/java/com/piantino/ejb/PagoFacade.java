/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Pago;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gaby
 */
@Stateless
public class PagoFacade extends AbstractFacade<Pago> implements PagoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoFacade() {
        super(Pago.class);
    }

    public List<Pago> buscarAprovadosPorAlquiler(int idAlquiler) {
        List<Pago> listaPagosPorFiltro = new ArrayList<Pago>();
        try {
            String consulta = "FROM Pago p WHERE 1=1 and p.alquiler.id = " + idAlquiler;
            consulta = consulta + " and p.status like 'Approved%' order by p.fecha_hora asc";
            Query q = em.createQuery(consulta);
            listaPagosPorFiltro = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPagosPorFiltro;
    }

    public List<Pago> buscarPorFiltro(String referencia, String numeroComprobante, Date fechaDesde, Date fechaHasta, String status, Integer automatico) {
        List<Pago> listaPagosPorFiltro = new ArrayList<Pago>();
        try {
            String consulta = "FROM Pago p WHERE 1=1 ";
            if (referencia != null) {
                consulta = consulta + " AND p.alquiler.id = " + referencia;
            }
            if (numeroComprobante != null) {
                consulta = consulta + " AND p.recibo_numero= " + numeroComprobante;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (fechaDesde != null) {

                consulta = consulta + " AND p.fecha_hora >='" + sdf.format(fechaDesde) + "'";
            }
            if (fechaHasta != null) {
                consulta = consulta + " AND p.fecha_hora <= '" + sdf.format(fechaHasta) + "'";
            }
            if (status != null) {
                consulta = consulta + " and p.status = '" + status + "'";
            }
            if (automatico != null) {
                consulta = consulta + " and p.automatico = " + automatico;
            }
            consulta = consulta + " order by p.fecha_hora desc";
            Query q = em.createQuery(consulta);
            //q.setMaxResults(1000);
            listaPagosPorFiltro = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPagosPorFiltro;
    }

    public List<Pago> buscarPorPago(int idPago) {
        List<Pago> listaPagosPago = new ArrayList<Pago>();
        try {
            String consulta = "FROM Pago p WHERE  p.alquiler.id = ?1";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idPago);

            listaPagosPago = q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return listaPagosPago;
    }

    @Override
    public float getMontoTotalMesAnioActual() {
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
        List<Float> listaPago = null;
        try {
            String consulta = "select a.monto from Pago a where a.fecha_hora >= '" + anio + "-" + mesStr + "-" + primerDiaStr + "' and a.fecha_hora <= '" + anio + "-" + mesStr + "-" + diaStr + "' and a.status like 'Approved%'";
            Query q = em.createQuery(consulta);
            listaPago = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaPago != null) {
            for (int a = 0; a < listaPago.size(); a++) {
                monto = monto + listaPago.get(a);
            }
        }
        int montoInt = (int) (monto * 100);
        monto = ((float) montoInt) / 100;

        return monto;

    }

    @Override
    public float getMontoTotalMesAnioAnterior() {
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
        List<Float> listaPago = null;
        try {
            String consulta = "select a.monto from Pago a where a.fecha_hora >= '" + anio + "-" + mesStr + "-" + primerDiaStr + "' and a.fecha_hora <= '" + anio + "-" + mesStr + "-" + diaStr + "' and a.status like 'Approved%'";
            Query q = em.createQuery(consulta);
            listaPago = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaPago != null) {
            for (int a = 0; a < listaPago.size(); a++) {
                monto = monto + listaPago.get(a);
            }
        }
        int montoInt = (int) (monto * 100);
        monto = ((float) montoInt) / 100;

        return monto;

    }

    @Override
    public List<Pago> buscarPorAlquiler(int idAlquiler) {
        List<Pago> listaPagosPorFiltro = new ArrayList<Pago>();
        try {
            String consulta = "FROM Pago p WHERE 1=1 ";
            consulta = consulta + " AND p.alquiler.id = " + idAlquiler + " order by p.fecha_hora asc";
            Query q = em.createQuery(consulta);

            listaPagosPorFiltro = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPagosPorFiltro;

    }

    @Override
    public List<Pago> listaPagosPorTipoDeAlquiler(int idAlquiler, int tipoPago) {
        List<Pago> listaPagosAlquiler = new ArrayList<Pago>();
        try {
            String consulta = "FROM Pago p WHERE p.alquiler.id = ?1 AND p.tipoPago.id = ?2";
            Query q = em.createQuery(consulta);
            q.setParameter(1, idAlquiler);
            q.setParameter(2, tipoPago);
            List<Pago> listaResultado = q.getResultList();
            listaPagosAlquiler = listaResultado;
        } catch (Exception e) {
            throw e;
        }
        return listaPagosAlquiler;
    }

    @Override
    public List<Pago> listaPagosAprovadosNoTollsNoLate(int idAlquiler, int tipoPagoToll, int tipoPagoLate) {
        List<Pago> listaPagosAlquiler = new ArrayList<Pago>();
        try {
            String consulta = "FROM Pago p WHERE 1=1 and p.alquiler.id = " + idAlquiler;
            consulta = consulta + " and p.status like 'Approved%' ";
            consulta = consulta + " and p.tipoPago.id != " + tipoPagoToll;
            consulta = consulta + " and p.tipoPago.id != " + tipoPagoLate;

            Query q = em.createQuery(consulta);
            List<Pago> listaResultado = q.getResultList();
            listaPagosAlquiler = listaResultado;
        } catch (Exception e) {
            throw e;
        }
        return listaPagosAlquiler;
    }

    @Override
    public void eliminarPorAlquiler(int idAlquiler) {
        List<Pago> listaPagosAlquiler = this.buscarPorAlquiler(idAlquiler);
        for (int k = 0; k < listaPagosAlquiler.size(); k++) {
            Pago pagoElimi = listaPagosAlquiler.get(k);
            this.remove(pagoElimi);
        }
    }

    @Override
    public float getMontoRecibidoDeAlquiler(int i) {
        float monto = 0;
        List<Float> listaPago = null;
        try {
            String consulta = "select a.monto from Pago a where a.alquiler.id =" + i + "  and a.status like 'Approved%'";
            Query q = em.createQuery(consulta);
            listaPago = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listaPago != null) {
            for (int a = 0; a < listaPago.size(); a++) {
                monto = monto + listaPago.get(a);
            }
        }
        int montoInt = (int) (monto * 100);
        monto = ((float) montoInt) / 100;

        return monto;

    }

    @Override
    public List<Pago> buscarPorFiltro2(Date fechaDesde, Date fechaHasta, String status, Integer idSucursal, Integer idFormaPago, String referencia) {
        List<Long> listaPago = null;
        List<Pago> listaPagosResultado = new ArrayList<>();
        float monto = 0;
        try {
            String consulta = "select p.id from Pago p where p.monto >0 ";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (fechaDesde != null) {

                consulta = consulta + " AND p.fecha_hora >='" + sdf.format(fechaDesde) + "'";
            }
            if (fechaHasta != null) {
                consulta = consulta + " AND p.fecha_hora <= '" + sdf.format(fechaHasta) + "'";
            }
            if (status != null) {
                consulta = consulta + " and p.status like '" + status + "%'";
            }
            if (idSucursal != null && idSucursal>-1) {
                consulta = consulta + " and p.alquiler.id in (select a.id from Alquiler a where a.sucursal_origen.id = " + idSucursal + ")";
            }
            if(idFormaPago != null && idFormaPago > -1)
            {
                consulta = consulta + " and p.tipoPago.id = "+idFormaPago;
            }
            else
            {
                consulta = consulta +" and p.tipoPago.id != "+2;
            }
            if(referencia!=null)
            {
                consulta = consulta +" and p.alquiler.id = "+referencia;
            }
            consulta = consulta + " order by p.fecha_hora desc";
            Query q = em.createQuery(consulta);
            listaPago = q.getResultList();
            
            for(int j=0;j<listaPago.size();j++)
            {
                Long idPago = listaPago.get(j);
                Pago pago = this.find(idPago);
                listaPagosResultado.add(pago);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPagosResultado;
    }

}
