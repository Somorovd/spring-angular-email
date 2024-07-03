import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

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
  statusId$ = this.route.paramMap.pipe(map((params) => params.get('statusId')));

  data$ = combineLatest({
    status: this.store.select(selectDetailsStatus),
    emails: this.store.select(selectDetailsEmails),
  });

  constructor(
    private store: Store,
    private route: ActivatedRoute,
    private router: Router
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
      }
    });
  }
}
