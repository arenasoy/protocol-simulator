package sensor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Sensor {

	private String server;
	private int port = 9000;
	private int id = 0;
	
	public void execute() {
		try {
			Socket socket = new Socket(server, port);
			OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write("SALA_INTEL\n");
			bw.write("TYPE: SENSOR_PRESENCA\n");
			bw.write("ID: " + id++ + "\n");
			bw.write("LEN: " + 0 + "\n");
			bw.write("ACTION: CONNECT\n");
			
			bw.flush();
			bw.close();
			//socket.close();
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
