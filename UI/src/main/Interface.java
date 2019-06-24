package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Interface extends JFrame {

	
	public Interface() {
		
		setSize(600, 600);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
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
