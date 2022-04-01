/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.ejb.InterfazSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author gaby
 */
public class AccesoBD {

    private static Connection[] conexiones;

    private static boolean[] conexionesLibres;

    public void inicializar() {
        conexiones = new Connection[2];
        conexionesLibres = new boolean[2];
        abrirConexiones();

    }

    public boolean modificar(String consulta) {
        Connection con = null;
        Vector elementos = new Vector();
        int resul = 0;
        Long idThread = new Long(Thread.currentThread().getId());
        try {

            con = this.getConexionLibre();

            PreparedStatement prep = con.prepareStatement(consulta);

            resul = prep.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                this.liberarUnaConexion(con);
            }
        }
        if (resul == 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean modificar(String consulta, InterfazSet k, Object obj) {
        Connection con = null;
        Vector elementos = new Vector();
        int resul = 0;
        Long idThread = new Long(Thread.currentThread().getId());
        try {

            con = this.getConexionLibre();

            PreparedStatement prep = con.prepareStatement(consulta);
            k.prepararElementoModificacion(prep, obj);
            resul = prep.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                this.liberarUnaConexion(con);
            }
        }
        if (resul == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertar(String consulta, InterfazSet k, Object obj) {
        Connection con = null;
        Vector elementos = new Vector();
        int resul = 0;
        Long idThread = new Long(Thread.currentThread().getId());

        try {
            con = this.getConexionLibre();
            PreparedStatement prep = con.prepareStatement(consulta);
            k.prepararElementoInsercion(prep, obj);
            resul = prep.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                this.liberarUnaConexion(con);
            }
        }
        if (resul == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Vector getListaObjetos(String consulta, InterfazSet k) {
        Connection con = null;
        Vector elementos = new Vector();

        try {
            con = this.getConexionLibre();
            PreparedStatement prep = con.prepareStatement(consulta);
            ResultSet resul = prep.executeQuery();

            while (resul.next()) {
                Object elemento = k.getInstance();
                k.rellenarElemento(resul, elemento);
                elementos.add(elemento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                this.liberarUnaConexion(con);
            }
        }
        return elementos;
    }

    private Connection getConexionLibre() {
        for (int l = 0; l < conexionesLibres.length; l++) {
            if (conexionesLibres[l] == true) {
                conexionesLibres[l] = false;
                return conexiones[l];
            }
        }
        return null;
    }

    private void abrirConexiones() {
        String NOM_METODO = "abrirConexiones";
        for (int k = 0; k < conexiones.length; k++) {
            conexiones[k] = abrirConexion();
            conexionesLibres[k] = true;
            System.out.println("abrimos conexion "+k);
        }
    }

    private Connection abrirConexion() {
        String NOM_METODO = "abrirConexion";
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //DriverManager.registerDriver(com.tecsidel.sega.jdbc.sqlserver.SQLServerDriver());
            //conexion = DriverManager.getConnection(Constante.URL);
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/fabio", "root", "gabyLKJ321");
            /*if (conexion instanceof ExtEmbeddedConnection) {
                ExtEmbeddedConnection embeddedCon = (ExtEmbeddedConnection) conexion;
                boolean unlocked = embeddedCon.unlock("00065602");
            }*/
        } catch (Exception ex) {
            System.out.println("Abrimos conexion base de datos ERROR");
            ex.printStackTrace();

        }

        return conexion;

    }

    public ResultSet getResult(String consulta) {
        Connection con = null;
        try {
            con = this.getConexionLibre();
            PreparedStatement prep = con.prepareStatement(consulta);
            ResultSet resul = prep.executeQuery();

            return resul;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                this.liberarUnaConexion(con);
            }
        }
        return null;
    }

    public Object getObjecto(String consulta, InterfazSet h) {
        Connection con = null;

        try {
            con = this.getConexionLibre();
            PreparedStatement prep = con.prepareStatement(consulta);
            ResultSet resul = prep.executeQuery();

            while (resul.next()) {
                Object elemento = h.getInstance();
                h.rellenarElemento(resul, elemento);
                return elemento;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                this.liberarUnaConexion(con);
            }
        }
        return null;
    }

    private void liberarUnaConexion(Connection c) {
        for (int l = 0; l < conexionesLibres.length; l++) {
            if (conexionesLibres[l] == false) {
                if (conexiones[l].equals(c)) {
                    conexionesLibres[l] = true;
                    return;
                }
            }
        }
    }

}
