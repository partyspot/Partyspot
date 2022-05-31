import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { ModalPage } from 'src/app/modals/modal/modal.page';
import { Router } from '@angular/router';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss'],
})
export class OverviewComponent implements OnInit {

  constructor(public modalCtrl: ModalController, private router: Router) { }

  ngOnInit() { }

  async showModal() {
    const modal = await this.modalCtrl.create({
      component: ModalPage
    });
    return await modal.present();
  }

  logout(): void {
    this.router.navigate(['/login']);
  }

}
