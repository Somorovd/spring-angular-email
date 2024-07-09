import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { BannerComponent } from '../shared/components/banner/banner.component';
import { NavSidebarComponent } from '../shared/components/navSidebar/navSidebar.component';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  standalone: true,
  imports: [BannerComponent, NavSidebarComponent, RouterOutlet],
})
export class MailComponent {}
