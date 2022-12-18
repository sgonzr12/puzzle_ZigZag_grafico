package zigzagvisual;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Jugar {
	
	private JPanel panelJugar;
	private JFrame frame;
	private ArrayList<Integer> matrix;
	private int filas, columnas;
	private LinkedList<Buttons> problemaBtn;
	private LinkedList<Labbels> problemaLbl;
	private Buttons[] selButton;
	private int max =  0, min = Integer.MAX_VALUE;
	private LinkedList<Acciones> secuence;
	private LinkedList<Acciones> undoSecuence;
	private JMenuItem undoBtn;
	private JMenuItem redoBtn;
	
	
	/**
	 * Create the application.
	 */
	public Jugar(ArrayList<Integer> lista, int filas, int columnas) {
	
		secuence = new LinkedList<>();
		undoSecuence = new LinkedList<>();
		this.matrix = lista;
		System.out.println(matrix.toString());
		this.filas = filas;
		this.columnas = columnas;
		problemaBtn = new LinkedList<>();
		problemaLbl = new LinkedList<>();
		selButton = new Buttons[2];
		maxMin();
		initialize();
	}

	private void maxMin() {
		
		for(int valor: matrix)	
		if(valor > this.max) {
			this.max = valor;
		}else if(valor < this.min) {
			this.min = valor;
		}
	}
	
	private void initialize() {
		frame = new JFrame("ZigZag Grafico");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menubar = new JMenuBar();
		JMenu edicion = new JMenu("Edicion");
		undoBtn = new JMenuItem("Deshacer");
		undoBtn.setEnabled(false);

		redoBtn = new JMenuItem("Rehacer");
		redoBtn.setEnabled(false);
		
		undoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!undo()) {
					undoBtn.setEnabled(false);
				}
				if(!redoBtn.isEnabled()) {
					redoBtn.setEnabled(true);
				}
			}
		});
		
		redoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!redo()) {
					redoBtn.setEnabled(false);
				}
				if(!undoBtn.isEnabled()) {
					undoBtn.setEnabled(true);
				}
			}
		});
		
		frame.setJMenuBar(menubar);
		menubar.add(edicion);
		edicion.add(undoBtn);
		edicion.add(redoBtn);
		
		frame.repaint();
		frame.setVisible(true);
		
		JPanel panelNombre = new JPanel();
		frame.getContentPane().add(panelNombre, BorderLayout.NORTH);
		
		JLabel lblZigzagPuzzle = new JLabel("ZigZag Puzzle");
		panelNombre.add(lblZigzagPuzzle);
		
		panelJugar = new JPanel();
		panelJugar.setLayout(new GridLayout((filas*2)-1,(columnas*2)-1));
		frame.add(panelJugar);
		loadTable();
		
		JPanel panelBotones = new JPanel();
		frame.getContentPane().add(panelBotones, BorderLayout.SOUTH);
		
		JButton btnSolver = new JButton("auto resolver");
		btnSolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					@SuppressWarnings("rawtypes")
					final SwingWorker autosolve = new SwingWorker(){
						@Override
						protected Object doInBackground()throws Exception{
							autosolver();
							return null;
						}
					};
					autosolve.execute();
				} catch (Exception e2) {}
		}});
		panelBotones.add(btnSolver);
		

	}
	
	private void autosolver() {
		
		AutoSolver solver = new AutoSolver(filas, columnas);
		solver.matrizInit(matrix);
		boolean hasSolution = solver.zigZagVueltaAtras();
		if(hasSolution) {
			String solution = solver.toString();	
			JOptionPane.showMessageDialog(frame, solution);
		}else {
			String solution = "el problema introducido no tiene solución";	
			JOptionPane.showMessageDialog(frame, solution);
		}
		
		
	}
	
	private void loadTable(){
		ArrayList<Integer> lista = new ArrayList<>(); 
				
		for(int valor: matrix) {
			lista.add(valor);
		}
		
		for(int i = 0; i < (filas*2)-1;i++) {
			for(int j = 0; j < (columnas*2)-1; j++) {
				
				if(i%2 == 0 && j%2 == 0) {
					Integer valor = lista.remove(0);
					Buttons tmp = new Buttons(new JButton(valor.toString()), new Puntos(j,i,valor), frame, this);
					panelJugar.add(tmp.getButton());
					problemaBtn.add(tmp);
				}else{
					Labbels tmp = new Labbels(new JLabel(" "), frame, i, j);
					panelJugar.add(tmp.getLabel());
					problemaLbl.add(tmp);
				}
			}
		}
		frame.setVisible(true);
	}
	
	public void setSelButton(int fila, int columna) {
		if(selButton[0] == null) {
			selButton[0] = searchButton(fila, columna);
			selButton[0].getButton().setEnabled(false);
		}else {
			selButton[1] = searchButton(fila, columna);
			if(dibujarLinea()) {
				selButton[0] = selButton[1];
			}
		}
	}
	
	private Buttons searchButton(int fila, int columna) {
		for (Buttons boton: problemaBtn) {
			if(boton.getFila() == fila && boton.getColumna() == columna) {
				return boton;
			}
		}
		return null;
	}
	
	private boolean dibujarLinea() {
		
		if(secuencial()) {
			undoSecuence.clear();
			redoBtn.setEnabled(false);
			selButton[1].getButton().setEnabled(false);
			int fila = selButton[1].getFila();
			int columna = selButton[1].getColumna();
			Labbels lbl = null;
			switch (selButton[1].getPunto().getVisitado()) {
			case 1:
				lbl = searchLbl(fila-1,columna-1);
				lbl.getLabel().setText("\\");
				break;
			case 2:
				lbl = searchLbl(fila-1,columna);
				lbl.getLabel().setText("|");
				break;
			case 3:
				lbl = searchLbl(fila-1,columna+1);
				lbl.getLabel().setText("/");
				break;
			case 4:
				lbl = searchLbl(fila,columna-1);
				lbl.getLabel().setText("-");
				break;
			case 5:
				lbl = searchLbl(fila,columna+1);
				lbl.getLabel().setText("-");
				break;
			case 6:
				lbl = searchLbl(fila+1,columna-1);
				lbl.getLabel().setText("/");
				break;
			case 7:
				lbl =searchLbl(fila+1,columna); 
				lbl.getLabel().setText("|");
				break;
			case 8:
				lbl = searchLbl(fila+1,columna+1); 
				lbl.getLabel().setText("\\");
				break;
			}
			
			secuence.addLast(new Acciones(selButton[1], selButton[0], lbl));
			undoBtn.setEnabled(true);
			frame.repaint();
			return true;
		}else {
			String errorMessage = "Casilla no valida";
			JOptionPane.showMessageDialog(frame, errorMessage);
			return false;
		}
		
	}
	
	private Labbels searchLbl(int fila, int columna) {
		
		for(Labbels label: problemaLbl) {
			if(label.getFila() == fila && label.getColumna() == columna) {
				return label;
			}
		}
		return null;
	}
	
	private boolean secuencial() {
		Puntos siguiente = selButton[1].getPunto();
		Puntos anterior  = selButton[0].getPunto();
		int valor = anterior.getValor();
		int FActual = anterior.getFila();
		int CActual = anterior.getColumna();
		
		if(valor == max) {//si el valor del ultimo punto selecionado es maximo se pasa al minimo
			valor = min;
		}else {//en cualquier otro caso se incrementa en 1 el valor para el siguiente punto 
			valor++;
		}
		
		if(siguiente.getValor() == valor) {
			if(siguiente .getColumna()-2 == CActual && siguiente.getFila()-2 == FActual && !(comprobarCruce(siguiente, 1))) {
				siguiente.setVisitado(1);
				return true;
			}else if(siguiente .getColumna() == CActual && siguiente.getFila()-2 == FActual){
				siguiente.setVisitado(2);
				return true;
			}else if(siguiente .getColumna()+2 == CActual && siguiente.getFila()-2 == FActual && !(comprobarCruce(siguiente, 3))) {
				siguiente.setVisitado(3);
				return true;
			}else if(siguiente .getColumna()-2 == CActual && siguiente.getFila() == FActual){
				siguiente.setVisitado(4);
				return true;
			}else if(siguiente .getColumna()+2 == CActual && siguiente.getFila() == FActual){
				siguiente.setVisitado(5);
				return true;
			}else if(siguiente .getColumna()-2 == CActual && siguiente.getFila()+2 == FActual && !(comprobarCruce(siguiente, 6))) {
				siguiente.setVisitado(6);
				return true;
			}else if(siguiente .getColumna() == CActual && siguiente.getFila()+2 == FActual){
				siguiente.setVisitado(7);
				return true;
			}else if(siguiente .getColumna()+2 == CActual && siguiente.getFila()+2 == FActual && !(comprobarCruce(siguiente, 8))) {
				siguiente.setVisitado(8);
				return true;
			}
		}
		return false;
	}
	
