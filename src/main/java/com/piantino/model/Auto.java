package com.piantino.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "auto")
public class Auto implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = -1;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca = new Marca();

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo = new Modelo();

    @Column(name = "color")
    private String color = "";

    @Column(name = "transmision")
    private int transmision = 0;

    @Column(name = "año")
    private int año = 0;

    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private Tipo_auto tipo_body = new Tipo_auto();

    @Column(name = "kilometraje")
    private String kilometraje = "";

    @Column(name = "aire_acondicionado")
    private int aire_acondicionado = 0;

    @Column(name = "observacion")
    private String observacion = "";

    @Column(name = "nro_motor")
    private String nro_motor = "";

    @Column(name = "nro_chasis")
    private String nro_chasis = "";

    @Column(name = "VIN")
    private String VIN = "";

    @ManyToOne
    @JoinColumn(name = "id_tipo_combustible")
    private Tipo_combustible tipo_combustible = new Tipo_combustible();

    @ManyToOne
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal = new Sucursal();

    @Column(name = "disponible")
    private boolean disponible = false;

    @Column(name = "estado")
    private int estado = 0;

    @Column(name = "stock")
    private long stock = 0;

    @Column(name = "grabado")
    private String grabado = "";

    @ManyToOne
    @JoinColumn(name = "id_drivetrain")
    private Drivetrain drivetrain = new Drivetrain();

    @Column(name = "descripcion")
    private String descripcion = "";

    @Column(name = "titulo")
    private String titulo = "";

    @Column(name = "uso_descripcion_especifica")
    private boolean usoDescripcionEspecifica = false;

    @Column(name = "cilindros")
    private String cilindros = "";

    @Column(name = "motor")
    private String motor = "";

    @Column(name = "puertas")
    private int puertas = 0;

    @Column(name = "asientos")
    private int asientos = 0;

    @Column(name = "build")
    @Temporal(TemporalType.DATE)
    private Date buildDate = new Date();

    @Column(name = "compliance")
    @Temporal(TemporalType.DATE)
    private Date compliance = new Date();

    @Column(name = "marcado_vendido")
    private boolean marcado_vendido = false;

    @ManyToOne
    @JoinColumn(name = "id_label")
    private Label label = new Label();

    @Column(name = "rego")
    private String rego = "";

    @Column(name = "fecha_vencimiento_rego")
    @Temporal(TemporalType.DATE)
    private Date fecha_vencimiento_rego;

    @ManyToOne
    @JoinColumn(name = "id_state")
    private State state = new State();

    @ManyToOne
    @JoinColumn(name = "id_tipo_compra")
    private TipoCompra tipoCompra = new TipoCompra();

    public static final int ESTADO_DISPONIBLE = 0;
    public static final int ESTADO_BAJA = 1;
    public static final int ESTADO_REPARACION = 2;
    public static final int ESTADO_ALQUILADO = 3;
    public static final int ESTADO_VENDIDO = 4;
    public static final int ESTADO_RETORNADO = 5;

    public static final int AUTOMATIC = 0;
    public static final int MANUAL = 1;

    public static final String diesel = "DIESEL";
    public static final String unleaded = "GASOLINE";
    public static final String hybrid = "HYBRID";
    public static final String otro = "OTHER";

    public static final String automaticExcel = "AUTOMATIC";
    public static final String manualExcel = "MANUAL";

    @Column(name = "valor_compra")
    private float valor_compra = 0;

    @Column(name = "fecha_compra")
    @Temporal(TemporalType.DATE)
    private Date fecha_compra;

    @Column(name = "written_off")
    private boolean written_off = false;

    @Column(name = "certificate_date")
    @Temporal(TemporalType.DATE)
    private Date certificate_date;

    @Column(name = "certificate_number")
    private String certificate_number = "";

    @Column(name = "water_damage")
    private boolean water_damage = false;

    @Column(name = "major_modification")
    private boolean major_damage = false;

    @Column(name = "imported_second_hand")
    private boolean imported_second_hand = false;

    @Column(name = "formulario_actualizado")
    private boolean formulario_actualizado = false;

    
    @ManyToOne
    @JoinColumn(name = "id_cliente_proveedor")
    private Cliente clienteProveedor = new Cliente();
    
    @Column(name = "transferencia_impresa")
    private boolean transferenciaImpresa = false;
    
    @Column(name = "disposal_impresa")
    private boolean disposalImpresa = false;
    
    @Column(name = "monto_load")
    private float load=0;
    
    @Column(name = "monto_warranty")
    private float warranty=0;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getTransmision() {
        return transmision;
    }

    public void setTransmision(int transmision) {
        this.transmision = transmision;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public Tipo_auto getTipo_body() {
        return tipo_body;
    }

    public void setTipo_body(Tipo_auto tipo_body) {
        this.tipo_body = tipo_body;
    }

    public String getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(String kilometraje) {
        this.kilometraje = kilometraje;
    }

    public int getAire_acondicionado() {
        return aire_acondicionado;
    }

    public void setAire_acondicionado(int aire_acondicionado) {
        this.aire_acondicionado = aire_acondicionado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNro_motor() {
        return nro_motor;
    }

    public void setNro_motor(String nro_motor) {
        this.nro_motor = nro_motor;
    }

    public String getNro_chasis() {
        return nro_chasis;
    }

    public void setNro_chasis(String nro_chasis) {
        this.nro_chasis = nro_chasis;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public Tipo_combustible getTipo_combustible() {
        return tipo_combustible;
    }

    public void setTipo_combustible(Tipo_combustible tipo_combustible) {
        this.tipo_combustible = tipo_combustible;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auto other = (Auto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Auto{" + "id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", color=" + color + ", transmision=" + transmision + ", a\u00f1o=" + año + ", tipo_body=" + tipo_body + ", kilometraje=" + kilometraje + ", aire_acondicionado=" + aire_acondicionado + ", observacion=" + observacion + ", nro_motor=" + nro_motor + ", nro_chasis=" + nro_chasis + ", VIN=" + VIN + ", tipo_combustible=" + tipo_combustible + ", sucursal=" + sucursal + ", disponible=" + disponible + ", estado=" + estado + ", stock=" + stock + ", grabado=" + grabado + ", drivetrain=" + drivetrain + ", descripcion=" + descripcion + ", titulo=" + titulo + ", usoDescripcionEspecifica=" + usoDescripcionEspecifica + ", cilindros=" + cilindros + ", motor=" + motor + ", puertas=" + puertas + ", asientos=" + asientos + ", buildDate=" + buildDate + ", compliance=" + compliance + ", marcado_vendido=" + marcado_vendido + ", label=" + label + ", rego=" + rego + ", fecha_vencimiento_rego=" + fecha_vencimiento_rego + ", state=" + state + ", tipoCompra=" + tipoCompra + ", valor_compra=" + valor_compra + ", fecha_compra=" + fecha_compra + ", written_off=" + written_off + ", certificate_date=" + certificate_date + ", certificate_number=" + certificate_number + ", water_damage=" + water_damage + ", major_damage=" + major_damage + ", imported_second_hand=" + imported_second_hand + ", formulario_actualizado=" + formulario_actualizado + ", clienteProveedor=" + clienteProveedor + ", transferenciaImpresa=" + transferenciaImpresa + ", disposalImpresa=" + disposalImpresa + ", load=" + load + ", warranty=" + warranty + '}';
    }

    

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public String getGrabado() {
        return grabado;
    }

    public void setGrabado(String grabado) {
        this.grabado = grabado;
    }

    public Drivetrain getDrivetrain() {
        return drivetrain;
    }

    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isUsoDescripcionEspecifica() {
        return usoDescripcionEspecifica;
    }

    public void setUsoDescripcionEspecifica(boolean usoDescripcionEspecifica) {
        this.usoDescripcionEspecifica = usoDescripcionEspecifica;
    }

    public String getCilindros() {
        return cilindros;
    }

    public void setCilindros(String cilindros) {
        this.cilindros = cilindros;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public int getPuertas() {
        return puertas;
    }

    public void setPuertas(int puertas) {
        this.puertas = puertas;
    }

    public int getAsientos() {
        return asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public Date getCompliance() {
        return compliance;
    }

    public void setCompliance(Date compliance) {
        this.compliance = compliance;
    }

    public boolean isMarcado_vendido() {
        return marcado_vendido;
    }

    public void setMarcado_vendido(boolean marcado_vendido) {
        this.marcado_vendido = marcado_vendido;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Date getFecha_vencimiento_rego() {
        return fecha_vencimiento_rego;
    }

    public void setFecha_vencimiento_rego(Date fecha_vencimiento_rego) {
        this.fecha_vencimiento_rego = fecha_vencimiento_rego;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public float getValor_compra() {
        return valor_compra;
    }

    public void setValor_compra(float valor_compra) {
        this.valor_compra = valor_compra;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public boolean isWritten_off() {
        return written_off;
    }

    public void setWritten_off(boolean written_off) {
        this.written_off = written_off;
    }

    public Date getCertificate_date() {
        return certificate_date;
    }

    public void setCertificate_date(Date certificate_date) {
        this.certificate_date = certificate_date;
    }

    public String getCertificate_number() {
        return certificate_number;
    }

    public void setCertificate_number(String certificate_number) {
        this.certificate_number = certificate_number;
    }

    public boolean isWater_damage() {
        return water_damage;
    }

    public void setWater_damage(boolean water_damage) {
        this.water_damage = water_damage;
    }

    public boolean isMajor_damage() {
        return major_damage;
    }

    public void setMajor_damage(boolean major_damage) {
        this.major_damage = major_damage;
    }

    public boolean isImported_second_hand() {
        return imported_second_hand;
    }

    public void setImported_second_hand(boolean imported_second_hand) {
        this.imported_second_hand = imported_second_hand;
    }

    public boolean isFormulario_actualizado() {
        return formulario_actualizado;
    }

    public void setFormulario_actualizado(boolean formulario_actualizado) {
        this.formulario_actualizado = formulario_actualizado;
    }

    public TipoCompra getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(TipoCompra tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    public Cliente getClienteProveedor() {
        return clienteProveedor;
    }

    public void setClienteProveedor(Cliente clienteProveedor) {
        this.clienteProveedor = clienteProveedor;
    }

    public boolean isTransferenciaImpresa() {
        return transferenciaImpresa;
    }

    public void setTransferenciaImpresa(boolean transferenciaImpresa) {
        this.transferenciaImpresa = transferenciaImpresa;
    }

    public boolean isDisposalImpresa() {
        return disposalImpresa;
    }

    public void setDisposalImpresa(boolean disposalImpresa) {
        this.disposalImpresa = disposalImpresa;
    }

    public float getLoad() {
        return load;
    }

    public void setLoad(float load) {
        this.load = load;
    }

    public float getWarranty() {
        return warranty;
    }

    public void setWarranty(float warranty) {
        this.warranty = warranty;
    }

}
