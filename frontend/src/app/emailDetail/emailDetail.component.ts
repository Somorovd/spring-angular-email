import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { BackButtonComponent } from '../shared/components/backButton/backButton.component';

@Component({
  selector: 'app-emailDetail',
  templateUrl: './emailDetail.component.html',
  standalone: true,
  imports: [BackButtonComponent],
})
export class EmailDetailComponent implements OnInit {
  statusId: string | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.statusId = params.get('id');
    });
  }
}
