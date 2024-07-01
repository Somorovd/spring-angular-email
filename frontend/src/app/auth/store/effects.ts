import { Injectable, inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, map, of, switchMap } from 'rxjs';

import { AuthService } from '../services/auth.service';
import { UserInterface } from '../../shared/types/user.interface';

import { AuthActions } from './actions';

@Injectable()
export class AuthEffects {
  constructor(private actions$: Actions, private authService: AuthService) {}

  login$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.login),
      switchMap(({ request }) => {
        return this.authService.login(request).pipe(
          map((currentUser: UserInterface) => {
            return AuthActions.loginSuccess({ currentUser });
          }),
          catchError((errorResponse: HttpErrorResponse) => {
            return of(AuthActions.loginFailed({ errors: errorResponse.error }));
          })
        );
      })
    );
  });
}
