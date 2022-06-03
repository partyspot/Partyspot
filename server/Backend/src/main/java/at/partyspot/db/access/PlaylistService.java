package at.partyspot.db.access;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.partyspot.db.model.Playlist;


public class PlaylistService {

	public List<Playlist> getAll() throws Exception {
		List<Playlist> playlists = new ArrayList<Playlist>();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getAll("playlist");
		while (resultSet.next()) {
			Playlist playlist = new Playlist();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			playlist.setId(uuid);
			playlist.setName(resultSet.getString("name"));
			playlists.add(playlist);
		}
		return playlists;
	}

	public Playlist getPlaylist(String name) throws Exception {
		Playlist playlist = new Playlist();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getByName("playlist", name);
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			playlist.setId(uuid);
			playlist.setName(resultSet.getString("name"));
		}
		return playlist;
	}

	public Playlist getPlaylist(UUID id) throws Exception {
		Playlist playlist = new Playlist();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getById("playlist", id);
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			playlist.setId(uuid);
			playlist.setName(resultSet.getString("name"));
		}
		return playlist;
	}

}
