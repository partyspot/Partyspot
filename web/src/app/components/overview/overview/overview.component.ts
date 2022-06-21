///  <reference types="@types/spotify-web-playback-sdk"/>
import { Component, OnInit } from '@angular/core';
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
  rows: [];

  constructor(public modalCtrl: ModalController, private router: Router, private restService: RestService,
    private stateService: StateService, private alertService: AlertService) { }

  async ngOnInit() {
    await this.onPageLoad();
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
      console.log("player connected!");
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
    if (window.location.search.length > 0) {
      if (sessionStorage.getItem("isAdmin") === "y") {
        await this.handleRedirect();
      } else {
        this.isAdmin = false;
      }
    } else {
      this.alertService.danger('Bitte loggen Sie sich ein!');
      this.router.navigate(['/login']);
    }

  }

  async handleRedirect() {
    this.spotifyCode = this.getCode();
    this.isAdmin = true;
    await this.restService.createNewPartyWithNewCodeAndHostAndGetGuestCode(this.spotifyCode).then(async res => {
      this.currentSessionId = UUID.UUID();
      const results = res.split(",");
      this.inviteCode = results[0];
      const adminId = results[1].replaceAll('-', '')
      this.stateService.addAdminId(this.currentSessionId, toUUID(adminId));
      this.restService.getDefaultPlaylist(results[1]).then(res => {
        this.restDefaultPlaylist = res;
        console.log(res);
      });

    });
  }

  getCode() {
    let code = null;
    const queryString = window.location.search;
    if (queryString.length > 0) {
      const urlParams = new URLSearchParams(queryString);
      code = urlParams.get("code");
    }
    return code;
  }

  async getSearchResults(searchString: string) {
    if (searchString.length > 2) {
      if (this.isAdmin) {
        const userId = this.stateService.getAdminId(this.currentSessionId);
        await this.restService.getSearchResult(searchString, userId).then(res => {
          this.searchResults = res as Song[];
          console.log(this.searchResults);
        });
      } else {
        const userId = sessionStorage.getItem("currentUser");
        await this.restService.getSearchResult(searchString, userId).then(res => {
          this.searchResults = res as Song[];
          console.log(this.searchResults);
        });
      }
      return this.searchResults;
    }
  }

  async addSong() {
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    // create song for testing
    let song: Song = { id: null, genre: null, name: null, spotifyUri: null };
    song.id = UUID.UUID();
    song.genre = null;
    song.name = 'Testsong';
    song.spotifyUri = '7xGfFoTpQ2E7fRF5lN10tr';
    const jsonsong = JSON.stringify(song);
    console.log(jsonsong);
    await this.restService.addSongToPlaylist(song, userId).then(res => {
      console.log("Song added");
    });
  }

  getVotingView() {
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    let view;
    this.restService.getVotingView(userId).then(res => {
      view = res as VotingView[];
      console.log(view);
    });
  }

  upVote() {
    console.log("upVote")
  }

  downVote() {
    console.log("downVote")
  }
  
  async waitForSpotifyWebPlaybackSDKToLoad () {
    return new Promise(resolve => {
      console.log("IM HERE0");
      if (window.Spotify) {
        console.log("IM HERE");
        resolve(window.Spotify);
      } else {
        window.onSpotifyWebPlaybackSDKReady = () => {
          resolve(window.Spotify);
          console.log("IM HERE1");
        };
      }
    });
  };

}
