package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Bezero extends Pertsona implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String izena;
	private String abizena;
	private String NA;
	private Date jaiotzeData;
	private int telZenb;
	private String postaElek;
	private float dirua;
	@XmlIDREF
	private Bezero kopiatzenDu;
	private float portzentaia;
	private boolean kopiatu;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Mugimendua> mugimenduak=new Vector<Mugimendua>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Apustua> apustuak=new Vector<Apustua>();
	@OneToMany(fetch=FetchType.EAGER)
	private Vector<Bezero> kopiatzaileak=new Vector<Bezero>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Kodea> aurrekoKodeak = new Vector<Kodea>();
	private Kodea unekoKodea;
	
	
	
	public Bezero(String erabiltzailea, String pasahitza, String izena, String abizena, String nA, Date jaiotzeData,
			int telZenb, String postaElek) {
		super(erabiltzailea, pasahitza);
		this.izena = izena;
		this.abizena = abizena;
		NA = nA;
		this.jaiotzeData = jaiotzeData;
		this.telZenb = telZenb;
		this.postaElek = postaElek;
		this.dirua=0;
		this.kopiatzenDu=null;
		this.kopiatu=false;
		this.unekoKodea=null;
	}
	
	public Bezero(){
		super();
	}
	public String getIzena() {
		return izena;
	}
	public void setIzena(String izena) {
		this.izena = izena;
	}
	public String getAbizena() {
		return abizena;
	}
	public void setAbizena(String abizena) {
		this.abizena = abizena;
	}
	public String getNA() {
		return NA;
	}
	public void setNA(String nA) {
		NA = nA;
	}
	public Date getJaiotzeData() {
		return jaiotzeData;
	}
	public void setJaiotzeData(Date jaiotzeData) {
		this.jaiotzeData = jaiotzeData;
	}
	public int getTelZenb() {
		return telZenb;
	}
	public void setTelZenb(int telZenb) {
		this.telZenb = telZenb;
	}
	public String getPostaElek() {
		return postaElek;
	}
	public void setPostaElek(String postaElek) {
		this.postaElek = postaElek;
	}
	public float getDirua() {
		return dirua;
	}	
	public void setDirua(float dirua) {
		this.dirua = dirua;
	}
	
	public void addDirua(float add) {
		this.dirua=this.dirua+add;
	}
	
	public void restDirua(float rest) {
		this.dirua=this.dirua-rest;
	}
	public Vector<Mugimendua> getMugimenduak() {
		return mugimenduak;
	}
	
	public Vector<Mugimendua> getMugimenduak(Date data) {
		Vector<Mugimendua> mug = new Vector<Mugimendua>();
		for (Mugimendua m:mugimenduak) {
			if (!m.getData().before(data)) {
				mug.add(m);
			}
		}
		return mug;
	}
	
	public void setMugimenduak(Vector<Mugimendua> mugimenduak) {
		this.mugimenduak = mugimenduak;
	}
	
	public void addMugimendua(float dirua, String izenburua, boolean bankMovement) {
		Mugimendua m = new Mugimendua (dirua, izenburua, new Date(), bankMovement);
		this.mugimenduak.add(m);
	}
	public Vector<Apustua> getApustuak() {
		return apustuak;
	}
	public void setApustuak(Vector<Apustua> apustuak) {
		this.apustuak = apustuak;
	}
	public Apustua addApustua(float dirua, Vector<Pronostikoa> pronostikoak, Bezero jabea, float kuota) {
		Apustua a = new Apustua (dirua, pronostikoak, this, jabea, kuota);
		this.apustuak.add(a);
		return a;
	}
	
	public void removeApustua (Apustua a) {
		apustuak.remove(a);
	}
	
	public double etekinakLortu(Date data) {
		double etekina=0;
		for (Mugimendua m : mugimenduak) {
			if (!m.isBankMovement()&&!m.getData().before(data))
				etekina+=m.getDirua();
		}
		return etekina;
	}

	public void addKopiatzailea(Bezero kopiatzailea) {
		kopiatzaileak.add(kopiatzailea);
	}

	public void setKopiatzenDu(Bezero kopiatua, float portzentaia) {
		this.kopiatzenDu=kopiatua;
		this.portzentaia=portzentaia;
	}

	public boolean isKopiatu() {
		return kopiatu;
	}

	public void setKopiatu(boolean kopiatu) {
		this.kopiatu = kopiatu;
	}

	

	public Bezero getKopiatzenDu() {
		return kopiatzenDu;
	}

	public void setKopiatzenDu(Bezero kopiatzenDu) {
		this.kopiatzenDu = kopiatzenDu;
	}

	public double getPortzentaia() {
		return portzentaia;
	}

	public void setPortzentaia(float portzentaia) {
		this.portzentaia = portzentaia;
	}

	public Vector<Bezero> getKopiatzaileak() {
		return kopiatzaileak;
	}

	public void setKopiatzaileak(Vector<Bezero> kopiatzaileak) {
		this.kopiatzaileak = kopiatzaileak;
	}
	
	public void emptyKopiatzaileak () {
		this.kopiatzaileak.clear();
	}
	
	public void removeKopiatzailea (Bezero kopiatzailea) {
		this.kopiatzaileak.remove(kopiatzailea);
	}
	public Vector<Kodea> getAurrekoKodeak () {
		return this.aurrekoKodeak;
	}
	public void setAurrekoKodeak(Vector<Kodea> kodeak) {
		this.aurrekoKodeak = kodeak;
	}
	
	public void addAurrekoKodea(Kodea k) {
		this.aurrekoKodeak.add(k);
	}
	
	public Kodea addUnekoKodea() {
		this.unekoKodea = new Kodea(this);
		return this.unekoKodea;
	}
	
	public Kodea getUnekoKodea() {
		return this.unekoKodea;
	}
	
	public void setUnekoKodea (Kodea k) {
		this.unekoKodea = k;
	}
	
	public int erabilitakoKodeKop () {
		int kop = 0;
		for (Kodea k:aurrekoKodeak) {
			if (k.isUsed())
				kop++;
		}
		return kop;
	}
	
}
