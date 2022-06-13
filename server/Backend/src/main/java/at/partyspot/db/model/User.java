package at.partyspot.db.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "puser", uniqueConstraints = @UniqueConstraint(name = "UK_puser_name", columnNames = { "name" }))
public class User extends NamedBaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "userrole_id")
	private Userrole userrole;
	
	@ManyToOne
	@JoinColumn(name = "party_id")
	private Party party;
	
	@OneToMany(mappedBy = "user")
    Set<UserSong> userSong;
	
	public Userrole getUserrole() {
		return userrole;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public void setUserrole(Userrole userrole) {
		this.userrole = userrole;
	}

	public Set<UserSong> getUserSong() {
		return userSong;
	}

	public void setUserSong(Set<UserSong> userSong) {
		this.userSong = userSong;
	}

	
}
