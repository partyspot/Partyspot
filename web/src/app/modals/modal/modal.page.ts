import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ModalController } from '@ionic/angular';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.page.html',
  styleUrls: ['./modal.page.scss'],
})
export class ModalPage implements OnInit {

  @ViewChild('partyName')
  partyName: ElementRef;

  partyNameToDisplay: String;

  constructor(public modalCtrl: ModalController) { }

  ngOnInit() {
    if (this.partyName !== undefined) {
      this.partyNameToDisplay = this.partyName.nativeElement.value;
    }
  }

  editParty() {
    this.partyNameToDisplay = this.partyName.nativeElement.value;
  }

  dismiss() {
    this.modalCtrl.dismiss();
  }
}  