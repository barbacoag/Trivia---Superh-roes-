/**
 * Representa a un jugador de la trivia: su nombre y su puntaje acumulado.
 */
public class Jugador {

    // ===== Atributos privados (encapsulamiento) =====
    private String nombre;
    private int puntajeActual;

    // ===== Constructor =====
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntajeActual = 0;   // todo jugador arranca con 0 puntos
    }

    // ===== Getters y Setters =====
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

    // ===== Métodos propios =====

    /** Suma puntos al puntaje actual del jugador. */
    public void sumarPuntos(int puntos) {
        this.puntajeActual += puntos;
    }

    /** Vuelve el puntaje a 0 (se usa al empezar una partida nueva). */
    public void reiniciarPuntaje() {
        this.puntajeActual = 0;
    }

    @Override
    public String toString() {
        return nombre + " - " + puntajeActual + " pts";
    }
}
