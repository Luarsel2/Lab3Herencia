package model;

public class Diagnostico extends OrdenDeServicio {

    public Diagnostico(String codigo, String cliente, String direccion, double horasEstimadas, Zona zona) {
        super(codigo, cliente, direccion, horasEstimadas, zona);
        if (horasEstimadas > 1.5) {
            throw new IllegalArgumentException("Un diagnóstico no puede superar 1.5 horas estimadas.");
        }
    }

    @Override
    public double calcularCostoTotal() {
        return (zona == Zona.METROPOLITANA) ? 150.00 : 225.00;
    }

    @Override
    public int calcularDiasEstimados() {
        return (zona == Zona.METROPOLITANA) ? 1 : 2;
    }

    @Override
    public int getVisitasMaximas() {
        return 3;
    }

    @Override
    public String getNombreCategoria() {
        return "Diagnóstico";
    }
}