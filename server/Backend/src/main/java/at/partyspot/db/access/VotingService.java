package at.partyspot.db.access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.partyspot.db.model.Song;
import at.partyspot.db.model.VotingView;

@Stateless
public class VotingService {
	
	@EJB
	DatabaseService databaseService;
	@EJB
	SongService songService;

	public List<VotingView> getVoting(UUID partyId) throws Exception{
		List<VotingView> voting = new ArrayList<VotingView>();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getVotingView(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, partyId.toString());
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			VotingView view = new VotingView();
			UUID uuid = UUID.fromString(resultSet.getString("song_id"));
			Song song = songService.getSong(uuid);
			view.setSong(song);
			view.setVoting(resultSet.getInt("voting"));
			voting.add(view);
		}
		conn.close();
		return voting;
	}

}
