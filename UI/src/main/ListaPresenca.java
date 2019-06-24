package main;

import message.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ListaPresenca extends JFrame implements WindowListener {

	private Interface parent;

	JList lbl;

	public ListaPresenca(Interface parent) {
		String s;

		setTitle("Lista de presença");
		getContentPane().setLayout(new FlowLayout());
		setSize(500, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		setResizable(false);


		this.parent = parent;

		try {
			s = new Message(new Message("INTERFACE_CLIENTE", "LISTA", "").send(this.parent.serverAddr, this.parent.serverPort)).getBody();
			String[] names = s.trim().split("[\r\n]+");
			lbl = new JList(names);
			lbl.setPreferredSize(new Dimension(400, 530));
			getContentPane().add(lbl);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Não foi possível comunicar com o gerenciador!", "Lista", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
			dispose();
		}

	}

	public void windowClosing(WindowEvent e) {
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
