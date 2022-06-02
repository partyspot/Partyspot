package at.partyspot.rest.resources;

import java.net.URI;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

// handles requests for the Token (needed for SpotifyAPI requests)
// either returns an existing saved token or requests a new token using the user credentials for the token-code
public class SpotifyAPITokenManager {
	
	private static final String clientId = "d303cca7130840ecb1fa1d1268e2feea";
	  private static final String clientSecret = "6de17e8306974b32b09f7f194a63f8cb";
	  private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:4200/overview");

	  private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
	    .setClientId(clientId)
	    .setClientSecret(clientSecret)
	    .setRedirectUri(redirectUri)
	    .build();
	  private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//	          .state("x4xkmn9pu3j6ukrs8n")
//	          .scope("user-read-birthdate,user-read-email")
//	          .show_dialog(true)
	    .build();

	  public static String authorizationCodeUri_Sync() {
	    final URI uri = authorizationCodeUriRequest.execute();

	    return "URI: " + uri.toString();
	  }
	
	public static boolean checkCredentials(String username, String pw) {
		// probably extract code for token from URI
		//String tokenCode = authorizationCodeUri_Sync();
		// save token before returning
		return true;
	}

}
