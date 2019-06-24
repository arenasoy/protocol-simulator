package main;

import message.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

@SuppressWarnings("serial")
public class Interface extends JFrame {

	private static final String ADDR_REGEX = "^\\s*([^:]+):([0-9]+)\\s*$";
	private static final Pattern ADDR_PATTERN = Pattern.compile(ADDR_REGEX);

	public String serverAddr;
	public int serverPort;

	public Interface() {
		String s;
		Matcher m;

		do {
			s = (String) JOptionPane.showInputDialog(this, "Qual o endereco do servidor do gerenciador? (formato = endereco:porta)", "Gerenciador", JOptionPane.PLAIN_MESSAGE);
			if(s == null) {
				System.exit(0);
			}
			m = ADDR_PATTERN.matcher(s);
			if (!m.matches()) {
				JOptionPane.showMessageDialog(this, "Endereco invalido! Deve ser no formato endereco:porta. Exemplos: dominio.com:9000 ou 192.168.0.110:8500.", "Gerenciador", JOptionPane.WARNING_MESSAGE);
			}
		} while (s == null || !m.matches());

		this.serverAddr = m.group(1);
		this.serverPort = Integer.parseInt(m.group(2));

		try {
			if(!new Message(new Message("INTERFACE_CLIENTE", "CONNECT", "").send(this.serverAddr, this.serverPort)).getBody().equals("1")) {
				throw new Exception("Not connected");
			}
		} catch(Exception exc) {
			JOptionPane.showMessageDialog(this, "Não foi possível conectar ao gerenciador em " + this.serverAddr + ":" + this.serverPort + ".", "Gerenciador", JOptionPane.ERROR_MESSAGE);
			System.err.println(exc);
			exc.printStackTrace();
			System.exit(1);
		}

		setSize(600, 600);
		
		getContentPane().setLayout(null);
		
		setTitle("Gerenciador");
		
		JButton btnPresenca = new JButton("Ver lista de presenca");
		btnPresenca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ListaPresenca().setVisible(true);;
			}
		});
		
		btnPresenca.setBounds(12, 13, 153, 102);
		getContentPane().add(btnPresenca);
		
		JButton btnProjetor = new JButton("Gerenciar projetor");
		btnProjetor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Projetor().setVisible(true);
			}
		});
		btnProjetor.setBounds(12, 138, 153, 102);
		getContentPane().add(btnProjetor);
		
		JButton btnAr = new JButton("Gerenciar ar-condicionado");
		btnAr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Ar().setVisible(true);
			}
		});
		btnAr.setBounds(267, 13, 153, 102);
		getContentPane().add(btnAr);
		
		JButton btnLuz = new JButton("Gerenciar luzes");
		btnLuz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Luz().setVisible(true);
			}
		});
		btnLuz.setBounds(267, 138, 153, 102);
		getContentPane().add(btnLuz);
		
		
	}
}
