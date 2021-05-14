package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezero;
import domain.Kodea;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class KodeaErabiliGUI extends JFrame {

	private JPanel contentPane;
	private MainGUI main;
	private KodeaErabiliGUI frame;
	private Bezero b;
	private JTextField textFieldCode;
	private JLabel lblQuestion;
	private JLabel lblCode;
	private JButton btnRegisterCode;
	private JButton btnNoCode;
	private JLabel lblError;

	
	public KodeaErabiliGUI(MainGUI main, Bezero b) {
		this.main=main;
		this.b=b;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisterCode"));
		frame=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblQuestion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DoYouHaveACode"));
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestion.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblQuestion.setBounds(22, 33, 382, 14);
		contentPane.add(lblQuestion);
		
		lblCode = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Code"));
		lblCode.setBounds(84, 90, 76, 14);
		contentPane.add(lblCode);
		
		textFieldCode = new JTextField();
		textFieldCode.setBounds(169, 87, 149, 20);
		contentPane.add(textFieldCode);
		textFieldCode.setColumns(10);
		
		btnRegisterCode = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterCode"));
		btnRegisterCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				if (textFieldCode.getText().equals("")) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Hutsa"));
					return;
				}
				Kodea kod = facade.getCode(textFieldCode.getText());
				if (kod==null) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("NotExists"));
					return;
				}
				if (kod.isUsed()) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Used"));
					return;
				}
				if (kod.getIraungipena().compareTo(new Date())<0) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Iraungi"));
					return;
				}
				facade.kodeaErabili(kod, b);
				JFrame a = new MainBezeroGUI(main, b);
				a.setVisible(true);
				frame.setVisible(false); 
			}
		});
		btnRegisterCode.setBounds(102, 154, 205, 23);
		contentPane.add(btnRegisterCode);
		
		btnNoCode = new JButton(ResourceBundle.getBundle("Etiquetas").getString("NoCode"));
		btnNoCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MainBezeroGUI(main, b);
				a.setVisible(true);
				frame.setVisible(false); 
			}
		});
		btnNoCode.setBounds(102, 206, 205, 23);
		contentPane.add(btnNoCode);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(51, 130, 353, 14);
		contentPane.add(lblError);
	}
}
