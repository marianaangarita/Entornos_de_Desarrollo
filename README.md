# Entornos_de_Desarrollo

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

Pero no es escalable si el numero de casillas cambia