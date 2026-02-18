# Entornos_de_Desarrollo

La lógica la estoy haciendo en python y luego le pedire al chatgpt, que m e lo pase a java y su vesion tkinter de java.
Lo que mas me está constando es sacar la logica de hay un ganador primero lo hice con tres metodos diferentes:

def hay_ganador_horizontal(self):
        for fila in range(0, self.tablero.num_casillas):
            if self.tablero.casillas[fila][0]==self.tablero.casillas[fila][1]==self.tablero.casillas[fila][2] and self.tablero.casillas[fila][0]!="   ":
                return True
        return False
    
    def hay_ganador_vertical(self):
        for columna in range(0, self.tablero.num_casillas):
            if self.tablero.casillas[0][columna]==self.tablero.casillas[1][columna]==self.tablero.casillas[2][columna] and self.tablero.casillas[1][columna]!="   ":
                return True
        return False
    
    def hay_ganador_diagonal(self):
        if self.tablero.casillas[0][0]==self.tablero.casillas[1][1]==self.tablero.casillas[2][2] and self.tablero.casillas[1][1]!="   ":
                return True
        if self.tablero.casillas[0][2]==self.tablero.casillas[1][1]==self.tablero.casillas[2][0] and self.tablero.casillas[1][1]!="   ":
                return True
        return False

Pero no es escalable, asi que mejore los metodos asi:
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

he creado getters para encapsular la info de tablero, y me di cuenta que tiene más sentido meter la logica de hay ganador() en la clase tablero no en la clase juego que es donde estaba anteriormente. El método hay_ganador() debe estar en Tablero porque depende únicamente del estado interno de las casillas. De esta forma aplicamos el principio de responsabilidad única y mejoramos la encapsulación, ya que toda la lógica relacionada con el tablero queda dentro de la misma clase.

finalmente el resultado en python es el siguiente:

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
            fila=int(input("Indica la fila: "))
            columna=int(input("Indica la columna: "))
            if fila==""or columna=="":
                print(f"Error! tienes que poner un número entre 0 y {self.tablero.get_num_casillas()-1} ")
            else:
                self.tablero.poner_ficha(fila,columna, self.turno)
            
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


A preguntar sobre la fila y columna solo hago input para pasar la prueba del error si no pone nada que salga un mensaje en lugar de que se me rompa el programa, y si está correcto pasar esos valores a int() (númerico) y meterlos en el método poner_ficha().
