package main;

import sistema.Iluminacao;
import console.Console;

public class Main {

	public static void main(String[] args) {
		Console console = Console.getInstance();
		Iluminacao iluminacao = new Iluminacao();
		while (true) {
		
			int read = console.readInt("1 - connect\n2 - answer connect");
			
			if (read == 1) {
				//TODO if is connected continue
				iluminacao.setServer(console.readLine("Server IP: "));
				iluminacao.connect();
			} else if (read == 2) {
				iluminacao.answerChange(console.readString("Action: "), console.readChar("Result: "));
			}
		}
	}

}
