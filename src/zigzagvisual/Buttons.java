package zigzagvisual;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Buttons {
	
	private JButton button;
	private Puntos punto;
	private JFrame frame;
	private Jugar jugar;
		
	public Buttons(JButton boton, Puntos punto, JFrame frame, Jugar jugar) {
		this.setButton(boton);
		this.punto = punto;
		this.frame = frame;
		this.jugar = jugar;
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jugar.setSelButton(punto.getFila(),punto.getColumna());	
		}});
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	public int getFila() {
		return punto.getFila();
	}

	public int getColumna() {
		return punto.getColumna();
	}
	
	public Puntos getPunto() {
		return this.punto;
	}
	
	
}

class Labbels {
	
	private JLabel label;
	private JFrame frame;
	private int fila;
	private int columna;
		
	public Labbels(JLabel label, JFrame frame, int fila, int columna) {
		this.setLabel(label);
		this.frame = frame;
		this.fila = fila;
		this.columna = columna;
	}

	/**
	 * @return the label
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}

	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}

	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}
}
