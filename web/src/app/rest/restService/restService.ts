import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable()
export class RestService {

    options = {
        responseType: 'text' as const,
    };

    constructor(private http: HttpClient) { }

    async adminLogin() {
        let partycode = '';
        this.http.get("http:/localhost:8080/Backend/rest/login/newHost", this.options).toPromise().then(res => {
            partycode = res;
            return partycode;
        })
    }

}