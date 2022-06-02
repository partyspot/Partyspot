import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GuestViewModalPage } from './guest-view-modal.page';

const routes: Routes = [
  {
    path: '',
    component: GuestViewModalPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GuestViewModalPageRoutingModule {}
