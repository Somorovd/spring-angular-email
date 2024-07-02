import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, map, of, switchMap } from 'rxjs';

import { MailService } from '../services/mail.service';
import { StatusListResponseInterface } from '../types/statusListResponse.interface';

import { MailActions } from './actions';

@Injectable()
export class MailEffects {
  constructor(private actions$: Actions, private mailService: MailService) {}

  getInbox$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(MailActions.getInbox),
      switchMap(() => {
        return this.mailService.getInbox().pipe(
          map((inbox: StatusListResponseInterface) => {
            return MailActions.getInboxSuccess({ inbox });
          }),
          catchError(() => of(MailActions.getInboxFailed()))
        );
      })
    );
  });
}
