# Práctica de Programación en Java – 1º DAM

## Índice

1. [Introducción General](#introducción-general)
2. [Actividad 1 – Detector de Palíndromos](#actividad-1--detector-de-palíndromos)
3. [Actividad 2 – Juego de Tres en Raya](#actividad-2--juego-de-tres-en-raya)
4. [Actividad 3 – Diccionario con HashMap](#actividad-3--diccionario-con-hashmap)
5. [Actividad 4 – Ficheros de Registro (.log)](#actividad-4--ficheros-de-registro-log)

---

## Introducción General

El objetivo de esta práctica es consolidar y ampliar los conocimientos adquiridos en programación orientada a objetos mediante el lenguaje Java, tomando como punto de partida los conceptos previamente trabajados en Python. A lo largo de cuatro módulos independientes, se aplican principios fundamentales del lenguaje, adaptándonos al cambio de paradigma y profundizando en el uso de estructuras de datos, gestión de ficheros y lógica de programación.

A continuación se documenta el proceso de desarrollo de cada módulo, incluyendo las decisiones tomadas, los problemas encontrados y las soluciones implementadas.

---

## Actividad 1 – Detector de Palíndromos

### 1. Introducción

En este primer módulo se ha implementado un programa capaz de determinar si una cadena de texto introducida por el usuario es un palíndromo, es decir, si se lee igual de izquierda a derecha que de derecha a izquierda.

La base de este ejercicio se trabajó durante una sesión práctica en clase, y ha servido como punto de partida para asimilar la sintaxis básica de Java, el uso de la clase `Scanner` para la entrada de datos por teclado, y el manejo de objetos de tipo `String`, adaptando la lógica de bucles ya conocida de Python al nuevo lenguaje.

### 2. Explicación del código paso a paso

A continuación se detalla el funcionamiento del script línea por línea:

- **`import java.util.Scanner;`**: Se importa la clase `Scanner` de la biblioteca estándar de Java, necesaria para leer lo que el usuario escribe por consola.
- **`Scanner sc = new Scanner(System.in);`**: Se instancia el objeto `Scanner` indicando que lea desde la entrada estándar del sistema (`System.in`, es decir, el teclado).
- **`System.out.println("Introduce la palabra: ");`**: Se imprime un mensaje en la consola para solicitar al usuario que introduzca el texto.
- **`String palabra = sc.nextLine();`**: Se guarda la frase o palabra introducida por el usuario en la variable `palabra`.
- **`String palabraIzquierda = "";`**: Se crea una variable de texto vacía que almacenará la palabra leída de izquierda a derecha.
- **Bucle *for-each*:** Como en Java no es posible iterar directamente sobre un `String`, se utiliza `.toCharArray()` para convertir la palabra en un array de caracteres. En cada iteración, se añade la letra a `palabraIzquierda`.

```java
for (char c : palabra.toCharArray()) {
    palabraIzquierda += c;
}
```

- **`String palabraDerecha = "";`**: Se crea otra variable de texto vacía que almacenará la palabra invertida.
- **Bucle `for` hacia atrás**: Empieza en la última posición de la palabra (`palabra.length() - 1`) y termina al llegar a 0, construyendo la palabra al revés.

```java
for (int i = palabra.length() - 1; i >= 0; i--) {
    palabraDerecha += palabra.charAt(i);
}
```

- **`boolean isPalindrome = palabraIzquierda.equals(palabraDerecha);`**: En Java, los `String` no se comparan con `==`, sino con el método `.equals()`. Esta línea guarda `true` si ambas cadenas son iguales, o `false` si no lo son.
- **Bloque `if-else`**: Se evalúa el resultado y se muestra por pantalla si la palabra introducida es un palíndromo o no.

---

## Actividad 2 – Juego de Tres en Raya

### 1. Introducción y evolución de la lógica

Para desarrollar el clásico juego "Tres en Raya", se decidió plantear primero la lógica algorítmica en Python antes de migrar el código a Java. El objetivo era conseguir una modalidad multijugador por consola, asegurando un diseño escalable y aplicando los principios de la Programación Orientada a Objetos (POO).

El mayor reto inicial fue implementar la detección del ganador. En la primera versión en Python, se comprobaban las posiciones de forma explícita (por ejemplo, `casillas[0][0] == casillas[1][1] == casillas[2][2]`). Sin embargo, este enfoque no era escalable: si el tablero pasara a ser de 4×4, habría que reescribir toda la lógica.

Para solucionarlo, se mejoraron los métodos utilizando bucles `for` que recorren dinámicamente las filas y columnas tomando como referencia el símbolo de la primera casilla, de modo que el código funciona para tableros de cualquier tamaño.

```python
# Planteamiento inicial en Python – comprobación horizontal
def hay_ganador_horizontal(self):
    ganador_fila = False
    for fila in range(0, self.tablero.num_casillas):
        if self.tablero.casillas[fila][0] != "   ":
            simbolo = self.tablero.casillas[fila][0]
            ganador_fila = True
            for columna in range(0, self.tablero.num_casillas):
                if self.tablero.casillas[fila][columna] != simbolo:
                    ganador_fila = False
            if ganador_fila:
                return ganador_fila
    return False
# Planteamiento inicial en Python – comprobación diagonal 
def hay_ganador_diagonal_derecha(self):
    ganador_diagonal_dere=False
    if self.tablero.casillas[0][0]!="   ":
        simbolo=self.tablero.casillas[0][0]
        ganador_diagonal_dere=True
        for diagonal in range(0, self.tablero.num_casillas):
            if self.tablero.casillas[diagonal][diagonal]!= simbolo: 
                ganador_diagonal_dere=False
        if ganador_diagonal_dere==True:
            return ganador_diagonal_dere
    return False
```

De forma análoga se implementaron los métodos `hay_ganador_vertical()`, y `hay_ganador_diagonal_izquierda()`, siguiendo el mismo patrón escalable.

### 2. Encapsulación y Principio de Responsabilidad Única

Durante el desarrollo se detectó un fallo de diseño: los métodos de validación (`hay_ganador()`) estaban dentro de la clase `Juego`. Tras reflexionar sobre los principios de la POO, toda esta lógica se trasladó a la clase `Tablero`, ya que la comprobación del ganador depende únicamente del estado interno de las casillas. Así se aplica el **principio de responsabilidad única** y se mejora la encapsulación mediante *getters*. La clase `Juego` queda encargada únicamente del flujo de la partida, mientras que `Tablero` gestiona su propio estado.

### 3. Gestión de errores en los inputs

A la hora de pedir la fila y la columna al usuario, se implementó inicialmente un condicional `if fila == ""` para evitar que el programa se bloqueara al pulsar Enter sin escribir nada. Sin embargo, el programa seguía fallando si el usuario introducía texto no numérico como "hola".

Para solucionar este problema de forma definitiva en Java, se implementó un bloque `try-catch` que captura la excepción `NumberFormatException`, garantizando que el juego gestione cualquier entrada inválida sin interrumpirse.

### 4. Explicación del script final (versión consola)

El script en Java se divide en dos clases principales:

**Clase `Tablero`**

- **Constructor:** Inicializa una matriz bidimensional `String[][] casillas` de 3×3 rellenada con espacios en blanco `"   "`.
- **`imprimirTablero()`:** Recorre la matriz y la dibuja en la consola utilizando los caracteres `|` y `-` para darle forma de cuadrícula.
- **`ponerFicha(fila, columna, simbolo)`:** Verifica que las coordenadas estén dentro del rango (0 a 2) y que la casilla esté vacía. Si es así, coloca la ficha y devuelve `true`; en caso contrario, avisa del error y devuelve `false`.
- **`hayGanadorHorizontal()`, `hayGanadorVertical()`, `hayGanadorDiagonal()`:** Implementan la lógica escalable mediante bucles `for`, tomando el primer símbolo de cada línea y comprobando si el resto de casillas coinciden.
- **`hayGanador()`:** Agrupa todos los métodos anteriores y devuelve `true` si se cumple alguna condición de victoria.

**Clase `Juego`**

- **Constructor:** Instancia un nuevo `Tablero`, establece el turno inicial a "X" e inicializa el `Scanner`.
- **`jugar()`:** Contiene el bucle principal `while` que mantiene la partida activa mientras haya casillas vacías y no exista un ganador. Imprime el tablero, solicita los inputs, usa `try-catch` con `Integer.parseInt(scanner.nextLine())` para evitar bloqueos, llama a `tablero.ponerFicha()` y evalúa si hay ganador o empate para salir del bucle con `break`. Si la partida continúa, invoca `cambiarTurno()`.

### 5. Implementación avanzada: interfaz gráfica con JOptionPane

Para alcanzar la máxima puntuación propuesta en la rúbrica, se ha desarrollado un archivo adicional en el que se adapta la lógica del juego de consola para funcionar íntegramente a través de ventanas emergentes, utilizando la librería `javax.swing.JOptionPane`.

Durante esta implementación se resolvieron dos problemas técnicos importantes:

**El problema del segundo plano:** Las ventanas de Java tienden a quedarse ocultas detrás del entorno de desarrollo. Se solucionó creando un marco invisible (`JFrame`) configurado con `setAlwaysOnTop(true)`, lo que fuerza a las ventanas a permanecer siempre por encima del resto de aplicaciones.

**El formato del tablero (fuente monoespaciada):** Al pasar a la interfaz gráfica, el tablero dibujado con `|` y `-` se descuadraba por completo. Esto ocurre porque `JOptionPane` utiliza por defecto una fuente proporcional, donde una "X" ocupa más ancho que un espacio. Para solucionarlo, se utilizó la clase `UIManager` para forzar globalmente el uso de una fuente monoespaciada (`new Font("Monospaced", Font.BOLD, 16)`) en todos los mensajes e inputs. Gracias a este ajuste, el tablero encaja matemáticamente dentro de la interfaz gráfica, conservando su aspecto de cuadrícula.

---

## Actividad 3 – Diccionario con HashMap

### 1. Introducción

El objetivo de esta actividad es crear un diccionario utilizando la estructura de datos `HashMap` de Java, donde las letras del alfabeto actúan como claves y cada una tiene asociada una lista de palabras que comienzan por dicha letra.

Al igual que en la actividad anterior, se optó por plantear y resolver primero la lógica en Python y, una vez optimizada, realizar la traducción a la sintaxis estricta de Java.

### 2. Proceso de desarrollo y evolución de la lógica

**Fase 1 – Planteamiento inicial**

En un primer momento se creó una lista con las letras del abecedario, se inicializó el diccionario con una lista vacía por letra y se emplearon bucles anidados para rellenarlo: por cada clave del diccionario, se recorría toda la lista de palabras en busca de coincidencias con la inicial.

```python
# Planteamiento inicial en Python
for letra in abecedario:
    diccionario[letra] = []

for clave, valor in diccionario.items():
    for palabra in lista_palabras:
        if clave == palabra[0]:
            valor.append(palabra)
```

**Fase 2 – Optimización de la lógica**

Al revisar el código, se observó que el doble bucle `for` y el condicional `if` eran innecesarios e ineficientes. Si ya se conoce la palabra, su primera letra (`palabra[0]`) es directamente la clave del diccionario donde debe guardarse. La lógica se simplificó eliminando el bucle exterior y el condicional:

```python
# Planteamiento final optimizado en Python
diccionario = {}

for letra in abecedario:
    diccionario[letra] = []

for palabra in lista_palabras:
    diccionario[palabra[0]].append(palabra)
```

**Fase 3 – Traducción a Java**

Con la lógica clara y optimizada, el último paso fue migrar el comportamiento a Java haciendo uso de la interfaz `Map`, la clase `HashMap` y las colecciones `ArrayList`.

### 3. Funcionamiento de la estructura de datos (HashMap)

A diferencia de los arrays tradicionales, que se indexan mediante números (0, 1, 2...), un `HashMap` es una estructura basada en el concepto de **Clave-Valor**. Como se indica en el README de referencia:

```java
// HashMap<K,V> donde:
// K -> Tipo de datos de la clave
// V -> Tipo de datos del valor
Map<Character, List<String>> diccionario = new HashMap<>();
```

El funcionamiento paso a paso en el código es el siguiente:

1. **Definición de tipos:** Se fuerza a que las claves sean de tipo `Character` (por ejemplo: `'a'`, `'b'`, `'c'`) y los valores sean listas de cadenas (`List<String>`).
2. **Inicialización de las claves:** Se recorre un array con el abecedario y, por cada letra, se usa `.put(letra, new ArrayList<>())` para crear las 26 "entradas" del `HashMap`, cada una con su lista vacía preparada para recibir palabras.
3. **Poblado de datos (acceso directo):** Se recorre la lista de palabras, se extrae la inicial con `palabra.charAt(0)` y, antes de insertar, se comprueba con `.containsKey()` si la clave existe. Esto añade robustez al código por si alguna palabra empieza por un símbolo no registrado. Si la clave existe, se recupera la lista con `.get(primeraLetra)` y se añade la palabra con `.add(palabra)`.

Este enfoque garantiza un rendimiento óptimo, ya que las tablas hash están diseñadas para recuperar y almacenar información de forma casi instantánea, sin necesidad de recorrer todos sus elementos.

---

## Actividad 4 – Ficheros de Registro (.log)

### 1. Introducción

Esta última actividad tiene como objetivo la generación y lectura de ficheros de registro utilizando Java. Para alcanzar la máxima puntuación en este apartado (30%), se han implementado las dos modalidades requeridas por el enunciado: la versión en texto plano (`.txt`) y la versión serializada (`.ser`).

Este módulo ha sido fundamental para comprender cómo Java gestiona los flujos de entrada y salida (I/O) y cómo aplicar la persistencia de datos, un concepto clave en la transición desde Python hacia la programación orientada a objetos en Java.

### 2. Modalidad 1 – Ficheros de texto plano (.txt)

Este primer script se encarga de guardar información de forma legible para el usuario.

**¿Qué hace?** Crea un archivo llamado `prueba.txt`, escribe una cadena de texto en él y, posteriormente, lo lee línea por línea para mostrar su contenido por consola.

**¿Cómo lo hace?** Para la escritura se utilizan las clases `FileWriter` y `BufferedWriter`. El `BufferedWriter` es esencial porque optimiza las operaciones de escritura en disco al reducir el número de accesos físicos al sistema de ficheros. Para la lectura se emplea `FileReader` junto con `BufferedReader`, recorriendo el archivo mediante un bucle `while` con `readLine()` hasta obtener `null`, lo que indica el final del fichero.

```java
// Escritura
FileWriter fw = new FileWriter("prueba.txt");
BufferedWriter bw = new BufferedWriter(fw);
bw.write("Esto es una prueba de fichero txt en java");
bw.close();

// Lectura
FileReader fr = new FileReader("prueba.txt");
BufferedReader br = new BufferedReader(fr);
String line = br.readLine();
while (line != null) {
    System.out.println(line);
    line = br.readLine();
}
br.close();
```

**Gestión de errores:** Todo el bloque está envuelto en un `try-catch` que captura la excepción `IOException`, garantizando que el programa no se cierre de forma abrupta ante problemas de permisos o ficheros inexistentes.

### 3. Modalidad 2 – Ficheros serializados (.ser)

Este segundo script implementa la persistencia de datos mediante la **serialización de objetos**, que permite guardar el estado exacto de un objeto Java en un archivo físico y recuperarlo después.

**¿Qué hace?** Instancia un objeto de una clase personalizada llamada `Registro`, lo convierte en una secuencia de bytes para guardarlo en `prueba.ser` y, a continuación, realiza el proceso inverso (deserialización) para recuperarlo y leer su contenido.

**¿Cómo lo hace?**

- **Interfaz `Serializable`:** El paso más importante es que la clase `Registro` implemente la interfaz `Serializable`, que es lo que le indica a Java que ese objeto puede ser convertido a bytes.
- **Escritura:** Se utiliza `FileOutputStream` combinado con `ObjectOutputStream`. El método `writeObject()` es el encargado de volcar el objeto completo al fichero.
- **Lectura:** Se emplean `FileInputStream` y `ObjectInputStream`. El método `readObject()` recupera los bytes y los transforma de nuevo a un objeto de tipo `Registro` mediante un *cast*.
- **Gestión de errores:** Además de `IOException`, es obligatorio capturar `ClassNotFoundException` al leer el archivo, por si el programa intenta cargar un objeto cuya clase ya no existe en el proyecto.

### 4. Reflexión sobre el proceso de aprendizaje

Venir de Python hace que la gestión de ficheros en Java parezca mucho más verbosa en un primer momento. En Python bastaba con un simple `open()`, mientras que en Java ha sido necesario comprender el concepto de los "flujos" (*Streams*) y cómo se "envuelven" unos objetos dentro de otros (como introducir un `FileReader` dentro de un `BufferedReader`).

El mayor reto ha sido entender la serialización. Inicialmente no quedaba claro por qué se producía un error al intentar guardar el objeto, hasta que se asimiló la importancia de la interfaz `Serializable` y el uso correcto de `ObjectInputStream` y `ObjectOutputStream`, tal y como requería el enunciado. Finalmente, comprender la obligatoriedad de cerrar siempre los flujos con `.close()` dentro de los bloques `try-catch` ha contribuido a desarrollar un código más seguro y robusto.

