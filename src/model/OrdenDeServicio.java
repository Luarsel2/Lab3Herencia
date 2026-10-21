package model;

public abstract class OrdenDeServicio {

    // ATRIBUTOS
    private final String codigo;
    private final String nombreCliente;
    private final String direccion;
    private final double horasEstimadas;
    private final Zona zona;

    private EstadoOrden estado;
    private int cantidadVisitas;
    private double ingresoRegistro;
    private double ingresoVisitas;
    private boolean registrada;

    // CONSTRUCTOR
    public OrdenDeServicio(String codigo, String nombreCliente,
                           String direccion, double horasEstimadas,
                           Zona zona) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El codigo no puede estar vacio."
            );
        }

        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre del cliente no puede estar vacio."
            );
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "La direccion no puede estar vacia."
            );
        }

        if (!Double.isFinite(horasEstimadas) || horasEstimadas <= 0) {
            throw new IllegalArgumentException(
                "Las horas estimadas deben ser un numero mayor que cero."
            );
        }

        if (zona == null) {
            throw new IllegalArgumentException(
                "Debes indicar una zona."
            );
        }

        this.codigo = codigo.trim();
        this.nombreCliente = nombreCliente.trim();
        this.direccion = direccion.trim();
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;

        this.estado = EstadoOrden.PENDIENTE;
        this.cantidadVisitas = 0;
        this.ingresoRegistro = 0;
        this.ingresoVisitas = 0;
        this.registrada = false;
    }

    // METODOS ABSTRACTOS
    public abstract double calcularTotal();

    public abstract int calcularDiasAtencion();

    public abstract int getMaximoVisitas();

    public abstract String getCategoria();

    // METODOS COMUNES
    public boolean esCompatibleCon(Cuadrilla cuadrilla) {
        return cuadrilla != null;
    }

    protected double getTarifaBase() {
        if (zona == Zona.METROPOLITANA) {
            return 100;
        }

        return 175;
    }

    public void registrar(boolean cobrar) {
        if (registrada) {
            throw new IllegalStateException(
                "La orden ya esta registrada."
            );
        }

        double monto = 0;

        if (cobrar) {
            monto = calcularTotal();

            if (!Double.isFinite(monto) || monto <= 0) {
                throw new IllegalArgumentException(
                    "El costo calculado no es valido."
                );
            }
        }

        ingresoRegistro = monto;
        registrada = true;
    }

    public void asignar() {
        if (!registrada) {
            throw new IllegalStateException(
                "Primero debes registrar la orden."
            );
        }

        if (estado != EstadoOrden.PENDIENTE) {
            throw new IllegalStateException(
                "Solo se pueden asignar ordenes pendientes."
            );
        }

        if (cantidadVisitas >= getMaximoVisitas()) {
            throw new IllegalStateException(
                "La orden ya agoto sus visitas."
            );
        }

        estado = EstadoOrden.ASIGNADA;
    }

    public void retirarAsignacion() {
        if (estado != EstadoOrden.ASIGNADA) {
            throw new IllegalStateException(
                "La orden no esta asignada."
            );
        }

        estado = EstadoOrden.PENDIENTE;
    }

    public void iniciarVisita() {
        if (estado != EstadoOrden.ASIGNADA) {
            throw new IllegalStateException(
                "La orden debe estar asignada para iniciar la visita."
            );
        }

        if (cantidadVisitas >= getMaximoVisitas()) {
            throw new IllegalStateException(
                "La orden ya agoto sus visitas."
            );
        }

        cantidadVisitas++;

        if (cantidadVisitas > 1) {
            ingresoVisitas += 40;
        }

        estado = EstadoOrden.EN_ATENCION;
    }

    public void registrarResultado(boolean completada) {
        if (estado != EstadoOrden.EN_ATENCION) {
            throw new IllegalStateException(
                "La orden debe estar en atencion."
            );
        }

        if (completada) {
            estado = EstadoOrden.COMPLETADA;
        } else if (cantidadVisitas >= getMaximoVisitas()) {
            estado = EstadoOrden.CANCELADA;
        } else {
            estado = EstadoOrden.PENDIENTE;
        }
    }

    // GETTERS
    public String getCodigo() {
        return codigo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getHorasEstimadas() {
        return horasEstimadas;
    }

    public Zona getZona() {
        return zona;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public int getCantidadVisitas() {
        return cantidadVisitas;
    }

    public double getIngresoRegistro() {
        return ingresoRegistro;
    }

    public double getIngresoVisitas() {
        return ingresoVisitas;
    }

    public boolean isRegistrada() {
        return registrada;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo
                + " Cliente: " + nombreCliente
                + " Direccion: " + direccion
                + " Categoria: " + getCategoria()
                + " Horas estimadas: " + horasEstimadas
                + " Zona: " + zona
                + " Estado: " + estado
                + " Visitas: " + cantidadVisitas;
    }
}