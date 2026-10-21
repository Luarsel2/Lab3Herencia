package model;

public abstract class OrdenDeServicio {
    protected String codigo;
    protected String cliente;
    protected String direccion;
    protected double horasEstimadas;
    protected Zona zona;
    protected EstadoOrden estado;
    protected int visitasRealizadas;

    public OrdenDeServicio(String codigo, String cliente, String direccion, double horasEstimadas, Zona zona) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        if (horasEstimadas <= 0) {
            throw new IllegalArgumentException("Las horas estimadas deben ser mayores a 0.");
        }
        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.horasEstimadas = horasEstimadas;
        this.zona = zona;
        this.estado = EstadoOrden.PENDIENTE;
        this.visitasRealizadas = 0;
    }

    // Métodos Abstractos
    public abstract double calcularCostoTotal();
    public abstract int calcularDiasEstimados();
    public abstract int getVisitasMaximas();
    public abstract String getNombreCategoria();

    // Métodos de Lógica
    public boolean permiteVisitaAdicional() {
        return visitasRealizadas < getVisitasMaximas();
    }

    public void registrarVisita() {
        this.visitasRealizadas++;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public String getCliente() { return cliente; }
    public String getDireccion() { return direccion; }
    public double getHorasEstimadas() { return horasEstimadas; }
    public Zona getZona() { return zona; }
    public EstadoOrden getEstado() { return estado; }
    public void setEstado(EstadoOrden estado) { this.estado = estado; }
    public int getVisitasRealizadas() { return visitasRealizadas; }

    @Override
    public String toString() {
        return String.format("[%s] Cod: %s | Cliente: %s | Zona: %s | Horas: %.2fh | Costo: Q%.2f | Días Est.: %d | Estado: %s | Visitas: %d/%d",
                getNombreCategoria(), codigo, cliente, zona, horasEstimadas, calcularCostoTotal(),
                calcularDiasEstimados(), estado, visitasRealizadas, getVisitasMaximas());
    }
}