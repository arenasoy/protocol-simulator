package gerenciador;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GerenciadorThread extends Thread {

	protected Gerenciador main;
	protected InetAddress remoteAddr;
	protected Socket socket;
	int remotePort;

	public GerenciadorThread(Socket socket, Gerenciador main) {
		this.socket = socket;
		this.remoteAddr = socket.getInetAddress();
		this.remotePort = socket.getPort();
		this.main = main;
	}

	public String getHostAddress() {
		return remoteAddr.getHostAddress();
	}

	public int getPort() {
		return this.remotePort;
	}

	@Override
	public void run() {
		BufferedWriter outputStream;
		Message entrada, saida;
		Dispositivo newD;
		Uspiano u;
		Matcher m;

		try {
			this.main.mutex.acquire();
		} catch(InterruptedException ex) {
			System.err.println("A requisição não pode ser processada. Algum erro ocorreu com a thread. :(");
			System.err.println(ex);
			ex.printStackTrace();
			return;
		}

		saida = new Message("GERENCIADOR", "", "");
		try {
			entrada = Message.getFromSocket(this.socket);

			switch (entrada.getAction().toUpperCase()) {
				case "CONNECT":
					saida.setAction("CONNECT");
					newD = new Dispositivo(this.getHostAddress(),Integer.parseInt(entrada.getBody()));
					switch(entrada.getType().toUpperCase()) {
						case "SISTEMA_ILUMINACAO":
							if(main.alimentadorLuzes == null) {
								main.alimentadorLuzes = newD;
								saida.setBody("1");
							} else { // Opa! Já tem um sistema de luzes conectado. Precisamos ver se ele não caiu e é ele mesmo reconectando...
								try {
									new Message("GERENCIADOR", "", "").send(main.alimentadorLuzes.getAddress(), main.alimentadorLuzes.getPort());
									saida.setBody("0"); // Ele não caiu! Temos um impostor aqui!
								} catch(IOException exc) {
									main.alimentadorLuzes = newD;
									saida.setBody("1"); // Ele caiu! Reconectado.
								}
							}
							break;
						case "AR_CONDICIONADO":
							if(main.alimentadorAr == null) {
								main.alimentadorAr = newD;
								saida.setBody("1");
							} else { // Opa! Já tem um alimentador de ar condicionado conectado. Precisamos ver se ele não caiu e é ele mesmo reconectando...
								try {
									new Message("GERENCIADOR", "", "").send(main.alimentadorAr.getAddress(), main.alimentadorAr.getPort());
									saida.setBody("0"); // Ele não caiu! Temos um impostor aqui!
								} catch(IOException exc) {
									main.alimentadorAr = newD;
									saida.setBody("1"); // Ele caiu! Reconectado.
								}
							}
							break;
						case "PROJETOR":
							if(main.alimentadorProjetor == null) {
								main.alimentadorProjetor = newD;
								saida.setBody("1");
							} else { // Opa! Já tem um alimentador do projetor conectado. Precisamos ver se ele não caiu e é ele mesmo reconectando...
								try {
									new Message("GERENCIADOR", "", "").send(main.alimentadorProjetor.getAddress(), main.alimentadorProjetor.getPort());
									saida.setBody("0"); // Ele não caiu! Temos um impostor aqui!
								} catch(IOException exc) {
									main.alimentadorProjetor = newD;
									saida.setBody("1"); // Ele caiu! Reconectado.
								}
							}
							break;
						case "SENSOR_PRESENCA":
							saida.setBody("1");
							break;
						case "LEITOR_CARTAO":
							saida.setBody("1");
							break;
						case "CHAVE":
							saida.setBody("1");
							break;
						case "CLIENTE":
							saida.setBody("1");
					}
					break;
				case "DETECTED": // Sensor de presença
					saida = null; // não tem resposta pro sensor de presença.
					break;
				case "READ": // Leitor de cartão
					m = Pattern.compile("^[0-9]+\\s+.+$", Pattern.DOTALL).matcher(entrada.getBody());
					if(!m.matches()) {
						saida.setBody("0");
						break;
					}
					u = new Uspiano(m.group(1), m.group(2));
					if(u.getNumeroUSP() >= 1 && u.getNumeroUSP() <= 10) { // É professor.
						this.main.professor = u;
						this.main.listaPresenca = new ArrayList<>();
						break;
					}
					// É aluno.
					if(this.main.professor == null) {
						System.out.println("Não é possível registrar presença pro aluno, porque nenhum outro professor registrou presença antes! (número usp de professor = 1...10)");
						saida.setBody("0");
						break;
					}
					this.main.listaPresenca.add(u);
					break;
				case "ON": // Chave projetor
					saida = null; // não tem resposta pra chave do projetor.
					try {
						if(main.alimentadorProjetor != null) {
							new Message("GERENCIADOR", "ON", "").send(main.alimentadorProjetor.getAddress(), main.alimentadorProjetor.getPort());
						}
					} catch(IOException exc) {
						System.err.println("Falha ao estabelecer comunicação com o alimentador do projetor. :O");
						System.err.println(exc);
						exc.printStackTrace();
					}
					break;
				case "OFF":
					saida = null; // não tem resposta pra chave do projetor.
					try {
						if(main.alimentadorProjetor != null) {
							new Message("GERENCIADOR", "OFF", "").send(main.alimentadorProjetor.getAddress(), main.alimentadorProjetor.getPort());
						}
					} catch(IOException exc) {
						System.err.println("Falha ao estabelecer comunicação com o alimentador do projetor. :O");
						System.err.println(exc);
						exc.printStackTrace();
					}
					break;
			}
			if(saida != null) {
				outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
				outputStream.write(saida.toString());
				outputStream.flush();
			}
			socket.close();
		} catch (IOException ex) {
			System.err.println("A requisição não pode ser processada. :(");
			System.err.println(ex);
			ex.printStackTrace();
		}
		this.main.mutex.release();
	}
	
}
