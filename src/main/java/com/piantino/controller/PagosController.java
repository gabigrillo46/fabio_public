package com.piantino.controller;

import com.piantino.ejb.PagoImportadoSet;
import com.piantino.ejb.PagoSet;
import com.piantino.ejb.AlquilerSet;
import com.piantino.ejb.PagosImportadosFacade;
import com.piantino.ejb.PagosImportadosFacadeLocal;
import com.piantino.ejb.ParametroFacade;
import com.piantino.ejb.ParametroFacadeLocal;
import com.piantino.model.Pago;
import com.piantino.model.PagoImportado;
import com.piantino.model.Parametro;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.piantino.model.Alquiler;
import com.piantino.model.Sucursal;
import com.piantino.model.Usuario;

public class PagosController extends Thread implements Serializable {

    private PagoImportadoSet pagoImportadoSet;
    private PagoSet pagoSet;
    private Vector idAlquilerVecPagos = new Vector();
    private AlquilerSet alquilerSet;

    public PagosController() {
        this.setName("procesadorPago");
        AccesoBD acc = new AccesoBD();
        acc.inicializar();
        pagoImportadoSet = new PagoImportadoSet();
        pagoSet = new PagoSet();
        alquilerSet = new AlquilerSet();
    }

    public void run() {
        boolean continuar = true;
        while (continuar) {

            for (int j = -60; j <= 0; j++) {
                Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
                c.add(Calendar.DAY_OF_YEAR, j);
                Date hoyDate = c.getTime();

                SimpleDateFormat adf = new SimpleDateFormat("yyyyMMdd");

                String hoyString = adf.format(hoyDate);
                if (hoyString.equalsIgnoreCase("20201127")) {
                    System.out.println("Aca esta");
                }
                String nombreFichero = "Payments" + hoyString + "_" + Parametro.USUARIO_BANCO + ".csv";

                File fileATratar = new File(Parametro.DIRECTORIO_A_TRATAR + nombreFichero);
                File fileProcesados = new File(Parametro.DIRECTORIO_IMPORTADOS + nombreFichero);
                File fileDescargados = new File(nombreFichero);
                System.out.println("ficherooooooooooooooooooooo");
                System.out.println(fileATratar.getAbsolutePath());
                System.out.println(fileDescargados.getAbsolutePath());
                System.out.println(fileProcesados.getAbsolutePath());
                if (!fileDescargados.exists() && !fileATratar.exists() && !fileProcesados.exists()) {
                    System.out.println("Busco el fichero " + nombreFichero + "  pero no existe, asi que lo importo");
                    importarFichero(hoyDate);
                    if (fileDescargados.exists()) {
                        moverFichero(fileDescargados, fileATratar);
                        eliminarFile(fileDescargados);
                        procesarFileATratar(fileATratar);
                        this.moverFichero(fileATratar, fileProcesados);
                        eliminarFile(fileATratar);
                    }
                }
            }

            this.meterPagosAdentro();
            this.actualizarAlquileres();

            int horas = 2;
            int minutos = horas * 60;
            int segundos = minutos * 60;
            int milisegundos = segundos * 1000;

            try {
                Thread.currentThread().sleep(milisegundos);
            } catch (Exception e) {
                return;
            }
        }
    }

    private void eliminarFile(File fileDescargados) {
        if (fileDescargados.exists()) {
            fileDescargados.delete();
        }
    }

