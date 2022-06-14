import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { ModalPage } from 'src/app/modals/modal/modal.page';
import { GuestModalPage } from 'src/app/modals/guest-modal/guest-modal.page';
import { GuestViewModalPage } from 'src/app/modals/guest-view-modal/guest-view-modal.page';
import { Router } from '@angular/router';
import { RestService } from 'src/app/rest/restService/restService';
import { StateService } from '../../services/stateService';
import { UUID } from 'angular2-uuid';
import { toUUID } from 'to-uuid'

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss'],
})
export class OverviewComponent implements OnInit {

  isAdmin: boolean = false;
  inviteCode: string;
  spotifyCode: string;
  partyName = "";
  private currentSessionId: UUID;

  constructor(public modalCtrl: ModalController, private router: Router, private restService: RestService, private stateService: StateService) { }

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
    this.stateService.removeAdminId(this.currentSessionId);
  }

  onPageLoad() {
    if (window.location.search.length > 0) {
      console.log("Page loaded");
      this.handleRedirect();
    }

  }

  async handleRedirect() {
    this.spotifyCode = this.getCode();
    this.isAdmin = true;
    await this.restService.createNewPartyWithNewCodeAndHostAndGetGuestCode(this.spotifyCode).then(res => {
      this.currentSessionId = UUID.UUID();
      console.log(res);
      const results = res.split(",");
      this.inviteCode = results[0];
      const adminId = results[1].replaceAll('-', '')
      this.stateService.addAdminId(this.currentSessionId, toUUID(adminId));
    });
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
