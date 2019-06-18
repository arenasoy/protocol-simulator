package main;

import java.util.Scanner;

import chave.Chave;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Chave chave = new Chave();
		while (true) {
		
			System.out.println("1 - connect\n2 - send status");
			int read = s.nextInt();
			
			if (read == 1) {
				//if is connected continue
				System.out.println("Server IP:");
				s.nextLine();
				chave.setServer(s.nextLine());
				chave.connect();
			} else if (read == 2) {
				System.out.println("Status (ON/OFF):");
				s.nextLine();
				chave.sendStatus(s.nextLine());
			}
		}
	}

}
