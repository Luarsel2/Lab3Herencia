package model;

public class Cuadrilla {
    private String codigo;
    private double capacidadHoras;
    private boolean certificadaRefrigeracion;

    public Cuadrilla(String codigo, double capacidadHoras, boolean certificadaRefrigeracion) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        if (capacidadHoras <= 0) {
            throw new IllegalArgumentException("La capacidad de horas debe ser mayor a 0.");
        }
        this.codigo = codigo;
        this.capacidadHoras = capacidadHoras;
        this.certificadaRefrigeracion = certificadaRefrigeracion;
    }

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
        return String.format("Cuadrilla: %s | Capacidad: %.2fh | Certificada Refrigeración: %s",
                codigo, capacidadHoras, certificadaRefrigeracion ? "Sí" : "No");
    }
}