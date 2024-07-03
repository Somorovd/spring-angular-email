import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

import { Store } from '@ngrx/store';

import { map } from 'rxjs';

import { EmailTableRowComponent } from '../emailTableRow/emailTableRow.component';
import { selectInboxCount } from '../mail/store/reducers';

@Component({
  selector: 'app-email-table',
  templateUrl: './emailTable.component.html',
  standalone: true,
  imports: [CommonModule, EmailTableRowComponent],
})
export class EmailTableComponent {
  count$ = this.store
    .select(selectInboxCount)
    .pipe(map((count) => Array.from({ length: count }, (_, i) => i)));

  constructor(private store: Store) {}
}
