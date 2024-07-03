import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';

import { Store } from '@ngrx/store';

import { combineLatest, filter, map, tap } from 'rxjs';

import { BackButtonComponent } from '../shared/components/backButton/backButton.component';
import { MailActions } from '../shared/components/mail/store/actions';
import {
  selectDetails,
  selectDetailsEmails,
  selectDetailsStatus,
} from '../shared/components/mail/store/reducers';

@Component({
  selector: 'app-emailDetail',
  templateUrl: './emailDetail.component.html',
  standalone: true,
  imports: [CommonModule, BackButtonComponent],
  providers: [DatePipe],
})
export class EmailDetailComponent implements OnInit {
  statusId$ = this.route.paramMap.pipe(map((params) => params.get('statusId')));

  data$ = combineLatest({
    status: this.store.select(selectDetailsStatus),
    emails: this.store.select(selectDetailsEmails),
  });

  constructor(
    private store: Store,
    private route: ActivatedRoute,
    private router: Router,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    // Get status based on route param
    this.statusId$.subscribe((statusId) => {
      if (statusId === null) return;
      this.store.dispatch(MailActions.getStatus({ statusId: +statusId }));
    });
    // If successful get status, get the chain of emails
    this.store.select(selectDetailsStatus).subscribe((status) => {
      if (status === null) {
        this.router.navigate(['/mail']);
      }
      if (status) {
        this.store.dispatch(MailActions.getChain({ chainId: status?.chainId }));
        this.store.dispatch(
          MailActions.updateStatus({
            statusId: status?.id,
            statusUpdate: { isRead: true },
          })
        );
      }
    });
  }

  getFormattedDate(date: Date): string {
    return this.datePipe.transform(date, 'EEE, MMM d, y h:mm a') as string;
  }
}
