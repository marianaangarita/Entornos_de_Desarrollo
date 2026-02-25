
# Documentación: Actividad 1 - Detector de Palíndromos

## 1. Introducción

En este primer módulo de la práctica, se ha implementado un detector de palíndromos. El objetivo de este programa es leer una cadena de texto introducida por el usuario y determinar si se lee igual de izquierda a derecha que de derecha a izquierda.

La base de este ejercicio la realizamos durante una práctica en clase, y me ha servido como punto de partida para asimilar la sintaxis básica de Java, el uso de la clase `Scanner` para la entrada de datos por teclado, y el manejo de objetos de tipo `String`, adaptando la lógica de bucles que ya conocíamos de Python al nuevo lenguaje.

## 2. Explicación del código paso a paso

A continuación, se detalla el funcionamiento del script línea por línea:

* **`import java.util.Scanner;`**: Importamos la clase `Scanner` de la biblioteca estándar de Java, que es necesaria para poder leer lo que el usuario escribe por consola.
* **`Scanner sc= new Scanner(System.in);`**: Instanciamos el objeto `Scanner` y le indicamos que lea desde la entrada estándar del sistema (`System.in`, que es el teclado).
* **`System.out.println("Introduce la palabra: ");`**: Imprimimos un mensaje en la consola para pedirle al usuario que introduzca el texto.
* **`String palabra=sc.nextLine();`**: Guardamos la frase o palabra que el usuario ha escrito en la variable `palabra`.
* **`String palabraIzquierda="";`**: Creamos una variable de texto vacía. Esta variable servirá para almacenar la palabra leída de izquierda a derecha.
* **`for(char c : palabra.toCharArray()) { palabraIzquierda+=c; }`**: Utilizamos un bucle *for-each*. Como en Java no podemos iterar directamente sobre un `String`, usamos `.toCharArray()` para convertir la palabra en un *array* de caracteres. En cada vuelta, añadimos la letra a `palabraIzquierda`.
* **`String palabraDerecha="";`**: Creamos otra variable de texto vacía que almacenará la palabra invertida.
* **`for (int i=palabra.length()-1;i>=0;i--) { ... }`**: Iniciamos un bucle `for` clásico que cuenta hacia atrás. Empieza en la última posición de la palabra (`palabra.length()-1`) y termina cuando llega a 0 (la primera letra).
* **`palabraDerecha+=palabra.charAt(i);`**: Dentro del bucle anterior, usamos `.charAt(i)` para extraer la letra en la posición exacta `i` y la vamos sumando a `palabraDerecha`, dándole así la vuelta completa a la palabra original.
* **`boolean isPalindrome=palabraIzquierda.equals(palabraDerecha);`**: En Java, los *Strings* no se comparan con `==`, sino con el método `.equals()`. Esta línea compara ambas variables y guarda `true` si son exactamente iguales o `false` si no lo son.
* **`if(isPalindrome) { ... } else { ... }`**: Por último, utilizamos un bloque condicional `if-else` para evaluar el resultado y mostrar por pantalla si la palabra introducida es un palíndromo o no.


# Documentación: Actividad 3 - Diccionario con HashMap

## 1. Introducción

En esta actividad, el objetivo ha sido crear un diccionario utilizando la estructura de datos `HashMap` de Java. La finalidad de este diccionario es almacenar las letras del alfabeto como claves, asociando a cada una de ellas una lista de palabras que comiencen por dicha letra.

Para abordar este reto, decidí plantear y resolver primero la lógica algorítmica en Python (lenguaje con el que me siento más familiarizado de evaluaciones anteriores) y, una vez optimizada, realizar la traducción al paradigma y sintaxis estricta de Java.

## 2. Proceso de Desarrollo y Evolución de la Lógica

El desarrollo de este ejercicio ha sido un proceso iterativo donde la lógica ha ido evolucionando para hacerse más eficiente.

### Fase 1: El planteamiento inicial 

Inicialmente, creé una lista con las letras del abecedario y preparé el diccionario asignando a cada letra una lista vacía. Luego, para rellenarlo, planteé una solución basada en bucles anidados: iterar sobre cada clave del diccionario y, por cada clave, iterar sobre toda la lista de palabras para comprobar si la inicial coincidía.

```python
# Planteamiento inicial en Python
for letra in abecedario:
    diccionario[letra] = []

for clave, valor in diccionario.items():
    for palabra in lista_palabras:
        if clave == palabra[0]: # Condicional para buscar la coincidencia
            valor.append(palabra)

print(diccionario)

```

### Fase 2: Optimización de la lógica

Al revisar el código, me di cuenta de que el doble bucle `for` y el condicional `if` eran totalmente innecesarios y hacían el código ineficiente. Si ya conozco la palabra, su primera letra (`palabra[0]`) es directamente la clave del diccionario donde debo guardarla.

Simplifiqué la lógica eliminando el bucle exterior y el condicional, accediendo directamente a la lista correspondiente:

```python
# Planteamiento final optimizado en Python
diccionario = {}

for letra in abecedario:
    diccionario[letra] = []

for palabra in lista_palabras:
    # Accedemos directamente a la clave usando la inicial de la palabra
    diccionario[palabra[0]].append(palabra)

print(diccionario)

```

### Fase 3: Traducción a Java

Con la lógica clara y optimizada, el último paso fue migrar este comportamiento a Java haciendo uso de la interfaz `Map`, la clase `HashMap` y las colecciones `ArrayList`.

