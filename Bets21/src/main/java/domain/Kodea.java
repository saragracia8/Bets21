package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Kodea implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@XmlID
//	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	private String izena;
	private Date iraungipena;
	private boolean used;
	@XmlIDREF
	private Bezero bezeroa;
	private static ArrayList<String> izenDesberdinak = new ArrayList<String>();
	
	public Kodea(Bezero bezeroa) {
		super();
		String izena = generateIzena();
		while (inList(izena)) {
			izena=generateIzena();
		}
		this.izena=izena;
		izenDesberdinak.add(izena);
		Date data= new Date();
		this.iraungipena=egunakGehitu(data,10);
		this.bezeroa=bezeroa;
		used=false;
	}
	
	public Kodea () {
		super();
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public Date getIraungipena() {
		return iraungipena;
	}

	public void setIraungipena(Date iraungipena) {
		this.iraungipena = iraungipena;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public Bezero getBezeroa() {
		return bezeroa;
	}

	public void setBezeroa(Bezero bezeroa) {
		this.bezeroa = bezeroa;
	}

	public String generateIzena() {
		String izena = "";
	    Random random = new Random();
	    for (int i = 0; i < 4; i++) {
	    	izena += random.nextInt(10);
	    }
	    return izena;
	}
	
	public boolean inList(String izena) {
		boolean is=false;
		int i=0;
		while(i<izenDesberdinak.size()&&!is) {
			if (izenDesberdinak.get(i).equals(izena)) {
				is=true;
			}
			i++;
		}
		return is;
	}
	
	public Date egunakGehitu(Date data, int egunak) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_YEAR, egunak);
		return cal.getTime();
	}
	
	public String toString() {
		return izena;
	}
	
	
	
}
