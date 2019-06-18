package main;

import java.util.Scanner;

import console.Console;
import leitor.Leitor;

public class Main {

	public static void main(String[] args) {
		Console console = Console.getInstance();
		Leitor leitor = new Leitor();
		while (true) {
		
			int read = console.readInt("1 - connect\n2 - send read");
			
			if (read == 1) {
				//TODO if is connected continue
				leitor.setServer(console.readLine("Server IP: "));
				leitor.connect();
			} else if (read == 2) {
				leitor.sendRead(console.readString("NUSP: "), console.readLine("Nome: "));
				
			}
		}
	}

}
