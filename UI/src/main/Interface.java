package main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Interface extends JFrame {

	private Container container;
	
	public Interface() {
		
		container = getContentPane();
		container.setLayout(null);
		
		setTitle("Gerenciador");
		
		JButton btnPresenca = new JButton("Ver lista de presenca");
		btnPresenca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.novaTela(container);
			}
		});
		
		btnPresenca.setBounds(12, 13, 153, 102);
		getContentPane().add(btnPresenca);
		
		JButton btnProjetor = new JButton("Gerenciar projetor");
		btnProjetor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.novaTela(container);
			}
		});
		btnProjetor.setBounds(12, 138, 153, 102);
		getContentPane().add(btnProjetor);
		
		JButton btnAr = new JButton("Gerenciar ar-condicionado");
		btnAr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.novaTela(container);
			}
		});
		btnAr.setBounds(267, 13, 153, 102);
		getContentPane().add(btnAr);
		
		JButton btnLuz = new JButton("Gerenciar luzes");
		btnLuz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.novaTela(container);
			}
		});
		btnLuz.setBounds(267, 138, 153, 102);
		getContentPane().add(btnLuz);
		
		
	}
}
