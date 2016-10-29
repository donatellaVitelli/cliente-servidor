package chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws Exception{
		String server="127.0.0.1";
		try{
			final int PORT = 444;
			Socket socket = new Socket(server, PORT);
			System.out.println("Te conectaste a: "+server);
			System.out.println("Seleccione una sala y nickName (Separados por espacio. Ejemplo 1 pepe)");
			Scanner salaNickname = new Scanner(System.in);	// OBTENGO LA ENTRADA POR TECLADO DE CONSOLA.	
			
			PrintWriter salidaDatos = new PrintWriter(socket.getOutputStream()); //OBTENGO EL CANAL DE SALIDA DEL SOCKET HACIA EL SERVIDOR
			salidaDatos.println(salaNickname); // LE ENVIO EL MENSAJE DE SALA Y NICKNAME
						
			ClienteChat nuevoCliente = new ClienteChat(socket); // CREO UN "CLIENTE" EL CUAL SOLO ESTARA ENCARGADO DE HACER LA ESCUCHA CONTINUA
			Thread thread = new Thread(nuevoCliente);
			thread.start(); // INICIO EL THREAD
			
			String textoTeclado = "";
			while(!textoTeclado.equals("fin")){ //MIENTRAS NO ESCRIBA FIN PODRE ENVIAR LOS MENSAJES QUE QUIERA
				Scanner bufferDeTeclado = new Scanner(System.in);
				textoTeclado = bufferDeTeclado.nextLine();
				
				if(textoTeclado.equals("fin")){ // SI ESCRIBO FIN DESCONECTA TODO
					nuevoCliente.desconectar();
					bufferDeTeclado.close();
				}else{
					nuevoCliente.enviarDatos(textoTeclado);
				}
			}
			
		}catch(Exception e){
			
		}
	}
}
