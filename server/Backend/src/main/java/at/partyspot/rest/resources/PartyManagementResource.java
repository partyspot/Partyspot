package at.partyspot.rest.resources;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import at.partyspot.db.access.PartyService;
import at.partyspot.db.model.Party;
import at.partyspot.db.model.Song;
import at.partyspot.spotifyAPI.SpotifyAPI;
import se.michaelthelin.spotify.model_objects.specification.Track;

// handles user requests
@Path("/party")
public class PartyManagementResource {
	
	@EJB
	SpotifyAPI spotifyAPIService;
	
	@EJB
	PartyService partyService;
	
	
	@Path("searchSongs")
	@GET
	@Consumes("text/plain")
	@Produces("application/json")
	public Response getSongSearchResults(@QueryParam("searchString") String searchString, @QueryParam("userId") String userID) throws Exception {
		ArrayList<Song> searchResult = spotifyAPIService.getSearchResults(searchString, userID);
		return Response.ok().entity(searchResult).build();

	}
	
	
	@Path("addSong")
	@GET
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addSong(@QueryParam("spotifyURI") String spotifyURI, @QueryParam("userId") String userID) throws Exception {
		Track spotifySong = spotifyAPIService.getSong(spotifyURI, userID);
		//partyService. Prozeduren ausführen
		return Response.ok().entity(spotifySong).build();

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
