package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.EventQueue;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;

@SuppressWarnings("unused")
public class CreateEventGUI extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUrtea;
	private JTextField textEguna;
	private JTextField textIzenburua;
	private JComboBox<String> comboHilabetea;
	private DefaultComboBoxModel<String> hilabIzenak = new DefaultComboBoxModel<String>();
	private JComboBox<String> comboKirola;
	private DefaultComboBoxModel<String> kirolIzenak = new DefaultComboBoxModel<String>();
	private JComboBox<String> comboTxapelketa;
	private DefaultComboBoxModel<String> txapelketaIzenak = new DefaultComboBoxModel<String>();
	private MainGUI main;
	private JFrame atzera;
	private CreateEventGUI frame;
	private JButton btnBack;
	private JLabel lblError;
	
	private JButton saioaItxi;
	private JButton btnAukeratu;
	private JButton btnSortu;
	private JLabel lblIzenburua;
	private JLabel lblTxapelketa;
	private JLabel lblKirola;
	private JLabel lblEguna;
	private JLabel lblHilabetea;
	private JLabel lblUrtea;

	/**
	 * Create the frame.
	 */
	public CreateEventGUI(MainGUI main, JFrame atzera) {
		this.main = main;
		this.atzera=atzera;
		frame = this;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 346, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblUrtea = new JLabel("Urtea:");
		lblUrtea.setText(ResourceBundle.getBundle("Etiquetas").getString("Year"));
		lblUrtea.setBounds(38, 39, 45, 14);
		contentPane.add(lblUrtea);
		
		textUrtea = new JTextField();
		textUrtea.setBounds(94, 36, 57, 20);
		contentPane.add(textUrtea);
		textUrtea.setColumns(10);
		
		lblHilabetea = new JLabel("Hilabetea:");
		lblHilabetea.setText(ResourceBundle.getBundle("Etiquetas").getString("Month"));
		lblHilabetea.setBounds(38, 67, 68, 14);
		contentPane.add(lblHilabetea);
		
		lblEguna = new JLabel("Eguna:");
		lblEguna.setText(ResourceBundle.getBundle("Etiquetas").getString("Day"));
		lblEguna.setBounds(38, 95, 46, 14);
		contentPane.add(lblEguna);
		
		lblKirola = new JLabel("Kirola:");
		lblKirola.setText(ResourceBundle.getBundle("Etiquetas").getString("Sport"));
		lblKirola.setBounds(38, 127, 46, 14);
		contentPane.add(lblKirola);
		
		comboHilabetea = new JComboBox<String>();
		comboHilabetea.setModel(hilabIzenak);
		comboHilabetea.setBounds(94, 63, 93, 22);
		contentPane.add(comboHilabetea);
		
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("December"));
		comboHilabetea.setSelectedIndex(0);
		
		textEguna = new JTextField();
		textEguna.setBounds(94, 92, 57, 20);
		contentPane.add(textEguna);
		textEguna.setColumns(10);
		
		comboKirola = new JComboBox<String>();
		comboKirola.setModel(kirolIzenak);
		comboKirola.setBounds(94, 123, 93, 22);
		contentPane.add(comboKirola);
		
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Soccer"));
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Tennis"));
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Basketball"));
		comboKirola.setSelectedIndex(0);
		
		lblTxapelketa = new JLabel();
		lblTxapelketa.setText(ResourceBundle.getBundle("Etiquetas").getString("Championship"));
		lblTxapelketa.setBounds(38, 225, 93, 14);
		contentPane.add(lblTxapelketa);
		lblTxapelketa.setVisible(false);
		
		lblIzenburua = new JLabel();
		lblIzenburua.setText(ResourceBundle.getBundle("Etiquetas").getString("Title"));
		lblIzenburua.setBounds(38, 267, 93, 14);
		contentPane.add(lblIzenburua);
		lblIzenburua.setVisible(false);
		
		comboTxapelketa = new JComboBox<String>();
		comboTxapelketa.setModel(txapelketaIzenak);
		comboTxapelketa.setBounds(141, 221, 93, 22);
		contentPane.add(comboTxapelketa);
		comboTxapelketa.setVisible(false);
	
		textIzenburua = new JTextField();
		textIzenburua.setBounds(141, 264, 93, 20);
		contentPane.add(textIzenburua);
		textIzenburua.setColumns(10);
		textIzenburua.setVisible(false);
		
		btnAukeratu = new JButton();
		btnAukeratu.setText(ResourceBundle.getBundle("Etiquetas").getString("Choose"));
		btnAukeratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				lblError.setText("");
				lblTxapelketa.setVisible(false);
				lblIzenburua.setVisible(false);
				comboTxapelketa.setVisible(false);
				textIzenburua.setVisible(false);
				btnSortu.setVisible(false);
				try {
					if(textUrtea.getText().equals("")||textEguna.getText().equals("")) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyError"));
					} else if (facade.validEventDate(Integer.parseInt(textUrtea.getText()), comboHilabetea.getSelectedIndex(), Integer.parseInt(textEguna.getText()))==1) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EventPastDateError"));
					} else if (facade.validEventDate(Integer.parseInt(textUrtea.getText()), comboHilabetea.getSelectedIndex(), Integer.parseInt(textEguna.getText()))==2){
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("IncorrectDateError"));
					}else {
						lblTxapelketa.setVisible(true);
						lblIzenburua.setVisible(true);
						comboTxapelketa.setVisible(true);
						textIzenburua.setVisible(true);
						textIzenburua.setText("");
						btnSortu.setVisible(true);
						btnSortu.setEnabled(true);
						btnSortu.setText(ResourceBundle.getBundle("Etiquetas").getString("Create"));
						String selectedSport = (String) comboKirola.getSelectedItem();
						if (selectedSport.equals((ResourceBundle.getBundle("Etiquetas").getString("Soccer")))) {
							txapelketaIzenak.removeAllElements();
							txapelketaIzenak.addElement("La Liga");
							txapelketaIzenak.addElement("Serie A");
							txapelketaIzenak.addElement("Premier League");
						} else if (selectedSport.equals((ResourceBundle.getBundle("Etiquetas").getString("Basketball")))) {
							txapelketaIzenak.removeAllElements();
							txapelketaIzenak.addElement("Liga ACB");
							txapelketaIzenak.addElement("NBA");
							txapelketaIzenak.addElement("Euroliga");
						} else if (selectedSport.equals((ResourceBundle.getBundle("Etiquetas").getString("Tennis")))) {
							txapelketaIzenak.removeAllElements();
							txapelketaIzenak.addElement("Wimbledon");
							txapelketaIzenak.addElement("Roland Garros");
							txapelketaIzenak.addElement("US Open");
						}
					}
				}catch(NumberFormatException  e1){
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EventDateError"));
				}
			}
		});
		btnAukeratu.setBounds(38, 166, 196, 23);
		contentPane.add(btnAukeratu);
		
		btnSortu = new JButton();
		btnSortu.setText(ResourceBundle.getBundle("Etiquetas").getString("Create"));
		btnSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				
				java.util.Date date= facade.newDate(Integer.parseInt(textUrtea.getText()), comboHilabetea.getSelectedIndex(), Integer.parseInt(textEguna.getText()));
				
				
				String selectedSport="";
				if(!textIzenburua.getText().equals("")) {
					if (((String)comboKirola.getSelectedItem()).equals("Soccer")||((String)comboKirola.getSelectedItem()).equals("Fútbol")||((String)comboKirola.getSelectedItem()).equals("Futbola"))
						selectedSport="Soccer";
					else if (((String)comboKirola.getSelectedItem()).equals("Tennis")||((String)comboKirola.getSelectedItem()).equals("Tenis")||((String)comboKirola.getSelectedItem()).equals("Tenisa"))
						selectedSport="Tennis";
					else if (((String)comboKirola.getSelectedItem()).equals("Basketball")||((String)comboKirola.getSelectedItem()).equals("Baloncesto")||((String)comboKirola.getSelectedItem()).equals("Saskibaloia"))
						selectedSport="Basketball";
					int n = facade.createEvent(textIzenburua.getText(), date, selectedSport, (String) comboTxapelketa.getSelectedItem());
					if(n==1) {	
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EventExistsError"));
					}else {
						btnSortu.setText(ResourceBundle.getBundle("Etiquetas").getString("EventCreated"));
						btnSortu.setEnabled(false);
					}
				}else {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyError"));	
				}
			}
		});
		btnSortu.setBounds(38, 304, 196, 23);
		contentPane.add(btnSortu);	
		btnSortu.setVisible(false);
		
		btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		btnBack.setBounds(226, 354, 89, 23);
		contentPane.add(btnBack);
		
		saioaItxi = new JButton();
		saioaItxi.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		saioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		saioaItxi.setBounds(226, 10, 89, 25);
		this.getContentPane().add(saioaItxi);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(10, 338, 322, 14);
		contentPane.add(lblError);
	}
}

