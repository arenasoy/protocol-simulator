package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ListaPresenca extends JFrame implements WindowListener {

	private JFrame parent;

	public ListaPresenca(JFrame parent) {
		setSize(600, 600);
		setTitle("Lista de presenca");
		addWindowListener(this);
		this.parent = parent;
	}

	public void windowClosing(WindowEvent e) {
		this.setVisible(false);
		this.parent.setVisible(true);
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
