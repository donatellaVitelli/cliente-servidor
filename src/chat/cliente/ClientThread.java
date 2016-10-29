package chat.cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientThread implements Runnable {
    private Socket socket;
    private Scanner sc;
    private PrintWriter out;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            this.sc = new Scanner(this.socket.getInputStream());
            this.out = new PrintWriter(this.socket.getOutputStream());
            this.out.flush();

            while (true) {
                recibirDatos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void recibirDatos() {
        if (this.sc.hasNext()) {
            String mensajeEntrante = this.sc.nextLine();
            System.out.println(mensajeEntrante);
        }
    }

    public void enviarDatos(String mensajeSaliente) {
        this.out.println(mensajeSaliente);
        this.out.flush();
    }

    public void desconectar() throws Exception {
        this.out.println(" se ha retirado de la sala");
        this.out.flush();
        this.socket.close();
        System.exit(0);
    }
}
