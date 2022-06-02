package at.partyspot.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import at.partyspot.Generators.CodeGenerator;

// manages login according to userrole
@Path("/login")
public class LoginManagementResource {
	
	
	@Path("newHost")
	@POST
	@Consumes("text/plain")
	public Response createNew(@QueryParam("credentials") String credentials) throws Exception {
		if (SpotifyAPITokenManager.checkCredentials(credentials)) {
			// Services create new AdminUser and Party
			String userCode = CodeGenerator.generateUserCode();
			return Response.ok().entity(userCode).build();
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
