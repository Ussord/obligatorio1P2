/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obligatorio1p2.modelo;

import java.util.Arrays;

import obligatorio1p2.modelo.Validaciones;

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

    //Caso 1: contarFichas
    public int contarFichas(char color) {

        int cantidad = 0;

        if (Validaciones.validarColor(color)) {

            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {

                    if (matriz[i][j] == color) {
                        cantidad++;
                    }

                }

            }
        }
        return cantidad;

    }

    // Caso 2: validar movimiento individual
    public boolean validarMovimientoIndividual(char color, String sentido, int fila, int columna, int pasos) {

        boolean valido = true;
        int cambioFila = 0;
        int cambioColumna = 0;
        int filaFinal = fila;
        int columnaFinal = columna;
        char colorOpuesto = 'B';

        if (!Validaciones.validarColor(color)) {
            valido = false;
        }

        // Definimos el color opuesto.
        if (valido && color == 'B') {
            colorOpuesto = 'N';
        }

        // Validamos que el sentido sea uno de los permitidos.
        if (valido && !Validaciones.validarSentidoIndividual(sentido)) {
            valido = false;
        }

        // Validamos pasos y posición inicial.
        if (valido && (!Validaciones.validarPasos(pasos) || !Validaciones.validarPosicion(fila, columna))) {
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
        if (valido && !Validaciones.validarSentidoSegunColor(color, sentido)) {
            valido = false;
        }

        // Calculamos posición final.
        if (valido) {
            filaFinal = fila + cambioFila * pasos;
            columnaFinal = columna + cambioColumna * pasos;
        }

        // Validamos que la posición final esté dentro del tablero.
        if (valido && !Validaciones.validarPosicion(filaFinal, columnaFinal)) {
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

    // Caso 3: validar movimiento en grupo
    public boolean validarMovimientoEnGrupo(char color, String forma, String sentido,
            int fila, int columna, int tamanio, int pasos) {

        boolean valido = true;
        int cambioFila = 0;
        int cambioColumna = 0;

        if (!Validaciones.validarColor(color)) {
            valido = false;
        }

        // Validamos forma.
        if (valido && !(forma.equals("H") || forma.equals("V"))) {
            valido = false;
        }

        // Validamos sentido.
        if (valido && !Validaciones.validarSentidoGrupo(sentido)) {
            valido = false;
        }

        // Validamos datos numéricos y posición inicial.
        if (valido && (tamanio <= 0 || !Validaciones.validarPasos(pasos) || !Validaciones.validarPosicion(fila, columna))) {
            valido = false;
        }

        // Validamos sentido según forma del grupo.
        if (valido && forma.equals("H") && !(sentido.equals("N") || sentido.equals("S"))) {
            valido = false;
        }

        if (valido && forma.equals("V") && !(sentido.equals("E") || sentido.equals("O"))) {
            valido = false;
        }

        // Validamos sentido según color.
        if (valido && !Validaciones.validarSentidoSegunColor(color, sentido)) {
            valido = false;
        }

        // Convertimos el sentido en cambios de fila y columna.
        if (valido) {
            if (sentido.equals("N")) {
                cambioFila = -1;
            }
            if (sentido.equals("S")) {
                cambioFila = 1;
            }
            if (sentido.equals("E")) {
                cambioColumna = 1;
            }
            if (sentido.equals("O")) {
                cambioColumna = -1;
            }
        }

        // Validamos que el grupo exista completo y sea del color indicado.
        for (int pos = 0; pos < tamanio && valido; pos = pos + 1) {
            int filaFicha = fila;
            int columnaFicha = columna;

            if (forma.equals("H")) {
                columnaFicha = columna + pos;
            } else {
                filaFicha = fila + pos;
            }

            if (!Validaciones.validarPosicion(filaFicha, columnaFicha)) {
                valido = false;
            } else {
                if (matriz[filaFicha][columnaFicha] != color) {
                    valido = false;
                }
            }
        }

        // Validamos camino libre y posiciones finales vacías.
        for (int pos = 0; pos < tamanio && valido; pos = pos + 1) {
            int filaFicha = fila;
            int columnaFicha = columna;

            if (forma.equals("H")) {
                columnaFicha = columna + pos;
            } else {
                filaFicha = fila + pos;
            }

            for (int paso = 1; paso <= pasos && valido; paso = paso + 1) {
                int filaActual = filaFicha + cambioFila * paso;
                int columnaActual = columnaFicha + cambioColumna * paso;

                if (!Validaciones.validarPosicion(filaActual, columnaActual)) {
                    valido = false;
                } else {
                    if (matriz[filaActual][columnaActual] != 'V') {
                        valido = false;
                    }
                }
            }
        }

        // Si todo fue válido, modificamos el tablero.
        if (valido) {
            for (int pos = 0; pos < tamanio; pos = pos + 1) {
                int filaFicha = fila;
                int columnaFicha = columna;

                if (forma.equals("H")) {
                    columnaFicha = columna + pos;
                } else {
                    filaFicha = fila + pos;
                }

                matriz[filaFicha][columnaFicha] = 'V';
            }

            for (int pos = 0; pos < tamanio; pos = pos + 1) {
                int filaFicha = fila;
                int columnaFicha = columna;

                if (forma.equals("H")) {
                    columnaFicha = columna + pos;
                } else {
                    filaFicha = fila + pos;
                }

                matriz[filaFicha + cambioFila * pasos][columnaFicha + cambioColumna * pasos] = color;
            }
        }

        return valido;
    }
    //Caso 4 Preparar Tablero

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
    //Caso 5 Verificar conexion 

    public boolean verificarConexion(char color) {

        if (!Validaciones.validarColor(color)) {
            return false;
        }

        boolean[][] visitado = new boolean[8][10];
        boolean encontrePrimera = false;
        int filaInicio = -1;
        int columnaInicio = -1;

        for (int i = 0; i < matriz.length && !encontrePrimera; i++) {
            for (int j = 0; j < matriz[i].length && !encontrePrimera; j++) {
                if (matriz[i][j] == color) {
                    filaInicio = i;
                    columnaInicio = j;
                    encontrePrimera = true;
                }
            }
        }

        boolean conectado = encontrePrimera;

        if (conectado) {
            recorrerConectadas(color, filaInicio, columnaInicio, visitado);

            for (int i = 0; i < matriz.length && conectado; i++) {
                for (int j = 0; j < matriz[i].length && conectado; j++) {
                    if (matriz[i][j] == color && !visitado[i][j]) {
                        conectado = false;
                    }
                }
            }
        }

        return conectado;
    }

    private void recorrerConectadas(char color, int fila, int columna, boolean[][] visitado) {
        if (fila >= 0 && fila < matriz.length
                && columna >= 0 && columna < matriz[0].length
                && !visitado[fila][columna]
                && matriz[fila][columna] == color) {

            visitado[fila][columna] = true;

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!(i == 0 && j == 0)) {
                        recorrerConectadas(color, fila + i, columna + j, visitado);
                    }
                }
            }
        }
    }

}
