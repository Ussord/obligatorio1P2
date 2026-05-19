package sistema;

import java.util.List;
import modelo.Tablero;
import modelo.Tester;
import java.util.Scanner;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import modelo.Testeo;
import java.util.Arrays;

public class Sistema {

    private static List<Tester> listaTesters;
    private static Tablero tableroActual;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Set<String> COLORES_VALIDOS = new HashSet<>(Arrays.asList("B", "N"));
    private static final Set<String> FORMAS_VALIDAS = new HashSet<>(Arrays.asList("H", "V"));
    private static final Set<String> SENTIDOS_INDIVIDUALES = new HashSet<>(Arrays.asList("N", "S", "E", "O", "NE", "NO", "SE", "SO"));
    private static final Set<String> SENTIDOS_GRUPO = new HashSet<>(Arrays.asList("N", "S", "E", "O"));
    private static final Map<String, Set<String>> SENTIDOS_POR_COLOR = new HashMap<>();
    private static final String COLOR_MSG = "Ingrese color (B/N):";
    private static final String COLOR_INVALIDO_MSG = "Color inválido";
    private static final String PASOS_REGEX = "[1-9]";

    static {
        SENTIDOS_POR_COLOR.put("B", new HashSet<>(Arrays.asList("N", "NE", "NO", "E", "O")));
        SENTIDOS_POR_COLOR.put("N", new HashSet<>(Arrays.asList("S", "SE", "SO", "E", "O")));
    }

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
        listaTesters.sort(Comparator.comparing(Tester::getNombre));
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

    @SuppressWarnings("java:S106")
    private static void registrarTesteo() {
        if (listaTesters.isEmpty()) {
            System.out.println("No existen testers registrados.");
            return;
        }
        System.out.println("Elija un tester:");
        System.out.println(desplegarListaTesters());
        int testerElegidoInt = scanner.nextInt();
        scanner.nextLine();
        if (testerElegidoInt < 1 || testerElegidoInt > listaTesters.size()) {
            System.out.println("Numero de tester no existente");
            return;
        }
        Tester testerElegidoObj = listaTesters.get(testerElegidoInt - 1);
        System.out.println("Elija un caso:");
        System.out.println(desplegarCasos());
        int casoElegido = scanner.nextInt();
        scanner.nextLine();
        if (casoElegido <= 0 || casoElegido > 5) {
            System.out.println("Caso elegido no existente");
            return;
        }
        List<String> parametros = pedirParametrosCaso(casoElegido);
        if (parametros.isEmpty()) {
            return;
        }
        System.out.println("Ingrese comentario:");
        String comentario = scanner.nextLine().trim();
        char[][] matrizOriginal = tableroActual.getMatriz();
        String resultado = ejecutarCaso(casoElegido, parametros);
        Testeo testRealizado = new Testeo(casoElegido, parametros, comentario, resultado);
        testRealizado.setMatrizOriginal(matrizOriginal);
        if ((casoElegido == 2 || casoElegido == 3) && resultado.equals("true")) {
            testRealizado.setMatrizResultante(tableroActual.getMatriz());
        }
        testerElegidoObj.agregarTesteo(testRealizado);
    }

    private static List<String> pedirParametrosCaso(int caso) {
        List<String> parametros = new ArrayList<>();
        switch (caso) {
            case 1 -> {
                return pedirParametrosCaso1Y5(parametros);
            }
            case 2 -> {
                return pedirParametrosCaso2(parametros);
            }
            case 3 -> {
                return pedirParametrosCaso3(parametros);
            }
            case 5 -> {
                return pedirParametrosCaso1Y5(parametros);
            }
            default -> {
                return parametros;
            }
        }
    }

