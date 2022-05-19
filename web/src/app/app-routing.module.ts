import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  {
    path: ''
  }
];
@NgModule({
  imports: [
    RouterModule.forRoot([
      { path: '', component: LoginComponent }
    ])
  ],
})
export class AppRoutingModule { }
