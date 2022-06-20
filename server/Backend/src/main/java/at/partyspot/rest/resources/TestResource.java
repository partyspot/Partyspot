package at.partyspot.rest.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import at.partyspot.db.access.PartyService;
import at.partyspot.db.access.PlaylistService;
import at.partyspot.db.access.SongService;
import at.partyspot.db.access.VotingService;

@Path("/testpath")
public class TestResource {
	
	@EJB
	PlaylistService playlistService;
	@EJB
	PartyService partyService;
	@EJB
	VotingService votingService;
	@EJB
	SongService songService;
		   
	@Path("testfunction")
	@GET
	@Produces("text/plain")
	public Response getTestString() throws Exception {
		//UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
		//Party obj1 = partyService.getPartyByCode("0000000001");
		//Song obj2 = songService.getSong(UUID.fromString("00000000-0000-0000-0000-000000000001"));
		//List<Song> songList = songService.getAll();
		//List<VotingView> list1 = votingService.getVoting(id);
		//partyService.updatePartyToken("tokenFromBackend", id);
		//Playlist playlist = playlistService.getPlaylistByPartyId(id);
		//Playlist playlist3 = playlistService.getPlaylist("playlist1");
		//Playlist playlsit2 = playlistService.createPlaylist("playlistFromBackend", id, "eineUri");
		//userService.createUser("hugo");
		//List<String> cols = databaseService.getColumns("puser");
		return Response.ok().entity("Hello World!").build();
	}
	
}
