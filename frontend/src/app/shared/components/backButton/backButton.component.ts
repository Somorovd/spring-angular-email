import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: ' app-back-button',
  template: `<button (click)="goBack()">back</button>`,
  standalone: true,
  imports: [],
})
export class BackButtonComponent {
  constructor(private route: ActivatedRoute, private router: Router) {}

  goBack() {
    const currentRoute = this.router.url;
    const splitIndex = currentRoute.lastIndexOf('/');
    this.router.navigate([currentRoute.slice(0, splitIndex)]);
  }
}
