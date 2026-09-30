import java.util.Scanner;

public class Jugador {
    private String nombre;
    private int puntajeActual;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntajeActual = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntajeActual() {
        return puntajeActual;
    }

    public void setPuntajeActual(int puntajeActual) {
        this.puntajeActual = puntajeActual;
    }

    public boolean responderPregunta(Scanner scanner, Pregunta pregunta)
            throws RespuestaInvalidaException {
        System.out.println(pregunta.getEnunciado());
        for (int i = 0; i < pregunta.getOpciones().size(); i++) {
            System.out.println((i + 1) + ". " + pregunta.getOpciones().get(i).getTexto());
        }

        System.out.print("Respuesta de " + nombre + ": ");
        String entrada = scanner.nextLine().trim();
        int indice;
        try {
            indice = Integer.parseInt(entrada);
        } catch (NumberFormatException excepcion) {
            throw new RespuestaInvalidaException("Debes ingresar un numero de opcion.");
        }

        boolean correcta = pregunta.esRespuestaCorrecta(indice);
        if (correcta) {
            puntajeActual += pregunta.getNivelDificultad() * 10;
        }
        return correcta;
    }

    public int obtenerPuntaje() {
        return puntajeActual;
    }
}