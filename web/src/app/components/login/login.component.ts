import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AlertService } from 'ngx-alerts';
import { RestService } from 'src/app/rest/restService/restService';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  @ViewChild('guestCode')
  guestCode: ElementRef;

  @ViewChild('guestName')
  guestName: ElementRef;

  constructor(private router: Router, private restService: RestService, private alertService: AlertService) {
  }

  ngOnInit() { }

  /* login for Host (admin) - works with the Spotify-API */
  async redirectToSpotify(): Promise<void> {
    let redirectURI = '';
    await this.restService.adminLogin().then(res => {
      redirectURI = res;
    });
    sessionStorage.setItem("isAdmin", "y");
    window.location.href = redirectURI;
  }

  async guestLogin(): Promise<void> {
    /* check if the user made any inputs at all */
    if ((this.guestCode.nativeElement.value === '') || (this.guestName.nativeElement.value === '')) {
      /* display the alert message if an input is missing */
      this.alertService.danger('Sie benötigen einen Code und einen Benutzernamen');
    } else {
      /* else activates if the code is valid and a username has been chosen */
      /* login with code and username and redirect to overview, set role to guest (isAdmin, n) */
      await this.restService.guestLogin(this.guestCode.nativeElement.value,
        this.guestName.nativeElement.value).then(userId => {
          sessionStorage.setItem("currentUser", userId);
          sessionStorage.setItem("isAdmin", "n");
          this.router.navigate(['/overview']);
        },
          err => {
            /* notify the user that the login has failed */
            this.alertService.danger('Ungültiger Zugangscode!');
          }
        )
    }
  }

}