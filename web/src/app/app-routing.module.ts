import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { OverviewComponent } from './components/overview/overview/overview.component';

const routes: Routes = [
  {
    path: ''
  },  {
    path: 'modal',
    loadChildren: () => import('./modals/modal/modal.module').then( m => m.ModalPageModule)
  },
  {
    path: 'guest-modal',
    loadChildren: () => import('./modals/guest-modal/guest-modal.module').then( m => m.GuestModalPageModule)
  },
  {
    path: 'guest-view-modal',
    loadChildren: () => import('./modals/guest-view-modal/guest-view-modal.module').then( m => m.GuestViewModalPageModule)
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
