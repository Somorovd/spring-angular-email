import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

import { Store } from '@ngrx/store';

import { combineLatest } from 'rxjs';

import { MailActions } from '../../store/actions';
import {
  selectError,
  selectInbox,
  selectIsLoading,
} from '../../store/reducers';
import { EmailTableComponent } from '../../../shared/components/emailTable/emailTable.component';

@Component({
  selector: 'app-sent',
  templateUrl: './sent.component.html',
  standalone: true,
  imports: [CommonModule, EmailTableComponent],
})
export class SentComponent implements OnInit {
  data$ = combineLatest({
    // inbox: this.store.select(selectInbox),
    // isLoading: this.store.select(selectIsLoading),
    // error: this.store.select(selectError),
  });

  constructor(private store: Store) {}

  ngOnInit(): void {
    // this.store.dispatch(MailActions.getInbox());
  }
}
