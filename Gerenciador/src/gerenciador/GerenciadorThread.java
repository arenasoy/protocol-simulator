package gerenciador;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
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
		String s;
		int i;

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
			saida.setAction(entrada.getAction());
			saida.setId(entrada.getId());

			switch (entrada.getAction().toUpperCase()) {
				case "CONNECT":
					switch(entrada.getType().toUpperCase()) {
						case "SISTEMA_ILUMINACAO":
							newD = new Dispositivo(this.getHostAddress(),Integer.parseInt(entrada.getBody()));
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
							newD = new Dispositivo(this.getHostAddress(),Integer.parseInt(entrada.getBody()));
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
							newD = new Dispositivo(this.getHostAddress(),Integer.parseInt(entrada.getBody()));
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
						case "INTERFACE_CLIENTE":
							saida.setBody("1");
							break;
						case "LUZON":
							if(main.alimentadorLuzes == null) {
								saida.setBody("0");
							} else {
								try {
									saida.setBody(new Message(new Message("GERENCIADOR", "ON", entrada.getBody()).send(main.alimentadorAr.getAddress(), main.alimentadorAr.getPort())).getBody());
								} catch(Exception exc) {
									saida.setBody("0"); // Ele caiu! Reconectado.
								}
							}
							break;
						case "LUZOFF":
							if(main.alimentadorLuzes == null) {
								saida.setBody("0");
							} else {
								try {
									saida.setBody(new Message(new Message("GERENCIADOR", "OFF", entrada.getBody()).send(main.alimentadorAr.getAddress(), main.alimentadorAr.getPort())).getBody());
								} catch(Exception exc) {
									saida.setBody("0"); // Ele caiu! Reconectado.
								}
							}
							break;
						default:
							saida.setBody("0");
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
				case "LISTA":
					s = "";
					if(this.main.professor == null) {
						System.out.println("Não tem ninguém na lista de presença porque nenhum outro professor registrou presença antes! (número usp de professor = 1...10)");
					} else {
						for(i = 0; i < this.main.listaPresenca.size(); i++) {
							s += this.main.listaPresenca.get(i).toString();
						}
					}
					s = s.trim();
					saida.setBody(s);
					break;
				case "SET_TEMP":
					if(this.main.alimentadorAr == null) {
						saida.setBody("Erro: o alimentador do ar-condicionado não está conectado ao gerenciador!");
						break;
					}
					try {
						if(new Message(new Message("GERENCIADOR", "GET_TEMP", "").send(this.main.alimentadorAr.getAddress(), this.main.alimentadorAr.getPort())).getBody().equals("1")) {
							saida.setBody("Temperatura alterada com sucesso.");
						} else {
							saida.setBody("Erro: o alimentador do ar-condicionado não permitiu vocẽ mudar a temperatura por algum motivo! O ar-condicionado tá ligado?");
						}
					} catch(IOException exc) {
						saida.setBody("Erro: não foi possível conectar ao alimentador do ar-condicionado.");
						break;
					}
					break;
				case "GET_TEMP":
					if(this.main.alimentadorAr == null) {
						saida.setBody("Erro: o alimentador do ar-condicionado não está conectado ao gerenciador!");
						break;
					}
					try {
						saida.setBody("Temperatura: " + new Message(new Message("GERENCIADOR", "GET_TEMP", "").send(this.main.alimentadorAr.getAddress(), this.main.alimentadorAr.getPort())).getBody() + "ºC");
					} catch(IOException exc) {
						saida.setBody("Erro: não foi possível conectar ao alimentador do ar-condicionado.");
						break;
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
