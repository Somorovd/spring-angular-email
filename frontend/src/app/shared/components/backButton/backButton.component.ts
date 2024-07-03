import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: ' app-back-button',
  template: `<button (click)="goBack()">
    <div
      class="rounded-full p-2 aspect-square flex-cc overflow-hidden hover:bg-slate-100"
    >
      <fa-icon [icon]="faArrowLeft"></fa-icon>
    </div>
  </button>`,
  standalone: true,
  imports: [FontAwesomeModule],
})
export class BackButtonComponent {
  faArrowLeft = faArrowLeft;

  constructor(private route: ActivatedRoute, private router: Router) {}

  goBack() {
    const currentRoute = this.router.url;
    const splitIndex = currentRoute.lastIndexOf('/');
    this.router.navigate([currentRoute.slice(0, splitIndex)]);
  }
}
