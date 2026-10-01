import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Clase principal de la lógica del juego.
 * Administra a los jugadores, el banco de preguntas y el desarrollo de la partida.
 */
public class JuegoTrivia {

    private static final int CANTIDAD_JUGADORES = 2;

    // ===== Atributos privados: estructuras de datos del juego =====
    private ArrayList<Jugador> jugadores;            // los 2 jugadores
    private Queue<Pregunta> mazoPreguntas;           // cola: orden en que salen las preguntas
    private ArrayList<Pregunta> repositorioGeneral;  // todas las preguntas cargadas

    // ===== Constructor =====
    public JuegoTrivia() {
        jugadores = new ArrayList<>();
        mazoPreguntas = new LinkedList<>();
        repositorioGeneral = new ArrayList<>();
        cargarPreguntas();
    }

    public boolean hayJugadoresRegistrados() {
        return jugadores.size() == CANTIDAD_JUGADORES;
    }

    // =====================================================================
    // 1) REGISTRO DE JUGADORES
    // =====================================================================
    public void registrarJugadores(Scanner scanner) {
        jugadores.clear();   // si se vuelve a registrar, se reemplazan los anteriores
        System.out.println("\n--- REGISTRO DE JUGADORES ---");

        for (int i = 1; i <= CANTIDAD_JUGADORES; i++) {
            String nombre = "";
            boolean nombreValido = false;

            while (!nombreValido) {
                System.out.print("Nombre del Jugador " + i + ": ");
                nombre = scanner.nextLine().trim();

                if (nombre.isEmpty()) {
                    System.out.println("[!] El nombre no puede estar vacío.");
                } else if (existeJugador(nombre)) {
                    System.out.println("[!] Ya hay un jugador con ese nombre. Elegí otro.");
                } else {
                    nombreValido = true;
                }
            }
            jugadores.add(new Jugador(nombre));
        }

        System.out.println("Jugadores registrados: " + jugadores.get(0).getNombre()
                + " vs " + jugadores.get(1).getNombre() + ". ¡Listos para jugar!");
    }

    /** Búsqueda secuencial de un jugador por nombre (evita nombres repetidos). */
    private boolean existeJugador(String nombre) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    // =====================================================================
    // 2) CARGA DE PREGUNTAS (4 por nivel = 12 preguntas = 6 rondas)
    // =====================================================================
    public void cargarPreguntas() {
        repositorioGeneral.clear();

        // ---------- NIVEL 1: FÁCIL (Prueba del Aprendiz) ----------
        agregarPregunta("En Super Mario, ¿qué objeto hace que Mario se vuelva más grande?",
                "Nintendo", 1, new String[]{"Una estrella", "Una flor de fuego", "Un champiñón", "Una moneda"}, 3);

        agregarPregunta("¿Qué objetos dorados recolecta Sonic mientras corre?",
                "Clásicos", 1, new String[]{"Monedas", "Anillos", "Estrellas", "Diamantes"}, 2);

        agregarPregunta("En Minecraft, ¿qué criatura verde se acerca en silencio y explota?",
                "PC", 1, new String[]{"Zombie", "Enderman", "Creeper", "Esqueleto"}, 3);

        agregarPregunta("¿Qué fruta recolecta Crash Bandicoot en sus aventuras?",
                "PlayStation", 1, new String[]{"Manzanas", "Frutas Wumpa", "Bananas", "Cerezas"}, 2);

        // ---------- NIVEL 2: INTERMEDIO (Prueba del Caballero) ----------
        agregarPregunta("¿Cómo se llama el héroe de la saga The Legend of Zelda?",
                "Nintendo", 2, new String[]{"Zelda", "Ganon", "Link", "Epona"}, 3);

        agregarPregunta("En Halo, ¿cómo se llama la inteligencia artificial que acompaña al Jefe Maestro?",
                "Xbox", 2, new String[]{"Cortana", "Siri", "GLaDOS", "Alexa"}, 1);

        agregarPregunta("En God of War (2018), ¿cómo se llama el hijo de Kratos?",
                "PlayStation", 2, new String[]{"Baldur", "Thor", "Mimir", "Atreus"}, 4);

        agregarPregunta("En Among Us, ¿cómo se llama al jugador que sabotea y elimina a la tripulación?",
                "PC", 2, new String[]{"Espía", "Impostor", "Fantasma", "Cazador"}, 2);

        // ---------- NIVEL 3: AVANZADO (Prueba del Heredero) ----------
        agregarPregunta("¿Qué villano busca apoderarse de la Trifuerza en la saga Zelda?",
                "Nintendo", 3, new String[]{"Bowser", "Ganon", "Dr. Eggman", "Ridley"}, 2);

        agregarPregunta("En The Last of Us, ¿cómo se llama la chica a la que Joel debe proteger?",
                "PlayStation", 3, new String[]{"Abby", "Dina", "Ellie", "Tess"}, 3);

        agregarPregunta("¿Cómo se llama el protagonista de la saga Gears of War?",
                "Xbox", 3, new String[]{"Master Chief", "Nathan Drake", "Kratos", "Marcus Fenix"}, 4);

        agregarPregunta("En Portal, ¿qué recompensa le promete GLaDOS a la protagonista?",
                "PC", 3, new String[]{"Un pastel", "Una medalla", "Una corona", "Un trofeo"}, 1);
    }

