import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faStar as faStarSolid } from '@fortawesome/free-solid-svg-icons';
import { faStar as faStarRegular } from '@fortawesome/free-regular-svg-icons';

import { Status } from '../../types/status.interface';

@Component({
  selector: 'app-email-table',
  templateUrl: './emailTable.component.html',
  standalone: true,
  imports: [CommonModule, FontAwesomeModule],
})
export class EmailTableComponent {
  @Input() emails: Status[] = [];

  faStarSolid = faStarSolid;
  faStarRegular = faStarRegular;

  toggleStarred(status: Status) {
    status.isStarred = !status.isStarred;
  }
}
