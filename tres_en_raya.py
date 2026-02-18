class Tablero():
    def __init__(self):
        self.num_casillas=3
        self.casillas=[["   " for x in range(0, self.num_casillas)] for y in range(0, self.num_casillas)]

    def get_casilla(self, fila, columna):
        return self.casillas[fila][columna]
    
    def get_num_casillas(self):
        return self.num_casillas


    def imprimir_tablero(self):
        for fila in range(0, self.num_casillas):
            print("|", end="")
            for columna in range(0,self.num_casillas):
                print(f"{self.casillas[fila][columna]}", end="|")
            print(" ")
            print(("-" * 13))

    def poner_ficha(self, fila, columna, simbolo):
        if fila>=0 and fila<self.num_casillas and columna>=0 and columna<self.num_casillas:
            if self.casillas[fila][columna]=="   ":
                self.casillas[fila][columna]=(f" {simbolo} ")
                return True
            else:
                print("¡Esa casilla ya está ocupada! Elige otra.")
                return False
        print(f"¡Error! El número debe estar entre 0 y {self.get_num_casillas()-1}.")
        return False

    def hay_casillas_vacias(self):
        for fila in range (0,self.num_casillas):
            for columna in range (0,self.num_casillas):
                if self.casillas[fila][columna]=="   ":
                    return True
        return False
    def hay_ganador_vertical(self):
        ganador_columna=False
        for columna in range(0, self.get_num_casillas()):
            if self.get_casilla(0, columna)!="   ":
                simbolo=self.get_casilla(0, columna)
                ganador_columna=True
                for fila in range(0, self.get_num_casillas()):
                    if self.get_casilla(fila,columna)!= simbolo: 
                        ganador_columna=False
                if ganador_columna==True:
                    return ganador_columna
        return False
    
    def hay_ganador_horizontal(self):
        ganador_fila=False
        for fila in range(0, self.get_num_casillas()):
            if self.get_casilla(fila,0)!="   ":
                simbolo=self.get_casilla(fila,0)
                ganador_fila=True
                for columna in range(0, self.get_num_casillas()):
                    if self.get_casilla(fila, columna)!= simbolo: 
                        ganador_fila=False
                if ganador_fila==True:
                    return ganador_fila
        return False
    
    def hay_ganador_diagonal_derecha(self):
        ganador_diagonal_dere=False
        if self.get_casilla(0,0)!="   ":
            simbolo=self.get_casilla(0,0)
            ganador_diagonal_dere=True
            for diagonal in range(0, self.get_num_casillas()):
                if self.get_casilla(diagonal,diagonal)!= simbolo: 
                    ganador_diagonal_dere=False
            if ganador_diagonal_dere==True:
                return ganador_diagonal_dere
        return False
    
    def hay_ganador_diagonal_izquierda(self):
        ganador_diagonal_izq=False
        if self.get_casilla(0,self.get_num_casillas()-1)!="   ":
            simbolo=self.get_casilla(0,self.get_num_casillas()-1)
            ganador_diagonal_izq=True
            for fila in range(0, self.get_num_casillas()):
                if self.get_casilla(fila, self.get_num_casillas()-1-fila)!= simbolo: 
                    ganador_diagonal_izq=False
            if ganador_diagonal_izq==True:
                return ganador_diagonal_izq
        return False
    
    def hay_ganador(self):
        return self.hay_ganador_horizontal() or self.hay_ganador_diagonal_derecha() or self.hay_ganador_vertical() or self.hay_ganador_diagonal_izquierda()
    

class Juego():
    def __init__(self):
        self.tablero = Tablero()  # El juego "tiene" un tablero
        self.turno ="X"          # Empezamos siempre con las X
    
    def cambiar_turno(self):
        # Si el turno actual es X, pasa a ser O, y viceversa
        if self.turno =="X":
            self.turno ="O"
        else:
            self.turno ="X"
     

    def jugar(self):
        while self.tablero.hay_casillas_vacias() and not self.tablero.hay_ganador():

            self.tablero.imprimir_tablero()
            print(f"Turno del jugador {self.turno}")
            fila=input("Indica la fila: ")
            columna=input("Indica la columna: ")
            if fila==""or columna=="":
                print(f"Error! tienes que poner un número entre 0 y {self.tablero.get_num_casillas()-1} ")
            else:
                fila_num=int(fila)
                columna_num=int(columna)
                self.tablero.poner_ficha(fila_num,columna_num, self.turno)
            
            if self.tablero.hay_ganador():
                self.tablero.imprimir_tablero()
                print(f"¡Felicidades! El jugador {self.turno} ha ganado!!!!")
                break

            if not self.tablero.hay_casillas_vacias():
                self.tablero.imprimir_tablero()
                print("¡Es un empate!")
                break
            self.cambiar_turno()

juego=Juego()
juego.jugar()