    /**
     * Método auxiliar para crear una pregunta con sus opciones de forma compacta.
     * @param indiceCorrecto posición de la opción correcta, empezando en 1.
     */
    private void agregarPregunta(String enunciado, String categoria, int nivel,
                                 String[] textosOpciones, int indiceCorrecto) {
        Pregunta pregunta = new Pregunta(enunciado, categoria, nivel);
        for (int i = 0; i < textosOpciones.length; i++) {
            boolean esCorrecta = (i + 1) == indiceCorrecto;
            pregunta.agregarOpcion(new Opcion(textosOpciones[i], esCorrecta));
        }
        repositorioGeneral.add(pregunta);
    }

    /**
     * Arma la cola de preguntas en orden de dificultad creciente:
     * primero todas las de nivel 1, después nivel 2 y al final nivel 3.
     * Dentro de cada nivel se mezclan para que cada partida sea distinta.
     */
    private void armarMazo() {
        mazoPreguntas.clear();

        for (int nivel = 1; nivel <= 3; nivel++) {
            ArrayList<Pregunta> delNivel = new ArrayList<>();
            for (int i = 0; i < repositorioGeneral.size(); i++) {
                if (repositorioGeneral.get(i).getNivelDificultad() == nivel) {
                    delNivel.add(repositorioGeneral.get(i));
                }
            }
            Collections.shuffle(delNivel);   // mezcla solo dentro del mismo nivel
            for (int i = 0; i < delNivel.size(); i++) {
                mazoPreguntas.offer(delNivel.get(i));   // offer() = encolar al final
            }
        }
    }

    // =====================================================================
    // 3) PARTIDA
    // =====================================================================
    public void jugar(Scanner scanner) {
        if (!hayJugadoresRegistrados()) {
            System.out.println("[!] Primero registrá a los jugadores (opción 1 del menú).");
            return;
        }

        // Preparar partida nueva
        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.get(i).reiniciarPuntaje();
        }
        armarMazo();

        int totalRondas = mazoPreguntas.size() / jugadores.size();
        int ronda = 1;
        int nivelActual = 0;   // para saber cuándo empieza una prueba nueva

        mostrarIntroduccion(scanner);

