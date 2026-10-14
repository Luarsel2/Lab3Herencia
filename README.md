# Lab3Herencia
# Análisis

## Problema

TecniHogar necesita organizar sus servicios a domicilio, calcular cobros y asignar órdenes a cuadrillas. El programa será de consola en Java y guardará los datos en memoria.

## Información y operaciones

Se guardarán los datos del cliente, código, dirección, zona, horas, estado y visitas de cada orden. También se guardarán cuadrillas, jornadas e ingresos.

El menú permitirá cotizar, registrar órdenes y cuadrillas, crear jornadas, asignar o retirar órdenes, iniciar jornadas, registrar resultados y consultar reportes.

## Reglas de los servicios

- Diagnóstico: cuesta Q150 en zona metropolitana o Q225 en periférica. Acepta hasta 1.5 horas, tarda 1 o 2 días y permite 3 visitas.
- Instalación: cuesta Q100 o Q175 de base, más Q90 por hora facturable o fracción. Se compara la estimación con puntos × 0.75 y se redondea el mayor hacia arriba. Tarda 3 o 5 días y permite 3 visitas.
- Instalación eléctrica: agrega Q120 y 5 % de materiales al costo de instalación. Tarda dos días más y permite 2 visitas.
- Mantenimiento de aire: cuesta Q100 más Q110 por hora y Q80 de refrigerante. Solo acepta zona metropolitana, equipos de 9,000 a 60,000 BTU y cuadrillas certificadas. Tarda 2 días y permite 1 visita.


## Clases y herencia

OrdenDeServicio será la clase abstracta con los datos comunes. Diagnostico, Instalacion y MantenimientoAire heredarán de ella. InstalacionElectrica heredará de Instalacion para reutilizar sus cálculos y evitar repetir código.

Cada categoría definirá su costo, días, visitas y compatibilidad mediante métodos. Las jornadas usarán esos métodos sin preguntar el tipo del objeto.

Cuadrilla guardará capacidad y certificación. Jornada controlará asignaciones y resultados. SistemaTecniHogar administrará las listas y reportes. VistaConsola leerá y mostrará datos, Controlador manejará el menú y Main iniciará el programa.

## Atributos y constructores

Los atributos serán privados. Se usarán String para textos, double para horas y montos, int para cantidades, boolean para condiciones y ArrayList para las listas.

Los constructores recibirán y validarán los datos iniciales. Las subclases usarán super para inicializar los datos comunes. Las órdenes comenzarán pendientes y con cero visitas e ingresos; las jornadas comenzarán en preparación.

Los getters permitirán consultar información. Los cambios de estado se harán mediante métodos con validaciones. El UML detallará los parámetros, resultados y visibilidad.

## Jornadas y estados

Una orden pasa de pendiente a asignada y luego a atención. Si se retira antes de iniciar, vuelve a pendiente. Si la visita se completa, queda completada; si falla, vuelve a pendiente o se cancela cuando agota sus visitas.

Una jornada pasa de preparación a iniciada y después a cerrada cuando todas sus órdenes tienen resultado.

No se permite iniciar una jornada vacía, exceder la capacidad, asignar una orden a dos jornadas activas ni dar dos jornadas sin cerrar a una cuadrilla. Para la capacidad se usan horas estimadas, no facturables.

## Ingresos y reportes

Registrar una orden nueva cobra el servicio completo. Las visitas se cuentan al iniciar la jornada y desde la segunda se cobran Q40 adicionales.

Cotizar, retirar órdenes y rechazar operaciones no genera ingresos. Una operación inválida no debe cambiar los datos anteriores.

Los reportes mostrarán órdenes por categoría y estado, ingresos por registro y visitas, ingresos por categoría y jornadas abiertas con sus horas y capacidad disponible.

## Carga inicial y validaciones

Habrá al menos dos órdenes pendientes de cada categoría, sin ingresos, y tres cuadrillas: al menos una certificada y una no certificada. Se incluirá una instalación con más horas por puntos que estimadas.

Se rechazarán códigos vacíos o repetidos, números no positivos o fuera de rango y cambios de estado inválidos. Las entradas incorrectas no cerrarán el programa y los montos se mostrarán con dos decimales.

## Crecimiento y pruebas

Para agregar otra categoría se creará una subclase y se actualizará el menú. Las jornadas y reportes utilizarán sus métodos sin cambiar sus reglas generales.

Se probarán cobros, tiempos, capacidad, certificación, cancelaciones y operaciones que no deben alterar ingresos. Cada prueba tendrá entrada, resultado esperado y obtenido.

Se entregará un PDF con análisis, UML y pruebas, además del repositorio público con el código y un README para ejecutarlo.