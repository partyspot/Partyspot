package at.partyspot.rest.resources;

import java.sql.ResultSet;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import at.partyspot.db.access.DatabaseService;

@Path("/testpath")
public class TestResource {
	
	DatabaseService databaseService = new DatabaseService();
		
	@Context
    private UriInfo context;
 
    public TestResource() {
    }
	
    
	@Path("testfunction")
	@GET
	@Produces("text/plain")
	public Response getTestString() throws Exception {
		UUID id = UUID.fromString("00000000-0000-0000-0000-000000000001");
		ResultSet res = databaseService.getAll("userrole");
		ResultSet res2 = databaseService.getById("userrole", id);
		ResultSet res3 = databaseService.getByName("userrole", "Testrolle");
		System.out.println(res);
		System.out.println(res2.getRow());
		return Response.ok().entity("Hello World!").build();
	}
	
}