        // Mientras queden preguntas suficientes para que TODOS respondan una distinta
        while (mazoPreguntas.size() >= jugadores.size()) {
            // peek() mira la primera pregunta de la cola SIN sacarla
            int nivelDeEstaRonda = mazoPreguntas.peek().getNivelDificultad();
            if (nivelDeEstaRonda != nivelActual) {
                nivelActual = nivelDeEstaRonda;
                mostrarCapitulo(nivelActual);
            }

            System.out.println("\n============ RONDA " + ronda + " de " + totalRondas + " ============");

            for (int i = 0; i < jugadores.size(); i++) {
                Jugador jugadorActual = jugadores.get(i);
                Pregunta pregunta = mazoPreguntas.poll();   // poll() = sacar la primera de la cola

                System.out.println("\n>> Turno de " + jugadorActual.getNombre()
                        + " (" + jugadorActual.getPuntajeActual() + " pts)");
                pregunta.mostrar();

                // Se repite hasta que el jugador ingrese una respuesta válida
                int respuesta = 0;
                boolean respuestaValida = false;
                while (!respuestaValida) {
                    System.out.print("Tu respuesta: ");
                    try {
                        respuesta = leerRespuesta(scanner, pregunta.getCantidadOpciones());
                        respuestaValida = true;
                    } catch (RespuestaInvalidaException e) {
                        System.out.println("[!] " + e.getMessage());
                    }
                }

                if (pregunta.esRespuestaCorrecta(respuesta)) {
                    jugadorActual.sumarPuntos(pregunta.getPuntos());
                    System.out.println("¡CORRECTO! El Rey Arcadio asiente con orgullo. +"
                            + pregunta.getPuntos() + " puntos.");
                } else {
                    System.out.println("Incorrecto... El Rey suspira. La respuesta era: "
                            + pregunta.getTextoRespuestaCorrecta());
                }
            }

            mostrarMarcador();
            ronda++;

            if (mazoPreguntas.size() >= jugadores.size()) {
                System.out.print("\nPresioná Enter para la siguiente ronda...");
                scanner.nextLine();
            }
        }

