package sensor;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import console.Console;

public class Sensor {

	private String serverAddress;
	private int serverPort, port;
	@SuppressWarnings("unused")
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
	public Sensor(String serverAddress, int serverPort, int port)
			throws IOException, IllegalArgumentException {
		String message;
		@SuppressWarnings("unused")
		Socket socket;
		Message m;

		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
		this.port = port;

		/* Tentar conectar ao gerenciador, mandando minha porta de servidor */
		message = new Message("SENSOR_PRESENCA", "CONNECT",
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

			boolean detected = console
					.readBoolean("Foi detectada presenca de pessoas?\n\t1 - Sim");
			
			if (!detected) continue;
			
			saida = new Message("SENSOR_PRESENCA", "DETECTED", "");

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
