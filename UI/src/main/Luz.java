package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.text.NumberFormatter;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Luz extends JFrame {
	
	private JTextField fileira;
	
	public Luz() {
		setResizable(false);
		setSize(325, 300);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setTitle("Luzes");
		getContentPane().setLayout(null);
		
		JRadioButton rdbtnAcender = new JRadioButton("Acender");
		rdbtnAcender.setBounds(105, 88, 127, 25);
		rdbtnAcender.setSelected(true);
		getContentPane().add(rdbtnAcender);
		
		JRadioButton rdbtnApagar = new JRadioButton("Apagar");
		rdbtnApagar.setBounds(105, 118, 127, 25);
		getContentPane().add(rdbtnApagar);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAcender);
		group.add(rdbtnApagar);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(105, 152, 97, 25);
		getContentPane().add(btnConfirmar);
		
		fileira = new JTextField();
		fileira.setBounds(105, 64, 116, 22);
		getContentPane().add(fileira);
		fileira.setColumns(10);
		
		JLabel lblNumeroDaFileira = new JLabel("Numero da fileira");
		lblNumeroDaFileira.setBounds(105, 40, 116, 16);
		getContentPane().add(lblNumeroDaFileira);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(105, 190, 97, 25);
		getContentPane().add(btnVoltar);
		
		btnVoltar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Interface().setVisible(true);
			}
		});
		
		btnConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int n;
				
				try {
					n = Integer.parseInt(fileira.getText());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Digite um valor de fileira valido (de 0 a quantidade de fileiras - 1)");
					fileira.setText("");
					return;
				}
				
				if(n < 0) {
					JOptionPane.showMessageDialog(null, "Digite um valor de fileira valido (de 0 a quantidade de fileiras - 1)");
					fileira.setText("");
					return;
				}
				
				if (rdbtnAcender.isSelected()) {
					System.out.println("acender " + n);
				} else if (rdbtnApagar.isSelected()) {
					System.out.println("apagar " + n);
				}
				
			}
		});
		
	}
}