        finalizar();
    }

    // =====================================================================
    // HISTORIA: El legado del Rey Arcadio
    // =====================================================================

    /** Cuenta la historia de fondo antes de empezar la partida. */
    private void mostrarIntroduccion(Scanner scanner) {
        String heredero1 = jugadores.get(0).getNombre();
        String heredero2 = jugadores.get(1).getNombre();

        System.out.println("\n=========================================");
        System.out.println("      EL LEGADO DEL REY ARCADIO");
        System.out.println("=========================================");
        System.out.println("En el Reino de Playlandia, donde cada rincón brilla con");
        System.out.println("luces de neón y música de 8 bits, reinó durante siglos");
        System.out.println("ARCADIO, el Rey de los Videojuegos.");
        System.out.println();
        System.out.println("Pero su barra de vida ya casi llega a cero...");
        System.out.println("Sin continues ni vidas extra, el viejo rey debe elegir");
        System.out.println("quién heredará su trono y su Joystick Dorado.");
        System.out.println();
        System.out.println("Dos herederos se presentan ante él: " + heredero1 + " y " + heredero2 + ".");
        System.out.println();
        System.out.println("REY ARCADIO: \"Un verdadero soberano no se mide por su fuerza,");
        System.out.println("  sino por cuánto conoce los mundos que gobierna.");
        System.out.println("  Superen mis tres pruebas... y el trono será suyo.\"");
        System.out.print("\nPresioná Enter para aceptar el desafío del Rey...");
        scanner.nextLine();
    }

    /** Anuncia cada una de las tres pruebas del Rey según el nivel. */
    private void mostrarCapitulo(int nivel) {
        System.out.println("\n*****************************************");
        switch (nivel) {
            case 1:
                System.out.println(" PRIMERA PRUEBA: El Salón del Aprendiz");
                System.out.println("*****************************************");
                System.out.println("REY ARCADIO: \"Empecemos por lo básico. Todo gran jugador");
                System.out.println("  conoce a los héroes que abrieron el camino.\"");
                break;
            case 2:
                System.out.println(" SEGUNDA PRUEBA: La Torre del Caballero");
                System.out.println("*****************************************");
                System.out.println("REY ARCADIO: \"Bien... pero conocer a los héroes no basta.");
                System.out.println("  Demuestren que recorrieron sus historias.\"");
                break;
            case 3:
                System.out.println(" PRUEBA FINAL: El Trono de las Leyendas");
                System.out.println("*****************************************");
                System.out.println("REY ARCADIO: \"*tose* Me queda poca energía...");
                System.out.println("  Solo un verdadero heredero conoce los secretos");
                System.out.println("  más profundos de mi reino. Esta es la última prueba.\"");
                break;
            default:
                System.out.println(" PRUEBA MISTERIOSA");
                System.out.println("*****************************************");
        }
    }

    /**
     * Lee la respuesta del jugador y la valida.
     * Se lee la línea completa con nextLine() para no dejar basura en el búfer
     * (el problema típico de mezclar nextInt() con nextLine()).
     * @throws RespuestaInvalidaException si no es un número o está fuera de rango.
     */
    private int leerRespuesta(Scanner scanner, int cantidadOpciones) throws RespuestaInvalidaException {
        String entrada = scanner.nextLine().trim();
        int opcion;

        try {
            opcion = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            throw new RespuestaInvalidaException("\"" + entrada + "\" no es un número válido. "
                    + "Ingresá un número del 1 al " + cantidadOpciones + ".");
        }

        if (opcion < 1 || opcion > cantidadOpciones) {
            throw new RespuestaInvalidaException("La opción " + opcion + " no existe. "
                    + "Ingresá un número del 1 al " + cantidadOpciones + ".");
        }
        return opcion;
    }

    private void mostrarMarcador() {
        System.out.println("\n--- Marcador ---");
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println("  " + jugadores.get(i));
        }
    }

    // =====================================================================
    // 4) ALGORITMO DE ORDENAMIENTO: BURBUJA (Bubble Sort) - de mayor a menor
    // =====================================================================
    public void ordenarJugadoresPorPuntaje() {
        int n = jugadores.size();
        for (int i = 0; i < n - 1; i++) {
            // En cada pasada, el menor puntaje "burbujea" hacia el final
            for (int j = 0; j < n - 1 - i; j++) {
                Jugador actual = jugadores.get(j);
                Jugador siguiente = jugadores.get(j + 1);
                if (actual.getPuntajeActual() < siguiente.getPuntajeActual()) {
                    // Intercambio (swap)
                    jugadores.set(j, siguiente);
                    jugadores.set(j + 1, actual);
                }
            }
        }
    }

    // =====================================================================
    // 5) ALGORITMO DE BÚSQUEDA SECUENCIAL por categoría
    // =====================================================================
    public ArrayList<Pregunta> buscarPreguntaPorCategoria(String categoria) {
        ArrayList<Pregunta> encontradas = new ArrayList<>();
        // Se recorre la lista completa, elemento por elemento, comparando la categoría
        for (int i = 0; i < repositorioGeneral.size(); i++) {
            Pregunta p = repositorioGeneral.get(i);
            if (p.getCategoria().equalsIgnoreCase(categoria.trim())) {
                encontradas.add(p);
            }
        }
        return encontradas;
    }

    /** Devuelve la lista de categorías sin repetir (para mostrarlas en el menú). */
    public ArrayList<String> getCategoriasDisponibles() {
        ArrayList<String> categorias = new ArrayList<>();
        for (int i = 0; i < repositorioGeneral.size(); i++) {
            String cat = repositorioGeneral.get(i).getCategoria();
            if (!categorias.contains(cat)) {
                categorias.add(cat);
            }
        }
        return categorias;
    }

    // =====================================================================
    // 6) FINAL DE LA PARTIDA
    // =====================================================================
    public void finalizar() {
        ordenarJugadoresPorPuntaje();

        System.out.println("\n=========================================");
        System.out.println("           POSICIONES FINALES");
        System.out.println("=========================================");
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println("  " + (i + 1) + "° puesto: " + jugadores.get(i));
        }

        Jugador primero = jugadores.get(0);
        Jugador segundo = jugadores.get(1);

        if (primero.getPuntajeActual() == segundo.getPuntajeActual()) {
            System.out.println("\n¡EMPATE con " + primero.getPuntajeActual() + " puntos cada uno!");
            System.out.println("\nREY ARCADIO: \"Jamás vi dos herederos tan parejos...");
            System.out.println("  Entonces que sea así: " + primero.getNombre() + " y " + segundo.getNombre());
            System.out.println("  gobernarán Consolandia juntos, en modo cooperativo.\"");
        } else {
            System.out.println("\n*** ¡GANADOR: " + primero.getNombre().toUpperCase()
                    + " con " + primero.getPuntajeActual() + " puntos! ***");
            System.out.println("\nREY ARCADIO: \"" + primero.getNombre() + "... demostraste estar a la altura.");
            System.out.println("  Te entrego mi Joystick Dorado y el trono de Consolandia.");
            System.out.println("  " + segundo.getNombre() + ", no te rindas: todo gran jugador");
            System.out.println("  tiene una segunda partida.\"");
        }
        System.out.println("\nY con una última sonrisa pixelada, el Rey Arcadio");
        System.out.println("apagó su consola para siempre... GAME OVER.");
        System.out.println("=========================================");
    }
}
