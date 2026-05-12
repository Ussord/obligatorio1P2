/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obligatorio1p2.modelo;

/**
 *
 * @author Camila
 */
public class Validaciones {

    public static boolean validarColor(char color) {

        return color == 'B' || color == 'N';

    }

    public static boolean validarPosicion(int fila, int columna) {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 10;
    }

    public static boolean validarPasos(int pasos) {
        return pasos > 0;
    }

    public static boolean validarSentidoSegunColor(char color, String sentido) {
        if (color == 'B') {
            return sentido.equals("N") || sentido.equals("NE") || sentido.equals("NO")
                    || sentido.equals("E") || sentido.equals("O");
        } else {
            return sentido.equals("S") || sentido.equals("SE") || sentido.equals("SO")
                    || sentido.equals("E") || sentido.equals("O");
        }
    }

    public static boolean validarSentidoIndividual(String sentido) {
        return sentido.equals("N") || sentido.equals("S")
                || sentido.equals("E") || sentido.equals("O")
                || sentido.equals("NE") || sentido.equals("NO")
                || sentido.equals("SE") || sentido.equals("SO");
    }

    public static boolean validarSentidoGrupo(String sentido) {
        return sentido.equals("N") || sentido.equals("S")
                || sentido.equals("E") || sentido.equals("O");
    }

}
