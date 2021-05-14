package gui;


import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bezero;
import domain.Mugimendua;


import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.SwingConstants;

@SuppressWarnings("unused")
public class MugimenduakIkusiGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUrtea;
	private JTextField textEguna;
	private JComboBox<String> comboHilabeteak;
	private DefaultComboBoxModel<String> hilabIzenak = new DefaultComboBoxModel<String>();
	private JLabel lblUrtea;
	private JLabel lblHilabetea;
	private JLabel lblEguna;
	private JButton btnIkusi;
	private JButton saioaItxi;
	private JButton btnBack;
	private JTable tableMugs= new JTable();
	private DefaultTableModel tableModelMugs;
	private JScrollPane scrollPaneMugs = new JScrollPane();
	private JFrame frame;
	private JLabel lblMugInfo;
	private JButton btnEtekinakLortu;
	
	private MainGUI main;
	private JFrame atzera;
	private Bezero b;
	private JLabel lblError;
	
	private String[] columnNamesMugs = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("Amount"), 
			ResourceBundle.getBundle("Etiquetas").getString("Date"),
	};
	private JLabel lblMugTable;
	

	/**
	 * Create the frame.
	 */
	public MugimenduakIkusiGUI(MainGUI main, JFrame atzera, Bezero b) {
		this.main=main;
		this.atzera=atzera;
		this.frame=this;
		this.b=b;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LookForMovements"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textUrtea = new JTextField();
		textUrtea.setBounds(93, 101, 69, 19);
		contentPane.add(textUrtea);
		textUrtea.setColumns(10);
		
		comboHilabeteak = new JComboBox<String>();
		comboHilabeteak.setBounds(188, 100, 155, 21);
		comboHilabeteak.setModel(hilabIzenak);
		contentPane.add(comboHilabeteak);
		
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("January"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("February"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("March"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("April"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("May"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("June"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("July"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("August"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("September"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("October"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("November"));
		hilabIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("December"));
		comboHilabeteak.setSelectedIndex(0);
		
		textEguna = new JTextField();
		textEguna.setBounds(370, 101, 51, 19);
		contentPane.add(textEguna);
		textEguna.setColumns(10);
		
		lblUrtea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Year"));
		lblUrtea.setBounds(93, 78, 69, 13);
		contentPane.add(lblUrtea);
		
		lblHilabetea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Month"));
		lblHilabetea.setBounds(188, 77, 155, 13);
		contentPane.add(lblHilabetea);
		
		lblEguna = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Day"));
		lblEguna.setBounds(370, 78, 52, 13);
		contentPane.add(lblEguna);
		
		btnIkusi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LookForMovements"));
		btnIkusi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblError.setText("");
				tableModelMugs.setRowCount(0);
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					if (facade.validNoFutureDate(Integer.parseInt(textUrtea.getText()), comboHilabeteak.getSelectedIndex(), Integer.parseInt(textEguna.getText()))==1) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNoFutureDate"));
					} else if(facade.validNoFutureDate(Integer.parseInt(textUrtea.getText()), comboHilabeteak.getSelectedIndex(), Integer.parseInt(textEguna.getText()))==2) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("IncorrectDateError"));
					} else {
						try {
							tableModelMugs.setDataVector(null, columnNamesMugs);
							tableModelMugs.setColumnCount(3);
							java.util.Date data= facade.newDate(Integer.parseInt(textUrtea.getText()), comboHilabeteak.getSelectedIndex(), Integer.parseInt(textEguna.getText()));
							
							Vector<Mugimendua> mug = facade.getMugimenduak(b,data);
							if (mug.isEmpty() ) lblMugTable.setText(ResourceBundle.getBundle("Etiquetas").getString("NoMovements"));
							else lblMugTable.setText(ResourceBundle.getBundle("Etiquetas").getString("Movements"));
							for (Mugimendua m:mug){
								Vector<Object> row = new Vector<Object>();
								row.add(m.getIzenburua());
								row.add(m.getDirua());
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
							    String strDate= formatter.format(m.getData());  
								row.add(strDate);
								tableModelMugs.addRow(row);		
							}
							tableMugs.getColumnModel().getColumn(0).setPreferredWidth(230);
							tableMugs.getColumnModel().getColumn(1).setPreferredWidth(60);
							tableMugs.getColumnModel().getColumn(2).setPreferredWidth(50);
							btnEtekinakLortu.setEnabled(true);
							btnEtekinakLortu.setText(ResourceBundle.getBundle("Etiquetas").getString("EtekinaKalkulatu"));
						} catch (Exception e1) {

						}
						
					}
				} catch (NumberFormatException e) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("IncorrectDateError"));
				}
			}
		});
		btnIkusi.setBounds(161, 131, 210, 21);
		contentPane.add(btnIkusi);
		
		
		scrollPaneMugs.setViewportView(tableMugs);
		scrollPaneMugs.setBounds(new Rectangle(48, 208, 445, 150));
		tableModelMugs = new DefaultTableModel(null, columnNamesMugs);
		tableMugs.setModel(tableModelMugs);
		
		scrollPaneMugs.setVisible(true);
		contentPane.add(scrollPaneMugs);
		
		lblMugTable = new JLabel();
		lblMugTable.setBounds(48, 185, 391, 13);
		tableMugs.getColumnModel().getColumn(0).setPreferredWidth(230);
		tableMugs.getColumnModel().getColumn(1).setPreferredWidth(60);
		tableMugs.getColumnModel().getColumn(2).setPreferredWidth(50);
		contentPane.add(lblMugTable);
		
		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		lblError.setBounds(114, 162, 307, 13);
		contentPane.add(lblError);
		
		saioaItxi = new JButton();
		saioaItxi.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		saioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		saioaItxi.setBounds(370, 10, 150, 25);
		this.getContentPane().add(saioaItxi);
		
		btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		btnBack.setBounds(431, 399, 89, 23);
		contentPane.add(btnBack);
		
		lblMugInfo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MovementsExpl"));
		lblMugInfo.setBounds(93, 50, 328, 13);
		contentPane.add(lblMugInfo);
		
		btnEtekinakLortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EtekinaKalkulatu"));
		btnEtekinakLortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				java.util.Date data= facade.newDate(Integer.parseInt(textUrtea.getText()), comboHilabeteak.getSelectedIndex(), Integer.parseInt(textEguna.getText()));
				double etekinak=facade.etekinakKalkulatu(b, data);
				btnEtekinakLortu.setText(ResourceBundle.getBundle("Etiquetas").getString("Etekina")+" "+String.valueOf(etekinak));
				btnEtekinakLortu.setEnabled(false);
			}
		});
		btnEtekinakLortu.setBounds(127, 375, 274, 23);
		contentPane.add(btnEtekinakLortu);
		

		
	}
}
