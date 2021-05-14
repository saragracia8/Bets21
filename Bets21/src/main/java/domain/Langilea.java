package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Langilea extends Pertsona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Langilea (String erabiltzailea, String pasahitza) {
		super(erabiltzailea, pasahitza);
	}
	
	public Langilea() {
		super(); 
	}

	
}
