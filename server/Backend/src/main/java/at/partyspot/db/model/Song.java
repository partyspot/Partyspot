package at.partyspot.db.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Song extends NamedBaseEntity {
	
	private String spoifyUri;
	private String genre;
	
	@OneToMany(mappedBy = "song")
    Set<SongPlaylist> songPlaylist;
	
	@OneToMany(mappedBy = "song")
    Set<UserSong> userSong;
	
	public String getSpoifyUri() {
		return spoifyUri;
	}
	public void setSpoifyUri(String spoifyUri) {
		this.spoifyUri = spoifyUri;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

}
