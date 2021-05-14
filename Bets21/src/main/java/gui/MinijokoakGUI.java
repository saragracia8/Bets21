package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezero;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class MinijokoakGUI extends JFrame {

	private JPanel contentPane;
	
	private JLabel lblTittle;
	private JButton btnLogOut;
	private JButton btnBack;
	private JButton btnAurpegiGurutze;
	private JButton btnRuleta;
	
	private MainGUI main;
	private JFrame atzera;
	private MinijokoakGUI frame;
	private Bezero b;
	
	public MinijokoakGUI(MainGUI main, JFrame atzera, Bezero b) {
		this.main=main;
		this.atzera=atzera;
		this.b=b;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Minigames"));
		frame=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 509, 321);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTittle = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Minigames")); 
		lblTittle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTittle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTittle.setBounds(10, 35, 491, 21);
		contentPane.add(lblTittle);
		
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
		btnBack.setBounds(375, 235, 106, 23);
		contentPane.add(btnBack);
		
		btnAurpegiGurutze = new JButton(ResourceBundle.getBundle("Etiquetas").getString("HeadsOrTail"));
		btnAurpegiGurutze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AurpegiaGurutzeaGUI a = new AurpegiaGurutzeaGUI(main, frame, b);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnAurpegiGurutze.setBounds(160, 83, 196, 41);
		contentPane.add(btnAurpegiGurutze);
		
		btnRuleta = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Roulette"));
		btnRuleta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErruletaGUI a = new ErruletaGUI(main, frame, b);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnRuleta.setBounds(160, 149, 196, 41);
		contentPane.add(btnRuleta);
		
		
		
		
	}
}