package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Bezero;
import domain.Pertsona;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

@SuppressWarnings("unused")
public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private MainGUI main;
	private JPanel contentPane;
	private JTextField textErabiltzailea;
	private JLabel lblErabiltzailea;
	private JLabel lblPasahitza;
	private JButton btnLogeatu;
	private JPasswordField textPasahitza;
	private LoginGUI frame;
	private JLabel labelError;
	private JButton btnBack;

	/**
	 * Create the frame.
	 */
	public LoginGUI(MainGUI main) {
		this.main = main;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LogIn")); 
		frame=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textErabiltzailea = new JTextField();
		textErabiltzailea.setBounds(221, 45, 131, 20);
		contentPane.add(textErabiltzailea);
		textErabiltzailea.setColumns(10);
		
		textPasahitza = new JPasswordField();
		textPasahitza.setBounds(221, 98, 131, 20);
		contentPane.add(textPasahitza);
		
		lblErabiltzailea = new JLabel();
		lblErabiltzailea.setText(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblErabiltzailea.setBounds(51, 48, 78, 14);
		contentPane.add(lblErabiltzailea);
		
		lblPasahitza = new JLabel();
		lblPasahitza.setText(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		lblPasahitza.setBounds(51, 101, 78, 14);
		contentPane.add(lblPasahitza);
		
		btnLogeatu = new JButton();
		btnLogeatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelError.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				if(textErabiltzailea.getText().equals("") || (new String (textPasahitza.getPassword())).equals("")){ //HAU KENDU TA DANA BATEA??
					labelError.setText("Errorea: Erabiltzailea eta pasahitza sartu behar dira.");	
				} else {
				Pertsona p = facade.isLogin(textErabiltzailea.getText(), new String (textPasahitza.getPassword()));
				if (p==null)
					labelError.setText(ResourceBundle.getBundle("Etiquetas").getString("WrongLogin"));
				else if (p instanceof Bezero) {
					JFrame a = new MainBezeroGUI(main,(Bezero) p);
					a.setVisible(true);
					frame.setVisible(false); 
				} else if (p instanceof Admin) {
					JFrame a = new MainAdminGUI(main);
					a.setVisible(true);
					frame.setVisible(false);
				} else {
					JFrame a = new MainLangileaGUI(main,frame);
					a.setVisible(true);
					frame.setVisible(false);
				}
			}
			}
		});
		btnLogeatu.setText(ResourceBundle.getBundle("Etiquetas").getString("LogIn"));
		btnLogeatu.setBounds(119, 165, 178, 38);
		contentPane.add(btnLogeatu);
		
		labelError = new JLabel("");
		labelError.setForeground(Color.RED);
		labelError.setBounds(36, 140, 388, 14);
		contentPane.add(labelError);
		
		btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		btnBack.setBounds(266, 220, 86, 21);
		contentPane.add(btnBack);
		

	}
}
