package model;

public class MantenimientoAire extends OrdenDeServicio {
    private int capacidadBTU;

    public MantenimientoAire(String codigo, String cliente, String direccion, double horasEstimadas, Zona zona, int capacidadBTU) {
        super(codigo, cliente, direccion, horasEstimadas, zona);
        if (zona != Zona.METROPOLITANA) {
            throw new IllegalArgumentException("El mantenimiento de aire solo se permite en la zona metropolitana.");
        }
        if (capacidadBTU < 9000 || capacidadBTU > 60000) {
            throw new IllegalArgumentException("La capacidad debe estar entre 9,000 y 60,000 BTU.");
        }
        this.capacidadBTU = capacidadBTU;
    }

    @Override
    public double calcularCostoTotal() {
        double tarifaBase = 100.00;
        return tarifaBase + (horasEstimadas * 110.00) + 80.00; // Q80 por refrigerante
    }

    @Override
    public int calcularDiasEstimados() {
        return 2;
    }

    @Override
    public int getVisitasMaximas() {
        return 1;
    }

    @Override
    public String getNombreCategoria() {
        return "Mantenimiento Aire Acondicionado";
    }

    public int getCapacidadBTU() { return capacidadBTU; }
}