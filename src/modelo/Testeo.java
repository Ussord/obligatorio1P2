/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;
import java.util.Arrays;

/**
 *
 * @author Camila
 */
public class Testeo {

    private int numero;
    private int caso;
    private List<String> parametros;
    private String comentario;
    private String resultado;
    private char[][] matrizOriginal = new char[8][10];
    private char[][] matrizResultante = new char[8][10];

    public Testeo(int numero, int caso, List<String> parametros, String comentario, String resultado) {
        this.numero = numero;
        this.caso = caso;
        this.parametros = parametros;
        this.comentario = comentario;
        this.resultado = resultado;
    }

    public int getNumero() {
        return numero;
    }

    public int getCaso() {
        return caso;
    }

    public String getParametros() {
        return prepararParametros(parametros);
    }

    public String getComentario() {
        return comentario;
    }

    public String getResultado() {
        return resultado;
    }

    public char[][] getMatrizOriginal() {
        char[][] copiaMatriz = new char[8][10];
        for (int i = 0; i < matrizOriginal.length; i++) {
            copiaMatriz[i] = Arrays.copyOf(matrizOriginal[i], matrizOriginal[i].length);
        }
        return copiaMatriz;
    }

    public void setMatrizOriginal(char[][] matrizOriginal) {
        for (int i = 0; i < matrizOriginal.length; i++) {
            this.matrizOriginal[i] = Arrays.copyOf(matrizOriginal[i], matrizOriginal[i].length);
        }
    }

    public char[][] getMatrizResultante() {
        char[][] copiaMatriz = new char[8][10];
        for (int i = 0; i < matrizResultante.length; i++) {
            copiaMatriz[i] = Arrays.copyOf(matrizResultante[i], matrizResultante[i].length);
        }
        return copiaMatriz;
    }

    public void setMatrizResultante(char[][] matrizResultante) {
        for (int i = 0; i < matrizResultante.length; i++) {
            this.matrizResultante[i] = Arrays.copyOf(matrizResultante[i], matrizResultante[i].length);
        }
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        texto.append("Número del testeo: ")
                .append(numero)
                .append("\n")
                .append("Caso: ")
                .append(caso)
                .append("\n")
                .append("Parametros: ")
                .append(prepararParametros(parametros))
                .append("\n")
                .append("Comentario: ")
                .append(comentario)
                .append("\n")
                .append("Resultado: ")
                .append(resultado)
                .append("\n")
                .append("Matriz original:")
                .append("\n")
                .append(prepararMatrizComoTablero(matrizOriginal))
                .append("\n");
        if (matrizResultante[0][0] != '\0') {
            texto.append("Matriz resultante")
                    .append("\n")
                    .append(prepararMatrizComoTablero(matrizResultante));
        }
        texto.append("\n");
        return texto.toString();
    }

    private String prepararMatrizComoTablero(char[][] matriz) {
        Tablero tablero = new Tablero(matriz);
        return tablero.prepararTablero();
    }

    private String prepararParametros(List<String> parametros) {
        StringBuilder parametrosPreparados = new StringBuilder();
        for (int i = 0; i < parametros.size(); i++) {
            parametrosPreparados.append(i + 1)
                    .append(": ")
                    .append(parametros.get(i));
            if (i < parametros.size() - 1) {
                parametrosPreparados.append("\n");
            }
        }
        return parametrosPreparados.toString();
    }

}
