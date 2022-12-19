package zigzagvisual;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import java.util.ArrayList;

public class Crear {

	private JFrame frame;
	private JPanel tablaActual;
	private ArrayList<Integer> valores;
	private JComboBox<Integer>[][] problema;
	private int filas, columnas;

	/**
	 * Create the application.
	 */
	public Crear(ArrayList<Integer> lista, int filas, int columnas) {
		
		if(lista == null) {
			this.valores = new ArrayList<Integer>();
		}else {
			this.valores = lista;	
		}
		this.filas = filas;
		this.columnas = columnas;
		
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panelNombre = new JPanel();
		frame.getContentPane().add(panelNombre, BorderLayout.NORTH);
		
		JLabel lblZigzagPuzzle = new JLabel("ZigZag Puzzle");
		panelNombre.add(lblZigzagPuzzle);
		
		JPanel panelSelector = new JPanel();
		frame.getContentPane().add(panelSelector, BorderLayout.SOUTH);
		
		JLabel lblNumeroDeFilas = new JLabel("numero de filas");
		panelSelector.add(lblNumeroDeFilas);
		
		JComboBox<Integer> nunFilasList = new JComboBox<Integer>();
		nunFilasList.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1,2,3,4,5,6,7,8,9}));
		if(filas!=0) {
			nunFilasList.setSelectedItem(filas);
		}
		nunFilasList.setMaximumRowCount(9);
		panelSelector.add(nunFilasList);
		
		JLabel lblNumeroDeColumnas = new JLabel("numero de columnas");
		panelSelector.add(lblNumeroDeColumnas);
		
		JComboBox<Integer> nunColumnasList = new JComboBox<Integer>();
		nunColumnasList.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1,2,3,4,5,6,7,8,9}));
		if(columnas!=0) {
			nunColumnasList.setSelectedItem(columnas);
		}
		nunColumnasList.setMaximumRowCount(9);
		panelSelector.add(nunColumnasList);
		
		JButton btnAceptar = new JButton("Crear");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				filas = (int)nunFilasList.getSelectedItem();
				columnas = (int)nunColumnasList.getSelectedItem();
				mostrarTabla(filas, columnas);
			}
		});
		panelSelector.add(btnAceptar);
		
		
		JButton btnMenu = new JButton("Guardar");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverAMenu();
			}
		});
		panelSelector.add(btnMenu);
		
	}
	
	private void volverAMenu() {
		
		if(problema!=null) {
			for(JComboBox<Integer>[] valor1: problema) {
				for(JComboBox<Integer> valor: valor1) {
					valores.add((Integer)valor.getSelectedItem());	
				}
			}
		}
			
		
		MenuPrincipal menu = new MenuPrincipal(valores, filas, columnas);	
		
		frame.dispose();
	}
	
	private void mostrarTabla(int filas, int columnas) {
		if(tablaActual!=null) {
			frame.getContentPane().remove(tablaActual);
		}
		JPanel panelTabla = new JPanel();
		
		panelTabla.setLayout(new GridLayout(filas,columnas));
		
		problema = new JComboBox[filas][columnas];
		
		for(int i=0; i < filas;i++){
			for(int j=0; j < columnas;j++){
			
				problema[i][j] = new JComboBox<Integer>();
				problema[i][j].setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1,2,3,4,5,6,7,8,9}));
				if(!valores.isEmpty()) {
					problema[i][j].setSelectedItem(valores.remove(0));
				}
				problema[i][j].setMaximumRowCount(9);
				panelTabla.add(problema[i][j]);
				
			}	
		}
		
		panelTabla.setVisible(true);
		tablaActual = panelTabla;
		System.out.println(filas + columnas);
		frame.getContentPane().add(panelTabla, BorderLayout.CENTER);
		frame.repaint();
		frame.validate();
	}

}
