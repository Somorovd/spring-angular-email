import { Routes } from '@angular/router';

import { HelloComponent } from './pages/hello/hello.component';
import { HomeComponent } from './pages/home/home.component';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'hello', component: HelloComponent },
  {
    path: 'login',
    loadChildren: () => import('./auth/auth.routes').then((m) => m.loginRoutes),
  },
];
