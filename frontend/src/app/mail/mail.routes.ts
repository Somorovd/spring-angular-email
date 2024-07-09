import { Route } from '@angular/router';

import { EmailDetailComponent } from '../emailDetail/emailDetail.component';

import { MailComponent } from './mail.component';

import { InboxComponent } from './components/inbox/inbox.component';
import { SentComponent } from './components/sent/sent.component';
import { StarredComponent } from './components/starred/starred.component';
import { TrashComponent } from './components/trash/trash.component';

export const mailRoutes: Route[] = [
  {
    path: '',
    redirectTo: 'inbox',
    pathMatch: 'full',
  },
  {
    path: '',
    component: MailComponent,
    children: [
      { path: 'inbox', component: InboxComponent },
      { path: 'inbox/:statusId', component: EmailDetailComponent },
      { path: 'starred', component: StarredComponent },
      { path: 'sent', component: SentComponent },
      { path: 'drafts', component: StarredComponent },
      { path: 'trash', component: TrashComponent },
    ],
  },
];
