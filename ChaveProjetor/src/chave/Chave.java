package chave;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Chave {
	private String server;
	private int port = 9000;
	private int id = 0;
	private Socket socket;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	
	public void connect() {
		try {
			System.out.println("connecting to " + server);
			socket = new Socket(server, port);
			osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			bw = new BufferedWriter(osw);
			bw.write("SALA_INTEL\n");
			bw.write("TYPE: CHAVE\n");
			bw.write("ID: " + id++ + "\n");
			bw.write("LEN: " + 0 + "\n");
			bw.write("ACTION: CONNECT\n");
			
			bw.flush();
			//bw.close();
			//socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendStatus(String status) {
		try {
			bw.write("SALA_INTEL\n");
			bw.write("TYPE: CHAVE\n");
			bw.write("ID: " + id++ + "\n");
			bw.write("LEN: " + 0 + "\n");
			bw.write("ACTION: " + status + "\n");
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
