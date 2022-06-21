import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { RestService } from 'src/app/rest/restService/restService';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  @ViewChild('guestCode')
  guestCode: ElementRef;

  constructor(private router: Router, private restService: RestService) {
  }

  ngOnInit() { }

  async redirectToSpotify(): Promise<void> {
    let redirectURI = '';
    await this.restService.adminLogin().then(res => {
      redirectURI = res;
    });
    console.log(redirectURI);
    sessionStorage.setItem("isAdmin", "y");
    window.location.href = redirectURI;

  }

  async guestLogin(): Promise<void> {
    console.log(this.guestCode.nativeElement.value);
    await this.restService.guestLogin(this.guestCode.nativeElement.value).then( userId => {
      sessionStorage.setItem("currentUser", userId);
      sessionStorage.setItem("isAdmin", "n");
      this.router.navigate(['/overview']);
    });
  }

}