///  <reference types="@types/spotify-web-playback-sdk"/>
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { ModalPage } from 'src/app/modals/modal/modal.page';
import { GuestModalPage } from 'src/app/modals/guest-modal/guest-modal.page';
import { GuestViewModalPage } from 'src/app/modals/guest-view-modal/guest-view-modal.page';
import { Router } from '@angular/router';
import { RestService } from 'src/app/rest/restService/restService';
import { StateService } from '../../services/stateService';
import { UUID } from 'angular2-uuid';
import { toUUID } from 'to-uuid';
import { AlertService } from 'ngx-alerts';
import { Song } from 'src/app/rest/DTOModels/Song';
import { Track } from 'src/app/rest/DTOModels/track';
import { VotingView } from 'src/app/rest/DTOModels/votingview';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss'],
})
export class OverviewComponent implements OnInit {

  isAdmin: boolean = false;
  inviteCode: string;
  spotifyCode: string;
  partyName = "";
  private currentSessionId: UUID;
  currentPlaylist;
  currentTrackURI: string;
  shownPlaylist: Track[] = [];
  player: Spotify.Player;
  model: any;
  searchResults: any;
  restDefaultPlaylist;
  rows: VotingView[];
  iframeSrc: SafeUrl;
  songUrlBasis1: string;
  songUrlBasis2: string;
  songUrl: string;
  voteSetting: number;
  needsPlaylistViewUpdateToken: string;
  timerSubscription: Subscription;
  duration: number;
  currentSong: Song;

  @ViewChild('songSearch')
  songSearch: ElementRef;

  // in the constructor we need to inject different services, the Modal Controller and Router
  constructor(public modalCtrl: ModalController, private router: Router, private restService: RestService,
    private stateService: StateService, private alertService: AlertService, private sanitizer: DomSanitizer) {
    // here we construct the spotify-embed URL for the player; the two Basis are constant wheras the id is meant to be exchanged
    this.songUrlBasis1 = 'https://open.spotify.com/embed/track/';
    this.songUrlBasis2 = '?utm_source=generator&theme=0&autoplay=1&muted=0&autopause=0';
    // we set a default song for initializing the page
    let id = '3jBLKVqnOcxeXaqdGZ0p45';
    this.songUrl = this.songUrlBasis1 + id + this.songUrlBasis2;
    this.iframeSrc = this.sanitizer.bypassSecurityTrustResourceUrl(this.songUrl);
    //this.iframeSrc = this.sanitizer.bypassSecurityTrustResourceUrl('');
    // this is an attempt at controlling the player to enable autoplay
    window.addEventListener("blur", () => {
      setTimeout(() => {
        if (document.activeElement.tagName === "IFRAME") {
          //document.activeElement.;
        }
      });
    }, { once: false });
  }

  async ngOnInit() {
    // the function that controlls, what is happening after login on the page
    await this.onPageLoad();
    // every second it is checked whether an internal updateToken has changed.
    // If it is changed in the localStorage on certain events the component will register it and update the shown playlist
    setInterval(() => {
      if (this.needsPlaylistViewUpdateToken !== localStorage.getItem(this.inviteCode)) {
        this.needsPlaylistViewUpdateToken = localStorage.getItem(this.inviteCode);
        this.getVotingView();
      }
    }, 1000);
    this.duration = 0;
    // the following is connected to the attempt of enabling autoplay of the player
    await this.waitForSpotifyWebPlaybackSDKToLoad();
    let token;
    await this.restService.getTokenWithPartyCode(this.inviteCode).then(res => {
      token = res;
    });
    const sdk = new window.Spotify.Player({
      name: "Web Playback SDK",
      volume: 1.0,
      getOAuthToken: callback => { callback(token); }
    });
    sdk.on("player_state_changed", state => {
      // Update UI with playback state changes
    });
    let connected = await sdk.connect();
    if (connected) {
      await sdk.resume();
      await sdk.setVolume(0.5);
    }
  }

  async showModal() {
    const modal = await this.modalCtrl.create({
      component: ModalPage
    });
    return await modal.present();
  }

  async showGuestModal() {
    const modal = await this.modalCtrl.create({
      component: GuestModalPage
    });
    return await modal.present();
  }

  async showGuestViewModal() {
    const modal = await this.modalCtrl.create({
      component: GuestViewModalPage
    });
    return await modal.present();
  }

  logout(): void {
    this.stateService.removeAdminId(this.currentSessionId);
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }

  async onPageLoad() {
    // as after login we are staying in the same session we can use the sessionStorage to determine whether an admin or guest has logged in
    if (sessionStorage.getItem("isAdmin") === "n") {
      // as a guest we want to set isAdmin to false and get the inviteCode of the party they have logged in, which is used later on
      // we also refresh the shown playlist for the guest after login
      this.isAdmin = false;
      await this.restService.getInviteCodeForUser(sessionStorage.getItem("currentUser")).then(res => {
        this.inviteCode = res;
        this.getVotingView();
        this.needsPlaylistViewUpdateToken = localStorage.getItem(this.inviteCode);
        return;
      });
    }
    if (sessionStorage.getItem("isAdmin") !== "n") {
      // in an admin login we have to check the location parameter of the browser window, later needed for retrieving the spotify code
      if (window.location.search.length > 0 && sessionStorage.getItem("isAdmin") === "y") {
        await this.handleRedirect();
      } else {
        this.alertService.danger('Bitte loggen Sie sich ein!');
        this.router.navigate(['/login']);
      }
    }
  }

