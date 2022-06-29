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
import at.partyspot.db.model.User;

@Stateless
public class SongService {
	
	@EJB
	DatabaseService databaseService;

	public List<Song> getAll() throws Exception {
		List<Song> songs = new ArrayList<Song>();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getAllSongs()}";
		CallableStatement statement = conn.prepareCall(query);
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			Song song = new Song();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			song.setId(uuid);
			song.setName(resultSet.getString("name"));
			song.setSpotifyUri(resultSet.getString("spotify_uri"));
			song.setGenre(resultSet.getString("genre"));
			songs.add(song);
		}
		conn.close();
		return songs;
	}

	public Song getSong(String name) throws Exception {
		Song song = new Song();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getSongByName(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, name);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			song.setId(uuid);
			song.setName(resultSet.getString("name"));
			song.setSpotifyUri(resultSet.getString("spotify_uri"));
			song.setGenre(resultSet.getString("genre"));
		}
		conn.close();
		return song;
	}

	public Song getSong(UUID id) throws Exception {
		Song song = new Song();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getSongById(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, id.toString());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			song.setId(uuid);
			song.setName(resultSet.getString("name"));
			song.setSpotifyUri(resultSet.getString("spotify_uri"));
			song.setGenre(resultSet.getString("genre"));
		}
		conn.close();
		return song;
	}
	
	public void addSong(Song song, UUID playlistId, User user) throws Exception {
		Connection conn = databaseService.createConnection();
		String query = "{CALL addSong(?, ?, ?, ?, ?, ?, ?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, song.getId().toString());
		statement.setString(2, song.getName());
		statement.setString(3, song.getSpotifyUri());
		statement.setString(4, "");
		statement.setString(5, playlistId.toString());
		statement.setString(6, user.getId().toString());
		statement.setString(7, user.getParty().getId().toString());
		statement.executeQuery();
		conn.close();		
	}
	
	public void deleteSong(UUID songId) throws Exception {
		Connection conn = databaseService.createConnection();
		String query = "{CALL deleteSong(?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, songId.toString());
		statement.executeQuery();
		conn.close();	
	}
	
	public boolean isSongInPlaylist(String uri, UUID playlistId) throws Exception {
		boolean isAdded = false;
		Connection conn = databaseService.createConnection();
		String query = "{CALL getSongBySpotifyUri(?, ?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, uri);
		statement.setString(2, playlistId.toString());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next() == true) {
			isAdded = true;
		}
		conn.close();
		return isAdded;
	}
	
	public Song getSongByUri(String uri, UUID playlistId) throws Exception {
		Song song = new Song();
		Connection conn = databaseService.createConnection();
		String query = "{CALL getSongBySpotifyUri(?, ?)}";
		CallableStatement statement = conn.prepareCall(query);
		statement.setString(1, uri);
		statement.setString(2, playlistId.toString());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			song.setId(uuid);
			song.setName(resultSet.getString("name"));
			song.setSpotifyUri(resultSet.getString("spotify_uri"));
			song.setGenre(resultSet.getString("genre"));
		}
		conn.close();
		return song;
	}

}