private boolean comprobarCruce(Puntos siguiente, int posicion) {
		
		switch (posicion) {
		case 1:
			if(searchButton(siguiente.getFila(),siguiente.getColumna()-2).getPunto().getVisitado() == 3) {
				return true;
			}else if(searchButton(siguiente.getFila()-2,siguiente.getColumna()).getPunto().getVisitado() == 6) {
				return true;
			}
			return false;
		case 3:
			if(searchButton(siguiente.getFila(),siguiente.getColumna()+2).getPunto().getVisitado() == 1) {
				return true;
			}else if(searchButton(siguiente.getFila()-2,siguiente.getColumna()).getPunto().getVisitado() == 8) {
				return true;
			}
			return false;
		case 6:
			if(searchButton(siguiente.getFila(),siguiente.getColumna()-2).getPunto().getVisitado() == 8) {
				return true;
			}else if(searchButton(siguiente.getFila()+2,siguiente.getColumna()).getPunto().getVisitado() == 1) {
				return true;
			}
			return false;
		case 8:
			if(searchButton(siguiente.getFila(),siguiente.getColumna()+2).getPunto().getVisitado() == 6) {
				return true;
			}else if(searchButton(siguiente.getFila()+2,siguiente.getColumna()).getPunto().getVisitado() == 3) {
				return true;
			}
			return false;

		default:
			return false;
		}
	}

	private boolean undo() {
		
		Acciones action = secuence.removeLast();
		
		selButton[0] = action.getBtnAnterior();
		action.getBtnPulsado().getButton().setEnabled(true);
		action.getBtnPulsado().getPunto().setVisitado(0);
		action.getLblModif().getLabel().setText(" ");
		
		undoSecuence.addLast(action);
		
		if(secuence.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	private boolean redo() {
		
		Acciones action = undoSecuence.removeLast();
		
		selButton[0] = action.getBtnPulsado();
		action.getBtnPulsado().getButton().setEnabled(false);
		action.getBtnPulsado().getPunto().setVisitado(action.getVisitado());
		action.getLblModif().getLabel().setText(action.getConnector());
		
		secuence.addLast(action);
		
		if(undoSecuence.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}
