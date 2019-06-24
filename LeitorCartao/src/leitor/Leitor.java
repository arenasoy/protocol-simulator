package leitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import console.Console;

public class Leitor {

	private String serverAddress;
	private int serverPort, port;
	private ServerSocket socket;
	private InetAddress addr;

	/**
	 * Construtor padrao.
	 *
	 * @param serverAddress
	 *            endereco do servidor do gerenciador.
	 * @param serverPort
	 *            porta do servidor do gerenciador.
	 * @param port
	 *            porta local.
	 */
	public Leitor(String serverAddress, int serverPort, int port)
			throws IOException, IllegalArgumentException {
		String message;
		Socket socket;
		Message m;

		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.port = port;

		/* Tentar conectar ao gerenciador, mandando minha porta de servidor */
		message = new Message("LEITOR_CARTAO", "CONNECT",
				Integer.toString(port)).send(serverAddress, serverPort);
		m = new Message(message);
		if (!m.getBody().equals("1")) {
			throw new IOException("O gerenciador nao permitiu a minha conexao!");
		}

		this.socket = new ServerSocket(this.port);
		this.addr = Inet4Address.getLocalHost();
		if (this.addr == null) {
			this.addr = Inet6Address.getLocalHost();
		}
		if (this.addr == null) {
			this.addr = InetAddress.getLocalHost();
		}
		if (this.addr == null) {
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

		Message saida;

		Console console = Console.getInstance();

		while (true) {

			int nusp = console
					.readInt("Se desejar enviar nova leitura de cartao, informe o numero USP");
			String nome = console.readLine("Informe o nome");

			saida = new Message("LEITOR_CARTAO", "READ", nusp + " " + nome);

			try {
				saida.send(this.serverAddress, this.serverPort);
			} catch (IOException ex) {
				System.err
						.println("Alguem tentou conectar aqui no servidor, mas nao deu certo. :(");
				System.err.println(ex);
				ex.printStackTrace();
			}

		}
	}

}
