import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { StatusListResponseInterface } from '../types/statusListResponse.interface';
import { PersistanceService } from '../../../services/persistance.service';
import { Status } from '../../../types/status.interface';
import { environment } from '../../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class MailService {
  userId = this.persistanceService.get('userId');

  constructor(
    private http: HttpClient,
    private persistanceService: PersistanceService
  ) {}

  getInbox(): Observable<StatusListResponseInterface> {
    const url = environment.apiUrl + `/emails/user/${this.userId}/inbox`;
    return this.http.get<StatusListResponseInterface>(url);
  }

  getStatus(statusId: number): Observable<Status> {
    const url = environment.apiUrl + `/statuses/${statusId}`;
    return this.http.get<Status>(url);
  }
}
