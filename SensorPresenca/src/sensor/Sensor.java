package sensor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sensor {

	private String server;
	private int port = 9000;
	private int id = 0;
	private Socket socket;
	private OutputStreamWriter osw;
	private BufferedWriter bw;
	
	public void connect() {
		try {
			System.out.println("connecting to " + server);
			startBuffers();
			bw.write("SALA_INTEL\n");
			bw.write("TYPE: SENSOR_PRESENCA\n");
			bw.write("ID: " + id++ + "\n");
			bw.write("LEN: " + 0 + "\n");
			bw.write("ACTION: CONNECT\n");
			
			closeBuffers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void startBuffers() throws UnknownHostException, IOException,
			UnsupportedEncodingException {
		socket = new Socket(server, port);
		osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
		bw = new BufferedWriter(osw);
	}
	
	public void sendPresence() {
		try {
			startBuffers();
			
			bw.write("SALA_INTEL\n");
			bw.write("TYPE: SENSOR_PRESENCA\n");
			bw.write("ID: " + id++ + "\n");
			bw.write("LEN: " + 0 + "\n");
			bw.write("ACTION: DETECTED\n");
			
			closeBuffers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeBuffers() throws IOException {
		bw.flush();
		bw.close();
		osw.close();
		socket.close();
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
