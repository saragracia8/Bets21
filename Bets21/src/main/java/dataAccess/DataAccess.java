package dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Apustua;
import domain.Bezero;
import domain.Event;
import domain.Kodea;
import domain.Langilea;
import domain.Mugimendua;
import domain.Pertsona;
import domain.Pronostikoa;
import domain.Question;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {
			Locale.setDefault(new Locale(c.getLocale()));
			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17), "Soccer", "La Liga");

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1), "Soccer", "La Liga");
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1), "Soccer", "La Liga");
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1), "Soccer", "La Liga");
			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1), "Soccer", "La Liga");
			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1), "Soccer", "La Liga");
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1), "Soccer", "La Liga");
			Event ev111 = new Event(11, "Atletico-Athletic", UtilDate.newDate(2021, 2, 2), "Soccer", "La Liga"); // probatarako

			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28), "Soccer", "La Liga");
			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month + 1, 28), "Soccer", "La Liga");
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28), "Soccer",
					"La Liga");
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28), "Soccer", "La Liga");

			Admin ad = new Admin("Admin", "Admin");
			Langilea l = new Langilea("Langilea", "Langilea");
			Bezero b1 = new Bezero("Bezero1", "Bezero1", "W", "E", "111", UtilDate.newDate(2000, 1, 1), 2, "w@m");
			b1.addDirua(30);
			b1.addMugimendua(30, ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"), true);
			b1.restDirua(5);
			b1.addMugimendua(-5,ResourceBundle.getBundle("Etiquetas").getString("Apustua"),false);
			b1.addDirua(7);
			b1.addMugimendua(7,ResourceBundle.getBundle("Etiquetas").getString("Win"),false);
			b1.restDirua(3);
			b1.addMugimendua(-3,ResourceBundle.getBundle("Etiquetas").getString("Apustua"),false);
			b1.addDirua(10);
			b1.addMugimendua(10,ResourceBundle.getBundle("Etiquetas").getString("Win"),false);
			Bezero b2 = new Bezero("Bezero2", "Bezero2", "W", "E", "111", UtilDate.newDate(2000, 1, 1), 2, "w@m");
			Bezero b3 = new Bezero("Bezero3", "Bezero3", "W", "E", "111", UtilDate.newDate(2000, 1, 1), 2, "w@m");
			b3.setKopiatu(true);
			b3.addDirua(5);
			b3.addMugimendua(5, ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"), true);
			b3.restDirua(3);
			b3.addMugimendua(-3,ResourceBundle.getBundle("Etiquetas").getString("Apustua"),false);
			Bezero b4 = new Bezero("Bezero4", "Bezero4", "W", "E", "111", UtilDate.newDate(2000, 1, 1), 2, "w@m");
			Bezero b5 = new Bezero("Bezero5", "Bezero5", "W", "E", "111", UtilDate.newDate(2000, 1, 1), 2, "w@m");
			Bezero b6 = new Bezero("Bezero6", "Bezero6", "W", "E", "111", UtilDate.newDate(2000, 1, 1), 2, "w@m");
			b6.setKopiatu(true);
			b6.addDirua(30);
			b6.addMugimendua(30, ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"), true);
			b6.restDirua(10);
			b6.addMugimendua(-10,ResourceBundle.getBundle("Etiquetas").getString("Apustua"),false);
			b6.addDirua(20);
			b6.addMugimendua(20,ResourceBundle.getBundle("Etiquetas").getString("Win"),false);
			b6.restDirua(7);
			b6.addMugimendua(-7,ResourceBundle.getBundle("Etiquetas").getString("Apustua"),false);
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			// Pronostikoa p1;
			// Pronostikoa p2;
			Pronostikoa p3;
			Pronostikoa p4;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}
			Question q7 = ev111.addQuestion("Zeinek irabaziko du partidua?", 1);
			p3 = q7.addPronostikoa("Atletico", 2);
			p4 = q7.addPronostikoa("Athletic", 4);
			Pronostikoa p5= q1.addPronostikoa("Atletico", (float)1.2);
			Pronostikoa p6= q1.addPronostikoa("Athletic", 2);
			Pronostikoa p7= q2.addPronostikoa("Atletico", (float)1.5);
			Pronostikoa p8= q2.addPronostikoa("Athletic", 2);

			b1.addDirua(10);
			b1.addMugimendua(10, ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"), true);
			b2.addDirua(10);
			b2.addMugimendua(10, ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"), true);
			
			Kodea k1 = b1.addUnekoKodea();
			k1.setUsed(true);
			b1.addAurrekoKodea(k1);
			Kodea k2 = b1.addUnekoKodea();
			k2.setUsed(true);
			b1.addAurrekoKodea(k2);
			Kodea k3 = b1.addUnekoKodea();
			k3.setUsed(true);
			b1.addAurrekoKodea(k3);
			Kodea k4 = b1.addUnekoKodea();
			k4.setUsed(true);
			b1.addAurrekoKodea(k4);
			Kodea k5 = b1.addUnekoKodea();
			k5.setUsed(true);
			b1.addAurrekoKodea(k5);
			Kodea k6 = b1.addUnekoKodea();
			k6.setUsed(true);
			b1.addAurrekoKodea(k6);
			Kodea k7 = b1.addUnekoKodea();
			k7.setUsed(true);
			b1.addAurrekoKodea(k7);
			Kodea k8 = b1.addUnekoKodea();
			k8.setUsed(true);
			b1.addAurrekoKodea(k8);
			Kodea k9 = b1.addUnekoKodea();
			k9.setUsed(true);
			b1.addAurrekoKodea(k9);
			b1.setUnekoKodea(null);
			

			db.persist(k1);
			db.persist(k2);
			db.persist(k3);
			db.persist(k4);
			db.persist(k5);
			db.persist(k6);
			db.persist(k7);
			db.persist(k8);
			db.persist(k9);
			
			db.persist(p3);
			db.persist(p4);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);
			db.persist(q7);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev111); ///
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.persist(ad);
			db.persist(l);
			db.persist(b1);
			db.persist(b2);
			db.persist(b3);
			db.persist(b4);
			db.persist(b5);
			db.persist(b6);
			db.persist(p5);
			db.persist(p6);
			db.persist(p7);
			db.persist(p8);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date, String kirola, String txapelketa) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery(
				"SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.kirola=?2 AND ev.txapelketa=?3", Event.class);
		query.setParameter(1, date);
		query.setParameter(2, kirola);
		query.setParameter(3, txapelketa);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;

	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date         of the month for which days with events want to be
	 *                     retrieved
	 * @param sport
	 * @param championship
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonthSportChampionship(Date date, String sport, String championship) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.kirola=?1 and ev.txapelketa=?2 and ev.eventDate BETWEEN ?3 and ?4",
				Date.class);
		query.setParameter(1, sport);
		query.setParameter(2, championship);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public Pertsona isLogin(String erabiltzailea, String pasahitza) {
		TypedQuery<Pertsona> query = db
				.createQuery("SELECT p FROM Pertsona p WHERE p.erabiltzailea=?1 AND p.pasahitza=?2", Pertsona.class);
		query.setParameter(1, erabiltzailea);
		query.setParameter(2, pasahitza);
		List<Pertsona> p = query.getResultList();
		if (p.isEmpty())
			return null;
		else
			return p.get(0);
	}

	public int register(Bezero p) {
		TypedQuery<Pertsona> query = db.createQuery("SELECT p FROM Pertsona p WHERE p.erabiltzailea=?1",
				Pertsona.class);
		query.setParameter(1, p.getErabiltzailea());
		List<Pertsona> l1 = query.getResultList();
		if (!l1.isEmpty())
			return 1; // erabiltzaile hori existitzen da
		else {
			TypedQuery<Bezero> query2 = db.createQuery("SELECT b FROM Bezero b WHERE b.NA=?1", Bezero.class);
			query2.setParameter(1, p.getNA());
			List<Bezero> l = query2.getResultList();
			if (!l.isEmpty()) {
				return 2; // NA hori existitzen da
			} else {
				db.getTransaction().begin();
				db.persist(p);
				db.getTransaction().commit();
				return 0;
			}
		}
	}

	public int createEvent(Event e) {
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.description=?1 AND ev.eventDate=?2",
				Event.class);
		query.setParameter(1, e.getDescription());
		query.setParameter(2, e.getEventDate());
		List<Event> l1 = query.getResultList();
		if (!l1.isEmpty())
			return 1; // gertaera hori existitzen da
		else {
			db.getTransaction().begin();
			db.persist(e);
			db.getTransaction().commit();
			return 0;
		}
	}

	public int createPronostikoa(String pronostikoa, float kuota, Question qi) {
		Question q = db.find(Question.class, qi.getQuestionNumber());
		if (q == null) {
			return 0;
		}
		if (!q.doesPronostikoaExist(pronostikoa)) {
			q.addPronostikoa(pronostikoa, kuota);
			db.getTransaction().begin();
			db.persist(q);
			db.getTransaction().commit();
			return 0;
		} else {
			return 1;
		}
	}

	public void diruaSartu(float dirua, Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		bez.addDirua(dirua);
		bez.addMugimendua(dirua, ResourceBundle.getBundle("Etiquetas").getString("DiruaSartu"), true);
		db.getTransaction().begin();
		db.persist(bez);
		db.getTransaction().commit();

	}

	public void apustuaEgin(Vector<Pronostikoa> pronostikoak, float dirua, Bezero b, Bezero jabea, float kuota) {
		Pronostikoa pri;
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		Apustua a = bez.addApustua(dirua, pronostikoak, jabea, kuota);
		bez.restDirua(dirua);
		bez.addMugimendua(-(dirua), ResourceBundle.getBundle("Etiquetas").getString("Apustua"), false);
		for (Pronostikoa p : pronostikoak) {
			pri = db.find(Pronostikoa.class, p.getPronostikoaNumber());
			pri.addApustua(a);
			db.getTransaction().begin();
			db.persist(pri);
			db.getTransaction().commit();
		}
		db.getTransaction().begin();
		db.persist(bez);
		db.getTransaction().commit();
	}

	public void deleteEvent(Event evi) {
		Event ev = db.find(Event.class, evi.getEventNumber());
		Bezero b;
		Bezero bez;
		Vector<Question> questions;
		Vector<Pronostikoa> pronostikoak;
		Vector<Apustua> apustuak;
		float dirua;

		questions = ev.getQuestions();
		for (Question qi : questions) {
			pronostikoak = qi.getPronostikoak();
			for (Pronostikoa pi : pronostikoak) {
				apustuak = pi.getApustuak();
				for (Apustua ai : apustuak) {
					dirua = ai.getApustuDirua();
					b = ai.getBezeroa();
					bez = db.find(Bezero.class, b.getErabiltzailea());
					bez.addDirua(dirua);
					bez.addMugimendua(dirua, ResourceBundle.getBundle("Etiquetas").getString("EventDeleted") + ": "
							+ ev.getDescription(), false);
					bez.removeApustua(ai);
					db.getTransaction().begin();
					db.persist(bez);
					db.getTransaction().commit();
				}
			}

		}
		db.getTransaction().begin();
		db.remove(ev);
		db.getTransaction().commit();
	}

	public void emaitzaIpini(Event ev, Question q, Pronostikoa pi) {
		boolean ordaindu = true;
		Question qi = db.find(Question.class, q.getQuestionNumber());
		Pronostikoa p = db.find(Pronostikoa.class, pi.getPronostikoaNumber());
		qi.setResult(p);
		db.getTransaction().begin();
		db.persist(qi);
		db.getTransaction().commit();
		Vector<Apustua> ap = p.getApustuak();
		for (Apustua api : ap) {
			Bezero b = db.find(Bezero.class, api.getBezeroa().getErabiltzailea());
			Float kuota = api.getKuota();
			Float apustuDirua = api.getApustuDirua();
			Vector<Pronostikoa> pronostikoak = api.getPronostikoak();
			for (Pronostikoa pr : pronostikoak) {
				Question qr = pr.getQ();
				if (!pr.equals(qr.getResult())) {
					ordaindu = false;
				}
			}
			if (ordaindu) {
				b.addDirua(kuota * apustuDirua);
				b.addMugimendua(kuota * apustuDirua,ResourceBundle.getBundle("Etiquetas").getString("Win"),false);
				db.getTransaction().begin();
				db.persist(b);
				db.getTransaction().commit();
				Bezero jabea=api.getJabea();
				if(jabea!=null) {
					Bezero aur = db.find(Bezero.class, jabea.getErabiltzailea());
					aur.addDirua((float)(kuota*apustuDirua*0.1));
					aur.addMugimendua((float)(kuota*apustuDirua*0.1), ResourceBundle.getBundle("Etiquetas").getString("WinThanksTo")+b.getErabiltzailea(), true);
					db.getTransaction().begin();
					db.persist(aur);
					db.getTransaction().commit();
				}
				
			}
		}

	}

	public Vector<Mugimendua> getMugimenduak(Bezero b, Date data) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		return bez.getMugimenduak(data);
	}

	public float getDirua(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		return bez.getDirua();
	}

	public boolean doesQuestionHaveResult(Question q) {
		Question qi = db.find(Question.class, q.getQuestionNumber());
		return qi.getResult() != null;
	}

	public double etekinakKalkulatu(Bezero b, Date data) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		return bez.etekinakLortu(data);
	}

	public void minijokoaIrabazi(float dirua, String izenburua, Bezero b, double portzentaia) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		bez.addDirua(dirua * (float) portzentaia);
		bez.addMugimendua(+(dirua * (float) portzentaia), izenburua, false);
		db.getTransaction().begin();
		db.persist(bez);
		db.getTransaction().commit();
	}

	public void minijokoaGaldu(float dirua, String izenburua, Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		bez.restDirua(dirua);
		bez.addMugimendua(-(dirua), izenburua, false);
		db.getTransaction().begin();
		db.persist(bez);
		db.getTransaction().commit();
	}

	public Bezero doesUserExist(String username) {
		TypedQuery<Bezero> query = db.createQuery("SELECT b FROM Bezero b WHERE b.erabiltzailea=?1", Bezero.class);
		query.setParameter(1, username);
		List<Bezero> p = query.getResultList();
		if (p.isEmpty())
			return null;
		else
			return p.get(0);
	}

	public void setKopiatzenDu(Bezero bez, Bezero b, float portzentaia) {
		Bezero kopiatzailea = db.find(Bezero.class, b.getErabiltzailea());
		Bezero kopiatua = db.find(Bezero.class, bez.getErabiltzailea());
		Bezero aurretikKopiatua1=kopiatzailea.getKopiatzenDu();
		if(aurretikKopiatua1!=null) {
			Bezero aurretikKopiatua= db.find(Bezero.class, aurretikKopiatua1.getErabiltzailea());
			if(aurretikKopiatua!=null) {
				aurretikKopiatua.getKopiatzaileak().remove(kopiatzailea);
				db.getTransaction().begin();
				db.persist(aurretikKopiatua);
				db.getTransaction().commit();
			}
		}
		
		
		kopiatzailea.setKopiatzenDu(kopiatua, portzentaia);
		kopiatua.addKopiatzailea(kopiatzailea);
		db.getTransaction().begin();
		db.persist(kopiatua);
		db.persist(kopiatzailea);
		db.getTransaction().commit();

	}

	public Kodea kodeaSortu(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		Kodea k = bez.addUnekoKodea();
		db.getTransaction().begin();
		db.persist(bez);
		db.persist(k);
		db.getTransaction().commit();
		return k;
	}
	
	public Kodea getCurrentCode (Bezero bez) {
		Bezero b = db.find(Bezero.class, bez.getErabiltzailea());
		return b.getUnekoKodea();
	}

	public Kodea getCode(String izena) {
		TypedQuery<Kodea> query = db.createQuery("SELECT k FROM Kodea k WHERE k.izena=?1", Kodea.class);
		query.setParameter(1, izena);
		List<Kodea> k = query.getResultList();
		if (k.isEmpty())
			return null;
		else
			return k.get(0);
	}

	public void kodeaErabili(Kodea k, Bezero erabiltzaile) {
		Kodea kod = db.find(Kodea.class, k.getIzena());
		Bezero e = db.find(Bezero.class, erabiltzaile.getErabiltzailea());
		Bezero s = db.find(Bezero.class, kod.getBezeroa().getErabiltzailea());
		e.addDirua(5);
		s.addDirua(5);
		e.addMugimendua(5, ResourceBundle.getBundle("Etiquetas").getString("KodeaErabili"), true);
		s.addMugimendua(5, ResourceBundle.getBundle("Etiquetas").getString("KodeaErabili"), true);
		kod.setUsed(true);
		s.addAurrekoKodea(kod);
		s.setUnekoKodea(null);
		if(s.erabilitakoKodeKop()%10==0) {
			s.addDirua(10);
			s.addMugimendua(10, ResourceBundle.getBundle("Etiquetas").getString("UseTenCodes"), true);
		}
		db.getTransaction().begin();
		db.persist(kod);
		db.persist(e);
		db.persist(s);
		db.getTransaction().commit();
	}

	public Vector<Bezero> getKopiatzaileak(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		return bez.getKopiatzaileak();
	}

	public void setKopiatu(Bezero b, boolean kopiatu) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		bez.setKopiatu(kopiatu);
		db.getTransaction().begin();
		db.persist(bez);
		db.getTransaction().commit();
	}
	
	public List<Bezero> getKopiatzeaNahiDutenak() {
		TypedQuery<Bezero> query = db.createQuery("SELECT b FROM Bezero b WHERE b.kopiatu=?1", Bezero.class);
		query.setParameter(1, true);
		List<Bezero> b = query.getResultList();
		return b;
	}

	public boolean getKopiatu(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		return bez.isKopiatu();
	}
	
	public void kodeaIraungiDa(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		Kodea unekoa = bez.getUnekoKodea();
		bez.addAurrekoKodea(unekoa);
		bez.setUnekoKodea(null);
		db.getTransaction().begin();
		db.persist(bez);
		db.getTransaction().commit();
	}
	
	public int getGonbidatuak(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		return bez.erabilitakoKodeKop();
	}

	public boolean kopiatzaileaDa(Bezero a, Bezero b) {
		Bezero bez = db.find(Bezero.class, a.getErabiltzailea());
		Bezero kopiatua= db.find(Bezero.class, b.getErabiltzailea());
		return bez.getKopiatzaileak().contains(kopiatua);
	}

	public void utziKopiatzeari(Bezero b, Bezero kop) {
		Bezero kopiatzailea= db.find(Bezero.class, b.getErabiltzailea());
		Bezero kopiatua= db.find(Bezero.class, kop.getErabiltzailea());
		kopiatzailea.setKopiatzenDu(null);
		kopiatua.removeKopiatzailea(kopiatzailea);
		db.getTransaction().begin();
		db.persist(kopiatzailea);
		db.persist(kopiatua);
		db.getTransaction().commit();
		
	}

	public Bezero getKopiatzenDu(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		return bez.getKopiatzenDu();
	}
}