    private static String ejecutarCaso(int caso, List<String> parametros) {
        switch (caso) {
            case 1 -> {
                return String.valueOf(tableroActual.contarFichas(parametros.get(0).charAt(0)));
            }
            case 2 -> {
                return String.valueOf(tableroActual.validarMovimientoIndividual(parametros.get(0).charAt(0),
                        parametros.get(1),
                        Integer.parseInt(parametros.get(2)),
                        Integer.parseInt(parametros.get(3)),
                        Integer.parseInt(parametros.get(4))));
            }
            case 3 -> {
                return String.valueOf(tableroActual.validarMovimientoEnGrupo(
                        parametros.get(0).charAt(0),
                        parametros.get(1),
                        parametros.get(2),
                        Integer.parseInt(parametros.get(3)),
                        Integer.parseInt(parametros.get(4)),
                        Integer.parseInt(parametros.get(5)),
                        Integer.parseInt(parametros.get(6))));
            }
            case 4 -> {
                return tableroActual.prepararTablero();
            }
            case 5 -> {
                return String.valueOf(
                        tableroActual
                                .verificarConexion(parametros.get(0).charAt(0)));
            }
            default ->
                throw new AssertionError();
        }
    }

    @SuppressWarnings("java:S106")
    private static void consultarTester() {
        if (listaTesters.isEmpty()) {
            System.out.println("No existen testers registrados.");
            return;
        }
        System.out.println("Elija un tester:");
        System.out.println(desplegarListaTesters());
        int testerElegidoInt = scanner.nextInt();
        scanner.nextLine();
        if (testerElegidoInt < 1 || testerElegidoInt > listaTesters.size()) {
            System.out.println("Numero de tester no existente");
            return;
        }
        Tester testerElegido = listaTesters.get(testerElegidoInt - 1);
        if (testerElegido.obtenerCantidadTesteos() == 0) {
            System.out.println("El tester no tiene testeos registrados.");
            return;
        }
        System.out.println("Testeos de " + testerElegido.getNombre() + ":");
        System.out.println(testerElegido.obtenerListaResumidaTesteos());
        System.out.println("Ingrese numero de testeo:");
        int numeroTesteo = scanner.nextInt();
        scanner.nextLine();
        String testeo = testerElegido.obtenerTesteoPorNumero(numeroTesteo);
        System.out.println(testeo);
    }
    
    @SuppressWarnings("java:S106")
    private static void mostrarEstadisticas() {
        if (listaTesters.isEmpty()) {
            System.out.println("No existen testers registrados.");
            return;
        }
        int maximo = 0;
        for (Tester tester : listaTesters) {
            if (tester.obtenerCantidadTesteos() > maximo) {
                maximo = tester.obtenerCantidadTesteos();
            }
        }
        System.out.println("Testers con mayor cantidad de testeos (" + maximo + "):");
        for (Tester tester : listaTesters) {
            if (tester.obtenerCantidadTesteos() == maximo) {
                System.out.println("- " + tester.getNombre());
            }
        }
        System.out.println("Testers sin testeos:");
        for (Tester tester : listaTesters) {
            if (tester.obtenerCantidadTesteos() == 0) {
                System.out.println("- " + tester.getNombre());
            }
        }
    }

    private static String desplegarListaTesters() {
        StringBuilder listaTestersDesplegada = new StringBuilder();
        for (int i = 0; i < listaTesters.size(); i++) {
            listaTestersDesplegada.append((i + 1))
                    .append(" - ")
                    .append(listaTesters.get(i).getNombre())
                    .append("\n");
        }
        return listaTestersDesplegada.toString();
    }

    private static String desplegarCasos() {
        StringBuilder casosDesplegados = new StringBuilder();
        casosDesplegados.append("1 - contarFichas\n")
                .append("2 - validarMovimientoIndividual\n")
                .append("3 - validarMovimientoEnGrupo\n")
                .append("4 - prepararTablero\n")
                .append("5 - verificarConexion\n");
        return casosDesplegados.toString();
    }

    @SuppressWarnings("java:S106")
    private static List<String> pedirParametrosCaso1Y5(List<String> parametros) {
        System.out.println(COLOR_MSG);
        String color = scanner.nextLine().trim().toUpperCase();
        if (!COLORES_VALIDOS.contains(color)) {
            System.out.println(COLOR_INVALIDO_MSG);
            return new ArrayList<>();
        }
        parametros.add(color);
        return parametros;
    }

