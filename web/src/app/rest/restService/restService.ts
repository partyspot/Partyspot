declare var require: any
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UUID } from 'angular2-uuid';

@Injectable()
export class RestService {

    queryParams: any;

    options = {
        responseType: 'text' as const,
    };

    constructor(private http: HttpClient) { }

    async adminLogin(): Promise<string> {
        return this.http.get('http://localhost:8080/Backend/rest/login/loginWithSpotify', this.options).toPromise();
    }

    async createNewPartyWithNewCodeAndHostAndGetGuestCode(code: string): Promise<string> {
        return this.http.get('http://localhost:8080/Backend/rest/login/newPartyAndNewHost?code=' + code, this.options).toPromise();
    }

    async getDefaultPlaylist(adminId: UUID) {
        return this.http.get('http://localhost:8080/Backend/rest/db/getDefaultPlaylist?adminId=' + adminId.toString()).toPromise();
    }

    async getSearchResult(searchString: string, userId: UUID) {
        return this.http.get('http://localhost:8080/Backend/rest/party/searchSongs?searchString=' + searchString + '&userId=' + userId.toString()).toPromise();
    }

}