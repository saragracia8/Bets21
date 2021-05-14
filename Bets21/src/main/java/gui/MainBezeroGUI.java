package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Bezero;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.EventQueue;

@SuppressWarnings("unused")
public class MainBezeroGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private MainGUI main;
	private MainBezeroGUI frame;
	private JLabel lblBezero;
	private JButton saioaItxi;
	private JButton queryQuestion;
	private JButton btnDiruaSartu;
	private JButton btnAskForCode;
	private Bezero b;
	private JButton btnMugimenduakIkusi;
	private JButton btnApustua;
	private JButton btnMinijokoak;
	private JButton btnKopiatu;

	/**
	 * Create the frame.
	 */
	public MainBezeroGUI(MainGUI main, Bezero b) {
		this.main = main;
		frame = this;
		this.b=b;
		this.setTitle(b.getErabiltzailea()); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblBezero = new JLabel();
		lblBezero.setText(ResourceBundle.getBundle("Etiquetas").getString("lblBezero"));
		lblBezero.setBounds(155, 35, 223, 14);
		contentPane.add(lblBezero);
		
		btnDiruaSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu")); 
		btnDiruaSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				JFrame a = new DiruaSartuGUI(main,frame,b);
				a.setVisible(true);
			}
		});
		btnDiruaSartu.setBounds(76, 112, 302, 42);
		contentPane.add(btnDiruaSartu);
		
		saioaItxi = new JButton();
		saioaItxi.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		saioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		saioaItxi.setBounds(273, 0, 159, 25);
		contentPane.add(saioaItxi);
		
		queryQuestion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		queryQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindQuestionsGUI(main,frame);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		queryQuestion.setBounds(76, 59, 302, 42);
		contentPane.add(queryQuestion);
		
		btnMugimenduakIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LookForMovements"));
		btnMugimenduakIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MugimenduakIkusiGUI(main,frame,b);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnMugimenduakIkusi.setBounds(76, 167, 302, 42);
		contentPane.add(btnMugimenduakIkusi);
		
		btnApustua = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet")); 
		btnApustua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ApustuaEginGUI(main,frame,b);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnApustua.setBounds(76, 220, 302, 42);
		contentPane.add(btnApustua);
		
		btnMinijokoak = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Minigames")); 
		btnMinijokoak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MinijokoakGUI(main,frame,b);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnMinijokoak.setBounds(76, 273, 302, 42);
		contentPane.add(btnMinijokoak);
		
		btnAskForCode = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AskForCode"));
		btnAskForCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new KodeaEskatuGUI(main,frame,b);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnAskForCode.setBounds(76, 326, 302, 42);
		contentPane.add(btnAskForCode);
		
		btnKopiatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CopyABet")); 
		btnKopiatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new ApustuaKopiatuGUI(main,frame,b);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnKopiatu.setBounds(76, 379, 302, 42);
		contentPane.add(btnKopiatu);
		

	}
}