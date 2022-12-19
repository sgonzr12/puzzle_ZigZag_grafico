package zigzagvisual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MenuPrincipal {

	private JFrame frame;
	private ArrayList<Integer> lista;
	private int filas, columnas;
	private JButton btnJugar, btnGuardar;

	/**
	 * Create the application.
	 */
	public MenuPrincipal() {
		this.lista = new ArrayList<Integer>();
		this.filas = 0;
		this.columnas = 0;
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setEnabled(false);
		btnJugar = new JButton("JUGAR");
		btnJugar.setEnabled(false);
		ocultarJugar();
		initialize();
		frame.setVisible(true);
	}
	
	public MenuPrincipal(ArrayList<Integer> lista, int filas, int columnas) {
		
		this.lista = lista;
		this.columnas = columnas;
		this.filas = filas;
		btnGuardar = new JButton("GUARDAR");
		btnGuardar.setEnabled(false);
		btnJugar = new JButton("JUGAR");
		btnJugar.setEnabled(false);
		ocultarJugar();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panelNombre = new JPanel();
		frame.getContentPane().add(panelNombre, BorderLayout.NORTH);
		
		JLabel lblZigzagPuzzle = new JLabel("ZigZag Puzzle");
		panelNombre.add(lblZigzagPuzzle);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnCrear = new JButton("CREAR");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnCrear) {
					pantallaCrear();	
				}
			}
		});
		GridBagConstraints gbc_btnCrear = new GridBagConstraints();
		gbc_btnCrear.insets = new Insets(0, 0, 5, 0);
		gbc_btnCrear.gridx = 6;
		gbc_btnCrear.gridy = 1;
		panel.add(btnCrear, gbc_btnCrear);
		
		JButton btnCargar = new JButton("CARGAR");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnCargar) {
					
					JFileChooser cargar = new JFileChooser();
					cargar.setFileSelectionMode(JFileChooser.FILES_ONLY);
					
					int returnValue = cargar.showOpenDialog(btnCargar);

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = cargar.getSelectedFile();
						leerArchivo(selectedFile);
					}
				}
			}
		});
		btnCargar.setVisible(true);
		GridBagConstraints gbc_btnCargar = new GridBagConstraints();
		gbc_btnCargar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCargar.gridx = 6;
		gbc_btnCargar.gridy = 2;
		panel.add(btnCargar, gbc_btnCargar);
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnGuardar) {
					
					JFileChooser guardar = new JFileChooser();
					guardar.setFileSelectionMode(JFileChooser.FILES_ONLY);
					
					int returnValue = guardar.showSaveDialog(btnCargar);

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = guardar.getSelectedFile();
						escribirArchivo(selectedFile);
					}
				}
			}
		});
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 5, 0);
		gbc_btnGuardar.gridx = 6;
		gbc_btnGuardar.gridy = 3;
		panel.add(btnGuardar, gbc_btnGuardar);
		
		GridBagConstraints gbc_btnJugar = new GridBagConstraints();
		gbc_btnJugar.gridx = 6;
		gbc_btnJugar.gridy = 4;
		panel.add(btnJugar, gbc_btnJugar);
		
		btnJugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==btnJugar) {
					pantallaJugar();	
				}
			}
		});
	
	}
	
	public void pantallaJugar() {
		
		new Jugar(lista, filas, columnas);
		
		frame.dispose();
		
	}
	
	public void pantallaCrear() {
		
		Crear crear = new Crear(lista, filas, columnas);
		
		frame.dispose();
		
	}
	
	public void ocultarJugar(){
		
		if(lista.isEmpty()) {
			btnGuardar.setEnabled(false);
			btnJugar.setEnabled(false);
		}else {
			btnGuardar.setEnabled(true);
			btnJugar.setEnabled(true);
		}
		
	}

	public void leerArchivo(File selectedFile) {
		try {
			Scanner scan = new Scanner(selectedFile);
			
			//lectura de la matriz
			String linea;
			
			linea = scan.nextLine();
			String[] numLinea1 = linea.split(" ");
			columnas = numLinea1.length;
			
			if (columnas > 10) {
				throw new NumberFormatException();
			}
			
			for(int i = 0; i< columnas ; i++) {
				
				int valor = 0;
				
				valor = Integer.parseInt(numLinea1[i]);
				
				if (valor <= 0 || valor >= 10) {
					throw new NumberFormatException();
				}else {
					lista.add(valor);	
				}
			}
			filas = 1;
			while (scan.hasNextLine()) {
				
				linea = scan.nextLine();
				String[] numLinea = linea.split(" ");
				if (columnas != numLinea.length) {
					throw new NumberFormatException();
				}
				
				for(int i = 0; i< columnas; i++) {
					
					int valor = 0;
					
					valor = Integer.parseInt(numLinea[i]);
					if (valor <= 0 || valor >= 10) {
						throw new NumberFormatException();
					}else {
						lista.add(valor);	
					}
				}
				filas++;
				if (filas > 10) {
					throw new NumberFormatException();
				}
			}
			scan.close();
		}catch (Exception e) {
			
		}
		ocultarJugar();
	}
	
	public void escribirArchivo(File selectedFile) {
		
		try {
			FileWriter escribir = new FileWriter(selectedFile);
			
			StringBuffer salida = new StringBuffer();
			
			ArrayList<Integer> listaSal = new ArrayList<>();
			for(int valor: lista){
				listaSal.add(valor);
			}
			
			for(int i=0; i<filas; i++) {
				for(int j=0; j<columnas; j++) {
					salida.append(listaSal.remove(0));
					if(j != columnas-1) {
						salida.append(' ');
					}
				}
				salida.append("\n");
			}
			System.out.println(salida.toString());
			escribir.write(salida.toString());
			
			escribir.close();
			
		}catch (IOException e) {
			// TODO: handle exception
		}
		
		
	}
}
