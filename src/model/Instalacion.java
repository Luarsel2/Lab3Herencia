package model;

public class Instalacion extends OrdenDeServicio {
    protected int puntos;

    public Instalacion(String codigo, String cliente, String direccion, double horasEstimadas, Zona zona, int puntos) {
        super(codigo, cliente, direccion, horasEstimadas, zona);
        if (puntos <= 0) {
            throw new IllegalArgumentException("La cantidad de puntos a instalar debe ser mayor a 0.");
        }
        this.puntos = puntos;
    }

    @Override
    public double calcularCostoTotal() {
        double tarifaBase = (zona == Zona.METROPOLITANA) ? 100.00 : 175.00;
        double horasPorPuntos = puntos * 0.75;
        double horasFacturables = Math.max(horasEstimadas, horasPorPuntos);
        int horasRedondeadas = (int) Math.ceil(horasFacturables);
        return tarifaBase + (horasRedondeadas * 90.00);
    }

    @Override
    public int calcularDiasEstimados() {
        return (zona == Zona.METROPOLITANA) ? 3 : 5;
    }

    @Override
    public int getVisitasMaximas() {
        return 3;
    }

    @Override
    public String getNombreCategoria() {
        return "Instalación";
    }

    public int getPuntos() { return puntos; }
}