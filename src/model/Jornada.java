package model;

import java.util.ArrayList;
import java.util.List;

public class Jornada {
    private Cuadrilla cuadrilla;
    private List<OrdenDeServicio> ordenesAsignadas;
    private EstadoJornada estado;

    public Jornada(Cuadrilla cuadrilla) {
        if (cuadrilla == null) {
            throw new IllegalArgumentException("La cuadrilla no puede ser nula.");
        }
        this.cuadrilla = cuadrilla;
        this.ordenesAsignadas = new ArrayList<>();
        this.estado = EstadoJornada.EN_PREPARACION;
    }

    public double getHorasTotalesAsignadas() {
        double total = 0;
        for (OrdenDeServicio o : ordenesAsignadas) {
            total += o.getHorasEstimadas();
        }
        return total;
    }

    public double getCapacidadDisponible() {
        return cuadrilla.getCapacidadHoras() - getHorasTotalesAsignadas();
    }

    public boolean puedeAsignarOrden(OrdenDeServicio orden, String[] errorOut) {
        if (estado != EstadoJornada.EN_PREPARACION) {
            errorOut[0] = "La jornada no está en preparación.";
            return false;
        }
        if (orden.getEstado() != EstadoOrden.PENDIENTE) {
            errorOut[0] = "La orden no está en estado pendiente.";
            return false;
        }
        if (getHorasTotalesAsignadas() + orden.getHorasEstimadas() > cuadrilla.getCapacidadHoras()) {
            errorOut[0] = "Supera la capacidad en horas de la cuadrilla.";
            return false;
        }
        if (orden instanceof MantenimientoAire && !cuadrilla.isCertificadaRefrigeracion()) {
            errorOut[0] = "Esta orden requiere cuadrilla certificada en refrigeración.";
            return false;
        }
        return true;
    }

    public void agregarOrden(OrdenDeServicio orden) {
        ordenesAsignadas.add(orden);
    }

    public boolean retirarOrden(OrdenDeServicio orden) {
        if (estado == EstadoJornada.EN_PREPARACION && ordenesAsignadas.remove(orden)) {
            orden.setEstado(EstadoOrden.PENDIENTE);
            return true;
        }
        return false;
    }

    public Cuadrilla getCuadrilla() { 
        return cuadrilla; 
    }

    public List<OrdenDeServicio> getOrdenesAsignadas() { 
        return ordenesAsignadas; 
    }

    public EstadoJornada getEstado() { 
        return estado; 
    }

    public void setEstado(EstadoJornada estado) { 
        this.estado = estado; 
    }
}