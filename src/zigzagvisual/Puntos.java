package zigzagvisual;

public class Puntos {
	
	private int valor;
	private int fila, columna;
	private int visitado;
	
	public Puntos(int columna, int fila, int valor) {
		
		this.valor = valor;
		this.columna = columna;
		this.fila = fila;
		
	}
	public Puntos(int columna, int fila, int valor, int visitados) {
		
		this.valor = valor;
		this.columna = columna;
		this.fila = fila;
		this.visitado = visitados;
	}
	
	public int getVisitado() {
		return this.visitado;
	}
	
	public int getColumna() {
		return this.columna;
	}
	
	public int getFila() {
		return this.fila;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public void setVisitado(int relacion) {
		this.visitado = relacion;
	}
	
	
}
