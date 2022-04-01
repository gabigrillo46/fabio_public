/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.ejb;

import com.piantino.model.Invoice_Mecanico;
import com.piantino.model.Marca;
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
public class Invoice_MecanicoFacade extends AbstractFacade<Invoice_Mecanico> implements Invoice_MecanicoFacadeLocal {

    @PersistenceContext(unitName = "primeFabio")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Invoice_MecanicoFacade() {
        super(Invoice_Mecanico.class);
    }

    @Override
    public Invoice_Mecanico getInvoicePorProveedorNumero(int idProveedor, String numero) {
        Invoice_Mecanico invoiceEncontrada = null;
        try {
            String consulta = "FROM Invoice_Mecanico i WHERE i.proveedor.id = " + idProveedor + " and i.invoice_number = '" + numero + "'";
            Query q = em.createQuery(consulta);
            List<Invoice_Mecanico> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                invoiceEncontrada = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return invoiceEncontrada;

    }

    @Override
    public Invoice_Mecanico getInvoiceIngresoPorNumero(String numero) {
        Invoice_Mecanico invoiceEncontrada = null;
        try {
            String consulta = "FROM Invoice_Mecanico i WHERE i.sentido ="+ Invoice_Mecanico.SENTIDO_INGRESO+ " and i.invoice_number = '" + numero + "'";
            Query q = em.createQuery(consulta);
            List<Invoice_Mecanico> listaResultado = q.getResultList();
            if (listaResultado.size() > 0) {
                invoiceEncontrada = listaResultado.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return invoiceEncontrada;        
    }



}
