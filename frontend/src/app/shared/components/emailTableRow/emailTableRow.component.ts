import { Component, Input, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Router } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faStar as faStarSolid } from '@fortawesome/free-solid-svg-icons';
import { faStar as faStarRegular } from '@fortawesome/free-regular-svg-icons';

import { Store } from '@ngrx/store';

import { EMPTY, Observable } from 'rxjs';

import { Status } from '../../types/status.interface';
import { MailActions } from '../mail/store/actions';
import { selectInboxStatusByIndex } from '../mail/store/reducers';

@Component({
  selector: 'app-email-table-row',
  templateUrl: './emailTableRow.component.html',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
  providers: [DatePipe],
})
export class EmailTableRowComponent implements OnInit {
  @Input() statusIndex: number = -1;

  status$: Observable<Status | null> = EMPTY;

  isStarred = false;

  faStarSolid = faStarSolid;
  faStarRegular = faStarRegular;

  constructor(
    private store: Store,
    private datePipe: DatePipe,
    private router: Router
  ) {}

  ngOnInit() {
    this.status$ = this.store.select(
      selectInboxStatusByIndex(this.statusIndex)
    );
    this.status$.subscribe((status) => {
      if (status) {
        this.isStarred = status.isStarred;
      }
    });
  }

  toggleStarred(event: Event, status: Status) {
    event.stopPropagation();
    this.isStarred = !this.isStarred;
    this.store.dispatch(
      MailActions.updateStatus({
        statusId: status.id,
        statusUpdate: { isStarred: !status.isStarred },
      })
    );
  }

  getFormattedDate(date: Date): string {
    if (date.getDay() === new Date().getDay()) {
      return this.datePipe.transform(date, 'h:mm a') as string;
    }
    if (date.getFullYear() === new Date().getFullYear()) {
      return this.datePipe.transform(date, 'MMM d') as string;
    }
    return this.datePipe.transform(date, 'M/d/yyyy') as string;
  }

  goToDetails(status: Status) {
    this.router.navigate(['/mail/inbox', status.id]);
  }
}
