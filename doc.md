# 1º Artículo:

## 3 Patrones:

### Embarazosamente paralelo:
- **Master worker**: Un patrón donde un proceso maestro distribuye tareas independientes a varios trabajadores que las ejecutan en paralelo.
- **Cola de tareas**: Un patrón donde las tareas se colocan en una cola y los trabajadores las toman y ejecutan en paralelo.

### Dependencias separables:
- **Descripción**: Un patrón donde las tareas tienen dependencias claras y separables, permitiendo que se ejecuten en paralelo siempre que se respeten las dependencias.

### Descomposición geométrica:
- **Descripción**: Un patrón donde el problema se divide en subproblemas geométricos más pequeños que pueden ser resueltos en paralelo, típicamente usado en problemas de simulación y gráficos.

# 2º Artículo:

## Programas paralelos 

### Lenguaje de patrones (3 espacios - fases)

#### Finding Concurrence (encontrar la concurrencia duh)
- Ayudan a descomponer el problema en tareas que puedan ser ejecutadas de forma concurrente
- Separar datos para cada actividad
- Orden, dependencias y restricciones entre las tareas

#### Estructurar el algoritmo
- Encontrar Patrón de Algoritmos más adecuado 
- Hay un patrón que me ayuda a seleccionar el patrón

#### Estructuras de soporte
- Estructuras de datos y mecanismos de sincronización que facilitan la implementación del patrón seleccionado
- Herramientas y bibliotecas que proporcionan soporte para la concurrencia

Criterios de paralelismo
    - Orden
    - Tareas
    - Datos

    - Lineal
    - Recursivo


# 3º Artículo:

## Estructura del lenguaje de patrones

### 2 Grupos:

#### Explotar concurrencia
- Identificar oportunidades para ejecutar tareas en paralelo.
- Dividir el trabajo en tareas más pequeñas y manejables.
- Sincronización y comunicación entre tareas concurrentes.
- Ejemplos de patrones: **Master-Worker**, **Pipeline**, **Event-Based Coordination**.

#### Estructura de datos comúnmente usadas
- **Patrón ChooseStructure**: Seleccionar la estructura de datos adecuada para soportar la concurrencia.
- **Colas concurrentes**: Permiten que múltiples tareas accedan y modifiquen la cola de manera segura.
- **Mapas concurrentes**: Facilitan el acceso concurrente a pares clave-valor.
- **Buffers circulares**: Utilizados para la comunicación entre tareas en sistemas de tiempo real.
- **Estructuras inmutables**: Ayudan a evitar problemas de sincronización al no permitir modificaciones después de su creación.
- **Locks y semáforos**: Mecanismos de sincronización para controlar el acceso a recursos compartidos.
- **Atomic variables**: Proporcionan operaciones atómicas para evitar condiciones de carrera.
- **Futures y Promises**: Facilitan la programación asíncrona y la gestión de resultados futuros.
- **Barrier synchronization**: Permite que un conjunto de tareas se sincronicen en un punto específico antes de 








Actor model para calcular --
Serie Leibniz

- Mìnimo de 500 000 terminos
- 100 esclavos
- En el main poner los integrantes