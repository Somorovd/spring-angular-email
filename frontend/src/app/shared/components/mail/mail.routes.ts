import { Route } from '@angular/router';

import { InboxComponent } from '../../../inbox/inbox.component';

import { MailComponent } from './mail.component';

export const mailRoutes: Route[] = [
  {
    path: '',
    redirectTo: 'inbox',
    pathMatch: 'full',
  },
  {
    path: '',
    component: MailComponent,
    children: [{ path: 'inbox', component: InboxComponent }],
  },
];
