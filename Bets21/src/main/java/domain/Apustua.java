package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Apustua implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@XmlID
	@GeneratedValue
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer betNumber;
	private float apustuDirua;
	private float kuota;
	//@XmlIDREF??
	private Vector<Pronostikoa> pronostikoak;
	@XmlIDREF
	private Bezero bezeroa;
	@XmlIDREF
	private Bezero jabea;

	public Apustua(float apustuDirua, Vector<Pronostikoa> pronostikoak, Bezero bezeroa, Bezero jabea, float kuota) {
		super();
		this.apustuDirua = apustuDirua;
		this.pronostikoak = pronostikoak;
		this.bezeroa = bezeroa;
		this.jabea=jabea;
		this.kuota=kuota;
	}
	
	public float getKuota() {
		return kuota;
	}

	public void setKuota(float kuota) {
		this.kuota = kuota;
	}

	public Bezero getJabea() {
		return jabea;
	}

	public void setJabea(Bezero jabea) {
		this.jabea = jabea;
	}

	public Apustua() {
		super(); 
	}

	public Integer getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}

	public float getApustuDirua() {
		return apustuDirua;
	}

	public void setApustuDirua(float apustuDirua) {
		this.apustuDirua = apustuDirua;
	}

	public Vector<Pronostikoa> getPronostikoak() {
		return pronostikoak;
	}

	public void setPronostikoak(Vector<Pronostikoa> pronostikoak) {
		this.pronostikoak = pronostikoak;
	}

	public Bezero getBezeroa() {
		return bezeroa;
	}

	public void setBezeroa(Bezero bezeroa) {
		this.bezeroa = bezeroa;
	}

}
