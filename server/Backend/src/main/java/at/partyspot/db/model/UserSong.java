package at.partyspot.db.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserSong extends NamedBaseEntity {
	
	@ManyToOne
    @JoinColumn(name = "song_id")
    Song song;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    int voting;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getVoting() {
		return voting;
	}

	public void setVoting(int voting) {
		this.voting = voting;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

}
