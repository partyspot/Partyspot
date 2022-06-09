package at.partyspot.rest.resources;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import at.partyspot.db.access.DatabaseService;
import at.partyspot.db.access.PartyService;
import at.partyspot.db.access.PlaylistService;
import at.partyspot.db.access.SongService;
import at.partyspot.db.access.UserService;
import at.partyspot.db.access.UserroleService;
import at.partyspot.db.model.Party;
import at.partyspot.db.model.Playlist;
import at.partyspot.db.model.Song;
import at.partyspot.db.model.User;
import at.partyspot.db.model.Userrole;

@Path("/testpath")
public class TestResource {
	
	@EJB
	UserService userService;
		   
	@Path("testfunction")
	@GET
	@Produces("text/plain")
	public Response getTestString() throws Exception {
		UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
		User obj1 = userService.getUser("testuser");
		User obj2 = userService.getUser(UUID.fromString("5f4a2ebd-e0c2-11ec-abb5-00090ffe0001"));
		List<User> list1 = userService.getAll();
		//partyService.createParty("einName", null, "einToken");
		//List<String> cols = databaseService.getColumns("puser");
		return Response.ok().entity("Hello World!").build();
	}
	
}
