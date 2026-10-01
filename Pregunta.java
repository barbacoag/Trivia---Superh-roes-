import java.util.ArrayList;

/**
 * Representa una pregunta de la trivia: enunciado, categoría,
 * nivel de dificultad (1, 2 o 3) y su lista de opciones.
 */
public class Pregunta {

    // ===== Atributos privados (encapsulamiento) =====
    private String enunciado;
    private String categoria;
    private int nivelDificultad;          // 1 = Fácil, 2 = Intermedio, 3 = Avanzado
    private ArrayList<Opcion> opciones;   // Estructura de datos: lista de opciones

    // ===== Constructor =====
    public Pregunta(String enunciado, String categoria, int nivelDificultad) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.nivelDificultad = nivelDificultad;
        this.opciones = new ArrayList<>();
    }

    // ===== Getters y Setters =====
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(int nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public ArrayList<Opcion> getOpciones() {
        return opciones;
    }

    public int getCantidadOpciones() {
        return opciones.size();
    }

    // ===== Métodos propios =====

    /** Agrega una opción de respuesta a la pregunta. */
    public void agregarOpcion(Opcion opcion) {
        opciones.add(opcion);
    }

    /**
     * Verifica si la opción elegida es la correcta.
     * @param indice número de opción tal como lo ve el jugador (empieza en 1).
     * @return true si es la respuesta correcta.
     */
    public boolean esRespuestaCorrecta(int indice) {
        // El jugador ve las opciones numeradas desde 1, pero el ArrayList empieza en 0
        if (indice < 1 || indice > opciones.size()) {
            return false;
        }
        return opciones.get(indice - 1).esCorrecta();
    }

    /** Devuelve el texto de la opción correcta (para mostrarlo si el jugador falla). */
    public String getTextoRespuestaCorrecta() {
        for (int i = 0; i < opciones.size(); i++) {
            if (opciones.get(i).esCorrecta()) {
                return opciones.get(i).getTexto();
            }
        }
        return "(sin respuesta correcta cargada)";
    }

    /** Puntos que otorga la pregunta: nivel de dificultad * 10. */
    public int getPuntos() {
        return nivelDificultad * 10;
    }

    /** Traduce el número de dificultad a un nombre legible. */
    public String getNombreDificultad() {
        switch (nivelDificultad) {
            case 1:
                return "Fácil";
            case 2:
                return "Intermedio";
            case 3:
                return "Avanzado";
            default:
                return "Desconocido";
        }
    }

    /** Muestra la pregunta con sus opciones numeradas en consola. */
    public void mostrar() {
        System.out.println("[" + categoria + " | " + getNombreDificultad()
                + " | " + getPuntos() + " pts]");
        System.out.println(enunciado);
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println("   " + (i + 1) + ") " + opciones.get(i).getTexto());
        }
    }
}
