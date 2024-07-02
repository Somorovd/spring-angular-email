import { CommonModule, DatePipe } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faStar as faStarSolid } from '@fortawesome/free-solid-svg-icons';
import { faStar as faStarRegular } from '@fortawesome/free-regular-svg-icons';

import { Status } from '../../types/status.interface';

@Component({
  selector: 'app-email-table',
  templateUrl: './emailTable.component.html',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
  providers: [DatePipe],
})
export class EmailTableComponent implements OnChanges {
  @Input() statuses: { [key: number]: Status } = {};

  statusList: Status[] = [];

  faStarSolid = faStarSolid;
  faStarRegular = faStarRegular;

  constructor(private datePipe: DatePipe) {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['statuses'] && changes['statuses'].currentValue) {
      this.statusList = Object.values(this.statuses).sort((a, b) => {
        return b.email.date.getTime() - a.email.date.getTime();
      });
    }
  }

  toggleStarred(status: Status) {
    status.isStarred = !status.isStarred;
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
}