    @SuppressWarnings("java:S106")
    private static List<String> pedirParametrosCaso2(List<String> parametros) {
        System.out.println(COLOR_MSG);
        String color = scanner.nextLine().trim().toUpperCase();
        if (!COLORES_VALIDOS.contains(color)) {
            System.out.println(COLOR_INVALIDO_MSG);
            return new ArrayList<>();
        }
        parametros.add(color);
        System.out.println("Ingrese sentido (N/S/E/O/NE/NO/SE/SO):");
        String sentido = scanner.nextLine().trim().toUpperCase();
        if (!SENTIDOS_INDIVIDUALES.contains(sentido)) {
            System.out.println("Sentido inválido");
            return new ArrayList<>();
        }
        if (!SENTIDOS_POR_COLOR.get(color).contains(sentido)) {
            System.out.println("Sentido inválido para ese color");
            return new ArrayList<>();
        }
        parametros.add(sentido);
        System.out.println("Ingrese fila:");
        String fila = scanner.nextLine().trim();
        if (!fila.matches("[0-7]")) {
            System.out.println("Fila inválida");
            return new ArrayList<>();
        }
        parametros.add(fila);
        System.out.println("Ingrese columna:");
        String columna = scanner.nextLine().trim();
        if (!columna.matches("\\d")) {
            System.out.println("Columna inválida");
            return new ArrayList<>();
        }
        parametros.add(columna);
        System.out.println("Ingrese pasos:");
        String pasos = scanner.nextLine().trim();
        if (!pasos.matches(PASOS_REGEX)) {
            System.out.println("Pasos inválidos");
            return new ArrayList<>();
        }
        parametros.add(pasos);
        return parametros;
    }

    @SuppressWarnings("java:S106")
    private static List<String> pedirParametrosCaso3(List<String> parametros) {
        System.out.println(COLOR_MSG);
        String color = scanner.nextLine().trim().toUpperCase();
        if (!COLORES_VALIDOS.contains(color)) {
            System.out.println(COLOR_INVALIDO_MSG);
            return new ArrayList<>();
        }
        parametros.add(color);
        System.out.println("Ingrese forma (H/V):");
        String forma = scanner.nextLine().trim().toUpperCase();
        if (!FORMAS_VALIDAS.contains(forma)) {
            System.out.println("Forma inválida");
            return new ArrayList<>();
        }
        parametros.add(forma);
        System.out.println("Ingrese sentido (N/S/E/O):");
        String sentido = scanner.nextLine().trim().toUpperCase();
        if (!SENTIDOS_GRUPO.contains(sentido)) {
            System.out.println("Sentido inválido");
            return new ArrayList<>();
        }
        if (!SENTIDOS_POR_COLOR.get(color).contains(sentido)) {
            System.out.println("Sentido inválido para ese color");
            return new ArrayList<>();
        }
        parametros.add(sentido);
        System.out.println("Ingrese fila:");
        String fila = scanner.nextLine().trim();
        if (!fila.matches("[0-7]")) {
            System.out.println("Fila inválida");
            return new ArrayList<>();
        }
        parametros.add(fila);
        System.out.println("Ingrese columna:");
        String columna = scanner.nextLine().trim();
        if (!columna.matches("\\d")) {
            System.out.println("Columna inválida");
            return new ArrayList<>();
        }
        parametros.add(columna);
        System.out.println("Ingrese tamaño:");
        String tamanio = scanner.nextLine().trim();
        if (!tamanio.matches(PASOS_REGEX)) {
            System.out.println("Tamaño inválido");
            return new ArrayList<>();
        }
        parametros.add(tamanio);
        System.out.println("Ingrese pasos:");
        String pasos = scanner.nextLine().trim();
        if (!pasos.matches(PASOS_REGEX)) {
            System.out.println("Pasos inválidos");
            return new ArrayList<>();
        }
        parametros.add(pasos);
        return parametros;
    }
}
