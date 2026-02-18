# Entornos_de_Desarrollo

La lógica la estoy haciendo en python y luego le pedire al chatgpt, que m e lo pase a java y su vesion tkinter.
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