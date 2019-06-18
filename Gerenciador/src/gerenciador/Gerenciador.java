package gerenciador;

import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Gerenciador {

	private int port = 9000;
	private List<Socket> client = new ArrayList<Socket>();
	
	@SuppressWarnings("resource")
	public void execute() {
		ServerSocket socket = null;
		
		try {
			socket = new ServerSocket(port);
			System.out.println(Inet4Address.getLocalHost());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while (true) {
			try {
				Socket s = socket.accept();
				client.add(s);
				new GerenciadorThread(s).start();
				System.out.println("Cliente adicionado: " + s.getLocalAddress().getHostAddress());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
