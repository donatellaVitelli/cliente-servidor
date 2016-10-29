package chat;

import java.io.Serializable;

/**
 * Created by gustavo on 29/10/2016.
 */
public class User implements Serializable {
    private String nombre;
    private int sala;

    public User(int sala, String nombre) {
        this.nombre = nombre;
        this.sala = sala;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSala() {
        return this.sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }
}
