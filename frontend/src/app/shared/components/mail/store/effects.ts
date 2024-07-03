import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, map, mergeMap, of, switchMap } from 'rxjs';

import { MailService } from '../services/mail.service';
import { InboxStateInterface } from '../types/inboxState.interface';
import { Email } from '../../../types/email.interface';
import { Status } from '../../../types/status.interface';

import { MailActions } from './actions';

@Injectable()
export class MailEffects {
  constructor(private actions$: Actions, private mailService: MailService) {}

  getInbox$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(MailActions.getInbox),
      switchMap(() => {
        return this.mailService.getInbox().pipe(
          map((inbox: InboxStateInterface) => {
            for (let status of inbox.statuses) {
              status.email.date = new Date(status.email.date);
            }
            return MailActions.getInboxSuccess({ inbox });
          }),
          catchError(() => of(MailActions.getInboxFailed()))
        );
      })
    );
  });

  getStatus$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(MailActions.getStatus),
      switchMap(({ statusId }) => {
        return this.mailService.getStatus(statusId).pipe(
          map((status: Status) => {
            return MailActions.getStatusSuccess({ status });
          }),
          catchError(() => of(MailActions.getStatusFailed()))
        );
      })
    );
  });

  updateStatus$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(MailActions.updateStatus),
      mergeMap(({ statusId, statusUpdate }) => {
        return this.mailService.updateStatus(statusId, statusUpdate).pipe(
          map((status: Status) => {
            status.email.date = new Date(status.email.date);
            return MailActions.updateStatusSuccess({ status });
          }),
          catchError(() => of(MailActions.updateStatusFailed()))
        );
      })
    );
  });

  getChain$ = createEffect(() => {
    return this.actions$.pipe(
      ofType(MailActions.getChain),
      switchMap(({ chainId }) => {
        return this.mailService.getChain(chainId).pipe(
          map((emails: Email[]) => {
            return MailActions.getChainSuccess({ emails });
          }),
          catchError(() => of(MailActions.getChainFailed()))
        );
      })
    );
  });
}
