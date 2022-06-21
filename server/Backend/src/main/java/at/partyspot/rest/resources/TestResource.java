package at.partyspot.rest.resources;

import java.util.UUID;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.taglibs.standard.util.UrlUtil;

import at.partyspot.db.access.PartyService;
import at.partyspot.db.access.PlaylistService;
import at.partyspot.db.access.SongService;
import at.partyspot.db.access.UserService;
import at.partyspot.db.access.VotingService;
import at.partyspot.db.model.Song;
import at.partyspot.db.model.User;

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
	@EJB
	UserService userService;
		   
	@Path("testfunction")
	@GET
	@Produces("text/plain")
	public Response getTestString() throws Exception {
		userService.connectUserWithSongs(UUID.fromString("00000000-0000-0000-0000-000000000013"), UUID.fromString("00000000-0000-0000-0000-000000000001"));
//		UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
//		Song song = new Song();
//		song.setId(UUID.randomUUID());
//		song.setName("SongFromBackend");
//		song.setSpotifyUri("BLABLA");
//		User user = userService.getUser("user14");
//		songService.addSong(song, UUID.fromString("00000000-0000-0000-0000-000000000000"), user);
		return Response.ok().entity("Hello World!").build();
	}
	
}
