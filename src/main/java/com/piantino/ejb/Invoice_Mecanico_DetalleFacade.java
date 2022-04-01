/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Invoice_Mecanico_Detalle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Gabriel
 */
@Stateless
public class Invoice_Mecanico_DetalleFacade extends AbstractFacade<Invoice_Mecanico_Detalle> implements Invoice_Mecanico_DetalleFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Invoice_Mecanico_DetalleFacade() {
        super(Invoice_Mecanico_Detalle.class);
    }

    @Override
    public List<Invoice_Mecanico_Detalle> buscarPorFiltro(String rego, int tipo, Date fechaDesde, Date fechaHasta, String invoiceNumber) {
        List<Invoice_Mecanico_Detalle> resultados = new ArrayList<>();
        List<Integer> listaIdInvoiceDetalle = new ArrayList<Integer>();
        try {
            String consulta = "select distinct(imd.id) from invoice_mecanico_detalle imd, invoice_mecanico im "
                    + " where imd.id_invoice = im.id";

            if (rego != null && rego.trim().length() > 0) {
                consulta = consulta + " and imd.rego = '" + rego + "'";
            }
            if (tipo > -1) {
                consulta = consulta + " and imd.tipo = " + tipo;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = "";
            if (fechaDesde != null) {
                fecha = sdf.format(fechaDesde);
                consulta = consulta + " and im.fecha >= '" + fecha + "'";
            }
            if (fechaHasta != null) {
                fecha = sdf.format(fechaHasta);
                consulta = consulta + " and im.fecha <= '" + fecha + "'";
            }

            if (invoiceNumber != null && invoiceNumber.trim().length() > 0) {
                consulta = consulta + " and im.invoice_number = '" + invoiceNumber + "'";
            }

            Query q = em.createNativeQuery(consulta);
            listaIdInvoiceDetalle = q.getResultList();
            for (int a = 0; a < listaIdInvoiceDetalle.size(); a++) {
                Invoice_Mecanico_Detalle invoiceMecanicoDetalle = this.find(listaIdInvoiceDetalle.get(a));
                if (invoiceMecanicoDetalle != null) {
                    resultados.add(invoiceMecanicoDetalle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultados;
    }

    @Override
    public List<Invoice_Mecanico_Detalle> listaDetallePorIdInvoice(long idInvoice) {
        List<Invoice_Mecanico_Detalle> resultado = new ArrayList<Invoice_Mecanico_Detalle>();
        try {
            String consulta = "FROM Invoice_Mecanico_Detalle imd where imd.invoiceMecanico.id = " + idInvoice;
            Query q = em.createQuery(consulta);
            resultado = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public void saveListaDetalleInvoiceDefinitiva(List<Invoice_Mecanico_Detalle> listaDetalleInvoiceDefinitiva, Invoice_Mecanico invoiceMecanico) {
        List<Invoice_Mecanico_Detalle> listaDetalleActual = this.listaDetallePorIdInvoice(invoiceMecanico.getId());
        for (int a = 0; a < listaDetalleActual.size(); a++) {
            Invoice_Mecanico_Detalle invoiceMecanicoDetalleActual = listaDetalleActual.get(a);
            boolean existe = false;
            for (int b = 0; b < listaDetalleInvoiceDefinitiva.size(); b++) {
                Invoice_Mecanico_Detalle invoiceMecanicoDefinitiva = listaDetalleInvoiceDefinitiva.get(b);
                if (invoiceMecanicoDefinitiva.getId() == invoiceMecanicoDetalleActual.getId()) {
                    existe = true;
                }
            }
            if (existe == false) {
                this.remove(invoiceMecanicoDetalleActual);
            }
        }

        for (int a = 0; a < listaDetalleInvoiceDefinitiva.size(); a++) {
            Invoice_Mecanico_Detalle invoiceMecanicoDetalleDefinitiva = listaDetalleInvoiceDefinitiva.get(a);
            boolean existe = false;
            for (int b = 0; b < listaDetalleActual.size(); b++) {
                Invoice_Mecanico_Detalle invoiceMecanicoDetalleActual = listaDetalleActual.get(b);
                if (invoiceMecanicoDetalleActual.getId() == invoiceMecanicoDetalleDefinitiva.getId()) {
                    existe = true;
                }
            }
            if (invoiceMecanicoDetalleDefinitiva.getModelo() != null && invoiceMecanicoDetalleDefinitiva.getModelo().getId() == -1) {
                invoiceMecanicoDetalleDefinitiva.setModelo(null);
            }
            if (invoiceMecanicoDetalleDefinitiva.getMarca() != null && invoiceMecanicoDetalleDefinitiva.getMarca().getId() == -1) {
                invoiceMecanicoDetalleDefinitiva.setMarca(null);
            }
            if (existe) {
                this.edit(invoiceMecanicoDetalleDefinitiva);
            } else {
                invoiceMecanicoDetalleDefinitiva.setInvoiceMecanico(invoiceMecanico);
                this.create(invoiceMecanicoDetalleDefinitiva);
            }
        }
    }

    public List<Invoice_Mecanico_Detalle> listaDetallePorIdAuto(int idAuto) {
        List<Invoice_Mecanico_Detalle> listaDetallePorIdAuto = new ArrayList<>();
        try {
            String consulta = "FROM Invoice_Mecanico_Detalle imd where imd.auto.id = " + idAuto;
            Query q = em.createQuery(consulta);
            listaDetallePorIdAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetallePorIdAuto;
    }

    @Override
    public List<Invoice_Mecanico_Detalle> listaDetallePorIdCliente(int idCliente) {
        List<Invoice_Mecanico_Detalle> listaDetallePorIdAuto = new ArrayList<>();
        try {
            String consulta = "FROM Invoice_Mecanico_Detalle imd where imd.cliente.id = " + idCliente;
            Query q = em.createQuery(consulta);
            listaDetallePorIdAuto = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetallePorIdAuto;
    }

    @Override
    public float getExpensesDelAuto(int idAuto) {
        List<Invoice_Mecanico_Detalle> listaDetalleInvoicesAuto = new ArrayList<>();
        listaDetalleInvoicesAuto = this.listaDetallePorIdAuto(idAuto);
        float montoFinal = 0;
        for (int a = 0; a < listaDetalleInvoicesAuto.size(); a++) {
            Invoice_Mecanico_Detalle invoiceDetalle = listaDetalleInvoicesAuto.get(a);
            montoFinal += invoiceDetalle.getSubtotal();
        }

        int montoFInalInt = (int) (montoFinal * 100);
        montoFinal = ((float) montoFInalInt) / 100;
        return montoFinal;

    }

    @Override
    public float getExpensesDelCliente(int idCliente) {
        List<Invoice_Mecanico_Detalle> listaDetalleInvoicesAuto = new ArrayList<>();
        listaDetalleInvoicesAuto = this.listaDetallePorIdCliente(idCliente);
        float montoFinal = 0;
        for (int a = 0; a < listaDetalleInvoicesAuto.size(); a++) {
            Invoice_Mecanico_Detalle invoiceDetalle = listaDetalleInvoicesAuto.get(a);
            montoFinal += invoiceDetalle.getSubtotal();
        }

        int montoFInalInt = (int) (montoFinal * 100);
        montoFinal = ((float) montoFInalInt) / 100;
        return montoFinal;
    }

}
