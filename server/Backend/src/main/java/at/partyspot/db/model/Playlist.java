package at.partyspot.db.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Playlist extends NamedBaseEntity {

	@Column(columnDefinition = "BINARY(16)")
	private UUID partyId;
	
	@OneToMany(mappedBy = "course")
    Set<SongPlaylist> songPlaylist;
}
