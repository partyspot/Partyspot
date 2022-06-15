package at.partyspot.spotifyAPI;

import java.util.Map;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import at.partyspot.db.access.PlaylistService;
import at.partyspot.db.model.Party;
import at.partyspot.rest.resources.Utilities;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

@Stateless
public class SpotifyAPI {
	
	@EJB
	PlaylistService playlistService;
	
	public at.partyspot.db.model.Playlist createDefaultPlaylist(String accessToken, Party party) throws Exception {
		SpotifyApi spotifyApi = new SpotifyApi.Builder()
			    .setAccessToken(accessToken).build();
		GetCurrentUsersProfileRequest userProfile = spotifyApi.getCurrentUsersProfile().build();
		Map<String, Object> userProfileObject = Utilities.jsonToMap(userProfile.getJson());
		Playlist spotifyPlaylist = spotifyApi.createPlaylist(userProfileObject.get("id").toString(), "DefaultPlaylist").public_(true).build().execute();
		at.partyspot.db.model.Playlist newPlaylist = new at.partyspot.db.model.Playlist();
		newPlaylist.setId(UUID.randomUUID());
		newPlaylist.setName("DefaultPlaylist");
		newPlaylist.setParty(party);
		newPlaylist.setPlaylistUri(spotifyPlaylist.getId());
		playlistService.createPlaylist(newPlaylist.getName(), newPlaylist.getParty().getId(), newPlaylist.getPlaylistUri());
		return newPlaylist;
	}
}