package at.partyspot.db.access;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.partyspot.db.model.Party;
import at.partyspot.db.model.Playlist;
import at.partyspot.db.model.User;

@Stateless
public class PlaylistService {
	
	@EJB
	DatabaseService databaseService;
	@EJB
	PartyService partyService;

	public List<Playlist> getAll() throws Exception {
		List<Playlist> playlists = new ArrayList<Playlist>();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getAllPlaylists()}";
		CallableStatement statement = conn.prepareCall(query);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Playlist playlist = new Playlist();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			playlist.setId(uuid);
			playlist.setName(resultSet.getString("name"));
			UUID partyId = UUID.fromString(resultSet.getString("party_id"));
			Party party = partyService.getParty(partyId);
			playlist.setParty(party);
			playlists.add(playlist);
		}
		conn.close();
		return playlists;
	}

	public Playlist getPlaylist(String name) throws Exception {
		Playlist playlist = new Playlist();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getPlaylistByName(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			playlist.setId(uuid);
			playlist.setName(resultSet.getString("name"));
			UUID partyId = UUID.fromString(resultSet.getString("party_id"));
			Party party = partyService.getParty(partyId);
			playlist.setParty(party);
		}
		conn.close();
		return playlist;
	}

	public Playlist getPlaylistByPartyId(UUID id) throws Exception {
		Playlist playlist = new Playlist();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getPlaylistByPartyId(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, id.toString());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			playlist.setId(uuid);
			playlist.setName(resultSet.getString("name"));
			UUID partyId = UUID.fromString(resultSet.getString("party_id"));
			Party party = partyService.getParty(partyId);
			playlist.setParty(party);
		}
		conn.close();
		return playlist;
	}
	
	public Playlist createPlaylist(String name, UUID partyId) throws Exception {
		Playlist playlist = new Playlist();
		UUID id = UUID.randomUUID();
		playlist.setId(id);
		playlist.setName(name);
		
		Connection conn = databaseService.createConnection();
		String query = "{CALL createPlaylist(?, ?, ?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, playlist.getId().toString());
		statement.setString(2, name);
		statement.setString(3, partyId.toString());
		statement.executeQuery();
		statement.close();
		conn.close();
		return playlist;
	}

}
