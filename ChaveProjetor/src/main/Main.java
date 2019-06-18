package main;

import java.util.Scanner;

import console.Console;
import chave.Chave;

public class Main {

	public static void main(String[] args) {
		Console console = Console.getInstance();
		Chave chave = new Chave();
		while (true) {
			
			int read = console.readInt("1 - connect\n2 - send status");
			
			if (read == 1) {
				//TODO if is connected continue
				chave.setServer(console.readString("Server IP: "));
			} else if (read == 2) {
				chave.sendStatus(console.readString("Status (ON/OFF):"));
			}
		}
	}

}
