package gui;

//import java.awt.BorderLayout;
//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezero;

import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;

@SuppressWarnings("unused")
public class DiruaSartuGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textAmount;
	private JButton btnDiruaSartu;
	private JLabel lblAmount;
	private DiruaSartuGUI frame;
	private MainGUI main;
	private JFrame atzera;
	private JLabel lblError;
	private JButton btnBack;
	private JLabel lblSuccess;
	private JButton saioaItxi;
	private Bezero b;

	/**
	 * Create the frame.
	 */
	public DiruaSartuGUI(MainGUI main, JFrame atzera, Bezero b) {
		this.main = main;
		this.atzera = atzera;
		this.b = b;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"));
		frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 434, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnDiruaSartu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"));
		btnDiruaSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				lblSuccess.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				if (textAmount.getText().equals("")) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Hutsa"));
				} else if (!facade.validAmount(textAmount.getText())) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountError"));
				} else if (Double.valueOf(textAmount.getText()) <= 0) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Negatiboa"));
				} else {
					try {
						facade.diruaSartu(Float.parseFloat(textAmount.getText()), b);
						lblSuccess.setText(ResourceBundle.getBundle("Etiquetas").getString("DiruaSartuOndo"));
						textAmount.setText("");
					} catch (Exception e1) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountError"));
					}
				}
			}
		});
		btnDiruaSartu.setBounds(58, 146, 313, 37);
		contentPane.add(btnDiruaSartu);

		textAmount = new JTextField();
		textAmount.setBounds(199, 84, 131, 20);
		contentPane.add(textAmount);
		textAmount.setColumns(10);

		lblAmount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Amount"));
		lblAmount.setBounds(80, 86, 71, 14);
		contentPane.add(lblAmount);

		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		lblError.setBounds(58, 127, 301, 14);
		contentPane.add(lblError);

		saioaItxi = new JButton();
		saioaItxi.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		saioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		saioaItxi.setBounds(285, 10, 121, 25);
		this.getContentPane().add(saioaItxi);
		
		btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		btnBack.setBounds(317, 230, 89, 23);
		contentPane.add(btnBack);
		
		lblSuccess = new JLabel();
		lblSuccess.setBounds(58, 193, 272, 13);
		contentPane.add(lblSuccess);
	}
}
