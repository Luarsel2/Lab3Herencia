package view;

import controller.EmpresaServicios;
import model.*;
import java.util.Scanner;

public class Main {
    private static EmpresaServicios empresa = new EmpresaServicios();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n---------------------------------------------");
            System.out.println("    SISTEMA DE GESTION - PRUEBA    ");
            System.out.println("------------------------------------------------");
            System.out.println("1. Cotizar Servicio");
            System.out.println("2. Registrar Orden de Servicio");
            System.out.println("3. Registrar Cuadrilla");
            System.out.println("4. Gestionar Jornada");
            System.out.println("5. Consultar Órdenes y Cuadrillas");
            System.out.println("6. Generar Reportes");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> cotizarServicio();
                    case 2 -> registrarOrden();
                    case 3 -> registrarCuadrilla();
                    case 4 -> gestionarJornada();
                    case 5 -> consultarDatos();
                    case 6 -> mostrarReportes();
                    case 7 -> {
                        salir = true;
                        System.out.println("¡Gracias por utilizar el sistema!");
                    }
                    default -> System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void cotizarServicio() {
        System.out.println("\n--- COTIZAR SERVICIO ---");
        OrdenDeServicio temporal = pedirDatosOrden("COT-000");
        if (temporal != null) {
            System.out.println("\n>>> RESULTADO DE LA COTIZACIÓN <<<");
            System.out.printf("Costo Estimado Total: Q%.2f\n", temporal.calcularCostoTotal());
            System.out.printf("Tiempo Estimado de Atención: %d días hábiles\n", temporal.calcularDiasEstimados());
            System.out.println("(Nota: La cotización no guarda datos ni altera ingresos)");
        }
    }

    private static void registrarOrden() {
        System.out.println("\n--- REGISTRAR ORDEN DE SERVICIO ---");
        System.out.print("Ingrese código único de orden: ");
        String cod = scanner.nextLine();
        OrdenDeServicio orden = pedirDatosOrden(cod);
        if (orden != null) {
            empresa.registrarOrden(orden);
            System.out.println("¡Orden registrada exitosamente!");
        }
    }

    private static OrdenDeServicio pedirDatosOrden(String cod) {
        System.out.println("Seleccione categoría:");
        System.out.println("1. Diagnóstico");
        System.out.println("2. Instalación");
        System.out.println("3. Instalación Eléctrica Certificada");
        System.out.println("4. Mantenimiento de Aire Acondicionado");
        int cat = Integer.parseInt(scanner.nextLine());

        System.out.print("Nombre del cliente: ");
        String cliente = scanner.nextLine();
        System.out.print("Dirección: ");
        String dir = scanner.nextLine();
        System.out.print("Horas estimadas: ");
        double horas = Double.parseDouble(scanner.nextLine());
        System.out.print("Zona (1. Metropolitana / 2. Periférica): ");
        Zona zona = Integer.parseInt(scanner.nextLine()) == 1 ? Zona.METROPOLITANA : Zona.PERIFERICA;

        return switch (cat) {
            case 1 -> new Diagnostico(cod, cliente, dir, horas, zona);
            case 2 -> {
                System.out.print("Puntos a instalar: ");
                int pts = Integer.parseInt(scanner.nextLine());
                yield new Instalacion(cod, cliente, dir, horas, zona, pts);
            }
            case 3 -> {
                System.out.print("Puntos a instalar: ");
                int pts = Integer.parseInt(scanner.nextLine());
                System.out.print("Valor declarado de materiales: ");
                double mat = Double.parseDouble(scanner.nextLine());
                yield new InstalacionElectrica(cod, cliente, dir, horas, zona, pts, mat);
            }
            case 4 -> {
                System.out.print("Capacidad en BTU (9000 - 60000): ");
                int btu = Integer.parseInt(scanner.nextLine());
                yield new MantenimientoAire(cod, cliente, dir, horas, zona, btu);
            }
            default -> throw new IllegalArgumentException("Categoría inválida.");
        };
    }

    private static void registrarCuadrilla() {
        System.out.println("\n--- REGISTRAR CUADRILLA ---");
        System.out.print("Código de cuadrilla: ");
        String cod = scanner.nextLine();
        System.out.print("Capacidad máxima de horas por jornada: ");
        double cap = Double.parseDouble(scanner.nextLine());
        System.out.print("¿Certificada en refrigeración? (1. Sí / 2. No): ");
        boolean cert = Integer.parseInt(scanner.nextLine()) == 1;

        empresa.registrarCuadrilla(new Cuadrilla(cod, cap, cert));
        System.out.println("¡Cuadrilla registrada con éxito!");
    }

    private static void gestionarJornada() {
        System.out.println("\n--- GESTIÓN DE JORNADAS ---");
        System.out.print("Ingrese código de cuadrilla para armar/gestionar jornada: ");
        String codCuadrilla = scanner.nextLine();

        Jornada jornada = null;
        for (Jornada j : empresa.getJornadas()) {
            if (j.getCuadrilla().getCodigo().equalsIgnoreCase(codCuadrilla) && j.getEstado() != EstadoJornada.CERRADA) {
                jornada = j;
                break;
            }
        }
        if (jornada == null) {
            System.out.println("Creando nueva jornada para la cuadrilla " + codCuadrilla + "...");
            jornada = empresa.crearJornada(codCuadrilla);
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- JORNADA DE CUADRILLA " + codCuadrilla + " (" + jornada.getEstado() + ") ---");
            System.out.println("1. Asignar orden pendiente");
            System.out.println("2. Retirar orden (En preparación)");
            System.out.println("3. Iniciar jornada");
            System.out.println("4. Registrar resultado de visita");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");

            try {
                int subOp = Integer.parseInt(scanner.nextLine());
                switch (subOp) {
                    case 1 -> {
                        System.out.print("Código de la orden a asignar: ");
                        String codO = scanner.nextLine();
                        empresa.asignarOrdenAJornada(jornada, codO);
                        System.out.println("¡Orden asignada correctamente!");
                    }
                    case 2 -> {
                        System.out.print("Código de la orden a retirar: ");
                        String codR = scanner.nextLine();
                        OrdenDeServicio ord = empresa.buscarOrden(codR);
                        if (ord != null && jornada.retirarOrden(ord)) {
                            System.out.println("¡Orden retirada!");
                        } else {
                            System.out.println("No se pudo retirar la orden.");
                        }
                    }
                    case 3 -> {
                        empresa.iniciarJornada(jornada);
                        System.out.println("¡Jornada iniciada! Las órdenes han pasado a atención.");
                    }
                    case 4 -> {
                        System.out.print("Código de orden a registrar resultado: ");
                        String codRes = scanner.nextLine();
                        System.out.print("¿Completada exitosamente? (1. Sí / 2. Visita Fallida): ");
                        boolean exito = Integer.parseInt(scanner.nextLine()) == 1;
                        empresa.registrarResultadoVisita(jornada, codRes, exito);
                        System.out.println("¡Resultado registrado!");
                    }
                    case 5 -> volver = true;
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void consultarDatos() {
        System.out.println("\n=== CONSULTA DE ÓRDENES Y CUADRILLAS ===");
        System.out.println("\n--- ÓRDENES ---");
        empresa.getOrdenes().forEach(System.out::println);

        System.out.println("\n--- CUADRILLAS ---");
        empresa.getCuadrillas().forEach(System.out::println);
}

    private static void mostrarReportes() {
        empresa.mostrarReporteOrdenes();
        empresa.mostrarReporteIngresos();
        empresa.mostrarReporteJornadasAbiertas();
    }
}