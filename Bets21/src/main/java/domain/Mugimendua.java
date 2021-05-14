package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Mugimendua implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer mugimenduaNumber;
	private float dirua;
	private String izenburua;
	private Date data;
	private boolean bankMovement;

	public Mugimendua(float dirua, String izenburua, Date data, boolean bankMovement) {
		super();
		this.dirua = dirua;
		this.izenburua = izenburua;
		this.data = data;
		this.bankMovement=bankMovement;
	}
	
	public Mugimendua() {
		super(); 
	}

	public Integer getMugimenduaNumber() {
		return mugimenduaNumber;
	}

	public void setMugimenduaNumber(Integer mugimenduaNumber) {
		this.mugimenduaNumber = mugimenduaNumber;
	}

	public float getDirua() {
		return dirua;
	}

	public void setDirua(float dirua) {
		this.dirua = dirua;
	}

	public String getIzenburua() {
		return izenburua;
	}

	public void setIzenburua(String izenburua) {
		this.izenburua = izenburua;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isBankMovement() {
		return bankMovement;
	}

	public void setBankMovement(boolean bankMovement) {
		this.bankMovement = bankMovement;
	}

	public String toString() {
		return izenburua + ": " + dirua + " " + data;
	}

}
