import { Injectable, inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, map, of, switchMap, tap } from 'rxjs';

import { AuthService } from '../services/auth.service';
import { PersistanceService } from '../../shared/services/persistance.service';
import { UserInterface } from '../../shared/types/user.interface';

import { AuthActions } from './actions';

@Injectable()
export class AuthEffects {
  constructor(
    private actions$: Actions,
    private authService: AuthService,
    private router: Router,
    private persistanceService: PersistanceService
  ) {}

  getCurrentUser$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.getCurrentUser),
      switchMap(() => {
        if (!this.persistanceService.get('userId')) {
          return of(AuthActions.getCurrentUserFailed());
        }
        return this.authService.getCurrentUser().pipe(
          map((currentUser: UserInterface) => {
            return AuthActions.getCurrentUserSuccess({ currentUser });
          }),
          catchError(() => of(AuthActions.getCurrentUserFailed()))
        );
      })
    );
  });

  login$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(AuthActions.login),
      switchMap(({ request }) => {
        return this.authService.login(request).pipe(
          map((currentUser: UserInterface) => {
            this.persistanceService.set('userId', currentUser.id);
            return AuthActions.loginSuccess({ currentUser });
          }),
          catchError((errorResponse: HttpErrorResponse) => {
            return of(AuthActions.loginFailed({ errors: errorResponse.error }));
          })
        );
      })
    );
  });

  redirectToMail$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(AuthActions.loginSuccess),
        tap(() => this.router.navigateByUrl('/mail'))
      ),
    { dispatch: false }
  );
}
