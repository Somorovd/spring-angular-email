import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { HelloComponent } from './pages/hello/hello.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'hello', component: HelloComponent },
  {
    path: 'login',
    loadChildren: () => import('./auth/auth.routes').then((m) => m.loginRoutes),
  },
];
