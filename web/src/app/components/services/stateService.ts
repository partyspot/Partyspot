import { Injectable } from "@angular/core";
import { UUID } from "angular2-uuid";
import { Observable, Subject } from "rxjs";

@Injectable()
export class StateService {

  loggedInAdminIds: Map<UUID, UUID>;
  isLoggedIn: boolean;

  public addAdminId(sessionId: UUID, id: UUID) {
    this.initializeStorageValues();
    if (!this.loggedInAdminIds || JSON.stringify(this.loggedInAdminIds, this.replacer).toString() === '{}') {
      this.loggedInAdminIds = new Map();
    }
    this.loggedInAdminIds.set(sessionId, id);
    localStorage.setItem('loggedInAdminIds', JSON.stringify(this.loggedInAdminIds, this.replacer));
    this.isLoggedIn = true;
  }

  public getAdminId(sessionId: UUID): UUID {
    this.initializeStorageValues();
    return this.loggedInAdminIds.get(sessionId);
  }

  public removeAdminId(sessionId: UUID) {
    this.initializeStorageValues();
    this.loggedInAdminIds.delete(sessionId);
    localStorage.setItem('loggedInAdminIds', JSON.stringify(this.loggedInAdminIds, this.replacer));
    this.isLoggedIn = false;
  }

  private initializeStorageValues() {
    this.loggedInAdminIds = JSON.parse(localStorage.getItem('loggedInAdminIds'), this.reviver);
  }

  replacer(key, value) {
    if (value instanceof Map) {
      return {
        dataType: 'Map',
        value: Array.from(value.entries()), // or with spread: value: [...value]
      };
    } else {
      return value;
    }
  }

  reviver(key, value) {
    if (typeof value === 'object' && value !== null) {
      if (value.dataType === 'Map') {
        return new Map(value.value);
      }
    }
    return value;
  }
}