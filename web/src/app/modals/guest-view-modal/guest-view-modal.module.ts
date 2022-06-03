import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { GuestViewModalPageRoutingModule } from './guest-view-modal-routing.module';

import { GuestViewModalPage } from './guest-view-modal.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    GuestViewModalPageRoutingModule
  ],
  declarations: [GuestViewModalPage]
})
export class GuestViewModalPageModule {}
