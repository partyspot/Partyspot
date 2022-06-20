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

  constructor(public modalCtrl: ModalController, private router: Router, private restService: RestService,
              private stateService: StateService, private alertService: AlertService ) { }

  async ngOnInit() {
    await this.onPageLoad();
    const playerInit = {
      name: 'Partyspot Player',
      getOAuthToken: async callback => {
        let accessToken = '';
        await this.restService.getTokenWithPartyCode(this.inviteCode).then(res => {
          accessToken = res.toString();
          console.log(accessToken);
          callback(accessToken);
          this.player = new Spotify.Player(playerInit);
          this.player.connect().then(success => {
            if (success) {
              console.log('The Web Playback SDK successfully connected to Spotify!');
            }
          })
        });
      },
      volume: 0.5
    }


    const play = ({
      spotify_uri,
      playerInstance: {
        _options: {
          getOAuthToken
        }
      }
    }) => {
      getOAuthToken(access_token => {
        fetch(`https://api.spotify.com/v1/me/player/play`, {
          method: 'PUT',
          body: JSON.stringify({ uris: [spotify_uri] }),
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${access_token}`
          },
        });
      });
    };
    
    // currentTrackUri has to be updated with first element in shown playlist
    this.currentTrackURI = 'spotify:track:7xGfFoTpQ2E7fRF5lN10tr';
    if (this.isAdmin && false) {
      play({
        playerInstance: new Spotify.Player(playerInit),
        spotify_uri: this.currentTrackURI,
      });
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
      await this.handleRedirect();
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
      console.log(res);
      const results = res.split(",");
      this.inviteCode = results[0];
      const adminId = results[1].replaceAll('-', '')
      this.stateService.addAdminId(this.currentSessionId, toUUID(adminId));
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
    let searchResults;
    if (this.isAdmin) {
      const userId = this.stateService.getAdminId(this.currentSessionId);
      await this.restService.getSearchResult(searchString, userId).then(res => {
        searchResults = res as Song[];
        console.log(searchResults);
      });
    } else {
      const userId = sessionStorage.getItem("currentUser");
      await this.restService.getSearchResult(searchString, userId).then(res => {
        searchResults = res as Song[];
        console.log(searchResults);
      });
    }
    return searchResults;
  }

  async addSong() {
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    await this.restService.addSongToPlaylist('5UFbfXuj4TjirzmcvTFBBy', userId).then(res => {
      const addedSong = res as Track;
      console.log(addedSong);
      this.shownPlaylist.push(addedSong);
      this.refreshPlayer();
    });
  }

  refreshPlayer() {

    const playerInit = {
      name: 'Partyspot Player',
      getOAuthToken: async callback => {
        let accessToken = '';
        await this.restService.getTokenWithPartyCode(this.inviteCode).then(res => {
          accessToken = res.toString();
          console.log(accessToken);
          callback(accessToken);
          this.player = new Spotify.Player(playerInit);
          this.player.connect().then(success => {
            if (success) {
              console.log('The Web Playback SDK successfully connected to Spotify!');
            }
          })
        });
      },
      volume: 0.5
    }


    const play = ({
      spotify_uri,
      playerInstance: {
        _options: {
          getOAuthToken
        }
      }
    }) => {
      getOAuthToken(access_token => {
        fetch(`https://api.spotify.com/v1/me/player/play`, {
          method: 'PUT',
          body: JSON.stringify({ uris: [spotify_uri] }),
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${access_token}`
          },
        });
      });
    };

    this.currentTrackURI = this.shownPlaylist[0].id;
    play({
      playerInstance: new Spotify.Player(playerInit),
      spotify_uri: this.currentTrackURI,
    });
  }

}