    private void procesarFileATratar(File fileATratar) {
        String SEPARADOR = ",";

        BufferedReader bufferLectura = null;
        try {

            // Abrir el .csv en buffer de lectura
            bufferLectura = new BufferedReader(new FileReader(fileATratar));

            // Leer una linea del archivo
            String linea = bufferLectura.readLine();

            String[] nombreColumna = linea.split(SEPARADOR);

            int contador = 0;

            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                String[] campos = linea.split(SEPARADOR);
                if (contador != 0) {
                    PagoImportado pagoImp = new PagoImportado();
                    pagoImp.setMonto(campos[12]);
                    pagoImp.setReferencia(campos[15]);
                    pagoImp.setCustomerName(campos[16]);
                    pagoImp.setRespuestaCodigo(campos[23]);
                    pagoImp.setRespuestaTexto(campos[24]);
                    pagoImp.setReciboNumero(campos[25]);
                    pagoImp.setFecha(campos[29]);
                    pagoImp.setStatus(campos[30]);
                    pagoImp.setEstadoPrivado(PagoImportado.ESTADO_NO_INGRESADO);
                    PagoImportado pagoExiste = pagoImportadoSet.getPagoImportadoPorNumeroCompro(pagoImp.getReciboNumero());
                    if (pagoExiste == null) {
                        pagoImportadoSet.insertar(pagoImp);
                    }
                    System.out.println("****************************");
                    for (int a = 0; a < campos.length; a++) {
                        System.out.println(a + " " + nombreColumna[a] + ": " + campos[a]);
                    }
                }
                contador++;

                // Volver a leer otra línea del fichero
                linea = bufferLectura.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void meterPagosAdentro() {
        try {
            Vector pagoNOIngresados = this.pagoImportadoSet.getListaPagoNOIngresados();
            for (int a = 0; a < pagoNOIngresados.size(); a++) {
                PagoImportado pagoImp = (PagoImportado) pagoNOIngresados.elementAt(a);
                Pago pagoEnBD = this.pagoSet.getPagoImportadoPorNumeroCompro(pagoImp.getReciboNumero());
                if (pagoEnBD == null) {
                    Pago pagoAEntrar = new Pago();
                    int idAlquiler = -1;
                    try {
                        idAlquiler = Integer.parseInt(pagoImp.getReferencia());
                    } catch (Exception e) {

                    }
                    pagoAEntrar.getAlquiler().setId(idAlquiler);
                    pagoAEntrar.setMonto(Float.parseFloat(pagoImp.getMonto()));

                    SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    pagoAEntrar.setFecha_hora(d.parse(pagoImp.getFecha()));
                    pagoAEntrar.setNombre_cliente(pagoImp.getCustomerName());
                    pagoAEntrar.setStatus(pagoImp.getStatus());

                    Alquiler alq = this.alquilerSet.getAlquierPorId(idAlquiler);
                    
                    
                    
                    if (alq != null && alq.getRate_per_day() != pagoAEntrar.getMonto() && pagoAEntrar.getStatus().startsWith("Approved")) {
                        if (alq.isFortnightly()) {
                            float montoQuincental = alq.getRate_per_day() * 2;
                            if (montoQuincental != pagoAEntrar.getMonto()) {
                                pagoAEntrar.setStatus(Pago.Texto_Aprobado_warning);
                            }
                            else
                            {
                                pagoAEntrar.setStatus(Pago.Texto_Aprobado);
                            }
                        } else {
                            pagoAEntrar.setStatus(Pago.Texto_Aprobado_warning);
                        }
                    }

                    Sucursal sucu = new Sucursal();
                    sucu.setId(2);
                    Usuario usu = new Usuario();
                    usu.setId(8);
                    pagoAEntrar.setSucursal(sucu);
                    pagoAEntrar.setUsuario(usu);
                    pagoAEntrar.setRecibo_numero(pagoImp.getReciboNumero());
                    pagoAEntrar.setAutomatico(1);
                    pagoAEntrar.setAgrupacion("");
                    pagoAEntrar.getTipoPago().setId(1);
                    if (this.pagoSet.insertar(pagoAEntrar)) {

                        if (!idAlquilerVecPagos.contains(pagoImp.getReferencia())) {
                            idAlquilerVecPagos.add(pagoImp.getReferencia());
                        }
                    }
                }
                pagoImp.setEstadoPrivado(PagoImportado.ESTADO_INGRESADO);
                this.pagoImportadoSet.actualizar(pagoImp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void actualizarAlquileres() {
        for (int j = 0; j < this.idAlquilerVecPagos.size(); j++) {
            String idAlquilerStr = (String) this.idAlquilerVecPagos.elementAt(j);
            int idAlquiler = Integer.parseInt(idAlquilerStr);
            Alquiler alq = this.alquilerSet.getAlquierPorId(idAlquiler);
            if (alq != null) {
                Vector pagosAlquiler = this.pagoSet.getListaAprobadoPorIDAlquiler(idAlquiler);
                float deuda = 0;
                deuda = alq.getGran_total();
                for (int u = 0; u < pagosAlquiler.size(); u++) {
                    Pago pagoProcesar = (Pago) pagosAlquiler.elementAt(u);
                    deuda = deuda - pagoProcesar.getMonto();

                    int deudaInt = (int) (deuda * 100);
                    deuda = ((float) deudaInt) / 100;
                }
                alq.setDeuda(deuda);
                this.alquilerSet.actualizarDeudaAlquiler(alq);
            }
        }
    }

    public void moverFichero(File sourceFile, File destFile) {
        try {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }

            FileChannel source = null;
            FileChannel destination = null;
            try {
                source = new FileInputStream(sourceFile).getChannel();
                destination = new FileOutputStream(destFile).getChannel();

                // previous code: destination.transferFrom(source, 0, source.size());
                // to avoid infinite loops, should be:
                long count = 0;
                long size = source.size();
                while ((count += destination.transferFrom(source, count, size - count)) < size);
            } finally {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
            }
        } catch (Exception e) {

        }

    }

    public void importarFichero(Date fechaDate) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaABuscar = sdf.format(fechaDate);
            Runtime rt = Runtime.getRuntime();
            String comando = "curl -i --basic --user " + Parametro.CLAVE_PUBLICA + ": https://api.payway.com.au/rest/v1";
            System.out.println("Comando:[" + comando + "] estado de red");
            Process proc = rt.exec(comando);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

// read the output from the command
            System.out.println("Respuesta de comando de estado de red");
            System.out.println("**************************************");
            String s = null;

            while ((s = stdInput.readLine()) != null) {
                if (s.contains("HTTP/1.1 200 OK")) {
                    System.out.println("Conexion correcta con el server pay way");
                }
                System.out.println(s);
            }
            System.out.println("**************************************");

            comando = "curl -OJ --config " + Parametro.FICHERO_CONFIGURACION + " https://api.payway.com.au/rest/v1/receipts-files/{" + fechaABuscar + "}";
            System.out.println("Comando:[" + comando + "] descarga de archivo");
            Process proc2 = rt.exec(comando);

            BufferedReader stdInput2 = new BufferedReader(new InputStreamReader(proc2.getInputStream()));

            BufferedReader stdError2 = new BufferedReader(new InputStreamReader(proc2.getErrorStream()));
            System.out.println("Respuesta al pedir el archivo");
            System.out.println("******************************");
            s = null;
            while ((s = stdInput2.readLine()) != null) {
                System.out.println(s);
            }

            while ((s = stdError2.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println("******************************");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
    Runtime rt = Runtime.getRuntime();
String[] commands = {"system.exe","-get t"};
Process proc = rt.exec(commands);

BufferedReader stdInput = new BufferedReader(new 
     InputStreamReader(proc.getInputStream()));

BufferedReader stdError = new BufferedReader(new 
     InputStreamReader(proc.getErrorStream()));

// read the output from the command
System.out.println("Here is the standard output of the command:\n");
String s = null;
while ((s = stdInput.readLine()) != null) {
    System.out.println(s);    
}

// read any errors from the attempted command
System.out.println("Here is the standard error of the command (if any):\n");
while ((s = stdError.readLine()) != null) {
    System.out.println(s);
}*/
}
