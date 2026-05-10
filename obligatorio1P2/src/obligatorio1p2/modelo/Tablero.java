/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obligatorio1p2.modelo;
import java.util.Arrays;
/**
 *
 * @author Camila
 */
public class Tablero {
    private char[][] matriz = new char[8][10];

    public Tablero() {
        String[] filas = {
            "VVNNVVNNVV",
            "NNNNNNNNNN",
            "NNVVNNVVNN",
            "VVVVVVVVVV",
            "VVVVVVVVVV",
            "BBVVBBVVBB",
            "BBBBBBBBBB",
            "VVBBVVBBVV"
        };
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = filas[i].charAt(j);
            }
        }
    }

    public Tablero(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            this.matriz[i] = Arrays.copyOf(matriz[i], matriz[i].length);
        }
    }

    public char[][] getMatriz() {
        char[][] copiaMatriz = new char[8][10];
        for (int i = 0; i < matriz.length; i++) {
            copiaMatriz[i] = Arrays.copyOf(matriz[i], matriz[i].length);
        }
        return copiaMatriz;
    }

    // Caso 2: validar movimiento individual
    public boolean validarMovimientoIndividual(char color, String sentido, int fila, int columna, int pasos) {

        boolean valido = true;
        int cambioFila = 0;
        int cambioColumna = 0;
        int filaFinal = fila;
        int columnaFinal = columna;
        char colorOpuesto = 'B';

        // Validamos que el color sea uno de los permitidos.
        if (color != 'B' && color != 'N') {
            valido = false;
        }

        // Definimos el color opuesto.
        if (valido && color == 'B') {
            colorOpuesto = 'N';
        }

        // Validamos que el sentido sea uno de los permitidos.
        if (valido && !(sentido.equals("N") || sentido.equals("S")
                || sentido.equals("E") || sentido.equals("O")
                || sentido.equals("NE") || sentido.equals("NO")
                || sentido.equals("SE") || sentido.equals("SO"))) {
            valido = false;
        }

        // Validamos pasos y posición inicial.
        if (valido && (pasos <= 0 || fila < 0 || fila >= matriz.length
                || columna < 0 || columna >= matriz[0].length)) {
            valido = false;
        }

        // Validamos que en la posición inicial haya una ficha del color indicado.
        if (valido && matriz[fila][columna] != color) {
            valido = false;
        }

        // Convertimos el sentido en cambios de fila y columna.
        if (valido) {
            if (sentido.equals("N") || sentido.equals("NE") || sentido.equals("NO")) {
                cambioFila = -1;
            }
            if (sentido.equals("S") || sentido.equals("SE") || sentido.equals("SO")) {
                cambioFila = 1;
            }
            if (sentido.equals("E") || sentido.equals("NE") || sentido.equals("SE")) {
                cambioColumna = 1;
            }
            if (sentido.equals("O") || sentido.equals("NO") || sentido.equals("SO")) {
                cambioColumna = -1;
            }
        }

        // Validamos que las blancas no retrocedan.
        if (valido && color == 'B'
                && !(sentido.equals("N") || sentido.equals("NE") || sentido.equals("NO")
                || sentido.equals("E") || sentido.equals("O"))) {
            valido = false;
        }

        // Validamos que las negras no retrocedan.
        if (valido && color == 'N'
                && !(sentido.equals("S") || sentido.equals("SE") || sentido.equals("SO")
                || sentido.equals("E") || sentido.equals("O"))) {
            valido = false;
        }

        // Calculamos posición final.
        if (valido) {
            filaFinal = fila + cambioFila * pasos;
            columnaFinal = columna + cambioColumna * pasos;
        }

        // Validamos que la posición final esté dentro del tablero.
        if (valido && (filaFinal < 0 || filaFinal >= matriz.length
                || columnaFinal < 0 || columnaFinal >= matriz[0].length)) {
            valido = false;
        }

        // Validamos que el camino intermedio esté libre.
        for (int paso = 1; paso < pasos && valido; paso = paso + 1) {
            int filaActual = fila + cambioFila * paso;
            int columnaActual = columna + cambioColumna * paso;

            if (matriz[filaActual][columnaActual] != 'V') {
                valido = false;
            }
        }

        // Validamos que la posición final esté vacía o tenga una ficha del color opuesto.
        if (valido && matriz[filaFinal][columnaFinal] != 'V'
                && matriz[filaFinal][columnaFinal] != colorOpuesto) {
            valido = false;
        }

        // Si todo fue válido, modificamos el tablero.
        if (valido) {
            matriz[filaFinal][columnaFinal] = color;
            matriz[fila][columna] = 'V';
        }

        return valido;
    }

    public String prepararTablero() {
        StringBuilder tableroPreparado = new StringBuilder();
        String filaSeparadora = "+---+---+---+---+---+---+---+---+---+---+";
        String separadorColumna = "|";

        for (int i = 0; i < this.matriz.length; i++) {
            tableroPreparado.append(filaSeparadora).append("\n");
            for (int j = 0; j < this.matriz[i].length; j++) {
                tableroPreparado.append(separadorColumna).append(" ");
                switch (matriz[i][j]) {
                    case 'V':
                        tableroPreparado.append("  ");
                        break;
                    case 'B':
                        tableroPreparado.append("B ");
                        break;
                    case 'N':
                        tableroPreparado.append("N ");
                        break;
                    default:
                        throw new AssertionError();
                }
                if (j == this.matriz[i].length - 1) {
                    tableroPreparado.append(separadorColumna).append("\n");
                }
            }
            if (i == this.matriz.length - 1) {
                tableroPreparado.append(filaSeparadora);
            }
        }
        return tableroPreparado.toString();
    }
    
}
