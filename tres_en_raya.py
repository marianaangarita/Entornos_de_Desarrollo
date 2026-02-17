class Tablero():
    def __init__(self):
        self.num_casillas=3
        self.casillas=[["   " for x in range(0, self.num_casillas)] for y in range(0, self.num_casillas)]

    def imprimir_tablero(self):
        for fila in range(0, self.num_casillas):
            print("|", end="")
            for columna in range(0,self.num_casillas):
                print(f"{self.casillas[fila][columna]}", end="|")
            print(" ")
            print(("-" * 13))

    def poner_ficha(self, fila, columna, simbolo):
        if self.casillas[fila][columna]=="   ":
            self.casillas[fila][columna]=(f" {simbolo} ")
            return True
        else:
            return False
    def hay_casillas_vacias(self):
        for fila in range (0,self.num_casillas):
            for columna in range (0,self.num_casillas):
                if self.casillas[fila][columna]=="   ":
                    return True
        return False

tablero=Tablero()
tablero.poner_ficha(0,0,"X") 
tablero.imprimir_tablero()   


class Juego():
    def __init__(self):
        self.tablero = Tablero()  # El juego "tiene" un tablero
        self.turno =" X "          # Empezamos siempre con las X
    
    def cambiar_turno(self):
        # Si el turno actual es X, pasa a ser O, y viceversa
        if self.turno ==" X ":
            self.turno =" O "
        else:
            self.turno =" X "
     
    def hay_ganador_vertical(self):
        ganador_columna=False
        for columna in range(0, self.tablero.num_casillas):
            if self.tablero.casillas[0][columna]!="   ":
                simbolo=self.tablero.casillas[0][columna]
                ganador_columna=True
                for fila in range(0, self.tablero.num_casillas):
                    if self.tablero.casillas[fila][columna]!= simbolo: 
                        ganador_columna=False
                if ganador_columna==True:
                    return ganador_columna
        return False
    
    def hay_ganador_horizontal(self):
        ganador_fila=False
        for fila in range(0, self.tablero.num_casillas):
            if self.tablero.casillas[fila][0]!="   ":
                simbolo=self.tablero.casillas[fila][0]
                ganador_fila=True
                for columna in range(0, self.tablero.num_casillas):
                    if self.tablero.casillas[fila][columna]!= simbolo: 
                        ganador_fila=False
                if ganador_fila==True:
                    return ganador_fila
        return False
    
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
    
    def hay_ganador_diagonal_izquierda(self):
        ganador_diagonal_izq=False
        if self.tablero.casillas[0][self.tablero.num_casillas-1]!="   ":
            simbolo=self.tablero.casillas[0][self.tablero.num_casillas-1]
            ganador_diagonal_izq=True
            for fila in range(0, self.tablero.num_casillas):
                if self.tablero.casillas[fila][self.tablero.num_casillas-1-fila]!= simbolo: 
                    ganador_diagonal_izq=False
            if ganador_diagonal_izq==True:
                return ganador_diagonal_izq
        return False
    
    def hay_ganador(self):
        return self.hay_ganador_horizontal() or self.hay_ganador_diagonal_derecha() or self.hay_ganador_vertical() or self.hay_ganador_diagonal_izquierda()
    

    def jugar(self):
        while self.tablero.hay_casillas_vacias() and not self.hay_ganador():

            self.tablero.imprimir_tablero()
            print(f"Turno del jugador {self.turno}")
            fila=int(input("Indica la fila: "))
            columna=int(input("Indica la columna: "))
            self.tablero.poner_ficha(fila,columna, self.turno)
            
            if self.hay_ganador():
                self.tablero.imprimir_tablero()
                print(f"¡Felicidades! El jugador {self.turno} ha ganado!!!!")
                break

            if not self.tablero.hay_casillas_vacias():
                self.tablero.imprimir_tablero()
                print("¡Es un empate!")
                break
            self.cambiar_turno()
'''
    
    
    def jugar(self):
        while not self.tablero.hay_ganador() and " " in self.tablero.casillas:
            self.tablero.imprimir_tablero()
            print(f"Turno del jugador {self.turno}")
            # 1. Pedir movimiento (usando la validación que hablamos antes)
            posicion = self.pedir_movimiento()
            
            # 2. Marcar la casilla
            self.tablero.marcar_casilla(posicion, self.turno)
            
            # 3. ¿Alguien ganó después de este movimiento?
            if self.tablero.verificar_ganador():
                self.tablero.imprimir_tablero()
                print(f"¡Felicidades! El jugador {self.turno} ha ganado. 🏆")
                break # Salimos del bucle, el juego termina
            
            # 4. ¿Hay empate? (Tablero lleno y nadie ganó)
            if " " not in self.tablero.casillas:
                self.tablero.imprimir_tablero()
                print("¡Es un empate! 🤝")
                break
                
            # 5. Si no hay ganador ni empate, cambiamos el turno
            self.cambiar_turno()


    def pedir_movimiento(self):
        while True:
            try:
                posicion = int(input(f"Jugador {self.turno}, elige una casilla (0-8): "))
            
                # 1. Validar que el número esté en el rango
                if posicion < 0 or posicion > 8:
                    print("¡Error! El número debe estar entre 0 y 8.")
                # 2. Validar que la casilla esté libre
                elif not self.tablero.espacio_libre(posicion):
                    print("¡Esa casilla ya está ocupada! Elige otra.")
                else:
                    # Si todo está bien, salimos del bucle
                    return posicion
            except ValueError:
                print("Por favor, introduce un número válido.")

    def verificar_ganador(self):
        combinaciones = [
            (0, 1, 2), (3, 4, 5), (6, 7, 8), # Horizontales
            (0, 3, 6), (1, 4, 7), (2, 5, 8), # Verticales
            (0, 4, 8), (2, 4, 6)             # Diagonales
        ]
    
        for a, b, c in combinaciones:
            # Revisamos si las tres posiciones tienen el mismo contenido
            # y que ese contenido no sea un espacio en blanco " "
            if self.casillas[a] == self.casillas[b] == self.casillas[c] != " ":
                return True # ¡Hay un ganador!
            
        return False # Nadie ha ganado todavía

mi_juego = Juego()
mi_juego.jugar()
'''