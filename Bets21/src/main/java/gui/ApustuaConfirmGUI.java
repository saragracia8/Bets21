package gui;

import businessLogic.BLFacade;

import domain.Bezero;
import domain.Pronostikoa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class ApustuaConfirmGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private ApustuaConfirmGUI frame;
	private ApustuaEginGUI atzera;
	private JButton goBack;
	private Bezero b;

	private JScrollPane scrollPaneHautatuak = new JScrollPane();
	private JTable tableHautatuak = new JTable();
	private DefaultTableModel tableModelHautatuak;

	private JButton btnBet;

	private JLabel lblSaldo;
	private JLabel lblSaldoKant;
	private JLabel lblMinBet;
	private JLabel lblMinBetKant;
	private JLabel lblKuota;
	private JLabel lblKuotaKant;

	private BLFacade facade;

	private Vector<String> columnNamesHautatuak;

	private float minBet;
	private float kuota;
	private float dirua;
	private Vector<Object> data;
	private JTextField textAmount;
	private JLabel lblErrorBet;
	private JLabel lblSuccess;
	private JLabel lblInfo;
	private JLabel lblAmount;
	private Vector<Pronostikoa> pronostikoak;

	public ApustuaConfirmGUI(ApustuaEginGUI back, Bezero b, float dirua, float minBet, float kuota,
			Vector<Object> data) {
		this.frame = this;
		this.atzera = back;
		this.b = b;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.facade = MainGUI.getBusinessLogic();
		this.dirua = dirua;
		this.minBet = minBet;
		this.kuota = kuota;
		this.data = data;
		this.pronostikoak = new Vector<Pronostikoa>();
		this.columnNamesHautatuak = new Vector<String>();
		columnNamesHautatuak.add(ResourceBundle.getBundle("Etiquetas").getString("Event"));
		columnNamesHautatuak.add(ResourceBundle.getBundle("Etiquetas").getString("Query"));
		columnNamesHautatuak.add(ResourceBundle.getBundle("Etiquetas").getString("Pronostikoa"));
		columnNamesHautatuak.add(ResourceBundle.getBundle("Etiquetas").getString("Fee"));
		columnNamesHautatuak.add("");
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(1026, 455));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Bet"));

		lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Saldo"));
		lblSaldo.setBounds(333, 199, 106, 13);
		getContentPane().add(lblSaldo);

		lblSaldoKant = new JLabel();
		lblSaldoKant.setBounds(586, 199, 50, 13);
		lblSaldoKant.setText(Float.toString(dirua));
		getContentPane().add(lblSaldoKant);

		lblMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice"));
		lblMinBet.setBounds(333, 222, 106, 13);
		getContentPane().add(lblMinBet);

		lblMinBetKant = new JLabel();
		lblMinBetKant.setBounds(586, 222, 46, 13);
		lblMinBetKant.setText(Float.toString(minBet));
		getContentPane().add(lblMinBetKant);

		lblKuota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Fee"));
		lblKuota.setBounds(333, 245, 106, 13);
		getContentPane().add(lblKuota);

		lblKuotaKant = new JLabel();
		lblKuotaKant.setBounds(586, 245, 45, 13);
		lblKuotaKant.setText(String.format("%.2f",kuota));
		getContentPane().add(lblKuotaKant);

		scrollPaneHautatuak.setBounds(64, 60, 861, 116);

		scrollPaneHautatuak.setViewportView(tableHautatuak);
		tableModelHautatuak = new DefaultTableModel(null, columnNamesHautatuak);
		tableHautatuak.setModel(tableModelHautatuak);
		tableModelHautatuak.setDataVector(data, columnNamesHautatuak); 
		tableModelHautatuak.setColumnCount(5); // another column added to allocate p objects
		tableHautatuak.getColumnModel().getColumn(0).setPreferredWidth(130);
		tableHautatuak.getColumnModel().getColumn(1).setPreferredWidth(130);
		tableHautatuak.getColumnModel().getColumn(2).setPreferredWidth(130);
		tableHautatuak.getColumnModel().getColumn(3).setPreferredWidth(40);
		tableHautatuak.getColumnModel().removeColumn(tableHautatuak.getColumnModel().getColumn(4)); // not shown in
																									// JTable

		btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblErrorBet.setText("");
				if (textAmount.getText().equals("")) {
					lblErrorBet.setText(ResourceBundle.getBundle("Etiquetas").getString("Hutsa"));
					return;
				}
				if (!facade.validAmount(textAmount.getText())) {
					lblErrorBet.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountError"));
					return;
				}
				if (Double.valueOf(textAmount.getText()) <= 0) {
					lblErrorBet.setText(ResourceBundle.getBundle("Etiquetas").getString("Negatiboa"));
					return;
				}
				try {
					if (minBet > Float.parseFloat(textAmount.getText())) {
						lblErrorBet.setText(ResourceBundle.getBundle("Etiquetas").getString("LessThanMin"));
						return;
					}
					if (dirua < Float.parseFloat(textAmount.getText())) {
						lblErrorBet.setText(ResourceBundle.getBundle("Etiquetas").getString("LessThanBalance"));
						return;
					}
					for (int j = 0; j < tableModelHautatuak.getRowCount(); j++) {
						domain.Pronostikoa pi = (domain.Pronostikoa) tableModelHautatuak.getValueAt(j, 4);
						pronostikoak.add(pi);
					}
		
					facade.apustuaEgin(pronostikoak, Float.parseFloat(textAmount.getText()), b, null, kuota);

					if(facade.getKopiatu(b)) {
						Vector<Bezero> kopiatzaileak = facade.getKopiatzaileak(b);
						for (Bezero a : kopiatzaileak) {
							float por = (float) a.getPortzentaia();
							if (!(a.getDirua() < Float.parseFloat(textAmount.getText())*por || Float.parseFloat(textAmount.getText())*por< minBet)) {
								facade.apustuaEgin(pronostikoak, Float.parseFloat(textAmount.getText()) * por, a, b, kuota);
							}
						}
						
					}					

					dirua = facade.getDirua(b);
					lblSaldoKant.setText(Float.toString(dirua));
					lblSuccess.setText(ResourceBundle.getBundle("Etiquetas").getString("ApustuaEginOndo"));
					textAmount.setText("");
					btnBet.setEnabled(false);
				} catch (Exception e1) {
					lblErrorBet.setText(ResourceBundle.getBundle("Etiquetas").getString("AmountError"));
					e1.printStackTrace();
				}
			}
		});
		btnBet.setBounds(322, 356, 314, 32);
		getContentPane().add(btnBet);

		this.getContentPane().add(scrollPaneHautatuak, null);
		goBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
				atzera.setDirua();
			}
		});
		goBack.setBounds(840, 362, 85, 21);
		getContentPane().add(goBack);

		lblInfo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BetInfo"));
		lblInfo.setBounds(64, 37, 861, 13);
		getContentPane().add(lblInfo);

		lblAmount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BetAmount"));
		lblAmount.setBounds(333, 304, 136, 13);
		getContentPane().add(lblAmount);

		textAmount = new JTextField();
		textAmount.setBounds(528, 301, 96, 19);
		getContentPane().add(textAmount);
		textAmount.setColumns(10);

		lblErrorBet = new JLabel();
		lblErrorBet.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorBet.setForeground(Color.RED);
		lblErrorBet.setBounds(261, 333, 435, 13);
		getContentPane().add(lblErrorBet);

		lblSuccess = new JLabel();
		lblSuccess.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuccess.setForeground(Color.GREEN);
		lblSuccess.setBounds(322, 333, 314, 13);
		getContentPane().add(lblSuccess);
	}
}