  // this function is meant for admin login specific, we get the spotify code from the query parameter of the spotify redirect uri
  // and add the adminId connected to the current session to the stateService, to keep track of all logged in hosts.
  async handleRedirect() {
    this.spotifyCode = this.getCode();
    this.isAdmin = true;
    await this.restService.createNewPartyWithNewCodeAndHostAndGetGuestCode(this.spotifyCode).then(async res => {
      this.currentSessionId = UUID.UUID();
      const results = res.split(",");
      this.inviteCode = results[0];
      const adminId = results[1].replaceAll('-', '')
      this.stateService.addAdminId(this.currentSessionId, toUUID(adminId));
      localStorage.setItem(this.inviteCode, UUID.UUID().toString());
      // the defaultPlaylist is created in spotify and can be used and modified in the spotify account or via the spotify API.
      // It is not used yet because we handle the playlist in our own database but can be used to improve playback-features in the future.
      this.restService.getDefaultPlaylist(results[1]).then(res => {
        this.restDefaultPlaylist = res;
      });

    });
  }

  //here we extract the spotify code spotify provided as query parameter in the redirectURI
  getCode() {
    let code = null;
    const queryString = window.location.search;
    if (queryString.length > 0) {
      const urlParams = new URLSearchParams(queryString);
      code = urlParams.get("code");
    }
    return code;
  }

  // triggers the spotify search in backend and returns the search results
  // that spotify delivers converted to Song-Objects, used in Frontend
  // it makes a difference if an admin or a guest is searching for a song
  async getSearchResults(searchString: string) {
    if (searchString.length > 2) {
      if (this.isAdmin) {
        const userId = this.stateService.getAdminId(this.currentSessionId);
        await this.restService.getSearchResult(searchString, userId).then(res => {
          this.searchResults = res as Song[];
        });
      } else {
        const userId = sessionStorage.getItem("currentUser");
        await this.restService.getSearchResult(searchString, userId).then(res => {
          this.searchResults = res as Song[];
        });
      }
      return this.searchResults;
    }
  }

  // adding selected song to playlist, determined by the userId (which is identically linked to the party)
  async addSong(song: Song) {
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    // create song for testing
    //let song: Song = { id: null, genre: null, name: null, spotifyUri: null };
    //song.id = UUID.UUID();
    //song.genre = null;
    //song.name = 'Testsong';
    //song.spotifyUri = '1234567890123:7xGfFoTpQ2E7fRF5lN10tr';
    //const song = this.searchResults[0];
    await this.restService.addSongToPlaylist(song, userId).then(res => {
      localStorage.setItem(this.inviteCode, UUID.UUID().toString());
    });
  }

  // the refresh-function for reading the current state of the playlist in database and updating shown playlist and player accordingly
  getVotingView() {
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    this.restService.getVotingView(userId).then(res => {
      this.rows = res as VotingView[];
    });
  }

  // resetting the player to select the first element of the playlist, after playing the song that song is removed from playlist,
  // therefore it only needs to select the first element
  async refreshPlayer() {
    if (this.rows[0]) {
      const parsedSpotifyUri = this.parseSongUri(this.rows[0].song.spotifyUri);
      this.songUrl = this.songUrlBasis1 + parsedSpotifyUri + this.songUrlBasis2;
      this.iframeSrc = this.sanitizer.bypassSecurityTrustResourceUrl(this.songUrl);
      let userId;
      if (this.isAdmin) {
        userId = this.stateService.getAdminId(this.currentSessionId);
      } else {
        userId = sessionStorage.getItem("currentUser");
      }
      await this.restService.getSongPlaybackTime(this.parseSongUri(this.rows[0].song.spotifyUri), userId).then(res => {
        this.duration = Number(res);
      });
      this.restService.deleteSong(this.rows[0].song.id.toString()).then(() => {
        localStorage.setItem(this.inviteCode, UUID.UUID().toString());
      });
      //this.setTimer();
    }
  }

  // the uri returned by the spotify API is not quite identical to the correct song URI, therefore some processing is needed
  parseSongUri(completeUri: string) {
    return completeUri.substring(14);
  }

  // adding +1 to the voting of one song
  upVote(value) {
    this.voteSetting = 1;
    this.vote(value);
  }

  // removing 1 from the voting of one song
  downVote(value) {
    this.voteSetting = -1;
    this.vote(value);
  }

  // generic voting function reading voteSetting to know whether song should be up- or downvoted and triggering update in database
  async vote(value) {
    console.log(value.id);
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    console.log(userId);
    if (this.voteSetting) {
      await this.restService.updateSongVoting(value.id, userId, this.voteSetting.toString()).then(res => {
        // the signal that is used so that every user instance knows that it should reload the shown playlist from the database
        localStorage.setItem(this.inviteCode, UUID.UUID().toString());
      });
    }
  }

  // another function as an attempt to get the autoplay working for the player
  async waitForSpotifyWebPlaybackSDKToLoad() {
    return new Promise(resolve => {
      if (window.Spotify) {
        resolve(window.Spotify);
      } else {
        window.onSpotifyWebPlaybackSDKReady = () => {
          resolve(window.Spotify);
        };
      }
    });
  };

  // parent function after song is selected to being added to playlist
  addThisSong(result: Song) {
    this.addSong(result);
    this.resetSearch();
  }

  /* song-search dropdown closes if the searchResults are NULL*/
  resetSearch() {
    this.searchResults = null;
    this.songSearch.nativeElement.value = '';
  }

  // the function used after playback of song has finished or admin wants to skip the song, thus the next song is played on the playlist
  async skipSong() {
    localStorage.setItem(this.inviteCode, UUID.UUID().toString());
    setTimeout(() => {
      this.refreshPlayer();
    }, 2000);
  }

  async setTimer() {
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    if (this.rows[0]) {
      console.log("Timer set");
      console.log(this.duration);
      setTimeout(() => {
        this.duration = 0;
        this.skipSong();
      }, this.duration);
    }
  }


}
