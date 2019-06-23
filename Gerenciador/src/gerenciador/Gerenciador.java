package gerenciador;


import java.io.IOException;
import java.net.*;
import java.util.ArrayList;


public class Gerenciador {

	private ServerSocket socket;
	private InetAddress addr;
	private int port;

	private ArrayList<Aluno> listaPresenca;

	public Gerenciador() throws IOException {
		this(9000);
	}

	public Gerenciador(int port) throws IOException {
		this.port = port;
		this.socket = new ServerSocket(this.port);
		this.addr = Inet4Address.getLocalHost();
		if(this.addr == null) {
			this.addr = Inet6Address.getLocalHost();
		}
		if(this.addr == null) {
			this.addr = InetAddress.getLocalHost();
		}
		if(this.addr == null) {
			throw new UnknownHostException();
		}
	}

	public String getHostAddress() {
		return addr.getHostAddress();
	}

	public int getPort() {
		return this.port;
	}

	public void execute() {
		GerenciadorThread t;

		while (true) {
			try {
				t = new GerenciadorThread(socket.accept(), this);
				System.out.println("Requisição: " + t.getHostAddress() + ":" + t.getPort());
				t.run();
			} catch(IOException ex) {
				System.err.println("Alguém tentou conectar aqui no servidor, mas não deu certo. :(");
				System.err.println(ex);
				ex.printStackTrace();
			}
		}
	}

	private void marcarPresenca(Aluno a) {
		listaPresenca.add(a);
	}

	/**
	 * Este método a thread cliente chama pra indicar que acabou a requisição do cliente.
	 */
	public void disconnected() {
		;
	}
	
}
