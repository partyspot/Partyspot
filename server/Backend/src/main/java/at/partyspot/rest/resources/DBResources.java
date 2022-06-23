package at.partyspot.rest.resources;

import java.util.UUID;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import at.partyspot.db.access.PartyService;
import at.partyspot.db.access.PlaylistService;
import at.partyspot.db.access.UserService;
import at.partyspot.db.model.Party;
import at.partyspot.db.model.Playlist;
import at.partyspot.db.model.User;
import at.partyspot.spotifyAPI.SpotifyAPI;

@Path("/db")
public class DBResources {
	
	@EJB
	UserService userService;
	
	@EJB
	PartyService partyService;
	
	@EJB
	PlaylistService playlistService;
	
	@EJB
	SpotifyAPI spotifyAPIService;

	@Path("getDefaultPlaylist")
	@GET
	@Consumes("text/plain")
	@Produces("application/json")
	public Response getDefaultPlaylist(@QueryParam("adminId") String adminId) throws Exception {
		User user = this.userService.getUser(UUID.fromString(adminId));
		Party party = user.getParty();
		UUID partyID = party.getId();
		String accessToken = party.getToken();
		Playlist playlist = playlistService.getPlaylistByPartyId(partyID);
		System.out.println(playlist);
		if (playlist == null || playlist.getId() == null) {
			playlist = spotifyAPIService.createDefaultPlaylist(accessToken, party);
		}
		return Response.ok().entity(playlist).build();

	}
	
	
	@Path("getSongPlaybackTime")
	@GET
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response getDefaultPlaylist(@QueryParam("spotifyUri") String spotifyUri, @QueryParam("userId") String userId) throws Exception {
		int duration = spotifyAPIService.getSongPlaybackTime(spotifyUri, userId);
		return Response.ok().entity(duration).build();

	}
}
