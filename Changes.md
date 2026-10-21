# Avances

## Enumeraciones

- Creé Zona con metropolitana y periférica.
- Creé Estado Orden con los estados de una orden.
- Creé Estado ornada con preparación, iniciada y cerrada.

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
- Define costo ydías según l a zona.
- Permite hasta tres visitas.

## Instalacion

- Hereda de Orden De Servicio.
- Agregué la cantidad de puntos.
- Compara las horas estimadas con las hor as por puntos.
- Redondea hacia arriba las horas para calcular el cobro.
- Define los días según la zona y permite tres visitas.

## Pendiente

- InstalacionElectrica y MantenimientoAire.
- Jornada y SistemaTecniHogar.
- VistaConsola, Controlador y Main.
- Ejecución y pruebas del programa completo.