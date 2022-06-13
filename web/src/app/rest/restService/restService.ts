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
        let redirectURI = '';
        return this.http.get('http://localhost:8080/Backend/rest/login/loginWithSpotify', this.options).toPromise();
    }

    sendTokenCode(code: string) {
        this.http.post('http://localhost:8080/Backend/rest/login/postCode?code=' + code, this.options);
    }

}