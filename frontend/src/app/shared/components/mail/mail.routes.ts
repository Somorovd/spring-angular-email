import { Route } from '@angular/router';

import { InboxComponent } from './components/inbox/inbox.component';

export const mailRoutes: Route[] = [
  {
    path: 'inbox',
    component: InboxComponent,
  },
  {
    path: '**',
    redirectTo: 'inbox',
  },
];
