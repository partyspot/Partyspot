import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { RestService } from 'src/app/rest/restService/restService';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  /*@ViewChild('username')
  username: ElementRef;

  @ViewChild('password')
  password: ElementRef; */

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
    window.location.href = redirectURI;
    
  }

  guestLogin(): void {
    console.log(this.guestCode.nativeElement.value);
    this.router.navigate(['/overview']);
  }

}
