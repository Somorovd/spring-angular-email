import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { LoginRequestInterface } from '../types/loginRequest.interface';
import { environment } from '../../../environments/environment';
import { UserInterface } from '../../shared/types/user.interface';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  login(data: LoginRequestInterface): Observable<UserInterface> {
    const url = environment.apiUrl + '/auth/login';
    return this.http.post<UserInterface>(url, data);
  }
}
