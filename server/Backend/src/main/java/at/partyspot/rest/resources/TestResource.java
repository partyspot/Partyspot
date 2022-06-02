package at.partyspot.rest.resources;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import at.partyspot.db.access.DatabaseService;
import at.partyspot.db.access.UserService;
import at.partyspot.db.access.UserroleService;
import at.partyspot.db.model.User;
import at.partyspot.db.model.Userrole;

@Path("/testpath")
public class TestResource {
	
	UserService userService = new UserService();
	DatabaseService databaseService = new DatabaseService();
	UserroleService userroleService = new UserroleService();
		
	@Context
    private UriInfo context;
 
    public TestResource() {
    }
	
    
	@Path("testfunction")
	@GET
	@Produces("text/plain")
	public Response getTestString() throws Exception {
		UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
		Userrole userrole = userroleService.getUserrole(id);
		List<Userrole> userroles = userroleService.getAll();
		//List<String> cols = databaseService.getColumns("puser");
		return Response.ok().entity("Hello World!").build();
	}
	
}
