package at.partyspot.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/testpath")
public class TestResource {
	
	@Context
    private UriInfo context;
 
    public TestResource() {
    }
	
    
	@Path("testfunction")
	@GET
	@Produces("text/plain")
	public Response getTestString() {
		return Response.ok().entity("Hello World!").build();
	}
	
}
