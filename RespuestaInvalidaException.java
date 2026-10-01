/**
 * Excepción personalizada del juego.
 * Se lanza cuando el jugador responde algo inválido:
 *  - un número fuera del rango de opciones (ej. 7 cuando hay 4 opciones), o
 *  - un texto que no es un número (ej. "hola").
 *
 * Hereda de Exception, por lo tanto es una excepción "checked":
 * el método que la lanza debe declararla con "throws" y quien lo llama
 * está obligado a atraparla con try-catch.
 */
public class RespuestaInvalidaException extends Exception {

    public RespuestaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
