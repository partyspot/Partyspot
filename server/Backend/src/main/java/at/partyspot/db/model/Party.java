package at.partyspot.db.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Party extends NamedBaseEntity {
	
	private String code;
	private String token;
	
	@OneToMany(mappedBy = "party")
	private List<User> users;
	
	@OneToOne(mappedBy = "party")
	private Playlist playlist;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

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
