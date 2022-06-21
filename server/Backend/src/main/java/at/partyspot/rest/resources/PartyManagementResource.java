package at.partyspot.rest.resources;

import java.util.ArrayList;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.partyspot.db.access.PartyService;
import at.partyspot.db.access.PlaylistService;
import at.partyspot.db.access.SongService;
import at.partyspot.db.access.UserService;
import at.partyspot.db.model.Party;
import at.partyspot.db.model.Playlist;
import at.partyspot.db.model.Song;
import at.partyspot.db.model.User;
import at.partyspot.spotifyAPI.SpotifyAPI;

// handles user requests
@Path("/party")
public class PartyManagementResource {
	
	@EJB
	SpotifyAPI spotifyAPIService;
	
	@EJB
	SongService songService;
	
	@EJB
	PartyService partyService;
	
	@EJB
	UserService userService;
	
	@EJB
	PlaylistService playlistService;
	
	
	@Path("searchSongs")
	@GET
	@Consumes("text/plain")
	@Produces("application/json")
	public Response getSongSearchResults(@QueryParam("searchString") String searchString, @QueryParam("userId") String userID) throws Exception {
		ArrayList<Song> searchResult = spotifyAPIService.getSearchResults(searchString, userID);
		return Response.ok().entity(searchResult).build();

	}
	
	
	@Path("addSong")
	@POST
	@Consumes("application/json")
	public Response addSong(@QueryParam("userId") String userID, String songJson) throws Exception {
		Song song = new ObjectMapper().readValue(songJson, Song.class);
		User user = userService.getUser(UUID.fromString(userID));
		Playlist playlist = playlistService.getPlaylistByPartyId(user.getParty().getId());
		songService.addSong(song, playlist.getId(), user);
		return Response.ok().build();

	}
	
	
	@Path("getPartyToken")
	@GET
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response getTokenWithPartyCode(@QueryParam("partycode") String partycode) throws Exception {
		Party party = partyService.getPartyByCode(partycode);
		return Response.ok().entity(party.getToken()).build();

	}

}
