import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

import { Store } from '@ngrx/store';

import { combineLatest } from 'rxjs';

import { EmailTableComponent } from '../shared/components/emailTable/emailTable.component';
import { MailActions } from '../shared/components/mail/store/actions';
import {
  selectError,
  selectInbox,
  selectIsLoading,
} from '../shared/components/mail/store/reducers';

@Component({
  selector: 'app-inbox',
  templateUrl: './inbox.component.html',
  standalone: true,
  imports: [CommonModule, EmailTableComponent],
})
export class InboxComponent implements OnInit {
  data$ = combineLatest({
    inbox: this.store.select(selectInbox),
    isLoading: this.store.select(selectIsLoading),
    error: this.store.select(selectError),
  });

  constructor(private store: Store) {}

  ngOnInit(): void {
    this.store.dispatch(MailActions.getInbox());
  }
}
