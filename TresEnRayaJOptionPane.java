package org.example;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Font;

public class TresEnRayaJOptionPane {

    static class Tablero {
        String[][] casillas;
        int numCasillas = 3;

        public Tablero() {
            casillas = new String[numCasillas][numCasillas];
            for (int i = 0; i < numCasillas; i++)
                for (int j = 0; j < numCasillas; j++)
                    casillas[i][j] = "   ";
        }

        public String generarTableroString() {
            StringBuilder sb = new StringBuilder();
            for (int fila = 0; fila < numCasillas; fila++) {
                sb.append("|");
                for (int columna = 0; columna < numCasillas; columna++)
                    sb.append(casillas[fila][columna]).append("|");
                sb.append("\n-------------\n");
            }
            return sb.toString();
        }

        public int ponerFicha(int fila, int columna, String simbolo) {
            if (fila >= 0 && fila < numCasillas && columna >= 0 && columna < numCasillas) {
                if (casillas[fila][columna].equals("   ")) {
                    casillas[fila][columna] = " " + simbolo + " ";
                    return 0;
                } else {
                    return 1;
                }
            }
            return 2;
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
        JFrame frameOnTop;

        public Juego() {
            tablero = new Tablero();
            turno = "X";

            frameOnTop = new JFrame();
            frameOnTop.setAlwaysOnTop(true);
            frameOnTop.setLocationRelativeTo(null);
        }

        public void cambiarTurno() {
            turno = turno.equals("X") ? "O" : "X";
        }

        public void jugar() {
            while (tablero.hayCasillasVacias() && !tablero.hayGanador()) {
                String estadoTablero = tablero.generarTableroString();

                String inputFila = JOptionPane.showInputDialog(frameOnTop,
                        estadoTablero + "Turno del jugador " + turno + "\nIndica la FILA (0 a " + (tablero.numCasillas - 1) + "):",
                        "Tres en Raya", JOptionPane.PLAIN_MESSAGE);

                if (inputFila == null) System.exit(0);

                String inputColumna = JOptionPane.showInputDialog(frameOnTop,
                        estadoTablero + "Turno del jugador " + turno + "\nIndica la COLUMNA (0 a " + (tablero.numCasillas - 1) + "):",
                        "Tres en Raya", JOptionPane.PLAIN_MESSAGE);

                if (inputColumna == null) System.exit(0);

                int fila, columna;
                try {
                    fila = Integer.parseInt(inputFila.trim());
                    columna = Integer.parseInt(inputColumna.trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frameOnTop, "¡Error! Tienes que poner un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                int resultadoMovimiento = tablero.ponerFicha(fila, columna, turno);

                if (resultadoMovimiento == 0) {
                    if (tablero.hayGanador()) {
                        JOptionPane.showMessageDialog(frameOnTop, tablero.generarTableroString() + "¡Felicidades! El jugador " + turno + " ha ganado!!!!", "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                    if (!tablero.hayCasillasVacias()) {
                        JOptionPane.showMessageDialog(frameOnTop, tablero.generarTableroString() + "¡Es un empate!", "Empate", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                    cambiarTurno();
                } else if (resultadoMovimiento == 1) {
                    JOptionPane.showMessageDialog(frameOnTop, "¡Esa casilla ya está ocupada! Elige otra.", "Movimiento inválido", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frameOnTop, "¡Error! El número debe estar entre 0 y " + (tablero.numCasillas - 1) + ".", "Fuera de límites", JOptionPane.WARNING_MESSAGE);
                }
            }

            frameOnTop.dispose();
        }
    }

    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 16));

        new Juego().jugar();
    }
}