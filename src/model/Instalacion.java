package model;

public class Instalacion extends OrdenDeServicio {

    // ATRIBUTOS
    private final int cantidadPuntos;

    // CONSTRUCTOR
    public Instalacion(String codigo, String nombreCliente, String direccion, double horasEstimadas,Zona zona, int cantidadPuntos) {

        super(codigo, nombreCliente, direccion, horasEstimadas, zona);

        if (cantidadPuntos <= 0) {
            throw new IllegalArgumentException(
                "La cantidad de puntos debe ser mayor que cero."
            );
        }

        this.cantidadPuntos = cantidadPuntos;
    }

    // METODOS
    public int getCantidadPuntos() {
        return cantidadPuntos;
    }

    public double calcularHorasFacturables() {
        double horasPorPuntos = cantidadPuntos * 0.75;

        return Math.max(getHorasEstimadas(), horasPorPuntos);
    }

    @Override
    public double calcularTotal() {
        double horasACobrar = Math.ceil(calcularHorasFacturables());

        return getTarifaBase() + horasACobrar * 90;
    }

    @Override
    public int calcularDiasAtencion() {
        if (getZona() == Zona.METROPOLITANA) {
            return 3;
        }

        return 5;
    }

    @Override
    public int getMaximoVisitas() {
        return 3;
    }

    @Override
    public String getCategoria() {
        return "Instalacion";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Puntos: " + cantidadPuntos;
    }
}