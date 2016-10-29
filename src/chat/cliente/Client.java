package chat.cliente;

import chat.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String server = "192.168.0.5";
        ObjectMapper mapper = new ObjectMapper();
        try {
            final int PORT = 4445;
            Socket socket = new Socket(server, PORT);
            System.out.println("Te conectaste a: " + server);
            System.out.println("Seleccione una sala y nickName (Separados por espacio. Ejemplo 1 pepe)");
            Scanner sc = new Scanner(System.in);

            User user = new User(sc.nextInt(), sc.next());
            String jsonInString = mapper.writeValueAsString(user);

            PrintWriter out = new PrintWriter(socket.getOutputStream()); //OBTENGO EL CANAL DE SALIDA DEL SOCKET HACIA EL SERVIDOR
            out.println(jsonInString); // LE ENVIO EL MENSAJE DE SALA Y NICKNAME

            out.flush();

            ClientThread newClient = new ClientThread(socket);
            Thread thread = new Thread(newClient);
            thread.start();

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
