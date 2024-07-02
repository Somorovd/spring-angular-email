import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Store } from '@ngrx/store';

import { Observable, map } from 'rxjs';

import { selectCurrentUser } from '../store/reducers';
import { LoginRequestInterface } from '../types/loginRequest.interface';
import { environment } from '../../../environments/environment';
import { UserInterface } from '../../shared/types/user.interface';

@Injectable({ providedIn: 'root' })
export class AuthService {
  userId: number | null = null;

  constructor(private http: HttpClient, private store: Store) {
    this.store.select(selectCurrentUser).subscribe((user) => {
      if (!user) return;
      this.userId = user.id;
    });
  }

  login(data: LoginRequestInterface): Observable<UserInterface> {
    const url = environment.apiUrl + '/auth/login';
    return this.http.post<UserInterface>(url, data);
  }
}
