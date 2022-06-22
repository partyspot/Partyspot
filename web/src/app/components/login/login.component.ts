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
    if ((this.guestCode.nativeElement.value === '') || (this.guestName.nativeElement.value === '')) {
      this.alertService.danger('Sie benötigen einen Code und einen Benutzernamen, der mindestens 2 Zeichen lang ist');
    } else {
      await this.restService.guestLogin(this.guestCode.nativeElement.value, this.guestName.nativeElement.value).then(userId => {
        sessionStorage.setItem("currentUser", userId);
        sessionStorage.setItem("isAdmin", "n");
        this.router.navigate(['/overview']);
      },
        err => {
          console.log(err);
          this.alertService.danger('Ungültiger Zugangscode!');
        }
      )
    }
  }

}