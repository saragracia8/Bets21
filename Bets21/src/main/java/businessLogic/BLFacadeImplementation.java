package businessLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
//hola
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Bezero;
import domain.Event;
import domain.Kodea;
import domain.Mugimendua;
import domain.Pertsona;
import domain.Pronostikoa;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
		}else {
			dbManager= new DataAccess();
			dbManager.close();
		}

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date, String kirola, String txapelketa) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date, kirola, txapelketa);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
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
	@WebMethod
	public Vector<Date> getEventsMonthSportChampionship(Date date, String sport, String championship) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonthSportChampionship(date, sport, championship);
		dbManager.close();
		return dates;
	}

	@WebMethod
	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	@WebMethod
	public Pertsona isLogin(String erabiltzailea, String pasahitza) {
		dbManager.open(false);
		Pertsona p = dbManager.isLogin(erabiltzailea, pasahitza);
		dbManager.close();
		return p;
	}

	@Override @WebMethod
	public int validDate(int year, int month, int day) {
		try {
			// gaurko data
			LocalDate date = LocalDate.now();

			// formatua ondo dagoen ikusi (egun egokiak hilabete bakoitzerako, urte
			// logikoak..) eta 18 urte dituela egiaztatu
			if (year > 1900 && year < date.getYear()) {
				SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
				date1.setLenient(false);
				date1.parse(day + "/" + (month + 1) + "/" + year);

				LocalDate date2 = LocalDate.of(year, month + 1, day);
				long urteak = ChronoUnit.YEARS.between(date2, date);
				if (urteak >= 18) {
					return 0; // 18 urte ditu
				} else {
					return 1; // ez ditu 18
				}
			} else {
				return 2; // ez da aurreko data bat
			}

		} catch (ParseException e) {
			return 2; // data formatua gaizki dago
		}

	}

	@Override @WebMethod
	public boolean validEmail(String email) {
		String regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
		return Pattern.matches(regexp, email);
	}

	@WebMethod
	public boolean validDNI(String dni) {
		if (dni.length() != 9)
			return false;
		else {
			try {
				int zenb = Integer.parseInt(dni.substring(0, 8));
				char letra = Character.toUpperCase(dni.charAt(8));
				char[] letraZerrenda = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S',
						'Q', 'V', 'H', 'L', 'C', 'K', 'E' };
				int hondarra = zenb % 23;
				char berezkoLetra = letraZerrenda[hondarra];
				if (berezkoLetra == letra)
					return true;
				else
					return false;
			} catch (Exception e) {
				return false;
			}
		}
	}

	@Override @WebMethod
	public int register(Bezero p) {
		dbManager.open(false);
		int n = dbManager.register(p);
		dbManager.close();
		return n;
	}

	@Override @WebMethod
	public boolean validTelNum(String zenb) {
		String regexp = "^[6789]\\d{2}((\\-\\d{3}){2}|(\\.\\d{2}){3}|(\\s\\d{2}){3}|(\\s\\d{3}){2}|\\d{6})$";
		return Pattern.matches(regexp, zenb);

	}

	@Override @WebMethod
	public int validPsw(String psw1, String psw2) {
		if (psw1.length() >= 8) {
			if (psw1.equals(psw2)) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return 2;
		}
	}

	@WebMethod
	public Date newDate(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	@Override @WebMethod
	public int validEventDate(int year, int month, int day) {
		try {
			// formatua ondo dagoen ikusi (egun egokiak hilabete bakoitzerako, urte
			// logikoak..)
			SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
			date1.setLenient(false);
			date1.parse(day + "/" + (month + 1) + "/" + year);

			// iraganeko data ez dela ikusi
			LocalDate today = LocalDate.now();
			LocalDate date2 = LocalDate.of(year, month + 1, day);
			long egunak = ChronoUnit.DAYS.between(today, date2);
			if (egunak >= 0) {
				return 0; // data egokia da
			} else {
				return 1; // iraganeko data da
			}
		} catch (ParseException e) {
			return 2; // data formatua gaizki dago
		}
	}

	@Override @WebMethod
	public int createEvent(String description, Date eventDate, String kirola, String txapelketa) {
		dbManager.open(false);
		Event e = new Event(description, eventDate, kirola, txapelketa);
		int n = dbManager.createEvent(e);
		dbManager.close();
		return n;
	}

	@Override @WebMethod
	public boolean validAmount(String amount) {
		boolean valid;
		try {
			Float.parseFloat(amount);
			valid = true;
		} catch (NumberFormatException excepcion) {
			valid = false;
		}
		return valid;
	}

	@WebMethod
	public int createPronostikoa(String pronostikoa, float kuota, Question qi) {
		dbManager.open(false);
		int n = dbManager.createPronostikoa(pronostikoa, kuota, qi);
		dbManager.close();
		return n;
	}

	public void diruaSartu(float dirua, Bezero b) {
		dbManager.open(false);
		dbManager.diruaSartu(dirua, b);
		dbManager.close();
	}

	@Override @WebMethod
	public int validNoFutureDate(int year, int month, int day) {
		try {
			// formatua ondo dagoen ikusi (egun egokiak hilabete bakoitzerako, urte
			// logikoak..)
			SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
			date1.setLenient(false);
			date1.parse(day + "/" + (month + 1) + "/" + year);

			// etorkizuneko data ez dela ikusi
			LocalDate today = LocalDate.now();
			LocalDate date2 = LocalDate.of(year, month + 1, day);
			long egunak = ChronoUnit.DAYS.between(date2, today);
			if (egunak >= 0) {
				return 0; // data egokia da
			} else {
				return 1; // etorkizuneko data da
			}
		} catch (ParseException e) {
			return 2; // data formatua gaizki dago
		}
	}

	@Override @WebMethod
	public void deleteEvent(Event evi) {
		dbManager.open(false);
		dbManager.deleteEvent(evi);
		dbManager.close();
	}

	@WebMethod
	public void apustuaEgin(Vector<Pronostikoa> pronostikoak, float dirua, Bezero b, Bezero jabea, float kuota) {
		dbManager.open(false);
		dbManager.apustuaEgin(pronostikoak, dirua, b, jabea, kuota);
		dbManager.close();
	}

	@Override @WebMethod
	public void emaitzaIpini(Event ev, Question q, Pronostikoa pi) {
		dbManager.open(false);
		dbManager.emaitzaIpini(ev, q, pi);
		dbManager.close();

	}
	
	@WebMethod
	public Vector<Mugimendua> getMugimenduak(Bezero b, Date data) {
		dbManager.open(false);
		Vector<Mugimendua> v = dbManager.getMugimenduak(b, data);
		dbManager.close();
		return v;
	}

	@WebMethod
	public float getDirua(Bezero b) {
		dbManager.open(false);
		float dirua = dbManager.getDirua(b);
		dbManager.close();
		return dirua;
	}

	@WebMethod
	public boolean doesQuestionHaveResult(Question q) {
		dbManager.open(false);
		boolean ex = dbManager.doesQuestionHaveResult(q);
		dbManager.close();
		return ex;
	}
	
	@WebMethod
	public double etekinakKalkulatu(Bezero b, Date data) {
		dbManager.open(false);
		double etekina = dbManager.etekinakKalkulatu(b, data);
		dbManager.close();
		return etekina;
	}
	
	@WebMethod
	public void minijokoaIrabazi(float dirua, String izenburua, Bezero b, double portzentaia) {
		dbManager.open(false);
		dbManager.minijokoaIrabazi(dirua, izenburua, b, portzentaia);
		dbManager.close();
	}
	
	@WebMethod
	public void minijokoaGaldu(float dirua, String izenburua, Bezero b) {
		dbManager.open(false);
		dbManager.minijokoaGaldu(dirua, izenburua, b);
		dbManager.close();
	}
	
	@Override @WebMethod
	public boolean validNumber(String number) {
		boolean valid;
		try {
			Integer.parseInt(number);
			valid=true;
		}catch (NumberFormatException excepcion) {
			valid=false;
		}
		return valid;
	}
	
	@WebMethod
	public Bezero doesUserExist(String username) {
		dbManager.open(false);
		Bezero b=dbManager.doesUserExist(username);
		dbManager.close();
		return b;
	}

	@Override@WebMethod
	public void setKopiatzenDu(Bezero bez, Bezero b, float portzentaia) {
		dbManager.open(false);
		dbManager.setKopiatzenDu(bez,b,portzentaia);
		dbManager.close();
	}
	
	@WebMethod
	public Kodea kodeaSortu(Bezero bez) {
		dbManager.open(false);
		Kodea k = dbManager.kodeaSortu(bez);
		dbManager.close();
		return k;
	}
	
	@WebMethod
	public Kodea getCurrentCode (Bezero bez) {
		dbManager.open(false);
		Kodea k = dbManager.getCurrentCode(bez);
		dbManager.close();
		return k;
	}
	
	@WebMethod
	public Kodea getCode(String izena) {
		dbManager.open(false);
		Kodea k = dbManager.getCode(izena);
		dbManager.close();
		return k;
	}
	
	@WebMethod
	public void kodeaErabili(Kodea k, Bezero erabiltzaile) {
		dbManager.open(false);
		dbManager.kodeaErabili(k, erabiltzaile);
		dbManager.close();
	}
	
	@WebMethod
	public void kodeaIraungiDa(Bezero b) {
		dbManager.open(false);
		dbManager.kodeaIraungiDa(b);
		dbManager.close();
	}
	
	@WebMethod
	public int getGonbidatuak(Bezero b) {
		dbManager.open(false);
		int g = dbManager.getGonbidatuak(b);
		dbManager.close();
		return g;
	}

	@Override
	public Vector<Bezero> getKopiatzaileak(Bezero b) {
		dbManager.open(false);
		Vector<Bezero> a=dbManager.getKopiatzaileak(b);
		dbManager.close();
		return a;
	}

	@Override
	public void setKopiatu(Bezero b, boolean kopiatu) {
		dbManager.open(false);
		dbManager.setKopiatu(b,kopiatu);
		dbManager.close();
	}
	
	@Override
	public List<Bezero> getKopiatzeaNahiDutenak() {
		dbManager.open(false);
		List<Bezero> b = dbManager.getKopiatzeaNahiDutenak();
		dbManager.close();
		return b;
	}

	@Override
	public boolean getKopiatu(Bezero b) {
		dbManager.open(false);
		boolean a = dbManager.getKopiatu(b);
		dbManager.close();
		return a;
	}
	
	
	public boolean kopiatzaileaDa(Bezero a, Bezero b) {
		dbManager.open(false);
		boolean c = dbManager.kopiatzaileaDa(a,b);
		dbManager.close();
		return c;
	}

	@Override
	public void utziKopiatzeari(Bezero b, Bezero kopiatua) {
		dbManager.open(false);
		dbManager.utziKopiatzeari(b,kopiatua);
		dbManager.close();
		
	}
	
	public Bezero getKopiatzenDu(Bezero b) {
		dbManager.open(false);
		Bezero a=dbManager.getKopiatzenDu(b);
		dbManager.close();
		return a;
	}


}
