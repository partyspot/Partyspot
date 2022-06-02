package at.partyspot.db.access;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.partyspot.db.model.Song;


public class SongService {

	public List<Song> getAll() throws Exception {
		List<Song> songs = new ArrayList<Song>();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getAll("song");
		while (resultSet.next()) {
			Song song = new Song();
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			song.setId(uuid);
			song.setName(resultSet.getString("name"));
			song.setSpotifyUri(resultSet.getString("spotify_uri"));
			song.setGenre(resultSet.getString("genre"));
			songs.add(song);
		}
		return songs;
	}

	public Song getSong(String name) throws Exception {
		Song song = new Song();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getByName("song", name);
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			song.setId(uuid);
			song.setName(resultSet.getString("name"));
			song.setSpotifyUri(resultSet.getString("spotify_uri"));
			song.setGenre(resultSet.getString("genre"));
		}
		return song;
	}

	public Song getSong(UUID id) throws Exception {
		Song song = new Song();
		DatabaseService databaseService = new DatabaseService();
		ResultSet resultSet = databaseService.getById("song", id);
		if (resultSet.next()) {
			UUID uuid = UUID.fromString(resultSet.getString("id"));
			song.setId(uuid);
			song.setName(resultSet.getString("name"));
			song.setSpotifyUri(resultSet.getString("spotify_uri"));
			song.setGenre(resultSet.getString("genre"));
		}
		return song;
	}

}
