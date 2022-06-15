import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { ModalPageModule } from './modals/modal/modal.module';

import { HttpClientModule } from '@angular/common/http';
import { OverviewComponent } from './components/overview/overview/overview.component';
import { LoginComponent } from './components/login/login.component';
import { RestService } from './rest/restService/restService';
import { StateService } from './components/services/stateService';

// Imports for Alerts
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { AlertModule } from 'ngx-alerts';

@NgModule({
  declarations: [AppComponent, OverviewComponent, LoginComponent],
  entryComponents: [],
  imports: [
    CommonModule,
    BrowserModule,
    HttpClientModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    ModalPageModule,
    FormsModule,
    BrowserAnimationsModule,
    AlertModule.forRoot({ maxMessages: 5, timeout: 5000, positionX: 'left' })
  ],
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    { provide: RestService, useClass: RestService },
    { provide: StateService, useClass: StateService }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

