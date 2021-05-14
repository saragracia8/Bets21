package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Pronostikoa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue @XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer pronostikoaNumber;
	private String izenburua;
	private float kuota;
	@XmlIDREF
	private Question q;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Apustua> apustuak=new Vector<Apustua>();
	
	public Pronostikoa (String pronostikoa, float kuota, Question qi) {
		this.izenburua = pronostikoa;
		this.kuota = kuota;
		this.q = qi;
	}
	
	public Pronostikoa() {
		super(); 
	}

	public Integer getPronostikoaNumber() {
		return pronostikoaNumber;
	}


	public void setPronostikoaNumber(Integer pronostikoaNumber) {
		this.pronostikoaNumber = pronostikoaNumber;
	}


	public String getIzenburua() {
		return izenburua;
	}

	public void setIzenburua(String izenburua) {
		this.izenburua = izenburua;
	}

	public float getKuota() {
		return kuota;
	}

	public void setKuota(float kuota) {
		this.kuota = kuota;
	}

	public Question getQ() {
		return q;
	}
	

	public void setQ(Question q) {
		this.q = q;
	}
	
	public Vector<Apustua> getApustuak() {
		return apustuak;
	}

	public void setApustuak(Vector<Apustua> apustuak) {
		this.apustuak = apustuak;
	}
	
	public void addApustua(Apustua a) {
		this.apustuak.add(a);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pronostikoa other = (Pronostikoa) obj;
		if (izenburua != other.izenburua)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return izenburua+", kuota= "+kuota;
	}
	
}
