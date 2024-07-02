import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Store } from '@ngrx/store';

import { AuthService } from './auth/services/auth.service';
import { AuthActions } from './auth/store/actions';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [RouterOutlet],
})
export class AppComponent implements OnInit {
  constructor(private store: Store, private authService: AuthService) {}

  ngOnInit(): void {
    AuthActions.getCurrentUser();
  }
}