## 3. Funcionamiento de la Estructura de Datos (HashMap)

A diferencia de los arrays tradicionales que se indexan mediante números (0, 1, 2...), un `HashMap` es una estructura de datos basada en el concepto de **Clave-Valor (Key-Value)**.

En nuestra implementación en Java, la estructura se ha definido de la siguiente manera:
`Map<Character, List<String>> diccionario = new HashMap<>();`

El funcionamiento paso a paso de esta estructura en el código es el siguiente:

1. **Definición de Tipos:** Forzamos a que las **Claves (Keys)** sean estrictamente caracteres (`Character`, por ejemplo: 'a', 'b', 'c') y que los **Valores (Values)** sean listas de cadenas de texto (`List<String>`).
2. **Inicialización de las Claves:** Recorremos un array con el abecedario. Por cada letra, usamos el método `.put(letra, new ArrayList<>())`. Esto crea las 26 "cajas" (claves) en nuestro `HashMap` y dentro de cada caja mete una lista vacía, lista para recibir palabras.
3. **Poblado de datos (Acceso directo):** * Recorremos nuestra lista de palabras.
* Extraemos la inicial de la palabra evaluada con `palabra.charAt(0)`.
* En lugar de buscar iterativamente, le preguntamos directamente al diccionario si existe esa clave usando `.containsKey()`. Esto añade una capa de robustez al código de Java para evitar errores si la palabra empieza por un símbolo no registrado.


* Si la clave existe, recuperamos la lista asociada a esa letra con `.get(primeraLetra)` y, sobre esa misma lista devuelta, ejecutamos el `.add(palabra)`.



Este enfoque garantiza un rendimiento óptimo, ya que los diccionarios (Tablas Hash) están diseñados para recuperar información casi instantáneamente sin necesidad de recorrer todos sus elementos.



Documentación: Actividad 4 - Ficheros de Registro (.log)
1. Introducción
Esta documentación detalla la solución desarrollada para la Actividad 4 de la práctica de Programación. El objetivo principal de este módulo es la generación y lectura de ficheros de registro utilizando Java.Para lograr la máxima puntuación en este apartado (30%), se han implementado ambas modalidades requeridas por el enunciado : la versión en texto plano (.txt) y la versión serializada (.ser).

Esta práctica ha sido fundamental para entender cómo Java maneja los flujos de entrada y salida (I/O) y cómo aplicar la persistencia de datos, un paso importante en nuestra adaptación desde Python hacia la programación orientada a objetos en Java.

2. Modalidad 1: Ficheros de texto plano (.txt)
Este primer script se encarga de guardar información de forma legible para el usuario.
¿Qué hace?: Crea un archivo llamado prueba.txt, escribe una cadena de texto en él ("Esto es una prueba de fichero txt en java") y, posteriormente, lee el archivo línea por línea para mostrar su contenido por consola.
¿Cómo lo hace?:Para la escritura, utilizamos las clases FileWriter y BufferedWriter. El BufferedWriter es crucial porque optimiza las operaciones de escritura en disco.Para la lectura, usamos FileReader junto con BufferedReader. Mediante un bucle while, leemos el archivo usando el método readLine() hasta que nos devuelve null, lo que indica el final del documento.
Gestión de errores: Todo el bloque está envuelto en un try-catch para capturar la excepción IOException, garantizando que el programa no se cierre de forma abrupta si hay problemas de permisos o si el archivo no se encuentra.

3. Modalidad 2: Ficheros serializados (.ser)

Este segundo script implementa la persistencia de datos mediante la serialización de objetos, lo que permite guardar el estado exacto de un objeto de Java en memoria hacia un archivo físico.

¿Qué hace?: 
Instancia un objeto de una clase personalizada llamada Registro, lo convierte en una secuencia de bytes para guardarlo en prueba.ser y luego realiza el proceso inverso (deserialización) para recuperarlo y leer su contenido.

¿Cómo lo hace?:
Interfaz Serializable:
 El paso más importante ha sido asegurar que la clase Registro implemente la interfaz Serializable para que Java permita su conversión.
 Escritura: Utilizamos FileOutputStream combinado con ObjectOutputStream. El método writeObject() es el encargado de volcar el objeto entero al fichero.Lectura: Empleamos FileInputStream y ObjectInputStream. Con el método readObject() recuperamos los bytes y los transformamos ("casteamos") de nuevo a un objeto de tipo Registro.Gestión de errores: Además de IOException, aquí es obligatorio capturar ClassNotFoundException al leer el archivo, por si el programa intenta cargar un objeto cuya clase ya no existe en el proyecto.
 
 4. Proceso de aprendizaje (Cómo he llegado hasta aquí)
 Venir de Python hace que la gestión de ficheros en Java parezca mucho más verbosa al principio. En Python bastaba con un simple open(), mientras que en Java he tenido que comprender el concepto de los "flujos" (Streams) y cómo se "envuelven" unos objetos dentro de otros (como meter un FileReader dentro de un BufferedReader).El mayor reto ha sido entender el concepto de la serialización. Al principio no comprendía por qué daba error al intentar guardar mi objeto, hasta que asimilé la importancia de la interfaz Serializable y la necesidad de usar ObjectInputStream y ObjectOutputStream tal y como pedía el enunciado. Finalmente, comprender la obligación de cerrar siempre los flujos (.close()) en los bloques try-catch me ha ayudado a crear un código mucho más seguro y robusto
 