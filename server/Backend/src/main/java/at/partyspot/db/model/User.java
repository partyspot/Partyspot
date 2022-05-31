package at.partyspot.db.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "puser", uniqueConstraints = @UniqueConstraint(name = "UK_puser_name", columnNames = { "name" }))
public class User extends NamedBaseEntity {

	@Column(columnDefinition = "BINARY(16)")
	private UUID partyId;
	
	@Column(columnDefinition = "BINARY(16)")
	private UUID userroleId;
	
	@OneToMany(mappedBy = "user")
    Set<UserSong> userSong;
}
