package main;

import console.Console;
import gerenciador.Gerenciador;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		Gerenciador g;
		Console c;
		int p;

		c = Console.getInstance();

		System.out.println("Bem-vinde ao Servidor Gerenciador da Sala Inteligente.");
		System.out.println();

		do {
			p = c.readInt("Em qual porta o servidor do gerenciador deve iniciar? (recomendado = 9000)");
			if (p < 1 || p > 65535) {
				System.out.println("Porta inválida! Deve ser um valor entre 1-65535.");
			}
		} while(p < 1 || p > 65535);

		try {
			g = new Gerenciador(p);
			System.out.println("O servidor está hospedado em: " + g.getHostAddress() + ":" + g.getPort());
			g.execute();
		} catch(IOException ex) {
			System.err.println("Não foi possível iniciar o servidor. :(");
			System.err.println(ex);
			ex.printStackTrace();
		}
	}
	
}
