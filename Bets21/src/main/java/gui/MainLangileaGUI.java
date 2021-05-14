package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import domain.Event;

import javax.swing.JLabel;

@SuppressWarnings("unused")
public class MainLangileaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Vector<Event> createQ;
	private MainGUI main;
	private MainLangileaGUI frame;
	private JFrame back;
	private JLabel lblLangile;
	private JButton saioaItxi;
	private JButton galderakSortu;
	private JButton ekitaldiakSortu;
	private JButton queryQuestion;
	private JButton btnCreatePronostikoa;
	private JButton gertaeraEzabatu;
	private JButton btnEmaitza;

	/**
	 * Create the frame.
	 */
	public MainLangileaGUI(MainGUI main, JFrame back) {
		this.main = main;
		frame = this;
		this.back=back;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Employee")); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblLangile = new JLabel();
		lblLangile.setText(ResourceBundle.getBundle("Etiquetas").getString("lblLangile"));
		lblLangile.setBounds(10, 11, 204, 14);
		contentPane.add(lblLangile);
		
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
		
		galderakSortu = new JButton(); 
		galderakSortu.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestion"));
		galderakSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				JFrame a = new CreateQuestionGUI(createQ, main, frame);
				a.setVisible(true);
			}
		});
		galderakSortu.setBounds(102, 127, 229, 36);
		contentPane.add(galderakSortu);
		
		ekitaldiakSortu = new JButton();
		ekitaldiakSortu.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent")); 
		ekitaldiakSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				JFrame a = new CreateEventGUI(main, frame);
				a.setVisible(true);
			}
		});
		ekitaldiakSortu.setBounds(102, 35, 229, 36);
		contentPane.add(ekitaldiakSortu);
		
		queryQuestion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		queryQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				JFrame a = new FindQuestionsGUI(main,frame);
				a.setVisible(true);
			}
		});
		queryQuestion.setBounds(102, 173, 229, 36);
		contentPane.add(queryQuestion);
		
		btnCreatePronostikoa = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateAPrediction"));
		btnCreatePronostikoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				JFrame a = new CreatePronostikoaGUI(main,frame);
				a.setVisible(true);
			}
		});
		btnCreatePronostikoa.setBounds(102, 219, 229, 36);
		contentPane.add(btnCreatePronostikoa);
		
		gertaeraEzabatu = new JButton();
		gertaeraEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				JFrame a = new GertaeraEzabatuGUI(main,frame);
				a.setVisible(true);
			}
		});
		gertaeraEzabatu.setText(ResourceBundle.getBundle("Etiquetas").getString("DeleteAnEvent"));
		gertaeraEzabatu.setBounds(102, 81, 229, 36);
		contentPane.add(gertaeraEzabatu);
		
		btnEmaitza = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UpdateAResult")); //$NON-NLS-1$ //$NON-NLS-2$
		btnEmaitza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				JFrame a= new EmaitzaIpiniGUI(main,frame);
				a.setVisible(true);
			}
		});
		btnEmaitza.setBounds(102, 265, 229, 36);
		contentPane.add(btnEmaitza);
	}
}