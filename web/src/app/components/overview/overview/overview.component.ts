import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { ModalPage } from 'src/app/modals/modal/modal.page';
import { GuestModalPage } from 'src/app/modals/guest-modal/guest-modal.page';
import { GuestViewModalPage } from 'src/app/modals/guest-view-modal/guest-view-modal.page';
import { Router } from '@angular/router';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss'],
})
export class OverviewComponent implements OnInit {

  isAdmin: Boolean = false;
  inviteCode: String;
  partyName = "";

  constructor(public modalCtrl: ModalController, private router: Router) { }

  ngOnInit() {
    this.onPageLoad();
  }

  async showModal() {
    const modal = await this.modalCtrl.create({
      component: ModalPage
    });
    return await modal.present();
  }

  async showGuestModal() {
    const modal = await this.modalCtrl.create({
      component: GuestModalPage
    });
    return await modal.present();
  }

  async showGuestViewModal() {
    const modal = await this.modalCtrl.create({
      component: GuestViewModalPage
    });
    return await modal.present();
  }

  logout(): void {
    this.router.navigate(['/login']);
  }

  onPageLoad() {
    if (window.location.search.length > 0) {
      console.log("Page loaded");
      this.handleRedirect();
    }

  }

  handleRedirect() {
    this.inviteCode = this.getCode();
    this.isAdmin = true;
  }

  getCode() {
    let code = null;
    const queryString = window.location.search;
    if (queryString.length > 0) {
      const urlParams = new URLSearchParams(queryString);
      code = urlParams.get("code");
    }
    return code;
  }

}
