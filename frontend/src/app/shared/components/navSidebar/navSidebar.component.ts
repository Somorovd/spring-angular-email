import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { NavSidebarLinkComponent } from './navSidebarLink.component';

@Component({
  selector: 'app-nav-sidebar',
  templateUrl: './navSidebar.component.html',
  standalone: true,
  imports: [CommonModule, RouterModule, NavSidebarLinkComponent],
})
export class NavSidebarComponent {
  links = [
    {
      title: 'Inbox',
      link: '/mail/inbox',
    },
    {
      title: 'Starred',
      link: '/mail/starred',
    },
    {
      title: 'Sent',
      link: '/mail/sent',
    },
    {
      title: 'Drafts',
      link: '/mail/drafts',
    },
    {
      title: 'Trash',
      link: '/mail/trash',
    },
  ];
}
