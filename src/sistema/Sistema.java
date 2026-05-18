package sistema;


import java.util.List;
import modelo.Tablero;
import modelo.Tester;
import java.util.Scanner;

public class Sistema {

    private static List<Tester> listaTesters;
    private static Tablero tableroActual;
    private static int contadorTesteos;
    private static Scanner scanner = new Scanner(System.in);

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        System.out.println("Trabajo desarrollado por:");
        System.out.println("Mauro Russo 300185");
        System.out.println("Valeria Otegui 281674");
        String opcionStr = "";
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("a) Registrar tester");
            System.out.println("b) Registrar matriz actual del juego");
            System.out.println("c) Registrar testeo");
            System.out.println("d) Consulta de testers");
            System.out.println("e) Estadísticas");
            System.out.println("f) Terminar el programa");
            System.out.print("Ingrese opción: ");
            opcionStr = scanner.nextLine().trim().toLowerCase();
            switch (opcionStr) {
                case "a" ->
                    registrarTester();
                case "b" ->
                    registrarMatriz();
                case "c" ->
                    registrarTesteo();
                case "d" ->
                    consultarTester();
                case "e" ->
                    mostrarEstadisticas();
                case "f" ->
                    System.out.println("Hasta luego");
                default ->
                    System.out.println("Opción inválida");
            }
        } while (!opcionStr.equals("f"));
    }

    @SuppressWarnings("java:S106")
    private static void registrarTester() {
        System.out.println("Ingrese nombre del tester");
        String nombre = scanner.nextLine().trim();
        if (listaTesters.stream().anyMatch(t -> t.getNombre().equalsIgnoreCase(nombre))) {
            System.out.println("Ya existe un tester con este nombre");
            return;
        }
        System.out.println("Ingrese edad del tester");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese anios de experiencia del tester");
        int aniosExperiencia = scanner.nextInt();
        scanner.nextLine();
        if (edad < 0 || aniosExperiencia < 0) {
            System.out.println("Los valores deben ser positivos");
            return;
        }
        listaTesters.add(new Tester(nombre, edad, aniosExperiencia));
    }

    @SuppressWarnings("java:S106")
    private static void registrarMatriz() {
        System.out.println("El tablero actual es:");
        System.out.println(tableroActual.prepararTablero());
        System.out.println("Desea cambiarla? S/N");
        String cambia = scanner.nextLine().trim();
        if (cambia.equalsIgnoreCase("S")) {
            System.out.println("Desea utilizar el tablero por defecto? S/N");
            String eligeDefaul = scanner.nextLine().trim();
            if (eligeDefaul.equalsIgnoreCase("S")) {
                tableroActual = new Tablero();
            } else if (eligeDefaul.equalsIgnoreCase("N")) {
                tableroActual = new Tablero(leerMatrizParticular());
            } else {
                System.out.println("Respuesta incorrecta");
            }
        } else if (!cambia.equalsIgnoreCase("N")) {
            System.out.println("Respuesta incorrecta");
        }
    }

    @SuppressWarnings({"java:S106", "java:S127"})
    private static char[][] leerMatrizParticular() {
        char[][] matrizParticular = new char[8][10];
        for (int i = 0; i < 8; i++) {
            System.out.println("Ingrese la fila " + (i + 1) + " del tablero particular:");
            String fila = scanner.nextLine();
            if (!fila.matches("[BNV]{10}")) {
                System.out.println("Fila mal ingresada");
                i--;
            } else {
                for (int j = 0; j < 10; j++) {
                    matrizParticular[i][j] = fila.charAt(j);
                }
            }
        }
        return matrizParticular;
    }

    private static void registrarTesteo() {

    }

    private static List<String> pedirParametrosCaso(int caso) {

    }

    private static String ejecutarCaso(int caso, List<String> parametros) {

    }

    private static void consultarTester() {

    }

    private static void mostrarEstadisticas() {

    }
}
