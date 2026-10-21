package model;

public class InstalacionElectrica extends Instalacion {

    // ATRIBUTOS
    private final double valorMateriales;

    // CONSTRUCTOR
    public InstalacionElectrica(String codigo, String nombreCliente, String direccion, double horasEstimadas,Zona zona, int cantidadPuntos,double valorMateriales) {

        super(codigo, nombreCliente, direccion, horasEstimadas,
              zona, cantidadPuntos);

        if (!Double.isFinite(valorMateriales) || valorMateriales <= 0) {
            throw new IllegalArgumentException(
                "El valor de los materiales debe ser mayor que cero."
            );
        }

        this.valorMateriales = valorMateriales;
    }

    // METODOS
    public double getValorMateriales() {
        return valorMateriales;
    }

    @Override
    public double calcularTotal() {
        double costoInstalacion = super.calcularTotal();
        double garantia = valorMateriales * 0.05;

        return costoInstalacion + 120 + garantia;
    }

    @Override
    public int calcularDiasAtencion() {
        return super.calcularDiasAtencion() + 2;
    }

    @Override
    public int getMaximoVisitas() {
        return 2;
    }

    @Override
    public String getCategoria() {
        return "Instalacion electrica certificada";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Valor de materiales: Q"
                + String.format(
                        java.util.Locale.ROOT,
                        "%.2f",
                        valorMateriales
                );
    }
}