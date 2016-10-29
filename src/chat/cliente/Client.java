package chat.cliente;

import chat.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String server = "192.168.0.5";
        try {
            final int PORT = 444;
            Socket socket = new Socket(server, PORT);
            System.out.println("Te conectaste a: " + server);
            System.out.println("Seleccione una sala y nickName (Separados por espacio. Ejemplo 1 pepe)");
            Scanner sc = new Scanner(System.in);    // OBTENGO LA ENTRADA POR TECLADO DE CONSOLA.

            ObjectMapper mapper = new ObjectMapper();
            User user = new User(sc.nextInt(), sc.next());
            String jsonInString = mapper.writeValueAsString(user);

            PrintWriter out = new PrintWriter(socket.getOutputStream()); //OBTENGO EL CANAL DE SALIDA DEL SOCKET HACIA EL SERVIDOR
            out.println(jsonInString); // LE ENVIO EL MENSAJE DE SALA Y NICKNAME

            ClientThread newClient = new ClientThread(socket); // CREO UN "CLIENTE" EL CUAL SOLO ESTARA ENCARGADO DE HACER LA ESCUCHA CONTINUA
            Thread thread = new Thread(newClient);
            thread.start(); // INICIO EL THREAD

            String textoTeclado = "";
            while (!textoTeclado.equals("fin")) { //MIENTRAS NO ESCRIBA FIN PODRE ENVIAR LOS MENSAJES QUE QUIERA
                Scanner bufferDeTeclado = new Scanner(System.in);
                textoTeclado = bufferDeTeclado.nextLine();

                if (textoTeclado.equals("fin")) { // SI ESCRIBO FIN DESCONECTA TODO
                    newClient.desconectar();
                    bufferDeTeclado.close();
                } else {
                    newClient.enviarDatos(textoTeclado);
                }
            }

        } catch (Exception e) {

        }
    }
}
