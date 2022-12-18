package zigzagvisual;

import java.util.ArrayList;
import java.util.LinkedList;

public class AutoSolver {


	private int n,m;
	private int max =  0, min = Integer.MAX_VALUE;
	private LinkedList<Puntos> disponibles, seleccionados;
	private LinkedList<LinkedList<Puntos>> soluciones;

	public AutoSolver(int n, int m) {
		
		this.n = n;
		this.m = m;
		this.disponibles = new LinkedList<>();
		this.seleccionados = new LinkedList<>();
		this.soluciones= new LinkedList<>();
		
	}
	
	public void matrizInit(ArrayList<Integer> lista) {
		int indice = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				disponibles.add(new Puntos(j, i, lista.get(indice)));
				indice++;
			}
		}
		
		seleccionados.add(disponibles.getFirst());
		
		maxMin();
	}
	
	private void maxMin() {
				
		for(Puntos posicion: disponibles)	
		if(posicion.getValor()>this.max) {
			this.max = posicion.getValor();
		}else if(posicion.getValor()<this.min) {
			this.min = posicion.getValor();
		}
	}
	
	public boolean isMaxMin() {
		
		if(max == min) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public boolean zigZagVueltaAtras() {
		
		if(seleccionados.getLast() == disponibles.getLast() && seleccionados.size() == n*m) {
			LinkedList<Puntos> nuevo = new LinkedList<>();
			
			for (int i = 0; i < n*m; i++) {
				Puntos nuevoP = seleccionados.get(i);
				nuevo.add(new Puntos(nuevoP.getColumna(), nuevoP.getFila(), nuevoP.getValor(), nuevoP.getVisitado()));
			}
			
			soluciones.add(nuevo);
			
			return true;
			
		}else {//solucion parcial no completa
			for(int i = 1; i < n*m; i++) {//para todas las fichas disponibles
				
				Puntos siguiente = disponibles.get(i);//seleccionar la siguiente
				//comprobar si la ficha seleccionada vale como solucion parcial
				if (siguiente.getVisitado() == 0 && secuencial(siguiente)) {
					seleccionados.add(siguiente);
					boolean finalizado = zigZagVueltaAtras();
					if(finalizado) {
						return true;
					}
					seleccionados.removeLast();
					siguiente.setVisitado(0);
				}
			}
			return false;
		} 
	}
 
	private boolean secuencial(Puntos siguiente) {
		Puntos anterior  = seleccionados.getLast();
		int valor = anterior.getValor();
		int FActual = anterior.getFila();
		int CActual = anterior.getColumna();
		
		if(valor == max) {//si el valor del ultimo punto selecionado es maximo se pasa al minimo
			valor = min;
		}else {//en cualquier otro caso se incrementa en 1 el valor para el siguiente punto 
			valor++;
		}
		
		if(siguiente.getValor() == valor) {
			if(siguiente .getColumna()-1 == CActual && siguiente.getFila()-1 == FActual && !(comprobarCruce(siguiente, 1))) {
				siguiente.setVisitado(1);
				return true;
			}else if(siguiente .getColumna() == CActual && siguiente.getFila()-1 == FActual){
				siguiente.setVisitado(2);
				return true;
			}else if(siguiente .getColumna()+1 == CActual && siguiente.getFila()-1 == FActual && !(comprobarCruce(siguiente, 3))) {
				siguiente.setVisitado(3);
				return true;
			}else if(siguiente .getColumna()-1 == CActual && siguiente.getFila() == FActual){
				siguiente.setVisitado(4);
				return true;
			}else if(siguiente .getColumna()+1 == CActual && siguiente.getFila() == FActual){
				siguiente.setVisitado(5);
				return true;
			}else if(siguiente .getColumna()-1 == CActual && siguiente.getFila()+1 == FActual && !(comprobarCruce(siguiente, 6))) {
				siguiente.setVisitado(6);
				return true;
			}else if(siguiente .getColumna() == CActual && siguiente.getFila()+1 == FActual){
				siguiente.setVisitado(7);
				return true;
			}else if(siguiente .getColumna()+1 == CActual && siguiente.getFila()+1 == FActual && !(comprobarCruce(siguiente, 8))) {
				siguiente.setVisitado(8);
				return true;
			}
		}
		return false;
	}
	
	private boolean comprobarCruce(Puntos siguiente, int posicion) {
		
		switch (posicion) {
		case 1:
			if(buscarPunto(disponibles, siguiente.getFila(),siguiente.getColumna()-1).getVisitado() == 3) {
				return true;
			}else if(buscarPunto(disponibles, siguiente.getFila()-1,siguiente.getColumna()).getVisitado() == 6) {
				return true;
			}
			return false;
		case 3:
			if(buscarPunto(disponibles, siguiente.getFila(),siguiente.getColumna()+1).getVisitado() == 1) {
				return true;
			}else if(buscarPunto(disponibles, siguiente.getFila()-1,siguiente.getColumna()).getVisitado() == 8) {
				return true;
			}
			return false;
		case 6:
			if(buscarPunto(disponibles, siguiente.getFila(),siguiente.getColumna()-1).getVisitado() == 8) {
				return true;
			}else if(buscarPunto(disponibles, siguiente.getFila()+1,siguiente.getColumna()).getVisitado() == 1) {
				return true;
			}
			return false;
		case 8:
			if(buscarPunto(disponibles, siguiente.getFila(),siguiente.getColumna()+1).getVisitado() == 6) {
				return true;
			}else if(buscarPunto(disponibles, siguiente.getFila()+1,siguiente.getColumna()).getVisitado() == 3) {
				return true;
			}
			return false;

		default:
			return false;
		}
	}
	
	private Puntos buscarPunto(LinkedList<Puntos> lista, int fila, int columna) {
		
		Puntos encontrado = null;
		
		for(Puntos prueba: lista) {
			if(prueba.getColumna() == columna && prueba.getFila() == fila) {
				encontrado = prueba;
				break;
			}
		}
		return encontrado;
	}
	
	public String toString() {
		
		StringBuffer salida = new StringBuffer();
		
		for(LinkedList<Puntos> lista: soluciones) {
			
			salida.append("\n" + solucionToString(lista));
			
		}
		
		return salida.toString();
	}
	
	private String solucionToString(LinkedList<Puntos> lista) {
		
		StringBuffer salida = new StringBuffer(); 
		
		char[][] solucion = new char[n*2][m*2];
		int indice = 0;
		Puntos actual;
		
		for(int i = 0; i < n*2; i++) {
			for(int j = 0; j < m*2; j++) {
				solucion[i][j] = ' ';
			}
		}
		
		for(int i = 0; i < n*2; i+=2) {
			for(int j = 0; j < m*2; j+=2) {
				actual = disponibles.get(indice);
				solucion[i][j] = (char)(actual.getValor()+48);
				
				Puntos separador = lista.get(indice);
				int columna = separador.getColumna()*2;
				int fila = separador.getFila()*2;
				
				switch (separador.getVisitado()) {
				case 1:
					solucion[fila-1][columna-1] = '\\';
					break;
				case 2:
					solucion[fila-1][columna] = '|';
					break;
				case 3:
					solucion[fila-1][columna+1] = '/';
					break;
				case 4:
					solucion[fila][columna-1] = '-';
					break;
				case 5:
					solucion[fila][columna+1] = '-';
					break;
				case 6:
					solucion[fila+1][columna-1] = '/';
					break;
				case 7:
					solucion[fila+1][columna] = '|';
					break;
				case 8:
					solucion[fila+1][columna+1] = '\\';
					break;
				}
				indice++;
			}
		}
		
		for(int i = 0; i < (n*2)-1; i++) {
				for(int j = 0; j < (m*2)-1; j++) {
					salida.append(solucion[i][j]);
				}
				salida.append("\n");
		}
		
		return salida.toString();
	}
}

