package chat;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {
	//SALAS DE CHAT DISPONIBLES
	public static ArrayList<Socket> listaDeConexionesSala1 = new ArrayList<>();
	public static ArrayList<Socket> listaDeConexionesSala2 = new ArrayList<>();
	public static ArrayList<Socket> listaDeConexionesSala3 = new ArrayList<>();	
	
	public static void main(String[] args) throws Exception{
		try{
			final int PORT = 444;
			ServerSocket server = new ServerSocket(PORT); //CREO EL SERVER CON EL PUERTO A UTILIZAR. ESTE VA A SER IGUAL PARA TODOS
			
			
			while(true){//BUCLE INFINITO A LA ESPERA DE NUEVAS CONEXIONES
				System.out.println("Esperando un cliente");
				
				Socket socket = server.accept();//CREA UN NUEVO SOCKET Y QUEDA A LA ESPERA DE UNA NUEVA SOLICITUD DE CONEXION
				
				Scanner input = new Scanner(socket.getInputStream()); //LO USO PARA LEER LA SALA Y NICK ENVIADO POR EL CLIENTE
				String [] salaNick = input.nextLine().split(" ");
				int numeroSala = Integer.parseInt(salaNick[0]);
				String nickName = salaNick[1];
				
				ServidorChat chat;
				
				switch(numeroSala){//DEPENDIENDO LA SALA ELEGIDA HAGO.
				case 1: listaDeConexionesSala1.add(socket);
						chat = new ServidorChat(socket,listaDeConexionesSala1, nickName);//CREO UN NUEVO "CHAT"
						Thread nuevoProcesoParalelo1 = new Thread(chat); // GENERO UN NUEVO THREAD PARA CORRER EL "CHAT" CREADO.
						nuevoProcesoParalelo1.start(); // HAGO CORRER EL THREAD DEL NUEVO PROCESO.
						break;
				case 2: listaDeConexionesSala2.add(socket);
						chat = new ServidorChat(socket,listaDeConexionesSala2,nickName);
						Thread nuevoProcesoParalelo2 = new Thread(chat);
						nuevoProcesoParalelo2.start();
						break;
				case 3: listaDeConexionesSala3.add(socket);
						chat = new ServidorChat(socket,listaDeConexionesSala3,nickName);
						Thread nuevoProcesoParalelo3 = new Thread(chat);
						nuevoProcesoParalelo3.start();
						break;
				default: ;break;
				}
				System.out.println("Cliente conectado a Sala "+numeroSala+" desde: "+ socket.getLocalAddress().getHostName());

				
			}
		}catch(Exception e){
			
		}
	}
	

}
