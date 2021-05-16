package businessLogic;

import java.util.Vector;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Question;
import domain.Bezero;
import domain.Event;
import domain.Kodea;
import domain.Mugimendua;
import domain.Pertsona;
import domain.Pronostikoa;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

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
	Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;

	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date, String kirola, String txapelketa);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();

	@WebMethod
	public Pertsona isLogin(String erabiltzailea, String pasahitza);

	@WebMethod
	public int validDate(int year, int month, int day);

	@WebMethod
	public boolean validEmail(String email);

	@WebMethod
	public boolean validDNI(String dni);

	@WebMethod
	public boolean validTelNum(String zenb);

	@WebMethod
	public int register(Bezero p);

	@WebMethod
	public int validPsw(String psw1, String psw2);

	@WebMethod
	public Date newDate(int parseInt, int selectedIndex, int parseInt2);

	@WebMethod
	public int createEvent(String description, Date eventDate, String kirola, String txapelketa);

	@WebMethod
	public int validEventDate(int year, int month, int day);

	@WebMethod
	public boolean validAmount(String amount);

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
	public Vector<Date> getEventsMonthSportChampionship(Date date, String sport, String championship);

	@WebMethod
	public int createPronostikoa(String pronostikoa, float kuota, Question qi);

	@WebMethod
	public void diruaSartu(float dirua, Bezero b);

	@WebMethod
	public int validNoFutureDate(int year, int month, int day);

	@WebMethod
	public void deleteEvent(Event evi);

	@WebMethod
	public void apustuaEgin(Vector<Pronostikoa> pronostikoak, float dirua, Bezero b, Bezero jabea, float kuota);

	@WebMethod
	public void emaitzaIpini(Event ev, Question q, Pronostikoa pi);

	@WebMethod
	public Vector<Mugimendua> getMugimenduak(Bezero b, Date data);

	@WebMethod
	public float getDirua(Bezero b);

	@WebMethod
	public boolean doesQuestionHaveResult(Question q);
	
	@WebMethod
	public double etekinakKalkulatu(Bezero b, Date data);
	
	@WebMethod
	public void minijokoaIrabazi(float dirua, String izenburua, Bezero b, double portzentaia);
	
	@WebMethod
	public void minijokoaGaldu(float dirua, String izenburua, Bezero b);
	
	@WebMethod
	public boolean validNumber(String number);
	
	@WebMethod
	public Bezero doesUserExist(String text);

	@WebMethod
	public void setKopiatzenDu(Bezero bez, Bezero b, float portzentaia);
	
	@WebMethod
	public Kodea kodeaSortu(Bezero b);
	
	@WebMethod
	public Kodea getCurrentCode (Bezero bez);
	
	@WebMethod
	public Kodea getCode(String izena);
	
	@WebMethod
	public void kodeaErabili(Kodea k, Bezero erabiltzaile);
	
	@WebMethod
	public int getGonbidatuak(Bezero b);

	@WebMethod
	public Vector<Bezero> getKopiatzaileak(Bezero b);

	@WebMethod
	void setKopiatu(Bezero b, boolean c);
	
	@WebMethod
	public List<Bezero> getKopiatzeaNahiDutenak();

	@WebMethod
	public boolean getKopiatu(Bezero b);
	
	@WebMethod
	public void kodeaIraungiDa(Bezero b);
	
	@WebMethod
	public boolean kopiatzaileaDa(Bezero a, Bezero b);

	@WebMethod
	public void utziKopiatzeari(Bezero b, Bezero kopiatua);
	
	@WebMethod
	public Bezero getKopiatzenDu(Bezero b);
}
