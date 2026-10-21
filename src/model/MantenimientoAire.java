package model;

public class MantenimientoAire extends OrdenDeServicio {

    // ATRIBUTOS
    private final int capacidadBTU;

    // CONSTRUCTOR
    public MantenimientoAire(String codigo, String nombreCliente,String direccion, double horasEstimadas,Zona zona, int capacidadBTU) {

        super(codigo, nombreCliente, direccion, horasEstimadas, zona);

        if (zona != Zona.METROPOLITANA) {
            throw new IllegalArgumentException(
                "El mantenimiento de aire solo se acepta en zona metropolitana."
            );
        }

        if (capacidadBTU < 9000 || capacidadBTU > 60000) {
            throw new IllegalArgumentException(
                "La capacidad debe estar entre 9000 y 60000 BTU."
            );
        }

        this.capacidadBTU = capacidadBTU;
    }

    // METODOS
    public int getCapacidadBTU() {
        return capacidadBTU;
    }

    @Override
    public double calcularTotal() {
        return getTarifaBase() + getHorasEstimadas() * 110 + 80;
    }

    @Override
    public int calcularDiasAtencion() {
        return 2;
    }

    @Override
    public int getMaximoVisitas() {
        return 1;
    }

    @Override
    public String getCategoria() {
        return "Mantenimiento de aire acondicionado";
    }

    @Override
    public boolean esCompatibleCon(Cuadrilla cuadrilla) {
        return cuadrilla != null
                && cuadrilla.isCertificadaRefrigeracion();
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Capacidad del equipo: " + capacidadBTU + " BTU";
    }
}