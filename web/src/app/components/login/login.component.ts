import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  @ViewChild('username')
  username: ElementRef;

  @ViewChild('password')
  password: ElementRef;

  @ViewChild('guestCode')
  guestCode: ElementRef;

  constructor(private router: Router) { }

  ngOnInit() { }

  login(): void {
    console.log(this.username.nativeElement.value);
    console.log(this.password.nativeElement.value);
    console.log(this.guestCode.nativeElement.value);
    this.router.navigate(['/overview']);
  }

}
