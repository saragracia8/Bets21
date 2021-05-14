package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezero;
import domain.Kodea;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Color;

public class KodeaEskatuGUI extends JFrame {

	private JPanel contentPane;
	private JButton btnLogOut;
	private JButton btnBack;
	private JButton btnKodeaEskatu;
	
	private MainGUI main;
	private JFrame atzera;
	private KodeaEskatuGUI frame;
	private Bezero b;
	private JTextArea textAreaCode;
	private JLabel lblError;
	private BLFacade facade;
	private JTextArea textAreaExpl;
	private JLabel lblErabiliak;
	private JLabel lblErabiliakKant;
	
	public KodeaEskatuGUI(MainGUI main, JFrame atzera, Bezero b) {
		this.main=main;
		this.atzera=atzera;
		this.b=b;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AskForCode"));
		frame=this;
		this.facade=MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		btnLogOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		btnLogOut.setBounds(375, 10, 106, 23);
		contentPane.add(btnLogOut);
		
		btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); 
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		btnBack.setBounds(375, 308, 106, 23);
		contentPane.add(btnBack);
		
		btnKodeaEskatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AskForCode"));
		btnKodeaEskatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (facade.getCurrentCode(b)!=null) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("KodeaBadu"));
					textAreaCode.setText("");
				}else {
					Kodea k = facade.kodeaSortu(b);
					textAreaCode.setText(k.toString());
					btnKodeaEskatu.setEnabled(false);
				}
			}
		});
		btnKodeaEskatu.setBounds(160, 191, 196, 41);
		contentPane.add(btnKodeaEskatu);
		
		textAreaCode = new JTextArea();
		textAreaCode.setEditable(false);
		textAreaCode.setBounds(201, 266, 126, 22);
		contentPane.add(textAreaCode);
		Kodea kod = facade.getCurrentCode(b);
		if (kod!=null) {
			if (kod.getIraungipena().compareTo(new Date())>0) {
				facade.kodeaIraungiDa(b);
			} else {
				textAreaCode.setText(kod.toString());
				btnKodeaEskatu.setEnabled(false);
			}
		}
		
		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(116, 242, 275, 14);
		contentPane.add(lblError);
		
		textAreaExpl = new JTextArea();
		textAreaExpl.setLineWrap(true);
		textAreaExpl.setEditable(false);
		textAreaExpl.setText(ResourceBundle.getBundle("Etiquetas").getString("KodeaExpl"));
		textAreaExpl.setBounds(58, 51, 377, 94);
		contentPane.add(textAreaExpl);
		
		lblErabiliak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("GonbidatutakoLagunKop"));
		lblErabiliak.setHorizontalAlignment(SwingConstants.LEFT);
		lblErabiliak.setBounds(140, 168, 187, 13);
		contentPane.add(lblErabiliak);
		
		lblErabiliakKant = new JLabel();
		lblErabiliakKant.setText(String.valueOf(facade.getGonbidatuak(b)));
		lblErabiliakKant.setBounds(335, 168, 32, 13);
		contentPane.add(lblErabiliakKant);
		
		
		
		
	}
}
