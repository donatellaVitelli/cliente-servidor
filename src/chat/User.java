package chat;

import java.io.Serializable;

/**
 * Created by gustavo on 29/10/2016.
 */
public class User implements Serializable {
    private int sala;
    private String nombre;

    public User() {
        
    }

    public User(int sala, String nombre) {
        this.nombre = nombre;
        this.sala = sala;
    }

    public int getSala() {
        return this.sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
