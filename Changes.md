# Avances

## Enumeraciones

- Creé Zona con metropolitana y periférica.
- Creé Estado Orden con los estados de una orden.
- Creé Estado Jornada con preparación, iniciada y cerrada.

## Cuadrilla

- Agregué código, capacidad de horas y certificación.
- Validé el código y la capacidad.
- Agregué getters y toString().

## OrdenDeServicio

- Creé la clase abstracta con los datos comunes.
- Agregué validaciones, estados, visitas e ingresos.
- Definí métodos abstractos para costo, días, visitas máximas y categoría.
- Separé el registro con cobro de la carga inicial sin cobro.
- Agregué el cargo de Q.40 desde la segunda visita.

## Diagnostico

- Hereda de Orden De Servicio.
- Limita las horas a un máximo de 1.5.
- Define costo y días según la zona.
- Permite hasta tres visitas.

## Instalacion

- Hereda de Orden De Servicio.
- Agregué la cantidad de puntos.
- Compara las horas estimadas con las horas por puntos.
- Redondea hacia arriba las horas para calcular el cobro.
- Define los días según la zona y permite tres visitas.

## InstalacionElectrica

- Hereda de Instalación.
- Agregué el valor declarado de los materiales.
- Suma Q.120 de certificado e inspección más un 5% sobre el valor de materiales.
- Aumenta en 2 días el tiempo estimado de entrega respecto a Instalación.
- Limita las visitas a un máximo de dos.

## MantenimientoAire

- Hereda de Orden De Servicio.
- Restringe el servicio únicamente a la Zona Metropolitana.
- Valida la capacidad del equipo entre 9,000 y 60,000 BTU.
- Calcula el costo base con horas de trabajo más Q.80 fijos de refrigerante.
- Limita el servicio a una sola visita.

## Jornada

- Agrupa la cuadrilla asignada con sus órdenes de servicio.
- Calcula las horas totales asignadas y la capacidad disponible.
- Valida la asignación de órdenes revisando capacidad de horas y requerimiento de certificación en refrigeración.
- Permite agregar, retirar e iniciar órdenes en la jornada.

## EmpresaServicios (Controlador)

- Organizado dentro del paquete `controller`.
- Gestiona las listas principales de órdenes, cuadrillas y jornadas.
- Contiene la carga inicial de datos (cuadrillas y órdenes de prueba).
- Controla el ciclo de vida de la jornada (iniciar jornada y registrar resultado de visita).
- Calcula los ingresos financieros por registros de órdenes y los cobros de Q.40 por visitas adicionales.
- Genera reportes de resumen por categoría, estado de orden e ingresos generales.

## Main y VistaConsola (Vista)

- Organizado dentro del paquete `view`.
- Implementa un menú interactivo por consola.
- Permite cotizar servicios en tiempo real sin guardar datos ni modificar ingresos.
- Mantiene opciones para registrar nuevas órdenes, registrar cuadrillas y armar/gestionar jornadas.
- Consulta datos de órdenes/cuadrillas y muestra reportes consolidados.