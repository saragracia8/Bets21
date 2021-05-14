package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class CreatePronostikoaGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private CreatePronostikoaGUI frame;
	private JFrame atzera;
	private MainGUI main;
	private JButton saioaItxi;
	private JButton goBack;
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	
	private JLabel lblChampionship;
	private JLabel lblSport;
	private JComboBox<String> comboSport;
	private DefaultComboBoxModel<String> kirolIzenak = new DefaultComboBoxModel<String>();
	private JComboBox<String> comboChampionship;
	private DefaultComboBoxModel<String> txapelketaIzenak = new DefaultComboBoxModel<String>();
	
	private JButton lookForEventsbtn;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private JButton btnChooseQuery;
	private JTextField textPronostikoa;
	private JTextField textKuota;
	
	private JLabel lblPronostikoa;
	private JLabel lblKuota;
	private JButton btnCreatePronostikoa;
	private JLabel lblError;
	private JLabel lblErrorQuery;
	
	private Question qi;
	
	private String selectedSport;

	public CreatePronostikoaGUI(MainGUI main,JFrame back)
	{
		this.main=main;
		this.frame = this;
		this.atzera = back;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		this.setSize(new Dimension(700, 690));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreatePrediction"));

		jLabelEventDate.setBounds(new Rectangle(40, 104, 140, 25));
		jLabelQueries.setBounds(57, 294, 406, 14);
		jLabelEvents.setBounds(292, 108, 259, 16);
		jLabelEventDate.setVisible(false);
		jLabelQueries.setVisible(false);
		jLabelEvents.setVisible(false);
		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		
		jCalendar1.setBounds(new Rectangle(40, 134, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					System.out.println("EVENTO CALENDAR");
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();
						if (((String)comboSport.getSelectedItem()).equals("Soccer")||((String)comboSport.getSelectedItem()).equals("F�tbol")||((String)comboSport.getSelectedItem()).equals("Futbola"))
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
						
						Vector<domain.Event> events=facade.getEvents(firstDay, selectedSport,(String) comboChampionship.getSelectedItem());
						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
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

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});
		jCalendar1.setVisible(false);
		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 134, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(57, 319, 406, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3); // another column added to allocate q objects
				
				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q); // q object added in order to obtain it with tableModelQueries.getValueAt(i,2)
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); // not shown in JTable
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);


		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneEvents.setVisible(false);
		scrollPaneQueries.setVisible(false);
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		
		lblChampionship = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Championship"));
		lblChampionship.setBounds(40, 67, 92, 14);
		getContentPane().add(lblChampionship);
		
		lblSport = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sport"));
		lblSport.setBounds(40, 33, 92, 14);
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
		comboSport.setBounds(134, 29, 131, 22);
		getContentPane().add(comboSport);
		
		comboChampionship = new JComboBox<String>();
		comboChampionship.setBounds(134, 63, 131, 22);
		getContentPane().add(comboChampionship);
		comboChampionship.setModel(txapelketaIzenak);
		
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Soccer"));
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Tennis"));
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Basketball"));
		comboSport.setSelectedIndex(0);
		
		lookForEventsbtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LookForEvents"));
		lookForEventsbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblErrorQuery.setText("");
				lblError.setText("");
				jLabelEventDate.setVisible(true);
				jLabelQueries.setVisible(true);
				jLabelEvents.setVisible(true);
				jCalendar1.setVisible(true);
				scrollPaneEvents.setVisible(true);
				scrollPaneQueries.setVisible(true);
				btnChooseQuery.setVisible(true);
				
				tableModelEvents.setRowCount(0);
				tableModelQueries.setRowCount(0);
				if (((String)comboSport.getSelectedItem()).equals("Soccer")||((String)comboSport.getSelectedItem()).equals("F�tbol")||((String)comboSport.getSelectedItem()).equals("Futbola"))
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
		lookForEventsbtn.setBounds(390, 48, 181, 21);
		getContentPane().add(lookForEventsbtn);
		
		btnChooseQuery = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ChooseQuery"));
		btnChooseQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=tableQueries.getSelectedRow();
				if (i==-1) {//no row selected
					lblErrorQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyQuery"));
					lblError.setText("");
					textPronostikoa.setVisible(false);
					textKuota.setVisible(false);
					lblPronostikoa.setVisible(false);
					lblKuota.setVisible(false);
					btnCreatePronostikoa.setVisible(false);
				} else {
					lblErrorQuery.setText("");
					lblError.setText("");
					qi =(domain.Question)tableModelQueries.getValueAt(i,2); // obtain qi object
					textPronostikoa.setVisible(true);
					textKuota.setVisible(true);
					lblPronostikoa.setVisible(true);
					lblKuota.setVisible(true);
					btnCreatePronostikoa.setVisible(true);
					btnCreatePronostikoa.setEnabled(true);
					btnCreatePronostikoa.setText(ResourceBundle.getBundle("Etiquetas").getString("CreatePrediction"));
				}
			}
		});
		btnChooseQuery.setBounds(489, 366, 149, 21);
		btnChooseQuery.setVisible(false);
		getContentPane().add(btnChooseQuery);
		
		lblErrorQuery = new JLabel();
		lblErrorQuery.setForeground(Color.RED);
		lblErrorQuery.setBounds(57, 447, 581, 13);
		getContentPane().add(lblErrorQuery);
		
		textPronostikoa = new JTextField();
		textPronostikoa.setBounds(145, 478, 318, 19);
		textPronostikoa.setVisible(false);
		getContentPane().add(textPronostikoa);
		textPronostikoa.setColumns(10);
		
		textKuota = new JTextField();
		textKuota.setBounds(145, 532, 96, 19);
		textKuota.setVisible(false);
		getContentPane().add(textKuota);
		textKuota.setColumns(10);
		
		lblPronostikoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Prediction"));
		lblPronostikoa.setBounds(40, 481, 92, 13);
		lblPronostikoa.setVisible(false);
		getContentPane().add(lblPronostikoa);
		
		lblKuota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Fee"));
		lblKuota.setBounds(40, 535, 92, 13);
		lblKuota.setVisible(false);
		getContentPane().add(lblKuota);
		
		btnCreatePronostikoa = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreatePrediction"));
		btnCreatePronostikoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				Date gaur = new Date();
				BLFacade facade = MainGUI.getBusinessLogic();
				if (textPronostikoa.getText().equals("") || textKuota.getText().equals("")) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyError"));
				} else if (jCalendar1.getDate().before(gaur)) {
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("PastDatePronostikoaError"));
				} else {
					try {
						float kuota = Float.parseFloat(textKuota.getText());
						if (kuota<1) {
							lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorFee"));
						} else {
							int n = facade.createPronostikoa(textPronostikoa.getText(), kuota, qi);
							if (n==1) {
								lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorPredictionExists"));
							} else {
								btnCreatePronostikoa.setEnabled(false);
								textPronostikoa.setText("");
								textKuota.setText("");
								btnCreatePronostikoa.setText(ResourceBundle.getBundle("Etiquetas").getString("PredictionCreated"));
							}
						}
					} catch (java.lang.NumberFormatException e1) {
						lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
					}
				}
			}
		});
		btnCreatePronostikoa.setBounds(40, 576, 201, 21);
		btnCreatePronostikoa.setVisible(false);
		getContentPane().add(btnCreatePronostikoa);
		
		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		lblError.setBounds(40, 561, 598, 13);
		getContentPane().add(lblError);
		
		goBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		goBack.setBounds(591, 613, 85, 21);
		getContentPane().add(goBack);

		
		saioaItxi = new JButton();
		saioaItxi.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		saioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		saioaItxi.setBounds(517, 10, 159, 25);
		this.getContentPane().add(saioaItxi);
		
	}
}
