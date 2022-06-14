declare var require: any
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

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

}