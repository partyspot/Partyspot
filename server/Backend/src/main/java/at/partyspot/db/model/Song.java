package at.partyspot.db.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Song extends NamedBaseEntity {
	
	private String spotifyUri;
	private String genre;
	
	@OneToMany(mappedBy = "song")
    Set<SongPlaylist> songPlaylist;
	
	@OneToMany(mappedBy = "song")
    Set<UserSong> userSong;
	
	public String getSpotifyUri() {
		return spotifyUri;
	}
	public void setSpotifyUri(String spoifyUri) {
		this.spotifyUri = spoifyUri;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

}
