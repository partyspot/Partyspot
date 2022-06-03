package at.partyspot.db.model;

import javax.persistence.Entity;

@Entity
public class Party extends NamedBaseEntity {
	
	private String code;
	private String token;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
