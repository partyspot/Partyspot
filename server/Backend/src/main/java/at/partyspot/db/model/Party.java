package at.partyspot.db.model;

import javax.persistence.Entity;

@Entity
public class Party extends NamedBaseEntity {
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
