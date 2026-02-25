package org.example;
import java.util.Scanner;

public class TresEnRaya {

    static class Tablero {
        String[][] casillas;
        int numCasillas = 3;

        public Tablero() {
            casillas = new String[numCasillas][numCasillas];
            for (int i = 0; i < numCasillas; i++)
                for (int j = 0; j < numCasillas; j++)
                    casillas[i][j] = "   ";
        }

        public void imprimirTablero() {
            for (int fila = 0; fila < numCasillas; fila++) {
                System.out.print("|");
                for (int columna = 0; columna < numCasillas; columna++)
                    System.out.print(casillas[fila][columna] + "|");
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
                if (!casillas[0][columna].equals("   ")) {
                    String simbolo = casillas[0][columna];
                    boolean ganador = true;
                    for (int fila = 0; fila < numCasillas; fila++)
                        if (!casillas[fila][columna].equals(simbolo))
                            ganador = false;
                    if (ganador) return true;
                }
            }
            return false;
        }

        public boolean hayGanadorHorizontal() {
            for (int fila = 0; fila < numCasillas; fila++) {
                if (!casillas[fila][0].equals("   ")) {
                    String simbolo = casillas[fila][0];
                    boolean ganador = true;
                    for (int columna = 0; columna < numCasillas; columna++)
                        if (!casillas[fila][columna].equals(simbolo))
                            ganador = false;
                    if (ganador) return true;
                }
            }
            return false;
        }

        public boolean hayGanadorDiagonalDerecha() {
            if (!casillas[0][0].equals("   ")) {
                String simbolo = casillas[0][0];
                boolean ganador = true;
                for (int i = 0; i < numCasillas; i++)
                    if (!casillas[i][i].equals(simbolo))
                        ganador = false;
                if (ganador) return true;
            }
            return false;
        }

        public boolean hayGanadorDiagonalIzquierda() {
            if (!casillas[0][numCasillas - 1].equals("   ")) {
                String simbolo = casillas[0][numCasillas - 1];
                boolean ganador = true;
                for (int fila = 0; fila < numCasillas; fila++)
                    if (!casillas[fila][numCasillas - 1 - fila].equals(simbolo))
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
        Tablero tablero;
        String turno;
        Scanner scanner;

        public Juego() {
            tablero = new Tablero();
            turno = "X";
            scanner = new Scanner(System.in);
        }

        public void cambiarTurno() {
            turno = turno.equals("X") ? "O" : "X";
        }

        public void jugar() {
            while (tablero.hayCasillasVacias() && !tablero.hayGanador()) {
                tablero.imprimirTablero();
                System.out.println("Turno del jugador " + turno);

                int fila, columna;
                try {
                    System.out.print("Indica la fila: ");
                    fila = Integer.parseInt(scanner.nextLine());
                    System.out.print("Indica la columna: ");
                    columna = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("¡Error! Tienes que poner un número entre 0 y "
                            + (tablero.numCasillas - 1));
                    continue;
                }

                if (tablero.ponerFicha(fila, columna, turno)) {
                    if (tablero.hayGanador()) {
                        tablero.imprimirTablero();
                        System.out.println("¡Felicidades! El jugador " + turno + " ha ganado!!!!");
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
        new Juego().jugar();
    }
}