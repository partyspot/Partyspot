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
import { Subject, Subscription, timer } from 'rxjs';

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

  @ViewChild('songSearch')
  songSearch: ElementRef;

  constructor(public modalCtrl: ModalController, private router: Router, private restService: RestService,
    private stateService: StateService, private alertService: AlertService, private sanitizer: DomSanitizer) {
    this.songUrlBasis1 = 'https://open.spotify.com/embed/track/';
    this.songUrlBasis2 = '?utm_source=generator&theme=0';
    let id = '5YHdRUzLWIJCBv7oSlItvA';
    this.songUrl = this.songUrlBasis1 + id + this.songUrlBasis2;
    this.iframeSrc = this.sanitizer.bypassSecurityTrustResourceUrl(this.songUrl);
  }

  async ngOnInit() {
    await this.onPageLoad();
    // timer call the function immediately and every 5 seconds 
    setInterval(() => {
      if (this.needsPlaylistViewUpdateToken !== localStorage.getItem(this.inviteCode)) {
        this.needsPlaylistViewUpdateToken = localStorage.getItem(this.inviteCode);
        this.getVotingView();
      }
    }, 5000);
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
    if (sessionStorage.getItem("isAdmin") === "n") {
      this.isAdmin = false;
      await this.restService.getInviteCodeForUser(sessionStorage.getItem("currentUser")).then(res => {
        this.inviteCode = res;
        this.needsPlaylistViewUpdateToken = localStorage.getItem(this.inviteCode);
        return;
      });
    }
    if (sessionStorage.getItem("isAdmin") !== "n") {
      if (window.location.search.length > 0 && sessionStorage.getItem("isAdmin") === "y") {
        await this.handleRedirect();
      } else {
        this.alertService.danger('Bitte loggen Sie sich ein!');
        this.router.navigate(['/login']);
      }
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
      localStorage.setItem(this.inviteCode, UUID.UUID().toString());
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
        });
      } else {
        const userId = sessionStorage.getItem("currentUser");
        await this.restService.getSearchResult(searchString, userId).then(res => {
          this.searchResults = res as Song[];
        });
      }
      console.log(this.searchResults);
      return this.searchResults;
    }
  }

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
    const jsonsong = JSON.stringify(song);
    await this.restService.addSongToPlaylist(song, userId).then(res => {
      console.log("Song added");
      localStorage.setItem(this.inviteCode, UUID.UUID().toString());
    });
  }

  getVotingView() {
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    this.restService.getVotingView(userId).then(res => {
      this.rows = res as VotingView[];
      this.refreshPlayer();
    });
  }

  refreshPlayer() {
    if (this.rows[0]) {
      const parsedSpotifyUri = this.parseSongUri(this.rows[0].song.spotifyUri);
      this.songUrl = this.songUrlBasis1 + parsedSpotifyUri + this.songUrlBasis2;
      this.iframeSrc = this.sanitizer.bypassSecurityTrustResourceUrl(this.songUrl);
    }
  }

  parseSongUri(completeUri: string) {
    return completeUri.substring(14);
  }

  upVote() {
    this.voteSetting = 1;
    console.log("upVote")
  }

  downVote() {
    this.voteSetting = -1;
    console.log("downVote")
  }

  vote(event) {
    console.log(event.row.song.id);
    let userId;
    if (this.isAdmin) {
      userId = this.stateService.getAdminId(this.currentSessionId);
    } else {
      userId = sessionStorage.getItem("currentUser");
    }
    if (this.voteSetting) {
      this.restService.updateSongVoting(event.row.song.id, this.voteSetting.toString());
      console.log(this.voteSetting);
    }
  }

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

  addThisSong(result: Song) {
    console.log(result);
    this.addSong(result);
    this.resetSearch();
  }

  /* song-search dropdown closes if the searchResults are NULL*/
  resetSearch() {
    this.searchResults = null;
    this.songSearch.nativeElement.value = '';
  }

}
