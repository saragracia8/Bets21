package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Bezero;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ApustuaKopiatuGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField diruaText;
	private JTextField portzentaiaText;
	private JLabel lehenTextua;
	private JButton kopiatuBotoia;
	private JButton bilatuBotoia;
	private JLabel lblNewLabel_1;
	private MainGUI main;
	private JFrame atzera;
	private Bezero b;
	private JLabel lblError;
	private JButton outButton;
	private JButton backButton;
	private JButton UtziKopiatzeari;
	private ApustuaKopiatuGUI frame;
	private Bezero bez;
	private JCheckBox chckbxNewCheckBox;
	private JLabel lblTable;
	private JLabel lblCopyingCurrently;
	private JLabel lblAllow;
	
	private JTextArea textKopiatuak;
	private JTextArea textKopiatuaIzan;
	
	
	private JTable tableKop= new JTable();
	private DefaultTableModel tableModelKop;
	private JScrollPane scrollPaneKop = new JScrollPane();
	private String[] columnNamesKop = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("User"), 
			ResourceBundle.getBundle("Etiquetas").getString("Etekina"), 
	};
	private JLabel lblSuccess;

	
	public ApustuaKopiatuGUI(MainGUI main, JFrame back, Bezero b) {
		this.main = main;
		this.atzera = back;
		this.b = b;
		this.frame = this;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 897, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CopyABet")); 
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPaneKop.setViewportView(tableKop);
		scrollPaneKop.setBounds(new Rectangle(10, 109, 255, 241));
		tableModelKop = new DefaultTableModel(null, columnNamesKop);
		tableKop.setModel(tableModelKop);
		contentPane.add(scrollPaneKop);
		
		tableModelKop.setDataVector(null, columnNamesKop);
		tableModelKop.setColumnCount(2);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);
		if (month == 11) {
			month = 0;
			year += 1;
		}
		
		lblTable = new JLabel();
		lblTable.setBounds(10, 86, 255, 13);
		contentPane.add(lblTable);
		
		List<Bezero> kop = facade.getKopiatzeaNahiDutenak();
		if (kop.isEmpty() ) lblTable.setText(ResourceBundle.getBundle("Etiquetas").getString("NoUsers"));
		else {
			lblTable.setText(ResourceBundle.getBundle("Etiquetas").getString("Users"));
			for (Bezero bez:kop){
				if (!b.getErabiltzailea().equals(bez.getErabiltzailea())) {
					Vector<Object> row = new Vector<Object>();
					row.add(bez.getErabiltzailea());
					row.add(facade.etekinakKalkulatu(bez,UtilDate.newDate(year, month, 1)));
					tableModelKop.addRow(row);		
				}
			}
		}
		tableKop.getColumnModel().getColumn(0).setPreferredWidth(70);
		tableKop.getColumnModel().getColumn(1).setPreferredWidth(30);
		
		bilatuBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Ok"));
																									
		bilatuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				if (diruaText.getText().equals("")) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Hutsa"));
				} else {
					bez = facade.doesUserExist(diruaText.getText());
					if (bez == null) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EzDaExistitzen"));
						return;
					}
					if (!bez.isKopiatu() ||bez.getErabiltzailea().equals(b.getErabiltzailea()) || facade.kopiatzaileaDa(b,bez)) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EzinKopiatu"));
						return;
					}
					kopiatuBotoia.setVisible(true);
					portzentaiaText.setVisible(true);
					lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("Portzentaia"));
				}

			}
		});
		bilatuBotoia.setBounds(540, 180, 85, 21);
		contentPane.add(bilatuBotoia);

		diruaText = new JTextField();
		diruaText.setBounds(521, 150, 123, 19);
		contentPane.add(diruaText);
		diruaText.setColumns(10);

		lehenTextua = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Copy"));
		lehenTextua.setHorizontalAlignment(SwingConstants.CENTER);
		lehenTextua.setBounds(339, 127, 495, 13);
		contentPane.add(lehenTextua);

		portzentaiaText = new JTextField();
		portzentaiaText.setBounds(521, 270, 123, 19);
		contentPane.add(portzentaiaText);
		portzentaiaText.setColumns(10);
		portzentaiaText.setVisible(false);

		kopiatuBotoia = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Kopiatu"));
		kopiatuBotoia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblSuccess.setText("");
				lblError.setText("");
				BLFacade facade = MainGUI.getBusinessLogic();
				if (portzentaiaText.getText().equals("")) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Hutsa"));
				} else if (!facade.validAmount(portzentaiaText.getText())) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountError"));
				} else if (Double.valueOf(portzentaiaText.getText()) <= 0) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Negatiboa"));
				} else {
					facade.setKopiatzenDu(bez, b, Float.valueOf(portzentaiaText.getText()) / 100);
					lblSuccess.setText(ResourceBundle.getBundle("Etiquetas").getString("SuccessMsg"));
					lblCopyingCurrently.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrCopy")+" "+bez.getErabiltzailea());
					lblAllow.setText("OK");
					lblAllow.setForeground(Color.GREEN);
					UtziKopiatzeari.setEnabled(true);
					
				}
			}

		});
		kopiatuBotoia.setBounds(540, 299, 85, 21);
		contentPane.add(kopiatuBotoia);
		kopiatuBotoia.setVisible(false);

		lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Portzentaia"));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(296, 247, 565, 13);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setText(" ");

		lblError = new JLabel();
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setBounds(415, 211, 334, 13);
		contentPane.add(lblError);

		outButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		outButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		outButton.setBounds(748, 23, 113, 21);
		contentPane.add(outButton);

		backButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		backButton.setBounds(756, 450, 105, 21);
		contentPane.add(backButton);
		
		chckbxNewCheckBox = new JCheckBox(ResourceBundle.getBundle("Etiquetas").getString("BeCopied"));
		chckbxNewCheckBox.setSelected(facade.getKopiatu(b));
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean kopiatu=facade.getKopiatu(b);
				facade.setKopiatu(b, !kopiatu);
			}
		});
		chckbxNewCheckBox.setBounds(10, 376, 255, 21);
		contentPane.add(chckbxNewCheckBox);
		
		textKopiatuak = new JTextArea();
		textKopiatuak.setLineWrap(true);
		textKopiatuak.setEditable(false);
		textKopiatuak.setText(ResourceBundle.getBundle("Etiquetas").getString("KopiatuakExpl"));
		textKopiatuak.setBounds(10, 21, 255, 58);
		contentPane.add(textKopiatuak);
		
		textKopiatuaIzan = new JTextArea();
		textKopiatuaIzan.setLineWrap(true);
		textKopiatuaIzan.setEditable(false);
		textKopiatuaIzan.setText(ResourceBundle.getBundle("Etiquetas").getString("KopiatuaIzanExpl"));
		textKopiatuaIzan.setBounds(10, 418, 255, 76);
		contentPane.add(textKopiatuaIzan);
		
		lblSuccess = new JLabel();
		lblSuccess.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuccess.setBounds(425, 330, 324, 13);
		contentPane.add(lblSuccess);
		
		UtziKopiatzeari = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KopiatzeariUtzi"));
		UtziKopiatzeari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Bezero kopiatua= facade.getKopiatzenDu(b);
				if(kopiatua==null) {
					return;
				}else {
					facade.utziKopiatzeari(b,kopiatua);
					lblCopyingCurrently.setText(ResourceBundle.getBundle("Etiquetas").getString("NoCopy"));
					UtziKopiatzeari.setEnabled(false);
				}
				
			}
		});
		UtziKopiatzeari.setBounds(296, 58, 123, 21);
		contentPane.add(UtziKopiatzeari);
		
		lblCopyingCurrently = new JLabel();
		Bezero cop = facade.getKopiatzenDu(b);
		if (cop==null) {
			lblCopyingCurrently.setText(ResourceBundle.getBundle("Etiquetas").getString("NoCopy"));
			UtziKopiatzeari.setEnabled(false);
		} else {
			lblCopyingCurrently.setText(ResourceBundle.getBundle("Etiquetas").getString("CurrCopy")+" "+cop.getErabiltzailea());
			UtziKopiatzeari.setEnabled(true);
		}
		lblCopyingCurrently.setBounds(296, 23, 380, 13);
		contentPane.add(lblCopyingCurrently);
		
		lblAllow = new JLabel(); 
		Bezero cop2 = facade.getKopiatzenDu(b);
		if (cop2==null) {
			lblAllow.setText("");
		} else {
			if (!(cop.isKopiatu())) {
				lblAllow.setText("X");
				lblAllow.setForeground(Color.RED);		
			}else {
				lblAllow.setText("OK");
				lblAllow.setForeground(Color.GREEN);	
			}
		}
		lblAllow.setBounds(692, 23, 46, 14);
		contentPane.add(lblAllow);
		
	}
}
