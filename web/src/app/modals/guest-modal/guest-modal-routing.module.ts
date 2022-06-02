import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GuestModalPage } from './guest-modal.page';

const routes: Routes = [
  {
    path: '',
    component: GuestModalPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GuestModalPageRoutingModule {}
