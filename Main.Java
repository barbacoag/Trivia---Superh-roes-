import java.util.ArrayList;
import java.util.Scanner;

/**
 * Punto de entrada del programa: muestra el menú principal y
 * delega cada opción a la clase JuegoTrivia.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JuegoTrivia juego = new JuegoTrivia();
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcionMenu(scanner);

            switch (opcion) {
                case 1:
                    juego.registrarJugadores(scanner);
                    break;
                case 2:
                    juego.jugar(scanner);
                    break;
                case 3:
                    buscarPorCategoria(juego, scanner);
                    break;
                case 4:
                    mostrarInstrucciones();
                    break;
                case 5:
                    System.out.println("\n¡Gracias por jugar! El Reino de Consolandia te espera. Hasta la próxima.");
                    break;
                default:
                    System.out.println("[!] Opción no válida. Elegí un número del 1 al 5.");
            }
        } while (opcion != 5);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=========================================");
        System.out.println("   TRIVIA GAMER: El Legado del Rey Arcadio");
        System.out.println("=========================================");
        System.out.println(" 1. Registrar a los herederos");
        System.out.println(" 2. Enfrentar las pruebas del Rey");
        System.out.println(" 3. Buscar preguntas por categoría");
        System.out.println(" 4. Ver instrucciones");
        System.out.println(" 5. Salir");
        System.out.print("Elegí una opción: ");
    }

    /**
     * Lee la opción del menú con nextLine() (así el búfer queda limpio)
     * y la convierte a número. Si no es un número devuelve -1,
     * que cae en el "default" del switch.
     */
    private static int leerOpcionMenu(Scanner scanner) {
        String entrada = scanner.nextLine().trim();
        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void buscarPorCategoria(JuegoTrivia juego, Scanner scanner) {
        ArrayList<String> categorias = juego.getCategoriasDisponibles();
        System.out.println("\n--- BUSCAR PREGUNTAS POR CATEGORÍA ---");
        System.out.println("Categorías disponibles: " + categorias);
        System.out.print("Escribí una categoría: ");
        String categoria = scanner.nextLine();

        ArrayList<Pregunta> resultado = juego.buscarPreguntaPorCategoria(categoria);

        if (resultado.isEmpty()) {
            System.out.println("[!] No se encontraron preguntas de la categoría \"" + categoria.trim() + "\".");
        } else {
            System.out.println("Se encontraron " + resultado.size() + " pregunta(s):");
            for (int i = 0; i < resultado.size(); i++) {
                Pregunta p = resultado.get(i);
                System.out.println("  " + (i + 1) + ". [" + p.getNombreDificultad() + "] "
                        + p.getEnunciado());
            }
        }
    }

    private static void mostrarInstrucciones() {
        System.out.println("\n--- INSTRUCCIONES ---");
        System.out.println("* El Rey Arcadio, soberano de los videojuegos, está por morir y");
        System.out.println("  pondrá a prueba a sus 2 herederos para elegir a quién le deja el trono.");
        System.out.println("* Registren a los 2 herederos con la opción 1. Juegan por turnos.");
        System.out.println("* En cada ronda, cada heredero recibe una pregunta DISTINTA, así nadie copia.");
        System.out.println("* Son 3 pruebas, de menor a mayor dificultad:");
        System.out.println("    Aprendiz = 10 pts | Caballero = 20 pts | Leyendas = 30 pts");
        System.out.println("* Respondé escribiendo el número de la opción (1 a 4) y Enter.");
        System.out.println("* Si escribís algo inválido, el juego te avisa y te deja volver a responder.");
        System.out.println("* Quien sume más puntos hereda el trono. Si empatan, ¡gobiernan juntos!");
    }
}
