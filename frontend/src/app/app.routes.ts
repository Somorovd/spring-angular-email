import { Routes } from '@angular/router';

import { authGuard } from './auth/services/authGuard';

export const appRoutes: Routes = [
  {
    path: 'login',
    loadChildren: () => import('./auth/auth.routes').then((m) => m.loginRoutes),
  },
  {
    path: 'mail',
    loadChildren: () => import('./mail/mail.routes').then((m) => m.mailRoutes),
    // canActivate: [authGuard],
  },
];
