import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { StatusListResponseInterface } from '../types/statusListResponse.interface';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../auth/services/auth.service';
import { PersistanceService } from '../../shared/services/persistance.service';

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
}
