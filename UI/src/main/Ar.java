package main;

import message.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Ar extends JFrame implements ActionListener, WindowListener {

	private Interface parent;

	JButton btnGetTemperatura;
	JButton btnSetTemperatura;

	public Ar(Interface parent) {
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

		this.parent = parent;

	}

	public void actionPerformed(ActionEvent e) {
		Object src;
		String s;
		double d;

		src = e.getSource();
		if(src == btnGetTemperatura) {
			try {
				s = new Message(new Message("INTERFACE_CLIENTE", "GET_TEMP", "").send(this.parent.serverAddr, this.parent.serverPort)).getBody();
				JOptionPane.showMessageDialog(this, s, "Temperatura", JOptionPane.INFORMATION_MESSAGE);
			} catch(Exception exc) {
				JOptionPane.showMessageDialog(this, "Não foi possível comunicar com o gerenciador!", "Temperatura", JOptionPane.ERROR_MESSAGE);
			}
		} else if(src == btnSetTemperatura) {
			try {
				s = (String) JOptionPane.showInputDialog(this, "Qual a nova temperatura? (entre com um numero real entre 10-25, em graus)", "Temperatura", JOptionPane.PLAIN_MESSAGE);
				if (s != null) {
					d = Double.parseDouble(s);
					if(d < 10 || d > 25) {
						throw new NumberFormatException();
					}
					s = new Message(new Message("INTERFACE_CLIENTE", "SET_TEMP", Double.toString(d)).send(this.parent.serverAddr, this.parent.serverPort)).getBody();
					JOptionPane.showMessageDialog(this, s, "Temperatura", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch(NumberFormatException exc) {
				JOptionPane.showMessageDialog(this, "Numero invalido!", "Temperatura", JOptionPane.ERROR_MESSAGE);
			} catch(Exception exc) {
				JOptionPane.showMessageDialog(this, "Não foi possível comunicar com o gerenciador!", "Temperatura", JOptionPane.ERROR_MESSAGE);
			}
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
