package Automaton.Parser;

/**
 * Created by Jimmy Ramos on 20-Mar-17.
 */
public class Temp {
    private int valor;
    private int profundidad;

    public Temp(int valor, int profundidad) {
        this.valor = valor;
        this.profundidad = profundidad;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
