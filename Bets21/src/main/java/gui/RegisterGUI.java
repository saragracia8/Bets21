package gui;


import java.util.Date;
import java.util.ResourceBundle;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezero;
import domain.Pertsona;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

@SuppressWarnings("unused")
public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private MainGUI main;
	private JPanel contentPane;
	private JTextField izenaText;
	private JTextField abizenaText;
	private JTextField erabiltzaileaText;
	private JPasswordField pasahitzaText;
	private JPasswordField pasahitzaTexterr;
	private JTextField naText;
	private JTextField tlfText;
	private JTextField postaText;
	private JTextField urtea;
	private JTextField eguna;
	private JLabel lblIzena;
	private JLabel lblAbizena;
	private JLabel lblErabiltzailea;
	private JLabel lblPasahitza;
	private JLabel lblPasahitzaErrepikatu;
	private JLabel lblNa;
	private JLabel lblJaiotzeData;
	private JLabel lblUrtea;
	private JLabel lblHilabetea;
	private JLabel lblEguna;
	private JLabel lblTelefonoZenbakia;
	private JLabel lblPostaElektronikoa;
	private JButton btnErregistratu;
	private JButton back;
	private JComboBox<String> hilabeteak;
	private DefaultComboBoxModel<String> hilabIzenak = new DefaultComboBoxModel<String>();
	private JLabel lblError;

	/**
	 * Create the frame.
	 */
	public RegisterGUI(MainGUI main) {
		this.main=main;
		frame=this;
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 422, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblIzena = new JLabel();
		lblIzena.setText(ResourceBundle.getBundle("Etiquetas").getString("Name"));
		lblIzena.setBounds(73, 34, 106, 14);
		contentPane.add(lblIzena);
		
		lblAbizena = new JLabel();
		lblAbizena.setText(ResourceBundle.getBundle("Etiquetas").getString("Surname"));
		lblAbizena.setBounds(73, 59, 106, 14);
		contentPane.add(lblAbizena);
		
		lblErabiltzailea = new JLabel();
		lblErabiltzailea.setText(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblErabiltzailea.setBounds(73, 84, 106, 14);
		contentPane.add(lblErabiltzailea);
		
		lblPasahitza = new JLabel();
		lblPasahitza.setText(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPasahitza.setBounds(73, 109, 106, 14);
		contentPane.add(lblPasahitza);
		
		lblPasahitzaErrepikatu = new JLabel();
		lblPasahitzaErrepikatu.setText(ResourceBundle.getBundle("Etiquetas").getString("RepPassword"));
		lblPasahitzaErrepikatu.setBounds(73, 134, 140, 14);
		contentPane.add(lblPasahitzaErrepikatu);
		
		lblNa = new JLabel();
		lblNa.setText(ResourceBundle.getBundle("Etiquetas").getString("DNI"));
		lblNa.setBounds(73, 159, 106, 14);
		contentPane.add(lblNa);
		
		lblJaiotzeData = new JLabel();
		lblJaiotzeData.setText(ResourceBundle.getBundle("Etiquetas").getString("BirthDate"));
		lblJaiotzeData.setBounds(73, 185, 140, 14);
		contentPane.add(lblJaiotzeData);
		
		lblUrtea = new JLabel();
		lblUrtea.setText(ResourceBundle.getBundle("Etiquetas").getString("Year"));
		lblUrtea.setBounds(73, 209, 45, 13);
		contentPane.add(lblUrtea);
		
		lblHilabetea = new JLabel();
		lblHilabetea.setText(ResourceBundle.getBundle("Etiquetas").getString("Month"));
		lblHilabetea.setBounds(154, 209, 59, 13);
		contentPane.add(lblHilabetea);
		
		lblEguna = new JLabel();
		lblEguna.setText(ResourceBundle.getBundle("Etiquetas").getString("Day"));
		lblEguna.setBounds(304, 209, 53, 13);
		contentPane.add(lblEguna);
		
		lblTelefonoZenbakia = new JLabel();
		lblTelefonoZenbakia.setText(ResourceBundle.getBundle("Etiquetas").getString("TelephoneNum"));
		lblTelefonoZenbakia.setBounds(73, 254, 140, 14);
		contentPane.add(lblTelefonoZenbakia);
		
		lblPostaElektronikoa = new JLabel("Posta elektronikoa:");
		lblPostaElektronikoa.setText(ResourceBundle.getBundle("Etiquetas").getString("Email"));
		lblPostaElektronikoa.setBounds(73, 278, 140, 14);
		contentPane.add(lblPostaElektronikoa);
		
		izenaText = new JTextField();
		izenaText.setBounds(219, 31, 121, 20);
		contentPane.add(izenaText);
		izenaText.setColumns(10);
		
		abizenaText = new JTextField();
		abizenaText.setBounds(219, 56, 121, 20);
		contentPane.add(abizenaText);
		abizenaText.setColumns(10);
		
		erabiltzaileaText = new JTextField();
		erabiltzaileaText.setBounds(219, 81, 121, 20);
		contentPane.add(erabiltzaileaText);
		erabiltzaileaText.setColumns(10);
		
		pasahitzaText = new JPasswordField();
		pasahitzaText.setBounds(219, 106, 121, 20);
		contentPane.add(pasahitzaText);
		
		pasahitzaTexterr = new JPasswordField();
		pasahitzaTexterr.setBounds(219, 131, 121, 20);
		contentPane.add(pasahitzaTexterr);
		
		naText = new JTextField();
		naText.setBounds(219, 156, 121, 20);
		contentPane.add(naText);
		naText.setColumns(10);
		
		tlfText = new JTextField();
		tlfText.setBounds(219, 252, 121, 20);
		contentPane.add(tlfText);
		tlfText.setColumns(10);
		
		postaText = new JTextField();
		postaText.setBounds(219, 276, 121, 20);
		contentPane.add(postaText);
		postaText.setColumns(10);
		
		urtea = new JTextField();
		urtea.setBounds(73, 224, 71, 19);
		contentPane.add(urtea);
		urtea.setColumns(10);
		
		hilabeteak = new JComboBox<String>();
		hilabeteak.setBounds(154, 223, 140, 21);
		hilabeteak.setModel(hilabIzenak);
		contentPane.add(hilabeteak);
		
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
		hilabeteak.setSelectedIndex(0);
		
		eguna = new JTextField();
		eguna.setBounds(304, 224, 36, 19);
		contentPane.add(eguna);
		eguna.setColumns(10);
		
		btnErregistratu = new JButton();
		btnErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				
				try {
					if (izenaText.getText().equals("")||abizenaText.getText().equals("")||erabiltzaileaText.getText().equals("")||
						(new String (pasahitzaText.getPassword())).equals("")||(new String (pasahitzaTexterr.getPassword())).equals("")||
						naText.getText().equals("")||tlfText.getText().equals("")||postaText.getText().equals("")||urtea.getText().equals("")||eguna.getText().equals("")) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyError"));
					System.out.println("A");
				}else if(facade.validPsw(new String(pasahitzaText.getPassword()), new String(pasahitzaTexterr.getPassword()))==1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("PasswordRepeatError"));
				}else if(facade.validPsw(new String(pasahitzaText.getPassword()), new String(pasahitzaTexterr.getPassword()))==2) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("PasswordLengthError"));
				}else if(!facade.validDNI(naText.getText())) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DNIError"));
				}else if(facade.validDate(Integer.parseInt(urtea.getText()), hilabeteak.getSelectedIndex(), Integer.parseInt(eguna.getText()))==1) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("UnderAgeError"));
				}else if(facade.validDate(Integer.parseInt(urtea.getText()), hilabeteak.getSelectedIndex(), Integer.parseInt(eguna.getText()))==2) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DateError"));
				}else if(!facade.validTelNum(tlfText.getText())) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("TelephoneError"));
				}else if(!facade.validEmail(postaText.getText())) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmailError"));
				}else {
						java.util.Date date= facade.newDate(Integer.parseInt(urtea.getText()), hilabeteak.getSelectedIndex(), Integer.parseInt(eguna.getText()));
						Bezero b= new Bezero(erabiltzaileaText.getText(), new String(pasahitzaText.getPassword()), izenaText.getText(), abizenaText.getText(), naText.getText(), date,Integer.parseInt(tlfText.getText()), postaText.getText());
						int n = facade.register(b);
						if (n==1) 
							lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("UserError"));
						else if (n==2)
							lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DNIExistsError"));
						else {
							JFrame a = new KodeaErabiliGUI(main, b);
							a.setVisible(true);
							frame.setVisible(false); 
						}
					}	
				}catch(NumberFormatException  e) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("DateError"));
				}catch(Exception e) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("UserError"));
				}
			}
		});
		btnErregistratu.setText(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		btnErregistratu.setBounds(73, 306, 267, 23);
		contentPane.add(btnErregistratu);
		
		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		lblError.setBounds(73, 339, 325, 14);
		contentPane.add(lblError);
		
		back = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.setVisible(true);
				frame.setVisible(false); 
			}
		});
		back.setBounds(254, 372, 85, 21);
		contentPane.add(back);
	}
}

