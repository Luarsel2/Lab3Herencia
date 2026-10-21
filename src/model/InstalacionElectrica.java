package model;

public class InstalacionElectrica extends Instalacion {
    private double valorMateriales;

    public InstalacionElectrica(String codigo, String cliente, String direccion, double horasEstimadas, Zona zona, int puntos, double valorMateriales) {
        super(codigo, cliente, direccion, horasEstimadas, zona, puntos);
        if (valorMateriales < 0) {
            throw new IllegalArgumentException("El valor de materiales no puede ser negativo.");
        }
        this.valorMateriales = valorMateriales;
    }

    @Override
    public double calcularCostoTotal() {
        double costoBase = super.calcularCostoTotal();
        double certificado = 120.00;
        double garantiaMateriales = valorMateriales * 0.05;
        return costoBase + certificado + garantiaMateriales;
    }

    @Override
    public int calcularDiasEstimados() {
        return super.calcularDiasEstimados() + 2;
    }

    @Override
    public int getVisitasMaximas() {
        return 2;
    }

    @Override
    public String getNombreCategoria() {
        return "Instalación Eléctrica Certificada";
    }

    public double getValorMateriales() { return valorMateriales; }
}