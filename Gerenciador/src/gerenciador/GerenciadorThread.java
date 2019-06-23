package gerenciador;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class GerenciadorThread extends Thread {

	protected Gerenciador main;
	protected InetAddress addr;
	protected Socket socket;
	int port;

	public GerenciadorThread(Socket socket, Gerenciador main) {
		this.socket = socket;
		this.addr = socket.getLocalAddress();
		this.port = socket.getPort();
		this.main = main;
	}

	public String getHostAddress() {
		return addr.getHostAddress();
	}

	public int getPort() {
		return this.port;
	}

	@Override
	public void run() {
		InputStream is = null;
		try
		{
			is = socket.getInputStream();
			BufferedReader entrada = new BufferedReader(new InputStreamReader(is));
			
			String line = "";
			
			while ((line = entrada.readLine()) != null)
			{
				System.out.println(line);
			}
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			w.write(new Message("GERENCIADOR", "CONNECT", "1").toString());
			w.flush();
			socket.shutdownOutput();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
