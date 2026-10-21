package model;

public class Cuadrilla {

    // Atributos
    private final String codigo;
    private final double capacidadHoras;
    private final boolean certificadaRefrigeracion;

    // Constructorr
    public Cuadrilla(String codigo, double capacidadHoras, boolean certificadaRefrigeracion) {

        if (codigo == null || codigo.trim().isEmpty()) { // comprueba que el codigo no este vacio ni tenga espacio
            throw new IllegalArgumentException(
                "El codigo de la cuadrilla no puede estar vacio."
            );
        }

        if (!Double.isFinite(capacidadHoras) || capacidadHoras <= 0) { // rechazara valores como finitos o NaN
            throw new IllegalArgumentException(
                "La capacidad debe ser un numero mayor que cero."
            );
        }

        this.codigo = codigo.trim();
        this.capacidadHoras = capacidadHoras;
        this.certificadaRefrigeracion = certificadaRefrigeracion;
    }

    // Metodos
    public String getCodigo() {
        return codigo;
    }

    public double getCapacidadHoras() {
        return capacidadHoras;
    }

    public boolean isCertificadaRefrigeracion() {
        return certificadaRefrigeracion;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo
                + " | Capacidad por jornada: " + capacidadHoras + " horas"
                + " | Certificada en refrigeraci  n: "
                + certificadaRefrigeracion;
    }
}