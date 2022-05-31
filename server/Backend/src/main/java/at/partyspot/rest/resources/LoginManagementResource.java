package at.partyspot.rest.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

// manages login according to userrole
@Path("/login")
public class LoginManagementResource {
	
	@Inject
	SpotifyAPITokenManager spotifyAPITokenManager;
	
	
	@Path("newHost")
	@POST
	@Consumes("text/plain")
	public Response createNew(@QueryParam("credentials") String credentials, @QueryParam("partycode") String partycode) throws Exception {
		if (spotifyAPITokenManager.checkCredentials(credentials)) {
			// Services create new AdminUser and Party, note that Partycode has to be defined prior by adminuser
			return Response.ok().entity("New host started a party!").build();
		} else {
			return Response.serverError().build();
		}
		
	}
	
	
	@Path("newUser")
	@POST
	@Consumes("text/plain")
	public Response createNewUser(@QueryParam("partycode") String partycode) throws Exception {
			// Services create new User and assign to party with partycode
			return Response.ok().entity("New user joined a party!").build();
	}

}
