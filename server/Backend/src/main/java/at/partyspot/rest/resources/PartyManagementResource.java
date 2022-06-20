package at.partyspot.rest.resources;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import at.partyspot.db.model.Song;
import at.partyspot.spotifyAPI.SpotifyAPI;

// handles user requests
@Path("/party")
public class PartyManagementResource {
	
	@EJB
	SpotifyAPI spotifyAPIService;
	
	
	@Path("searchSongs")
	@GET
	@Consumes("text/plain")
	@Produces("application/json")
	public Response getDefaultPlaylist(@QueryParam("searchString") String searchString, @QueryParam("userId") String userID) throws Exception {
		ArrayList<Song> searchResult = spotifyAPIService.getSearchResults(searchString, userID);
		return Response.ok().entity(searchResult).build();

	}

}
