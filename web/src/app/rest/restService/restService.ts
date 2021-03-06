declare var require: any
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UUID } from 'angular2-uuid';
import { Song } from '../DTOModels/Song';

@Injectable()
export class RestService {

    queryParams: any;
    root: string;

    options = {
        responseType: 'text' as const,
    };

    constructor(private http: HttpClient) { 

        if (window.location.hostname === 'localhost') {
            this.root = 'http://localhost:8080/Backend/';
            } else {
            this.root = this.replaceBaseURL(window.location.origin) + '/Backend/';
            }
    }

    replaceBaseURL(origin: string) {
        const parts = origin.split(":");
        return parts[0].concat(":").concat(parts[1]).concat(":").concat("8080");
    }

    async adminLogin(): Promise<string> {
        return this.http.get(this.root + 'rest/login/loginWithSpotify', this.options).toPromise();
    }

    async guestLogin(guestCode: string, guestName: string): Promise<string> {
        return this.http.get(this.root + 'rest/login/guestLogin?guestCode=' + guestCode + '&username=' + guestName, this.options).toPromise();
    }

    async createNewPartyWithNewCodeAndHostAndGetGuestCode(code: string): Promise<string> {
        return this.http.get(this.root + 'rest/login/newPartyAndNewHost?code=' + code, this.options).toPromise();
    }

    async getDefaultPlaylist(adminId: UUID) {
        return this.http.get(this.root + 'rest/db/getDefaultPlaylist?adminId=' + adminId.toString(), this.options).toPromise();
    }

    async getSearchResult(searchString: string, userId: UUID) {
        return this.http.get(this.root + 'rest/party/searchSongs?searchString=' + searchString + '&userId=' + userId.toString()).toPromise();
    }

    async addSongToPlaylist(song: Song, userId: UUID) {
        return this.http.post(this.root + 'rest/party/addSong?userId=' + userId.toString(), song).toPromise();
    }

    async getTokenWithPartyCode(partycode: string) {
        return this.http.get(this.root + 'rest/party/getPartyToken?partycode=' + partycode, this.options).toPromise();
    }

    async getVotingView(userId: string) {
        return this.http.get(this.root + 'rest/party/getVotingView?userId=' + userId).toPromise();
    }

    async updateSongVoting(songId: string, userId: string, voteSetting: string) {
        return this.http.post(this.root + 'rest/party/updateVotingView?songId=' + songId + '&userId=' + userId + '&voteSetting=' + voteSetting, "").toPromise();
    }

    async getInviteCodeForUser(userId: string) {
        return this.http.get(this.root + 'rest/party/getInviteCodeForUser?userId=' + userId, this.options).toPromise();
    }

    async deleteSong(songId: string) {
        return this.http.get(this.root + 'rest/party/deleteSong?songId=' + songId, this.options).toPromise();
    }

    async getSongPlaybackTime(songUri: string, userId: string) {
        return this.http.get(this.root + 'rest/db/getSongPlaybackTime?spotifyUri=' + songUri + '&userId=' + userId, this.options).toPromise();
    }

}