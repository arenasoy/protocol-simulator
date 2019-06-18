package main;

import alimentador.Alimentador;
import console.Console;

public class Main {

	public static void main(String[] args) {
		Console console = Console.getInstance();
		Alimentador alimentador = new Alimentador();
		while (true) {
		
			int read = console.readInt("1 - connect\n2 - answer status\n3 - answer get screen\n4 - answer set screen");
			
			if (read == 1) {
				//TODO if is connected continue
				alimentador.setServer(console.readLine("Server IP: "));
				alimentador.connect();
			} else if (read == 2) {
				alimentador.answerChange(console.readString("Action: "), console.readChar("Result: "));
			} else if (read == 3) {
				alimentador.answerGetScreen(console.readInt("Actual screen: "));
			} else if (read == 4) {
				alimentador.answerSetScreen(console.readChar("1 - Success\n0 - Error"));
			}
		}
	}

}
