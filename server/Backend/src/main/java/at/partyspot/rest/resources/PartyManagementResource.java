package at.partyspot.rest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.partyspot.db.access.PartyService;
import at.partyspot.db.access.PlaylistService;
import at.partyspot.db.access.SongService;
import at.partyspot.db.access.UserService;
import at.partyspot.db.access.VotingService;
import at.partyspot.db.model.Party;
import at.partyspot.db.model.Playlist;
import at.partyspot.db.model.Song;
import at.partyspot.db.model.User;
import at.partyspot.db.model.VotingView;
import at.partyspot.spotifyAPI.SpotifyAPI;

// handles user requests
@Path("/party")
public class PartyManagementResource {
	
	@EJB
	SpotifyAPI spotifyAPIService;
	
	@EJB
	SongService songService;
	
	@EJB
	PartyService partyService;
	
	@EJB
	UserService userService;
	
	@EJB
	PlaylistService playlistService;
	
	@EJB
	VotingService votingService;
	
	
	@Path("searchSongs")
	@GET
	@Consumes("text/plain")
	@Produces("application/json")
	public Response getSongSearchResults(@QueryParam("searchString") String searchString, @QueryParam("userId") String userID) throws Exception {
		ArrayList<Song> searchResult = spotifyAPIService.getSearchResults(searchString, userID);
		return Response.ok().entity(searchResult).build();

	}
	
	
	@Path("addSong")
	@POST
	@Consumes("application/json")
	public Response addSong(@QueryParam("userId") String userID, String songJson) throws Exception {
		Song song = new ObjectMapper().readValue(songJson, Song.class);
		User user = userService.getUser(UUID.fromString(userID));
		Playlist playlist = playlistService.getPlaylistByPartyId(user.getParty().getId());
		if(!songService.isSongInPlaylist(song.getSpotifyUri(), playlist.getId())) {
			songService.addSong(song, playlist.getId(), user);
		} else {
			Song storedSong = songService.getSongByUri(song.getSpotifyUri(), playlist.getId());
			votingService.updateVoting(storedSong.getId(), user.getId(), 1);
		}
		
		return Response.ok().build();

	}
	
	
	@Path("getPartyToken")
	@GET
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response getTokenWithPartyCode(@QueryParam("partycode") String partycode) throws Exception {
		Party party = partyService.getPartyByCode(partycode);
		return Response.ok().entity(party.getToken()).build();

	}
	
	
	@Path("getVotingView")
	@GET
	@Consumes("text/plain")
	@Produces("application/json")
	public Response getVotingView(@QueryParam("userId") String userId) throws Exception {
		User user = userService.getUser(UUID.fromString(userId));
		List<VotingView> votingView = votingService.getVoting(user.getParty().getId());
		return Response.ok().entity(votingView).build();

	}
	
	
	@Path("updateVotingView")
	@POST
	@Consumes("text/plain")
	public Response updateVotingView(@QueryParam("songId") String songId, @QueryParam("userId") String userId, @QueryParam("voteSetting") String voteSetting) throws Exception {
		votingService.updateVoting(UUID.fromString(songId), UUID.fromString(userId), Integer.parseInt(voteSetting));
		return Response.ok().build();

	}
	
	@Path("getInviteCodeForUser")
	@GET
	@Consumes("text/plain")
	@Produces("application/json")
	public Response getInviteCodeForUser(@QueryParam("userId") String userId) throws Exception {
		User user = userService.getUser(UUID.fromString(userId));
		String partycode = user.getParty().getCode();
		return Response.ok().entity(partycode).build();

	}
	
	
	@Path("deleteSong")
	@GET
	@Consumes("text/plain")
	public Response deleteSong(@QueryParam("songId") String songId) throws Exception {
		songService.deleteSong(UUID.fromString(songId));
		return Response.ok().build();

	}
	

}
