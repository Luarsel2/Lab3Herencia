package controller;

import model.*;
import java.util.*;

public class EmpresaServicios {
    private List<OrdenDeServicio> ordenes = new ArrayList<>();
    private List<Cuadrilla> cuadrillas = new ArrayList<>();
    private List<Jornada> jornadas = new ArrayList<>();

    private double ingresoPorRegistro = 0.0;
    private double ingresoPorVisitasAdicionales = 0.0;

    public EmpresaServicios() {
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {

        // Cuadrillas iniciales (mínimo 3)

        cuadrillas.add(new Cuadrilla("CUA-01", 8.0, true));
        cuadrillas.add(new Cuadrilla("CUA-02", 6.0, false));
        cuadrillas.add(new Cuadrilla("CUA-03", 10.0, false));


        // Órdenes iniciales (mínimo 2 por categoría)

        registrarOrden(new Diagnostico("ORD-101", "Juan Pérez", "Zona 10", 1.0, Zona.METROPOLITANA));
        registrarOrden(new Diagnostico("ORD-102", "Maria Lopez", "Carretera a El Salvador", 1.5, Zona.PERIFERICA));

        registrarOrden(new Instalacion("ORD-201", "Carlos Gómez", "Zona 14", 2.5, Zona.METROPOLITANA, 5));
        registrarOrden(new Instalacion("ORD-202", "Ana Torres", "Mixco", 4.0, Zona.PERIFERICA, 2));

        registrarOrden(new InstalacionElectrica("ORD-301", "Empresa ABC", "Zona 4", 3.0, Zona.METROPOLITANA, 4, 2000.0));
        registrarOrden(new InstalacionElectrica("ORD-302", "Hotel Sol", "Amatitlán", 5.0, Zona.PERIFERICA, 6, 3500.0));

        registrarOrden(new MantenimientoAire("ORD-401", "Pedro Pablo", "Zona 15", 2.0, Zona.METROPOLITANA, 12000));
        registrarOrden(new MantenimientoAire("ORD-402", "Lucia Méndez", "Zona 9", 3.0, Zona.METROPOLITANA, 24000));
    }

    public void registrarOrden(OrdenDeServicio orden) {
        if (buscarOrden(orden.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una orden registrada con el código: " + orden.getCodigo());
        }
        ordenes.add(orden);
        ingresoPorRegistro += orden.calcularCostoTotal();
    }

    public void registrarCuadrilla(Cuadrilla cuadrilla) {
        if (buscarCuadrilla(cuadrilla.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una cuadrilla con el código: " + cuadrilla.getCodigo());
        }
        cuadrillas.add(cuadrilla);
    }

    public OrdenDeServicio buscarOrden(String codigo) {
        for (OrdenDeServicio o : ordenes) {
            if (o.getCodigo().equalsIgnoreCase(codigo)) return o;
        }
        return null;
    }

    public Cuadrilla buscarCuadrilla(String codigo) {
        for (Cuadrilla c : cuadrillas) {
            if (c.getCodigo().equalsIgnoreCase(codigo)) return c;
        }
        return null;
    }

    public Jornada crearJornada(String codigoCuadrilla) {
        Cuadrilla c = buscarCuadrilla(codigoCuadrilla);
        if (c == null) throw new IllegalArgumentException("Cuadrilla no encontrada.");

        for (Jornada j : jornadas) {
            if (j.getCuadrilla().getCodigo().equalsIgnoreCase(codigoCuadrilla) && j.getEstado() != EstadoJornada.CERRADA) {
                throw new IllegalArgumentException("La cuadrilla ya tiene una jornada activa.");
            }
        }
        Jornada nueva = new Jornada(c);
        jornadas.add(nueva);
        return nueva;
    }

    public void asignarOrdenAJornada(Jornada jornada, String codigoOrden) {
        OrdenDeServicio orden = buscarOrden(codigoOrden);
        if (orden == null) throw new IllegalArgumentException("Orden no encontrada.");

        String[] error = new String[1];
        if (!jornada.puedeAsignarOrden(orden, error)) {
            throw new IllegalArgumentException("No se pudo asignar: " + error[0]);
        }
        jornada.agregarOrden(orden);
    }

    public void iniciarJornada(Jornada jornada) {
        if (jornada.getOrdenesAsignadas().isEmpty()) {
            throw new IllegalArgumentException("No se puede iniciar una jornada sin órdenes asignadas.");
        }
        jornada.setEstado(EstadoJornada.EN_ATENCION);
        for (OrdenDeServicio o : jornada.getOrdenesAsignadas()) {
            o.setEstado(EstadoOrden.EN_ATENCION);
            o.registrarVisita();
            if (o.getVisitasRealizadas() > 1) {
                ingresoPorVisitasAdicionales += 40.00;
            }
        }
    }

    public void registrarResultadoVisita(Jornada jornada, String codigoOrden, boolean completada) {
        if (jornada.getEstado() != EstadoJornada.EN_ATENCION) {
            throw new IllegalArgumentException("La jornada debe estar en atención para registrar resultados.");
        }
        OrdenDeServicio orden = null;
        for (OrdenDeServicio o : jornada.getOrdenesAsignadas()) {
            if (o.getCodigo().equalsIgnoreCase(codigoOrden)) {
                orden = o;
                break;
            }
        }
        if (orden == null) throw new IllegalArgumentException("La orden no pertenece a esta jornada.");

        if (completada) {
            orden.setEstado(EstadoOrden.COMPLETADA);
        } else {
            if (orden.permiteVisitaAdicional()) {
                orden.setEstado(EstadoOrden.PENDIENTE);
            } else {
                orden.setEstado(EstadoOrden.CANCELADA);
            }
        }

        boolean todasProcesadas = true;
        for (OrdenDeServicio o : jornada.getOrdenesAsignadas()) {
            if (o.getEstado() == EstadoOrden.EN_ATENCION) {
                todasProcesadas = false;
                break;
            }
        }
        if (todasProcesadas) {
            jornada.setEstado(EstadoJornada.CERRADA);
        }
    }

    // reportes y metricas
    public void mostrarReporteOrdenes() {
        System.out.println("\n--- REPORTE DE ÓRDENES POR CATEGORÍA Y ESTADO ---");
        Map<String, Map<EstadoOrden, Integer>> resumen = new LinkedHashMap<>();

        for (OrdenDeServicio o : ordenes) {
            String cat = o.getNombreCategoria();
            resumen.putIfAbsent(cat, new HashMap<>());
            Map<EstadoOrden, Integer> conteo = resumen.get(cat);
            conteo.put(o.getEstado(), conteo.getOrDefault(o.getEstado(), 0) + 1);
        }

        resumen.forEach((cat, mapa) -> {
            System.out.println("Categoría: " + cat);
            for (EstadoOrden est : EstadoOrden.values()) {
                System.out.println("   - " + est + ": " + mapa.getOrDefault(est, 0));
            }
        });
    }

    public void mostrarReporteIngresos() {
        System.out.println("\n--- REPORTE DE INGRESOS ---");
        System.out.printf("Ingreso por Registros: Q%.2f\n", ingresoPorRegistro);
        System.out.printf("Ingreso por Visitas Adicionales: Q%.2f\n", ingresoPorVisitasAdicionales);
        System.out.printf("INGRESO TOTAL GENERAL: Q%.2f\n", (ingresoPorRegistro + ingresoPorVisitasAdicionales));

        System.out.println("\nIngreso por Categoría (Registros):");
        Map<String, Double> porCat = new LinkedHashMap<>();
        for (OrdenDeServicio o : ordenes) {
            porCat.put(o.getNombreCategoria(), porCat.getOrDefault(o.getNombreCategoria(), 0.0) + o.calcularCostoTotal());
        }
        porCat.forEach((cat, monto) -> System.out.printf("   - %s: Q%.2f\n", cat, monto));
    }

    public void mostrarReporteJornadasAbiertas() {
        System.out.println("\n--- JORNADAS NO CERRADAS ---");
        boolean hay = false;
        for (Jornada j : jornadas) {
            if (j.getEstado() != EstadoJornada.CERRADA) {
                hay = true;
                System.out.printf("Cuadrilla: %s | Estado: %s | Horas Asignadas: %.2fh | Disponibles: %.2fh\n",
                        j.getCuadrilla().getCodigo(), j.getEstado(), j.getHorasTotalesAsignadas(), j.getCapacidadDisponible());
            }
        }
        if (!hay) System.out.println("No hay jornadas abiertas en este momento.");
    }

    public List<OrdenDeServicio> getOrdenes() { return ordenes; }
    public List<Cuadrilla> getCuadrillas() { return cuadrillas; }
    public List<Jornada> getJornadas() { return jornadas; }
}