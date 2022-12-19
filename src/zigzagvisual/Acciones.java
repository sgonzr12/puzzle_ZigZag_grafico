package zigzagvisual;

import javax.swing.*;

public class Acciones {
	
	public Acciones() {
		
	}
	
	public boolean undo(){
		return true;
	}
	
	public boolean redo(){
		return true;
	}
}

class Unir extends Acciones{

	private Buttons btnPulsado;
	private Buttons btnAnterior;
	private Labbels LblModif;
	private String connector;
	private int visitado;
	private Jugar jugar;
	
	public Unir(Buttons btnPulsado, Buttons btnAnterior, Labbels lblModif, Jugar jugar) {
		this.btnPulsado = btnPulsado;
		this.btnAnterior = btnAnterior;
		this.LblModif = lblModif;
		this.connector = lblModif.getLabel().getText();
		this.visitado = btnPulsado.getPunto().getVisitado();
		this.jugar = jugar;
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
	
	@Override
	public boolean undo(){
		
		jugar.selButton[0] = this.getBtnAnterior();
		this.getBtnPulsado().getButton().setEnabled(true);
		this.getBtnPulsado().getPunto().setVisitado(0);
		this.getLblModif().getLabel().setText(" ");
		jugar.counter--;
		
		if(jugar.secuence.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public boolean redo() {
			
		jugar.selButton[0] = this.getBtnPulsado();
		this.getBtnPulsado().getButton().setEnabled(false);
		this.getBtnPulsado().getPunto().setVisitado(this.getVisitado());
		this.getLblModif().getLabel().setText(this.getConnector());
		jugar.counter++;
		
		if(jugar.undoSecuence.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
}
