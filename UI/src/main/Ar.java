package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Ar extends JFrame implements ActionListener, WindowListener {

	private JFrame parent;

	JButton btnGetTemperatura;
	JButton btnSetTemperatura;

	public Ar(JFrame parent) {
		setSize(200, 400);
		setTitle("Gerenciador do ar-condicionado");
		getContentPane().setLayout(null);

		btnGetTemperatura = new JButton("Obter temperatura");
		btnGetTemperatura.addActionListener(this);

		btnGetTemperatura.setBounds(5, 5, 190, 190);
		getContentPane().add(btnGetTemperatura);

		btnSetTemperatura = new JButton("Mudar temperatura");
		btnSetTemperatura.addActionListener(this);
		btnSetTemperatura.setBounds(5, 200, 190, 190);
		getContentPane().add(btnSetTemperatura);

	}

	public void actionPerformed(ActionEvent e) {
		Object src;

		src = e.getSource();
		if(src == btnGetTemperatura) {
			this.setVisible(false);
			new ListaPresenca(this).setVisible(true);
		} else if(src == btnSetTemperatura) {
			this.setVisible(false);
			new Projetor().setVisible(true);
		}
	}

	public void windowClosing(WindowEvent e) {
		this.parent.setVisible(true);
		this.setVisible(false);
		dispose();
	}

	public void windowOpened(WindowEvent e) {
	}
	public void windowClosed(WindowEvent e) {
	}
	public void windowIconified(WindowEvent e) {
	}
	public void windowDeiconified(WindowEvent e) {
	}
	public void windowActivated(WindowEvent e) {
	}
	public void windowDeactivated(WindowEvent e) {
	}
}
