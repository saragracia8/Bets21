package gui;

import java.text.DateFormat;
import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class CreateQuestionGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelQuery = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Query"));
	private JLabel jLabelMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JLabel lblSport;
	private JLabel lblChampionship;
	private MainGUI main;
	private JFrame atzera;
	private CreateQuestionGUI frame;
	private JTextField jTextFieldQuery = new JTextField();
	private JTextField jTextFieldPrice = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;
	
	private JComboBox<String> comboSport;
	private DefaultComboBoxModel<String> kirolIzenak = new DefaultComboBoxModel<String>();
	private JComboBox<String> comboChampionship;
	private DefaultComboBoxModel<String> txapelketaIzenak = new DefaultComboBoxModel<String>();
	

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back")); //$NON-NLS-1$ //$NON-NLS-2$
	private JButton saioaItxi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
	private JButton btnLookForEvents;
	
	private String selectedSport="";
	
	public CreateQuestionGUI(Vector<domain.Event> v, MainGUI main, JFrame back) {
		this.main = main;
		this.atzera=back;
		frame = this;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 480));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 212, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(279, 182, 250, 20));
		jLabelQuery.setBounds(new Rectangle(25, 289, 75, 20));
		jTextFieldQuery.setBounds(new Rectangle(100, 290, 429, 20));
		jLabelMinBet.setBounds(new Rectangle(25, 319, 75, 20));
		jTextFieldPrice.setBounds(new Rectangle(100, 320, 60, 20));

		jCalendar.setBounds(new Rectangle(40, 130, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 350, 130, 30));
		jButtonCreate.setEnabled(false);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		
		jLabelMsg.setBounds(new Rectangle(240, 360, 225, 20));
		jLabelMsg.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(100, 400, 305, 20));
		jLabelError.setForeground(Color.red);
		
		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		jButtonCreate.setVisible(false);
		jTextFieldQuery.setVisible(false);
		jLabelQuery.setVisible(false);
		jTextFieldPrice.setVisible(false);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldQuery, null);
		this.getContentPane().add(jLabelQuery, null);
		this.getContentPane().add(jTextFieldPrice, null);

		jLabelMinBet.setVisible(false);
		jLabelListOfEvents.setVisible(false);
		jComboBoxEvents.setVisible(false);
		jCalendar.setVisible(false);
		this.getContentPane().add(jLabelMinBet, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);
		this.getContentPane().add(jCalendar, null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
	

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 95, 140, 25);
		jLabelEventDate.setVisible(false);
		getContentPane().add(jLabelEventDate);
		
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
		comboSport.setBounds(140, 34, 125, 22);
		getContentPane().add(comboSport);
		
		
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Soccer"));
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Tennis"));
		kirolIzenak.addElement(ResourceBundle.getBundle("Etiquetas").getString("Basketball"));
		comboSport.setSelectedIndex(0);
		
		comboChampionship = new JComboBox<String>();
		comboChampionship.setBounds(140, 64, 125, 22);
		getContentPane().add(comboChampionship);
		comboChampionship.setModel(txapelketaIzenak);
		
		lblSport = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sport")); 
		lblSport.setBounds(new Rectangle(282, 138, 277, 20));
		lblSport.setBounds(40, 35, 86, 20);
		getContentPane().add(lblSport);
		
		lblChampionship = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Championship"));
		lblChampionship.setBounds(new Rectangle(282, 138, 277, 20));
		lblChampionship.setBounds(40, 65, 86, 20);
		getContentPane().add(lblChampionship);
		
		btnLookForEvents = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LookForEvents"));
		btnLookForEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelMsg.setText("");
				jLabelError.setText("");
				jButtonCreate.setVisible(true);
				jTextFieldQuery.setVisible(true);
				jLabelQuery.setVisible(true);
				jTextFieldPrice.setVisible(true);
				jLabelMinBet.setVisible(true);
				jLabelListOfEvents.setVisible(true);
				jComboBoxEvents.setVisible(true);
				jCalendar.setVisible(true);
				jLabelEventDate.setVisible(true);
				jComboBoxEvents.removeAllItems();
				String selectedSport="";
				if (((String)comboSport.getSelectedItem()).equals("Soccer")||((String)comboSport.getSelectedItem()).equals("Fútbol")||((String)comboSport.getSelectedItem()).equals("Futbola"))
					selectedSport="Soccer";
				else if (((String)comboSport.getSelectedItem()).equals("Tennis")||((String)comboSport.getSelectedItem()).equals("Tenis")||((String)comboSport.getSelectedItem()).equals("Tenisa"))
					selectedSport="Tennis";
				else if (((String)comboSport.getSelectedItem()).equals("Basketball")||((String)comboSport.getSelectedItem()).equals("Baloncesto")||((String)comboSport.getSelectedItem()).equals("Saskibaloia"))
					selectedSport="Basketball";
				jCalendar.setLocale(jCalendar.getLocale()); //kalendarioa redibujatzeko
				datesWithEventsCurrentMonth=facade.getEventsMonthSportChampionship(jCalendar.getDate(), selectedSport, (String) comboChampionship.getSelectedItem());
				CreateQuestionGUI.paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);
			}
		});
		btnLookForEvents.setBounds(320, 49, 168, 21);
		getContentPane().add(btnLookForEvents);
		
		saioaItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				main.setVisible(true);
			}
		});
		saioaItxi.setBounds(419, 0, 159, 25);
		this.getContentPane().add(saioaItxi, null);
		
		btnBack.setBounds(448, 390, 130, 30);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		getContentPane().add(btnBack);

		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar estÃƒ ¡ 30 de enero y se avanza al mes siguiente, devolverÃƒ ­a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃƒ ³digo se dejarÃƒ ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);
						
						BLFacade facade = MainGUI.getBusinessLogic();
						if (((String)comboSport.getSelectedItem()).equals("Soccer")||((String)comboSport.getSelectedItem()).equals("Fútbol")||((String)comboSport.getSelectedItem()).equals("Futbola"))
							selectedSport="Soccer";
						else if (((String)comboSport.getSelectedItem()).equals("Tennis")||((String)comboSport.getSelectedItem()).equals("Tenis")||((String)comboSport.getSelectedItem()).equals("Tenisa"))
							selectedSport="Tennis";
						else if (((String)comboSport.getSelectedItem()).equals("Basketball")||((String)comboSport.getSelectedItem()).equals("Baloncesto")||((String)comboSport.getSelectedItem()).equals("Saskibaloia"))
							selectedSport="Basketball";
						datesWithEventsCurrentMonth=facade.getEventsMonthSportChampionship(jCalendar.getDate(), selectedSport, (String) comboChampionship.getSelectedItem());
					}



					paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay, selectedSport, (String) comboChampionship.getSelectedItem());

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
			}
		});
	}

	
public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.

		
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:datesWithEventsCurrentMonth){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);

	 	
	}
	
	 
	private void jButtonCreate_actionPerformed(ActionEvent e) {
		domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());

		try {
			jLabelError.setText("");
			jLabelMsg.setText("");

			// Displays an exception if the query field is empty
			String inputQuery = jTextFieldQuery.getText();

			if (inputQuery.length() > 0) {

				// It could be to trigger an exception if the introduced string is not a number
				float inputPrice = Float.parseFloat(jTextFieldPrice.getText());

				if (inputPrice <= 0)
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
				else {

					// Obtain the business logic from a StartWindow class (local or remote)
					BLFacade facade = MainGUI.getBusinessLogic();

					facade.createQuestion(event, inputQuery, inputPrice);

					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryCreated"));
				}
			} else
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQuery"));
		} catch (EventFinished e1) {
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished") + ": "
					+ event.getDescription());
		} catch (QuestionAlreadyExist e1) {
			jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
		} catch (java.lang.NumberFormatException e1) {
			jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorFormatNumber"));
		} catch (Exception e1) {

			e1.printStackTrace();

		}
	}
}






