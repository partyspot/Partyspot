package at.partyspot.rest.resources;

import java.util.UUID;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import at.partyspot.Generators.CodeGenerator;
import at.partyspot.db.access.PartyService;
import at.partyspot.db.access.UserService;

// manages login according to userrole
@Path("/login")
public class LoginManagementResource {

	@EJB
	SpotifyAPITokenManager spotifyAPITokenManager;
	
	@EJB
	UserService userService;
	
	@EJB
	PartyService partyService;

	// retrieving the code from the redirectURI from frontend and calling function
	// createNew
	@Path("newPartyAndNewHost")
	@GET
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response createNew(@QueryParam("code") String code) throws Exception {
		String userCode = CodeGenerator.generateUserCode();
		UUID adminId = spotifyAPITokenManager.createPartyAndNewAdminUser(code, userCode);
		// Services create new AdminUser and Party
		return Response.ok().entity(userCode.concat(",").concat(adminId.toString())).build();

	}

	// one function for returning the redirectURI for the frontend to route the
	// adminuser to
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
	@Produces("text/plain")
	public Response createNewGuest(@QueryParam("partycode") String partycode, @QueryParam("username") String username) throws Exception {
		// Services create new User and assign to party with partycode
		//Party party = partyService.get;
		userService.createUser(username, UUID.fromString("00000000-0000-0000-0000-000000000002"), null);
		return Response.ok().entity("New user joined a party!").build();
	}

}
