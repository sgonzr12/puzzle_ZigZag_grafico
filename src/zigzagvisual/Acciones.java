package zigzagvisual;

import javax.swing.*;

public class Acciones {

	private Buttons btnPulsado;
	private Buttons btnAnterior;
	private Labbels LblModif;
	private String connector;
	private int visitado;
	
	public Acciones(Buttons btnPulsado, Buttons btnAnterior, Labbels lblModif) {
		this.btnPulsado = btnPulsado;
		this.btnAnterior = btnAnterior;
		this.LblModif = lblModif;
		this.connector = lblModif.getLabel().getText();
		this.visitado = btnPulsado.getPunto().getVisitado();
	}

	public Buttons getBtnPulsado() {
		return btnPulsado;
	}

	public Buttons getBtnAnterior() {
		return btnAnterior;
	}

	public Labbels getLblModif() {
		return LblModif;
	}

	public String getConnector() {
		return connector;
	}

	public int getVisitado() {
		return visitado;
	}
	
	
	
}
