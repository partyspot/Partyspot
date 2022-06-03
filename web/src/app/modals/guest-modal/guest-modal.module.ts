import { ElementRef, NgModule, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { GuestModalPageRoutingModule } from './guest-modal-routing.module';

import { GuestModalPage } from './guest-modal.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    GuestModalPageRoutingModule
  ],
  declarations: [GuestModalPage]
})
export class GuestModalPageModule {

  @ViewChild('mail')
  mail: ElementRef;

  inviteGuest(): void {
    console.log(this.mail.nativeElement.value);
  }

}
