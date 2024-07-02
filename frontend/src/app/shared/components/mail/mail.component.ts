import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { BannerComponent } from '../banner/banner.component';
import { NavSidebarComponent } from '../navSidebar/navSidebar.component';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  standalone: true,
  imports: [BannerComponent, NavSidebarComponent, RouterOutlet],
})
export class MailComponent {}
