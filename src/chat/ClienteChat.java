package chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClienteChat implements Runnable{ //PARA QUE TRABAJE COMO THREAD DEBE IMPLEMENTAR LA INTERFAZ RUNNABLE
	Socket socket;
	Scanner entradaDatos;
	PrintWriter salidaDatos;
	
	public ClienteChat(Socket socket){
		this.socket=socket;
	}

	@Override
	public void run() {
		try{
			try{
				entradaDatos = new Scanner(socket.getInputStream()); // OBTENGO EL CANAL DE ENTRADA
				salidaDatos = new PrintWriter(socket.getOutputStream()); //OBTENGO EL CANAL DE SALIDA
				salidaDatos.flush();

				while(true){//HAGO LA ESCUCHA PERMANENTE
					recibirDatos();
				}			
			}finally{
				this.socket.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void recibirDatos(){
		if(entradaDatos.hasNext()){
			String mensajeEntrante = entradaDatos.nextLine();
			System.out.println(mensajeEntrante);
		}
	}
	
	public void enviarDatos(String mensajeSaliente){
		salidaDatos.println(mensajeSaliente);
		salidaDatos.flush();
	}
	
	public void desconectar()throws Exception{
		salidaDatos.println(" se ha retirado de la sala");
		salidaDatos.flush();
		socket.close();
		System.exit(0);
	}
}
