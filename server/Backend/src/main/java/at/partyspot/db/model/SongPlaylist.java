package at.partyspot.db.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SongPlaylist extends NamedBaseEntity {
	
	@ManyToOne
    @JoinColumn(name = "song_id")
    Song song;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    Playlist playlist;

    Date createdDate;

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
