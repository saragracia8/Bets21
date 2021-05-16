package gui;

import businessLogic.BLFacade;

import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Pronostikoa;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class EmaitzaIpiniGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private EmaitzaIpiniGUI frame;
	private JFrame atzera;
	private MainGUI main;
	private JButton saioaItxi;
	private JButton goBack;
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private final JLabel jLabelPredictions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Predictions"));
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPanePronostikoa = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tablePronostikoa = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelPronostikoa;

	private JLabel lblChampionship;
	private JLabel lblSport;
	private JComboBox<String> comboSport;
	private DefaultComboBoxModel<String> kirolIzenak = new DefaultComboBoxModel<String>();
	private JComboBox<String> comboChampionship;
	private DefaultComboBoxModel<String> txapelketaIzenak = new DefaultComboBoxModel<String>();
	private String selectedSport;

	private JButton lookForEventsbtn;
	private JButton btnEmaitzaIpini;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesPronostikoa = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("PronostikoaN"),
			ResourceBundle.getBundle("Etiquetas").getString("Pronostikoa")

	};
	private JLabel lblError;
	private JLabel lblSuccess;

	public EmaitzaIpiniGUI(MainGUI main, JFrame back) {
		this.main = main;
		this.frame = this;
		this.atzera = back;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 658));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("UpdateAResult"));

		jLabelEventDate.setBounds(new Rectangle(40, 104, 140, 25));
		jLabelQueries.setBounds(57, 294, 406, 14);
		jLabelEvents.setBounds(292, 108, 259, 16);
		jLabelPredictions.setBounds(10, 312, 605, 12);
		jLabelEventDate.setVisible(false);
		jLabelQueries.setVisible(false);
		jLabelEvents.setVisible(false);
		jLabelPredictions.setVisible(false);

		jCalendar1.setBounds(new Rectangle(40, 134, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();
						if (((String) comboSport.getSelectedItem()).equals("Soccer")
								|| ((String) comboSport.getSelectedItem()).equals("Fútbol")
								|| ((String) comboSport.getSelectedItem()).equals("Futbola"))
							selectedSport = "Soccer";
						else if (((String) comboSport.getSelectedItem()).equals("Tennis")
								|| ((String) comboSport.getSelectedItem()).equals("Tenis")
								|| ((String) comboSport.getSelectedItem()).equals("Tenisa"))
							selectedSport = "Tennis";
						else if (((String) comboSport.getSelectedItem()).equals("Basketball")
								|| ((String) comboSport.getSelectedItem()).equals("Baloncesto")
								|| ((String) comboSport.getSelectedItem()).equals("Saskibaloia"))
							selectedSport = "Basketball";
						datesWithEventsCurrentMonth = facade.getEventsMonthSportChampionship(jCalendar1.getDate(),
								selectedSport, (String) comboChampionship.getSelectedItem());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = MainGUI.getBusinessLogic();
						String selectedSport = "";
						if (((String) comboSport.getSelectedItem()).equals("Soccer")
								|| ((String) comboSport.getSelectedItem()).equals("Fútbol")
								|| ((String) comboSport.getSelectedItem()).equals("Futbola"))
							selectedSport = "Soccer";
						else if (((String) comboSport.getSelectedItem()).equals("Tennis")
								|| ((String) comboSport.getSelectedItem()).equals("Tenis")
								|| ((String) comboSport.getSelectedItem()).equals("Tenisa"))
							selectedSport = "Tennis";
						else if (((String) comboSport.getSelectedItem()).equals("Basketball")
								|| ((String) comboSport.getSelectedItem()).equals("Baloncesto")
								|| ((String) comboSport.getSelectedItem()).equals("Saskibaloia"))
							selectedSport = "Basketball";
						Vector<domain.Event> events = facade.getEvents(firstDay, selectedSport,
								(String) comboChampionship.getSelectedItem());

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev : events) {
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
						// JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});
		jCalendar1.setVisible(false);
		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 134, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 319, 406, 116));
		scrollPanePronostikoa.setBounds(40, 446, 406, 116);

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelQueries.setRowCount(0);
				tableModelPronostikoa.setRowCount(0);
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3); // another column added to allocate q objects

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q); // q object added in order to obtain it with tableModelQueries.getValueAt(i,2)
					tableModelQueries.addRow(row);
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); // not shown in
																										// JTable

			}
		});

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();
				domain.Question qi = (domain.Question) tableModelQueries.getValueAt(i, 2); // obtain qi object
				Vector<Pronostikoa> predictions = qi.getPronostikoak();

				tableModelPronostikoa.setDataVector(null, columnNamesPronostikoa);
				tableModelPronostikoa.setColumnCount(3); // another column added to allocate p objects

				if (predictions.isEmpty())
					jLabelPredictions.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoPredictions") + ": " + qi.getQuestion());
				else
					jLabelPredictions.setText(
							ResourceBundle.getBundle("Etiquetas").getString("SelectedQuery") + " " + qi.getQuestion());

				for (domain.Pronostikoa p : predictions) {
					Vector<Object> row = new Vector<Object>();

					row.add(p.getPronostikoaNumber());
					row.add(p.getIzenburua());
					row.add(p);
					tableModelPronostikoa.addRow(row);
				}
				tablePronostikoa.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePronostikoa.getColumnModel().getColumn(1).setPreferredWidth(268);
				tablePronostikoa.getColumnModel().removeColumn(tablePronostikoa.getColumnModel().getColumn(2)); // not
																												// shown
																												// in
																												// JTable

			}
		});

		tablePronostikoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnEmaitzaIpini.setVisible(true);
				btnEmaitzaIpini.setEnabled(false);

				if (jCalendar1.getDate().compareTo(new Date()) < 0) {
					btnEmaitzaIpini.setEnabled(true);
				}
			}
		});

		btnEmaitzaIpini = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UpdateAResult"));
		btnEmaitzaIpini.setBounds(470, 479, 169, 41);
		btnEmaitzaIpini.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				lblSuccess.setText("");
				int i = tablePronostikoa.getSelectedRow();
				int j = tableQueries.getSelectedRow();
				domain.Question q = (domain.Question) tableModelQueries.getValueAt(j,2);
				int k = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(k,2);
				if (i == -1) {// no row selected
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("EmptyPronostikoa"));
				} else if (facade.doesQuestionHaveResult(q)){
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("ResultAlreadyDefined"));
				} else {
					domain.Pronostikoa pi = (domain.Pronostikoa) tableModelPronostikoa.getValueAt(i, 2); // obtain pi																			// object
						facade.emaitzaIpini(ev, q, pi);
					
					lblSuccess.setText(ResourceBundle.getBundle("Etiquetas").getString("EmaitzaIpini"));
				}
			}
		});
		getContentPane().add(btnEmaitzaIpini);
		btnEmaitzaIpini.setVisible(false);

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		scrollPanePronostikoa.setViewportView(tablePronostikoa);
		tableModelPronostikoa = new DefaultTableModel(null, columnNamesPronostikoa);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		tablePronostikoa.setModel(tableModelPronostikoa);
		tablePronostikoa.getColumnModel().getColumn(0).setPreferredWidth(25);
		tablePronostikoa.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneEvents.setVisible(false);
		scrollPaneQueries.setVisible(false);
		scrollPanePronostikoa.setVisible(false);
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePronostikoa, null);

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

		goBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Back"));
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				atzera.setVisible(true);
			}
		});
		goBack.setBounds(553, 541, 85, 21);
		getContentPane().add(goBack);

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
				jLabelEventDate.setVisible(true);
				jLabelQueries.setVisible(true);
				jLabelEvents.setVisible(true);
				jLabelPredictions.setVisible(true);
				jCalendar1.setVisible(true);
				scrollPaneEvents.setVisible(true);
				scrollPaneQueries.setVisible(true);
				scrollPanePronostikoa.setVisible(true);
				tableModelEvents.setRowCount(0);
				tableModelQueries.setRowCount(0);
				tableModelPronostikoa.setRowCount(0);
				String selectedSport = "";
				if (((String) comboSport.getSelectedItem()).equals("Soccer")
						|| ((String) comboSport.getSelectedItem()).equals("Fútbol")
						|| ((String) comboSport.getSelectedItem()).equals("Futbola"))
					selectedSport = "Soccer";
				else if (((String) comboSport.getSelectedItem()).equals("Tennis")
						|| ((String) comboSport.getSelectedItem()).equals("Tenis")
						|| ((String) comboSport.getSelectedItem()).equals("Tenisa"))
					selectedSport = "Tennis";
				else if (((String) comboSport.getSelectedItem()).equals("Basketball")
						|| ((String) comboSport.getSelectedItem()).equals("Baloncesto")
						|| ((String) comboSport.getSelectedItem()).equals("Saskibaloia"))
					selectedSport = "Basketball";
				jCalendar1.setLocale(jCalendar1.getLocale()); // kalendarioa redibujatzeko
				datesWithEventsCurrentMonth = facade.getEventsMonthSportChampionship(jCalendar1.getDate(),
						selectedSport, (String) comboChampionship.getSelectedItem());
				CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);
			}
		});
		lookForEventsbtn.setBounds(390, 48, 181, 21);
		getContentPane().add(lookForEventsbtn);

		lblError = new JLabel();
		lblError.setForeground(Color.RED);
		lblError.setBounds(40, 572, 429, 14);
		getContentPane().add(lblError);

		lblSuccess = new JLabel();
		lblSuccess.setForeground(Color.GREEN);
		lblSuccess.setBounds(40, 577, 411, 14);
		getContentPane().add(lblSuccess);

	}
}
