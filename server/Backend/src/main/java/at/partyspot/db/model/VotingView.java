package at.partyspot.db.model;

import java.util.UUID;

public class VotingView {

	private Song song;
	private int voting;
	
	
	
	public Song getSong() {
		return song;
	}
	public void setSong(Song song) {
		this.song = song;
	}
	public int getVoting() {
		return voting;
	}
	public void setVoting(int voting) {
		this.voting = voting;
	}
	
	
}
