package at.partyspot.rest.resources;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.hc.core5.http.ParseException;

import at.partyspot.db.access.PartyService;
import at.partyspot.db.access.UserService;
import at.partyspot.db.model.Party;
import at.partyspot.db.model.User;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

// handles requests for the Token (needed for SpotifyAPI requests)
// either returns an existing saved token or requests a new token using the user credentials for the token-code
@Stateless
public class SpotifyAPITokenManager {

	@EJB
	PartyService partyService;

	@EJB
	UserService userService;

	private static final String clientId = "d303cca7130840ecb1fa1d1268e2feea";
	private static final String clientSecret = "6de17e8306974b32b09f7f194a63f8cb";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:4200/overview");

	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(clientId)
			.setClientSecret(clientSecret).setRedirectUri(redirectUri).build();
	private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//	          .state("x4xkmn9pu3j6ukrs8n")
	          .scope("playlist-modify-private,playlist-modify-public")
//	          .show_dialog(true)
			.build();

	public String authorizationCodeUri_Sync() {
		final URI uri = authorizationCodeUriRequest.execute();

		return uri.toString();
	}

	public String authorizationCode_Sync(String code) {
		try {
			final AuthorizationCodeCredentials authorizationCodeCredentials = spotifyApi.authorizationCode(code).build()
					.execute();

			// Set access and refresh token for further "spotifyApi" object usage
			spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
			spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

			System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
			return authorizationCodeCredentials.getAccessToken();
		} catch (IOException | SpotifyWebApiException | ParseException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return "AuthorizationToken could not be retrieved";
	}

	public UUID createPartyAndNewAdminUser(String code, String userCode) throws Exception {
		// using extracted code from URI the frontend was routed to
		String accessToken = authorizationCode_Sync(code);
		System.out.println(accessToken);
		// create party with accessToken
		Party newParty = partyService.createParty("Partyname", userCode, accessToken);
		User newAdminUser = userService.createUser("Admin", UUID.fromString("00000000-0000-0000-0000-000000000001"),
				newParty.getId());
		return newAdminUser.getId();
	}

}
