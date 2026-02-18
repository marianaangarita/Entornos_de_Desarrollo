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


A preguntar sobre la fila y columna solo hago input para pasar la prueba del error si no pone nada que salga un mensaje en lugar de que se me rompa el programa, y si está correcto pasar esos valores a int() (númerico) y meterlos en el método poner_ficha(). Pero se me sigue rompiendo el programa si el usuario pone hola en los inputs, asi que nome queda de otra que usar try except, lo hemos dado por encima en clase pero aun no lo habiamos aplicado a un programa

Código en java

import java.util.Scanner;

public class TicTacToe {

    static class Tablero {
        private int numCasillas = 3;
        private String[][] casillas;

        public Tablero() {
            casillas = new String[numCasillas][numCasillas];
            for (int i = 0; i < numCasillas; i++)
                for (int j = 0; j < numCasillas; j++)
                    casillas[i][j] = "   ";
        }

        public String getCasilla(int fila, int columna) {
            return casillas[fila][columna];
        }

        public int getNumCasillas() {
            return numCasillas;
        }

        public void imprimirTablero() {
            for (int fila = 0; fila < numCasillas; fila++) {
                System.out.print("|");
                for (int columna = 0; columna < numCasillas; columna++) {
                    System.out.print(casillas[fila][columna] + "|");
                }
                System.out.println();
                System.out.println("-------------");
            }
        }

        public boolean ponerFicha(int fila, int columna, String simbolo) {
            if (fila >= 0 && fila < numCasillas && columna >= 0 && columna < numCasillas) {
                if (casillas[fila][columna].equals("   ")) {
                    casillas[fila][columna] = " " + simbolo + " ";
                    return true;
                } else {
                    System.out.println("¡Esa casilla ya está ocupada! Elige otra.");
                    return false;
                }
            }
            System.out.println("¡Error! El número debe estar entre 0 y " + (numCasillas - 1) + ".");
            return false;
        }

        public boolean hayCasillasVacias() {
            for (int fila = 0; fila < numCasillas; fila++)
                for (int columna = 0; columna < numCasillas; columna++)
                    if (casillas[fila][columna].equals("   "))
                        return true;
            return false;
        }

        public boolean hayGanadorVertical() {
            for (int columna = 0; columna < numCasillas; columna++) {
                if (!getCasilla(0, columna).equals("   ")) {
                    String simbolo = getCasilla(0, columna);
                    boolean ganador = true;
                    for (int fila = 0; fila < numCasillas; fila++)
                        if (!getCasilla(fila, columna).equals(simbolo))
                            ganador = false;
                    if (ganador) return true;
                }
            }
            return false;
        }

        public boolean hayGanadorHorizontal() {
            for (int fila = 0; fila < numCasillas; fila++) {
                if (!getCasilla(fila, 0).equals("   ")) {
                    String simbolo = getCasilla(fila, 0);
                    boolean ganador = true;
                    for (int columna = 0; columna < numCasillas; columna++)
                        if (!getCasilla(fila, columna).equals(simbolo))
                            ganador = false;
                    if (ganador) return true;
                }
            }
            return false;
        }

        public boolean hayGanadorDiagonalDerecha() {
            if (!getCasilla(0, 0).equals("   ")) {
                String simbolo = getCasilla(0, 0);
                boolean ganador = true;
                for (int i = 0; i < numCasillas; i++)
                    if (!getCasilla(i, i).equals(simbolo))
                        ganador = false;
                if (ganador) return true;
            }
            return false;
        }

        public boolean hayGanadorDiagonalIzquierda() {
            if (!getCasilla(0, numCasillas - 1).equals("   ")) {
                String simbolo = getCasilla(0, numCasillas - 1);
                boolean ganador = true;
                for (int fila = 0; fila < numCasillas; fila++)
                    if (!getCasilla(fila, numCasillas - 1 - fila).equals(simbolo))
                        ganador = false;
                if (ganador) return true;
            }
            return false;
        }

        public boolean hayGanador() {
            return hayGanadorHorizontal() || hayGanadorVertical()
                    || hayGanadorDiagonalDerecha() || hayGanadorDiagonalIzquierda();
        }
    }

    static class Juego {
        private Tablero tablero;
        private String turno;
        private Scanner scanner;

        public Juego() {
            tablero = new Tablero();
            turno = "X";
            scanner = new Scanner(System.in);
        }

        public String getTurno() {
            return turno;
        }

        public void setTurno(String t) {
            turno = t;
        }

        public void cambiarTurno() {
            turno = turno.equals("X") ? "O" : "X";
        }

        public void jugar() {
            while (tablero.hayCasillasVacias() && !tablero.hayGanador()) {
                tablero.imprimirTablero();
                System.out.println("Turno del jugador " + getTurno());

                int fila, columna;
                try {
                    System.out.print("Indica la fila: ");
                    fila = Integer.parseInt(scanner.nextLine());
                    System.out.print("Indica la columna: ");
                    columna = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("¡Error! Tienes que poner un número entre 0 y "
                            + (tablero.getNumCasillas() - 1));
                    continue;
                }

                boolean fichaColocada = tablero.ponerFicha(fila, columna, getTurno());

                if (fichaColocada) {
                    if (tablero.hayGanador()) {
                        tablero.imprimirTablero();
                        System.out.println("¡Felicidades! El jugador " + getTurno() + " ha ganado!!!!");
                        break;
                    }
                    if (!tablero.hayCasillasVacias()) {
                        tablero.imprimirTablero();
                        System.out.println("¡Es un empate!");
                        break;
                    }
                    cambiarTurno();
                }
            }
            scanner.close();
        }
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.jugar();
    }
}
