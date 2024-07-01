import { Routes } from '@angular/router';

import { HelloComponent } from './pages/hello/hello.component';
import { HomeComponent } from './pages/home/home.component';
import { authGuard } from './shared/services/authGuard';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'hello', component: HelloComponent },
  {
    path: 'login',
    loadChildren: () => import('./auth/auth.routes').then((m) => m.loginRoutes),
  },
  {
    path: 'mail',
    loadChildren: () => import('./mail/mail.routes').then((m) => m.mailRoutes),
    canActivate: [authGuard],
  },
];
