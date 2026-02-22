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

asi es como hice el diccionario en python y luego lo pasé a java con HasMap