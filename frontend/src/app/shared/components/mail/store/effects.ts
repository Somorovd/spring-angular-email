import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, map, of, switchMap } from 'rxjs';

import { MailService } from '../services/mail.service';
import { InboxStateInterface } from '../types/inboxState.interface';
import { StatusListResponseInterface } from '../types/statusListResponse.interface';
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
          map((res: StatusListResponseInterface) => {
            const inbox = this._statusResToInboxState(res);
            return MailActions.getInboxSuccess({ inbox: inbox });
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

  _statusResToInboxState(
    res: StatusListResponseInterface
  ): InboxStateInterface {
    const inboxState: InboxStateInterface = {
      statuses: {},
      count: res.count,
    };
    res.statuses.forEach((status) => {
      inboxState.statuses[status.id] = status;
      status.email.date = new Date(status.email.date);
    });
    return inboxState;
  }
}
