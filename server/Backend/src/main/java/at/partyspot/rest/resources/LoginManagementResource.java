package at.partyspot.rest.resources;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import at.partyspot.Generators.CodeGenerator;

// manages login according to userrole
@Path("/login")
public class LoginManagementResource {
	
	@EJB
	SpotifyAPITokenManager spotifyAPITokenManager;
	
	// retrieving the code from the redirectURI from frontend and calling function createNew
	@Path("newPartyAndNewHost")
	@GET
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response createNew(@QueryParam("code") String code) throws Exception {
		if (spotifyAPITokenManager.createPartyAndNewAdminUser(code)) {
			// Services create new AdminUser and Party
			String userCode = CodeGenerator.generateUserCode();
			return Response.ok().entity(userCode).build();
		} else {
			return Response.serverError().build();
		}
		
	}
	
	// one function for returning the redirectURI for the frontend to route the adminuser to
	@Path("loginWithSpotify")
	@GET
	@Produces("text/plain")
	public Response getSpotifyLoginURI() throws Exception {
		String redirectURI = spotifyAPITokenManager.authorizationCodeUri_Sync();
		
		return Response.ok().entity(redirectURI).build();
	}
	
	
	@Path("newGuest")
	@POST
	@Consumes("text/plain")
	public Response createNewGuest(@QueryParam("partycode") String partycode) throws Exception {
			// Services create new User and assign to party with partycode
			return Response.ok().entity("New user joined a party!").build();
	}

}
