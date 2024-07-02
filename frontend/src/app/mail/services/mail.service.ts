import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { StatusListResponseInterface } from '../types/statusListResponse.interface';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../auth/services/auth.service';

@Injectable({ providedIn: 'root' })
export class MailService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getInbox(): Observable<StatusListResponseInterface> {
    const url =
      environment.apiUrl + `/emails/user/${this.authService.userId}/inbox`;
    return this.http.get<StatusListResponseInterface>(url);
  }
}
