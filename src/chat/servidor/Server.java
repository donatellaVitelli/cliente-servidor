package chat.servidor;

import chat.User;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    //SALAS DE CHAT DISPONIBLES
    public static ArrayList<Socket> listaDeConexionesSala1 = new ArrayList<>();
    public static ArrayList<Socket> listaDeConexionesSala2 = new ArrayList<>();
    public static ArrayList<Socket> listaDeConexionesSala3 = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        try {
            final int PORT = 4445;
            ServerSocket server = new ServerSocket(PORT); //CREO EL SERVER CON EL PUERTO A UTILIZAR. ESTE VA A SER IGUAL PARA TODOS
            ObjectMapper mapper = new ObjectMapper();

            while (true) {
                System.out.println("Esperando un cliente");
                Socket socket = server.accept();

                Scanner sc = new Scanner(socket.getInputStream());
                String input = sc.nextLine();

                User user = mapper.readValue(input, User.class);

                ServerThread chat;

                switch (user.getSala()) {//DEPENDIENDO LA SALA ELEGIDA HAGO.
                    case 1:
                        listaDeConexionesSala1.add(socket);
                        chat = new ServerThread(socket, listaDeConexionesSala1, user.getNombre());//CREO UN NUEVO "CHAT"
                        Thread nuevoProcesoParalelo1 = new Thread(chat); // GENERO UN NUEVO THREAD PARA CORRER EL "CHAT" CREADO.
                        nuevoProcesoParalelo1.start(); // HAGO CORRER EL THREAD DEL NUEVO PROCESO.
                        break;
                    case 2:
                        listaDeConexionesSala2.add(socket);
                        chat = new ServerThread(socket, listaDeConexionesSala2, user.getNombre());
                        Thread nuevoProcesoParalelo2 = new Thread(chat);
                        nuevoProcesoParalelo2.start();
                        break;
                    case 3:
                        listaDeConexionesSala3.add(socket);
                        chat = new ServerThread(socket, listaDeConexionesSala3, user.getNombre());
                        Thread nuevoProcesoParalelo3 = new Thread(chat);
                        nuevoProcesoParalelo3.start();
                        break;
                    default:
                        break;
                }
                System.out.println("Client conectado a Sala " + user.getSala() + " desde: " + socket.getLocalAddress().getHostName());


            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
