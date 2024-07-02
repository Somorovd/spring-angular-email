import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, map } from 'rxjs';

import { LoginRequestInterface } from '../types/loginRequest.interface';
import { environment } from '../../../environments/environment';
import { PersistanceService } from '../../shared/services/persistance.service';
import { UserInterface } from '../../shared/types/user.interface';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(
    private http: HttpClient,
    private persistanceService: PersistanceService
  ) {}

  login(data: LoginRequestInterface): Observable<UserInterface> {
    const url = environment.apiUrl + '/auth/login';
    return this.http.post<UserInterface>(url, data);
  }

  getCurrentUser(): Observable<UserInterface> {
    const userId = this.persistanceService.get('userId');
    const url = environment.apiUrl + `/users/${userId}`;
    return this.http.get<UserInterface>(url);
  }
}
