package model;

public class Diagnostico extends OrdenDeServicio {

    public Diagnostico(String codigo, String nombreCliente,String direccion, double horasEstimadas,Zona zona) {

        super(codigo, nombreCliente, direccion, horasEstimadas, zona);

        if (horasEstimadas > 1.5) {
            throw new IllegalArgumentException(
                "El diagnostico no puede superar 1.5 horas."
            );
        }
    }

    @Override
    public double calcularTotal() {
        if (getZona() == Zona.METROPOLITANA) {
            return 150;
        }

        return 225;
    }

    @Override
    public int calcularDiasAtencion() {
        if (getZona() == Zona.METROPOLITANA) {
            return 1;
        }

        return 2;
    }

    @Override
    public int getMaximoVisitas() {
        return 3;
    }

    @Override
    public String getCategoria() {
        return "Diagnostico";
    }
}