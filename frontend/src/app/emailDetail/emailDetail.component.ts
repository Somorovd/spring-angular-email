import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Store } from '@ngrx/store';

import { combineLatest, filter, map, tap } from 'rxjs';

import { BackButtonComponent } from '../shared/components/backButton/backButton.component';
import { MailActions } from '../shared/components/mail/store/actions';
import {
  selectDetails,
  selectDetailsEmails,
  selectDetailsStatus,
} from '../shared/components/mail/store/reducers';
import { EmailDetailStateInterface } from '../shared/components/mail/types/emailDetailsState.interface';

@Component({
  selector: 'app-emailDetail',
  templateUrl: './emailDetail.component.html',
  standalone: true,
  imports: [BackButtonComponent],
})
export class EmailDetailComponent implements OnInit {
  statusId: string | null = null;

  data$ = combineLatest({
    status: this.store.select(selectDetailsStatus),
    emails: this.store.select(selectDetailsEmails),
  });

  constructor(private store: Store, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        filter(
          (params) => (
            console.log(params.get('statusId')), params.get('statusId') !== null
          )
        ),
        tap((params) => {
          this.statusId = params.get('statusId');
          this.store.dispatch(
            MailActions.getStatus({ statusId: +this.statusId! })
          );
        })
      )
      .subscribe();
  }
}
