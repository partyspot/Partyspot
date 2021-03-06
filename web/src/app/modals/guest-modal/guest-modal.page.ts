import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-guest-modal',
  templateUrl: './guest-modal.page.html',
  styleUrls: ['./guest-modal.page.scss'],
})
export class GuestModalPage implements OnInit {

  constructor(public modalCtrl: ModalController) { }

  ngOnInit() {
  }

  dismiss() {
    this.modalCtrl.dismiss();
  }

}
