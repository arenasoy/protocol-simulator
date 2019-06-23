package main;

import alimentador.Alimentador;
import java.util.regex.*;
import console.Console;

import java.io.IOException;

public class Main {

	private static final String ADDR_REGEX = "^\\s*([^:]+):([0-9]+)\\s*$";
	private static final Pattern ADDR_PATTERN = Pattern.compile(ADDR_REGEX);

	public static void main(String[] args) {
		Alimentador a;
		Console c;
		Matcher m;
		String s;
		int p;

		c = Console.getInstance();

		System.out.println("Bem-vinde ao Alimentador do Ar-condicionado.");
		System.out.println();

		do {
			p = c.readInt("Em qual porta o servidor do alimentador deve iniciar? (recomendado = 9001-9999)");

			if (p < 1 || p > 65535) {
				System.out.println("Porta inválida! Deve ser um valor entre 1-65535.");
			}
		} while(p < 1 || p > 65535);

		do {
			s = c.readLine("E qual o endereço do servidor do gerenciador? (formato = endereço:porta)");
			m = ADDR_PATTERN.matcher(s);
			if(s == null || !m.matches()) {
				System.out.println("Endereço inválido! Deve ser no formato endereço:porta. Exemplos: dominio.com:9000 ou 192.168.0.110:8500.");
			}
		} while(s == null || !m.matches());

		try {
			a = new Alimentador(m.group(1).trim(), Integer.parseInt(m.group(2)), p);
			System.out.println("O servidor está hospedado em: " + a.getHostAddress() + ":" + a.getPort());
			a.execute();
		} catch(Exception ex) {
			System.err.println("Não foi possível iniciar o servidor. :(");
			System.err.println(ex);
			ex.printStackTrace();
		}

	}

}
