este ejercicio de diciionario ha sido relativamente facil de ejecutar primero cre una lista con letras del abecedario, 
luego cree el dicionario y con un bucle for hice que cada clave fuera una letra del dicionario.

python
    for letra in abecedario:
    diccionario[letra]=[]

luego cree otra lista con palabras que empezaran con cada letra del abecedario, y cree un segundo bucle for donde se si la clave del diccionario coincidia con la posicion[0] de la palabra de la lista (su inicial) hiciera un append con el valor del diccionario.

for clave, valor in diccionario.items():
    for letra in lista_palabras:
        if clave==letra[0]:
            valor.append(letra)

print(diccionario)

asi es como hice el diccionario en python y luego lo pasé a java con HasMap.

despues de darle un par de vueltas he simplificado la lógica del diccionario de la siguiente manera, (vi que el condicional es totalmente innecesario)

resultado final python:

diccionario={}

for letra in abecedario:
    diccionario[letra]=[]



for palabra in lista_palabras:
    diccionario[palabra[0]].append(palabra)

print(diccionario)

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
 