/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Arrays;

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

    public int contarFichas(char color) {
        int cantidad = 0;
        for (char[] matriz1 : matriz) {
            for (int j = 0; j < matriz1.length; j++) {
                if (matriz1[j] == color) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    public boolean validarMovimientoIndividual(char color, String sentido, int fila, int columna, int pasos) {
        boolean valido = true;
        int cambioFila = 0;
        int cambioColumna = 0;
        int filaFinal = fila;
        int columnaFinal = columna;
        char colorOpuesto = color == 'B' ? 'N' : 'B';
        if (matriz[fila][columna] != color) {
            valido = false;
        }
        if (valido) {
            cambioFila = obtenerCambioFila(sentido);
            cambioColumna = obtenerCambioColumna(sentido);
        }
        if (valido) {
            filaFinal = fila + cambioFila * pasos;
            columnaFinal = columna + cambioColumna * pasos;
        }
        if (valido && !validarPosicionFinal(filaFinal, columnaFinal)) {
            valido = false;
        }
        if (valido) {
            valido = caminoIntermedioLibre(fila, columna, cambioFila, cambioColumna, pasos);
        }
        if (valido) {
            valido = posicionFinalDisponible(filaFinal, columnaFinal, colorOpuesto);
        }
        if (valido) {
            matriz[filaFinal][columnaFinal] = color;
            matriz[fila][columna] = 'V';
        }
        return valido;
    }

    public boolean validarMovimientoEnGrupo(char color, String forma, String sentido,
            int fila, int columna, int tamanio, int pasos) {
        boolean valido = true;
        if (forma.equals("H") && !(sentido.equals("N") || sentido.equals("S"))) {
            valido = false;
        }
        if (valido && forma.equals("V") && !(sentido.equals("E") || sentido.equals("O"))) {
            valido = false;
        }
        if (valido) {
            valido = validarGrupoExiste(color, forma, fila, columna, tamanio);
        }
        if (valido) {
            valido = validarCaminoGrupoLibre(forma, fila, columna, tamanio, sentido, pasos);
        }
        if (valido) {
            moverGrupo(forma, fila, columna, tamanio, sentido, pasos, color);
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
                    case 'V' ->
                        tableroPreparado.append("  ");
                    case 'B' ->
                        tableroPreparado.append("B ");
                    case 'N' ->
                        tableroPreparado.append("N ");
                    default ->
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

    public boolean verificarConexion(char color) {
        boolean[][] visitado = new boolean[8][10];
        int[] posicionPrimera = buscarPrimeraFicha(color);
        boolean conectado = posicionPrimera[0] != -1;
        if (conectado) {
            int filaInicio = posicionPrimera[0];
            int columnaInicio = posicionPrimera[1];
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

    private void recorrerConectadas(char color, int filaInicio, int columnaInicio, boolean[][] visitado) {
        boolean indicesPositivos = filaInicio >= 0
                && columnaInicio >= 0;
        boolean posicionInicialEnRango = filaInicio < matriz.length
                && columnaInicio < matriz[0].length;
        if (indicesPositivos && posicionInicialEnRango
                && !visitado[filaInicio][columnaInicio]
                && matriz[filaInicio][columnaInicio] == color) {
            visitado[filaInicio][columnaInicio] = true;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!(i == 0 && j == 0)) {
                        recorrerConectadas(color, filaInicio + i, columnaInicio + j, visitado);
                    }
                }
            }
        }
    }

    private boolean validarPosicionFinal(int fila, int columna) {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 10;
    }

    private int obtenerCambioFila(String sentido) {
        if (sentido.equals("N") || sentido.equals("NE") || sentido.equals("NO")) {
            return -1;
        }
        if (sentido.equals("S") || sentido.equals("SE") || sentido.equals("SO")) {
            return 1;
        }
        return 0;
    }

    private int obtenerCambioColumna(String sentido) {
        if (sentido.equals("E") || sentido.equals("NE") || sentido.equals("SE")) {
            return 1;
        }
        if (sentido.equals("O") || sentido.equals("NO") || sentido.equals("SO")) {
            return -1;
        }
        return 0;
    }

    private boolean caminoIntermedioLibre(int fila, int columna, int cambioFila, int cambioColumna, int pasos) {
        boolean valido = true;
        for (int paso = 1; paso < pasos && valido; paso = paso + 1) {
            int filaActual = fila + cambioFila * paso;
            int columnaActual = columna + cambioColumna * paso;
            if (matriz[filaActual][columnaActual] != 'V') {
                valido = false;
            }
        }
        return valido;
    }

    private boolean posicionFinalDisponible(int filaFinal, int columnaFinal, char colorOpuesto) {
        boolean valido = true;
        if (matriz[filaFinal][columnaFinal] != 'V'
                && matriz[filaFinal][columnaFinal] != colorOpuesto) {
            valido = false;
        }
        return valido;
    }

    private int[] buscarPrimeraFicha(char color) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == color) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1};
    }

    private boolean validarGrupoExiste(char color, String forma, int fila, int columna, int tamanio) {
        boolean valido = true;
        for (int pos = 0; pos < tamanio && valido; pos = pos + 1) {
            int filaFicha = fila;
            int columnaFicha = columna;

            if (forma.equals("H")) {
                columnaFicha = columna + pos;
            } else {
                filaFicha = fila + pos;
            }

            if (!validarPosicionFinal(filaFicha, columnaFicha) || matriz[filaFicha][columnaFicha] != color) {
                valido = false;
            }
        }
        return valido;
    }

    private boolean validarCaminoGrupoLibre(String forma, int fila, int columna, int tamanio, String sentido, int pasos) {
        int cambioFila = obtenerCambioFila(sentido);
        int cambioColumna = obtenerCambioColumna(sentido);
        boolean valido = true;
        for (int pos = 0; pos < tamanio; pos = pos + 1) {
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
                if (!validarPosicionFinal(filaActual, columnaActual) || matriz[filaActual][columnaActual] != 'V') {
                    valido = false;
                }
            }
        }
        return valido;
    }

    private void moverGrupo(String forma, int fila, int columna, int tamanio, String sentido, int pasos, char color) {
        int cambioFila = obtenerCambioFila(sentido);
        int cambioColumna = obtenerCambioColumna(sentido);
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
}