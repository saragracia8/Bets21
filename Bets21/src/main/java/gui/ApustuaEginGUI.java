package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Bezero;
import domain.Pronostikoa;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class ApustuaEginGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private ApustuaEginGUI frame;
	private JFrame atzera;
	private MainGUI main;
	private JButton saioaItxi;
	private JButton goBack;
	private Bezero b;
	 
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private	JScrollPane scrollPanePronostikoa = new JScrollPane();
	private	JScrollPane scrollPaneHautatuak = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tablePronostikoa = new JTable();
	private JTable tableHautatuak = new JTable();
	
	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelPronostikoa;
	private DefaultTableModel tableModelHautatuak;
	
	private JLabel lblChampionship;
	private JLabel lblSport;
	private JComboBox<String> comboSport;
	private DefaultComboBoxModel<String> kirolIzenak = new DefaultComboBoxModel<String>();
	private JComboBox<String> comboChampionship;
	private DefaultComboBoxModel<String> txapelketaIzenak = new DefaultComboBoxModel<String>();
	private String selectedSport;

	private JButton lookForEventsbtn;
	private JButton btnGehitu;
	private JButton btnBet;
	
	private JLabel lblSaldo;
	private JLabel lblSaldoKant;
	private JLabel lblMinBet;
	private JLabel lblMinBetKant;
	private JLabel lblKuota;
	private JLabel lblKuotaKant;
	
	private BLFacade facade;
	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice")

	};
	private String[] columnNamesPronostikoa = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("PronostikoaN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Pronostikoa"),
			ResourceBundle.getBundle("Etiquetas").getString("Fee")

	};
	private String[] columnNamesHautatuak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			ResourceBundle.getBundle("Etiquetas").getString("Pronostikoa"),
			ResourceBundle.getBundle("Etiquetas").getString("Fee")

	};
	private JLabel lblError;
	private JLabel lblErrorBet;
	private JLabel lblEventDate;
	private JLabel lblQueries;
	private JLabel lblPredictions;
	private JLabel lblEvents;
	private JLabel lblHautatuak;
	
	private JButton btnEzabatu;
	
	private ArrayList<Integer> selectedQNumbers;
	private float minBet;
	private float kuota;
	private float dirua;


	public ApustuaEginGUI(MainGUI main,JFrame back, Bezero b)
	{
		this.main=main;
		this.frame = this;
		this.atzera = back;
		this.b=b;
		this.selectedQNumbers = new ArrayList<Integer>();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.facade = MainGUI.getBusinessLogic();
		this.dirua = facade.getDirua(b);
		this.minBet=0;
		this.kuota=1;
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	
	private void jbInit() throws Exception
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(1026, 866));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		
		lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Saldo"));
		lblSaldo.setBounds(333, 654, 106, 13);
		getContentPane().add(lblSaldo);
		
		lblSaldoKant = new JLabel();
		lblSaldoKant.setBounds(597, 654, 50, 13);
		lblSaldoKant.setText(Float.toString(dirua));
		getContentPane().add(lblSaldoKant);
		
		lblMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice"));
		lblMinBet.setBounds(333, 677, 106, 13);
		getContentPane().add(lblMinBet);
		
		lblMinBetKant = new JLabel();
		lblMinBetKant.setBounds(597, 677, 46, 13);
		lblMinBetKant.setText(Float.toString(minBet));
		getContentPane().add(lblMinBetKant);
		
		lblKuota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Fee"));
		lblKuota.setBounds(333, 700, 106, 13);
		getContentPane().add(lblKuota);
		
		lblKuotaKant = new JLabel();
		lblKuotaKant.setBounds(597, 700, 45, 13);
		lblKuotaKant.setText(String.format("%.2f",kuota));
		getContentPane().add(lblKuotaKant);
		
		lblEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
		lblEventDate.setBounds(169, 112, 226, 13);
		getContentPane().add(lblEventDate);
		
		lblQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
		lblQueries.setBounds(57, 297, 406, 13);
		getContentPane().add(lblQueries);
		
		lblPredictions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Predictions"));
		lblPredictions.setBounds(512, 296, 406, 13);
		getContentPane().add(lblPredictions);
		
		lblEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
		lblEvents.setBounds(450, 112, 346, 13);
		getContentPane().add(lblEvents);
		
		lblHautatuak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListOfChosen"));
		lblHautatuak.setBounds(57, 497, 861, 13);
		getContentPane().add(lblHautatuak);
		
		scrollPaneEvents.setBounds(new Rectangle(450, 134, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(57, 319, 406, 116));
		scrollPanePronostikoa.setBounds(512, 319, 406, 116);
		scrollPaneHautatuak.setBounds(57, 520, 861, 116);
		
		jCalendar1.setBounds(new Rectangle(170, 135, 225, 150));

		scrollPaneHautatuak.setViewportView(tableHautatuak);
		tableModelHautatuak=new DefaultTableModel(null, columnNamesHautatuak);
		tableHautatuak.setModel(tableModelHautatuak);
		tableModelHautatuak.setRowCount(0);
		tableModelHautatuak.setDataVector(null, columnNamesHautatuak);
		tableModelHautatuak.setColumnCount(5); // another column added to allocate p objects
		tableHautatuak.getColumnModel().getColumn(0).setPreferredWidth(130);
		tableHautatuak.getColumnModel().getColumn(1).setPreferredWidth(130);
		tableHautatuak.getColumnModel().getColumn(2).setPreferredWidth(130);
		tableHautatuak.getColumnModel().getColumn(3).setPreferredWidth(40);
		tableHautatuak.getColumnModel().removeColumn(tableHautatuak.getColumnModel().getColumn(4)); // not shown in JTable
		
		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		
		scrollPanePronostikoa.setViewportView(tablePronostikoa);
		tableModelPronostikoa=new DefaultTableModel(null, columnNamesPronostikoa);
		
		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(240);
		tableQueries.getColumnModel().getColumn(2).setPreferredWidth(28);
		
		tablePronostikoa.setModel(tableModelPronostikoa);
		tablePronostikoa.getColumnModel().getColumn(0).setPreferredWidth(25);
		tablePronostikoa.getColumnModel().getColumn(1).setPreferredWidth(268);
		
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePronostikoa, null);	
		this.getContentPane().add(scrollPaneHautatuak, null);


		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{
				tableModelEvents.setRowCount(0);
				tableModelQueries.setRowCount(0);
				tableModelPronostikoa.setRowCount(0);
				lblQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
				lblPredictions.setText(ResourceBundle.getBundle("Etiquetas").getString("Predictions"));

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();
						if (((String)comboSport.getSelectedItem()).equals("Soccer")||((String)comboSport.getSelectedItem()).equals("Fútbol")||((String)comboSport.getSelectedItem()).equals("Futbola"))
							selectedSport="Soccer";
						else if (((String)comboSport.getSelectedItem()).equals("Tennis")||((String)comboSport.getSelectedItem()).equals("Tenis")||((String)comboSport.getSelectedItem()).equals("Tenisa"))
							selectedSport="Tennis";
						else if (((String)comboSport.getSelectedItem()).equals("Basketball")||((String)comboSport.getSelectedItem()).equals("Baloncesto")||((String)comboSport.getSelectedItem()).equals("Saskibaloia"))
							selectedSport="Basketball";
						datesWithEventsCurrentMonth=facade.getEventsMonthSportChampionship(jCalendar1.getDate(), selectedSport, (String) comboChampionship.getSelectedItem());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
					
					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();
						String selectedSport="";
						if (((String)comboSport.getSelectedItem()).equals("Soccer")||((String)comboSport.getSelectedItem()).equals("Fútbol")||((String)comboSport.getSelectedItem()).equals("Futbola"))
							selectedSport="Soccer";
						else if (((String)comboSport.getSelectedItem()).equals("Tennis")||((String)comboSport.getSelectedItem()).equals("Tenis")||((String)comboSport.getSelectedItem()).equals("Tenisa"))
							selectedSport="Tennis";
						else if (((String)comboSport.getSelectedItem()).equals("Basketball")||((String)comboSport.getSelectedItem()).equals("Baloncesto")||((String)comboSport.getSelectedItem()).equals("Saskibaloia"))
							selectedSport="Basketball";
						Vector<domain.Event> events=facade.getEvents(firstDay, selectedSport,(String) comboChampionship.getSelectedItem());

						if (events.isEmpty() ) lblEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else lblEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						lblQueries.setText(e1.getMessage());
					}

				}
			} 
		});
		jCalendar1.setVisible(true);
		this.getContentPane().add(jCalendar1, null);
		
		

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblPredictions.setText(ResourceBundle.getBundle("Etiquetas").getString("Predictions"));
				tableModelQueries.setRowCount(0);
				tableModelPronostikoa.setRowCount(0);
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(4); // another column added to allocate q objects

				if (queries.isEmpty())
					lblQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					lblQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();
					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q.getBetMinimum());
					row.add(q); // q object added in order to obtain it with tableModelQueries.getValueAt(i,2)
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(240);
				tableQueries.getColumnModel().getColumn(2).setPreferredWidth(28);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(3)); // not shown in JTable
			}
		});
		
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelPronostikoa.setRowCount(0);
				int i=tableQueries.getSelectedRow();
				domain.Question qi=(domain.Question)tableModelQueries.getValueAt(i,3); // obtain qi object
				Vector<Pronostikoa> predictions=qi.getPronostikoak();

				tableModelPronostikoa.setDataVector(null, columnNamesPronostikoa);
				tableModelPronostikoa.setColumnCount(4); // another column added to allocate p objects

				if (predictions.isEmpty())
					lblPredictions.setText(ResourceBundle.getBundle("Etiquetas").getString("NoPredictions")+": "+qi.getQuestion());
				else 
					lblPredictions.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedQuery")+" "+qi.getQuestion());

				for (domain.Pronostikoa p:predictions){
					Vector<Object> row = new Vector<Object>();

					row.add(p.getPronostikoaNumber());
					row.add(p.getIzenburua());
					row.add(p.getKuota());
					row.add(p); // p object added in order to obtain it with tableModelPronostikoa.getValueAt(i,2)
					tableModelPronostikoa.addRow(row);	
				}
				tablePronostikoa.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePronostikoa.getColumnModel().getColumn(1).setPreferredWidth(240);
				tablePronostikoa.getColumnModel().getColumn(2).setPreferredWidth(28);
				tablePronostikoa.getColumnModel().removeColumn(tablePronostikoa.getColumnModel().getColumn(3)); // not shown in JTable
			}
		});
		
		btnGehitu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddToBet")); 
		btnGehitu.setBounds(333, 461, 314, 25);
		getContentPane().add(btnGehitu);
		btnGehitu.setVisible(true);
		btnGehitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				int k=tableEvents.getSelectedRow();
				int j=tableQueries.getSelectedRow();
				int i=tablePronostikoa.getSelectedRow();
				if (i==-1||j==-1||k==-1) {//no row selected
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyPronostikoa"));
				} else {
					domain.Event ev=(domain.Event)tableModelEvents.getValueAt(k,2); // obtain ev object
					if (ev.getEventDate().compareTo(new Date()) < 0) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EventPastDateError"));
						return;
					}
					domain.Question qi=(domain.Question)tableModelQueries.getValueAt(j,3); //obtain qi object
					if (!selectedQNumbers.contains(qi.getQuestionNumber())) {
						if (qi.getBetMinimum()>minBet) {
							minBet=qi.getBetMinimum();
							lblMinBetKant.setText(Float.toString(minBet));
						}
						selectedQNumbers.add(qi.getQuestionNumber());
						domain.Pronostikoa pi=(domain.Pronostikoa)tableModelPronostikoa.getValueAt(i,3); // obtain pi object
						Vector<Object> row = new Vector<Object>();
						row.add(ev.getDescription());
						row.add(qi.getQuestion());
						row.add(pi.getIzenburua());
						row.add(pi.getKuota());
						row.add(pi);
						tableModelHautatuak.addRow(row);
						kuota=kuota*pi.getKuota();
						lblKuotaKant.setText(String.format("%.2f",kuota));
					} else {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("AlreadyChosen"));
					}
				}
			}
		});
		
		btnEzabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Delete"));
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableHautatuak.getSelectedRow();
				if (i!=-1) {
					domain.Pronostikoa p = (domain.Pronostikoa)tableModelHautatuak.getValueAt(i,4);
					selectedQNumbers.remove(p.getQ().getQuestionNumber());
					tableModelHautatuak.removeRow(i);
					kuota=kuota/p.getKuota();
					lblKuotaKant.setText(String.format("%.2f",kuota));
					if (p.getQ().getBetMinimum()==minBet) {
						minBet=0;
						for (int j=0; j<tableModelHautatuak.getRowCount(); j++) {
							domain.Pronostikoa pi = (domain.Pronostikoa)tableModelHautatuak.getValueAt(j,4);
							if(pi.getQ().getBetMinimum()>minBet) {
								minBet=pi.getQ().getBetMinimum();
							}
						}
						lblMinBetKant.setText(Float.toString(minBet));
					}
				}
			}
		});
		btnEzabatu.setBounds(771, 650, 147, 21);
		getContentPane().add(btnEzabatu);
		
		btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblErrorBet.setText("");
				if (tableModelHautatuak.getRowCount()==0) {
					lblErrorBet.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyBet"));
					return;
				}
				if (dirua<minBet) {
					lblErrorBet.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorLessMoney"));
					return;
				}
				Vector data = tableModelHautatuak.getDataVector();
				ApustuaConfirmGUI a = new ApustuaConfirmGUI(frame, b, dirua, minBet, kuota, data);
				a.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnBet.setBounds(333, 744, 314, 32);
		getContentPane().add(btnBet);
		
		saioaItxi = new JButton();
		saioaItxi.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		saioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		saioaItxi.setBounds(843, 10, 159, 25);
		this.getContentPane().add(saioaItxi);
		
		goBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		goBack.setBounds(893, 784, 85, 21);
		getContentPane().add(goBack);
		
		lblChampionship = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Championship"));
		lblChampionship.setBounds(173, 67, 92, 14);
		getContentPane().add(lblChampionship);
		
		lblSport = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sport"));
		lblSport.setBounds(173, 33, 92, 14);
		getContentPane().add(lblSport);
		
		comboSport = new JComboBox<String>();
		comboSport.setModel(kirolIzenak);
		comboSport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedSport = (String) comboSport.getSelectedItem();
				if (selectedSport.equals((ResourceBundle.getBundle("Etiquetas").getString("Soccer")))) {
					txapelketaIzenak.removeAllElements();
					txapelketaIzenak.addElement("La Liga");
					txapelketaIzenak.addElement("Serie A");
					txapelketaIzenak.addElement("Premier League");
				} else if (selectedSport.equals((ResourceBundle.getBundle("Etiquetas").getString("Basketball")))) {
					txapelketaIzenak.removeAllElements();
					txapelketaIzenak.addElement("Liga ACB");
					txapelketaIzenak.addElement("NBA");
					txapelketaIzenak.addElement("Euroliga");
				} else if (selectedSport.equals((ResourceBundle.getBundle("Etiquetas").getString("Tennis")))) {
					txapelketaIzenak.removeAllElements();
					txapelketaIzenak.addElement("Wimbledon");
					txapelketaIzenak.addElement("Roland Garros");
					txapelketaIzenak.addElement("US Open");
				}	
			}
		});
		comboSport.setBounds(308, 29, 131, 22);
		getContentPane().add(comboSport);
		
		comboChampionship = new JComboBox<String>();
		comboChampionship.setBounds(308, 63, 131, 22);
		getContentPane().add(comboChampionship);
		comboChampionship.setModel(txapelketaIzenak);
		
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Soccer"));
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Tennis"));
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Basketball"));
		comboSport.setSelectedIndex(0);
		
		lookForEventsbtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LookForEvents"));
		lookForEventsbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jCalendar1.setVisible(true);
				scrollPaneEvents.setVisible(true);
				scrollPaneQueries.setVisible(true);
				scrollPanePronostikoa.setVisible(true);
				tableModelEvents.setRowCount(0);
				tableModelQueries.setRowCount(0);
				tableModelPronostikoa.setRowCount(0);
				lblEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events"));
				lblQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
				lblPredictions.setText(ResourceBundle.getBundle("Etiquetas").getString("Predictions"));
				String selectedSport="";
				if (((String)comboSport.getSelectedItem()).equals("Soccer")||((String)comboSport.getSelectedItem()).equals("Fútbol")||((String)comboSport.getSelectedItem()).equals("Futbola"))
					selectedSport="Soccer";
				else if (((String)comboSport.getSelectedItem()).equals("Tennis")||((String)comboSport.getSelectedItem()).equals("Tenis")||((String)comboSport.getSelectedItem()).equals("Tenisa"))
					selectedSport="Tennis";
				else if (((String)comboSport.getSelectedItem()).equals("Basketball")||((String)comboSport.getSelectedItem()).equals("Baloncesto")||((String)comboSport.getSelectedItem()).equals("Saskibaloia"))
					selectedSport="Basketball";
				jCalendar1.setLocale(jCalendar1.getLocale()); //kalendarioa redibujatzeko
				datesWithEventsCurrentMonth=facade.getEventsMonthSportChampionship(jCalendar1.getDate(), selectedSport, (String) comboChampionship.getSelectedItem());
				CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
			}
		});
		lookForEventsbtn.setBounds(500, 48, 181, 21);
		lookForEventsbtn.setVisible(true);
		getContentPane().add(lookForEventsbtn);
		
		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		lblError.setBounds(333, 445, 314, 14);
		getContentPane().add(lblError);
		
		lblErrorBet = new JLabel();
		lblErrorBet.setForeground(Color.RED);
		lblErrorBet.setBounds(333, 721, 314, 13);
		getContentPane().add(lblErrorBet);
		
	}
	
	protected void setDirua () {
		dirua = facade.getDirua(b);
		lblSaldoKant.setText(Float.toString(dirua));
	}
}


