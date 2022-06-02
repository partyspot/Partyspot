import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-guest-view-modal',
  templateUrl: './guest-view-modal.page.html',
  styleUrls: ['./guest-view-modal.page.scss'],
})
export class GuestViewModalPage implements OnInit {

  constructor(public modalCtrl: ModalController) { }

  ngOnInit() {
  }

  dismiss() {
    this.modalCtrl.dismiss();
  }
}
