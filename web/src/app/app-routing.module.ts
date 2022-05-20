import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { OverviewComponent } from './components/overview/overview/overview.component';

const routes: Routes = [
  {
    path: ''
  }
];
@NgModule({
  imports: [
    RouterModule.forRoot([
      { path: '', redirectTo: '/login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'overview', component: OverviewComponent },
    ])
  ],
})
export class AppRoutingModule { }
