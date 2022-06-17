package at.partyspot.db.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Playlist extends NamedBaseEntity {

	@OneToOne
	Party party;
	
	@OneToMany(mappedBy = "playlist")
    Set<SongPlaylist> songPlaylist;
	
	String playlistUri;
	
	public String getPlaylistUri() {
		return playlistUri;
	}

	public void setPlaylistUri(String playlistUri) {
		this.playlistUri = playlistUri;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
}
