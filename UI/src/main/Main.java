package main;

import java.awt.Container;

public class Main {

	public static void novaTela(Container c) {
		c.removeAll();
		c.revalidate();
		c.repaint();
	}
	
	public static void main(String[] args) {
	
		Interface i = new Interface();
		i.setSize(600, 600);
		i.setVisible(true);
	}
	
}
