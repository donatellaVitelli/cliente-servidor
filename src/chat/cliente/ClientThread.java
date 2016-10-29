package chat.cliente;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientThread implements Runnable { //PARA QUE TRABAJE COMO THREAD DEBE IMPLEMENTAR LA INTERFAZ RUNNABLE
    Socket socket;
    Scanner entradaDatos;
    PrintWriter salidaDatos;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            try {
                this.entradaDatos = new Scanner(this.socket.getInputStream()); // OBTENGO EL CANAL DE ENTRADA
                this.salidaDatos = new PrintWriter(this.socket.getOutputStream()); //OBTENGO EL CANAL DE SALIDA
                this.salidaDatos.flush();

                while (true) {//HAGO LA ESCUCHA PERMANENTE
                    recibirDatos();
                }
            } finally {
                this.socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void recibirDatos() {
        if (this.entradaDatos.hasNext()) {
            String mensajeEntrante = this.entradaDatos.nextLine();
            System.out.println(mensajeEntrante);
        }
    }

    public void enviarDatos(String mensajeSaliente) {
        this.salidaDatos.println(mensajeSaliente);
        this.salidaDatos.flush();
    }

    public void desconectar() throws Exception {
        this.salidaDatos.println(" se ha retirado de la sala");
        this.salidaDatos.flush();
        this.socket.close();
        System.exit(0);
    }
}
