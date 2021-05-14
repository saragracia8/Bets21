package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;

@SuppressWarnings("unused")
public class MainAdminGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private MainAdminGUI frame;
	private MainGUI main;
	
	private JLabel lblAdmin;
	private JButton saioaItxi;

	/**
	 * Create the frame.
	 */
	public MainAdminGUI(MainGUI main) {
		this.frame = this;
		this.main = main;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Admin")); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblAdmin = new JLabel();
		lblAdmin.setText(ResourceBundle.getBundle("Etiquetas").getString("lblAdmin"));
		lblAdmin.setBounds(76, 106, 283, 14);
		contentPane.add(lblAdmin);
		
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
	}
}
