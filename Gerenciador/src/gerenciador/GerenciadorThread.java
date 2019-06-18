package gerenciador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class GerenciadorThread extends Thread {

	protected Socket socket;

	public GerenciadorThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run()
	{
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
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
