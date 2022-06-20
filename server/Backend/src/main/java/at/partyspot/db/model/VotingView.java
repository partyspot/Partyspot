package at.partyspot.db.model;

import java.util.UUID;

public class VotingView {

	private UUID songId;
	private int voting;
	
	
	public UUID getSongId() {
		return songId;
	}
	public void setSongId(UUID songId) {
		this.songId = songId;
	}
	public int getVoting() {
		return voting;
	}
	public void setVoting(int voting) {
		this.voting = voting;
	}
	
	
}
