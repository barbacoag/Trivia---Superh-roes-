/**
 * Representa una opción de respuesta dentro de una Pregunta.
 * Cada opción tiene un texto y sabe si es la respuesta correcta o no.
 */
public class Opcion {

    // ===== Atributos privados (encapsulamiento) =====
    private String texto;
    private boolean esCorrecta;

    // ===== Constructor =====
    public Opcion(String texto, boolean esCorrecta) {
        this.texto = texto;
        this.esCorrecta = esCorrecta;
    }

    // ===== Getters y Setters =====
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Getter del atributo booleano. Por convención, los booleanos
     * usan un nombre tipo "es..." en lugar de "get...".
     */
    public boolean esCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    @Override
    public String toString() {
        return texto;
    }
}
